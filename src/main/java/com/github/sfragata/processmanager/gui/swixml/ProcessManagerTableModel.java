package com.github.sfragata.processmanager.gui.swixml;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * @author Silvio Fragata da Silva - a5014999
 * 
 */
public class ProcessManagerTableModel extends DefaultTableModel {

	/**
     *
     */
	private static final long serialVersionUID = -4223874201981670143L;

	/**
     *
     */
	public ProcessManagerTableModel() {
		this(new Object[] { "Name", "PID", "Pri", "Thd", "Hnd", "Priv",
				"CPU Time", "Elapsed Time" }, 0);
	}

	public ProcessManagerTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public ProcessManagerTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public ProcessManagerTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	@SuppressWarnings("unchecked")
	public ProcessManagerTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public ProcessManagerTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable(int row, int column) {
		return false;// super.isCellEditable(row, column);
	}
}
