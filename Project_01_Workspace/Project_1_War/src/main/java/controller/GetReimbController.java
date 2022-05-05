package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ReimbursementDao;
import dao.ReimbursementStatusDao;
import model.ReimbursementStatus;
import model.RequestObject;
import model.User;
import service.DbService;

/**
 * This is in charge of taking in AJAX requests and returning JSON.
 * 
 * @author darkm
 *
 */
public class GetReimbController {
	// values
	private ReimbursementDao reimbDao = null;
	private ReimbursementStatusDao reimbStatusDao = null;
	private ReimbursementStatus reimbStatus = null;
	private RequestObject reqObj = null;
	private User user = null;
	private DbService dbService = null;
	private JsonController jsonControl = null;
	
	// constructors
	public GetReimbController() {
		setReimbDao(new ReimbursementDao());
		setReimbStatusDao(new ReimbursementStatusDao());
		setReimbStatus(new ReimbursementStatus());
		setReqObj(new RequestObject());
		setUser(new User());
		setDbService(new DbService());
		setJsonControl(new JsonController());
	}
	public GetReimbController(ReimbursementDao reimbDao, ReimbursementStatusDao reimbStatusDao,
			ReimbursementStatus reimbStatus, RequestObject reqObj, User user, DbService dbService, JsonController jsonControl) {
		super();
		this.reimbDao = reimbDao;
		this.reimbStatusDao = reimbStatusDao;
		this.reimbStatus = reimbStatus;
		this.reqObj = reqObj;
		this.user = user;
		this.dbService = dbService;
		this.jsonControl = jsonControl;
	}
	
	// getters and setters
	public ReimbursementDao getReimbDao() {
		return reimbDao;
	}
	public void setReimbDao(ReimbursementDao reimbDao) {
		this.reimbDao = reimbDao;
	}
	public ReimbursementStatusDao getReimbStatusDao() {
		return reimbStatusDao;
	}
	public void setReimbStatusDao(ReimbursementStatusDao reimbStatusDao) {
		this.reimbStatusDao = reimbStatusDao;
	}
	public ReimbursementStatus getReimbStatus() {
		return reimbStatus;
	}
	public void setReimbStatus(ReimbursementStatus reimbStatus) {
		this.reimbStatus = reimbStatus;
	}
	public RequestObject getReqObj() {
		return reqObj;
	}
	public void setReqObj(RequestObject reqObj) {
		this.reqObj = reqObj;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public DbService getDbService() {
		return dbService;
	}
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	public JsonController getJsonControl() {
		return jsonControl;
	}
	public void setJsonControl(JsonController jsonControl) {
		this.jsonControl = jsonControl;
	}
	
	// Get all
	public void getAllReimbs(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getDbService().getAllReimbursements(req, resp);
		getJsonControl().setReimbDao(getDbService().getReimbDao());
		getJsonControl().returnAllReimbJson(resp, "All reimbursements retrieved!");
	}
	
	// Get by Status
	public void getReimbsByStatus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// HTML and JS are handling validation.
		// So we should NEVER get an empty ID.
		// This is holding all of our request data
		ObjectMapper mapper = new ObjectMapper();
		RequestObject reqData = mapper.readValue(req.getInputStream(), RequestObject.class);
		HttpSession mySession = req.getSession();
		setReimbStatusDao( (ReimbursementStatusDao) mySession.getAttribute("allReimbStatuses") );
		ReimbursementStatus reimbStatus = getReimbStatusDao().getById(reqData.getFilterId());
		if (reimbStatus != null) {
			String ourStatus = reimbStatus.getStatus();
			getDbService().getReimbursementsByStatus(req, resp, reimbStatus.getId());
			getJsonControl().setAllReimbs( (getDbService().getReimbDao().getAll()) );
			getJsonControl().returnAllReimbJson(resp, "Filtering by status \"" + ourStatus + "\"");
		} else {
			// null status, that status does not exist
			getJsonControl().returnErrorJson(resp, "Status Does Not Exist");
		}
	} // end method

	// Get by Author
	public void getReimbsByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// The author in this case is just our current user
		HttpSession mySession = req.getSession();
		User currentUser = (User) mySession.getAttribute("user");

		// get by author using this user's ID
		getDbService().getReimbursementsByAuthor(req, resp, currentUser.getId());
		getJsonControl().setAllReimbs(getDbService().getReimbDao().getAll());
		getJsonControl().returnAllReimbJson(resp, "User reimbursements retrieved!");
	} // end method

}
