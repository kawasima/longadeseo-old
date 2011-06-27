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
package net.unit8.longadeseo.dto;

import java.io.Serializable;

import net.unit8.longadeseo.plugin.Plugin;
import net.unit8.longadeseo.util.AntPathMatcher;


/**
 * This class has registration of plugin in longadeseo.
 *
 * @author kawasima
 */
public class PluginRegistry implements Serializable {
	private static final long serialVersionUID = -9088870871764573522L;

	private String name;
	private String description;
	private Class<? extends Plugin> pluginClass;
	private Plugin plugin;
	private String[] includes;
	private Boolean isActive = false;
	private AntPathMatcher matcher;

	public PluginRegistry() {
		matcher = new AntPathMatcher();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Class<? extends Plugin> getPluginClass() {
		return pluginClass;
	}
	public void setPluginClass(Class<? extends Plugin> pluginClass) {
		this.pluginClass = pluginClass;
	}
	public String[] getIncludes() {
		return includes;
	}
	public void setIncludes(String[] includes) {
		this.includes = includes;
	}
	public Boolean isActive() {
		return isActive;
	}
	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public boolean isApplied(String resourcePath) {
		if(includes == null || includes.length == 0)
			return true;
		for(String include : includes) {
			if(matcher.match(include, resourcePath))
				return true;
		}
		return false;
	}
}
