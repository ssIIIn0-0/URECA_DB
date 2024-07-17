package Matching.ui;

import javax.swing.*;
import java.awt.*;
import Matching.dto.UserDTO;

public class RecommendationPanel extends JPanel {
    private UserDTO user;

    public RecommendationPanel(UserDTO user) {
        this.user = user;
        setLayout(new BorderLayout());

        JLabel label = new JLabel("추천 친구 목록", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        // 여기에서 추천 친구 목록을 추가합니다.
    }
}