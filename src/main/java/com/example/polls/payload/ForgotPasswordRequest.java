package com.example.polls.payload;

import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

	@NotBlank
    private String email;

	@NotBlank
    private String pwd;

	
	public String getPassword() {
		return pwd;
	}

	public void setPassword(String password) {
		this.pwd = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
