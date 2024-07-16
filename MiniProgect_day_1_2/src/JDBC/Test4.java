package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// crud 를 개별 메소드로 구현
// SQLException Handling
// 개별 메소를 파라미터, 리턴을 추가해서 main() 협업 메소드내 하드코딩 제거
// Statement -> PreparedStatement
// select query 처리에 dto 적용
// 전체 dto 적용
// Utility class DBManager
// AutoClosable  delete 메소드 하나 더
public class Test4 {

	public static void main(String[] args) {
		int ret = -1;

//      CustomerDto dto = new CustomerDto(6, "손흥민", "영국 토트넘", "010-1111-1111");
//      ret = insertCustomer(dto);
//      System.out.println(ret);

//      CustomerDto dto = new CustomerDto(6, "손흥민", "한국 서울2", "010-1111-1111");
//      ret = updateCustomer(dto);
//      System.out.println(ret);
//      
//      List<CustomerDto> list = listCustomer();
//      for (CustomerDto dto : list) {
//          System.out.println(dto);
//      }

//      CustomerDto dto = detailCustomer(6);
//      System.out.println(dto);

		ret = deleteCustomer(6);
		System.out.println(ret);
	}

	static int insertCustomer(CustomerDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String insertSql = "insert into customer values (?, ?, ?, ?); "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(insertSql);
			pstmt.setInt(1, dto.getCustId());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getAddress());
			pstmt.setString(4, dto.getPhone());

			ret = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

		return ret;
	}

	static int updateCustomer(CustomerDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String updateSql = "update customer set address = ? where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, dto.getAddress());
			pstmt.setInt(2, dto.getCustId());
			ret = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

		return ret;
	}

	static List<CustomerDto> listCustomer() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CustomerDto> list = new ArrayList<>();

		String selectListSql = "select * from customer ";
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectListSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// row 한 개당 행(컬럼) 값 추출
				CustomerDto dto = new CustomerDto();
				dto.setCustId(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}

		return list;
	}

	static CustomerDto detailCustomer(int custId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		CustomerDto dto = null;

		String selectDetailSql = "select * from customer where custid = ? ";
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(selectDetailSql);
			pstmt.setInt(1, custId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new CustomerDto();
				dto.setCustId(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}

		return dto;
	}

	static int deleteCustomer(int custId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(deleteSql);
			pstmt.setInt(1, custId);
			ret = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

		return ret;
	}

	static int deleteCustomerAutoCloseable(int custId) {

		String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
		int ret = -1;
		// try with resources 블럭안에서 선언 생성된 AutoCloseable 객체는 자동으로 close() 호출된다.
		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(deleteSql);) {
			pstmt.setInt(1, custId);
			ret = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
