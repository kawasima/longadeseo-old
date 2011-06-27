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
package net.unit8.longadeseo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;


import net.unit8.longadeseo.RepositoryRuntimeException;
import net.unit8.longadeseo.dto.PluginOptionEntry;
import net.unit8.longadeseo.dto.PluginRegistry;
import net.unit8.longadeseo.plugin.Plugin;

import org.apache.jackrabbit.value.ValueFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginRegistryService implements Serializable {
	private static final long serialVersionUID = -3094642174698191113L;
	private static final Logger logger = LoggerFactory.getLogger(PluginRegistryService.class);
	public transient Session session;

	@SuppressWarnings("unchecked")
	public List<PluginRegistry> findAll() {
		List<PluginRegistry> pluginRegistryList = new ArrayList<PluginRegistry>();
		try {
			Node root = session.getRootNode();
			if (root.hasNode("plugins")) {
				NodeIterator iter = root.getNode("plugins").getNodes("plugin");
				while (iter.hasNext()) {
					PluginRegistry pluginRegistry = new PluginRegistry();
					Node node = iter.nextNode();
					pluginRegistry.setName(node.getProperty("name").getString());
					pluginRegistry.setDescription(node.getProperty("description").getString());

					Value[] values = node.getProperty("includes").getValues();
					if(values != null){
						List<String> includes = new ArrayList<String>(values.length);
						for(Value v : values) {
							includes.add(v.getString());
						}
						pluginRegistry.setIncludes(includes.toArray(new String[0]));
					}

					pluginRegistry.setActive(node.getProperty("isActive").getBoolean());
					Class<? extends Plugin> pluginClass;
					Plugin plugin;
					try {
						pluginClass = (Class<? extends Plugin>)Class.forName(node.getProperty("class").getString());
						plugin = pluginClass.newInstance();
					} catch(Exception e) {
						logger.error("Plugin load error. plugin " + node.getProperty("class").getString() + " was not loaded.", e);
						continue;
					}
					pluginRegistry.setPluginClass(pluginClass);
					if(node.hasNode("options")) {
						Node optionsNode = node.getNode("options");
						PropertyIterator propIter = optionsNode.getProperties();
						while(propIter.hasNext()) {
							Property prop = propIter.nextProperty();
							plugin.setOption(prop.getName(), prop.getValue());
						}
					}
					pluginRegistry.setPlugin(plugin);
					pluginRegistryList.add(pluginRegistry);
				}
			}
		} catch (RepositoryException e) {
			throw new RepositoryRuntimeException(e);
		}
		return pluginRegistryList;
	}

	public int insert(PluginRegistry pluginRegistry) {
		try {
			Node root = session.getRootNode();
			Node pluginsNode;
			if (!root.hasNode("plugins")) {
				pluginsNode = root.addNode("plugins");
			} else {
				pluginsNode = root.getNode("plugins");
			}
			Node pluginNode = pluginsNode.addNode("plugin");
			populate(pluginNode, pluginRegistry);
		} catch(RepositoryException e) {
			throw new RepositoryRuntimeException(e);
		}
		return 1;
	}

	public int update(PluginRegistry pluginRegistry) {
		try {
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery("SELECT * FROM [nt:unstructured] WHERE ISCHILDNODE([/plugins]) AND name = $name", Query.JCR_SQL2);
			query.bindValue("name", ValueFactoryImpl.getInstance().createValue(pluginRegistry.getName()));
			QueryResult result = query.execute();
			Node pluginNode = result.getNodes().nextNode();
			if(pluginNode == null)
				return 0;
			populate(pluginNode, pluginRegistry);
		} catch (RepositoryException e) {
			throw new RepositoryRuntimeException(e);
		}
		return 1;
	}

	public int delete(PluginRegistry pluginRegistry) {
		try {
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery("SELECT * FROM [nt:unstructured] WHERE ISCHILDNODE([/plugins]) AND name = $name", Query.JCR_SQL2);
			query.bindValue("name", ValueFactoryImpl.getInstance().createValue(pluginRegistry.getName()));
			QueryResult result = query.execute();
			Node pluginNode = result.getNodes().nextNode();
			if(pluginNode == null)
				return 0;
			pluginNode.remove();
		} catch (RepositoryException e) {
			throw new RepositoryRuntimeException(e);
		}
		return 1;
	}

	private void populate(Node pluginNode, PluginRegistry pluginRegistry) throws RepositoryException{
		pluginNode.setProperty("name", pluginRegistry.getName());
		pluginNode.setProperty("description", pluginRegistry.getDescription());
		pluginNode.setProperty("class", pluginRegistry.getPluginClass().getName());
		pluginNode.setProperty("includes", pluginRegistry.getIncludes());
		pluginNode.setProperty("isActive", pluginRegistry.isActive());
		List<PluginOptionEntry> options = pluginRegistry.getPlugin().getOptions();
		if(options != null) {
			NodeIterator optionsIter = pluginNode.getNodes("options");
			while(optionsIter.hasNext()) {
				optionsIter.nextNode().remove();
			}
			Node optionsNode = pluginNode.addNode("options");
			for(PluginOptionEntry e : options) {
				if(e.getValue() == null) {
					optionsNode.setProperty(e.getName(), "");
				} else if(e.getValue() instanceof String) {
					optionsNode.setProperty(e.getName(), e.getStringValue());
				} else {
					throw new RepositoryException("can't handle this option type:" + e.getValue().getClass());
				}
			}
		}
	}
}
