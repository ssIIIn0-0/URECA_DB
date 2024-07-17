package Matching.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame; // windows application
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Matching.dao.UserDAO;
import Matching.dto.InterestDTO;
import Matching.dto.UserDTO;

public class MainFrameManager extends JFrame {

//	private JTable table; // grid ui component
//	private DefaultTableModel tableModel; // grid data
//	private JButton signUpButton, signInButton;
//	private JTextField idField, passwordField;
	private UserDAO userDao = new UserDAO();
	private JPanel mainPanel;

	public MainFrameManager() {

		// 기본 화면 UI
		setTitle("Interest Based User Recommendation");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		mainPanel = new JPanel();
		showLoginPanel();

		add(mainPanel, BorderLayout.CENTER);
	}

	private void showLoginPanel() {
		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(4, 2));
		
		// 로그인 화면
		JLabel idLabel = new JLabel("ID");
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();
        
        JButton signUpButton = new JButton("sign up");
		JButton signInButton = new JButton("sign in");

		// button action event
		signUpButton.addActionListener(e -> {
			SignUpDialog signupDialog = new SignUpDialog(this);
			signupDialog.setVisible(true);
		});

		signInButton.addActionListener(e -> {
			String Id = idField.getText();
			String PassWord = new String(passwordField.getPassword());
			
			if (Id.isBlank() || PassWord.isBlank()) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력하세요.");
			}

			try {
				UserDTO user = userDao.getUserByID(Id);
				if (user == null) {
					JOptionPane.showMessageDialog(this, "조회되지 않는 아이디 입니다.");
				} else if (!user.getPassword().equals(PassWord)) {
					JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다.");
				} else {
					showInterestPanel();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error during login", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		mainPanel.add(idLabel);
		mainPanel.add(idField);
		mainPanel.add(passwordLabel);
		mainPanel.add(passwordField);
		mainPanel.add(signUpButton);
		mainPanel.add(signInButton);

		mainPanel.revalidate();
        mainPanel.repaint();
	}

	void signupUser(UserDTO userdto) {
		int ret = userDao.addUser(userdto);
		if (ret == 1) {
			System.out.println("sign up successful");
		}
	}

	private void showInterestPanel() {
		mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new InterestPanel(this), BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrameManager().setVisible(true);
			}
		});
	}
}
