package com.github.sfragata.processmanager.manager.impl;

import com.github.sfragata.processmanager.manager.ProcessManagerException;
import com.github.sfragata.processmanager.to.ProcessTO;
import com.github.sfragata.processmanager.util.MethodInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	protected abstract List<ProcessTO> stream2List(InputStream inputStream, String pattern)
			throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

	protected abstract String[] getSetProcessMethods();

	public List<ProcessTO> getProcesses(final String pattern) throws ProcessManagerException {
		try {
			Process proc = Runtime.getRuntime().exec(getListCommand(pattern));
			proc.waitFor(5, TimeUnit.SECONDS);
			return stream2List(proc.getInputStream(), pattern);
		} catch (IOException | InterruptedException | NoSuchMethodException | InvocationTargetException
				| IllegalAccessException e) {
			throw new ProcessManagerException("Error", e);
		}
	}

	public void killProcess(String pid) throws ProcessManagerException {
		try {
			Process proc = Runtime.getRuntime().exec(getKillCommand(pid));
			int status = proc.waitFor();
			if (status != 0) {
				String stack = readStream(proc.getInputStream());
				if (!StringUtils.hasLength(stack)) {
					stack = String.format("PID: %s", pid);
				}
				throw new ProcessManagerException(stack);
			}
		} catch (IOException | InterruptedException e) {
			throw new ProcessManagerException(String.format("Error killing process %s", pid), e);
		}
	}

	protected String readStream(InputStream inputStream) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			StringBuilder buffer = new StringBuilder();
			while (bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				if (line != null && !line.trim().equals("")) {
					buffer.append(line).append("\n");
				}
			}
			return buffer.toString();
		}
	}

	protected ProcessTO createProcessManagerTO(final String processLine, String pattern)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		ProcessTO processManagerTO = new ProcessTO();
		if (StringUtils.hasLength(processLine)) {
			String[] setMethods = getSetProcessMethods();
			String line = processLine.trim();
			for (int i = 0; i < setMethods.length; i++) {
				String column;
				if (i == (setMethods.length - 1)) {
					column = line;
				} else {
					column = line.substring(0, line.indexOf(" "));
					line = line.substring(line.indexOf(" ")).trim();
				}
				methodInvoker.invokeSetMethod(processManagerTO, setMethods[i], column, String.class);
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
