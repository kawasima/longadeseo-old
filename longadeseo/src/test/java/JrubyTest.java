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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

import net.unit8.axebomber.parser.Book;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

@SuppressWarnings("restriction")
public class JrubyTest {
	static {
		System.setProperty("org.jruby.embed.localvariable.behavior", "transient");
		System.setProperty("org.jruby.embed.localcontext.scope", "threadsafe");
	}

	private Workbook load(InputStream in) throws InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(in);
		return workbook;
	}

	/**
	 * 別のExcelシートに転記するサンプルです
	 *
	 * @throws Exception
	 */
	@Test
	public void parseTestSpecification() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("jruby");
		Reader scriptReader = null;
		InputStream in = null;
		try {
			in = getClass().getClassLoader().getResourceAsStream("test-specifications.xls");
			Workbook workbook = load(in);
			Book book = new Book(workbook);
			scriptReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("test-specification-parser.rb"));
			if(engine instanceof Compilable) {
				CompiledScript script = ((Compilable)engine).compile(scriptReader);
				SimpleBindings bindings = new SimpleBindings();
				bindings.put("@book", book);
				script.eval(bindings);
			}
		} finally {
			IOUtils.closeQuietly(scriptReader);
			IOUtils.closeQuietly(in);
		}

	}

	/**
	 * 
	 */
	@Test
	public void repeatedTitle() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("jruby");
		Reader scriptReader = null;
		InputStream in = null;
		try {
			in = getClass().getClassLoader().getResourceAsStream("test-specifications.xls");
			Workbook workbook = load(in);
			Book book = new Book(workbook);
			scriptReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("test-specification-repeated-title.rb"));
			if(engine instanceof Compilable) {
				CompiledScript script = ((Compilable)engine).compile(scriptReader);
				SimpleBindings bindings = new SimpleBindings();
				bindings.put("@book", book);
				script.eval(bindings);
			}
		} finally {
			IOUtils.closeQuietly(scriptReader);
			IOUtils.closeQuietly(in);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void poi() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("jruby");
		Reader scriptReader = null;
		InputStream in = null;
		try {
			in = getClass().getClassLoader().getResourceAsStream("sample1.xls");
			Workbook workbook = load(in);
			Book book = new Book(workbook);
			scriptReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("test.rb"));
			if(engine instanceof Compilable) {
				CompiledScript script = ((Compilable)engine).compile(scriptReader);
				SimpleBindings bindings = new SimpleBindings();
				bindings.put("@book", book);
				script.eval(bindings);
			}
		} finally {
			IOUtils.closeQuietly(scriptReader);
			IOUtils.closeQuietly(in);
		}
	}

}
