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

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.jcr.Value;


import net.unit8.longadeseo.dto.PluginOptionEntry;

import org.apache.jackrabbit.webdav.DavResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Plugin implements Serializable{
	protected static final Logger logger = LoggerFactory.getLogger(Plugin.class);
	private static final long serialVersionUID = -1402313087122850003L;

	private String[] includes;
	private PluginDescriptor pluginDescriptor;

	public String[] getIncludes() {
		return includes;
	}

	public abstract void init();
	public abstract void beforeService(DavResource resource);
	public abstract void afterService(DavResource resource, InputStream in);

	public boolean apply(String resourcePath) {
		return true;
	}

	public void setOption(String name, Value value) {
		if(pluginDescriptor == null) {
			pluginDescriptor = new PluginDescriptor(this.getClass());
		}
		pluginDescriptor.setOption(this, name, value);
	}

	public List<PluginOptionEntry> getOptions() {
		if(pluginDescriptor == null) {
			pluginDescriptor = new PluginDescriptor(this.getClass());
		}
		return pluginDescriptor.getOptions(this);
	}

}
