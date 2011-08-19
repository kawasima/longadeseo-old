package net.unit8.longadeseo.security;

import java.security.Principal;

import javax.jcr.Session;

import org.apache.jackrabbit.api.security.principal.PrincipalIterator;
import org.apache.jackrabbit.core.security.principal.AbstractPrincipalProvider;

public class LongadeseoPrincipalProvider extends AbstractPrincipalProvider {

	public PrincipalIterator findPrincipals(String simpleFilter) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PrincipalIterator findPrincipals(String simpleFilter, int searchType) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PrincipalIterator getPrincipals(int searchType) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PrincipalIterator getGroupMembership(Principal principal) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean canReadPrincipal(Session session, Principal principalToRead) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected Principal providePrincipal(String principalName) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
