package com.ak.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="Rents")
public class Rent extends BaseEntity {

	public enum Status {IN_PROGRESS, FINISHED};
	
	
	@Column(name="created_date", nullable=false)
	public Date createdDate;
	
	//nie dajemy manytomany zeby mozna bylo 
	@ManyToOne(fetch=FetchType.LAZY) //lazy => wyciagnie tylko wypozyczenia
	//eager -> zawsze wyciaga wszystko wypozyczenia + dane o ksiazce itd
	@JoinColumn(name="user_id")
	public User user;
	
	@ManyToOne //wiele wypozyczen przez jednego 
	@JoinColumn(name="book_id")
	public Book book;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable=false)
	public Status status;

	
	public Rent() {
		super();
	}

	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}





	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
	
	
	
}
