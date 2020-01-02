package com.github.sfragata.processmanager.manager;

import com.github.sfragata.processmanager.to.ProcessTO;

import java.util.List;

/**
 * @author Silvio Fragata da Silva
 * 
 */
public interface ProcessManager {

	List<ProcessTO> getProcesses(String pattern)
			throws ProcessManagerException;

	void killProcess(String pid) throws ProcessManagerException;
}
