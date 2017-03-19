package com.ak.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.transform.impl.AddDelegateTransformer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ak.config.SecurityConfig;
import com.ak.entity.User;
import com.ak.service.EmailService;
import com.ak.service.UserService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

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
	public String getPasswordForgetPage(Model model) {
//		model.addAttribute("password", "objectContent12");
//		<p>objectContent <c:out value='${password}' /></p>
		
//		System.out.println("Remote Addr: " + request.getRemoteAddr());
//		System.out.println("Remote HOST: " + request.getRemoteHost());
//		System.out.println("Remote Port: " + request.getRemotePort());
//		System.out.println("Remote User: " + request.getRemoteUser());

		return "password-forget";
	}
	
	@RequestMapping(value = "/password-forget", method = RequestMethod.POST) // tylko wyswietlenie strony password-forget!
	public String sendNewPassword(@ModelAttribute User user, Model model, RedirectAttributes redir) throws Exception {
		System.out.println("Send email " + user.getEmail());
		if(userService.findByEmail(user.getEmail())==null){
//			response.getWriter().println("No such email");
			model.addAttribute("ServerInfo", "No such user-email in our data base"); //dodaje atrybut do tej strony!
			System.out.println("No such email! ");
			return "/password-forget";
		}
		redir.addFlashAttribute("ServerInfo", "Email sent - check your mailbox for next instructions"); //dodaje atrybut ale do strony już po redirect!
		  emailService.sendEmail("metinhack911@gmail.com", user.getEmail(), "LibraryApp", "Welcome " + user.getFirstName());
//		  emailService.sendEmail(from, to, title, body);

		// check password forget to login.jsp
		return "redirect:/login";
	}
	

}
