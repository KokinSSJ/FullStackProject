package com.ak.prepare;

import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.ak.entity.User;

@Component
public class ForgetPassword {
	
	public static String servletContext(HttpServletRequest request){
		return "http://"+ request.getServerName()+ ":" + request.getServerPort()+request.getContextPath();
	}
	
	
	// setup all necessary properties for ForgetPassword template for FreeMarkerTemplate
	// not use dots (".")
	//TODO check if template can use "-" or "_" in attribute name
	public static Properties propertiesForgetPassword(HttpServletRequest request, User user, String token){
		Properties properties = new Properties();
		
		properties.setProperty("user_email", user.getEmail());
		properties.setProperty("user_name", user.getFirstName());
		properties.setProperty("link", servletContext(request)+"/user/changePassword?id="+user.getId()+"&token="+token);
		properties.setProperty("date", new Date().toString());
		return properties;
	}

}
