package com.LibraryInformationSystem.model;

public class UserModel {
	private int userId;
	private String fullName;
	private String username;
	private String userEmail;
	private boolean isAdmin;
	private String password;
	private String imageUrl;
	
	public UserModel(int userId, String fullName, String username, String userEmail, boolean isAdmin, String password, String imageUrl) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.username = username;
		this.userEmail = userEmail;
		this.isAdmin = isAdmin;
		this.password = password;
		this.imageUrl = imageUrl;
		}
	
	public UserModel(String fullName, String username, String userEmail, String password, String imageUrl) {

		this.fullName = fullName;
		this.username = username;
		this.userEmail = userEmail;
		this.password = password;
		this.imageUrl = imageUrl;

	}
	
	public UserModel(String fullName, String username, String userEmail, String imageUrl) {

		this.fullName = fullName;
		this.username = username;
		this.userEmail = userEmail;
		this.imageUrl = imageUrl;

	}
	
	public UserModel(String fullName, String username, String userEmail) {

		this.fullName = fullName;
		this.username = username;
		this.userEmail = userEmail;

	}

	
	public UserModel(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UserModel(String username, String imageUrl, int a) {
		this.username = username;
		this.imageUrl = imageUrl;
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

	public void setUserEmail(String userEmail) {
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
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	

}
