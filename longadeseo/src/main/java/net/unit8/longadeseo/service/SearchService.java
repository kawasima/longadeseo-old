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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;


import net.unit8.longadeseo.dto.SearchResultDto;

import org.apache.jackrabbit.util.Text;

import com.google.inject.Inject;

public class SearchService implements Serializable {
	private static final long serialVersionUID = 423155072461918118L;

	@Inject
	protected transient Repository repository;

	public List<SearchResultDto> find(String q) {
		List<SearchResultDto> rowList = new ArrayList<SearchResultDto>();
		if (q == null || q.length() == 0)
			return rowList;

		Session jcrSession = null;
		try {
			jcrSession = repository.login(new SimpleCredentials("anonymous", ""
					.toCharArray()));

			String queryTerms = null;
			String stmt;
			if (q.startsWith("related:")) {
				String path = q.substring("related:".length());
				path = path.replaceAll("'", "''");
				stmt = "//element(*, nt:file)[rep:similar(jcr:content, '"
						+ path
						+ "/jcr:content')]/rep:excerpt(.) order by @jcr:score descending";
				queryTerms = "similar to <b>"
						+ Text.encodeIllegalXMLCharacters(path) + "</b>";
			} else {
				queryTerms = "for <b>" + Text.encodeIllegalXMLCharacters(q)
						+ "</b>";
				q = q.replaceAll("'", "''");
				stmt = "//element(*, nt:file)[jcr:contains(jcr:content, '" + q
						+ "')]/rep:excerpt(.) order by @jcr:score descending";
			}
			try {
				Query query = jcrSession.getWorkspace().getQueryManager()
						.createQuery(stmt, Query.XPATH);
				RowIterator iter = query.execute().getRows();
				for (; iter.hasNext();) {
					Row row = iter.nextRow();
					Node file = (Node) jcrSession.getItem(row.getValue(
							"jcr:path").getString());
					Node resource = file.getNode("jcr:content");

					SearchResultDto dto = new SearchResultDto();
					dto.setPath(file.getPath());
					dto.setTitle(Text.encodeIllegalXMLCharacters(file.getName()));
					dto.setExcerpt(row.getValue("rep:excerpt(jcr:content)")
							.getString());
					dto.setLastModified(resource
							.getProperty("jcr:lastModified").getDate());
					dto.setSize(resource.getProperty("jcr:data").getLength());
					rowList.add(dto);
				}
			} catch (InvalidQueryException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
			return rowList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (jcrSession != null && jcrSession.isLive())
				jcrSession.logout();
		}
	}
}
