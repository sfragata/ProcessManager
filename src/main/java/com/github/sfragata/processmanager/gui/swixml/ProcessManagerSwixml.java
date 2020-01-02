package com.github.sfragata.processmanager.gui.swixml;

import com.github.sfragata.processmanager.manager.ProcessManager;
import com.github.sfragata.processmanager.manager.ProcessManagerException;
import com.github.sfragata.processmanager.manager.ProcessManagerFactory;
import com.github.sfragata.processmanager.to.ProcessTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.swixml.SwingEngine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;

@Component
public class ProcessManagerSwixml implements ActionListener {

	private static final String PROCESSMANAGER_XML = "com/github/sfragata/processmanager/gui/swixml/processmanager.xml";

	private static final Logger logger = LoggerFactory.getLogger(ProcessManagerSwixml.class);

	private SwingEngine swixml;

	private ProcessManager manager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	public void setProcessManagerFactory(ProcessManagerFactory factory) {
		manager = factory.getProcessManager();
	}

	@Autowired
	public ProcessManagerSwixml(SwingEngine swixml) throws Exception {
		this.swixml = swixml;
		swixml.render(PROCESSMANAGER_XML);
		swixml.setActionListener(swixml.getRootComponent(), this);
		swixml.getRootComponent().setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("QUIT".equals(command)) {
			System.exit(0);
		} else if ("KILL_PROCESSES".equals(command)) {
			killProcesses();
		} else if ("GET_PROCESSES".equals(command)) {
			getProcesses();
		}
	}

	private JTable getTable() {
		return (JTable) swixml.find("tableProcesses");
	}

	private JTextField getTextField() {
		return (JTextField) swixml.find("textFieldParameter");
	}

	private void clearTable() {
		DefaultTableModel defaultTableModel = (DefaultTableModel) getTable().getModel();
		int rows = defaultTableModel.getRowCount();
		for (int i = 0; i < rows; i++) {
			defaultTableModel.removeRow(0);
		}
	}

	private void getProcesses() {
		clearTable();
		String params = getTextField().getText();
		try {
			List<ProcessTO> processes = manager.getProcesses(params);
			populateTable(processes);
		} catch (ProcessManagerException e) {
			error(e);
		}
	}

	private void populateTable(List<ProcessTO> processes) {
		DefaultTableModel defaultTableModel = (DefaultTableModel) getTable().getModel();
		for (ProcessTO process : processes) {
			Object[] row = new Object[] { process.getName(), process.getPid(), process.getPri(), process.getThd(),
					process.getHnd(), process.getPriv(), process.getCpuTime(), process.getElapsedTime() };
			defaultTableModel.addRow(row);
		}
	}

	private void killProcesses() {
		boolean errors = false;
		JTable table = getTable();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
		int rowCount = table.getRowCount();
		if (rowCount > 0) {
			int[] selectedRows = table.getSelectedRows();
			if (selectedRows.length > 0) {
				StringBuilder bufferErrorProcess = new StringBuilder();
				for (int i = 0; i < selectedRows.length; i++) {
					String pid = (String) defaultTableModel.getValueAt(selectedRows[i], 1);
					try {
						manager.killProcess(pid);
					} catch (ProcessManagerException e) {
						errors = true;
						bufferErrorProcess.append(e.getMessage()).append("\n");
					}
				}
				if (errors) {
					setError(messageSource.getMessage("ERROR_KILL_PROCESS",
							new Object[] { bufferErrorProcess.toString() }, Locale.getDefault()));
				} else {
					setMessage(messageSource.getMessage("KILL_SUCCESS", null, Locale.getDefault()));
					getProcesses();
				}
			} else {
				setError(messageSource.getMessage("SELECT_PROCESS", null, Locale.getDefault()));
			}
		} else {
			setError(messageSource.getMessage("NO_PROCCESS", null, Locale.getDefault()));
		}
	}

	public void error(Exception e) {
		logger.error("Error", e);
		try {
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			s.flush();
			s.close();
			setError(s.getBuffer().toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	void setError(String msg) {
		JOptionPane.showMessageDialog(swixml.getRootComponent(), msg,
				messageSource.getMessage("TITLE_ERROR", null, Locale.getDefault()), JOptionPane.ERROR_MESSAGE);
	}

	void setMessage(String msg) {
		JOptionPane.showMessageDialog(swixml.getRootComponent(), msg,
				messageSource.getMessage("TITLE_MESSAGE", null, Locale.getDefault()), JOptionPane.INFORMATION_MESSAGE);
	}

}
