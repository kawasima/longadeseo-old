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

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.resource.CssResourceReference;

@SuppressWarnings("serial")
public class BasePage extends WebPage {
	private static final CssResourceReference STYLE_CSS = new CssResourceReference(BasePage.class, "css/style.css");
	private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(BasePage.class, "css/bootstrap-custom.css");

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssReferenceHeaderItem.forReference(BOOTSTRAP_CSS));
		response.render(CssReferenceHeaderItem.forReference(STYLE_CSS));
	}

	public BasePage() {
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
