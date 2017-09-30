package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class processDialog extends JDialog implements ActionListener {
	
	private ArrayList<JTextField> arrivalTime;
	private ArrayList<JTextField> purstTime;
	private ArrayList<JTextField> priority;
	private JButton okButton;
	private JButton cancelButton;
	private DialogListener listner;
	
	public processDialog(JFrame parent) {
		super(parent, "Process/Processes Details", false);
		setSize(600, 300);
		arrivalTime = new ArrayList<JTextField>();
		purstTime = new ArrayList<JTextField>();
		priority = new ArrayList<JTextField>();
		
		okButton = new JButton("Ok");
		okButton.addActionListener(this);
		okButton.setActionCommand("okButton");
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancelButton");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		//adding the 3 labels
		gc.gridy = 0;
		gc.gridx = 1;
		gc.weighty = 0.1;
		add(new JLabel("ArrivalTime"), gc);
		gc.gridx++;
		add(new JLabel("PurstTime"), gc);
		gc.gridx++;
		add(new JLabel("Priority"));
		
		//for text fields
		gc.gridx = 0;
		gc.gridy = 1;
		JTextField tempTextField;
		for(int i = 1; i < 11; i++) {
			gc.insets = new Insets(0,0,0,5);
			add(new JLabel("Process" + i + ":"), gc);
			
			gc.insets = new Insets(0,0,0,15);
			gc.gridx++;
			tempTextField = new JTextField(10);
			arrivalTime.add(tempTextField);
			add(tempTextField, gc);
			
			gc.gridx++;
			tempTextField = new JTextField(10);
			purstTime.add(tempTextField);
			add(tempTextField, gc);
			
			gc.gridx++;
			tempTextField = new JTextField(10);
			priority.add(tempTextField);
			add(tempTextField, gc);
			gc.gridx = 0;
			gc.gridy++;
			
		}
		
		gc.weighty = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.EAST;
		Dimension dim = okButton.getPreferredSize();
		add(okButton, gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		cancelButton.setPreferredSize(dim);
		add(cancelButton, gc);
		setVisibility(0);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button = (JButton)e.getSource();
			String Command = button.getActionCommand();
			if(Command.equals("okButton")) {
				ArrayList<Integer> arrivals = new ArrayList<>();
				ArrayList<Integer> pursts = new ArrayList<>();
				ArrayList<Integer> Priority = new ArrayList<>();
				//fill up the arrayLists
				for(JTextField field : arrivalTime) {
					if(field.isEditable()) {
							try {
								arrivals.add(Integer.parseInt(field.getText()));
							}catch(NumberFormatException ev) {
								field.setText("1");
								arrivals.add(Integer.parseInt(field.getText()));
							}
					}
				}
					
				for(JTextField field : purstTime) {
					if(field.isEditable()) {
						try {
							pursts.add(Integer.parseInt(field.getText()));
						}catch(NumberFormatException ev) {
							field.setText("1");
							pursts.add(Integer.parseInt(field.getText()));
						}
					}
				}
				for(JTextField field : priority) {
						if(field.isEditable()) {
							try {
								Priority.add(Integer.parseInt(field.getText()));
							}catch(NumberFormatException ev) {
								field.setText("1");
								Priority.add(Integer.parseInt(field.getText()));
							}
						}
				}
					
				DialogEvent ev = new DialogEvent(processDialog.this, arrivals, pursts, Priority);
				listner.DialogEventOccured(ev);
			}
			processDialog.this.setVisible(false);
			setVisibility(0);
		}
	}

	
	public void setVisibility(int num) {
		for(int i = 0; i < num; i++) {
			arrivalTime.get(i).setEditable(true);
			purstTime.get(i).setEditable(true);
			priority.get(i).setEditable(true);
			arrivalTime.get(i).setText("");
			purstTime.get(i).setText("");
			priority.get(i).setText("1");
			
		}
		for(int j = num; j < 10; j++) {
			arrivalTime.get(j).setEditable(false);
			purstTime.get(j).setEditable(false);
			priority.get(j).setEditable(false);
			arrivalTime.get(j).setText("");
			purstTime.get(j).setText("");
			priority.get(j).setText("");
		}
	}
	
	public void setDialogListner(DialogListener listner) {
		this.listner = listner;
	}
}
