package com.ak.service;

import java.util.List;

import com.ak.entity.Book;
import com.ak.entity.Rent;
import com.ak.entity.User;

public interface RentService{

	List<Rent> findAll();
	Rent findOne(Long id);
	void save(Rent rent);
	void delete(Long id);
	
	void createRent(User user, Book book);
	List<Rent> findByUserOrderByCreatedDateDesc(User user);
}
