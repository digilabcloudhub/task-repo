package com.neueda.atm.task.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {

	@Email(message = "invalid email")
	@NotNull
	private String email;

	@NotNull
	@Size(min = 6, max = 10, message = "password must be {min} to {max} characters long")
	private String password;

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

	@Override
	public String toString() {
		return "RegistrationRequest [email=" + email + ", password=" + password + "]";
	}

}
