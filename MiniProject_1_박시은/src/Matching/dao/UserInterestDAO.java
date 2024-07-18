package Matching.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Matching.dto.InterestDTO;
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

	public void deleteUserInterest(UserInterestDTO userInterest) throws SQLException {
        String sql = "DELETE FROM user_interests WHERE user_id = ? AND interest_id = (SELECT interest_id FROM interests WHERE category = ? AND name = ?)";

        try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userInterest.getUserId());
            pstmt.setString(2, userInterest.getCategory());
            pstmt.setString(3, userInterest.getName());
            pstmt.executeUpdate();
        }
    }

	public List<UserInterestDTO> getUserInterests(String userId) throws SQLException {
		List<UserInterestDTO> userInterests = new ArrayList<>();
		String sql = "SELECT ui.user_id, i.category, i.name FROM user_interests ui INNER JOIN interests i ON ui.interest_id = i.interest_id WHERE ui.user_id = ?";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					UserInterestDTO userInterest = new UserInterestDTO();
					userInterest.setUserId(rs.getString("user_id"));
					userInterest.setCategory(rs.getString("category"));
					userInterest.setName(rs.getString("name"));
					userInterests.add(userInterest);
				}
			}
		}
		return userInterests;
	}

}