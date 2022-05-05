package model;

import java.util.ArrayList;

import lombok.Data;

@Data
/**
 * This will be what is sent back from any JSON requests via ObjectMapper. The
 * message is for the header of the table, while the Reimbursement ArrayList is
 * to go through and populate the table.
 * 
 * @author darkm
 */
public class ResponseObject {
	private String head = "";
	private String message = "Oops! I wasn't set!";
	private ArrayList<Reimbursement> reimbs = new ArrayList<Reimbursement>();
	// We don't need to hold just one because the ArrayList CAN just have one reimb.

	/**
	 * For the sake of completion really. Unused.
	 * @param head
	 * @param msg
	 * @param reimbsList
	 */
	public ResponseObject(String head, String msg, ArrayList<Reimbursement> reimbsList) {
		this.head = head;
		this.message = msg;
		this.reimbs.addAll(reimbsList);
	}

	/**
	 * A list of reimbursements and a message is returned. No header.
	 * @param msg
	 * @param reimbsList
	 */
	public ResponseObject(String msg, ArrayList<Reimbursement> reimbsList) {
		this.head = null;
		this.message = msg;
		this.reimbs.addAll(reimbsList);
	}
	
	/**
	 * Just the header and message, no reimbs are placed.
	 * @param head
	 * @param msg
	 */
	public ResponseObject(String head, String msg) {
		this.head = head;
		this.message = msg;
		this.reimbs = null;
	}

	/**
	 * Used to send JUST a message. Useful for error messages.
	 * @param msg
	 */
	public ResponseObject(String msg) {
		this.head = null;
		this.message = msg;
		this.reimbs = null;
	}
}
