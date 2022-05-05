package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;
import service.DbService;

/**
 * Handles logging in and out. Redirects to an HTML page.
 * 
 * @author darkm
 */
public class Login {
	private DbService myDbService = null;
	private JsonController jsonControl = null;
	private UserDao userDao = null;
	private User user = null;
	
	public Login() {
		setMyDbService(new DbService());
		setJsonControl(new JsonController());
		setUserDao(new UserDao());
		setUser(new User());
	}
	public Login(DbService myDbService, JsonController jsonController, UserDao userDao, User user) {
		super();
		this.myDbService = myDbService;
		this.jsonControl = jsonController;
		this.userDao = userDao;
		this.user = user;
	}
	
	// Getters and setters
	public DbService getMyDbService() {
		return myDbService;
	}
	public void setMyDbService(DbService myDbService) {
		this.myDbService = myDbService;
	}
	public JsonController getJsonControl() {
		return jsonControl;
	}
	public void setJsonControl(JsonController jsonControl) {
		this.jsonControl = jsonControl;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// First, lets get the login info!
		HttpSession mySession = req.getSession();
		
		String myUsername = "";
		String myPassword = "";
		
		if(!req.getParameter("username").equals(null)
		&& !req.getParameter("password").equals(null)) {
			myUsername = req.getParameter("username");
			myPassword = req.getParameter("password");
		}
		System.out.println("This is the params I got passed: " + myUsername + ", " + myPassword);
		
		// Get from session if it exists
		if(mySession.getAttribute("allUsers") == null) {
			// Get by credentials (slightly more efficient than all)
			System.out.println("Getting myDbService user");
			getMyDbService().getUserByCredentials(myUsername, myPassword);
			System.out.println(getMyDbService().getUser().toString());
			setUser(getMyDbService().getUser());
		} else {
			// the session fetch is much faster, get that instead!
			setUserDao( (UserDao) mySession.getAttribute("allUsers") );
			setUser( getUserDao().getByCredentials(myUsername, myPassword) );
		}
		
		// Remember that getByCredentials returns an empty constructor if nothing
		// is found.
		if(!getUser().equals(new User())) {
			// login is valid
			System.out.println("Login is valid");
			mySession.setAttribute("user", getUser());
			// Finally, where are they going based on their type?
			switch(getUser().getUserRoleId()) {
				case 1:
					// This is a Ranger/Employee
					req.getRequestDispatcher("/employee").forward(req, resp);
					break;
				case 2:
					// This is a Chief
					req.getRequestDispatcher("/finance").forward(req, resp);
					break;
				default:
					System.out.println("[Login] Oops! This employee doesn't have a valid type!");
					break;
			}
		} else {
			// login is NOT valid
			System.out.println("Login is INVALID");
			// do NOT set the user value. If it is null, it is invalid by default
			req.getRequestDispatcher("/resources/html/BadLogin.html").forward(req, resp);
		}
		// Only get allUsers if we need it.
		if(mySession.getAttribute("allUsers") == null) {
			System.out.println("Getting all users!");
			// Initialize all static Daos.
			getMyDbService().initialize(req);
			// We need to save this in the session now
			getMyDbService().getAllUsers();
			mySession.setAttribute("allUsers", getMyDbService().getUserDao());
		}
	}

	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		// THIS LOGS OUT
		session.setAttribute("user", null);
		
		// To clear all session data, I will need to restart the server.
		// This is overall more efficient.

		// Return home
		String myPath = "/home";
		req.getRequestDispatcher(myPath).forward(req, resp);
	}
	
	// Get current user and return the head JSON
	public void getCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get user from session.
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("user");
		
		// Set up the head and message
		String head = "Welcome " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!";
		String msg = "Fetching Reimbursements, one moment...";
		
		// Now lets get the head and message
		getJsonControl().returnHeadAndMsgJson(resp, head, msg);
	}

}
