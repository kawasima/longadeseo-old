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

import java.lang.reflect.Method;

import javax.jcr.Repository;

import net.unit8.longadeseo.interceptor.JcrSessionInterceptor;
import net.unit8.longadeseo.provider.RepositoryProvider;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;

public class PluginRepositoryServiceTest {

	Injector injector;
	@Before
	public void setUp() {
		injector = Guice.createInjector(new Module() {
			public void configure(Binder binder) {
				binder.bind(Repository.class).toProvider(RepositoryProvider.class);
				binder.bind(PluginRegistryService.class);
				binder.bindInterceptor(Matchers.inSubpackage("longadeseo.service"), new AbstractMatcher<Method>() {
					public boolean matches(Method t) {
						return (t.getName().startsWith("find"));
					}
				}, new JcrSessionInterceptor(binder.getProvider(Repository.class)));
			}
		});
	}

	@Test
	public void findAll() {
		PluginRegistryService pluginRegistryService = injector.getInstance(PluginRegistryService.class);
		pluginRegistryService.findAll();
	}
}
