package org.glite.security.voms.admin.server.ssl;

import java.security.Security;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glite.security.trustmanager.ContextWrapper;
import org.glite.security.util.CaseInsensitiveProperties;
import org.glite.security.voms.admin.error.VOMSException;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.resource.Resource;
import org.mortbay.thread.concurrent.ThreadPool;
import org.mortbay.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VOMSServer {

	static final Logger log = LoggerFactory.getLogger(VOMSServer.class);

	static final int MAX_REQUEST_QUEUE_SIZE = 50;
	static final int MAX_CONNECTIONS = 50;
	static final int PORT = 8443;
	static final String HOSTNAME = "wilco.cnaf.infn.it";

	static final String CERT = "/etc/grid-security/hostcert.pem";
	static final String KEY = "/etc/grid-security/hostkey.pem";
	static final String TRUST_DIR = "/etc/grid-security/certificates";
	
	static final String VOMS_ADMIN_WAR = "/Users/andreaceccanti/git/voms-admin-server/target/glite-security-voms-admin.war";
	
	private Server server;

	private Properties buildTrustmanagerConfiguration() {

		Properties tmProps = new Properties();

		tmProps.setProperty("sslCertFile", CERT);
		tmProps.setProperty("sslKey", KEY);
		tmProps.setProperty("crlEnabled", "true");
		tmProps.setProperty("crlUpdateInterval", "30m");

		tmProps.setProperty("trustStoreDir", TRUST_DIR);

		return tmProps;

	}

	private Connector configureTMConnector(String host, int port) {

		CaseInsensitiveProperties props = new CaseInsensitiveProperties(
				buildTrustmanagerConfiguration());

		try {

			ContextWrapper context = new ContextWrapper(props, false);

			JettySslSelectChannelConnector connector = new JettySslSelectChannelConnector(
					context.getKeyManager(), context.m_trustmanager);

			connector.setPort(port);
			connector.setHost(host);

			connector.setWantClientAuth(true);
			connector.setNeedClientAuth(true);

			log.info("VOMS will listen on {}:{}", new Object[] { host, port });

			return connector;

		} catch (Exception e) {

			log.error("Error initializing trustmanager connector: "
					+ e.getMessage());
			if (log.isDebugEnabled())
				log.error(
						"Error initializing trustmanager connector: "
								+ e.getMessage(), e);

			throw new VOMSException(e);
		}

	}

	public void configureJettyServer() {

		try {

			server = new Server();

			server.setSendServerVersion(false);
			server.setSendDateHeader(false);

			BlockingQueue<Runnable> requestQueue;

			requestQueue = new ArrayBlockingQueue<Runnable>(
					MAX_REQUEST_QUEUE_SIZE);

			server.setThreadPool(new ThreadPool(5, MAX_CONNECTIONS, 60,
					TimeUnit.SECONDS, requestQueue));

			server.setConnectors(new Connector[] { configureTMConnector(
					HOSTNAME, PORT) });

			
			WebAppContext webappContext = new WebAppContext();

			webappContext.setContextPath("/voms/mysql");
			webappContext.setWar(VOMS_ADMIN_WAR);
			
			HashMap<String, String> params = new HashMap<String, String>();
			
			params.put("VO_NAME", "mysql");
			webappContext.setInitParams(params);
			
			webappContext.setAttribute("VO_NAME", "mysql");
			webappContext.setParentLoaderPriority(true);
			
			HandlerCollection handlers = new HandlerCollection();
			handlers.setHandlers(new Handler[] { webappContext,
					new DefaultHandler() });
			
			server.setHandler(handlers);

		} catch (Exception e) {

			log.error("Error creating VOMS context", e);
			System.exit(-1);

		}

	}

	/**
	 * Create a WebAppContext for the webapp being hot deployed, then apply the
	 * xml config file to it to configure it.
	 * 
	 * @param filename
	 *            the config file found in the hot deploy directory
	 * @return
	 * @throws Exception
	 */
	private ContextHandler createContext(String filename) throws Exception {
		
		Resource resource = Resource.newResource(filename);
		if (!resource.exists())
			return null;

		XmlConfiguration xmlConfiguration = new XmlConfiguration(
				resource.getURL());
		
		ContextHandler context = (ContextHandler) xmlConfiguration.configure();
		return context;
	}

	
	public VOMSServer(String[] args) {
		
		Security.addProvider(new BouncyCastleProvider());
		
		configureJettyServer();
		
		try {
			server.start();
			checkStatus();
			server.join();
			
		} catch (Throwable e) {
			log.error("Error starting VOMS server: {}", e.getClass().getName(), e);
			System.exit(-1);
		}
		
	}
	private void checkStatus() throws Throwable {
		for (Handler h: server.getHandlers()){
			
			if (h instanceof WebAppContext){
				
				WebAppContext c = (WebAppContext)h;
				
				if (c.getUnavailableException() != null)
					throw c.getUnavailableException();
				
					
			}
		}
	}

	public static void main(String[] args) {

		new VOMSServer(args);
		
	}

}
