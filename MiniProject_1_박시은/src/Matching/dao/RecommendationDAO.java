package Matching.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Matching.common.DBManager;
import Matching.dto.RecommendationDTO;

public class RecommendationDAO {

	public void generateRecommendations(String userId) throws SQLException {
		String sql = "INSERT INTO recommendations (user_id, recommended_user_id, recomScore) "
				+ "SELECT ?, ui2.user_id, COUNT(*) as score " + "FROM user_interests ui1 "
				+ "JOIN user_interests ui2 ON ui1.interest_id = ui2.interest_id "
				+ "WHERE ui1.user_id = ? AND ui2.user_id != ? " + "GROUP BY ui2.user_id "
				+ "ON DUPLICATE KEY UPDATE recomScore = VALUES(recomScore)";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.executeUpdate();
		}
	}

	public List<RecommendationDTO> getRecommendations(String userId) throws SQLException {
		List<RecommendationDTO> recommendations = new ArrayList<>();
		String sql = "SELECT * FROM recommendations WHERE user_id = ? ORDER BY recomScore DESC";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					RecommendationDTO recommendation = new RecommendationDTO();
					recommendation.setUserId(rs.getString("user_id"));
					recommendation.setRecommendedUserId(rs.getString("recommended_user_id"));
					recommendation.setRecomScore(rs.getInt("recomScore"));
					recommendations.add(recommendation);
				}
			}
		}
		return recommendations;
	}

	public List<String> getRecommendedUserInterests(String recommendedUserId) throws SQLException {
		List<String> interests = new ArrayList<>();
		String sql = "SELECT i.name FROM user_interests ui " + "JOIN interests i ON ui.interest_id = i.interest_id "
				+ "WHERE ui.user_id = ?";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, recommendedUserId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					interests.add(rs.getString("name"));
				}
			}
		}
		return interests;
	}
}
