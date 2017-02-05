package com.ak.service;

import java.util.List;

import com.ak.entity.Book;

public interface BookService { // nie trzeba rozszerza o nic, bo nie korzystamy ze spring security
	
	List<Book> findAll();
	Book findOne(Long id);
	void save(Book book);
	void delete(Long id);
	
	

}
