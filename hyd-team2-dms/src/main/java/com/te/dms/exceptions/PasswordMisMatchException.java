package com.te.dms.exceptions;

public class PasswordMisMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordMisMatchException(String message) {
		super(message);
	}
}
