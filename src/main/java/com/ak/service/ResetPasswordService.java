package com.ak.service;

import com.ak.entity.User;

public interface ResetPasswordService {
	
	void createResetPasswordTokenForUser(String token, User user);
	
	String validateResetPasswordToken(Long id, String token);

}
