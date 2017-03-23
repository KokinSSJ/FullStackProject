package com.ak.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

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
	public void sendEmailHTML(String fromCustom, String title, String templateName, User user, Properties properties){
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		//prop for template 
		Properties templateProperties = new Properties();
		templateProperties.setProperty("email", user.getEmail());
		templateProperties.setProperty("forget.link", "http://localhost:8090/FullStackProject/login");
////		----------- Properties With Dots --------------------
// 		// access from *JSP with FreeMarkerTemplate		
//	       <!-- It works  -->
//			<a href="${.data_model["page.URL"]}"> cos ${.data_model["page.URL"]} tam</a><br>
//				
//	            <!-- It works, too -->
//			- <a href="${.data_model['page.URL']}"> cos  ${.data_model['page.URL']} tam2</a><br> 
//		------------------------------------------------------------------------
		
		
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setTo(user.getEmail());
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
	public void sendEmailHTML(String title, String templateName, User user, Properties properties){
		sendEmailHTML(AppConfig.EMAIL, title, templateName, user, properties);
	}
	
	
	@Override
	public String getContentTemplate(Properties properties){
		StringBuilder stringContent = new StringBuilder();
		try{
			stringContent.append(FreeMarkerTemplateUtils.processTemplateIntoString(
					freeMarkerConf.getTemplate("forgotten_password.jsp"), properties)); //properties/ model -> not allowed dots 
			//-> see as nesting (nameAttributeWithoutDots.email/name/password etc)
			// model -> all nesting -> put "user" as attribute,
			//model -> attributes
			// properties -> only one variable -> only email, only name, for every "variable" separate .addAttribute
		
			return stringContent.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong!"; //if something went wrong!
		}	
	}
	
	
	
	

}
