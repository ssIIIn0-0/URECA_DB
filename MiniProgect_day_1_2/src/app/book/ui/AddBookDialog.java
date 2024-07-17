package app.book.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddBookDialog extends JDialog{
	private JTextField bookIdField, bookNameField, publisherField, priceField;
	private JButton addButton;
	
	
	public AddBookDialog(JFrame parent, DefaultTableModel tableModel) {
		setTitle("Book Add Dialog");
		setSize(300, 200);
		setLayout(new GridLayout(5, 2));
		setLocationRelativeTo(parent);	// 부모에 맞게 위치 조정
		
		// field
		bookIdField = new JTextField();
		bookNameField = new JTextField();
		publisherField = new JTextField();
		priceField = new JTextField();
		
		// button
		addButton = new JButton("Add");
		
		// add field with label
		add(new JLabel("Book ID"));
		add(bookIdField);
		add(new JLabel("Book Name"));
		add(bookNameField);
		add(new JLabel("Publisher"));
		add(publisherField);
		add(new JLabel("Price"));
		add(priceField);
		add(new JLabel(""));
		add(addButton);
		
		// add button actionListner
		addButton.addActionListener(e -> {
			String bookId = bookIdField.getText();
			String bookName = bookNameField.getText();
			String publisher = publisherField.getText();
			String price = priceField.getText();
			
			tableModel.addRow(new Object[] {bookId, bookName, publisher, price});
			
			dispose();	// 실행 후 창 자동 닫기
		});
	}
}











