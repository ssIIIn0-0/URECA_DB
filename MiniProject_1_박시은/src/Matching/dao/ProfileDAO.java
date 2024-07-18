package Matching.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Matching.common.DBManager;
import Matching.dto.InterestDTO;
import Matching.dto.ProfileDTO;

public class ProfileDAO {

	public void insertOrUpdateProfile(ProfileDTO profile) throws SQLException {
		String sql = "INSERT INTO profile (userId, userName, selfIntroduce) VALUES (?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE selfIntroduce = VALUES(selfIntroduce)";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, profile.getUserId());
			pstmt.setString(2, profile.getUserName());
			pstmt.setString(3, profile.getSelfIntroduce());
			pstmt.executeUpdate();
		}
	}

	public ProfileDTO getProfile(String userId) throws SQLException {
		String sql = "SELECT * FROM profile WHERE userId = ?";
		ProfileDTO profile = null;

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					profile = new ProfileDTO();
					profile.setUserId(rs.getString("userId"));
					profile.setUserName(rs.getString("userName"));
					profile.setSelfIntroduce(rs.getString("selfIntroduce"));
				}
			}
		}
		return profile;
	}

	public List<InterestDTO> getUserInterests(String userId) throws SQLException {
		String sql = "SELECT i.category, i.name FROM user_interests ui "
				+ "INNER JOIN interests i ON ui.interest_id = i.interest_id " + "WHERE ui.user_id = ?";
		List<InterestDTO> interests = new ArrayList<>();

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					InterestDTO interest = new InterestDTO();
					interest.setCategory(rs.getString("category"));
					interest.setName(rs.getString("name"));
					interests.add(interest);
				}
			}
		}
		return interests;
	}

	public void deleteProfile(String userId) throws SQLException {
		String sql = "DELETE FROM profile WHERE userId = ?";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		}
	}

}