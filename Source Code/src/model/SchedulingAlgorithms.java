package model;

import java.util.ArrayList;

public class SchedulingAlgorithms {
	public static void FCFS(ArrayList<process> processes) {
		sortMinArrival(processes);
		int time = 0;
		boolean isFirstProcess = true;
		for(process temp : processes) {
			if(isFirstProcess) {
				isFirstProcess = false;
				time =+ temp.getArrivalTime();
			}
			temp.addStart(time);
			time = executeProcess(temp, time);
			temp.addStopingTime(time);
		}
		sortMinID(processes);
	}
	
	public static void SJF(ArrayList<process> processes) {
		
		int time = 0;
		process temp;
		int pointer = 0;
		//for first entry
		sortMinArrival(processes);
		time += processes.get(0).getArrivalTime(); 
		
		processes.get(0).addStart(time);
		time = executeProcess(processes.get(0), time);
		processes.get(0).addStopingTime(time);
		
		//for the rest of processes
		SortMinBurst(processes);
		while(!processesDeparted(processes)) {
			temp = processes.get(pointer);
			
			if(temp.isDeparted() || time < temp.getArrivalTime()) {
				pointer++;
				continue;
			}
			
			temp.addStart(time);
			time = executeProcess(temp, time);
			temp.addStopingTime(time);
			pointer = 0;
		}
		sortMinID(processes);
	}
	
	public static void RoundRoben(ArrayList<process> processes, int timeSlice) {
		sortMinArrival(processes);
		int realTime = 0;
		
		boolean isFirstProcess = true;
		
		while(!processesDeparted(processes)) {
			for(process temp : processes) {
				if(isFirstProcess) {
					realTime += temp.getArrivalTime();
					isFirstProcess = false;
				}
				if(realTime < temp.getArrivalTime() || temp.isDeparted())
					continue;
				temp.addStart(realTime);
				realTime = executeTimeSlice(temp, realTime, timeSlice);
				temp.addStopingTime(realTime);
			}
		}
		sortMinID(processes);
	}
	
	public static void Priority(ArrayList<process> processes) {
		sortMinArrival(processes);
		int realTime = 0;
		int pointer = 0;
		
		boolean isFirstProcess = true;
		
		while(!processesDeparted(processes)) {
			if(isFirstProcess) {
				isFirstProcess = false;
				realTime += processes.get(pointer).getArrivalTime();
				processes.get(pointer).addStart(realTime);
				realTime = executeProcess(processes.get(pointer), realTime);
				processes.get(pointer).addStopingTime(realTime);
				sortMinPriority(processes);
				
			}
			else if(realTime < processes.get(pointer).getArrivalTime() || processes.get(pointer).isDeparted()) {
				pointer++;
				continue;
			}
			else {
				processes.get(pointer).addStart(realTime);
				realTime = executeProcess(processes.get(pointer), realTime);
				processes.get(pointer).addStopingTime(realTime);
				pointer = 0;
			}
		}
		sortMinID(processes);
	}
	
	public static void Priority_Preemptive(ArrayList<process> p) {
		sortMinArrival(p);
		int realTime = 0;
		int pointer = 0;
		boolean isFirstProcess = true;
		while(!processesDeparted(p)) {
			if(isFirstProcess) {
				realTime += p.get(pointer).getArrivalTime();
				p.get(pointer).addStart(realTime);
				realTime = executeTimeSlice(p.get(pointer), realTime, 1);
				p.get(pointer).addStopingTime(realTime);
				sortMinPriority(p);
				isFirstProcess = false;
				continue;
			}
			if(realTime < p.get(pointer).getArrivalTime() || p.get(pointer).isDeparted()) {
				pointer++;
				continue;
			}
			if(pointer == p.size()) {
				pointer = 0;
				continue;
			}
			p.get(pointer).addStart(realTime);
			realTime = executeTimeSlice(p.get(pointer), realTime, 1);
			p.get(pointer).addStopingTime(realTime);
			pointer = 0;
		}
		deleteRedundent(p);
		sortMinID(p);
	}
	
