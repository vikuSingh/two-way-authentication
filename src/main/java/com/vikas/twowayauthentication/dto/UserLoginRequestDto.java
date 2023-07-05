package com.vikas.twowayauthentication.dto;

public class UserLoginRequestDto {

	private String username;

	private String password;

	private int otp;

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

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "UserLoginRequestDto [username=" + username + ", password=" + password + ", otp=" + otp + "]";
	}

}
