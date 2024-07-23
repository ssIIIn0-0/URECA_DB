package app.book.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import app.book.dto.BookDto;

// mapper : java method - sql
public interface BookDao {

	@Select("select bookid bookId, bookname bookName, publisher, price from book")
	List<BookDto> listBook();

	@Select("select bookid bookId, bookname bookName, publisher, price from book where bookname like concat('%', #{searchWord}, '%')")
	List<BookDto> listBookSearch(String searchWord);

	@Select("select bookid bookId, bookname bookName, publisher, price from book where bookid = #{bookId}")
	BookDto detailBook(int bookId);

	@Insert("insert into book (bookid, bookname, publisher, price) values ( #{bookId}, #{bookName}, #{publisher}, #{price} )")
	int insertBook(BookDto bookDto);

	@Update("update book " +
			"		   set bookname = #{bookName}," + 
			"		       publisher = #{publisher},"+ 
			"		       price = #{price} " + 
			"		 where bookid = #{bookId}")
	int updateBook(BookDto bookDto);

	@Delete("delete from book where bookid = #{bookId}")
	int deleteBook(int bookId);
}