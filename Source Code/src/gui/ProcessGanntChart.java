package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

import model.process;

public class ProcessGanntChart extends JComponent {

	private ArrayList<process> processes;
	private int xPosition;
	private int yPosition;

	public ProcessGanntChart(ArrayList<process> processes) {
		this.processes = processes;
		this.xPosition = this.getX();
		this.yPosition = this.getY();
	}

	@Override
	public void paint(Graphics g) {
		int currentX = xPosition + 20;
		int currentY = yPosition + 50;

		g.setColor(Color.BLACK);
		g.fillRect(this.getX(), this.getY() - 100, 1200, 1200);
		

		g.setColor(Color.WHITE);
		for (process temp : processes) {
			for (int i = 0; i < temp.getStoppingTimes().size(); i++) {
				g.drawRect(currentX + temp.getStarts().get(i) * 30, currentY,
						30 * (temp.getStoppingTimes().get(i) - temp.getStarts().get(i)), 50);

				g.drawString("p" + (temp.getId() + 1), currentX + temp.getStarts().get(i) * 30
						+ 13 * (temp.getStoppingTimes().get(i) - temp.getStarts().get(i)), currentY + 25);
				g.setFont(new Font("sansreef", Font.PLAIN, 11));
				g.drawString("" + temp.getStarts().get(i), currentX + temp.getStarts().get(i) * 30, currentY + 60);
				g.drawString("" + temp.getStoppingTimes().get(i), currentX + temp.getStoppingTimes().get(i) * 30, currentY + 60);
			}
		}
	}

	public void setProcesses(ArrayList<process> p) {
		this.processes = p;
		this.repaint();
	}

}
