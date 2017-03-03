package com.ak.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ak.entity.Book;
import com.ak.entity.Rent;
import com.ak.entity.User;
import com.ak.entity.User.Role;
import com.ak.service.BookService;
import com.ak.service.RentService;
import com.ak.service.UserService;

@Controller
public class RentController {

	@Autowired
	private RentService rentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	
	// wyswietla wypozyczenia
	@RequestMapping(value="/rents", method=RequestMethod.GET)
	public String getRentPage(Model model, Principal principal ){ //principal -> z niego mozna wyciangać obecnie zalogowane uzytkownika!
		String email = principal.getName();
		User user = userService.findByEmail(email);
		
		List<Rent> rents;
		// w zależności kto odczytuje ADMIN/USER 
		// ----USER---
		if(user.getRole()==Role.USER){
			rents = rentService.findByUserOrderByCreatedDateDesc(user);
		}
		else{ //----ADMIN----
			rents = rentService.findAll();
		}
		
		model.addAttribute("rentsList", rents);
		
		return "rents";
	}
	
	
	//dodaje nowe wypożyczenie
	@RequestMapping(value="/rent/book/{bookId}", method=RequestMethod.GET) //domyslnie GET
	public String createRent(@PathVariable Long bookId, Principal principal){ // principal -> email -> na podstawie frontu (*.jsp)
		String email = principal.getName(); // po zalogowaniu principal przetrzymuje dane o emailu
		User user = userService.findByEmail(email);
		Book book = bookService.findOne(bookId);
		rentService.createRent(user, book);
		
		return "redirect:/rents";
	
	}
	
	
	
}
