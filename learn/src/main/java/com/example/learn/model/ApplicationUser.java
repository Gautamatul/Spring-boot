package com.example.learn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "application_user")
public class ApplicationUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8481152163194773505L;

	public ApplicationUser() {
		super();
	}

	public ApplicationUser(Long id, @Size(min = 5, message = "Length should be greater than 10") String username,
			@Size(min = 7, max = 15, message = "password should be between 8 to 15 charector") String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "user_name")
	private String username;

	@NotNull
	@Column(name = "password")
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
