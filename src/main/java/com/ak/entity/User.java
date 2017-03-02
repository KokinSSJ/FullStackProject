package com.ak.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;



@Entity
@Table(name="Users")
public class User extends BaseEntity {
	
	public enum Role{USER, ADMIN}
	
	@NotNull
	@Column(name="first_name")
	private String firstName;
	
	@NotNull
	@Column(name="last_name")
	private String lastName;
	
	@Email
	@Column(name="email", nullable=false, unique=true) // unique = w bazie nie ma juz maila
	private String email;
	
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Role role=Role.USER;
	
	// NIE DAJEMy bo to podobno sié tworzy domyslnie 
//	@OneToMany(mappedBy="user")
//	private List<Rent> rent;
	

	public User() {
		super();
	}
	
	

	public User(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = Role.USER;
	}



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	


}
