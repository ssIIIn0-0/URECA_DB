package Matching.dto;

public class RecommendationDTO {
	private String userId;
	private String recommendedUserId;
	private int recomScore;

	public RecommendationDTO() {}
	
	public RecommendationDTO(String userId, String recommendedUserId, int recomScore) {
		super();
		this.userId = userId;
		this.recommendedUserId = recommendedUserId;
		this.recomScore = recomScore;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRecommendedUserId() {
		return recommendedUserId;
	}

	public void setRecommendedUserId(String recommendedUserId) {
		this.recommendedUserId = recommendedUserId;
	}

	public int getRecomScore() {
		return recomScore;
	}

	public void setRecomScore(int recomScore) {
		this.recomScore = recomScore;
	}
}
