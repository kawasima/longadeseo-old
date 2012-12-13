package net.unit8.longadeseo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class LongadeseoServer {
	public static void main(String[] args) throws Exception {
		Integer port = 8091;
		String contextPath = "/longadeseo";

		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		server.addConnector(connector);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(contextPath);
		webapp.setResourceBase("src/main/webapp");
		webapp.setParentLoaderPriority(true);

		server.setHandler(webapp);

		server.start();
		server.join();
	}
}
