package Matching.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Matching.dto.InterestDTO;
import Matching.common.DBManager;

public class InterestDAO {

	public List<InterestDTO> listInterest(){
		List<InterestDTO> list = new ArrayList<>();

		String sql = "select * from interests; ";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				InterestDTO interest = new InterestDTO();
				interest.setInterestId(rs.getInt("interest_id"));
				interest.setCategory(rs.getString("category"));
				interest.setName(rs.getString("name"));
				list.add(interest);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

		return list;
	}

	public List<InterestDTO> listBook(String searchWord) {
		List<InterestDTO> list = new ArrayList<>();

		String sql = "select * from interests where name like ?; "; // % 사용 X

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				InterestDTO interest = new InterestDTO();
				interest.setInterestId(rs.getInt("interest_id"));
				interest.setCategory(rs.getString("category"));
				interest.setName(rs.getString("name"));
				list.add(interest);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}

		return list;
	}
}
