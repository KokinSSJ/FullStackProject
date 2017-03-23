package com.ak.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ak.dao.UserDao;
import com.ak.entity.User;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	

	
		//jezeli nie bedzie uzytkownika w BD to rzuci wyjatek
	@Override 
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
		if(user==null){
			throw new UsernameNotFoundException(String.format("Uzytkownik z emailem %s nie istnieje", email));
			
		}
		System.out.println("Log in - User " + user.getEmail()); //to sie nie wykona, gdy zostanie rzucony wyjatek -> nie skonczy wykonywac sie tylko ta funkcja
		// aplikacja dalej dziala!!!
		//mozna dac finally {} zeby jeszcze sie jakis kod wykonal!!!
		
		List<GrantedAuthority> authorities = new  ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
			
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		//return new User(user.getEmail(), user.getPassword(), authorities); // nie moze byc bo to nie chodzi o tego naszego usera
	
	}

	//funkcja dla ADMIN
	@Override
	public List<User> findAll() {	
		System.out.println("findAll UserServiceImpl");
		return userDao.findAll();
	}

	//funkcja dla ADMIN
	@Override
	public User findOne(Long id) {
		System.out.println("finOne UserServiceImpl");
		return userDao.findOne(id);
	}

	//funkcja dla ADMIN
	@Override
	public void save(User user) {
		System.out.println("save UserServiceImpl");
		userDao.save(user);
		
	}

	//funkcja dla ADMIN
	@Override
	public void delete(Long id) {
		System.out.println("delete UserServiceImpl");
		userDao.delete(id);
		
	}

	@Override
	public User findByEmail(String email) {
		System.out.println("findByEmail UserServiceImpl");
		return userDao.findByEmail(email);
	}



}
