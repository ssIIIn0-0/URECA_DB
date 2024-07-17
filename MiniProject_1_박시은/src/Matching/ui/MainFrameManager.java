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
import Matching.dto.UserDTO;

public class MainFrameManager extends JFrame {

	private JTable table; // grid ui component
	private DefaultTableModel tableModel; // grid data
	private JButton signUpButton, signInButton;
	private JTextField nameField, passwordField;
	private UserDAO userDao = new UserDAO();
	
    public MainFrameManager() {

    	// 기본 화면 UI
        setTitle("Interest Based User Recommendation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        
        signUpButton = new JButton("sign up");
        signInButton = new JButton("sign in");
        
        // button action event
        signUpButton.addActionListener(e -> {
        	SignUpDialog signupDialog = new SignUpDialog(this, this.tableModel);
        	signupDialog.setVisible(true);
		});

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signUpButton);
        panel.add(signInButton);

        add(panel, BorderLayout.CENTER);
    }

    void signupUser (UserDTO userdto){
		int ret = userDao.addUser(userdto);
		if (ret == 1) {
			System.out.println("sign up successful");
		}
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
