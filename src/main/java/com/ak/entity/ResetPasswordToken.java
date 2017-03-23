package com.ak.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ResetPasswordToken extends BaseEntity{
	
	private static final int VALIDITY = 24 * 60 * 60 * 1000; //24 hours in millis
	
	@NotNull
	@Column(name = "token")
	private String token;
	
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@NotNull
	@Column(name="validity_date")
	private Date vailidityDate;

	public ResetPasswordToken() {
		super();
	}

	public ResetPasswordToken(String token, User user) {
		super();
		this.token = token;
		this.user = user;
		this.vailidityDate=calcValidityDate(VALIDITY);
	}
	
	
	private Date calcValidityDate(int validityTime){
		return new Date(new Date().getTime() + VALIDITY);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getVailidityDate() {
		return vailidityDate;
	}

	public void setVailidityDate(Date vailidityDate) {
		this.vailidityDate = vailidityDate;
	}
	
	
	
	
	

}
