package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ReimbursementDao;
import dao.ReimbursementStatusDao;
import dao.ReimbursementTypeDao;
import dao.UserDao;
import dao.UserRoleDao;
import db.DbConnectionHandler;
import model.Reimbursement;
import model.User;

/**
 * The LOGIC behind connecting to the DB. E.g. checks for input validation,
 * checks for if the Dao is empty, etc.
 */
public class DbService {
	private UserDao userDao = null;
	private ReimbursementDao reimbDao = null;
	private ReimbursementTypeDao reimbTypeDao = null;
	private ReimbursementStatusDao reimbStatusDao = null;
	private Reimbursement currentReimb = null;
	private UserRoleDao userRoleDao = null;
	private User user = null;
	
	// Inversion of Control Constructors
	public DbService() {
		super();
		this.setUserDao(new UserDao());
		this.setReimbDao(new ReimbursementDao());
		this.setReimbTypeDao(new ReimbursementTypeDao());
		this.setReimbStatusDao(new ReimbursementStatusDao());
		this.setCurrentReimb(new Reimbursement());
		this.setUserRoleDao(new UserRoleDao());
		this.setUser(new User());
	}
	public DbService(UserDao userDao, ReimbursementDao reimbDao, ReimbursementTypeDao reimbTypeDao,
			ReimbursementStatusDao reimbStatusDao, Reimbursement currentReimb, UserRoleDao userRoleDao, Reimbursement reimb, User user) {
		super();
		this.userDao = userDao;
		this.reimbDao = reimbDao;
		this.reimbTypeDao = reimbTypeDao;
		this.reimbStatusDao = reimbStatusDao;
		this.currentReimb = currentReimb;
		this.userRoleDao = userRoleDao;
		this.user = user;
	}
	// Getters and setters
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public ReimbursementDao getReimbDao() {
		return reimbDao;
	}
	public void setReimbDao(ReimbursementDao reimbDao) {
		this.reimbDao = reimbDao;
	}
	public Reimbursement getCurrentReimb() {
		return currentReimb;
	}
	public void setCurrentReimb(Reimbursement currentReimb) {
		this.currentReimb = currentReimb;
	}
	public ReimbursementTypeDao getReimbTypeDao() {
		return reimbTypeDao;
	}
	public void setReimbTypeDao(ReimbursementTypeDao reimbTypeDao) {
		this.reimbTypeDao = reimbTypeDao;
	}
	public ReimbursementStatusDao getReimbStatusDao() {
		return reimbStatusDao;
	}
	public void setReimbStatusDao(ReimbursementStatusDao reimbStatusDao) {
		this.reimbStatusDao = reimbStatusDao;
	}
	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * A quick initialize method for all the static Daos.
	 */
	public void initialize(HttpServletRequest req) {
		HttpSession mySession = req.getSession();
		if (getReimbTypeDao().getAll().isEmpty()) {
			// we need to get this
			getAllReimbursementTypes();
		}
		if (getUserRoleDao().getAll().isEmpty()) {
			// we need to get this
			getAllUserRoles();
		}
		if (getReimbStatusDao().getAll().isEmpty()) {
			// we need to get this
			getAllReimbursementStatuses();
		}
		mySession.setAttribute("allReimbTypes", getReimbTypeDao());
		mySession.setAttribute("allUserRoles", getUserRoleDao());
		mySession.setAttribute("allReimbStatuses", getReimbStatusDao());
	}

	/**
	 * Pulls into the database directly.
	 * 
	 * @return void. ReimbursementStatusDao is static.
	 */
	private void getAllReimbursementStatuses() {
		getReimbStatusDao().initialize();
	}

	/**
	 * Pulls into the database directly.
	 * 
	 * @return void. ReimbursementTypeDao is static.
	 */
	private void getAllReimbursementTypes() {
		getReimbTypeDao().initialize();
	}

	/**
	 * Pulls into the database directly.
	 * 
	 * @return UserDao of all users.
	 */
	public void getAllUsers() {
		getUserDao().saveAll(DbConnectionHandler.getAllUsers());
	}
	
	/**
	 * Pulls one user
	 * 
	 * 
	 */
	public void getUserByCredentials(String name, String pass) {
		setUser((DbConnectionHandler.getUserByCredentials(name, pass)));
	}

	/**
	 * Pulls into the database directly.
	 * 
	 * @return None, UserRoleDao is static
	 */
	public void getAllUserRoles() {
		getUserRoleDao().saveAll(DbConnectionHandler.getAllUserRoles());
	}

	// None of these should run until the static DAOs are populated.
	/**
	 * Pulls into the database directly.
	 * 
	 * @return ReimbursementDao of all reimbursements, regardless of status
	 */
	public void getAllReimbursements(HttpServletRequest req, HttpServletResponse resp) {
		getReimbDao().saveAll(DbConnectionHandler.getAllReimbursements());
		setReimbNames(req, resp);
	}

