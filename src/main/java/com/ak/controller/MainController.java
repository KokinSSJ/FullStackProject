package com.ak.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String register(@ModelAttribute User user, RedirectAttributes redirectAttributes) { //działanie / logika gdy uzytkownik sie rejestruje
		try{
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SecurityConfig.ENCODE_STRENGTH);
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userService.save(user);
		}catch(Exception ex){
			System.out.println("Register problem");
			return "redirect:/register";
		}
		
		// setFrom -> email> można zmienić natywny email "nadawcy"
		// email bedzie wysłany z tego natywnego, ale u odbiorcy pokaze sie że wysłał ten nowy (w wyslanych skrzynki natywnej widać treść wiadomosci)
		// w ustawieniach poczty natywnej (np.gmail) trzeba dodać tego dodatkowego maila do obsługiawanych np. metinhack911@gmail.com
		// gmail>settings->Accounts and Import -> "Send mail as"
		emailService.sendEmail("metinhack911@gmail.com", user.getEmail(), "LibraryApp", "Welcome " + user.getFirstName());
		redirectAttributes.addFlashAttribute("ServerInfo", "New user " +user.getEmail()+" created");
		
		return "redirect:/login";
				
	}
	
	@RequestMapping(value = "/password-forget", method = RequestMethod.GET) // tylko wyswietlenie strony password-forget!
	public String getPasswordForgetPage() {
		return "password-forget";
	}
	
	@RequestMapping(value = "/password-forget", method = RequestMethod.POST) // tylko wyswietlenie strony password-forget!
	public String sendNewPassword(@ModelAttribute User user, Model model, RedirectAttributes redir) throws Exception {
		System.out.println("Send email " + user.getEmail());
		User userTemp = userService.findByEmail(user.getEmail());
		if(userTemp==null){
//			response.getWriter().println("No such email");
			model.addAttribute("ServerInfo", "No such user-email in our data base"); //dodaje atrybut do tej strony!
			System.out.println("No such email! ");
			return "/password-forget";
		}
		
			//send email with pass reminder!
//		  emailService.sendEmail("metinhack911@gmail.com", userTemp.getEmail(), "LibraryApp", "Welcome " + userTemp.getFirstName() +" send: " + new Date());
		  emailService.sendEmailHTML(userTemp.getEmail(), "LibraryApp Password reminder", "forgotten_password.jsp", userTemp);
		 
		  // add attribute for new site -> login.jsp
		  redir.addFlashAttribute("ServerInfo", "Email sent - check your mailbox for next instructions"); //dodaje atrybut ale do strony już po redirect!
		
		  // if all ok redirect to login.jsp
		return "redirect:/login";
	}
	

}
