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
package net.unit8.longadeseo.interceptor;

import java.lang.reflect.Field;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;

/**
 * This class inject an JcrSession to service classes
 *
 * @author kawasima
 *
 */
public class JcrSessionInterceptor implements MethodInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(JcrSessionInterceptor.class);

	private Provider<Repository> repositoryProvider;

	public JcrSessionInterceptor(Provider<Repository> repositoryProvider) {
		this.repositoryProvider = repositoryProvider;
	}
	public Object invoke(MethodInvocation invocation) throws Throwable {
		logger.debug(invocation.getThis().toString());
		Repository repository = repositoryProvider.get();
		Field sessionField = null;
		for(Field field :invocation.getThis().getClass().getFields()) {
			logger.debug(field.toString());
			if(field.getType().equals(Session.class)) {
				sessionField = field;
			}
		}
		Session session = null;
		if(sessionField != null) {
			logger.debug("session field injection");
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()), "longadeseo");
			sessionField.set(invocation.getThis(), session);
		}

		Object ret;
		try {
			ret = invocation.proceed();
			if (session != null && session.isLive())
				session.save();
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
		return ret;
	}

}
