package com.github.sfragata.processmanager.to;

import java.io.Serializable;

/**
 * @author Silvio Fragata da Silva
 * 
 *         All memory values are displayed in KB. Abbreviation key: Pri Priority
 *         Thd Number of Threads Hnd Number of Handles Priv Private Virtual
 *         Memory
 */
public class ProcessTO implements Serializable {

	private static final long serialVersionUID = -3076309518887280634L;
	private String name;
	private String pid;
	private String pri;
	private String thd;
	private String hnd;
	private String priv;
	private String cpuTime;
	private String elapsedTime;

	public String getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(String cpuTime) {
		this.cpuTime = cpuTime;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getHnd() {
		return hnd;
	}

	public void setHnd(String hnd) {
		this.hnd = hnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPri() {
		return pri;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}

	public String getPriv() {
		return priv;
	}

	public void setPriv(String priv) {
		this.priv = priv;
	}

	public String getThd() {
		return thd;
	}

	public void setThd(String thd) {
		this.thd = thd;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\nProcessManagerTO\n[");
		buffer.append("cpuTime = ").append(cpuTime);
		buffer.append(" elapsedTime = ").append(elapsedTime);
		buffer.append(" hnd = ").append(hnd);
		buffer.append(" name = ").append(name);
		buffer.append(" pid = ").append(pid);
		buffer.append(" pri = ").append(pri);
		buffer.append(" priv = ").append(priv);
		buffer.append(" thd = ").append(thd);
		buffer.append("]");
		return buffer.toString();
	}
}
