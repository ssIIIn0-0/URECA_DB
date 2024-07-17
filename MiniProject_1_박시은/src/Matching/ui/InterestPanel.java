package Matching.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Matching.dto.InterestDTO;
import Matching.dto.UserDTO;
import Matching.ui.MainFrameManager;
import Matching.dao.InterestDAO;

public class InterestPanel extends JPanel{
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField interestField;
    private JButton addInterestButton;
    private InterestDAO interestDao = new InterestDAO();
    
	
	public InterestPanel(MainFrameManager parent) {
		//setTitle("Get your Interest Dialog");
		setLayout(new GridLayout(3, 2));
		tableModel = new DefaultTableModel(new Object[] { "Interest ID", "Category", "Name" }, 0) {
			// table을 더블클릭 시 edit 상태가 되는 것을 방지
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are not editable
			}
		};
		table = new JTable(tableModel);
		listInterest();
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void listInterest() {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<InterestDTO> interestList = interestDao.listInterest();

		for (InterestDTO i : interestList) {
			tableModel.addRow(
					new Object[] { i.getInterestId(), i.getCategory(), i.getName()});
		}
	}

	private void listBook(String searchWord) {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<InterestDTO> interestList = interestDao.listBook(searchWord);

		for (InterestDTO i : interestList) {
			tableModel.addRow(
					new Object[] { i.getInterestId(), i.getCategory(), i.getName()});
		}
	}

}
