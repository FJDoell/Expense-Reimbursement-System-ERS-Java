package model;

import lombok.Data;

@Data
public class ReimbursementType {
	private int typeId = 0;
	private String type = null;
	
	public ReimbursementType() {
		
	}

	public ReimbursementType(int typeId, String type) {
		super();
		this.typeId = typeId;
		this.type = type;
	}
	
}
