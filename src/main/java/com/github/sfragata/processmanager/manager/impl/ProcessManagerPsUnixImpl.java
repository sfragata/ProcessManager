package com.github.sfragata.processmanager.manager.impl;

import com.github.sfragata.processmanager.manager.ProcessManager;
import com.github.sfragata.processmanager.to.ProcessTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Fragata da Silva
 * 
 */
@Component
@Qualifier("Unix")
public class ProcessManagerPsUnixImpl extends ProcessManagerBaseImpl implements ProcessManager {

	public ProcessManagerPsUnixImpl() {
	}

	@Override
	protected List<ProcessTO> stream2List(InputStream inputStream, String pattern)
			throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			List<ProcessTO> process = new ArrayList<>();
			int nLine = 0;

			while (bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				if (line != null && !line.trim().equals("")
						&& !line.contains(createCommand(getListCommand(pattern)))
						&& (StringUtils.hasLength(pattern) || nLine > 1)) {
					process.add(createProcessManagerTO(line, pattern));
				}
				nLine++;
			}
			return process;
		}
	}

	@Override
	protected String[] getListCommand(String pattern) {
		if (StringUtils.hasLength(pattern)) {
			return new String[] { "/bin/sh", "-c", String.format("ps -ef | grep %s", pattern) };
		} else {
			return new String[] { "/bin/sh", "-c", "ps -ef" };
		}
	}

	@Override
	protected String getKillCommand(String process) {
		return String.format("kill %s", process);
	}

	@Override
	protected String[] getSetProcessMethods() {
		return new String[] { "setPriv", "setPid", "setThd", "setPri", "setCpuTime", "setHnd", "setElapsedTime",
				"setName" };
	}
}
