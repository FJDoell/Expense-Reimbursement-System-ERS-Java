package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AppOrDenReimb;
import controller.GetReimbController;
import controller.InsertReimb;
import controller.Login;

/**
 * The Dispatcher takes the passed request and response from
 * the front controller and then figures out what controllers
 * to use from there.
 * A Controller then calls the Service layer.
 * So, to recap:
 * FrontController -> Dispatcher ----> Controller -----> Service ---> DAO
 *                    Switch Case      Functionality     DB Link      DB Layer
 */
public class Dispatcher {
	AppOrDenReimb appOrDenReimb = null;
	GetReimbController getReimbController = null;
	InsertReimb insertReimb = null;
	Login login = null;
	
	// empty construct
	Dispatcher() {
		this.appOrDenReimb = new AppOrDenReimb();
		this.getReimbController = new GetReimbController();
		this.insertReimb = new InsertReimb();
		this.login = new Login();
	}
	
	// all args
	public Dispatcher(AppOrDenReimb appOrDenReimb, GetReimbController getReimbController, InsertReimb insertReimb,
			Login login) {
		super();
		this.appOrDenReimb = appOrDenReimb;
		this.getReimbController = getReimbController;
		this.insertReimb = insertReimb;
		this.login = login;
	}
	
	// Getters and setters
	public AppOrDenReimb getAppOrDenReimb() {
		return appOrDenReimb;
	}
	public void setAppOrDenReimb(AppOrDenReimb appOrDenReimb) {
		this.appOrDenReimb = appOrDenReimb;
	}
	public GetReimbController getGetReimbController() {
		return getReimbController;
	}
	public void setGetReimbController(GetReimbController getReimbController) {
		this.getReimbController = getReimbController;
	}
	public InsertReimb getInsertReimb() {
		return insertReimb;
	}
	public void setInsertReimb(InsertReimb insertReimb) {
		this.insertReimb = insertReimb;
	}

	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
	
	// Methods
	public void route(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		switch(req.getRequestURI()) {
		case "/Project_1_War/home":
			// go home
			req.getRequestDispatcher("/resources/html/index.html").forward(req, resp);
			break;
		case "/Project_1_War/login":
			// this will handle both types. The type is defined in the DB.
			getLogin().login(req, resp);
			break;
		case "/Project_1_War/logout":
			// this will handle both types.
			getLogin().logout(req, resp);
			break;
		case "/Project_1_War/employee":
			req.getRequestDispatcher("/resources/html/EmployeeHome.html").forward(req, resp);
			break;
		case "/Project_1_War/employee/getReimbsByAuthor":
			getGetReimbController().getReimbsByAuthor(req, resp);
			break;
		case "/Project_1_War/employee/addReimb":
			getInsertReimb().insertReimb(req, resp);
			break;
		case "/Project_1_War/finance":
			req.getRequestDispatcher("/resources/html/FinanceHome.html").forward(req, resp);
			break;
		case "/Project_1_War/finance/getAllReimbs":
			getGetReimbController().getAllReimbs(req, resp);
			break;
		case "/Project_1_War/finance/getReimbsByStatus":
			getGetReimbController().getReimbsByStatus(req, resp);
			break;
		case "/Project_1_War/finance/getReimbsByType":
			// FILTER BY TYPE HERE, NOT REQUIRED
			break;
		case "/Project_1_War/finance/appOrDen":
			getAppOrDenReimb().appOrDen(req, resp);
			break;
		case "/Project_1_War/json/getCurrentUser":
			getLogin().getCurrentUser(req, resp);
			break;
		default:
			System.out.println("In default case: Bruh, this URL is bad");
			System.out.println("Going to bad URL page");
			req.getRequestDispatcher("/resources/html/BadUrl.html").forward(req, resp);
		} // end switch
	} // end route
	
} // end class
