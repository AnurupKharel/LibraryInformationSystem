package com.LibraryInformationSystem.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.LibraryInformationSystem.config.DbConfig;
import com.LibraryInformationSystem.model.UserModel;
import com.LibraryInformationSystem.util.PasswordUtil;

public class RegisterService {

	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public RegisterService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Registers a new user in the database.
	 *
	 * @param userModel the user details to be registered
	 * @return Boolean indicating the success of the operation
	 */
	public Boolean addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");

			return null;
		}

		String insertQuery = "INSERT INTO user (full_name, username, user_email, password, image_path)"
				+ "VALUES (?, ?, ?,?, ?)";

		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

			// Insert user details

			insertStmt.setString(1, userModel.getFullName());
			insertStmt.setString(2, userModel.getUsername());
			insertStmt.setString(3, userModel.getUserEmail());
			insertStmt.setString(4, userModel.getPassword());
			insertStmt.setString(5, userModel.getImageUrl());

			return insertStmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean checkUsername(String username) {
        String usernameQuery = "SELECT username FROM user WHERE username=?";
        try (PreparedStatement Stmt = dbConn.prepareStatement(usernameQuery)) {
            Stmt.setString(1,username);
            return Stmt.executeQuery().next();
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
