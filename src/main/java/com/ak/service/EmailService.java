package com.ak.service;


import com.ak.entity.User;

public interface EmailService {
	
	void sendEmail(String from, String to, String title, String body);
	
	void sendEmailHTML(String fromCustom, String to, String title, String templateName, User user);
	void sendEmailHTML(String to, String title, String templateName, User user);
}
