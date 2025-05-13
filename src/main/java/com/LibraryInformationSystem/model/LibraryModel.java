package com.LibraryInformationSystem.model;

public class LibraryModel {
	private int libraryId;
	private String libraryName;
	private String location;
	private String libraryEmail;
	private String libraryContact;
	private int totalBooks;
	
	public LibraryModel(int libraryId, String libraryName, String location, String libraryEmail, String libraryContact,
			int totalBooks) {
		super();
		this.libraryId = libraryId;
		this.libraryName = libraryName;
		this.location = location;
		this.libraryEmail = libraryEmail;
		this.libraryContact = libraryContact;
		this.totalBooks = totalBooks;
	}
	
	public LibraryModel(String libraryName, String location, String libraryEmail, String libraryContact,
			int totalBooks) {
		super();
		this.libraryName = libraryName;
		this.location = location;
		this.libraryEmail = libraryEmail;
		this.libraryContact = libraryContact;
		this.totalBooks = totalBooks;
	}
	
	public LibraryModel(String libraryName) {
		this.libraryName = libraryName;
	}

	public int getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(int libraryId) {
		this.libraryId = libraryId;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLibraryEmail() {
		return libraryEmail;
	}

	public void setLibraryEmail(String libraryEmail) {
		this.libraryEmail = libraryEmail;
	}

	public String getLibraryContact() {
		return libraryContact;
	}

	public void setLibraryContact(String libraryContact) {
		this.libraryContact = libraryContact;
	}

	public int getTotalBooks() {
		return totalBooks;
	}

	public void setTotalBooks(int totalBooks) {
		this.totalBooks = totalBooks;
	}
	
	
	
	

}
