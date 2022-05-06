package model;

import java.sql.Date;

import lombok.Data;

@Data
public class Reimbursement {
	private int id = 0;
	private int amount = 0;
	private String submitted = null;
	private String resolved = null;
	private String description = null;
	private int author = 0;
	private String authorName = "";
	private int resolver = 0;
	private String resolverName = "";
	private int statusId = 1;
	private String statusName = "";
	private int typeId = 0;
	private String typeName = "";
	
	public Reimbursement() {
		
	}
	
	// This is what our user inputs for inserting. After this we need the other info
	public Reimbursement(int amount, String description, int typeId) {
		super();
		this.amount = amount;
		this.description = description;
		this.typeId = typeId;
		this.statusId = 1;
		// Before this is ready for DB, we still need after construction
		// the following:
		// the author
		// the status name
		// the type name
	}
	
	public Reimbursement(int amount, Date submitted, int author, int statusId, int typeId) {
		// no need for id in this constructor, it is set after inserting
		this.amount = amount;
		if(submitted != null) {
			this.submitted = submitted.toString();
		}
		this.author = author;
		this.statusId = statusId;
		this.typeId = typeId;
		// we can set the names and such in the DbService
	}

	public Reimbursement(int id, int amount, Date submitted, Date resolved, String description, int author,
			int resolver, int statusId, int typeId) {
		super();
		this.id = id;
		this.amount = amount;
		if(submitted != null) {
			this.submitted = submitted.toString();
		}
		if(resolved != null) {
			this.resolved = resolved.toString();
		}
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
		// we can set the names and such in the DbService
	}
	
	public Reimbursement(int id, int amount, String submitted, String resolved, String description, int author,
			int resolver, int statusId, int typeId) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
		// we can set the names and such in the DbService
	}	

}
