package com.LibraryInformationSystem.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.LibraryInformationSystem.config.DbConfig;
import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.model.ReviewModel;

public class DashboardService {
	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public DashboardService() {
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

	public boolean addLibrary(LibraryModel library) {
		if (isConnectionError)
			return false;

		String addQuery = "INSERT INTO `library` (`library_id`, `library_name`, `location`, `library_email`, `library_contact`, `total_books`) "
				+ "VALUES (NULL, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = dbConn.prepareStatement(addQuery)) {

			stmt.setString(1, library.getLibraryName());
			stmt.setString(2, library.getLocation());
			stmt.setString(3, library.getLibraryEmail());
			stmt.setString(4, library.getLibraryContact());
			stmt.setInt(5, library.getTotalBooks());

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean updateLibrary(LibraryModel library) {
		if (isConnectionError)
			return false;

		String updateQuery = "UPDATE library SET library_name = ?, location = ?, "
				+ " library_email = ?, library_contact = ?," + "total_books = ? WHERE library_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(updateQuery)) {
			stmt.setString(1, library.getLibraryName());
			stmt.setString(2, library.getLocation());
			stmt.setString(3, library.getLibraryEmail());
			stmt.setString(4, library.getLibraryContact());
			stmt.setInt(5, library.getTotalBooks());

			stmt.setInt(6, library.getLibraryId());

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteLibrary(int libraryId) {
		if (isConnectionError)
			return false;

		String deleteQuery = "DELETE FROM library WHERE library_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(deleteQuery)) {
			stmt.setInt(1, libraryId);

			int rowsDeleted = stmt.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public LibraryModel getLibraryById(int libraryId) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		// SQL query to fetch a single library by ID
		String query = "SELECT library_id, library_name, location, library_email, library_contact, total_books FROM library WHERE library_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, libraryId); // Set the library ID parameter
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				// Found the library, create and return the model
				return new LibraryModel(result.getInt("library_id"), result.getString("library_name"),
						result.getString("location"), result.getString("library_email"),
						result.getString("library_contact"), result.getInt("total_books"));
			} else {
				// Library with the given ID was not found
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Log and handle exceptions
			return null; // Return null on error
		}
	}
	
	public int getUserCount() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return -1;
		}
		
		String extractUserCountQuery = "SELECT COUNT(*) AS Total_Users FROM user;";
		try(PreparedStatement stmt = dbConn.prepareStatement(extractUserCountQuery)){
			ResultSet userCount = stmt.executeQuery();

		        if (userCount.next()) {
		            return userCount.getInt("Total_Users");
		        } else {
		            return 0;
		        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getLibraryCount() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return -1;
		}
		
		String extractLibCountQuery = "SELECT COUNT(*) AS Total_Libs FROM library;";
		try(PreparedStatement stmt = dbConn.prepareStatement(extractLibCountQuery)){
			ResultSet libCount = stmt.executeQuery();

		        if (libCount.next()) {
		            return libCount.getInt("Total_Libs");
		        } else {
		            return 0;
		        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Retrieves the 3 most recent reviews from the database, including user information.
	 *
	 * @return A list of ReviewModel objects for the most recent reviews. Returns an empty list
	 * if there is a connection error, an exception occurs during query execution, or no reviews are found.
	 */
	public ArrayList<ReviewModel> getRecentReviews() {
		ArrayList<ReviewModel> reviewList = new ArrayList<>();

		if (isConnectionError) {
			System.out.println("Connection Error!");
			return reviewList;
		}

		String extractQuery = "SELECT r.rating, r.review_message, r.created_time, u.username FROM review AS r "
				+ "JOIN user_library_review AS ulr ON r.review_id = ulr.review_id " 
				+ "JOIN user AS u ON ulr.user_id = u.user_id " 
				+ "ORDER BY r.created_time DESC LIMIT 3"; 
		try (PreparedStatement stmt = dbConn.prepareStatement(extractQuery)) {
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				
				
				reviewList.add(new ReviewModel(result.getInt("rating"), result.getString("review_message"), result.getTimestamp("created_time").toLocalDateTime(), result.getString("username")));
			}

			return reviewList;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		
		
	}
	
	/**
     * Retrieves the username and review count for the top N users with the most reviews.
     *
     * @param limit The maximum number of top users to retrieve.
     * @return A List of Map.Entry where each entry's key is the username (String)
     * and the value is the review count (Integer). Returns an empty list if there's
     * a connection error, a database error, or if no reviews/users are found.
     */
    
    public List<Map.Entry<String, Integer>> getTopReviewingUsers() {
        
        List<Map.Entry<String, Integer>> topUsersList = new ArrayList<>(); // Initialize an empty list

        if (isConnectionError) {
            System.out.println("Connection Error!");
            return topUsersList; // Return empty list on connection error
        }

        String query = "SELECT u.username, COUNT(ulr.review_id) AS review_count " +
                       "FROM user_library_review ulr " +
                       "JOIN user u ON ulr.user_id = u.user_id " +
                       "GROUP BY u.user_id, u.username " +
                       "ORDER BY review_count DESC " +
                       "LIMIT 3"; // Use a placeholder for the limit

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) { // Loop through all the results (up to the limit)
                    String username = rs.getString("username");
                    int reviewCount = rs.getInt("review_count");
                    // Add each user/count pair to the list
                    topUsersList.add(new AbstractMap.SimpleEntry<>(username, reviewCount));
                }

                return topUsersList; // Return the list (will be empty if no results)
            } // ResultSet try-with-resources closes here
        } catch (SQLException e) {
            System.err.println("Error getting top reviewing users: " + e.getMessage()); // Log the error
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list on database error for consistency
        }
    }
    
    /**
     * Retrieves the library with the highest average rating.
     *
     * @return A Map.Entry where the key is the library name (String)
     * and the value is the average rating (Double). Returns null if
     * there's a connection error, a database error, or if no libraries
     * have reviews.
     */
    // --- Changed return type to Map.Entry<String, Double> ---
    public Map.Entry<String, Double> getHighestRatedLibrary() {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null; // Return null on connection error
        }

        String query = "SELECT " +
                       "l.library_name, " + // Select library_name
                       "AVG(r.rating) AS average_rating " +
                       "FROM library l " +
                       "JOIN user_library_review ulr ON l.library_id = ulr.library_id " +
                       "JOIN review r ON ulr.review_id = r.review_id " +
                       "GROUP BY l.library_id, l.library_name " + // Group by ID and Name
                       "ORDER BY average_rating DESC " +
                       "LIMIT 1";

        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String libraryName = rs.getString("library_name");
                double averageRating = rs.getDouble("average_rating");
                System.out.println(averageRating);

                // Return a SimpleEntry containing the library name and average rating
                return new AbstractMap.SimpleEntry<>(libraryName, averageRating);

            } else {
                // No libraries have reviews
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting highest rated library: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null on database error
        }
    }


}
