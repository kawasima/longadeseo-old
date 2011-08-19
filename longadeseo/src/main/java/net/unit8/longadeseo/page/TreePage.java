package net.unit8.longadeseo.page;

import java.util.List;

import net.unit8.longadeseo.dto.SearchResultDto;
import net.unit8.longadeseo.service.DirectoryService;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;

import com.google.inject.Inject;

public class TreePage extends BasePage {
	@Inject
	DirectoryService directoryService;

	public TreePage() {
		add(new Label("pageTitle", "The tree of directories"));
		List<SearchResultDto> directoryList = directoryService.list();

		final ListView<SearchResultDto> listView = new ListView<SearchResultDto>("directoryList", directoryList) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<SearchResultDto> item) {
				SearchResultDto row = item.getModelObject();
				item.add(new Label("path", row.getPath()));
			}
		};
		add(listView);

	}
}
