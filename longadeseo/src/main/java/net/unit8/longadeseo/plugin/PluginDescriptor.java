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
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Value;

import net.unit8.longadeseo.RepositoryRuntimeException;
import net.unit8.longadeseo.dto.PluginOptionEntry;


public class PluginDescriptor implements Serializable {
	private static final long serialVersionUID = 4414713790266673318L;

	private transient Map<String, Field> fields;
	private Class<? extends Plugin> pluginClass;

	public PluginDescriptor(Class<? extends Plugin> pluginClass) {
		this.pluginClass = pluginClass;
		init();
	}

	private void init() {
		fields = new HashMap<String, Field>();
		for(Field field : pluginClass.getDeclaredFields()) {
			PluginOption anno = field.getAnnotation(PluginOption.class);
			if(anno != null) {
				fields.put(field.getName(), field);
			}
		}		
	}
	
	public void setOption(Plugin plugin, String optionName, Value value) {
		Field f = fields.get(optionName);
		if(f == null)
			return;
		Class<?> type = f.getType();
		try {
			Object v = null;
			if(type.equals(String.class)) {
				v = value.getString();
			} else if (type.equals(Integer.class) || type.equals(Long.class) || type.equals(Short.class)) {
				v = value.getDecimal();
			} else if (type.equals(Boolean.class)) {
				v = value.getBoolean();
			} else {
				throw new IllegalArgumentException("unknown option type: " + type);
			}
			if(!f.isAccessible())
				f.setAccessible(true);
			f.set(plugin, v);
		} catch(Exception e) {
			throw new RepositoryRuntimeException(e);
		}
	}

	public Object getOption(Plugin plugin, String optionName) {
		Field f = fields.get(optionName);
		if(f == null)
			throw new IllegalArgumentException("not found:" + optionName);
		try {
			if(!f.isAccessible())
				f.setAccessible(true);
			return f.get(plugin);
		} catch (Exception e) {
			throw new RepositoryRuntimeException(e);
		}
	}

	public List<PluginOptionEntry> getOptions(Plugin plugin) {
		List<PluginOptionEntry> options = new ArrayList<PluginOptionEntry>();
		if(fields != null) {
			for(Map.Entry<String, Field> e : fields.entrySet()) {
				PluginOptionEntry entry = new PluginOptionEntry(e.getKey(), getOption(plugin, e.getKey()));
				Field f = fields.get(e.getKey());
				PluginOption anno = f.getAnnotation(PluginOption.class);
				entry.setFormType(anno.formType());
				entry.setLabel((anno.label().length() > 0)?anno.label() : e.getKey());
				options.add(entry);
			}
		}
		return options;
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		init();
	}
}
