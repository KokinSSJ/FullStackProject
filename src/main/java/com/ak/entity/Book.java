package com.ak.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="Books")
public class Book extends BaseEntity {
	
	@NotNull
	@Column(name="author")
	@Size(min=3, max=255, message="Nie poprawna dlugosc authora")
	private String author;
	
	@NotNull
	@Column(name="title")
	private String title;
	
	
	@Min(0)
	@Column(name="available")
	private Integer availabe; //ilosc ksiazek
	
	// NIE DAJEMY bo to sié tworzy podobno domyslnie!
//	@OneToMany(mappedBy="book")
//	private List<Rent> rent;
	

	public Book() {
		super();
	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getAvailabe() {
		return availabe;
	}

	public void setAvailabe(Integer availabe) {
		this.availabe = availabe;
	}
	
	

}