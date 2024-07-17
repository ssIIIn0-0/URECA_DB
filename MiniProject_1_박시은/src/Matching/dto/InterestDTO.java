package Matching.dto;

public class InterestDTO {
	private int interestId;
    private String category;
    private String name;
    
    public InterestDTO() {}
    
	public InterestDTO(int interestId, String category, String name) {
		super();
		this.interestId = interestId;
		this.category = category;
		this.name = name;
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
