package Matching.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Matching.dao.InterestDAO;
import Matching.dto.InterestDTO;

public class NewInterestDialog extends JDialog {
	private JTextField categoryField;
	private JTextField nameField;
	private JButton addButton;
	private InterestDAO interestDao = new InterestDAO();

	public NewInterestDialog(JFrame parent) {
		super(parent, "새로운 취미 등록하기", true);
		setLayout(new BorderLayout());
		setSize(300, 200);
		setLocationRelativeTo(parent);

		// Input Panel
		JPanel inputPanel = new JPanel(new GridLayout(3, 2));
		inputPanel.add(new JLabel("Category:"));
		categoryField = new JTextField();
		inputPanel.add(categoryField);

		inputPanel.add(new JLabel("Name:"));
		nameField = new JTextField();
		inputPanel.add(nameField);

		addButton = new JButton("Add");
		addButton.addActionListener(e -> addNewInterest());

		// Add panels to dialog
		add(inputPanel, BorderLayout.CENTER);
		add(addButton, BorderLayout.SOUTH);
	}

	private void addNewInterest() {
		String category = categoryField.getText();
		String name = nameField.getText();

		if (category.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "취미의 카테고리와 이름을 작성해주세요", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
		
		InterestDTO newInterest = new InterestDTO();
		newInterest.setCategory(category);
		newInterest.setName(name);

		try {
			interestDao.addInterest(newInterest);
			JOptionPane.showMessageDialog(this, "새로운 취미가 등록되었습니다.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error adding new interest", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}