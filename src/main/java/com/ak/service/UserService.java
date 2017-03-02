package com.ak.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ak.entity.User;

public interface UserService extends UserDetailsService{ //dla spring security dodajemy UserDetailsService

	//logowanie
	//wylogowanie
	
	
	//to tylko dla ADMINa - zeby mogl wylistowac, dodac, znalezc jednego, usunac
	List<User> findAll();
	User findOne(Long id);
	void save(User user);
	void delete(Long id);
	User findByEmail(String email);
}
