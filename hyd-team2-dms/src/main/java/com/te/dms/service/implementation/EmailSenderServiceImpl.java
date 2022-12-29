package com.te.dms.service.implementation;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.te.dms.service.EmailSenderService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String toEmail, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("demoemail256.a@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}

	@Override
	public void sendEmailWithUrl(String emailId, String subject, String text, String filePath) {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("demoemail256.a@gmail.com");
			helper.setTo(emailId);
			helper.setSubject(subject);
			helper.setText(text + "\n" + filePath);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(message);

	}

//	@Override
//	public void sendMessageWithAttachment(String to, String subject, String text, byte[] value) {
//		// ...
//
//		MimeMessage message = mailSender.createMimeMessage();
//
//		MimeMessageHelper helper;
//		try {
//			helper = new MimeMessageHelper(message, true);
//			helper.setFrom("demoemail256.a@gmail.com");
//			helper.setTo(to);
//			helper.setSubject(subject);
//			helper.setText(text);
//			ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(value);
//			helper.addAttachment(text, ByteArrayInputStream);
//			FileSystemResource file = FileSystemResource();
//			helper.addAttachment("Document", file);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		mailSender.send(message);
//		// ...
//	}

}