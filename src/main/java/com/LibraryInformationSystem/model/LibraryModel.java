package com.LibraryInformationSystem.model;

public class LibraryModel {
	private int libraryId;
	private String libraryName;
	private String location;
	private String library_email;
	private int library_contact;
	private String total_books;
	
	public LibraryModel(int libraryId, String libraryName, String location, String library_email, int library_contact,
			String total_books) {
		super();
		this.libraryId = libraryId;
		this.libraryName = libraryName;
		this.location = location;
		this.library_email = library_email;
		this.library_contact = library_contact;
		this.total_books = total_books;
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

	public String getLibrary_email() {
		return library_email;
	}

	public void setLibrary_email(String library_email) {
		this.library_email = library_email;
	}

	public int getLibrary_contact() {
		return library_contact;
	}

	public void setLibrary_contact(int library_contact) {
		this.library_contact = library_contact;
	}

	public String getTotal_books() {
		return total_books;
	}

	public void setTotal_books(String total_books) {
		this.total_books = total_books;
	}
	
	
	
	

}
