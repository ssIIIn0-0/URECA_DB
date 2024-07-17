package Matching.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Matching.dto.UserDTO;
import Matching.ui.MainFrameManager;

public class SignUpDialog extends JDialog {
	private JTextField userIdField, nameField, emailField, passWordField;
	private JButton signupButton;

	public SignUpDialog(MainFrameManager parent) {
		setTitle("Sign Up Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent); // 부모에 맞게

		// input panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));

		// field
		userIdField = new JTextField();
		nameField = new JTextField();
		emailField = new JTextField();
		passWordField = new JTextField();

		// add field with label, button
		inputPanel.add(new JLabel("ID"));
		inputPanel.add(userIdField);
		inputPanel.add(new JLabel("Name"));
		inputPanel.add(nameField);
		inputPanel.add(new JLabel("Email"));
		inputPanel.add(emailField);
		inputPanel.add(new JLabel("PassWord"));
		inputPanel.add(passWordField);

		// button panel
		JPanel buttonPanel = new JPanel();

		// button
		signupButton = new JButton("sign up");

		buttonPanel.add(signupButton);
		// add inputPanel, buttonPanel to Dialog
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// add button actionListner
		signupButton.addActionListener(e -> {
			String userId = userIdField.getText();
			String name = nameField.getText();
			String email = emailField.getText();
			String password = passWordField.getText();

			parent.signupUser(new UserDTO(userId, name, email, password));

			dispose();
		});
	}
}