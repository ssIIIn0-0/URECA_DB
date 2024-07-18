package Matching.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class RecommendationDialog extends JDialog {
	private JLabel messageLabel;
	private JLabel countdownLabel;

	public RecommendationDialog(JFrame parent) {
		super(parent, "Recommendation", true);
		setLayout(new BorderLayout());
		setSize(400, 200);
		setLocationRelativeTo(parent);

		messageLabel = new JLabel("<html><div style='text-align: center;'>프로필 기반으로 비슷한 관심사를 가진 친구를 찾는중입니다!<br>두구두구두구o(≧口≦)o</div></html>", JLabel.CENTER);
		countdownLabel = new JLabel("3", JLabel.CENTER);

		add(messageLabel, BorderLayout.CENTER);
		add(countdownLabel, BorderLayout.SOUTH);

		startCountdown();
	}

	private void startCountdown() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int countdown = 3;

			@Override
			public void run() {
				countdown--;
				if (countdown == 0) {
					timer.cancel();
					dispose();
				}
				countdownLabel.setText(String.valueOf(countdown));
			}
		}, 1000, 1000);
	}
}
