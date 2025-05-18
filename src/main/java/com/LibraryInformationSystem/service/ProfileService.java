package com.LibraryInformationSystem.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.LibraryInformationSystem.config.DbConfig;
import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.model.UserModel;
import com.LibraryInformationSystem.util.PasswordUtil;

public class ProfileService {
	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public ProfileService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}

	}

	public UserModel getUserProfile(String username) {
		if (isConnectionError) {
			System.out.println("ProfileService Connection Error!");
			return null;
		}

		String query = "SELECT full_name, username, user_email, is_admin, image_path FROM user WHERE username = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, username);

			try (ResultSet result = stmt.executeQuery()) {
				if (result.next()) {
					// User found - populate UserModel with profile data

					return new UserModel(result.getString("full_name"), result.getString("username"),
							result.getString("user_email"), result.getString("image_path"));
				} else {
					// User ID not found
					return null;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error retrieving user profile: " + e.getMessage());
			e.printStackTrace();
			return null; // Database error
		}
	}

	public boolean updateProfile(UserModel user) {
		if (isConnectionError)
			return false;

		String updateQuery = "UPDATE user SET full_name = ?, " + " user_email = ? " + " WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(updateQuery)) {
			stmt.setString(1, user.getFullName());
			stmt.setString(2, user.getUserEmail());
			stmt.setString(3, user.getUsername());

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public Boolean updatePassword(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "UPDATE user SET password = ?  WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			
			String password = PasswordUtil.encrypt(userModel.getUsername(), userModel.getPassword());

			stmt.setString(1, password);
			stmt.setString(2, userModel.getUsername());


			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	public Boolean checkPassword(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT username, password FROM user WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getUsername());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, userModel);

			}
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

		return false;
	}
	
	public Boolean updateImage(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "UPDATE user SET image_path = ?  WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {

			stmt.setString(1, userModel.getImageUrl());
			System.out.println(userModel.getImageUrl());
			stmt.setString(2, userModel.getUsername());


			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}
	

	

	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result    the ResultSet containing the username and password from the
	 *                  database
	 * @param userModel the StudentModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");

		return dbUsername.equals(userModel.getUsername())
				&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
	}

}
