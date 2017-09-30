package gui;

import java.util.ArrayList;
import java.util.EventObject;

public class DialogEvent extends EventObject {

	private ArrayList<Integer> arrivals;
	private ArrayList<Integer> pursts;
	private ArrayList<Integer> priority;
	
	public DialogEvent(Object arg0) {
		super(arg0);
	}
	
	public DialogEvent(Object arg0, ArrayList<Integer> arrivals, ArrayList<Integer> pursts, ArrayList<Integer> priority) {
		super(arg0);
		this.arrivals = arrivals;
		this.pursts = pursts;
		this.priority = priority;
	}

	public ArrayList<Integer> getArrivals() {
		return arrivals;
	}

	public void setArrivals(ArrayList<Integer> arrivals) {
		this.arrivals = arrivals;
	}

	public ArrayList<Integer> getPursts() {
		return pursts;
	}

	public void setPursts(ArrayList<Integer> pursts) {
		this.pursts = pursts;
	}

	public ArrayList<Integer> getPriority() {
		return priority;
	}

	public void setPriority(ArrayList<Integer> priority) {
		this.priority = priority;
	}
	
	
}
