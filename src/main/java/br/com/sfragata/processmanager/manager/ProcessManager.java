/**
 * 
 */
package br.com.sfragata.processmanager.manager;

import java.util.List;

import br.com.sfragata.processmanager.to.ProcessTO;

/**
 * @author Silvio Fragata da Silva
 * 
 */
public interface ProcessManager {

	public List<ProcessTO> getProcesses(String pattern)
			throws ProcessManagerException;

	public void killProcess(String pid) throws ProcessManagerException;
}
