package com.personal.financial_dashboard_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserDTO {

	private Long id;

	@NotEmpty(message = "Username is required.")
	@Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
	private String username;

	@NotEmpty(message = "Email is required.")
	@Email(message = "Email should be valid.")
	private String email;

	@NotEmpty(message = "Password is required.")
	@Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters.")
	private String password;

	@Size(max = 50, message = "First name can have a maximum of 50 characters.")
	private String firstName;

	@Size(max = 50, message = "Last name can have a maximum of 50 characters.")
	private String lastName;

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
}