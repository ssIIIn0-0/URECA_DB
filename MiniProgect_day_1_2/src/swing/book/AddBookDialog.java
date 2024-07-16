package swing.book;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddBookDialog extends JDialog{
	private JTextField bookIdField, bookNameField, publisherField, priceField;
	private JButton addButton;
	
	
	public AddBookDialog(DefaultTableModel tableModel) {
		setSize(300, 200);
		setLayout(new GridLayout(5, 2));
		
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
		});
	}
}











