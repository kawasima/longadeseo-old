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
package net.unit8.longadeseo.dto;

import java.io.IOException;
import java.util.List;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResource;
import org.apache.jackrabbit.webdav.DavResourceFactory;
import org.apache.jackrabbit.webdav.DavResourceIterator;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.DavSession;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.jackrabbit.webdav.lock.ActiveLock;
import org.apache.jackrabbit.webdav.lock.LockInfo;
import org.apache.jackrabbit.webdav.lock.LockManager;
import org.apache.jackrabbit.webdav.lock.Scope;
import org.apache.jackrabbit.webdav.lock.Type;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.jackrabbit.webdav.property.PropEntry;

/**
 * Mock class of DavResource (jackrabbit).
 * 
 * MockDavResource is used by plugin tests.
 * 
 * @author kawasima
 */
public class MockDavResource implements DavResource {
	private String resourceName;
	public MockDavResource(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getComplianceClass() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getSupportedMethods() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean exists() {
		return false;
	}

	public boolean isCollection() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public String getDisplayName() {
		return resourceName;
	}

	public DavResourceLocator getLocator() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getResourcePath() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getHref() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public long getModificationTime() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public void spool(OutputContext outputContext) throws IOException {
		outputContext.getOutputStream();
	}

	public DavPropertyName[] getPropertyNames() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public DavProperty<?> getProperty(DavPropertyName name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public DavPropertySet getProperties() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void setProperty(DavProperty<?> property) throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void removeProperty(DavPropertyName propertyName)
			throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public MultiStatusResponse alterProperties(
			List<? extends PropEntry> changeList) throws DavException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public DavResource getCollection() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void addMember(DavResource resource, InputContext inputContext)
			throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public DavResourceIterator getMembers() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void removeMember(DavResource member) throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void move(DavResource destination) throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void copy(DavResource destination, boolean shallow)
			throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean isLockable(Type type, Scope scope) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean hasLock(Type type, Scope scope) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public ActiveLock getLock(Type type, Scope scope) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public ActiveLock[] getLocks() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public ActiveLock lock(LockInfo reqLockInfo) throws DavException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public ActiveLock refreshLock(LockInfo reqLockInfo, String lockToken)
			throws DavException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void unlock(String lockToken) throws DavException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void addLockManager(LockManager lockmgr) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public DavResourceFactory getFactory() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public DavSession getSession() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
