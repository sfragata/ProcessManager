/**
 * 
 */
package com.github.sfragata.processmanager.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Spring configuration (to replace the xml)
 * 
 * @author Silvio Fragata Silva
 * 
 */
@Configuration
@ComponentScan(basePackages = { "com.github.sfragata.processmanager.apo", "com.github.sfragata.processmanager.manager"
		,"com.github.sfragata.processmanager.util", "br.com.sfragata.log4jmanager" })
@EnableMBeanExport
@EnableAspectJAutoProxy
public class ProcessManagerConfig {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("locale/messages");
		return source;
	}


}
