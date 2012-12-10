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
package net.unit8.longadeseo.servlet;

import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import net.unit8.longadeseo.LongadeseoModule;
import net.unit8.longadeseo.plugin.PluginExecutionException;
import net.unit8.longadeseo.plugin.PluginManager;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavMethods;
import org.apache.jackrabbit.webdav.DavResource;
import org.apache.jackrabbit.webdav.WebdavRequest;
import org.apache.jackrabbit.webdav.WebdavResponse;
import org.apache.jackrabbit.webdav.simple.DavSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class WebdavServlet extends
		org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet {

	private static final long serialVersionUID = -1528828512434124574L;
	private static final Logger logger = LoggerFactory
			.getLogger(WebdavServlet.class);
	public static final String PLUGIN_MANAGER_KEY = PluginManager.class.getName();

	/** the guice injector */
	private transient Injector injector;

	/** the jcr repository */
	private transient Repository repository;

	@Override
	public void init() throws ServletException {
		super.init();
		injector = Guice.createInjector(new LongadeseoModule());
		try {
			Session session = null;
			Credentials creds = new SimpleCredentials("admin",
					"admin".toCharArray());
			try {
				session = getRepository().login(creds, "longadeseo");
			} catch (NoSuchWorkspaceException e) {
				session = getRepository().login(creds);
				session.getWorkspace().createWorkspace("longadeseo");
			}
			session.logout();
		} catch (LoginException e) {
			logger.error("login exception", e);
		} catch (RepositoryException e) {
			logger.error("repository exception", e);
		}
		getServletContext().setAttribute(PLUGIN_MANAGER_KEY, new PluginManager());
	}

	/**
	 * Returns the <code>Repository</code>. If no repository has been set or
	 * created the repository initialized by
	 * <code>RepositoryAccessServlet</code> is returned.
	 *
	 * @return repository
	 * @see RepositoryAccessServlet#getRepository(ServletContext)
	 */
	public Repository getRepository() {
		if (repository == null) {
			repository = injector.getInstance(Repository.class);
		}
		return repository;
	}

	/**
	 * Sets the <code>Repository</code>.
	 *
	 * @param repository
	 */
	public void setRepository(Repository repository) {
		this.repository = repository;
	}


	protected boolean execute(WebdavRequest request, WebdavResponse response,
			int method, DavResource resource) throws ServletException,
			IOException, DavException {
		PluginManager pluginManager = (PluginManager) getServletContext().getAttribute(PLUGIN_MANAGER_KEY);
		try {
			if(method == DavMethods.DAV_PUT || method == DavMethods.DAV_POST)
				pluginManager.beforeService(resource);
		} catch(PluginExecutionException e) {
			NotifyClient notifyClient = injector.getInstance(NotifyClient.class);
			if(notifyClient != null) {
				String username = DavSessionImpl.getRepositorySession(request.getDavSession()).getUserID();
				notifyClient.send(username, e.getLocalizedMessage());
			}
			response.sendError(WebdavResponse.SC_FORBIDDEN, e.getMessage());
			return true;
		}
		boolean ret = super.execute(request, response, method, resource);
		if(method == DavMethods.DAV_PUT || method == DavMethods.DAV_POST)
			pluginManager.afterService(resource);
		return ret;
	}

}
