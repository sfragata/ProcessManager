/**
 * 
 */
package com.github.sfragata.processmanager.manager.gui.swixml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.sfragata.processmanager.config.ProcessManagerConfig;

/**
 * @author Silvio Fragata da Silva
 */
public class ProcessManagerLauncher {

	private static final Logger logger = LoggerFactory.getLogger(ProcessManagerLauncher.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {

			@SuppressWarnings("resource")
			public void run() {
				try {
					if (logger.isInfoEnabled()) {
						logger.info("Starting application...");
					}
					new AnnotationConfigApplicationContext(ProcessManagerConfig.class);
				} catch (Exception e) {
					logger.error("Error", e);
				}
			}
		});
		t.setName("ProcessManagerLauncher");
		t.start();
	}
}
