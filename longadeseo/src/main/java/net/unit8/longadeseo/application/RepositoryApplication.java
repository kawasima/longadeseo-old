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
package net.unit8.longadeseo.application;


import net.unit8.longadeseo.LongadeseoModule;
import net.unit8.longadeseo.page.HomePage;
import net.unit8.longadeseo.page.ManualPage;
import net.unit8.longadeseo.page.plugin.PluginListPage;

import org.apache.wicket.Page;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

public class RepositoryApplication extends WebApplication {
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new GuiceComponentInjector(this, new LongadeseoModule()));
		mountPage("/manual", ManualPage.class);
		mountPage("/plugin/list", PluginListPage.class);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}


}
