package com.github.sfragata.processmanager.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.sfragata.processmanager.manager.ProcessManager;
import com.github.sfragata.processmanager.to.ProcessTO;

/**
 * @author Silvio Fragata da Silva - a5014999
 *         http://technet.microsoft.com/en-us/sysinternals/bb842062.aspx
 */
@Component
@Qualifier("SysinternalsPsListWindows")
public class ProcessManagerSysinternalsPsListImpl extends ProcessManagerBaseImpl implements ProcessManager {

	public ProcessManagerSysinternalsPsListImpl() {
	}

	@Override
	protected String[] getListCommand(String pattern) {
		if (StringUtils.hasLength(pattern)) {
			return new String[] { "pslist", pattern };
		}
		return new String[] { "pslist" };
	}

	@Override
	protected String getKillCommand(String process) {
		return String.format("pskill %s", process);
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
				if (line != null && !line.trim().equals("") && nLine > 2) {
					process.add(createProcessManagerTO(line, pattern));
				}
				nLine++;
			}
			return process;
		}
	}

	@Override
	protected String[] getSetProcessMethods() {
		return new String[] { "setName", "setPid", "setPri", "setThd", "setHnd", "setPriv", "setCpuTime",
				"setElapsedTime" };
	}
}
