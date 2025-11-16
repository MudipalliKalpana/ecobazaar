package com.ecobazaar.ecobazaar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	
	@NotBlank(message="Name cannot be empty")
	private String name;
	
	@NotBlank(message="email cannot be empty")
	@Email(message="Invalid Input, enter valid mail!")
	private String email;
	
	@NotBlank(message="password should not be empty")
	@Size(min=5, message="password should have atleast 5 digits!")
	private String password;

	public RegisterRequest() {}

	public RegisterRequest(@NotBlank(message = "Name is Required") String name,
			@NotBlank(message = "EMail is Required") @Email(message = "Enter a valid email") String email,
			@NotBlank(message = "Password is required") @Size(min = 4, message = "Password must be 4 characters") String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
}
