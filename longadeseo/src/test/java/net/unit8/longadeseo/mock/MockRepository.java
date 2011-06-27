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
package net.unit8.longadeseo.mock;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

public class MockRepository implements Repository {

	public String[] getDescriptorKeys() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean isStandardDescriptor(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean isSingleValueDescriptor(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public Value getDescriptorValue(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Value[] getDescriptorValues(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getDescriptor(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Session login(Credentials credentials, String workspaceName)
			throws LoginException, NoSuchWorkspaceException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Session login(Credentials credentials) throws LoginException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Session login(String workspaceName) throws LoginException,
			NoSuchWorkspaceException, RepositoryException {
		return new MockSession(this);
	}

	public Session login() throws LoginException, RepositoryException {
		return login("dummy");
	}

}
