package com.github.sfragata.processmanager.gui.swixml;

import com.github.sfragata.processmanager.config.ProcessManagerUIConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Silvio Fragata da Silva
 */
public class ProcessManagerLauncher {

	private static final Logger logger = LoggerFactory.getLogger(ProcessManagerLauncher.class);

	public static void main(String[] args) {
		Thread t = new Thread(() -> {
			try {
				if (logger.isInfoEnabled()) {
					logger.info("Starting application...");
				}
				new AnnotationConfigApplicationContext(ProcessManagerUIConfig.class);
			} catch (Exception e) {
				logger.error("Error", e);
			}
		});
		t.setName("ProcessManagerLauncher");
		t.start();
	}
}
