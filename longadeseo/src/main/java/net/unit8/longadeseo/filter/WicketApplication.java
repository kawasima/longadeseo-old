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
package net.unit8.longadeseo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.wicket.application.ReloadingClassLoader;
import org.apache.wicket.protocol.http.ReloadingWicketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WicketApplication extends ReloadingWicketFilter {
	private Logger logger = LoggerFactory.getLogger(WicketApplication.class);
	static
	{
		ReloadingClassLoader.includePattern("longadeseo.application.**");
		ReloadingClassLoader.includePattern("longadeseo.page.**");
		ReloadingClassLoader.includePattern("longadeseo.service.**");
		ReloadingClassLoader.excludePattern("longadeseo.servlet.**");
		ReloadingClassLoader.excludePattern("longadeseo.plugin.**");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		logger.trace("longadeseo application filter start");
		super.doFilter(request, response, chain);
		logger.trace("longadeseo application filter end");
	}
}
