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
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import net.arnx.jsonic.JSON;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifyClient {
	private static final Logger logger = LoggerFactory.getLogger(NotifyClient.class);
	private WebSocketClientFactory webSocketClientFactory;
	private Connection connection;

	public NotifyClient() {
		URI uri;
		try {
			uri = new URI("ws://localhost:8091/longadeseo/webdav-message?username=longadeseo-notifier");
			webSocketClientFactory = new WebSocketClientFactory();
			webSocketClientFactory.start();

			WebSocketClient client = webSocketClientFactory.newWebSocketClient();
			Future<Connection> futureConnection =
				    client.open(uri, new WebSocket.OnTextMessage() {

						public void onOpen(Connection connection) { /* Do nothing */ }

						public void onClose(int closeCode, String message) { /* Do nothing*/ }

						public void onMessage(String data) { /* Do nothing */ }
				    });
			connection = futureConnection.get();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void send(String username, String message) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", username);
		data.put("message", message);
		try {
			connection.sendMessage(JSON.encode(data));
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	public void dispose() {
		connection.close();
		try {
			webSocketClientFactory.stop();
		} catch (Exception e) {
			logger.error("Failure to stop WebSocketClientFactory", e);
		}
	}
}
