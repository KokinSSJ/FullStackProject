package com.ak.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ak.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByEmail(String email); //tutaj ma znaczenie nazwa, 
	//bo zostanie stworzony select do bazydanych, na podstawie nazwy
	// jest to napisane przez "aspekty"
	
}
