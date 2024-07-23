package app.book.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame; // windows application
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import app.book.dao.BookDao;
import app.book.dto.BookDto;
import app.book.config.MyBatisConfig;

public class BookManager extends JFrame {

	private JTable table; // grid ui component
	private DefaultTableModel tableModel; // grid data
	private JButton searchButton, addButton, editButton, listButton;
	private JTextField searchWordField;

	private BookDao bookDao;

	public BookManager() {

		// bookDao 설정
		SqlSessionFactory sqlSessionFactory = new MyBatisConfig().getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);	// autocommit
		bookDao = session.getMapper(BookDao.class); // book-mapper.xml 과 BookDao 가 연결

		// 화면 UI와 관련된 설정
		setTitle("Book Manager");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 화면 중심에 오도록 설정
		setLocationRelativeTo(null);

		// table
		// 문자열의 배열인데, Object로 나타내는거임
		tableModel = new DefaultTableModel(new Object[] { "Book ID", "Book Name", "Publisher", "Price" }, 0) {
			// table을 더블클릭 시 edit 상태가 되는 것을 방지
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are not editable
			}
		};
		table = new JTable(tableModel);

		// DB 로부터 현재 book 테이블에 있는 데이터를 가져와서 보여준다.
		listBook();

		// search
		Dimension textFieldSize = new Dimension(400, 28); // 검색창의 크기를 결정
		searchWordField = new JTextField();
		searchWordField.setPreferredSize(textFieldSize);
		searchButton = new JButton("검색");

		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("제목 검색"));
		searchPanel.add(searchWordField);
		searchPanel.add(searchButton);

		// button
		addButton = new JButton("등록");
		editButton = new JButton("수정 & 삭제");
		listButton = new JButton("목록");

		// button 2개를 담는 JPanel 객체를 만들고 그 객체를 BookManager에 담는다.
		JPanel buttonPanel = new JPanel(); // default layout : Flow Layout
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(listButton);

		// table, buttonPanel 을 BookManager 에 붙인다.
		// BookManager 의 layout에 따라 결정

		// BookManager 의 layout 설정
		setLayout(new BorderLayout());
		add(searchPanel, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER); // table < scroll pane < jframe
		add(buttonPanel, BorderLayout.SOUTH);

		// button action event 처리
		// 함수형 인터페이스이므로 람다를 사용할 수 있다.
		searchButton.addActionListener(e -> {
			String searchWord = searchWordField.getText();
			if (!searchWord.isBlank()) {
				listBook(searchWord);
			}
		});

		addButton.addActionListener(e -> {
//			System.out.println("addButton!!");
			// AddBookDialog를 띄운다.
			AddBookDialog addDialog = new AddBookDialog(this, this.tableModel);
			addDialog.setVisible(true);
		});

		editButton.addActionListener(e -> {
			// table에 선택된 row가 있으면 EditBookDialog를 띄운다.
			// table에 선택된 row
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				EditBookDialog editDialog = new EditBookDialog(this, this.tableModel, selectedRow);
				editDialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "도서를 선택하세요.");
			}
		});

		listButton.addActionListener(e -> listBook());

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// double click
				if (e.getClickCount() == 2) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow >= 0) {
						EditBookDialog editDialog = new EditBookDialog(BookManager.this, tableModel, selectedRow);
						editDialog.setVisible(true);
					}
				}
			}
		});
	}

	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void listBook() {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<BookDto> bookList = bookDao.listBook();

		for (BookDto book : bookList) {
			tableModel.addRow(
					new Object[] { book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice() });
		}
	}

	private void listBook(String searchWord) {
		// 현재 tableModel 을 정리하고
		clearTable();

		List<BookDto> bookList = bookDao.listBookSearch(searchWord);

		for (BookDto book : bookList) {
			tableModel.addRow(
					new Object[] { book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice() });
		}
	}

	BookDto detailBook(int bookId) {
		return bookDao.detailBook(bookId);
	}

	void insertBook(BookDto book) {
		int ret = bookDao.insertBook(book);
		if (ret == 1) {
			listBook();
		}
	}

	void updateBook(BookDto book) {
		int ret = bookDao.updateBook(book);
		if (ret == 1) {
			listBook();
		}
	}

	void deleteBook(int bookId) {
		int ret = bookDao.deleteBook(bookId);
		if (ret == 1) {
			listBook();
		}
	}

	public static void main(String[] args) {
		// main() thread 와 별개로 별도의 thread 로 화면을 띄운다.
		// thread 처리를 간단히 해주는 utility method 제공
		// invokeLater(thread 객체 <- runnable interface를 구현한 <- runnable interface가
		// functional interface)
		// 결과적으로 invokeLAter(lambda 식 표현 객체)
		SwingUtilities.invokeLater(() -> {
//			BookManager bookManager = new BookManager();
//			bookManager.setVisible(true);
			new BookManager().setVisible(true);
		});

	}
}
