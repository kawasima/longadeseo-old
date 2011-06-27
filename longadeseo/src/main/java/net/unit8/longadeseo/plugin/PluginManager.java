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
package net.unit8.longadeseo.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.List;


import net.unit8.longadeseo.LongadeseoModule;
import net.unit8.longadeseo.dto.PluginRegistry;
import net.unit8.longadeseo.service.PluginRegistryService;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.webdav.DavResource;
import org.apache.jackrabbit.webdav.io.OutputContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PluginManager implements Serializable {
	private static final long serialVersionUID = 5607517477328160751L;
	private transient Injector injector;

	List<PluginRegistry> pluginRegistryList;

	public PluginManager() {
		injector = Guice.createInjector(new LongadeseoModule());
		loadPlugins();
	}

	public void loadPlugins() {
		PluginRegistryService pluginRegistryService = injector.getInstance(PluginRegistryService.class);
		pluginRegistryList = pluginRegistryService.findAll();
		for(PluginRegistry pluginRegistry : pluginRegistryList) {
			pluginRegistry.getPlugin().init();
		}
	}

	public void beforeService(DavResource resource) {
		for (PluginRegistry pluginRegistry : pluginRegistryList) {
			if (!pluginRegistry.isActive() || !pluginRegistry.isApplied(resource.getResourcePath()))
				continue;
			Plugin plugin = pluginRegistry.getPlugin();
			plugin.beforeService(resource);
		}
	}

	public void afterService(DavResource resource) {
		for (PluginRegistry pluginRegistry : pluginRegistryList) {
			if (!pluginRegistry.isActive() || !pluginRegistry.isApplied(resource.getResourcePath()))
				continue;
			Plugin plugin = pluginRegistry.getPlugin();
			final PipedOutputStream out;
			InputStream in = null;
			try {
				out = new PipedOutputStream();
				in = new PipedInputStream(out);
				resource.spool(new OutputContext() {
					public void setProperty(String propertyName,
							String propertyValue) {
					}

					public void setModificationTime(long modificationTime) {
					}

					public void setETag(String etag) {
					}

					public void setContentType(String contentType) {
					}

					public void setContentLength(long contentLength) {
					}

					public void setContentLanguage(String contentLanguage) {
					}

					public boolean hasStream() {
						return true;
					}

					public OutputStream getOutputStream() {
						return out;
					}
				});
				plugin.afterService(resource, in);
			} catch (IOException e) {
				throw new PluginExecutionException(e);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
	}

}