	/**
	 * Get Reimbursements by status from DB.
	 * 
	 * @return ReimbursementDao of reimbursements by status ID.
	 */
	public void getReimbursementsByStatus(HttpServletRequest req, HttpServletResponse resp, int id) {
		getReimbDao().saveAll(DbConnectionHandler.getReimbursementsByStatus(id));
		setReimbNames(req, resp);
	}

	/**
	 * Get Reimbursements by type from DB.
	 * 
	 * @return ReimbursementDao of reimbursements by type ID.
	 */
	public void getReimbursementsByType(HttpServletRequest req, HttpServletResponse resp, int id) {
		getReimbDao().saveAll(DbConnectionHandler.getReimbursementsByType(id));
		setReimbNames(req, resp);
	}

	/**
	 * Get Reimbursements by author from DB.
	 * 
	 * @return ReimbursementDao of reimbursements by author ID.
	 */
	public void getReimbursementsByAuthor(HttpServletRequest req, HttpServletResponse resp, int id) {
		getReimbDao().saveAll(DbConnectionHandler.getReimbursementsByAuthor(id));
		setReimbNames(req, resp);
	}

	/**
	 * Get resolved Reimbursements from DB.
	 * 
	 * @return ReimbursementDao of reimbursements that are unresolved.
	 */
	public void getResolvedReimbursements(HttpServletRequest req, HttpServletResponse resp) {
		getReimbDao().saveAll(DbConnectionHandler.getResolvedReimbursements());
		setReimbNames(req, resp);
	}

	/**
	 * Get unresolved Reimbursements from DB.
	 * 
	 * @return ReimbursementDao of reimbursements that are resolved.
	 */
	public void getUnresolvedReimbursements(HttpServletRequest req, HttpServletResponse resp) {
		getReimbDao().saveAll(DbConnectionHandler.getUnresolvedReimbursements());
		setReimbNames(req, resp);
	}

	/**
	 * Get just one reimb from DB by ID.
	 * 
	 * @return Reimbursement.
	 */
	public void getReimbursementById(HttpServletRequest req, HttpServletResponse resp, int id) {
		setCurrentReimb(DbConnectionHandler.getReimbursementById(id));
		setReimbName(req, resp);
	}

	/**
	 * This method simply reaches into a given ReimbursementDao and sets all Status
	 * and Type names correctly.
	 */
	private void setReimbNames(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession mySession = req.getSession();
		setUserDao((UserDao) mySession.getAttribute("allUsers"));
		setReimbTypeDao((ReimbursementTypeDao) mySession.getAttribute("allReimbTypes"));
		setReimbStatusDao((ReimbursementStatusDao) mySession.getAttribute("allReimbStatuses"));

		// for every reimbursement I was given from this dao...
		for (Reimbursement r : getReimbDao().getAll().values()) {
			// get the author name
			if (r.getAuthor() != 0) {
				r.setAuthorName(getUserDao().getById(r.getAuthor()).getFirstName());
			}
			// get the resolver's name
			if (r.getResolver() != 0) {
				r.setResolverName(getUserDao().getById(r.getResolver()).getFirstName());
			}
			// get the status and type names
			r.setStatusName(getReimbStatusDao().getById(r.getStatusId()).getStatus());
			r.setTypeName(getReimbTypeDao().getById(r.getTypeId()).getType());
		} // end for
	} // end method

	/**
	 * This method simply reaches into a given Reimbursement and sets all Status and
	 * Type names correctly.
	 */
	private void setReimbName(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession mySession = req.getSession();
		setUserDao( (UserDao) mySession.getAttribute("allUsers") );
		setReimbTypeDao((ReimbursementTypeDao) mySession.getAttribute("allReimbTypes"));
		setReimbStatusDao((ReimbursementStatusDao) mySession.getAttribute("allReimbStatuses"));
		Reimbursement r = getCurrentReimb();

		// get the author name
		if (r.getAuthor() != 0) {
			r.setAuthorName(getUserDao().getById(r.getAuthor()).getFirstName());
		}
		// get the resolver's name
		if (r.getResolver() != 0) {
			r.setResolverName(getUserDao().getById(r.getResolver()).getFirstName());
		}
		// get the status and type names
		r.setStatusName(getReimbStatusDao().getById(r.getStatusId()).getStatus());
		r.setTypeName(getReimbTypeDao().getById(r.getTypeId()).getType());
		setCurrentReimb(r);
	} // end method

	/**
	 * Insert a reimbursement
	 */
	public int insertReimb(Reimbursement reimb) {
		return DbConnectionHandler.insertReimbursement(reimb);
	}

	/**
	 * Update a reimbursement
	 */
	public void updateReimb(Reimbursement reimb) {
		DbConnectionHandler.updateReimbursement(reimb);
	}

}
