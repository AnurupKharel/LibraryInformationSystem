package com.LibraryInformationSystem.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.LibraryInformationSystem.config.DbConfig;
import com.LibraryInformationSystem.model.LibraryModel;

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
			
			while(result.next()) {


					// Create and add LibraryModel to the list
					libraryList.add(new LibraryModel(result.getInt("library_id"), // Library ID
							result.getString("library_name"), // Library Name
							result.getString("location"), // Location
							result.getString("library_email"), // Email
							result.getString("library_contact"), // Phone Number
							result.getInt("total_books")
					));
			}
return libraryList;
		 }catch (SQLException e) {
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

		String updateQuery = "UPDATE library SET library_name = ?, location = ?, " + " library_email = ?, library_contact = ?,"
				+ "total_books = ? WHERE library_id = ?";
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
            return new LibraryModel(
                    result.getInt("library_id"),
                    result.getString("library_name"),
                    result.getString("location"),
                    result.getString("library_email"),
                    result.getString("library_contact"),
                    result.getInt("total_books")
            );
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
}


