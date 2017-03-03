package com.ak.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.dao.BookDao;
import com.ak.dao.RentDao;
import com.ak.entity.Book;
import com.ak.entity.Rent;
import com.ak.entity.Rent.Status;
import com.ak.entity.User;

@Service
public class RentServiceImpl implements RentService {

	@Autowired
	private RentDao rentDao;
	
	@Autowired
	private BookDao bookDao;

	@Override
	public List<Rent> findAll() {

		return rentDao.findAll();
	}

	@Override
	public Rent findOne(Long id) {
		return rentDao.findOne(id);
	}

	@Override
	public void save(Rent rent) {
		rentDao.save(rent);

	}

	@Override
	public void delete(Long id) {
		rentDao.delete(id);

	}

	@Override
	public void createRent(User user, Book book) {
		Rent rent = new Rent();
		System.out.println(book.getAvailable());
		book.setAvailable(book.getAvailable() - 1);
		System.out.println(book.getAvailable());
		// TODO sprawdzenie czy dostepnych ksiazek jest min =1 po stronie widoku
		// -> *.jsp
		rent.setUser(user);
		rent.setBook(book);
		rent.setStatus(Status.IN_PROGRESS);
		rent.createdDate = Date.from(Instant.now());

		bookDao.save(book); //save new book availability
		rentDao.save(rent);

	}

	@Override
	public List<Rent> findByUserOrderByCreatedDateDesc(User user) {
		return rentDao.findByUserOrderByCreatedDateDesc(user);
	}

}
