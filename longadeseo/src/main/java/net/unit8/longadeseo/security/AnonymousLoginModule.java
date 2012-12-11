package net.unit8.longadeseo.security;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.jackrabbit.core.security.SystemPrincipal;
import org.apache.jackrabbit.core.security.principal.AdminPrincipal;

public class AnonymousLoginModule implements LoginModule {
	private Subject subject;
	public boolean abort() throws LoginException {
		return true;
	}

	public boolean commit() throws LoginException {
		return true;
	}

	public void initialize(Subject subject, CallbackHandler cb,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
	}

	public boolean login() throws LoginException {
		subject.getPrincipals().add(new SystemPrincipal());
		subject.getPrincipals().add(new AdminPrincipal("admin"));
		return true;
	}

	public boolean logout() throws LoginException {
		return true;
	}

}
