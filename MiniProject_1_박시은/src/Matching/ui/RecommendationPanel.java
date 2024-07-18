package Matching.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import Matching.dto.RecommendationDTO;
import Matching.dto.UserDTO;
import Matching.dto.ProfileDTO;
import Matching.dao.RecommendationDAO;
import Matching.dao.UserDAO;

public class RecommendationPanel extends JPanel {
	private RecommendationDAO recommendationDao = new RecommendationDAO();
	private UserDAO userDao = new UserDAO();
	private UserDTO user;

	public RecommendationPanel(UserDTO user) {
		this.user = user;
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(50, 50, 50, 50)); // 여백 추가
		Font fontK = new Font("돋움", Font.BOLD, 20); // 글자 크기 설정

		// 사용자 관심사 로드
		List<String> userInterests = loadUserInterests(user.getUserId());
		String userInterestNames = String.join(", ", userInterests);

		// 추천된 친구들 로드
		List<RecommendationDTO> recommendations = loadRecommendations(user.getUserId());

		// 사용자 관심사 표시
		JLabel userInterestLabel = new JLabel(user.getName() + "님의 관심사는");
		userInterestLabel.setFont(fontK);
		JLabel userInterestsListLabel = new JLabel(userInterestNames + "로 선택해주셨는데요,");
		userInterestsListLabel.setFont(fontK);
		JLabel recommendationLabel = new JLabel(user.getName() + "님과 비슷한 관심사를 가진 친구를 추천드립니다~");
		recommendationLabel.setFont(fontK);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setPreferredSize(new Dimension(400, 100));
		infoPanel.add(userInterestLabel);
		infoPanel.add(userInterestsListLabel);
		infoPanel.add(recommendationLabel);
		// 추천된 친구들 표시
		JPanel recommendationsPanel = new JPanel();
		recommendationsPanel.setLayout(new BoxLayout(recommendationsPanel, BoxLayout.Y_AXIS));

		for (RecommendationDTO recommendation : recommendations) {
			try {
				UserDTO recommendedUser = userDao.getUserByID(recommendation.getRecommendedUserId());
				ProfileDTO recommendedUserProfile = userDao.getProfileByID(recommendation.getRecommendedUserId());
				List<String> recommendedUserInterests = loadUserInterests(recommendation.getRecommendedUserId());
				String recommendedUserInterestNames = String.join(", ", recommendedUserInterests);

				JPanel recommendedUserPanel = new JPanel(new BorderLayout());
				JLabel recommendedUserLabel = new JLabel(recommendedUser.getName() + "님의 프로필");
				recommendedUserLabel.setFont(fontK);
				JTextArea profileArea = new JTextArea(
						"자기소개" + "\n" + (recommendedUserProfile != null ? recommendedUserProfile.getSelfIntroduce() : "없음") + "\n" + "\n" + 
						"관심사" + "\n" + recommendedUserInterestNames);
				profileArea.setFont(fontK);
				profileArea.setEditable(false);
				profileArea.setLineWrap(true);
				profileArea.setWrapStyleWord(true);

				recommendedUserPanel.add(recommendedUserLabel, BorderLayout.NORTH);
				recommendedUserPanel.add(new JScrollPane(profileArea), BorderLayout.CENTER);

				recommendationsPanel.add(recommendedUserPanel);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error loading recommended user profile", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		add(infoPanel, BorderLayout.NORTH);
		add(new JScrollPane(recommendationsPanel), BorderLayout.CENTER);
	}

	private List<String> loadUserInterests(String userId) {
		try {
			return recommendationDao.getRecommendedUserInterests(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading user interests", "Error", JOptionPane.ERROR_MESSAGE);
			return List.of();
		}
	}

	private List<RecommendationDTO> loadRecommendations(String userId) {
		try {
			recommendationDao.generateRecommendations(userId);
			return recommendationDao.getRecommendations(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading recommendations", "Error", JOptionPane.ERROR_MESSAGE);
			return List.of();
		}
	}
}
