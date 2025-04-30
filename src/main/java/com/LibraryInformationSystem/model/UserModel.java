package com.LibraryInformationSystem.model;

public class UserModel {
	private int userId;
	private String fullName;
	private String username;
	private String userEmail;
	private boolean isAdmin;
	private String password;
	
	public UserModel(int userId, String fullName, String username, String userEmail, boolean isAdmin, String password) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.username = username;
		this.userEmail = userEmail;
		this.isAdmin = isAdmin;
		this.password = password;
	}
	
	public UserModel(String fullName, String username, String userEmail, String password) {

		this.fullName = fullName;
		this.username = username;
		this.userEmail = userEmail;
		this.password = password;
	}
	
	public UserModel(String username, String pasword) {
		this.username = username;
		this.password = pasword;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUser_email(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
