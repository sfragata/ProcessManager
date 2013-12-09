package br.com.sfragata.processmanager.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.sfragata.processmanager.to.ProcessTO;

/**
 * 
 * @author sfragata
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ProcessManagerTest {

	@Autowired
	private ProcessManagerFactory processManagerFactory;

	@Test
	public void shouldReturnJava() {
		try {
			List<ProcessTO> processes = processManagerFactory
					.getProcessManager().getProcesses("java");
			// assertEquals(1, processes.size());
			System.out.println(processes);
		} catch (ProcessManagerException ex) {
			Logger.getLogger(ProcessManagerTest.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@Test
	public void shouldReturn() {
		try {
			// String args[] = new String[] { "ps", "-ef", "|", "grep", "java"
			// };
			String[] args = { "/bin/sh", "-c", "ps -ef | grep java" };
			Process proc = Runtime.getRuntime().exec(args);
			BufferedReader read = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				Logger.getLogger(ProcessManagerTest.class.getName()).log(
						Level.SEVERE, null, e);
			}
			while (read.ready()) {
				System.out.println(read.readLine());
			}
		} catch (IOException e) {
			Logger.getLogger(ProcessManagerTest.class.getName()).log(
					Level.SEVERE, null, e);
		}

	}

}
