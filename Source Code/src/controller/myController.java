package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import gui.MainFrame;
import model.SchedulingAlgorithms;
import model.process;

public class myController {
	private MainFrame mainFrame;
	private static myController ptr = null;
	ArrayList<process> processes = new ArrayList<>();
	private String str;

	private myController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public static myController getInstance(MainFrame mainFrame) {
		if (ptr == null) {
			ptr = new myController(mainFrame);
		}
		return ptr;
	}

	public void schedule(ArrayList<Integer> arrivals, ArrayList<Integer> Burst, ArrayList<Integer> priority,
			String algorithm, boolean preemptive, int timeslice) {
		processes.clear();
		str = "";
		for (int i = 0; i < arrivals.size(); i++) {
				processes.add(new process(i, arrivals.get(i), Burst.get(i), priority.get(i)));
		}
		switch (algorithm) {
		case "FCFS":
			SchedulingAlgorithms.FCFS(processes);
			break;
		case "Round Roben":
			SchedulingAlgorithms.RoundRoben(processes, timeslice);
			break;
		case "SJF":
			if(preemptive)
				SchedulingAlgorithms.SJF_Preemptive(processes);
			else
				SchedulingAlgorithms.SJF(processes);
			break;
		case "Priority":
			if(preemptive)
				SchedulingAlgorithms.Priority_Preemptive(processes);
			else
				SchedulingAlgorithms.Priority(processes);
		}
		for (process temp : processes) {
			str = str + ("process: " + (temp.getId() + 1) + "\t" + "Arrival time: " + temp.getArrivalTime() + "\t"
					+ "Departure time: " + temp.getDepartureTime() + "    " +"WaitingTime: " + temp.getWaitingTime() + "\n");
		}
		mainFrame.viewProcesses(str);
		mainFrame.setTable(processes);
		mainFrame.setGanntChart(processes);
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fio = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fio);
		process[] p = processes.toArray(new process[processes.size()]);
		oos.writeObject(p);
		oos.writeObject(str);
		fio.close();
	}
	
	public void LoadFromFile(File file) throws IOException {
		FileInputStream fio = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fio);
		try {
			process[] p = (process[]) ois.readObject();
			str = (String)ois.readObject();
			processes.clear();
			processes.addAll(Arrays.asList(p));
		} catch (ClassNotFoundException e) {
			System.err.println("wrong file");
			e.printStackTrace();
		}
		ois.close();
		mainFrame.viewProcesses(str);
		mainFrame.setTable(processes);
		mainFrame.setGanntChart(processes);
	}
	
}
