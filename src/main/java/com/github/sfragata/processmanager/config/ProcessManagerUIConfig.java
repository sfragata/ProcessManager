/**
 *
 */
package com.github.sfragata.processmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.swixml.SwingEngine;

/**
 * Spring configuration (to replace the xml)
 *
 * @author Silvio Fragata Silva
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.github.sfragata.processmanager.gui.swixml"})
@Import(ProcessManagerConfig.class)
public class ProcessManagerUIConfig {

    @Bean
    public SwingEngine swixml() {
        return new SwingEngine();
    }

}
