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
package net.unit8.longadeseo.page;


import net.unit8.longadeseo.page.plugin.PluginListPage;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

public class BasePage extends WebPage {
	public BasePage() {
		add(CSSPackageResource.getHeaderContribution("stylesheets/style.css"));
		add(new Link<String>("linkToPluginRegistry") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(PluginListPage.class);
			}
		});

		add(new Link<String>("linkToDirectoryList") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(TreePage.class);
			}
		});
	}
}
