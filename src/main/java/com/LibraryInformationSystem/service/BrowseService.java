package com.LibraryInformationSystem.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.LibraryInformationSystem.config.DbConfig;
import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.model.ReviewModel;

public class BrowseService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public BrowseService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Retrieves all libary information from the database.
	 * 
	 * @return A list of LibraryModel objects containing library data. Returns null
	 *         if there is a connection error or if an exception occurs during query
	 *         execution.
	 */
	public List<LibraryModel> getAllLibraryInfo() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		// SQL query to fetch library details
		String query = "SELECT library_id, library_name, location, library_email, library_contact, total_books FROM library";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet result = stmt.executeQuery();
			List<LibraryModel> libraryList = new ArrayList<>();

			while (result.next()) {

				// Create and add LibraryModel to the list
				libraryList.add(new LibraryModel(result.getInt("library_id"), // Library ID
						result.getString("library_name"), // Library Name
						result.getString("location"), // Location
						result.getString("library_email"), // Email
						result.getString("library_contact"), // Phone Number
						result.getInt("total_books")));
			}
			return libraryList;
		} catch (SQLException e) {
			// Log and handle exceptions related to student query execution
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves all review information from the database.
	 * 
	 * @return A list of ReviewModel objects containing library data. Returns null
	 *         if there is a connection error or if an exception occurs during query
	 *         execution.
	 */
	public List<ReviewModel> getAllReviews(int libraryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		// SQL query to fetch library details
		String query = "SELECT r.rating, r.review_message, r.created_time, u.username FROM review AS r "
				+ "JOIN user_library_review AS ulr ON r.review_id = ulr.review_id " 
				+ "JOIN user AS u ON ulr.user_id = u.user_id " 
				+ "WHERE ulr.library_id = ?"; 
		
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, libraryId);
			ResultSet result = stmt.executeQuery();
			List<ReviewModel> reviewList = new ArrayList<>();

			while (result.next()) {

				Timestamp timestamp = result.getTimestamp("created_time");
				LocalDateTime createdTime = null;
				if (timestamp != null) {
					createdTime = timestamp.toLocalDateTime();
				}

				// Create and add LibraryModel to the list
				reviewList.add(new ReviewModel(result.getInt("rating"), result.getString("review_message"), createdTime, result.getString("username") ));
			}
			return reviewList;
		} catch (SQLException e) {
			// Log and handle exceptions related to student query execution
			e.printStackTrace();
			return null;
		}
	}

	public boolean addReview(ReviewModel review) {
		if (isConnectionError)
			return false;

		String addQuery = "INSERT INTO `review` (`rating`, `review_message`, `created_time`) " + "VALUES (?, ?, ?)";

		try (PreparedStatement stmt = dbConn.prepareStatement(addQuery)) {

			stmt.setInt(1, review.getRating());
			stmt.setString(2, review.getReviewMessage());
			stmt.setObject(3, LocalDateTime.now());

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean addReviewBridge(int userId, int libraryId, int reviewId) {
		if (isConnectionError)
			return false;

		String linkReviewQuery = "INSERT INTO `user_library_review` (`user_id`, `library_id`, `review_id`) "
				+ "VALUES (?, ?, ?)";

		try (PreparedStatement stmt = dbConn.prepareStatement(linkReviewQuery)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, libraryId);
			stmt.setInt(3, reviewId);

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public int getReviewId() {

		String getIdQuery = "SELECT review_id FROM review ORDER BY created_time DESC LIMIT 1";

		try (PreparedStatement stmt = dbConn.prepareStatement(getIdQuery)) {

			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				// Found the library, create and return the model
				return (result.getInt("review_id"));

			} else {

				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Log and handle exceptions
			return -1; // Return null on error
		}
	}
	
	public ArrayList<LibraryModel> extractLibrarySearch(String searchValue) {
		ArrayList<LibraryModel> libraryList = new ArrayList<>();

		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String searchQuery = "SELECT * FROM library WHERE library_name LIKE ?";
		try(PreparedStatement stmt = dbConn.prepareStatement(searchQuery)){
			stmt.setString(1, "%" + searchValue + "%");
			

			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				libraryList.add(new LibraryModel(result.getInt("library_id"), // Library ID
						result.getString("library_name"), // Library Name
						result.getString("location"), // Location
						result.getString("library_email"), // Email
						result.getString("library_contact"), // Phone Number
						result.getInt("total_books")));
				
			}

			return libraryList;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
