package com.ak.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass //mowi że tabela ksiazka, wypozyczenie i user maja 
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //gdy się zmieni na inne to trzeba nową scheme w mySQL stworzyć! nowe ustawienia tabel itd
	@Column(name="id")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	

}
