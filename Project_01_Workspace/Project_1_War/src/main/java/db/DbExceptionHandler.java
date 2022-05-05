package db;





@SuppressWarnings("serial")
/**
 * Works with DbConnectionHandler to handle exceptions.
 * @author darkm
 *
 */
public class DbExceptionHandler extends RuntimeException {
	private String code;

	public DbExceptionHandler(String code, String message) {
	        super(message);
	        this.setCode(code);
	    }

	public DbExceptionHandler(String code, String message, Throwable cause) {
	        super(message, cause);
	        this.setCode(code);
	    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
} // end class
