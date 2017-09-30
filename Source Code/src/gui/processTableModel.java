package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.process;

public class processTableModel extends AbstractTableModel {
	
	private ArrayList<process> processes;
	private String[] colNames = {"Process ID", "Arrival time", "Purst time", "Departure time" , "Waiting time", "Priority"};

	public processTableModel() {
		this.processes = new ArrayList<>();
	}
	
	public String getColumnName(int column) {
		return colNames[column];
	}
	

	public int getRowCount() {
		return processes.size();
	}
	
	public int getColumnCount() {
		return 6;
	}

	public Object getValueAt(int row, int column) {
		process p = processes.get(row);
		switch(column) {
		case 0:
			 return p.getId();
		case 1:
			 return  p.getArrivalTime();
		case 2:
			return p.getBurstTime();
		case 3:
			 return p.getDepartureTime();
		case 4:
			 return p.getWaitingTime();
		case 5:
			 return p.getPriority();
		}
		return null;
	}
	
	

	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return super.getColumnClass(columnIndex);
	}

	public void setProcesses(ArrayList<process> processes) {
		this.processes = processes;
	}

}
