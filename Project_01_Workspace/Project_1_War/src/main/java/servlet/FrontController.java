package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Master Servlet, simply takes in a broad range of URIs (file paths) and then
 * passes the request and response to the Dispatcher. This must be separate for
 * say, security checks.
 */
// http://localhost:9003/Project_1_War/
@WebServlet(name = "FrontController", urlPatterns = { "/home", "/login", "/logout", "/employee", "/employee/*", "/finance",
		"/finance/*", "/json/*" })
public class FrontController extends HttpServlet {
	Dispatcher dispatch = null;
	public FrontController() {
		setDispatch(new Dispatcher());
	}
	public FrontController(Dispatcher dispatch) {
		setDispatch(dispatch);
	}
	public Dispatcher getDispatch() {
		return dispatch;
	}
	public void setDispatch(Dispatcher dispatch) {
		this.dispatch = dispatch;
	}

	private static final long serialVersionUID = 1L;

	// Check login method
	protected boolean isValid(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession mySession = req.getSession();
		String path = req.getRequestURI();
		System.out.println("path: " + path);

		// if they try to access other stuff besides home and login
		if (!(path.equals("/Project_1_War/login"))
			&& !(path.equals("/Project_1_War/home"))
			&& !(path.equals("/Project_1_War/logout"))) {
			// user doesn't exist so it is invalid by default
			if ((mySession.getAttribute("user") == null) || ((User) mySession.getAttribute("user") == new User())) {
				System.out.println("I'M GOING HOME IN FRONT CONTROLLER");
				// send them home
				return false;
			} else {
				// user exists in session. That means that they are valid.
				User temp = (User) mySession.getAttribute("user");
				// First of all, is this a JSON access? Any account can access those.
				if(path.equals("/Project_1_War/json/getCurrentUser")) {
					// Trying to access a json data method and they're logged
					// in? This is fine.
					return true;
				}
				// now to check where they're going; Finance manager or Employee?
				else if(path.equals("/Project_1_War/employee")
					|| path.equals("/Project_1_War/employee/")
					|| path.equals("/Project_1_War/employee/getReimbsByAuthor")
					|| path.equals("/Project_1_War/employee/addReimb")) {
					// Trying to access employee, are they an Employee?
					if(temp.getUserRoleId() == 1) {
						System.out.println("This is an employee and valid, come on in!");
						return true;
					}
					System.out.println("This isn't an employee, I'm going home!");
					return false;
				} // end accessing employee if
				else if(path.equals("/Project_1_War/finance")
					|| path.equals("/Project_1_War/finance/getAllReimbs")
					|| path.equals("/Project_1_War/finance/getReimbsByStatus")
					|| path.equals("/Project_1_War/finance/getReimbsByType")
					|| path.equals("/Project_1_War/finance/appOrDen")) {
					// Trying to access finance manager page,
					// are they a finance manager?
					if(temp.getUserRoleId() == 2) {
						System.out.println("This is a manager and valid, come on in!");
						return true;
					}
					System.out.println("This isn't a manager, I'm going home!");
					return false;
				} else {
					// This isn't a valid type!
					System.out.println("This isn't a valid employee type! (Front Controller)");
					return false;
				}
			} // end else if exists
		} // end NOT public page
		// Login and logout are public pages, so let them through otherwise
		System.out.println("FRONT CONTROLLER PASSED");
		return true;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("IN OUR MASTER SERVLET: doGet");
		if (isValid(req, resp)) {
			getDispatch().route(req, resp); // HERE I am offloading my work to another entity
		} 
		else {
			req.getRequestDispatcher("/resources/html/InvalidLogin.html").forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
