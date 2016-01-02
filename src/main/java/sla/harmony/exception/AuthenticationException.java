package sla.harmony.exception;

public class AuthenticationException extends RuntimeException {

	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 938598856489182973L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}
}
