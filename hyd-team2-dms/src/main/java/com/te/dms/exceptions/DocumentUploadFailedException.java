package com.te.dms.exceptions;

public class DocumentUploadFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DocumentUploadFailedException(String message) {
		super(message);
	}

}
