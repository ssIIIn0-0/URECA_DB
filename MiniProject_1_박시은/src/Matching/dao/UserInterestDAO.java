package Matching.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Matching.dto.UserInterestDTO;
import Matching.common.DBManager;

public class UserInterestDAO {
	public void addUserInterest(UserInterestDTO userInterest) throws SQLException {
		String sql = "INSERT INTO user_interests (user_id, interest_id) VALUES (?, ?)";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userInterest.getUserId());
			pstmt.setInt(2, userInterest.getInterestId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // MySQL error code for duplicate entry
				throw new SQLException("이미 추가한 관심사 입니다.", e);
			} else {
				throw e;
			}
		}
	}
}