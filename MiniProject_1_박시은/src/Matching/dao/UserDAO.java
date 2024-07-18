package Matching.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Matching.dto.ProfileDTO;
import Matching.dto.UserDTO;
import Matching.common.DBManager;

public class UserDAO {

	public int addUser(UserDTO user) {
		int ret = -1;
		String sql = "INSERT INTO Users VALUES (?, ?, ?, ?);";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPassword());

			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

		return ret;
	}

	public UserDTO getUserByID(String id) throws SQLException {
		String sql = "SELECT * FROM Users WHERE user_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				UserDTO user = new UserDTO();
				user.setUserId(rs.getString("user_id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		return null;
	}

	public ProfileDTO getProfileByID(String userId) throws SQLException {
		String sql = "SELECT * FROM profile WHERE userId = ?";

		try (Connection con = DBManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					ProfileDTO profile = new ProfileDTO();
					profile.setUserId(rs.getString("userId"));
					profile.setUserName(rs.getString("userName"));
					profile.setSelfIntroduce(rs.getString("selfIntroduce"));
					return profile;
				}
			}
		}
		return null;
	}

}
