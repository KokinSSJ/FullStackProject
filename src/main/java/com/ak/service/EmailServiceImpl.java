package com.ak.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class EmailServiceImpl implements EmailService{
	
	private final static String WEB_NAME ="[DB's Webs Designs] ";

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Override
	public void sendEmail(String from, String to, String title, String body) {
	SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
	simpleMailMessage.setTo(to);
	simpleMailMessage.setFrom(from);
	simpleMailMessage.setSubject(WEB_NAME + title);
	simpleMailMessage.setText(body);
	javaMailSender.send(simpleMailMessage);
		
	}


	@Override
	public void sendEmailHTML(String from, String to, String title, String body) throws MessagingException{
		
		//TODO check if javaMailSender is changed
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setSubject(WEB_NAME + title);
		
		StringBuilder stringContent = new StringBuilder();
//		stringContent.append(FreeMarkerTemplateUtils.processTemplateIntoString(template, model))
		
	}
	
	
	
	

}
