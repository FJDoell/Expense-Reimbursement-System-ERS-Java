package model;

import lombok.Data;

@Data
public class ReimbursementStatus {
	private int id = 0;
	private String status = null;
	
	public ReimbursementStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}

	public ReimbursementStatus() {
	}
	
}
