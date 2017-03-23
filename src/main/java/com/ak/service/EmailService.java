package com.ak.service;


import java.util.Properties;

import com.ak.entity.User;

public interface EmailService {
	
	void sendEmail(String from, String to, String title, String body);
	
	void sendEmailHTML(String fromCustom, String title, String templateName, User user, Properties properties);
	void sendEmailHTML(String title, String templateName, User user, Properties properties);
	
	
	String getContentTemplate(Properties properties);
}
