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
package net.unit8.longadeseo.plugin.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import net.unit8.longadeseo.plugin.Plugin;
import net.unit8.longadeseo.plugin.PluginExecutionException;
import net.unit8.longadeseo.plugin.PluginOption;

import org.apache.jackrabbit.webdav.DavResource;

public class ClamavPlugin extends Plugin {
	private static final long serialVersionUID = -5232242387158282981L;
	private static final int DEFAULT_CHUNK_SIZE = 2048;
	private static final byte[] INSTREAM = "zINSTREAM\0".getBytes();
	private static final byte[] PING = "zPING\0".getBytes();
	private static final byte[] STATS = "nSTATS\n".getBytes();

	@PluginOption(label = "Clamav Host")
	protected String host;

	@PluginOption(label = "Clamav Port")
	protected int port;

	@Override
	public void init() {
	}

	@Override
	public void beforeService(DavResource resource) {
	}

	@Override
	public void afterService(DavResource resource, InputStream in) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(host, port));
		} catch (IOException e) {
			logger.error("could not connect to clamd server", e);
			throw new PluginExecutionException(e);
		}

		try {
			socket.setSoTimeout(5000);
		} catch (SocketException e) {
			logger.error("Could not set socket timeout to " + 5000 + "ms", e);
			throw new PluginExecutionException(e);
		}

		DataOutputStream dos = null;
		String response = "";
		try { // finally to close resources
			try {
				dos = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				logger.error("could not open socket OutputStream", e);
				throw new PluginExecutionException(e);
			}

			try {
				dos.write(INSTREAM);
			} catch (IOException e) {
				logger.debug("error writing INSTREAM command", e);
				throw new PluginExecutionException(e);
			}

			int read = DEFAULT_CHUNK_SIZE;
			byte[] buffer = new byte[DEFAULT_CHUNK_SIZE];
			while (read == DEFAULT_CHUNK_SIZE) {
				try {
					read = in.read(buffer);
				} catch (IOException e) {
					logger.debug("error reading from InputStream", e);
					throw new PluginExecutionException(e);
				}

				// we may exceed the clamd size limit, so we don't immediately
				// return
				// if we get an error here.
				try {
					dos.writeInt(read);
					dos.write(buffer, 0, read);
				} catch (IOException e) {
					logger.debug("error writing data to socket", e);
					break;
				}
			}

			try {
				dos.writeInt(0);
				dos.flush();
			} catch (IOException e) {
				logger.debug("error writing zero-length chunk to socket", e);
			}

			try {
				read = socket.getInputStream().read(buffer);
			} catch (IOException e) {
				logger.debug("error reading result from socket", e);
				read = 0;
			}

			if (read > 0)
				response = new String(buffer, 0, read);

		} finally {
			if (dos != null)
				try {
					dos.close();
				} catch (IOException e) {
					logger.debug("exception closing DOS", e);
				}
			try {
				socket.close();
			} catch (IOException e) {
				logger.debug("exception closing socket", e);
			}
		}
		// TODO ウイルスが見つかったときの隔離処理
	}

}
