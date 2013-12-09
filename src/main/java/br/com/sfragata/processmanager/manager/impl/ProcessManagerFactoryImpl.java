/**
 * 
 */
package br.com.sfragata.processmanager.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.sfragata.processmanager.manager.ProcessManager;
import br.com.sfragata.processmanager.manager.ProcessManagerFactory;

/**
 * @author sfragata OS Name: Windows XP OS Name: Mac OS X ﻿OS Name: Linux
 * 
 */
@Component
public class ProcessManagerFactoryImpl implements ProcessManagerFactory {

	private static final Log logger = LogFactory
			.getLog(ProcessManagerFactoryImpl.class);

	@Autowired
	@Qualifier("Unix")
	private ProcessManager unix;

	@Autowired
	@Qualifier("SysinternalsPsListWindows")
	private ProcessManager sysinternals;

	public ProcessManager getProcessManager() {
		String osName = System.getProperty("os.name");
		logger.info("OS Name: " + osName);
		if (osName.indexOf("Windows") == -1) {
			return unix;
		} else {
			return sysinternals;
		}
	}
}
