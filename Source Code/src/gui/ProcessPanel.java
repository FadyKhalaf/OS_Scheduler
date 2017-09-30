package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.lang.model.type.NullType;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProcessPanel extends JPanel {
	
	private JList<String> comboBox;
	private JLabel algorithmType;
	private JButton okButton;
	private JLabel procLabel;
	private JComboBox<Integer> processes;
	private JCheckBox Preemptive;
	private FormListner formListner;
	private JTextField timeSlice;

	
	public ProcessPanel() {
		setBorder(BorderFactory.createTitledBorder("Add Processes"));
		
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(200, 600));

		//number of processes
		procLabel = new JLabel("Processes:");
		DefaultComboBoxModel<Integer> processesModel = new DefaultComboBoxModel<>();
		for(int i = 0; i < 11; i++) {
			processesModel.addElement(i);
		}
		processes = new JComboBox<Integer>(processesModel);
		
		//Algorithm type
		algorithmType = new JLabel("Algorithm:");
		
		DefaultListModel<String> comboModel = new DefaultListModel<>();
		comboModel.add(0 , "FCFS");
		comboModel.add(1 ,"Round Roben");
		comboModel.add(2 ,"SJF");
		comboModel.add(3 ,"Priority");
		comboBox = new JList<String>(comboModel);
		comboBox.setSelectedIndex(0);
		comboBox.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				int first = e.getFirstIndex();
				int second = e.getLastIndex();
				if((first == 0 || first == 1) && (second == 2 || second == 3))
					Preemptive.setEnabled(true);
				else if((first == 0 || first == 1) && (second == 0 || second == 1))
					Preemptive.setEnabled(false);
				else if((first == 2 || first == 3) && (second == 2 || second == 3))
					Preemptive.setEnabled(true);
				else if((first == 2 || first == 3) && (second == 0 || second == 1))
					Preemptive.setEnabled(false);				
			}
		});
			
			
		
		
		//determining if the algorithm is preemptive or not
		Preemptive = new JCheckBox("pre-emptive");
		Preemptive.setEnabled(false);
		Preemptive.setSelected(false);
		
		//time slice
		timeSlice = new JTextField(8);
		timeSlice.setText("1");

		//ok button
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Algorithm = comboBox.getSelectedValue();
				int number = processes.getSelectedIndex();
				boolean PREEMPTION = false;
				if(Preemptive.isEnabled())
					PREEMPTION = Preemptive.isSelected();
				int timeslice;
				if(Algorithm == "Round Roben") {
					timeslice = Integer.parseInt(timeSlice.getText());
				}
				else 
					timeslice = 0;
					FormEvent ev = new FormEvent(this, Algorithm, number, PREEMPTION, timeslice);
				formListner.formEventOccured(ev);
			}
		});
		
		//adding items
		MyLayout(this);
	}
	
	private void MyLayout(JPanel parent) {
		GridBagConstraints gc = new GridBagConstraints();
		gc.weighty = 0.1;
		gc.weightx = 0.1;
		//gc.fill = GridBagConstraints.NONE;
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0,0,0,5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(procLabel, gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0,0,0,0);
		add(processes, gc);
		//////////////next Line/////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(algorithmType, gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0,0,0,5);
		add(comboBox, gc);
				
		//////////////next Line/////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("PREEMPTION"), gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0,0,0,5);
		add(Preemptive, gc);
		//////////////next Line/////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("TIME SLICE"), gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0,0,0,5);
		add(timeSlice, gc);
		//////////////next Line/////////////////
		gc.gridy++;
		gc.gridx = 1;
		gc.weighty = 1;
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okButton, gc);
	}
	
	public void setFormListner(FormListner listner) {
		this.formListner = listner;
	}
}
