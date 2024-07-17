package Matching.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Matching.ui.MainFrameManager;
import Matching.dto.InterestDTO;
import Matching.dto.UserDTO;
import Matching.dto.UserInterestDTO;
import Matching.dao.InterestDAO;
import Matching.dao.UserInterestDAO;

public class InterestPanel extends JPanel {
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField interestField;
	private JButton addInterestButton;
	private UserDTO user;
	private InterestDAO interestDao = new InterestDAO();
	private UserInterestDAO userInterestDao = new UserInterestDAO();

	public InterestPanel(UserDTO user) {
		this.user = user;
//		setLayout(new GridLayout(3, 2));
		setLayout(new BorderLayout());
		tableModel = new DefaultTableModel(new Object[] { "Interest ID", "Category", "Name" }, 0) {
			// table을 더블클릭 시 edit 상태가 되는 것을 방지
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are not editable
			}
		};
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listInterest();
		add(new JScrollPane(table), BorderLayout.CENTER);

		addInterestButton = new JButton("Add Interest");
		addInterestButton.addActionListener(e -> addSelectedInterests());
		add(addInterestButton, BorderLayout.SOUTH);
	}

	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void addSelectedInterests() {
		int[] selectedRows = table.getSelectedRows();
		for (int row : selectedRows) {
			int interestId = (int) tableModel.getValueAt(row, 0);
			UserInterestDTO userInterest = new UserInterestDTO(user.getUserId(), interestId);
			try {
				userInterestDao.addUserInterest(userInterest);
				JOptionPane.showMessageDialog(this, "선택한 관심사가 추가되었습니다.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				if (ex.getMessage().contains("이미 추가한 관심사 입니다.")) {
					JOptionPane.showMessageDialog(this, "이미 추가한 관심사 입니다.", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error adding interest", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void listInterest() {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<InterestDTO> interestList = interestDao.listInterest();

		for (InterestDTO i : interestList) {
			tableModel.addRow(new Object[] { i.getInterestId(), i.getCategory(), i.getName() });
		}
	}

	private void listBook(String searchWord) {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<InterestDTO> interestList = interestDao.listBook(searchWord);

		for (InterestDTO i : interestList) {
			tableModel.addRow(new Object[] { i.getInterestId(), i.getCategory(), i.getName() });
		}
	}

}
