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
package net.unit8.longadeseo.plugin.impl;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import net.unit8.longadeseo.plugin.Plugin;
import net.unit8.longadeseo.plugin.PluginExecutionException;
import net.unit8.longadeseo.plugin.PluginOption;

import org.apache.jackrabbit.webdav.DavResource;

public class FilenameCheckPlugin extends Plugin {
	private static final long serialVersionUID = 7152676089794308463L;

	private transient Pattern pattern;

	@PluginOption(label="ファイル名のパターン")
	protected String filenamePattern;

	@Override
	public void init() {
		pattern = Pattern.compile(filenamePattern, Pattern.DOTALL);
	}

	@Override
	public void beforeService(DavResource resource) {
		Matcher matcher = pattern.matcher(resource.getDisplayName());
		if(!matcher.find())
			throw new PluginExecutionException("["+resource.getDisplayName()+"]はファイル名規約 ["+filenamePattern+"]に違反しています");
	}

	@Override
	public void afterService(DavResource resource, InputStream in) {
	}

}
