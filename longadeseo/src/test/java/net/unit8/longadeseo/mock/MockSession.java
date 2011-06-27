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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;

import javax.jcr.AccessDeniedException;
import javax.jcr.Credentials;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.retention.RetentionManager;
import javax.jcr.security.AccessControlManager;
import javax.jcr.version.VersionException;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class MockSession implements Session {
	private Repository repository;

	public MockSession(Repository repository) {
		this.repository = repository;
	}
	public Repository getRepository() {
		return repository;
	}

	public String getUserID() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String[] getAttributeNames() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getAttribute(String name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Workspace getWorkspace() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node getRootNode() throws RepositoryException {

		return null;
	}

	public Session impersonate(Credentials credentials) throws LoginException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node getNodeByUUID(String uuid) throws ItemNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node getNodeByIdentifier(String id) throws ItemNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Item getItem(String absPath) throws PathNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node getNode(String absPath) throws PathNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Property getProperty(String absPath) throws PathNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean itemExists(String absPath) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean nodeExists(String absPath) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean propertyExists(String absPath) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void move(String srcAbsPath, String destAbsPath)
			throws ItemExistsException, PathNotFoundException,
			VersionException, ConstraintViolationException, LockException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void removeItem(String absPath) throws VersionException,
			LockException, ConstraintViolationException, AccessDeniedException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void save() throws AccessDeniedException, ItemExistsException,
			ReferentialIntegrityException, ConstraintViolationException,
			InvalidItemStateException, VersionException, LockException,
			NoSuchNodeTypeException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void refresh(boolean keepChanges) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean hasPendingChanges() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public ValueFactory getValueFactory()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean hasPermission(String absPath, String actions)
			throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void checkPermission(String absPath, String actions)
			throws AccessControlException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean hasCapability(String methodName, Object target,
			Object[] arguments) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public ContentHandler getImportContentHandler(String parentAbsPath,
			int uuidBehavior) throws PathNotFoundException,
			ConstraintViolationException, VersionException, LockException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void importXML(String parentAbsPath, InputStream in, int uuidBehavior)
			throws IOException, PathNotFoundException, ItemExistsException,
			ConstraintViolationException, VersionException,
			InvalidSerializedDataException, LockException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void exportSystemView(String absPath, ContentHandler contentHandler,
			boolean skipBinary, boolean noRecurse)
			throws PathNotFoundException, SAXException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void exportSystemView(String absPath, OutputStream out,
			boolean skipBinary, boolean noRecurse) throws IOException,
			PathNotFoundException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void exportDocumentView(String absPath,
			ContentHandler contentHandler, boolean skipBinary, boolean noRecurse)
			throws PathNotFoundException, SAXException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void exportDocumentView(String absPath, OutputStream out,
			boolean skipBinary, boolean noRecurse) throws IOException,
			PathNotFoundException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void setNamespacePrefix(String prefix, String uri)
			throws NamespaceException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public String[] getNamespacePrefixes() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getNamespaceURI(String prefix) throws NamespaceException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getNamespacePrefix(String uri) throws NamespaceException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void logout() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean isLive() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void addLockToken(String lt) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public String[] getLockTokens() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void removeLockToken(String lt) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public AccessControlManager getAccessControlManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public RetentionManager getRetentionManager()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
