package com.ak.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ak.entity.ResetPasswordToken;



@Repository
public interface ResetPasswordTokenDao extends JpaRepository<ResetPasswordToken, Long>{

	ResetPasswordToken findByToken(String token);
	List<ResetPasswordToken> findAllByUserId(Long id);
		
}
