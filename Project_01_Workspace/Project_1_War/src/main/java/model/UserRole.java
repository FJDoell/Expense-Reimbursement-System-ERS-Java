package model;

import lombok.Data;

@Data
public class UserRole {
	private int id = 0;
	private String role = null;
	
	public UserRole() {
		
	}

	public UserRole(int id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	
}
