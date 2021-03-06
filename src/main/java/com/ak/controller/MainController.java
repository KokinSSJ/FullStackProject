package com.ak.controller;



import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ak.config.SecurityConfig;
import com.ak.entity.User;
import com.ak.prepare.ForgetPassword;
import com.ak.service.EmailService;
import com.ak.service.ResetPasswordService;
import com.ak.service.UserService;


@Controller
public class MainController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ResetPasswordService resetPasswordService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMainPage() {
		return "main";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(Locale locale) {
		System.out.println("User Locale: "  + locale);
		return "login";
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.GET) // tylko wyswietlenie strony register
	public String getRegisterPage() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute User user, RedirectAttributes redirectAttributes) { //działanie / logika gdy uzytkownik sie rejestruje
		try{
			//TODO sprawdź skorzystanie z AuthenticationManagerBuilder authenticationManagerBuilder a nie ustawianie tego na nowo -> patrz klasa security
			// szukaj przykładu
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
	public String sendNewPassword(@ModelAttribute User user, Model model, RedirectAttributes redir, HttpServletRequest request) throws Exception {
		System.out.println("Send email " + user.getEmail());
		User userTemp = userService.findByEmail(user.getEmail());
		if(userTemp==null){
			model.addAttribute("server_info", "No such user-email in our data base"); //dodaje atrybut do tej strony! // bez kropek, pauzy! moga byc poskreslenia
			model.addAttribute("server_info_status", "error");
			System.out.println("No such email! ");
			return "redirect:/password-forget";
		}
		String token = UUID.randomUUID().toString();
		resetPasswordService.createResetPasswordTokenForUser(token, userTemp);
//		System.out.println("model " + model);
//		model.addAttribute("test.dot", "testowanie dota"); // nie można do
			//send email with pass reminder!
//		  emailService.sendEmail("metinhack911@gmail.com", userTemp.getEmail(), "LibraryApp", "Welcome " + userTemp.getFirstName() +" send: " + new Date());
		  emailService.sendEmailHTML("LibraryApp Password reminder", "forgotten_password.jsp", userTemp, ForgetPassword.propertiesForgetPassword(request, userTemp, token));
		 
		  // add attribute for new site -> login.jsp
		  redir.addFlashAttribute("server_info", "Email sent - check your mailbox for next instructions"); //dodaje atrybut ale do strony już po redirect!
		  redir.addFlashAttribute("server_info_status", "ok");
		  // if all ok redirect to login.jsp
		return "redirect:/login";
	}
	
	@RequestMapping(value="/user/changePassword", method= RequestMethod.GET)
	public String getChangePassword(@RequestParam("id") Long id, @RequestParam("token") String token, Model model, RedirectAttributes redir){
		System.out.println("sprawdzam");
		String result = resetPasswordService.validateResetPasswordToken(id, token);
		System.out.println("result" + result);
		if(result!=null){
			model.addAttribute("server_info", result);
			return "redirect:/login";
		}
		
		redir.addFlashAttribute("user", userService.findOne(id));
		return "/updatePassword"; //zmień to ! 
		//TODO check if is authenticated then is principal.username -> navbar.jsp for "/"
	}
	
	@RequestMapping (value ="/user/updatePassword", method=RequestMethod.POST)
	public String changeUserPassword(){
		
		return "redirect:/login";
	}
	

}
