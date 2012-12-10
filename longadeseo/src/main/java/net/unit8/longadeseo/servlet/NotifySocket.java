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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import net.arnx.jsonic.JSON;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifySocket extends MessageInbound {
	private static final Logger logger = LoggerFactory.getLogger(NotifySocket.class);
	private WsOutbound outbound;
	private static Set<NotifySocket> sockets = new CopyOnWriteArraySet<NotifySocket>();
	private String username;

	public NotifySocket(String username) {
		this.username = username;
	}

	@Override
	public void onOpen(WsOutbound outbound) {
		logger.debug("connect:" + username);
		this.outbound = outbound;
		sockets.add(this);
	}

	@Override
	public void onClose(int status) {
		logger.debug("close:" + username);
		sockets.remove(this);
	}

	@Override
	public void onTextMessage(CharBuffer cb) throws IOException {
		logger.debug("message:" + cb);
		Object obj = JSON.decode(cb.toString());
		if(obj instanceof Map) {
			Map<String, String> msgObj = (Map<String, String>)obj;
			String targetUsername = msgObj.get("username");
			for(NotifySocket socket : sockets) {
				if(!StringUtils.equals(socket.username, targetUsername))
					continue;
				socket.outbound.writeTextMessage(cb);
			}
		}
	}

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
	}
}
