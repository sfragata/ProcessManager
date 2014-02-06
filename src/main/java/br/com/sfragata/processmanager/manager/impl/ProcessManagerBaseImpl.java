/**
 * 
 */
package br.com.sfragata.processmanager.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.sfragata.processmanager.manager.ProcessManagerException;
import br.com.sfragata.processmanager.to.ProcessTO;
import br.com.sfragata.processmanager.util.MethodInvoker;

/**
 * @author Silvio Fragata da Silva
 * 
 */
@Component
public abstract class ProcessManagerBaseImpl {
	@Autowired
	private MethodInvoker methodInvoker;

	protected abstract String[] getListCommand(String pattern);

	protected abstract String getKillCommand(String process);

	protected abstract List<ProcessTO> stream2List(BufferedReader read,
			String pattern) throws IOException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException;

	protected abstract String[] getSetProcessMethods();

	public List<ProcessTO> getProcesses(final String pattern)
			throws ProcessManagerException {
		try {
			Process proc = Runtime.getRuntime().exec(getListCommand(pattern));
			BufferedReader read = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			proc.waitFor();
			return stream2List(read, pattern);
		} catch (IOException e) {
			throw new ProcessManagerException("Error", e);
		} catch (InterruptedException e) {
			throw new ProcessManagerException("Error", e);
		} catch (NoSuchMethodException e) {
			throw new ProcessManagerException("Error", e);
		} catch (InvocationTargetException e) {
			throw new ProcessManagerException("Error", e);
		} catch (IllegalAccessException e) {
			throw new ProcessManagerException("Error", e);
		}
	}

	public void killProcess(String pid) throws ProcessManagerException {
		try {
			Process proc = Runtime.getRuntime().exec(getKillCommand(pid));
			BufferedReader read = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			int status = proc.waitFor();
			if (status != 0) {
				String stack = readStream(read);
				if (!StringUtils.hasLength(stack)) {
					stack = String.format("PID: %s", pid);
				}
				throw new ProcessManagerException(stack);
			}
		} catch (IOException e) {
			throw new ProcessManagerException(String.format(
					"Error killing process %s", pid), e);
		} catch (InterruptedException e) {
			throw new ProcessManagerException(String.format(
					"Error killing process %s", pid), e);
		}
	}

	protected String readStream(BufferedReader reader) throws IOException {
		try {
			StringBuilder buffer = new StringBuilder();
			while (reader.ready()) {
				String line = reader.readLine();
				if (line != null && !line.trim().equals("")) {
					buffer.append(line).append("\n");
				}
			}
			return buffer.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	protected ProcessTO createProcessManagerTO(final String processLine,
			String pattern) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		ProcessTO processManagerTO = new ProcessTO();
		if (StringUtils.hasLength(processLine)) {
			String[] setMethods = getSetProcessMethods();
			String line = processLine.trim();
			for (int i = 0; i < setMethods.length; i++) {
				String column = "";
				if (i == (setMethods.length - 1)) {
					column = line;
				} else {
					column = line.substring(0, line.indexOf(" "));
					line = line.substring(line.indexOf(" ")).trim();
				}
				methodInvoker.invokeSetMethod(processManagerTO, setMethods[i],
						column, String.class);
			}
		}
		return processManagerTO;
	}

	protected String createCommand(String[] listCommand) {
		StringBuilder sb = new StringBuilder();
		for (String string : listCommand) {
			sb.append(string).append(" ");
		}
		return sb.toString().trim();
	}

}
