package swing.book;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;	// windows application
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class BookManager extends JFrame{
	
	private JTable table;	// grid ui component
	private DefaultTableModel tableModel;	// grid data
	private JButton addButton, editButton;
	
	public BookManager() {
		// 화면  UI와 관련된 설정
		setTitle("Book Manager");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// table
		// 문자열의 배열인데, Object로 나타내는거임
		tableModel = new DefaultTableModel(new Object[] {"Book ID", "Book Name", "Publisher", "Price"}, 0);
		table = new JTable(tableModel);
		
		// button
		addButton = new JButton("Add Book");
		editButton = new JButton("Edit Book");
		
		// button 2개를 담는 JPanel 객체를 만들고 그 객체를 BookManager에 담는다.
		JPanel buttonPanel = new JPanel();	// default layout : Flow Layout
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		
		// table 을 BookManager 에 붙인다.
		// BookManager 의 layout에 따라 결정
		
		// BookManager 의 layout 설정
		setLayout(new BorderLayout());
//		add(table, BorderLayout.CENTER);
		add(new JScrollPane(table), BorderLayout.CENTER);	// table < scroll pane < jframe
		add(buttonPanel, BorderLayout.SOUTH);
		
		// button action event 처리
		// 함수형 인터페이스이므로 람다를 사용할 수 있다.
		addButton.addActionListener(e -> {
//			System.out.println("addButton!!");
			// AddBookDialog를 띄운다.
			AddBookDialog addDialog = new AddBookDialog(tableModel);
			addDialog.setVisible(true);
		});
		
		editButton.addActionListener(new ActionListener(){
			// event 객체를 받아서 처리하는 로직을 가진 객체 <= ActionListener()
			@Override
			public void actionPerformed(ActionEvent e) {
				// 버튼을 누르면 실행되는 부분
				System.out.println("editButton!!");
				
			}
			
		});
	}
	
	public static void main(String[] args) {
		// main() thread 와 별개로 별도의 thread 로 화면을 띄운다.
		// thread 처리를 간단히 해주는 utility method 제공
		// invokeLater(thread 객체 <- runnable interface를 구현한 <- runnable interface가 functional interface)
		// 결과적으로  invokeLAter(lambda 식 표현 객체)
		SwingUtilities.invokeLater(() -> {
//			BookManager bookManager = new BookManager();
//			bookManager.setVisible(true);
			new BookManager().setVisible(true);
		});
		
	}
}
