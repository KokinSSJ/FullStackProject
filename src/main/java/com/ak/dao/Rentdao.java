package com.ak.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ak.entity.Rent;
import com.ak.entity.User;

@Repository
public interface RentDao extends JpaRepository<Rent, Long> {

	//zwrocic liste wypozyczen danego usera posortowana nie rosnaco ze wzg na createdDate
	List<Rent> findByUserOrderByCreatedDateDesc(User user); //createdDate -> nazwa musi byc z malej litery w encji rent
	
	
}
