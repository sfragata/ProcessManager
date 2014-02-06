/**
 * 
 */
package br.com.sfragata.processmanager.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.sfragata.processmanager.manager.ProcessManager;
import br.com.sfragata.processmanager.to.ProcessTO;

/**
 * @author Silvio Fragata da Silva
 * 
 */
@Component
@Qualifier("Unix")
public class ProcessManagerPsUnixImpl extends ProcessManagerBaseImpl implements
		ProcessManager {

	public ProcessManagerPsUnixImpl() {
	}

	@Override
	protected List<ProcessTO> stream2List(BufferedReader read, String pattern)
			throws IOException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		try {
			List<ProcessTO> process = new ArrayList<ProcessTO>();
			int nLine = 0;

			while (read.ready()) {
				String line = read.readLine();
				if (line != null
						&& !line.trim().equals("")
						&& line.indexOf(createCommand(getListCommand(pattern))) == -1
						&& (StringUtils.hasLength(pattern) || nLine > 1)) {
					process.add(createProcessManagerTO(line, pattern));
				}
				nLine++;
			}
			return process;
		} finally {
			if (read != null) {
				read.close();
			}
		}
	}

	@Override
	protected String[] getListCommand(String pattern) {
		if (StringUtils.hasLength(pattern)) {
			return new String[] { "/bin/sh", "-c",
					String.format("ps -ef | grep %s", pattern) };
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
		return new String[] { "setPriv", "setPid", "setThd", "setPri",
				"setCpuTime", "setHnd", "setElapsedTime", "setName" };
	}
}
