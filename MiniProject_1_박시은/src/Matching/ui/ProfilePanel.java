package Matching.ui;

import javax.swing.*;
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

		// User name
		JPanel namePanel = new JPanel(new BorderLayout());
		namePanel.add(new JLabel("Name    "), BorderLayout.WEST);
		namePanel.add(new JLabel(user.getName()), BorderLayout.CENTER);
		infoPanel.add(namePanel);

		// Interest categories
		JPanel categoryPanel = new JPanel(new BorderLayout());
		categoryPanel.add(new JLabel("Interest Category     "), BorderLayout.WEST);
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
			categoryPanel.add(new JLabel(categoriesString.toString()), BorderLayout.CENTER);
		}
		infoPanel.add(categoryPanel);

		// Interests
		JPanel interestPanel = new JPanel(new BorderLayout());
		interestPanel.add(new JLabel("Interest    "), BorderLayout.WEST);
		if (interests != null) {
			StringBuilder interestNames = new StringBuilder();
			for (InterestDTO interest : interests) {
				if (interestNames.length() > 0) {
					interestNames.append(" ,  ");
				}
				interestNames.append(interest.getName());
			}
			interestPanel.add(new JLabel(interestNames.toString()), BorderLayout.CENTER);
		}
		infoPanel.add(interestPanel);

		// Self introduction
		JPanel introducePanel = new JPanel(new BorderLayout());
		introducePanel.add(new JLabel("Self Introduce    "), BorderLayout.WEST);
		selfIntroduceArea = new JTextArea(profile != null ? profile.getSelfIntroduce() : "");
		introducePanel.add(new JScrollPane(selfIntroduceArea), BorderLayout.CENTER);
		infoPanel.add(introducePanel);

		saveButton = new JButton("Save");
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