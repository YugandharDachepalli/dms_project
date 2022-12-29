package com.te.dms.service;

public interface EmailSenderService {

	public void sendEmail(String toEmail, String subject, String message);
	
	public void sendEmailWithUrl(String emailId, String subject, String text, String filePath);	
}
