package org.fnet.countdown;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AddTimerDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private Timer dialogResult = null;

	public AddTimerDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Add new timer");
		setBounds(100, 100, 450, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTimerName = new JLabel("Timer name");
		lblTimerName.setBounds(15, 16, 79, 14);
		contentPanel.add(lblTimerName);

		JTextField textField = new JTextField();
		textField.setBounds(15, 34, 277, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setBounds(15, 65, 79, 14);
		contentPanel.add(lblDuration);

		JSpinner hourSpinner = new JSpinner();
		hourSpinner.setBounds(15, 83, 38, 20);
		hourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		contentPanel.add(hourSpinner);

		JSpinner minuteSpinner = new JSpinner();
		minuteSpinner.setBounds(66, 83, 38, 20);
		minuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		contentPanel.add(minuteSpinner);

		JSpinner secondSpinner = new JSpinner();
		secondSpinner.setBounds(118, 83, 38, 20);
		contentPanel.add(secondSpinner);

		JLabel lblHours = new JLabel("h");
		lblHours.setBounds(55, 86, 12, 14);
		contentPanel.add(lblHours);

		JLabel lblMinutes = new JLabel("m");
		lblMinutes.setBounds(106, 86, 12, 14);
		contentPanel.add(lblMinutes);

		JLabel lblSeconds = new JLabel("s");
		lblSeconds.setBounds(158, 86, 12, 14);
		contentPanel.add(lblSeconds);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		ActionListener buttonActionListener = e -> {
			if (e.getActionCommand().equalsIgnoreCase("OK")) {
				dialogResult = new Timer(textField.getText(),
						(int) secondSpinner.getValue() + TimeUnit.MINUTES.toSeconds((int) minuteSpinner.getValue())
								+ TimeUnit.HOURS.toSeconds((int) hourSpinner.getValue()));
			}
			dispose();
		};
		okButton.addActionListener(buttonActionListener);
		cancelButton.addActionListener(buttonActionListener);
	}

	public Timer getDialogResult() {
		return dialogResult;
	}

}
