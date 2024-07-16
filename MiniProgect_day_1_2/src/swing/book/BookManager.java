package swing.book;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;	// windows application
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
		
		// table 을 BookManager 에 붙인다.
		// BookManager 의 layout에 따라 결정
		
		// BookManager 의 layout 설정
		setLayout(new BorderLayout());
//		add(table, BorderLayout.CENTER);
		add(new JScrollPane(table), BorderLayout.CENTER);	// table < scroll pane < jframe
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
