package net.unit8.longadeseo.security;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class AnonymousLoginModule implements LoginModule {

	public boolean abort() throws LoginException {
		return true;
	}

	public boolean commit() throws LoginException {
		return true;
	}

	public void initialize(Subject subject, CallbackHandler cb,
			Map<String, ?> arg2, Map<String, ?> arg3) {
	}

	public boolean login() throws LoginException {
		return true;
	}

	public boolean logout() throws LoginException {
		return true;
	}

}
