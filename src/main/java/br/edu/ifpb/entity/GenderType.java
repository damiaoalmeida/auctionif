package br.edu.ifpb.entity;

public enum GenderType {
	MALE("Male"), 
	FEMALE("Female"), 
	OTHER("Other"), 
	NOT_SPECIFY("Not Specify");
	
	private final String displatyName;
	
	GenderType(String displayName){
		this.displatyName = displayName;
	}
	
	public String getDisplayName() {
		return displatyName;
	}
}