package com.LibraryInformationSystem.model;

import java.time.LocalDateTime;

public class ReviewModel {
	private int reviewId;
	private int rating;
	private String reviewMessage;
	private LocalDateTime createdTime;
	private String username;

	public ReviewModel(int reviewId, int rating, String reviewMessage, LocalDateTime createdTime,  String username) {
		super();
		this.reviewId = reviewId;
		this.rating = rating;
		this.reviewMessage = reviewMessage;
		this.createdTime = createdTime;
		this.username = username;

	}
	
	public ReviewModel( int rating, String reviewMessage, LocalDateTime createdTime,  String username) {
		super();

		this.rating = rating;
		this.reviewMessage = reviewMessage;
		this.createdTime = createdTime;
		this.username = username;

	}

	public ReviewModel(int rating, String reviewMessage) {

		this.rating = rating;
		this.reviewMessage = reviewMessage;

	}

	public ReviewModel(int rating, String reviewMessage, LocalDateTime createdTime) {

		this.rating = rating;
		this.reviewMessage = reviewMessage;
		this.createdTime = createdTime;

	}
	


	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewMessage() {
		return reviewMessage;
	}

	public void setReviewMessage(String reviewMessage) {
		this.reviewMessage = reviewMessage;
	}

	public LocalDateTime getcreatedTime() {
		return createdTime;
	}

	public void setcreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
