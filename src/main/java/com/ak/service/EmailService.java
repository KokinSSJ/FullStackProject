package com.ak.service;

public interface EmailService {
	
	void sendEmail(String from, String to, String title, String body);
}
