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


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import net.unit8.axebomber.parser.Book;
import net.unit8.longadeseo.plugin.Plugin;
import net.unit8.longadeseo.plugin.PluginExecutionException;
import net.unit8.longadeseo.plugin.PluginOption;
import net.unit8.longadeseo.plugin.PluginOptionType;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.webdav.DavResource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class JRubyExcelPlugin extends Plugin {
	private static final long serialVersionUID = 3582301799818568571L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(JRubyExcelPlugin.class);
	private transient CompiledScript script;

	@PluginOption(label="スクリプトファイル名")
	private String scriptFile;

	@PluginOption(label="スクリプトコード", formType=PluginOptionType.TEXTAREA)
	private String scriptText;

	static {
		System.setProperty("org.jruby.embed.localvariable.behavior", "transient");
		System.setProperty("org.jruby.embed.localcontext.scope", "threadsafe");
	}

	public JRubyExcelPlugin() {

	}

	public void init() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("jruby");
		Reader scriptReader = null;
		try {
			if(scriptText == null || scriptText.length() == 0) {
				scriptReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(scriptFile));
				script = ((Compilable)engine).compile(scriptReader);
			} else {
				script = ((Compilable)engine).compile(scriptText);
			}
		} catch(ScriptException e) {
			throw new PluginExecutionException(e);
		} finally {
			IOUtils.closeQuietly(scriptReader);
		}
	}

	@Override
	public void beforeService(DavResource resource) {
	}

	@Override
	public void afterService(DavResource resource, InputStream in) {
		try {
			Workbook workbook = load(in);
			Book book = new Book(workbook);
			SimpleBindings bindings = new SimpleBindings();
			bindings.put("@book", book);
			script.eval(bindings);
		} catch (Exception e) {
			throw new PluginExecutionException(e);
		}
	}

	private Workbook load(InputStream in) throws InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(in);
		return workbook;
	}

}
