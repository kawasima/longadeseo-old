package net.unit8.longadeseo;

import java.io.File;

import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

public class LongadeseoServer {
	public static void main(String[] args) throws Exception {
		String appBase = new File("src/main/webapp").getAbsolutePath();
		Integer port = 8091;
		String contextPath = "/longadeseo";

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);

		tomcat.setBaseDir(".");
		tomcat.getHost().setAppBase(appBase);

		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);

		tomcat.addWebapp(contextPath, appBase);
		tomcat.start();
		tomcat.getServer().await();
	}
}
