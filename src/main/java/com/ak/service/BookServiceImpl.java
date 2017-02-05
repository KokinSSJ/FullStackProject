package com.ak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.dao.BookDao;
import com.ak.entity.Book;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookDao bookdao;
	
	
	@Override
	public List<Book> findAll() {
		return bookdao.findAll();
	}

	@Override
	public Book findOne(Long id) {
		return bookdao.findOne(id);
	}

	@Override
	public void save(Book book) {
		bookdao.save(book);
		
	}

	@Override
	public void delete(Long id) {
		bookdao.delete(id);
		
	}

}
