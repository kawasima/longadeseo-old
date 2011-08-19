package net.unit8.longadeseo.service;

import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.security.AccessControlList;
import javax.jcr.security.AccessControlManager;
import javax.jcr.security.AccessControlPolicy;
import javax.jcr.security.AccessControlPolicyIterator;
import javax.jcr.security.Privilege;

import net.unit8.longadeseo.dto.SearchResultDto;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class DirectoryService implements Serializable{
	private static final long serialVersionUID = -4335289276063646236L;
	private static final Logger logger = LoggerFactory.getLogger(DirectoryService.class);

	@Inject
	protected transient Repository repository;

	public List<SearchResultDto> list() {
		Session session = null;
		List<SearchResultDto> directoryList = new ArrayList<SearchResultDto>();
		try {
			session = repository.login(new SimpleCredentials("admin", "admin"
					.toCharArray()));
			Node root = session.getRootNode();
			addPermission(session, root);

			NodeIterator iter = root.getNodes("*");
			while(iter.hasNext()) {
				Node node = iter.nextNode();
				if(!StringUtils.startsWith(node.getPrimaryNodeType().getName(), "nt:"))
					continue;
				addPermission(session, node);
				SearchResultDto dto = new SearchResultDto();
				dto.setPath(node.getPath());
				directoryList.add(dto);
			}
			session.save();
		} catch(RepositoryException e) {
			logger.error("directoryList failure", e);
		} finally {
			if (session != null && session.isLive())
				session.logout();

		}

		return directoryList;
	}
	private void addPermission(Session session, Node node) throws RepositoryException {
		AccessControlManager acm = session.getAccessControlManager();
		AccessControlPolicy[] policies = acm.getPolicies(node.getPath());
		for(AccessControlPolicy acp : policies) {
			Privilege[] privileges = new Privilege[] {
					acm.privilegeFromName(Privilege.JCR_ALL)};
			((AccessControlList)acp).addAccessControlEntry(new PrincipalImpl(session.getUserID()), privileges);
			acm.setPolicy(node.getPath(), acp);
		}
	}


}
