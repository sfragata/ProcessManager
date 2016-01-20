/**
 * 
 */
package com.github.sfragata.processmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.swixml.SwingEngine;

/**
 * Spring configuration (to replace the xml)
 * 
 * @author Silvio Fragata Silva
 * 
 */
@Configuration
@ComponentScan(basePackages = { "com.github.sfragata", "br.com.sfragata.log4jmanager" })
@EnableMBeanExport
@EnableAspectJAutoProxy
public class ProcessManagerConfig {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("locale/messages");
		return source;
	}

	@Bean
	public SwingEngine swixml() {
		SwingEngine swixml = new SwingEngine();
		return swixml;
	}

}
