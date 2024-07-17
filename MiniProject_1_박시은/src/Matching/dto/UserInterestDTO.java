package Matching.dto;

public class UserInterestDTO {
	private String userId;
    private int interestId;
    
    public UserInterestDTO() {}
    
	public UserInterestDTO(String userId, int interestId) {
		super();
		this.userId = userId;
		this.interestId = interestId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterest_Id(int interestId) {
		this.interestId = interestId;
	}
    
    
    
}
