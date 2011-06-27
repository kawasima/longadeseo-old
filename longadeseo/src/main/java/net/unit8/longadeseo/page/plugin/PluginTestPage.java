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
package net.unit8.longadeseo.page.plugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import net.unit8.longadeseo.dto.MockDavResource;
import net.unit8.longadeseo.dto.PluginRegistry;
import net.unit8.longadeseo.plugin.Plugin;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginTestPage extends WebPage {
	private static final Logger logger = LoggerFactory.getLogger(PluginTestPage.class);

	public PluginTestPage(final PluginRegistry pluginRegistry) {
		final Label testResult = new Label("testResult", "");
		testResult.setOutputMarkupId(true);
		add(testResult);
		add(new Label("pluginName", pluginRegistry.getName()));

		Form<ValueMap> uploadForm = new Form<ValueMap>("uploadForm");
		final FileUploadField fileUploadField = new FileUploadField("testFile");
		fileUploadField.add(new AjaxFormSubmitBehavior(uploadForm, "onchange") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				FileUpload file = fileUploadField.getFileUpload();
				Plugin plugin = pluginRegistry.getPlugin();
				MockDavResource resource = new MockDavResource(file.getClientFileName());
				StringWriter error = new StringWriter();
				try {
					plugin.init();
					plugin.beforeService(resource);
					plugin.afterService(resource, file.getInputStream());
				} catch (IOException e) {
					logger.error("アップロードしたファイルが読み込めません", e);
				} catch (Exception e) {
					e.printStackTrace(new PrintWriter(error));
				} finally {
					file.delete();
				}
				testResult.setDefaultModelObject(error.toString());
				target.addComponent(testResult);
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
				// TODO 自動生成されたメソッド・スタブ
			}
		});
		uploadForm.add(fileUploadField);
		add(uploadForm);
	}
}
