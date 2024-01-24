package com.example.demo.service;

import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private final JavaMailSender emailSender;
	private final Environment env;

	public EmailService(JavaMailSender emailSender, Environment env) {
		this.emailSender = emailSender;
		this.env = env;
	}

	public boolean sendEmail(String subject, String message, String to) {
		boolean isSent = false;
		String from = env.getProperty("from.email");

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message, true); // true indicates HTML content

			emailSender.send(mimeMessage);
			isSent = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return isSent;
	}
}
