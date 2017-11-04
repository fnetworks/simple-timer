package org.fnet.countdown;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class Main {

	public static void main(String[] args) {
		JFrame frmTimer = new JFrame();
		frmTimer.setTitle("Timer");
		frmTimer.setSize(558, 224);
		
		JLabel lblTime = new JLabel("00:00:00");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		frmTimer.getContentPane().add(lblTime, BorderLayout.CENTER);
		
		JLabel lblTimerName = new JLabel("Timer name");
		lblTimerName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTimerName.setHorizontalAlignment(SwingConstants.CENTER);
		frmTimer.getContentPane().add(lblTimerName, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frmTimer.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnAddTimer = new JButton("+");
		panel.add(btnAddTimer, BorderLayout.EAST);
		
		JLabel lblNextTimerTimer = new JLabel("Next timer: Timer name");
		panel.add(lblNextTimerTimer, BorderLayout.WEST);
		frmTimer.setVisible(true);
	}
	
}
