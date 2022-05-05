package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Reimbursement;
import model.RequestObject;
import model.User;
import service.DbService;
/**
 * This is in charge of adding a reimbursement.
 * @author darkm
 *
 */
public class InsertReimb {
	private JsonController jsonControl = null;
	private DbService dbService = null;
	private RequestObject reqObj = null;
	private User currentUser = null;
	private Reimbursement reimb = null;
	
	public InsertReimb() {
		setJsonControl(new JsonController());
		setDbService(new DbService());
		setReqObj(new RequestObject());
		setCurrentUser(new User());
		setReimb(new Reimbursement());
	}
	public InsertReimb(JsonController jsonControl, DbService dbService, RequestObject reqObj, User currentUser, Reimbursement reimb) {
		super();
		this.jsonControl = jsonControl;
		this.dbService = dbService;
		this.reqObj = reqObj;
		this.currentUser = currentUser;
		this.reimb = reimb;
	}
	// Getters and Setters
	public JsonController getJsonControl() {
		return jsonControl;
	}
	public void setJsonControl(JsonController jsonControl) {
		this.jsonControl = jsonControl;
	}
	public DbService getDbService() {
		return dbService;
	}
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	public RequestObject getReqObj() {
		return reqObj;
	}
	public void setReqObj(RequestObject reqObj) {
		this.reqObj = reqObj;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	public Reimbursement getReimb() {
		return reimb;
	}
	public void setReimb(Reimbursement reimb) {
		this.reimb = reimb;
	}
	
	public void insertReimb(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// first we need the user request object
		ObjectMapper mapper = new ObjectMapper();
		setReqObj(mapper.readValue(req.getInputStream(), RequestObject.class));
		
		// after that, use the Reimbursement from it to construct one
		setReimb(getReqObj().getReimb());
		
		// author info must be retrieved
		HttpSession mySession = req.getSession();
		setCurrentUser( (User) mySession.getAttribute("user") );
		getReimb().setAuthor(getCurrentUser().getId());
		
		// Insertion itself
		int newId = getDbService().insertReimb(getReimb());
		getDbService().getReimbursementById(req, resp, newId);
		getJsonControl().setOurReimb(getDbService().getCurrentReimb());
		
		// Insert successful!
		getJsonControl().returnReimbJson(resp, "Reimbursement of ID " + newId + " successfully added!");
	}
	
}
