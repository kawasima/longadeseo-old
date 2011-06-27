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

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.jcr.AccessDeniedException;
import javax.jcr.Binary;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidLifecycleTransitionException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.ActivityViolationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;

import org.apache.jackrabbit.commons.AbstractNode;

public class MockNode extends AbstractNode {

	public Node addNode(String relPath) throws ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node addNode(String relPath, String primaryNodeTypeName)
			throws ItemExistsException, PathNotFoundException,
			NoSuchNodeTypeException, LockException, VersionException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void orderBefore(String srcChildRelPath, String destChildRelPath)
			throws UnsupportedRepositoryOperationException, VersionException,
			ConstraintViolationException, ItemNotFoundException, LockException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public Property setProperty(String name, Value value)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Property setProperty(String name, Value[] values)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Property setProperty(String name, Binary value)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Property setProperty(String name, BigDecimal value)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node getNode(String relPath) throws PathNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public NodeIterator getNodes() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public NodeIterator getNodes(String namePattern) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public NodeIterator getNodes(String[] nameGlobs) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PropertyIterator getProperties() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PropertyIterator getProperties(String namePattern)
			throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PropertyIterator getProperties(String[] nameGlobs)
			throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Item getPrimaryItem() throws ItemNotFoundException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getIdentifier() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public int getIndex() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public PropertyIterator getReferences() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PropertyIterator getReferences(String name)
			throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PropertyIterator getWeakReferences() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PropertyIterator getWeakReferences(String name)
			throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void setPrimaryType(String nodeTypeName)
			throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void addMixin(String mixinName) throws NoSuchNodeTypeException,
			VersionException, ConstraintViolationException, LockException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void removeMixin(String mixinName) throws NoSuchNodeTypeException,
			VersionException, ConstraintViolationException, LockException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean canAddMixin(String mixinName)
			throws NoSuchNodeTypeException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public NodeDefinition getDefinition() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Version checkin() throws VersionException,
			UnsupportedRepositoryOperationException, InvalidItemStateException,
			LockException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void checkout() throws UnsupportedRepositoryOperationException,
			LockException, ActivityViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void doneMerge(Version version) throws VersionException,
			InvalidItemStateException, UnsupportedRepositoryOperationException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void cancelMerge(Version version) throws VersionException,
			InvalidItemStateException, UnsupportedRepositoryOperationException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void update(String srcWorkspace) throws NoSuchWorkspaceException,
			AccessDeniedException, LockException, InvalidItemStateException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public NodeIterator merge(String srcWorkspace, boolean bestEffort)
			throws NoSuchWorkspaceException, AccessDeniedException,
			MergeException, LockException, InvalidItemStateException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getCorrespondingNodePath(String workspaceName)
			throws ItemNotFoundException, NoSuchWorkspaceException,
			AccessDeniedException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public NodeIterator getSharedSet() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void removeSharedSet() throws VersionException, LockException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void removeShare() throws VersionException, LockException,
			ConstraintViolationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void restore(Version version, String relPath, boolean removeExisting)
			throws PathNotFoundException, ItemExistsException,
			VersionException, ConstraintViolationException,
			UnsupportedRepositoryOperationException, LockException,
			InvalidItemStateException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public Version getBaseVersion()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Lock lock(boolean isDeep, boolean isSessionScoped)
			throws UnsupportedRepositoryOperationException, LockException,
			AccessDeniedException, InvalidItemStateException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Lock getLock() throws UnsupportedRepositoryOperationException,
			LockException, AccessDeniedException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void unlock() throws UnsupportedRepositoryOperationException,
			LockException, AccessDeniedException, InvalidItemStateException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void followLifecycleTransition(String transition)
			throws UnsupportedRepositoryOperationException,
			InvalidLifecycleTransitionException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public String[] getAllowedLifecycleTransistions()
			throws UnsupportedRepositoryOperationException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getName() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Node getParent() throws ItemNotFoundException,
			AccessDeniedException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Session getSession() throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean isNew() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean isModified() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean isSame(Item otherItem) throws RepositoryException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void save() throws AccessDeniedException, ItemExistsException,
			ConstraintViolationException, InvalidItemStateException,
			ReferentialIntegrityException, VersionException, LockException,
			NoSuchNodeTypeException, RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void refresh(boolean keepChanges) throws InvalidItemStateException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void remove() throws VersionException, LockException,
			ConstraintViolationException, AccessDeniedException,
			RepositoryException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
