package gui;

import java.util.EventObject;

public class FormEvent extends EventObject{
	
	private String algorithm;
	private int processes;
	private boolean preemptive;
	private int timeslice;

	public FormEvent(Object arg0) {
		super(arg0);
	}
	
	public FormEvent(Object parent, String algorithm , int processes, boolean preemptive, int timeslice) {
		super(parent);
		this.algorithm = algorithm;
		this.processes = processes;
		this.preemptive = preemptive;
		this.timeslice = timeslice;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public int getProcesses() {
		return processes;
	}

	public void setProcesses(int processes) {
		this.processes = processes;
	}

	public boolean isPreemptive() {
		return preemptive;
	}

	public void setPreemptive(boolean preemptive) {
		this.preemptive = preemptive;
	}

	public int getTimeslice() {
		return timeslice;
	}

	public void setTimeslice(int timeslice) {
		this.timeslice = timeslice;
	}
	
	

}
