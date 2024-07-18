package Matching.dto;

public class ProfileDTO {
	private String userId;
	private String userName;
    private String selfIntroduce;
    
    public ProfileDTO() {}
    
	public ProfileDTO(String userId, String userName, String selfIntroduce) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.selfIntroduce = selfIntroduce;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSelfIntroduce() {
		return selfIntroduce;
	}

	public void setSelfIntroduce(String selfIntroduce) {
		this.selfIntroduce = selfIntroduce;
	}
	
	
    
    
}
