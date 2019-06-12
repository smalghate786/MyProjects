package com.tyss.crud.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "STUDENT")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    @NotEmpty(message = "Please enter name")
	@Column(name = "NAME")
	String name;
    @NotEmpty(message = "Please enter age")
	@Column(name = "AGE")
	String age;
    @NotEmpty(message = "Please enter address")
	@Column(name = "ADDRESS")
	String address;
    @NotEmpty(message = "Please enter password")
	@Column(name = "PASSWORD")
	String password;
}
