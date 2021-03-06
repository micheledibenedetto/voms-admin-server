/**
 * Copyright (c) Members of the EGEE Collaboration. 2006-2009.
 * See http://www.eu-egee.org/partners/ for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors:
 * 	Andrea Ceccanti (INFN)
 */
package org.glite.security.voms.admin.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.glite.security.voms.admin.configuration.VOMSConfigurationException;
import org.glite.security.voms.admin.error.VOMSFatalException;
import org.glite.security.voms.admin.util.ExceptionUtils;
import org.glite.security.voms.admin.util.SysconfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;


public class SiblingsContextListener implements ServletContextListener {

	private static final Logger log = LoggerFactory
			.getLogger(SiblingsContextListener.class);

	public void contextDestroyed(ServletContextEvent ev) {
		
		
	}

	public void contextInitialized(ServletContextEvent ev) {

		try {
		
			findConfiguredVOs(ev);
			loadVersionProperties(ev);
			configureLogging(ev);
			log.info("Siblings context initialized.");
		
		} catch (IOException e) {
			
			throw new RuntimeException("Error configuring loggin system!", e);
		}
		

	}

	
	protected void configureLogging(ServletContextEvent ev) throws IOException{
		
		InputStream is = ev.getServletContext().getResourceAsStream("/WEB-INF/classes/logback.siblings.xml");
		
		if (is == null){
			throw new VOMSConfigurationException("Error configuring logging! Logback configuration file not found in context!");
		}
		
		try{
			LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory(); 
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);

			lc.reset();
			configurator.doConfigure(is);
		
		}catch(JoranException e){
			
			ExceptionUtils.logError(log, e);
			throw new VOMSConfigurationException("Error configuring logging system: "+e.getMessage(), e);
			
		}
		
	
	}
	
	public void loadVersionProperties(ServletContextEvent ev) throws IOException{
		
		Properties versionProps = new Properties();
		InputStream is = ev.getServletContext()
		.getResourceAsStream("/WEB-INF/classes/version.properties");
		
		versionProps.load(is);
		
		ev.getServletContext().setAttribute("version", versionProps.get("voms-admin.server.version"));
		
		
	}
	
	public void findConfiguredVOs(ServletContextEvent ev) {

		
		Properties sysconfigProps = SysconfigUtil.loadSysconfig();
		
		String configDirPath = sysconfigProps.getProperty(SysconfigUtil.SYSCONFIG_CONF_DIR);
		
		if (configDirPath == null){
			
			throw new VOMSFatalException(SysconfigUtil.SYSCONFIG_CONF_DIR +" undefined in VOMS sysconfig!");
			
		}

		
		List voList = new ArrayList();

		File configDir = new File(configDirPath);

		if (!configDir.exists())
			throw new RuntimeException(
					"Voms configuration directory does not exist");

		File[] filez = configDir.listFiles();

		if (filez != null)
			for (int i = 0; i < filez.length; i++) {

				if (filez[i].isDirectory()) {
					log.debug("Found vo: " + filez[i].getName());
					voList.add(filez[i].getName());
				}

			}
		
		ev.getServletContext().setAttribute("configuredVOs", voList);
		
	}
}


