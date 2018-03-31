package com.example.okc.auth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.PersistenceConstructor;

@Entity
@Table(name = "user")
public class User {
	private Long id;
	@NotNull(message = "NotEmpty")
	@Size(min = 6, max = 32, message = "Size.userForm.username")
	private String username;
	@NotNull(message = "NotEmpty")
	@Size(min = 6, max = 128, message = "Size.userForm.password")
	private String password;
	@Transient
	private String passwordConfirm;

	@PersistenceConstructor
	User() {
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
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

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
