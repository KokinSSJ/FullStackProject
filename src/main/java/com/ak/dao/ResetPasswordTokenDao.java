package com.ak.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ak.entity.ResetPasswordToken;

@Repository
public interface ResetPasswordTokenDao extends JpaRepository<ResetPasswordToken, Long>{

	
		
}
