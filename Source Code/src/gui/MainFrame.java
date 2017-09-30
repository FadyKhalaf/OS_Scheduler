package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import controller.myController;
import model.process;

public class MainFrame extends JFrame {
	
	private JTextArea textArea;
	private processTablePanel processTable;
	private ProcessPanel processPanel;
	private processDialog processDetails;
	private JTabbedPane tabbedPane;
	private ProcessGanntChart GanntChart;
	private JFileChooser fileChooser;
	myController controller;
	
	private String Algorithm;
	boolean preemptive = false;
	int timeslice ;
	
	public MainFrame() {
		super("schedular");
		controller =  myController.getInstance(this);
		fileChooser = new JFileChooser();
		
		processPanel = new ProcessPanel();
		processDetails = new processDialog(this);
		textArea = new JTextArea();
		textArea.setFont(new Font("sansref", Font.BOLD, 16));
		processTable = new processTablePanel();
		GanntChart = new ProcessGanntChart(new ArrayList<process>());
		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(900, 600));
		tabbedPane.add("Console log", textArea);
		tabbedPane.add("Process Table", processTable);
		tabbedPane.add("Gannt Chart", GanntChart);
		
		setLayout(new BorderLayout());
		add(processPanel, BorderLayout.WEST);
		add(tabbedPane, BorderLayout.CENTER);
		
		
		processPanel.setFormListner(new FormListner() {
			
			public void formEventOccured(FormEvent e) {
				Algorithm = e.getAlgorithm();
				preemptive = e.isPreemptive();
				timeslice = e.getTimeslice();
				int Processes = e.getProcesses();
				processDetails.setVisibility(Processes);
				processDetails.setVisible(true);
			}
		});
		
		processDetails.setDialogListner(new DialogListener() {
			
			public void DialogEventOccured(DialogEvent e) {
				ArrayList<Integer> arrivals = e.getArrivals();
				ArrayList<Integer> pursts = e.getPursts();
				ArrayList<Integer> priority = e.getPriority();
				controller.schedule(arrivals, pursts, priority,  Algorithm, preemptive, timeslice);
			}
		});
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		pack();
		setVisible(true);
		createMenuBar();
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem imporT = new JMenuItem("Import");
		JMenuItem export = new JMenuItem("Export");
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(imporT);
		fileMenu.add(export);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		
		JMenu editMenu = new JMenu("Edit");
		
		imporT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.LoadFromFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						e1.getCause();
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(MainFrame.this, "cannot Import File");
					}
				}
			}
		});
		
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						e1.getStackTrace();
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(MainFrame.this, "cannot export File");
					}
				}
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		MainFrame.this.add(menuBar, BorderLayout.NORTH);
	}
	
	public void viewProcesses(String str) {
		textArea.setText("");
		textArea.append(str);
	}
	
	public void setTable(ArrayList<process> p) {
		processTable.setProcesses(p);
		processTable.refresh();
	}
	
	public void setGanntChart(ArrayList<process> p) {
		GanntChart.setProcesses(p);
	}
	
}
