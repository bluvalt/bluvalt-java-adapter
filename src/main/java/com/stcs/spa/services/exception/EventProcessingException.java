package com.stcs.spa.services.exception;

public class EventProcessingException extends Exception {

	private static final long serialVersionUID = 1L;

	public EventProcessingException(String message) {
		super(message);
	}

	public EventProcessingException(Throwable cause) {
		super(cause);
	}

	public EventProcessingException(String message, Throwable cause) {
		super(message, cause);
	}
}
