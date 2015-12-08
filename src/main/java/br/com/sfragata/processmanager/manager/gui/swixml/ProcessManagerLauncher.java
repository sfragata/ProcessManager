/**
 * 
 */
package br.com.sfragata.processmanager.manager.gui.swixml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Silvio Fragata da Silva
 */
public class ProcessManagerLauncher {

	private static final Log logger = LogFactory
			.getLog(ProcessManagerLauncher.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {
				try {
					ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
							"applicationContext.xml");
					applicationContext.getBean("processManagerSwixml");
				} catch (Exception e) {
					logger.error(e);
				}
			}
		});
		t.setName("ProcessManager");
		t.start();
	}
}
