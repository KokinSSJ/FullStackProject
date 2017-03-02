package com.ak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ak.entity.User;
import com.ak.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping (value="/users", method=RequestMethod.GET)
	public String getUserPage(Model model){
		List<User> users = userService.findAll();
		model.addAttribute("usersList", users);
		return "users";

	}
	
	
	@RequestMapping(value ="/create-user", method =RequestMethod.GET) //wyswietla strone 
	public String createUser(){
		
		return "user-create";
	}
	
	
	//tworzy usera
	@RequestMapping(value ="/create-user", method =RequestMethod.POST) //wyswietla strone 
	public String saveUser(@RequestParam (required=false) Long id, @RequestParam(name="firstName", required=true) String firstName, 
			@RequestParam(name="lastName") String lastName, @RequestParam String email, @RequestParam String password){ // nie trzeba podawać "name"
		
		User user = new User(firstName, lastName, email, password);
		userService.save(user);
		return "redirect:/users";
	}
	
	//usuwanie
	@RequestMapping(value="/users/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable Long id){
		userService.delete(id);
		return "redirect:/users";
	}
	
	
	//edit -> wyswietlenie edycji!
	@RequestMapping(value="users/edit/{id}", method=RequestMethod.GET)
	public String editUser(@PathVariable Long id, Model model){
		User user = userService.findOne(id);
		model.addAttribute("user", user);
		return "user-create";
	}
		
}
