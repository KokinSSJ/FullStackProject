package com.ak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Override
	public void sendEmail(String from, String to, String title, String body) {
	SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
	simpleMailMessage.setTo(to);
	simpleMailMessage.setFrom(from);
	simpleMailMessage.setSubject("[SzkolenieSpringDB] " + title);
	simpleMailMessage.setText(body);
	javaMailSender.send(simpleMailMessage);
		
	}

}
