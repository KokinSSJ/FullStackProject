package com.ak.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ak.dao.ResetPasswordTokenDao;
import com.ak.entity.ResetPasswordToken;
import com.ak.entity.User;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService{
	
	@Autowired
	private ResetPasswordTokenDao resetPasswordDao;
	
	@Override
	public void createResetPasswordTokenForUser(String token, User user) {
		if(resetPasswordDao.findAllByUserId(user.getId())!=null){
			//TODO remove previous tokens!
			//if user send a few tokens, only last is valid!
			//TODO user can send only 3 reminder /day
			System.out.println("Many tokens for one user");
		}
		ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, user);
		resetPasswordDao.save(resetPasswordToken);
		
	}

	@Override
	public String validateResetPasswordToken(Long id, String token) {
		ResetPasswordToken resetPasswordToken = resetPasswordDao.findByToken(token);
		if(resetPasswordToken== null || resetPasswordToken.getUser().getId()!=id){
			return "invalid_token";
		}
		
		else if(resetPasswordToken.getVailidityDate().getTime()-new Date().getTime()<=0){
			return "expired_token";
		}
		
		User userTemp = resetPasswordToken.getUser();
		Authentication authentication = new UsernamePasswordAuthenticationToken(userTemp, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return null;
	}

}
