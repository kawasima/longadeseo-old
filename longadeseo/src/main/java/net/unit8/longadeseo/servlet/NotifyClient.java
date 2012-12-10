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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.roderick.weberknecht.WebSocket;
import de.roderick.weberknecht.WebSocketConnection;
import de.roderick.weberknecht.WebSocketEventHandler;
import de.roderick.weberknecht.WebSocketException;
import de.roderick.weberknecht.WebSocketMessage;

public class NotifyClient {
	private static final Logger logger = LoggerFactory.getLogger(NotifyClient.class);
	private WebSocket websocket;

	public NotifyClient() {
		URI uri;
		try {
			uri = new URI("ws://localhost:9090/longadeseo/webdav-message?username=longadeseo-notifier");
			websocket = new WebSocketConnection(uri);
			websocket.setEventHandler(new WebSocketEventHandler() {
				public void onOpen() { /* do nothing */ }

				public void onMessage(WebSocketMessage message) { /* do nothing */ }

				public void onClose() {
					try {
						websocket.connect();
					} catch (WebSocketException e) {
						logger.error("Reconnect failure", e);
					}

				}
			});
			websocket.connect();
		} catch (URISyntaxException e) {
			logger.error("", e);
		} catch (WebSocketException e) {
			logger.error("", e);
		}
	}
	public void send(String username, String message) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", username);
		data.put("message", message);
		try {
			websocket.send(JSON.encode(data));
		} catch (WebSocketException e) {
			e.printStackTrace();

		}
	}

	public void dispose() {
		try {
			websocket.close();
		} catch (WebSocketException e) {
			logger.error("Notify client is disconnected.", e);
		}
	}
}
