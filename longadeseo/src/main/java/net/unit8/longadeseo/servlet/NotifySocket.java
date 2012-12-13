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
package net.unit8.longadeseo.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import net.arnx.jsonic.JSON;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifySocket implements WebSocket, WebSocket.OnTextMessage {
	private static final Logger logger = LoggerFactory.getLogger(NotifySocket.class);
	private Connection connection;
	private static Set<NotifySocket> sockets = new CopyOnWriteArraySet<NotifySocket>();
	private String username;

	public NotifySocket(String username) {
		this.username = username;
	}

	public void onOpen(Connection connection) {
		logger.debug("connect:" + username);
		this.connection = connection;
		sockets.add(this);
	}

	public void onClose(int status, String message) {
		logger.debug("close:" + username);
		sockets.remove(this);
	}

	public void onMessage(String msg) {
		logger.debug("message:" + msg);
		Object obj = JSON.decode(msg.toString());
		if(obj instanceof Map) {
			Map<String, String> msgObj = (Map<String, String>)obj;
			String targetUsername = msgObj.get("username");
			for(NotifySocket socket : sockets) {
				if(!StringUtils.equals(socket.username, targetUsername))
					continue;
				try {
					socket.connection.sendMessage(msg);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
