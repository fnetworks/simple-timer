package org.fnet.countdown;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class TimerListDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public TimerListDialog(List<Timer> timers) {
		setTitle("Timer overview");
		setBounds(100, 100, 450, 233);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		DefaultListModel<Timer> model = new DefaultListModel<>();
		for (Timer timer : timers) {
			model.addElement(timer);
		}
		JList<Timer> list = new JList<>(model);
		contentPanel.add(list);

		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new BorderLayout(0, 0));

		JPanel closeButtonPanel = new JPanel();
		buttonPane.add(closeButtonPanel, BorderLayout.EAST);

		JButton okButton = new JButton("OK");
		closeButtonPanel.add(okButton);
		okButton.addActionListener(e -> {
			dispose();
		});
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		JPanel timerButtonsPanel = new JPanel();
		buttonPane.add(timerButtonsPanel, BorderLayout.WEST);

		JButton addTimerBtn = new JButton("\u2795");
		addTimerBtn.addActionListener(e -> {
			AddTimerDialog dialog = new AddTimerDialog();
			dialog.setVisible(true);
			Timer timer = dialog.getDialogResult();
			if (timer != null) {
				timers.add(timer);
				model.addElement(timer);
			}
		});
		timerButtonsPanel.add(addTimerBtn);

		JButton removeTimerBtn = new JButton("\u2796");
		removeTimerBtn.setEnabled(false);
		removeTimerBtn.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(this, "Delete timer " + list.getSelectedValue().getName().trim() + "?",
					"Delete component", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				int selectedIndex = list.getSelectedIndex();
				model.remove(selectedIndex);
				timers.remove(selectedIndex);
			}
		});
		timerButtonsPanel.add(removeTimerBtn);

		list.addListSelectionListener(e -> {
			removeTimerBtn.setEnabled(e.getFirstIndex() >= 0);
		});
	}

}
