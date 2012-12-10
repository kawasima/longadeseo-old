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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.unit8.longadeseo.dto.SearchResultDto;
import net.unit8.longadeseo.service.SearchService;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * The home page of longadeseo.
 *
 * @author kawasima
 */
@SuppressWarnings("serial")
public class HomePage extends BasePage {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(HomePage.class);

	private String query;
	private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Inject
	SearchService searchService;

	List<SearchResultDto> results = new ArrayList<SearchResultDto>();

	public HomePage() {
		add(new Label("pageTitle", "Top"));

		Form<ValueMap> form = new SearchForm("searchForm");
		form.setMarkupId("searchform");
		add(form);

		final PageableListView<SearchResultDto> listView = new PageableListView<SearchResultDto>("results", results, 5) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<SearchResultDto> item) {
				SearchResultDto row = item.getModelObject();
				item.add(new Label("excerpt", row.getExcerpt()).setEscapeModelStrings(false));
				ExternalLink link = new ExternalLink("titleLink",
						WebApplication.get().getServletContext().getContextPath() + "/repository/default" + row.getPath());
				link.add(new Label("title", row.getTitle()));
				item.add(link);
				item.add(new Label("size", String.valueOf(Math.round(Math.ceil(row.getSize() / 1000d))) + "k"));
				item.add(new Label("lastModified", df.format(row.getLastModified().getTime())));
				item.add(new Label("path", row.getPath()));
			}
		};
		add(listView);

		add(new PagingNavigation("navigation", listView) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(LoopItem loopItem) {
				final int page = loopItem.getIndex();
				final PagingNavigationLink<Label> link = new PagingNavigationLink<Label>("pageLink", listView, page);
				if(page > 0) {
					loopItem.add(new Label("separator", "|"));
				} else {
					loopItem.add(new Label("separator", ""));
				}
				link.add(new Label("pageNumber", String.valueOf(page + 1)));
				loopItem.add(link);
			}
		});
	}

	public final class SearchForm extends Form<ValueMap> {
		public SearchForm(String id) {
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
			add(new TextField<String>("query").setType(String.class));
		}

		@Override public void onSubmit() {
			ValueMap values = getModelObject();
			String query = (String)values.get("query");
			results.clear();
			results.addAll(searchService.find(query));
		}
	}

}
