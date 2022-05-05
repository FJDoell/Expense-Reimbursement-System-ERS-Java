package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.TreeMap;

import model.Reimbursement;
import model.ReimbursementStatus;
import model.ReimbursementType;
import model.User;
import model.UserRole;

/**
 * Makes use of TreeMaps and database connections.
 * You will have to provide the DB properties.
 * In this case I can just change the DB name.
 * Identical to DbConnectionHandler but uses the test DB.
 * v1.0
 * @author darkm
 *
 */
public class DbConnectionHandlerTest {

	private static String DATABASE_EXCEPTION = "DATABASE_EXCEPTION";
	// this connection is self contained; we only need one copy!
	// This is bad practice
	// private static Connection conn;

	private static Connection getRemoteConnection() throws DbExceptionHandler {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				String hostname = System.getenv("FRIDGE_HOST");
				String port = System.getenv("FRIDGE_PORT");
				String dbName = System.getenv("ERS_DB_TEST");
				String userName = System.getenv("FRIDGE_USER");
				String password = System.getenv("FRIDGE_PASS");
				String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName
						+ "&password=" + password;
				System.out.println("Getting remote connection with connection string from environment variables.");
				Connection conn = DriverManager.getConnection(jdbcUrl);
				System.out.println("Remote connection successful.");
				return conn;
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Failed to connect to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Failed to connect to database", e);
		}
		System.out.println("Postgres driver does not exist!");
		return null;
	} // end getRemoteConnection
	
	/*
	 * ORDER OF METHODS
	 * GET ALL
	 * GET BY X SINGULAR
	 * GET BY X MULTIPLE
	 * INSERTS
	 * UPDATES
	 * DELETES
	 */
	
