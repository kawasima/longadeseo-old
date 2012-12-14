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

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Value;

import net.unit8.longadeseo.RepositoryRuntimeException;
import net.unit8.longadeseo.dto.PluginOptionEntry;
import net.unit8.longadeseo.dto.PluginRegistry;
import net.unit8.longadeseo.page.BasePage;
import net.unit8.longadeseo.plugin.Plugin;
import net.unit8.longadeseo.plugin.PluginManager;
import net.unit8.longadeseo.service.PluginRegistryService;
import net.unit8.longadeseo.servlet.WebdavServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.value.ValueFactoryImpl;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PluginListPage extends BasePage {
	private static final Logger logger = LoggerFactory.getLogger(PluginListPage.class);
	private static final JavaScriptResourceReference CODEMIRROR_JS = new JavaScriptResourceReference(BasePage.class, "js/codemirror.js");
	private static final JavaScriptResourceReference CODEMIRROR_RUBY_JS = new JavaScriptResourceReference(BasePage.class, "js/mode/ruby/ruby.js");
	private static final CssResourceReference CODEMIRROR_CSS = new CssResourceReference(BasePage.class, "css/codemirror.css");

	@Inject
	PluginRegistryService pluginRegistryService;

	private List<PluginRegistry> pluginRegistryList;

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptReferenceHeaderItem.forReference(CODEMIRROR_JS));
		response.render(JavaScriptReferenceHeaderItem.forReference(CODEMIRROR_RUBY_JS));
		response.render(CssReferenceHeaderItem.forReference(CODEMIRROR_CSS));
	}
	public PluginListPage() {
		add(new Label("pageTitle", "プラグインの設定"));
		pluginRegistryList = pluginRegistryService.findAll();

		final ModalWindow window = new ModalWindow("testWindow");
		window.setTitle("test");
		add(window);

		add(new ListView<PluginRegistry>("pluginRegistryList", pluginRegistryList) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PluginRegistry> item) {
				final PluginRegistry pluginRegistry = item.getModelObject();
				Form<PluginRegistry> pluginUpdateForm = new Form<PluginRegistry>("pluginUpdateForm", new CompoundPropertyModel<PluginRegistry>(pluginRegistry));
				item.add(pluginUpdateForm);

				final Model<String> includes = new Model<String>(StringUtils.join(pluginRegistry.getIncludes(), "\n"));
				pluginUpdateForm
					.add(new Label("name"))
					.add(new AjaxEditableMultiLineLabel<PluginRegistry>("description") {
						private static final long serialVersionUID = 1L;
						@Override
						protected void onSubmit(AjaxRequestTarget target) {
							pluginRegistryService.update(pluginRegistry);
							super.onSubmit(target);
						}
					})
					.add(new Label("pluginClass", pluginRegistry.getPluginClass().getName()))
					.add(new AjaxEditableMultiLineLabel<String>("pluginIncludes", includes) {
						private static final long serialVersionUID = 1L;
						@Override
						protected void onSubmit(AjaxRequestTarget target) {
							pluginRegistry.setIncludes(includes.getObject().split("\n"));
							pluginRegistryService.update(pluginRegistry);
							super.onSubmit(target);
						}
					})
					.add(new Button("deleteButton") {
						private static final long serialVersionUID = 1L;
						@Override
						public void onSubmit() {
							pluginRegistryService.delete(pluginRegistry);
							pluginRegistryList.remove(pluginRegistry);
							super.onSubmit();
						}
					})
					.add(new AjaxButton("activeButton", new Model<String>(activeButtonLabel(pluginRegistry.isActive()))) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onSubmit(AjaxRequestTarget target, Form<?> form) {
							pluginRegistry.setActive(!pluginRegistry.isActive());
							this.setModelObject(activeButtonLabel(pluginRegistry.isActive()));
							pluginRegistryService.update(pluginRegistry);
							PluginManager pluginManager = (PluginManager) WebApplication.get().getServletContext().getAttribute(WebdavServlet.PLUGIN_MANAGER_KEY);
							pluginManager.loadPlugins();
							target.add(this);
						}
					}.setOutputMarkupId(true))
					.add(new AjaxButton("testButton") {
						private static final long serialVersionUID = 1L;
						@Override
						protected void onSubmit(AjaxRequestTarget target,
								Form<?> form) {
							window.setPageCreator(new ModalWindow.PageCreator(){
								private static final long serialVersionUID = 1L;

								public Page createPage() {
									return new PluginTestPage(pluginRegistry);
								}
							});
							window.show(target);
						}
					});
				final WebMarkupContainer optionsContainer = new WebMarkupContainer("optionsContainer");
				final ListView<PluginOptionEntry> options = new ListView<PluginOptionEntry>("options", pluginRegistry.getPlugin().getOptions()) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(final ListItem<PluginOptionEntry> item) {
						final PluginOptionEntry option = item.getModelObject();
						item.add(new Label("name", option.getLabel()));
						switch(option.getFormType()) {
						case TEXTAREA:
							item.add(new AjaxEditableMultiLineLabel<PluginOptionEntry>("value", new PropertyModel<PluginOptionEntry>(option, "value")) {
								private static final long serialVersionUID = 1L;
								@Override
								public void onEdit(AjaxRequestTarget target) {
									String selector = ".codemirror:eq(" + (item.getIndex() - 1) + ") textarea";
									target.appendJavaScript("CodeMirror.fromTextArea($('" + selector +
											"')[0], {mode: 'text/x-ruby', lineNumbers: true,indentUnit: 2,tabMode: 'shift',matchBrackets: true})" +
											".on('blur', function(cm) { cm.save(); $('"+ selector +"').trigger('blur') });");
									super.onEdit(target);
								}
								@Override protected void onSubmit(AjaxRequestTarget target) {
									pluginRegistry.getPlugin().setOption(option.getName(), ValueFactoryImpl.getInstance().createValue(option.getStringValue()));
									pluginRegistryService.update(pluginRegistry);
									super.onSubmit(target);
								}
							}.add(new AttributeModifier("class", "codemirror")));
							break;
						default:
							item.add(new AjaxEditableLabel<PluginOptionEntry>("value", new PropertyModel<PluginOptionEntry>(option, "value")) {
								private static final long serialVersionUID = 1L;
								@Override protected void onSubmit(AjaxRequestTarget target) {
									pluginRegistry.getPlugin().setOption(option.getName(), ValueFactoryImpl.getInstance().createValue(option.getStringValue()));
									pluginRegistryService.update(pluginRegistry);
									super.onSubmit(target);
								}
							});
							break;
						}
					}
				};
				optionsContainer.add(options);
				pluginUpdateForm.add(optionsContainer);

			}

		});

		Form<ValueMap> form = new PluginRegistryForm("pluginRegistryForm");
		add(form);
	}

	private String activeButtonLabel(boolean isActive) {
		return (isActive)?"Inactive" : "Active";
	}
	public final class PluginRegistryForm extends Form<ValueMap> {
		private static final long serialVersionUID = 1L;
		private List<PluginOptionEntry> optionList = new ArrayList<PluginOptionEntry>();

		public PluginRegistryForm(String id) {
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
			add(new FeedbackPanel("errorMessages"));

			add(new TextField<String>("newName").setRequired(true));
			add(new TextArea<String>("newDescription").setRequired(true));
			final TextField<String> newClass = new TextField<String>("newClass");
			add(newClass.setRequired(true));
			final Label newClassWarning = new Label("newClassWarning", "");
			newClassWarning.setOutputMarkupId(true);
			add(newClassWarning);

			final WebMarkupContainer newOptionsContainer = new WebMarkupContainer("newOptionsContainer");
			final ListView<PluginOptionEntry> newOptions = new ListView<PluginOptionEntry>("newOptions", optionList) {
				private static final long serialVersionUID = 1L;
				@Override
				protected void populateItem(ListItem<PluginOptionEntry> item) {
					final PluginOptionEntry option = item.getModelObject();
					item.add(new Label("optionNameLabel",  option.getLabel()));
					item.add(new HiddenField<String>("name"));
					Object value = option.getValue();
					if(value == null)
						value = "";
					switch(option.getFormType()) {
					case TEXTAREA:
						item.add(new TextArea<String>("value")
								.add(new AttributeModifier("class", "codemirror")));
						break;
					default:
						item.add(new TextField<String>("value"));
						break;
					}
				}
				@Override
				protected IModel<PluginOptionEntry> getListItemModel(IModel<? extends List<PluginOptionEntry>> model, int i) {
					List<PluginOptionEntry> list = model.getObject();
					return new CompoundPropertyModel<PluginOptionEntry>(list.get(i));
				}
			};
			newOptions.setReuseItems(true);
			newOptionsContainer.setOutputMarkupId(true);
			newOptionsContainer.add(newOptions);
			add(newOptionsContainer);

			newClass.add(new AjaxFormComponentUpdatingBehavior("onblur") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					optionList.clear();
					try {
						@SuppressWarnings("unchecked")
						Class<? extends Plugin> pluginClass = (Class<? extends Plugin>)Class.forName(newClass.getInput());
						Plugin plugin = pluginClass.newInstance();
						for(PluginOptionEntry option : plugin.getOptions()) {
							optionList.add(option);
						}
						newClassWarning.setDefaultModelObject("");
						target.add(newClassWarning);
						target.add(newOptionsContainer);
						String selector = ".codemirror[name^=newOptionsContainer\\\\:newOptions]";
						target.appendJavaScript("if ($('"+selector+"').size() > 0) { CodeMirror.fromTextArea($('" + selector +
								"')[0], {mode: 'text/x-ruby', lineNumbers: true,indentUnit: 2,tabMode: 'shift',matchBrackets: true})" +
								".on('blur', function(cm) { cm.save(); $('"+ selector +"').trigger('blur') });}");
					} catch(Exception e) {
						logger.warn("Plugin Class not found.", e);
						newClassWarning.setDefaultModelObject("Not found " + newClass.getInput() + " in classpath");
						target.add(newClassWarning);
					}
				}

			});
			add(new TextField<String>("newIncludes"));
		}

		@Override
		public void onSubmit() {
			ValueMap values = getModelObject();
			PluginRegistry pluginRegistry = new PluginRegistry();
			pluginRegistry.setName(values.getString("newName"));
			pluginRegistry.setDescription(values.getString("newDescription"));
			pluginRegistry.setIncludes(values.getString("newIncludes").split("\n"));
			try {
				@SuppressWarnings("unchecked")
				Class<? extends Plugin> pluginClass = (Class<? extends Plugin>)Class.forName(values.get("newClass").toString());
				pluginRegistry.setPluginClass(pluginClass);
				Plugin plugin = pluginClass.newInstance();
				pluginRegistry.setPlugin(plugin);
				@SuppressWarnings("unchecked")
				List<PluginOptionEntry> optionEntryList = (List<PluginOptionEntry>) this.get("newOptionsContainer:newOptions").getDefaultModelObject();
				if(optionEntryList != null) {
					for(PluginOptionEntry optionEntry : optionEntryList) {
						Value v = ValueFactoryImpl.getInstance().createValue(optionEntry.getStringValue());
						plugin.setOption(optionEntry.getName(), v);
					}
				}
			} catch (Exception e) {
				throw new RepositoryRuntimeException(e);
			}
			pluginRegistryService.insert(pluginRegistry);
			pluginRegistryList.add(pluginRegistry);
		}
	}
}
