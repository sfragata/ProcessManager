package com.github.sfragata.processmanager.manager;

import com.github.sfragata.processmanager.config.ProcessManagerConfig;
import com.github.sfragata.processmanager.to.ProcessTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author sfragata
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProcessManagerConfig.class })
public class ProcessManagerTest {

	@Autowired
	private ProcessManagerFactory processManagerFactory;

	@Test
	public void shouldReturnJava() {
		try {
			List<ProcessTO> processes = processManagerFactory.getProcessManager().getProcesses("java");
			assertNotNull(processes);
			assertFalse(processes.isEmpty());
		} catch (ProcessManagerException ex) {
			Logger.getLogger(ProcessManagerTest.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
/*
	@Test
	public void shouldReturn() {
		try {
			String[] args = { "/bin/sh", "-c", "ps -ef | grep java" };
			Process proc = Runtime.getRuntime().exec(args);

			try (BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
				proc.waitFor(5, TimeUnit.SECONDS);
				while (read.ready()) {
					System.out.println(read.readLine());
				}
			} catch (InterruptedException e) {
				Logger.getLogger(ProcessManagerTest.class.getName()).log(Level.SEVERE, null, e);
			}
		} catch (IOException e) {
			Logger.getLogger(ProcessManagerTest.class.getName()).log(Level.SEVERE, null, e);
		}

	}
*/
}
