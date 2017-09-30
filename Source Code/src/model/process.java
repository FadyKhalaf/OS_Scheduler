package model;

import java.io.Serializable;
import java.util.ArrayList;

public class process implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7091187428925473702L;
	
	
	private int id;
	private int ArrivalTime;
	private int BurstTime;
	private int RemainingTime;
	private int DepartureTime;
	private boolean Departed;
	private int priority;
	private int WaitingTime;
	private ArrayList<Integer> stoppingTimes = new ArrayList<>();
	private ArrayList<Integer> starts = new ArrayList<>();
	
	public process(int id, int ArrivalTime, int BurstTime, int priority) {
		this.id = id;
		this.ArrivalTime = ArrivalTime;
		this.BurstTime = BurstTime;
		this.RemainingTime = BurstTime;
		this.Departed = false;
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return ArrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		ArrivalTime = arrivalTime;
	}

	public int getBurstTime() {
		return BurstTime;
	}

	public void setBurstTime(int purstTime) {
		BurstTime = purstTime;
	}

	public int getDepartureTime() {
		return DepartureTime;
	}

	public void setDepartureTime(int departureTime) {
		DepartureTime = departureTime;
		WaitingTime = DepartureTime - ArrivalTime - BurstTime;
	}

	public boolean isDeparted() {
		return Departed;
	}

	public void setDeparted(boolean departed) {
		Departed = departed;
	}

	public int getRemainingTime() {
		return RemainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		RemainingTime = remainingTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getWaitingTime() {
		return WaitingTime;
	}
	
	public ArrayList<Integer> getStoppingTimes() {
		return stoppingTimes;
	}
	
	public void addStopingTime(int st) {
		stoppingTimes.add(st);
	}
	
	public ArrayList<Integer> getStarts() {
		return starts;
	}
	
	public void addStart(int s) {
		starts.add(s);
	}
	
}
