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

import br.com.sfragata.processmanager.manager.AbstractProccessManager;
import br.com.sfragata.processmanager.manager.ProcessManager;
import br.com.sfragata.processmanager.to.ProcessTO;

/**
 * @author Silvio Fragata da Silva - a5014999
 *         http://technet.microsoft.com/en-us/sysinternals/bb842062.aspx
 */
@Component
@Qualifier("SysinternalsPsListWindows")
public class ProcessManagerSysinternalsPsListImpl extends
		AbstractProccessManager implements ProcessManager {

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
	protected List<ProcessTO> stream2List(BufferedReader reader, String pattern)
			throws IOException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		try {
			List<ProcessTO> process = new ArrayList<ProcessTO>();
			int nLine = 0;

			while (reader.ready()) {
				String line = reader.readLine();
				if (line != null && !line.trim().equals("") && nLine > 2) {
					process.add(createProcessManagerTO(line, pattern));
				}
				nLine++;
			}
			return process;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	@Override
	protected String[] getSetProcessMethods() {
		return new String[] { "setName", "setPid", "setPri", "setThd",
				"setHnd", "setPriv", "setCpuTime", "setElapsedTime" };
	}
}
