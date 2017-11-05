package org.fnet.countdown;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private List<Timer> timers;
	private boolean running = false;

	private static final String PAUSE_STRING = "\u275A\u275A", PLAY_STRING = "\u25B6";
	private Icon stopwatchIcon;
	private JLabel lblTime;
	private JLabel lblTimerName;
	private JLabel lblNextTimer;
	private JButton btnStartStop;

	private void updateTimer() {
		if (timers.size() == 0) {
			lblTimerName.setText("-");
			lblTime.setText("0:00:00");
			lblNextTimer.setText("Next timer: -");
			btnStartStop.setEnabled(false);
			running = false;
		} else if (timers.size() == 1) {
			Timer timer = timers.get(0);
			lblTimerName.setText(timer.getName());
			lblTime.setText(timer.formatTimeSpan());
			btnStartStop.setEnabled(true);
		} else {
			Timer timer = timers.get(0);
			lblTimerName.setText(timer.getName());
			lblTime.setText(timer.formatTimeSpan());
			lblNextTimer.setText("Next timer: " + timers.get(1).getName());
			btnStartStop.setEnabled(true);
		}
	}

	public MainFrame() {
		try {
			Image icon = ImageIO.read(MainFrame.class.getResourceAsStream("/icons/stopwatch.png")).getScaledInstance(16,
					16, BufferedImage.SCALE_SMOOTH);
			stopwatchIcon = new ImageIcon(icon);
			setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		timers = new WatchableList<>();

		java.util.Timer countdownTimer = new java.util.Timer();
		countdownTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (running && timers.size() > 0) {
					Timer currentTimer = timers.get(0);
					currentTimer.setTimeSpanSeconds(currentTimer.getTimeSpanSeconds() - 1);
					updateTimer();
				}
			}
		}, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Timer");
		setBounds(100, 100, 450, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lblTime = new JLabel("0:00:00");
		lblTime.setFont(new Font("Consolas", Font.PLAIN, 99));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTime, BorderLayout.CENTER);

		lblTimerName = new JLabel("-");
		lblTimerName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTimerName.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTimerName, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 4, 0, 0));
		panel.setBackground(SystemColor.controlHighlight);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		lblNextTimer = new JLabel("Next timer: -");
		lblNextTimer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNextTimer, BorderLayout.WEST);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(135, 30));
		panel.add(buttonPanel, BorderLayout.EAST);

		btnStartStop = new JButton(PLAY_STRING);
		btnStartStop.setEnabled(false);
		btnStartStop.addActionListener(e -> {
			if (running) {
				btnStartStop.setText(PLAY_STRING);
			} else {
				btnStartStop.setText(PAUSE_STRING);
			}
			running = !running;
		});
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		buttonPanel.add(btnStartStop);

		JButton btnAddTimer = new JButton("\u2795");
		buttonPanel.add(btnAddTimer);

		JButton btnMenu = new JButton("...");
		btnMenu.addActionListener(e -> {
			JPopupMenu menu = new JPopupMenu("Menu");
			JMenuItem showAllTimersItem = new JMenuItem("Show all timers", stopwatchIcon);
			showAllTimersItem.addActionListener(ev -> {
				TimerListDialog dialog = new TimerListDialog(timers);
				dialog.setVisible(true);
			});
			menu.add(showAllTimersItem);
			menu.show(btnMenu, 0, -btnMenu.getHeight());
		});
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonPanel.add(btnMenu);
		btnAddTimer.addActionListener(e -> {
			AddTimerDialog dialog = new AddTimerDialog();
			dialog.setVisible(true);
			Timer timer = dialog.getDialogResult();
			if (timer != null)
				timers.add(timer);
		});

		((WatchableList<Timer>) timers).addElementListener((type, e) -> updateTimer());
	}

}
