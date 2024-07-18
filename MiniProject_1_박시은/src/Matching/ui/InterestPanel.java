package Matching.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Matching.ui.MainFrameManager;
import Matching.dto.InterestDTO;
import Matching.dto.UserDTO;
import Matching.dto.UserInterestDTO;
import Matching.dao.InterestDAO;
import Matching.dao.UserInterestDAO;

public class InterestPanel extends JPanel {
	private JTable interestTable;
	private JTable userInterestTable;
	private DefaultTableModel interestTableModel;
	private DefaultTableModel userInterestTableModel;
	private JTextField interestField;
	private JButton addInterestButton;
	private JButton deleteInterestButton;
	private JButton newInterestButton;
	private JButton recommendButton;
	private UserDTO user;
	private MainFrameManager parent;
	private InterestDAO interestDao = new InterestDAO();
	private UserInterestDAO userInterestDao = new UserInterestDAO();

	public InterestPanel(UserDTO user, MainFrameManager parent) {
		this.user = user;
		this.parent = parent;
		setLayout(new BorderLayout());

		// Interests Table
		interestTableModel = new DefaultTableModel(new Object[] { "Interest ID", "Category", "Name" }, 0) {
			// table을 더블클릭 시 edit 상태가 되는 것을 방지
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are not editable
			}
		};
		interestTable = new JTable(interestTableModel);
		interestTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listInterest();

		// User Interest Table
		userInterestTableModel = new DefaultTableModel(new Object[] { "User ID", "Category", "Name" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		userInterestTable = new JTable(userInterestTableModel);
		userInterestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUserInterests();

		// 테이블 당 라벨 추가
		JLabel interestLabel = new JLabel("취미 종류", JLabel.CENTER);
		JLabel userInterestLabel = new JLabel("선택한 취미", JLabel.CENTER);

		JPanel interestTablePanel = new JPanel(new BorderLayout());
		interestTablePanel.add(interestLabel, BorderLayout.NORTH);
		interestTablePanel.add(new JScrollPane(interestTable), BorderLayout.CENTER);

		JPanel userInterestTablePanel = new JPanel(new BorderLayout());
		userInterestTablePanel.add(userInterestLabel, BorderLayout.NORTH);
		userInterestTablePanel.add(new JScrollPane(userInterestTable), BorderLayout.CENTER);

		// 테이블 위치 수정
		JPanel tablesPanel = new JPanel(new GridLayout(1, 2));
		tablesPanel.add(new JScrollPane(interestTable));
		tablesPanel.add(new JScrollPane(userInterestTable));

		// 관심사 추가버튼
		addInterestButton = new JButton("취미 추가하기");
		addInterestButton.addActionListener(e -> addSelectedInterests());

		// 관심사 삭제버튼
		deleteInterestButton = new JButton("취미 삭제하기");
		deleteInterestButton.addActionListener(e -> deleteSelectedInterest());

		// 새로운 관심사 등록버튼
		newInterestButton = new JButton("새로운 취미 등록하기");
		newInterestButton.addActionListener(e -> {
			NewInterestDialog dialog = new NewInterestDialog((JFrame) SwingUtilities.getWindowAncestor(this));
			dialog.setVisible(true);
			listInterest(); // Refresh the interest table after adding a new interest
		});
		
		// 비슷한 관심사를 가진 친구 추천 버튼
		recommendButton = new JButton("비슷한 취미를 가진 친구추천받기");
        recommendButton.addActionListener(e -> parent.showRecommendationPanel(user));

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newInterestButton);
		buttonPanel.add(addInterestButton);
		buttonPanel.add(deleteInterestButton);
		buttonPanel.add(recommendButton);

		add(tablesPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void addSelectedInterests() {
		int[] selectedRows = interestTable.getSelectedRows();
		for (int row : selectedRows) {
			int interestId = (int) interestTableModel.getValueAt(row, 0);
			UserInterestDTO userInterest = new UserInterestDTO(user.getUserId(), interestId);
			try {
				userInterestDao.addUserInterest(userInterest);
				JOptionPane.showMessageDialog(this, "선택한 취미가 추가되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
				listUserInterests(); // 추가된 취미 확인
			} catch (SQLException ex) {
				if (ex.getMessage().contains("이미 추가한 관심사 입니다.")) {
					JOptionPane.showMessageDialog(this, "이미 추가한 취미 입니다.", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error adding interest", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void deleteSelectedInterest() {
		int selectedRow = userInterestTable.getSelectedRow();
		if (selectedRow != -1) {
			String category = (String) userInterestTableModel.getValueAt(selectedRow, 1);
            String name = (String) userInterestTableModel.getValueAt(selectedRow, 2);
            UserInterestDTO userInterest = new UserInterestDTO(user.getUserId(), category, name);
			try {
				userInterestDao.deleteUserInterest(userInterest);
				JOptionPane.showMessageDialog(this, "선택한 취미가 삭제되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
				listUserInterests(); // Refresh user interests table
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error deleting interest", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "삭제할 취미를 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void clearTable() {
		interestTableModel.setRowCount(0);
	}

	private void clearTable(DefaultTableModel tableModel) {
		tableModel.setRowCount(0);
	}

	private void listUserInterests() {
		clearTable(userInterestTableModel);
		try {
			List<UserInterestDTO> userInterestList = userInterestDao.getUserInterests(user.getUserId());
			for (UserInterestDTO ui : userInterestList) {
				userInterestTableModel
						.addRow(new Object[] { ui.getUserId(), ui.getCategory(), ui.getName() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading user interests", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void listInterest() {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<InterestDTO> interestList = interestDao.listInterest();

		for (InterestDTO i : interestList) {
			interestTableModel.addRow(new Object[] { i.getInterestId(), i.getCategory(), i.getName() });
		}
	}

	private void listInterest(String searchWord) {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<InterestDTO> interestList = interestDao.listBook(searchWord);

		for (InterestDTO i : interestList) {
			interestTableModel.addRow(new Object[] { i.getInterestId(), i.getCategory(), i.getName() });
		}
	}

}
