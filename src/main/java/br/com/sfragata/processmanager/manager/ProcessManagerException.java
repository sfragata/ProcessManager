/**
 * 
 */
package br.com.sfragata.processmanager.manager;

/**
 * @author Silvio Fragata da Silva
 * 
 */
public class ProcessManagerException extends Exception {

	/**
     *
     */
	private static final long serialVersionUID = 5709179518913839634L;

	/**
     *
     */
	@SuppressWarnings("unused")
	private ProcessManagerException() {
	}

	/**
	 * @param arg0
	 */
	public ProcessManagerException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ProcessManagerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ProcessManagerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
