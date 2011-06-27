/*******************************************************************************
 * Copyright 2011 kawasima
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.unit8.longadeseo.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;


import net.unit8.longadeseo.servlet.BootstrapConfig;
import net.unit8.longadeseo.servlet.ServletExceptionWithCause;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.google.inject.Provider;

public class RepositoryProvider implements Provider<Repository> {
	private static final Logger logger = LoggerFactory
			.getLogger(RepositoryProvider.class);
	/**
	 * initial param name for the bootstrap config location
	 */
	public final static String INIT_PARAM_BOOTSTRAP_CONFIG = "bootstrap-config";

	/**
	 * Context parameter name for 'this' instance.
	 */

	/**
	 * the file to the bootstrap config
	 */
	private File bootstrapConfigFile;

	/**
	 * the bootstrap config
	 */
	private BootstrapConfig config;

	/**
	 * the repository
	 */
	private static Repository repository;

	public RepositoryProvider() {
	}

	public Repository get() {
		try {
			synchronized (this) {
				if (repository == null) {
					startup();
				}
			}
			return repository;
		} catch (ServletException e) {
			throw new IllegalStateException(
					"The repository is not available. Please check"
							+ " RepositoryAccessServlet configuration in web.xml.",
					e);
		}
	}

	public void startup() throws ServletException {
		if (repository != null) {
			logger.error("Startup: Repository already running.");
			throw new ServletException("Repository already running.");
		}
		logger.info("RepositoryStartupServlet initializing...");
		try {
			if (configure()) {
				initRepository();
			}
			logger.info("RepositoryStartupServlet initialized.");
		} catch (ServletException e) {
			// shutdown repository
			shutdownRepository();
			logger.error("RepositoryStartupServlet initializing failed: " + e,
					e);
		}
	}

	/**
	 * Reads the configuration and initializes the {@link #config} field if
	 * successful.
	 * 
	 * @throws ServletException
	 *             if an error occurs.
	 */
	private boolean configure() throws ServletException {
		// check if there is a loadable bootstrap config
		Properties bootstrapProps = new Properties();
		String bstrp = "bootstrap.properties";
		if (bstrp != null) {
			// check if it's a web-resource
			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(bstrp);
			if (in == null) {
				// check if it's a file
				bootstrapConfigFile = new File(bstrp);
				if (bootstrapConfigFile.canRead()) {
					try {
						in = new FileInputStream(bootstrapConfigFile);
					} catch (FileNotFoundException e) {
						throw new ServletExceptionWithCause(
								"Bootstrap configuration not found: " + bstrp,
								e);
					}
				}
			}
			if (in != null) {
				try {
					bootstrapProps.load(in);
				} catch (IOException e) {
					throw new ServletException(
							"Bootstrap configuration failure: " + bstrp, e);
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		}

		// read bootstrap config
		config = new BootstrapConfig();
		config.init(bootstrapProps);
		config.validate();
		if (!config.isValid() || config.getRepositoryHome() == null
				|| config.getRepositoryConfig() == null) {
			logger.error("Repository startup configuration is not valid but a bootstrap config is specified.");
			logger.error("Either create the {} file or", bstrp);
			logger.error("use the '/config/index.jsp' for easy configuration.");
			return false;
		} else {
			config.logInfos();
			return true;
		}
	}

	/**
	 * Creates a new Repository based on the configuration and initializes the
	 * {@link #repository} field if successful.
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	private void initRepository() throws ServletException {
		// get repository config
		File repHome;
		try {
			repHome = new File(config.getRepositoryHome()).getCanonicalFile();
		} catch (IOException e) {
			throw new ServletExceptionWithCause(
					"Repository configuration failure: "
							+ config.getRepositoryHome(), e);
		}
		String repConfig = config.getRepositoryConfig();
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(repConfig);
		if (in == null) {
			try {
				in = new FileInputStream(new File(repConfig));
			} catch (FileNotFoundException e) {
				// fallback to old config
				try {
					in = new FileInputStream(new File(repHome, repConfig));
				} catch (FileNotFoundException e1) {
					throw new ServletExceptionWithCause(
							"Repository configuration not found: " + repConfig,
							e);
				}
			}
		}

		try {
			repository = createRepository(new InputSource(in), repHome);
		} catch (RepositoryException e) {
			throw new ServletExceptionWithCause(
					"Error while creating repository", e);
		}
	}

	/**
	 * Shuts down the repository. If the repository is an instanceof
	 * {@link JackrabbitRepository} it's {@link JackrabbitRepository#shutdown()}
	 * method is called. in any case, the {@link #repository} field is
	 * <code>nulled</code>.
	 */
	private void shutdownRepository() {
		if (repository instanceof JackrabbitRepository) {
			((JackrabbitRepository) repository).shutdown();
		}
		repository = null;
	}

	/**
	 * Creates the repository instance for the given config and homedir.
	 * Subclasses may override this method of providing own implementations of a
	 * {@link Repository}.
	 * 
	 * @param is
	 *            input source of the repository config
	 * @param homedir
	 *            the repository home directory
	 * @return a new jcr repository.
	 * @throws RepositoryException
	 *             if an error during creation occurs.
	 */
	protected Repository createRepository(InputSource is, File homedir)
			throws RepositoryException {
		RepositoryConfig config = RepositoryConfig.create(is,
				homedir.getAbsolutePath());
		return RepositoryImpl.create(config);
	}

	@Override
	protected void finalize() throws Throwable {
		shutdownRepository();
	}
}
