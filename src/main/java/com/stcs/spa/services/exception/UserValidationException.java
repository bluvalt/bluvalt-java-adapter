package com.stcs.spa.services.exception;

public class UserValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserValidationException(String message) {
		super(message);
	}

	public UserValidationException(Throwable cause) {
		super(cause);
	}

	public UserValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
