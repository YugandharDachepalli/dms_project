package com.te.dms.exceptions;

public class CannotBeProcessedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CannotBeProcessedException(String message) {
		super(message);
	}
}
