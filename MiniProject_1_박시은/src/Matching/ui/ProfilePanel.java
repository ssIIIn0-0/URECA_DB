package Matching.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Matching.dto.ProfileDTO;
import Matching.dto.UserDTO;
import Matching.dto.InterestDTO;
import Matching.dao.ProfileDAO;

public class ProfilePanel extends JPanel {
	private JTextArea selfIntroduceArea;
	private JButton saveButton;
	private ProfileDAO profileDao = new ProfileDAO();
	private UserDTO user;
	private MainFrameManager parent;

	public ProfilePanel(UserDTO user, MainFrameManager parent) {
		this.user = user;
		this.parent = parent;
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(30, 50, 20, 50)); // 여백 추가
		Font fontE = new Font("Arial", Font.BOLD, 20); // 글자 크기 설정
		Font fontK = new Font("돋움", Font.BOLD, 20); // 글자 크기 설정

		// Fetch existing profile data
		ProfileDTO profile = null;
		List<InterestDTO> interests = null;
		try {
			profile = profileDao.getProfile(user.getUserId());
			interests = profileDao.getUserInterests(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading profile", "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Panel for user information
		JPanel infoPanel = new JPanel(new GridLayout(4, 1));
		infoPanel.setPreferredSize(new Dimension(600, 300)); // 패널의 선호 크기 설정
		LineBorder lborder = new LineBorder(Color.GRAY, 2);
		Border border = new TitledBorder(lborder, "PROFILE");
		((TitledBorder) border).setTitleFont(new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, 30));
		infoPanel.setBorder(border);

		// User name
		JPanel namePanel = new JPanel(new BorderLayout());
		JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
		nameLabel.setFont(fontE);
		nameLabel.setPreferredSize(new Dimension(200, 50));
		namePanel.add(nameLabel, BorderLayout.WEST);
		
		JLabel userNameLabel = new JLabel(user.getName(), JLabel.CENTER);
		userNameLabel.setFont(fontK);
		userNameLabel.setPreferredSize(new Dimension(300, 50));
		namePanel.add(userNameLabel, BorderLayout.CENTER);
		infoPanel.add(namePanel);

		// Interest categories
		JPanel categoryPanel = new JPanel(new BorderLayout());
		JLabel categoryLabel = new JLabel("Interest Category", JLabel.CENTER);
		categoryLabel.setFont(fontE);
		categoryLabel.setPreferredSize(new Dimension(200, 50));
		categoryPanel.add(categoryLabel, BorderLayout.WEST);
		if (interests != null) {
			Set<String> categories = new HashSet<>();
			StringBuilder categoriesString = new StringBuilder();
			for (InterestDTO interest : interests) {
				if (categories.add(interest.getCategory())) {
					if (categoriesString.length() > 0) {
						categoriesString.append(" ,  ");
					}
					categoriesString.append(interest.getCategory());
				}
			}
			JLabel categoriesValueLabel = new JLabel(categoriesString.toString(), JLabel.CENTER);
			categoriesValueLabel.setFont(fontK);
			categoriesValueLabel.setPreferredSize(new Dimension(300, 50));
			categoryPanel.add(categoriesValueLabel, BorderLayout.CENTER);
		}
		infoPanel.add(categoryPanel);

		// Interests
		JPanel interestPanel = new JPanel(new BorderLayout());
		JLabel interestLabel = new JLabel("Interest", JLabel.CENTER);
		interestLabel.setFont(fontE);
		interestLabel.setPreferredSize(new Dimension(200, 50));
		interestPanel.add(interestLabel, BorderLayout.WEST);
		if (interests != null) {
			StringBuilder interestNames = new StringBuilder();
			for (InterestDTO interest : interests) {
				if (interestNames.length() > 0) {
					interestNames.append(" ,  ");
				}
				interestNames.append(interest.getName());
			}
			JLabel interestValueLabel = new JLabel(interestNames.toString(), JLabel.CENTER);
			interestValueLabel.setFont(fontK);
			interestValueLabel.setPreferredSize(new Dimension(300, 50));
			interestPanel.add(interestValueLabel, BorderLayout.CENTER);
		}
		infoPanel.add(interestPanel);

		// Self introduction
		JPanel introducePanel = new JPanel(new BorderLayout());
		JLabel introduceLabel = new JLabel("Self Introduce", JLabel.CENTER);
		introduceLabel.setFont(fontE);
		introduceLabel.setPreferredSize(new Dimension(200, 50));
		introducePanel.add(introduceLabel, BorderLayout.WEST);
		selfIntroduceArea = new JTextArea(profile != null ? profile.getSelfIntroduce() : "");
		selfIntroduceArea.setFont(fontK);
		selfIntroduceArea.setPreferredSize(new Dimension(300, 30));
		introducePanel.add(new JScrollPane(selfIntroduceArea), BorderLayout.CENTER);
		infoPanel.add(introducePanel);

		saveButton = new JButton("Save");
		saveButton.setFont(fontE);
		saveButton.addActionListener(e -> saveProfile());

		add(infoPanel, BorderLayout.CENTER);
		add(saveButton, BorderLayout.SOUTH);
	}

	private void saveProfile() {
		ProfileDTO profile = new ProfileDTO();
		profile.setUserId(user.getUserId());
		profile.setUserName(user.getName());
		profile.setSelfIntroduce(selfIntroduceArea.getText());

		try {
			profileDao.insertOrUpdateProfile(profile);
			int result = JOptionPane.showConfirmDialog(this, "프로필을 생성했습니다. \n 친구추천을 받아보겠습니까?", "Success",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				parent.showRecommendationPanel(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving profile", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
