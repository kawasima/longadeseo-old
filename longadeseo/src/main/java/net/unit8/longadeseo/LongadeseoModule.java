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
package net.unit8.longadeseo;

import java.lang.reflect.Method;

import javax.jcr.Repository;

import net.unit8.longadeseo.interceptor.JcrSessionInterceptor;
import net.unit8.longadeseo.provider.RepositoryProvider;
import net.unit8.longadeseo.service.PluginRegistryService;
import net.unit8.longadeseo.service.SearchService;
import net.unit8.longadeseo.servlet.NotifyClient;


import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;

/**
 * Guice module class.
 * 
 * This class describes settings of the dependency injections.
 * 
 * @author kawasima
 */
public class LongadeseoModule implements Module {
	public void configure(Binder binder) {
		RepositoryProvider repositoryProvider = new RepositoryProvider();
		binder.bind(SearchService.class);
		binder.bind(PluginRegistryService.class);
		binder.bind(Repository.class).toProvider(repositoryProvider);
		binder.bind(NotifyClient.class).in(Singleton.class);
		binder.bindInterceptor(Matchers.inSubpackage("net.unit8.longadeseo.service"), new AbstractMatcher<Method>() {
			public boolean matches(Method t) {
				return t.getName().startsWith("find")
					|| t.getName().startsWith("insert")
					|| t.getName().startsWith("update")
					|| t.getName().startsWith("delete");
			}
		}, new JcrSessionInterceptor(binder.getProvider(Repository.class)));
	}
}
