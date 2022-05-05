package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Reimbursement;
import model.RequestObject;
import model.User;
import service.DbService;

/**
 * This is in charge of approving or denying a given reimbursement. appOrDen
 * must be set in the RequestObject sent. I will also need to get WHICH
 * reimbursement they selected somehow. I think if I give each table column that
 * contains the reimb ID an ID of it's own that is based on that reimb...I can
 * accomplish that.
 * 
 * @author darkm
 *
 */
public class AppOrDenReimb {
	private JsonController jsonControl = null;
	private DbService dbService = null;
	private RequestObject reqObj = null;
	private Reimbursement currentReimb = null;
	
	public AppOrDenReimb() {
		setJsonControl(new JsonController());
		setDbService(new DbService());
		setReqObj(new RequestObject());
		setCurrentReimb(new Reimbursement());
	}
	public AppOrDenReimb(JsonController jsonControl, DbService dbService, RequestObject reqObj, Reimbursement currentReimb) {
		super();
		this.jsonControl = jsonControl;
		this.dbService = dbService;
		this.reqObj = reqObj;
		this.currentReimb = currentReimb;
	}
	public AppOrDenReimb(RequestObject reqObj) {
		super();
		setJsonControl(new JsonController());
		setDbService(new DbService());
		this.reqObj = reqObj;
		setCurrentReimb(new Reimbursement());
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
	public Reimbursement getCurrentReimb() {
		return currentReimb;
	}
	public void setCurrentReimb(Reimbursement currentReimb) {
		this.currentReimb = currentReimb;
	}
	

	public void appOrDen(HttpServletRequest req, HttpServletResponse resp)
			throws StreamReadException, DatabindException, IOException, ServletException {
		// first we need the user input
		ObjectMapper mapper = new ObjectMapper();
		RequestObject reqObj = mapper.readValue(req.getInputStream(), RequestObject.class);
		int requestedId = reqObj.getFilterId();

		// We need to get this reimb by that ID
		// Note that it's only unresolved ones, this is more efficient
		getDbService().getUnresolvedReimbursements(req, resp);
		setCurrentReimb( getDbService().getReimbDao().getById(requestedId) );

		if (reqObj.isAppOrDen()) {
			// approve
			approve(req, resp);
		} else {
			// deny
			deny(req, resp);
		}
	}

	private void approve(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// We are updating the given reimbursement
		// Approved is ID 2
		// set resolver to the current user
		getCurrentReimb().setResolver(((User) req.getSession().getAttribute("user")).getId());
		getCurrentReimb().setStatusId(2);

		// Actually update
		getDbService().updateReimb(getCurrentReimb());

		// refresh this reimbResponse to be up to date
		getDbService().getReimbursementById(req, resp, getCurrentReimb().getId());

		getJsonControl().setOurReimb(getDbService().getCurrentReimb());
		// return only the one we modded
		getJsonControl().returnReimbJson(resp, "Reimbursement approved!");
	}

	private void deny(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Denied is ID 3
		// set resolver to the current user
		getCurrentReimb().setResolver(((User) req.getSession().getAttribute("user")).getId());
		getCurrentReimb().setStatusId(3);

		// Actually update
		getDbService().updateReimb(getCurrentReimb());

		// refresh this reimbResponse to be up to date
		getDbService().getReimbursementById(req, resp, getCurrentReimb().getId());

		// return only the one we modded
		getJsonControl().setOurReimb(getDbService().getCurrentReimb());
		getJsonControl().returnReimbJson(resp, "Reimbursement denied!");

	}

}
