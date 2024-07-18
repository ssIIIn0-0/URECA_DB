package Matching.dto;

public class UserInterestDTO {
	private String userId;
    private int interestId;
    private String category;
    private String name;
    
    public UserInterestDTO() {}
    
	public UserInterestDTO(String userId, int interestId) {
		super();
		this.userId = userId;
		this.interestId = interestId;
		this.category = category;
        this.name = name;
	}

	public UserInterestDTO(String userId, String category, String name) {
        this.userId = userId;
        this.category = category;
        this.name = name;
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

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
    
	public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
