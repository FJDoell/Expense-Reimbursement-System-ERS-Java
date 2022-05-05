package model;

import lombok.Data;

@Data
/**
 * This is what the getReimbsByX AJAX requests will be converted to
 * with our handy ObjectMapper. filterId can be used for BOTH
 * types and status.
 * @author darkm
 *
 */
public class RequestObject {
	private int filterId = 0;
	private boolean appOrDen = false;
	private Reimbursement reimb = null;
	
	public RequestObject() {
		super();
	}
	public RequestObject(int filterId, boolean appOrDen, Reimbursement reimb) {
		super();
		this.filterId = filterId;
		this.appOrDen = appOrDen;
		this.reimb = reimb;
	}
}