///////////////////////// GENERAL DATABASE METHODS
	public static ResultSet getDbResultSet(String sql) throws DbExceptionHandler {
		// initialize statements, SQL and results
		try {
			Connection conn = getRemoteConnection();
			String ourSQLStatement = sql;
			PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
			ResultSet rst = ps.executeQuery();
			return rst;
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get DB result set

	public static void pushUpdateToDb(String sql) throws ClassNotFoundException {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				String ourSQLStatement = sql;
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(ourSQLStatement);
				System.out.println("Executing update instantly.");
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

///////////////////////// DATABASE GET ALL (Just select the section, find and replace)
	// All Reimbursement
	public static TreeMap<Integer, Reimbursement> getAllReimbursements() throws DbExceptionHandler {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				ResultSet rst = getDbResultSet("SELECT * FROM ers_reimbursement");
				TreeMap<Integer, Reimbursement> reimbursementArray = new TreeMap<Integer, Reimbursement>();
				while (rst.next()) {
					// add to array
					reimbursementArray.put(rst.getInt("reimb_id"),
						new Reimbursement(rst.getInt("reimb_id"),
								rst.getInt("reimb_amount"),
								rst.getDate("reimb_submitted"),
								rst.getDate("reimb_resolved"),
								rst.getString("reimb_description"),
								rst.getInt("reimb_author"),
								rst.getInt("reimb_resolver"),
								rst.getInt("reimb_status_id"),
								rst.getInt("reimb_type_id")));
				}
				return reimbursementArray;
			} else {
				return new TreeMap<Integer, Reimbursement>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get all Reimbursement
		
	// All ReimbursementStatus
	public static TreeMap<Integer, ReimbursementStatus> getAllReimbursementStatuses() throws DbExceptionHandler {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				ResultSet rst = getDbResultSet("SELECT * FROM ers_reimbursement_status");
				TreeMap<Integer, ReimbursementStatus> objectTree = new TreeMap<Integer, ReimbursementStatus>();
				while (rst.next()) {
					// add to array
					objectTree.put(rst.getInt("reimb_status_id"),
						new ReimbursementStatus(rst.getInt("reimb_status_id"),
								rst.getString("reimb_status")));
					}
				return objectTree;
			} else {
				return new TreeMap<Integer, ReimbursementStatus>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get all ReimbursementStatus
	
	// All ReimbursementType
	public static TreeMap<Integer, ReimbursementType> getAllReimbursementTypes() throws DbExceptionHandler {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				ResultSet rst = getDbResultSet("SELECT * FROM ers_reimbursement_type");
				TreeMap<Integer, ReimbursementType> objectTree = new TreeMap<Integer, ReimbursementType>();
				while (rst.next()) {
					// add to array
					objectTree.put(rst.getInt("reimb_type_id"),
						new ReimbursementType(rst.getInt("reimb_type_id"),
								rst.getString("reimb_type")));
					}
				return objectTree;
			} else {
				return new TreeMap<Integer, ReimbursementType>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get all ReimbursementType
	
	// All User
	public static TreeMap<Integer, User> getAllUsers() throws DbExceptionHandler {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				ResultSet rst = getDbResultSet("SELECT * FROM ers_users");
				TreeMap<Integer, User> userMap = new TreeMap<Integer, User>();
				while (rst.next()) {
					// add to array
					userMap.put(rst.getInt("ers_users_id"),
						new User(rst.getInt("ers_users_id"),
								rst.getString("ers_username"),
								rst.getString("ers_password"),
								rst.getString("user_first_name"),
								rst.getString("user_last_name"),
								rst.getString("user_email"),
								rst.getInt("user_role_id")));
				}
				return userMap;
			} else {
				return new TreeMap<Integer, User>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get all User
	
	// All UserRole
	public static TreeMap<Integer, UserRole> getAllUserRoles() throws DbExceptionHandler {
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				ResultSet rst = getDbResultSet("SELECT * FROM ers_user_roles");
				TreeMap<Integer, UserRole> userRoleTree = new TreeMap<Integer, UserRole>();
				while (rst.next()) {
					// add to array
					userRoleTree.put(rst.getInt("ers_user_role_id"),
						new UserRole(rst.getInt("ers_user_role_id"),
								rst.getString("user_role")));
					}
				return userRoleTree;
			} else {
				return new TreeMap<Integer, UserRole>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get all UserRole

///////////////////////// DATABASE FIND ONE BY (Can be ID, name, any column)
	// Reimbursement By Id
	public static Reimbursement getReimbursementById(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// initialize object(s) we are finding
				Reimbursement reimbursement = new Reimbursement();

				// IF you find one thing vs WHILE finding multiple things
				if (rst.next()) {
					// set object values
					reimbursement.setId(rst.getInt("reimb_id"));
					reimbursement.setAmount(rst.getInt("reimb_amount"));
					if(rst.getDate("reimb_submitted") != null) {
						reimbursement.setSubmitted(rst.getDate("reimb_submitted").toString());
					}
					if(rst.getDate("reimb_resolved") != null) {
						reimbursement.setResolved(rst.getDate("reimb_resolved").toString());
					}
					reimbursement.setDescription(rst.getString("reimb_description"));
					reimbursement.setAuthor(rst.getInt("reimb_author"));
					reimbursement.setResolver(rst.getInt("reimb_resolver"));
					reimbursement.setStatusId(rst.getInt("reimb_status_id"));
					reimbursement.setTypeId(rst.getInt("reimb_type_id"));
				}
				// if you find nothing, return a default object
				return reimbursement;
			} else {
				// return a default object
				return new Reimbursement();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get Reimbursement by Id
	
	// ReimbursementStatus By Id
	public static ReimbursementStatus getReimbursementStatusById(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement_status WHERE reimb_status_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// initialize object(s) we are finding
				ReimbursementStatus reimbursementStatus = new ReimbursementStatus();

				// IF you find one thing vs WHILE finding multiple things
				if (rst.next()) {
					// set object values
					reimbursementStatus.setId(rst.getInt("reimb_status_id"));
					reimbursementStatus.setStatus(rst.getString("reimb_status"));
				}
				// if you find nothing, return a default object
				return reimbursementStatus;
			} else {
				// return a default object
				return new ReimbursementStatus();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get ReimbursementStatus by Id
	
	// ReimbursementType By Id
	public static ReimbursementType getReimbursementTypeById(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement_type WHERE reimb_type_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// initialize object(s) we are finding
				ReimbursementType reimbursementType = new ReimbursementType();

				// IF you find one thing vs WHILE finding multiple things
				if (rst.next()) {
					// set object values
					reimbursementType.setTypeId(rst.getInt("reimb_type_id"));
					reimbursementType.setType(rst.getString("reimb_type"));
				}
				// if you find nothing, return a default object
				return reimbursementType;
			} else {
				// return a default object
				return new ReimbursementType();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get ReimbursementType by Id
	
	// User by ID
	public static User getUserById(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_users WHERE ers_users_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// initialize object(s) we are finding
				User user = new User();

				// IF you find one thing vs WHILE finding multiple things
				if (rst.next()) {
					// set object values
					user.setId(rst.getInt("ers_users_id"));
					user.setUsername(rst.getString("ers_username"));
					user.setPassword(rst.getString("ers_password"));
					user.setFirstName(rst.getString("user_first_name"));
					user.setLastName(rst.getString("user_last_name"));
					user.setEmail(rst.getString("user_email"));
					user.setUserRoleId(rst.getInt("user_role_id"));
				}
				// if you find nothing, return a default object
				return user;
			} else {
				return new User();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get User by Id
	
	// User by Credentials
	public static User getUserByCredentials(String username, String pass) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_users "
				+ "WHERE ers_username = ? AND ers_password = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setString(1, username); // Search for what value?
				ps.setString(2, pass); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// initialize object(s) we are finding
				User user = new User();

				// IF you find one thing vs WHILE finding multiple things
				if (rst.next()) {
					// set object values
					user.setId(rst.getInt("ers_users_id"));
					user.setUsername(rst.getString("ers_username"));
					user.setPassword(rst.getString("ers_password"));
					user.setFirstName(rst.getString("user_first_name"));
					user.setLastName(rst.getString("user_last_name"));
					user.setEmail(rst.getString("user_email"));
					user.setUserRoleId(rst.getInt("user_role_id"));
				}
				// if you find nothing, return a default object
				return user;
			} else {
				return new User();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get User by Credentials
	
	// UserRole by ID
	public static UserRole getUserRoleById(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_user_roles WHERE ers_user_role_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// initialize object(s) we are finding
				UserRole userRole = new UserRole();

				// IF you find one thing vs WHILE finding multiple things
				if (rst.next()) {
					// set object values
					userRole.setId(rst.getInt("ers_user_role_id"));
					userRole.setRole(rst.getString("user_role"));
				}
				// if you find nothing, return a default object
				return userRole;
			} else {
				return new UserRole();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get UserRole by Id
	
///////////////////////// DATABASE FIND MULTIPLE BY
	// Reimbursement by Author
	public static TreeMap<Integer, Reimbursement> getReimbursementsByAuthor(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// IF you find one thing vs WHILE finding multiple things
				TreeMap<Integer, Reimbursement> reimbursementTree = new TreeMap<Integer, Reimbursement>();
				while (rst.next()) {
					// add to array
					reimbursementTree.put(rst.getInt("reimb_id"),
						new Reimbursement(rst.getInt("reimb_id"),
								rst.getInt("reimb_amount"),
								rst.getDate("reimb_submitted"),
								rst.getDate("reimb_resolved"),
								rst.getString("reimb_description"),
								rst.getInt("reimb_author"),
								rst.getInt("reimb_resolver"),
								rst.getInt("reimb_status_id"),
								rst.getInt("reimb_type_id")));
				}
				// if you find nothing, return a default object
				return reimbursementTree;
			} else {
				// return a default object
				return new TreeMap<Integer, Reimbursement>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get Reimbursement by Author
	
	// Reimbursement by Status
	public static TreeMap<Integer, Reimbursement> getReimbursementsByStatus(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// IF you find one thing vs WHILE finding multiple things
				TreeMap<Integer, Reimbursement> reimbursementTree = new TreeMap<Integer, Reimbursement>();
				while (rst.next()) {
					// add to array
					reimbursementTree.put(rst.getInt("reimb_id"),
						new Reimbursement(rst.getInt("reimb_id"),
								rst.getInt("reimb_amount"),
								rst.getDate("reimb_submitted"),
								rst.getDate("reimb_resolved"),
								rst.getString("reimb_description"),
								rst.getInt("reimb_author"),
								rst.getInt("reimb_resolver"),
								rst.getInt("reimb_status_id"),
								rst.getInt("reimb_type_id")));
				}
				// if you find nothing, return a default object
				return reimbursementTree;
			} else {
				// return a default object
				return new TreeMap<Integer, Reimbursement>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get Reimbursement by Status
	
	// Reimbursement by Type
	public static TreeMap<Integer, Reimbursement> getReimbursementsByType(int id) throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_type_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, id); // Search for what value?

				// execute
				ResultSet rst = ps.executeQuery();

				// IF you find one thing vs WHILE finding multiple things
				TreeMap<Integer, Reimbursement> reimbursementTree = new TreeMap<Integer, Reimbursement>();
				while (rst.next()) {
					// add to array
					reimbursementTree.put(rst.getInt("reimb_id"),
						new Reimbursement(rst.getInt("reimb_id"),
								rst.getInt("reimb_amount"),
								rst.getDate("reimb_submitted"),
								rst.getDate("reimb_resolved"),
								rst.getString("reimb_description"),
								rst.getInt("reimb_author"),
								rst.getInt("reimb_resolver"),
								rst.getInt("reimb_status_id"),
								rst.getInt("reimb_type_id")));
				}
				// if you find nothing, return a default object
				return reimbursementTree;
			} else {
				// return a default object
				return new TreeMap<Integer, Reimbursement>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get Reimbursement by Type

	// Resolved Reimbursements
	public static TreeMap<Integer, Reimbursement> getResolvedReimbursements() throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_resolver IS NOT NULL";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// execute
				ResultSet rst = ps.executeQuery();

				// IF you find one thing vs WHILE finding multiple things
				TreeMap<Integer, Reimbursement> reimbursementTree = new TreeMap<Integer, Reimbursement>();
				while (rst.next()) {
					// add to array
					reimbursementTree.put(rst.getInt("reimb_id"),
						new Reimbursement(rst.getInt("reimb_id"),
								rst.getInt("reimb_amount"),
								rst.getDate("reimb_submitted"),
								rst.getDate("reimb_resolved"),
								rst.getString("reimb_description"),
								rst.getInt("reimb_author"),
								rst.getInt("reimb_resolver"),
								rst.getInt("reimb_status_id"),
								rst.getInt("reimb_type_id")));
				}
				// if you find nothing, return a default object
				return reimbursementTree;
			} else {
				// return a default object
				return new TreeMap<Integer, Reimbursement>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get Resolved Reimbursement
	
	// Unresolved Reimbursements
	public static TreeMap<Integer, Reimbursement> getUnresolvedReimbursements() throws DbExceptionHandler {
		// SQL base structure
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_resolver IS NULL";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// execute
				ResultSet rst = ps.executeQuery();

				// IF you find one thing vs WHILE finding multiple things
				TreeMap<Integer, Reimbursement> reimbursementTree = new TreeMap<Integer, Reimbursement>();
				while (rst.next()) {
					// add to array
					reimbursementTree.put(rst.getInt("reimb_id"),
						new Reimbursement(rst.getInt("reimb_id"),
								rst.getInt("reimb_amount"),
								rst.getDate("reimb_submitted"),
								rst.getDate("reimb_resolved"),
								rst.getString("reimb_description"),
								rst.getInt("reimb_author"),
								rst.getInt("reimb_resolver"),
								rst.getInt("reimb_status_id"),
								rst.getInt("reimb_type_id")));
				}
				// if you find nothing, return a default object
				return reimbursementTree;
			} else {
				// return a default object
				return new TreeMap<Integer, Reimbursement>();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end get Unresolved Reimbursement
	
	
///////////////////////// DB INSERTS
	// Insert Reimbursement
	public static int insertReimbursement(Reimbursement reimbursement) throws DbExceptionHandler {
		// SQL base structure
		String sql = "INSERT INTO ers_reimbursement "
				+ "(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) "
				+ "VALUES (?, NOW(), ?, ?, ?, ?, ?)";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				// Setup the SQL statement values
				ps.setInt(1, reimbursement.getAmount()); // Insert
				// ps.setDate(2, reimbursement.getSubmitted()); // Insert
				// ps.setDate(2, reimbursement.getResolved()); // Insert
				ps.setString(2, reimbursement.getDescription()); // Insert
				ps.setInt(3, reimbursement.getAuthor()); // Insert
				if(reimbursement.getResolver() != 0) {
					ps.setInt(4, reimbursement.getResolver()); // Insert
				} else {
					ps.setNull(4, Types.INTEGER);
				}
				ps.setInt(5, reimbursement.getStatusId()); // Insert
				ps.setInt(6, reimbursement.getTypeId()); // Insert

				// execute
				ps.executeUpdate();
				int newId = 0;
				
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						newId = generatedKeys.getInt(1);
					} else {
						throw new SQLException("Creating Reimbursement failed, no ID obtained.");
					}
				}
				return newId;
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
		return -1;
	} // end insert Reimbursement
	
	// There is no reason to insert anything else through this app

///////////////////////// DB UPDATES
	// Update Reimbursement
	public static void updateReimbursement(Reimbursement myReimbursement) throws DbExceptionHandler {
		// SQL base structure
		String sql = "UPDATE ers_reimbursement "
				+ "SET reimb_amount = ?, reimb_resolved = NOW(), reimb_description = ?, reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, reimb_type_id = ? "
				+ "WHERE reimb_id = ?";
		try {
			if (Class.forName("org.postgresql.Driver") != null) {
				Connection conn = getRemoteConnection();
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setup the SQL statement values
				ps.setInt(1, myReimbursement.getAmount()); // Update
				ps.setString(2, myReimbursement.getDescription()); // Update
				ps.setInt(3, myReimbursement.getAuthor()); // Update
				if(myReimbursement.getResolver() != 0) {
					ps.setInt(4, myReimbursement.getResolver()); // Update
				} else {
					ps.setNull(4, Types.INTEGER); // Update
				}
				ps.setInt(5, myReimbursement.getStatusId()); // Update
				ps.setInt(6, myReimbursement.getTypeId()); // Update
				ps.setInt(7, myReimbursement.getId()); // Update what?

				// execute
				ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		} catch (SQLException e) {
			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
		}
	} // end Update Reimbursement

	// This app doesn't update anything else at the moment
	
///////////////////////// DB DELETES
	
	// This app doesn't delete anything!
	// All reimbursements are simply updated in their status.
	// No records of them are ever deleted.
	
//	// Delete Object
//	public static void deleteObject(int id) throws DbExceptionHandler {
//		// SQL base structure
//		String sql = "DELETE FROM myTable " + "WHERE my_id = ?";
//		try {
//			if (Class.forName("org.postgresql.Driver") != null) {
//				PreparedStatement ps = conn.prepareStatement(sql);
//
//				// Setup the SQL statement values
//				ps.setInt(1, id); // What ID?
//
//				// execute
//				ps.executeUpdate();
//			}
//		} catch (ClassNotFoundException e) {
//			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
//		} catch (SQLException e) {
//			throw new DbExceptionHandler(DATABASE_EXCEPTION, "Lost connection to database", e);
//		}
//	} // end Delete Restaurant
	
	// For removing a many to many relationship, just pass in TWO ids, and check 
	// the junction table
	

} // end class