	public static void SJF_Preemptive(ArrayList<process> p) {
		sortMinArrival(p);
		int realTime = 0;
		int pointer = 0;
		boolean isFirstProcess = true;
		while(!processesDeparted(p)) {
			if(isFirstProcess) {
				realTime += p.get(pointer).getArrivalTime();
				p.get(pointer).addStart(realTime);
				realTime = executeTimeSlice(p.get(pointer), realTime, 1);
				p.get(pointer).addStopingTime(realTime);
				SortMinRemainingTime(p);
				isFirstProcess = false;
				continue;
			}
			if(realTime < p.get(pointer).getArrivalTime() || p.get(pointer).isDeparted()) {
				pointer++;
				continue;
			}
			if(pointer == p.size()) {
				pointer = 0;
				continue;
			}
			p.get(pointer).addStart(realTime);
			realTime = executeTimeSlice(p.get(pointer), realTime, 1);
			p.get(pointer).addStopingTime(realTime);
			pointer = 0;
			SortMinRemainingTime(p);
		}
		deleteRedundent(p);
		sortMinID(p);
	}
	
	private static void sortMinArrival(ArrayList<process> processes) {
		for(int i = 0; i < processes.size() - 1; i++) {
			process min = processes.get(i);
			for(int j = i+1; j < processes.size(); j++) {
				if(processes.get(j).getArrivalTime() < min.getArrivalTime()) {
					min = processes.get(j);
				}
			}
			swap(processes, processes.indexOf(min),  processes.indexOf(processes.get(i)));
		}
	}
	
	private static void sortMinID(ArrayList<process> processes) {
		for(int i = 0; i < processes.size() - 1; i++) {
			process min = processes.get(i);
			for(int j = i+1; j < processes.size(); j++) {
				if(processes.get(j).getId() < min.getId()) {
					min = processes.get(j);
				}
			}
			swap(processes, processes.indexOf(min),  processes.indexOf(processes.get(i)));
		}
	}
	
	private static void sortMinPriority(ArrayList<process> processes) {
		//selection sort
		for(int i = 0; i < processes.size() - 1; i++) {
			process min = processes.get(i);
			for(int j = i+1; j < processes.size(); j++) {
				if(processes.get(j).getPriority() < min.getPriority()) {
					min = processes.get(j);
				}
			}
			swap(processes, processes.indexOf(min),  processes.indexOf(processes.get(i)));
		}
	}
	
	private static void SortMinBurst(ArrayList<process> processes) {
		//selection sort
		for(int i = 0; i < processes.size() - 1; i++) {
			process min = processes.get(i);
			for(int j = i+1; j < processes.size(); j++) {
				if(processes.get(j).getBurstTime() < min.getBurstTime()) {
					min = processes.get(j);
				}
			}
			swap(processes, processes.indexOf(min),  processes.indexOf(processes.get(i)));
		}
	}
	
	private static void SortMinRemainingTime(ArrayList<process> processes) {
		//selection sort
		for(int i = 0; i < processes.size() - 1; i++) {
			process min = processes.get(i);
			for(int j = i+1; j < processes.size(); j++) {
				if(processes.get(j).getRemainingTime() < min.getRemainingTime()) {
					min = processes.get(j);
				}
			}
			swap(processes, processes.indexOf(min),  processes.indexOf(processes.get(i)));
		}
	}
	
	private static void swap(ArrayList<process> p, int first, int second) {
		process tempfirst = p.get(first);
		process tempsecond = p.get(second);
		p.remove(first);
		p.add(first, tempsecond);
		p.remove(second);
		p.add(second, tempfirst);
	}
	
	private static boolean processesDeparted(ArrayList<process> p) {
		for(process temp : p) {
			if(!temp.isDeparted())
				return false;
		}
		return true;
	}
	
	private static int executeProcess(process p, int time) {
		time += p.getBurstTime();
		p.setDepartureTime(time);
		p.setDeparted(true);
		return time;
	}
	
	private static int executeTimeSlice(process p, int time, int timeSlice) {
		if(p.getRemainingTime() <= timeSlice) {
			time += p.getRemainingTime();
			p.setDeparted(true);
			p.setDepartureTime(time);
		}
		
		else  {
			p.setRemainingTime(p.getRemainingTime() - timeSlice);
			time += timeSlice;
		}
		
		return time;
	}
	
	
	private static void deleteRedundent(ArrayList<process> p) {
		ArrayList<Integer> starts;
		ArrayList<Integer> stops;
		int index = 1;
		for(process temp : p) {
			starts = temp.getStarts();
			stops = temp.getStoppingTimes();
			while(true) {
				if(index == starts.size() || starts.size() == 1) {
					index = 1;
					break;
				}
				if(starts.get(index) == stops.get(index-1)) {
					starts.remove(index);
					stops.remove(index-1);
				}
				else
					index++;
			}
		}
	}
}
	
