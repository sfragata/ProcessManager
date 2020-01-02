package com.github.sfragata.processmanager.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.sfragata.processmanager.manager.ProcessManager;
import com.github.sfragata.processmanager.manager.ProcessManagerFactory;

/**
 * @author sfragata OS Name: Windows XP OS Name: Mac OS X ﻿OS Name: Linux
 * 
 */
@Component
public class ProcessManagerFactoryImpl implements ProcessManagerFactory {

	private static final Logger logger = LoggerFactory.getLogger(ProcessManagerFactoryImpl.class);

	@Autowired
	@Qualifier("Unix")
	private ProcessManager unix;

	@Autowired
	@Qualifier("SysinternalsPsListWindows")
	private ProcessManager sysinternals;

	public ProcessManager getProcessManager() {
		String osName = System.getProperty("os.name");
		logger.info("OS Name: {}", osName);
		if (!osName.contains("Windows")) {
			return unix;
		} else {
			return sysinternals;
		}
	}
}
