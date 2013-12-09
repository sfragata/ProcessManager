/**
 * 
 */
package br.com.sfragata.processmanager.manager.gui.swixml;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

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

	/**
	 * @param rowCount
	 * @param columnCount
	 */
	public ProcessManagerTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	/**
	 * @param columnNames
	 * @param rowCount
	 */
	@SuppressWarnings("rawtypes")
	public ProcessManagerTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	/**
	 * @param columnNames
	 * @param rowCount
	 */
	public ProcessManagerTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	/**
	 * @param data
	 * @param columnNames
	 */
	@SuppressWarnings("rawtypes")
	public ProcessManagerTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	/**
	 * @param data
	 * @param columnNames
	 */
	public ProcessManagerTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable(int row, int column) {
		return false;// super.isCellEditable(row, column);
	}
}
