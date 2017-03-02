package com.ak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ak.config.SecurityConfig;
import com.ak.entity.User;
import com.ak.service.EmailService;
import com.ak.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	// w ustawieniach konta gmail -> trzeba "how to access mail for external
	// app"
	// w przeciwnym razie aplikacja nie bedzie w stanie wysyłać maila!

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMainPage() {
		return "main";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET) // tylko wyswietlenie strony register
	public String getRegisterPage() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute User user) { //działanie / logika gdy uzytkownik sie rejestruje
		try{
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SecurityConfig.ENCODE_STRENGTH);
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userService.save(user);
		}catch(Exception ex){
			return "register";
		}
		emailService.sendEmail("metinhack911@gmail.com", user.getEmail(), "LibraryApp", "Welcome " + user.getFirstName());
		
		
		return "login";
				
	}

}
