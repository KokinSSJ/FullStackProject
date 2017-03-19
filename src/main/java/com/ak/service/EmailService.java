package com.ak.service;

import javax.mail.MessagingException;

public interface EmailService {
	
	void sendEmail(String from, String to, String title, String body);
	
	void sendEmailHTML(String from, String to, String title, String body) throws MessagingException;
}
