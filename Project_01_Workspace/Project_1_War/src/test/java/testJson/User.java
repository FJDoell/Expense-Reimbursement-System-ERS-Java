package testJson;

import lombok.Data;

@Data
public class User {
	private int id = 0;
	private String username = null;
	private String password = null;
	private String firstName = null;
	private String lastName = null;
	private String email = null;
	private int userRoleId = 0;
	private String userRoleName = "";
	
	public User() {
		
	}

	public User(int id, String username, String password, String firstName, String lastName, String email,
			int userRoleId) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRoleId = userRoleId;
		if(this.userRoleId == 1) {
			this.userRoleName = "Ranger";
		} else if(this.userRoleId == 2) {
			this.userRoleName = "Chief";
		}
	}
	
}
