package transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionTest2 {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String insertSql = "insert into customer values (?, ?); ";
		int ret = -1;

		boolean isSuccess = false;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(insertSql);

			// 아래의 작업을 하나의 트랜잭션으로 처리
			con.setAutoCommit(false); // auto commit 속성 해제

			pstmt.setInt(1, 1);
			pstmt.setString(2, "홍길동");
			ret = pstmt.executeUpdate();
			System.out.println(ret);

			pstmt.setInt(1, 2);
			pstmt.setString(2, "이길동");
			ret = pstmt.executeUpdate();
			System.out.println(ret);

			pstmt.setInt(1, 1); // dup 발생
			pstmt.setString(2, "삼길동");
			ret = pstmt.executeUpdate();
			System.out.println(ret);

			pstmt.setInt(1, 4);
			pstmt.setString(2, "사길동");
			ret = pstmt.executeUpdate();
			System.out.println(ret);

			isSuccess = true;

		} catch (SQLException e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				if (isSuccess)
					con.commit();
				else
					con.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}

			DBManager.releaseConnection(pstmt, con);
		}

	}

}