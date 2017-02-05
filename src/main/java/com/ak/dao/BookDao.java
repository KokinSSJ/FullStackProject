package com.ak.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ak.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Long>{

	
}
