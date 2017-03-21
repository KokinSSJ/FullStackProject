package com.ak.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ClassUtils;

import com.ak.config.AppConfig;
import com.ak.entity.User;


import freemarker.template.Configuration;


@Service
public class EmailServiceImpl implements EmailService{
	
	private final static String WEB_NAME ="[DB's Webs Designs] ";

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Configuration freeMarkerConf;
	
	
	@Override
	public void sendEmail(String from, String to, String title, String body) {
	SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
	simpleMailMessage.setTo(to);
	simpleMailMessage.setFrom(from);
	simpleMailMessage.setSubject(WEB_NAME + title);
	simpleMailMessage.setText(body);
	javaMailSender.send(simpleMailMessage);
		
	}

	//TODO add properties att -> send model -> user, order, whatever you want
	@Override
	public void sendEmailHTML(String fromCustom, String to, String title, String templateName, User user){
		
		//TODO check if javaMailSender is changed
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		//prop for template 
		Properties properties = new Properties();
		properties.setProperty("email", to);

//		properties.setProperty("user", model.ge)
		
		MimeMessageHelper mimeMessageHelper;
		
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setFrom(fromCustom);
			mimeMessageHelper.setSubject(WEB_NAME + title);
			mimeMessageHelper.setText(getContentTemplate(properties), true);
			
			//add attachmets ! remember to add postfix to file name!
			mimeMessageHelper.addAttachment("Wild boar.jpg" ,new ClassPathResource("/templates_resources/test_attachment2.jpg"));
			
			javaMailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}
	
	
	//if you don't use custom email -> but default email!
	@Override
	public void sendEmailHTML(String to, String title, String templateName, User user){
		sendEmailHTML(AppConfig.EMAIL, to, title, templateName, user);
	}
	
	
	
	public String getContentTemplate(Properties properties){
		StringBuilder stringContent = new StringBuilder();
		try{
			stringContent.append(FreeMarkerTemplateUtils.processTemplateIntoString(
					freeMarkerConf.getTemplate("forgotten_password.jsp"), properties));
			stringContent.append(new Date());
		
			return stringContent.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong!"; //if something went wrong!
		}	
	}
	
	
	
	

}
