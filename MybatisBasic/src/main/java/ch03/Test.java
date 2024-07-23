package ch03;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import ch03.config.MyBatisConfig;
import ch03.dao.BookDao;

// config : java
// sql(mapper) : java
public class Test {
	public static void main(String[] args) throws Exception {
		// Java 설정 이용
		SqlSessionFactory sqlSessionFactory = new MyBatisConfig().getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();

		// SQL(mapper) + Java
		// Java 의 어떤 메소드(BookDao) 가 호출되면 mapper 의 어떤 sql 이 수행되는지 연결
		BookDao bookDao = session.getMapper(BookDao.class); // book-mapper.xml 과 BookDao 가 연결

		// 목록
//      {
//          List<BookDto> bookList = bookDao.listBook();
//          for (BookDto bookDto : bookList) {
//              System.out.println(bookDto);
//          }
//      }
		// 상세
//      {
//          BookDto bookDto = bookDao.detailBook(1);
//          System.out.println(bookDto);
//      }

		// 등록
//      {
//          BookDto bookDto = new BookDto(11, "11번째 책", "uplus press", 5000);
//          int ret = bookDao.insertBook(bookDto);
//          System.out.println(ret);
//          session.commit();
//      }
		// 수정
//      {
//          BookDto bookDto = new BookDto(11, "11번째 책 수정", "uplus press 수정", 10000);
//          int ret = bookDao.updateBook(bookDto);
//          System.out.println(ret);
//          session.commit();
//      }
		// 삭제
		{
			int ret = bookDao.deleteBook(11);
			System.out.println(ret);
			session.commit();
		}

		session.close();
	}
}