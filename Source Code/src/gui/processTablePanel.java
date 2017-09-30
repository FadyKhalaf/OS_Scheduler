package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import model.process;

public class processTablePanel extends JPanel {

	private JTable processTable;
	private processTableModel model;
	
	public processTablePanel() {
		model = new processTableModel();
		processTable = new JTable(model);
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 200));
		add(new JScrollPane(processTable), BorderLayout.CENTER);
	}
	
	public void setProcesses(ArrayList<process> p) {
		model.setProcesses(p);
	}
	
	public void refresh() {
		model.fireTableDataChanged();
	}
}
