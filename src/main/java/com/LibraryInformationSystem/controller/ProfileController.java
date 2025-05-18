package com.LibraryInformationSystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;

import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.model.UserModel;
import com.LibraryInformationSystem.service.DashboardService;
import com.LibraryInformationSystem.service.ProfileService;
import com.LibraryInformationSystem.util.ImageUtil;
import com.LibraryInformationSystem.util.PasswordUtil;
import com.LibraryInformationSystem.util.SessionUtil;
import com.LibraryInformationSystem.util.ValidationUtil;

/**
 * Servlet implementation class ProfileController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/profile" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB

public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ImageUtil imageUtil = new ImageUtil();
	private final ProfileService profileService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileController() {
		this.profileService = new ProfileService();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = (String) SessionUtil.getAttribute(request, "username");

		UserModel userProfile = profileService.getUserProfile(username);

		if (userProfile != null) {
			request.setAttribute("userProfile", userProfile);

			request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
		} else {

			handleError(request, response, "Could not load profile data. Please try again.");
			request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("profile".equals(action)) {
			try {
				String validationMessage = validateProfileUpdateForm(request);
				if (validationMessage != null) {
					handleError(request, response, validationMessage);
					return;
				}

				UserModel userModelProfile = extractUpdatedUserModel(request);
				Boolean isUpdated = profileService.updateProfile(userModelProfile);

				if (isUpdated == false) {

					handleError(request, response, "Our server is under maintenance. Please try again later!");
				} else if (isUpdated) {
					handleSuccess(request, response, "Your account is updated successfully!");

				} else {
					handleError(request, response, "Could not update your account. Please try again later!");
				}

			} catch (Exception e) {
				handleError(request, response, "An unexpected error occurred. Please try again later!");
				e.printStackTrace(); // Log the exception
			}
		} else if ("password".equals(action)) {
			try {
				String oldPassword = request.getParameter("oldPassword");
				String username = (String) SessionUtil.getAttribute(request, "username");

				UserModel userModelPwCheck = new UserModel(username, oldPassword);
				Boolean correctPw = profileService.checkPassword(userModelPwCheck);

				if (correctPw != null && correctPw) {
					String newPassword = request.getParameter("newPassword");
					String retypePassword = request.getParameter("retypePassword");

					if (!ValidationUtil.doPasswordsMatch(newPassword, retypePassword)) {
						handleError(request, response, "Passwords do not match.");
					} else {
						UserModel userModelPw = new UserModel(username, newPassword);
						Boolean pwUpdateStatus = profileService.updatePassword(userModelPw);
						if (pwUpdateStatus == null) {
							handleError(request, response, "Our server is under maintenance. Please try again later!");
						} else if (pwUpdateStatus == true) {
							handleSuccess(request, response, "Password successfully updated");
						} else {
							handleError(request, response, "Could not update your password. Please try again later!");

						}
					}
				} else {
					handleError(request, response, "Old password is incorrect.");
				}

			} catch (Exception e) {
				handleError(request, response, "An unexpected error occurred. Please try again later!");
				e.printStackTrace(); // Log the exception
			}

		} else if ("photo".equals(action)) {

			try {

				String username = (String) SessionUtil.getAttribute(request, "username");

				Part image = request.getPart("image");

				if (!ValidationUtil.isValidImageExtension(image)) {
					handleError(request, response, "Invalid image format. Only jpg, jpeg, png, and gif are allowed.");
					return;
				} else {
					String imageName = imageUtil.getImageNameFromPart(image);

					UserModel userModelImage = new UserModel(username, imageName, 1);
					Boolean imageUploaded = profileService.updateImage(userModelImage);

					System.out.println(imageUploaded);

					if (imageUploaded == null) {
						handleError(request, response, "Our server is under maintenance. Please try again later!");

					} else if (imageUploaded) {
						try {
							if (uploadImage(request)) {
								handleSuccess(request, response, "Your image is successfully updated!");
							} else {
								handleError(request, response, "Could not upload the image. Please try again later!");
							}
						} catch (IOException | ServletException e) {
							handleError(request, response,
									"An error occurred while uploading the image. Please try again later!");
							e.printStackTrace(); // Log the exception
						}
					} else {
						handleError(request, response, "Could not update image. Please try again later!");
					}

				}

			} catch (Exception e) {
				handleError(request, response, "An unexpected error occurred. Please try again later!");
				e.printStackTrace();
			}
		} else {
			handleError(request, response, "Invalid Action");
		}

	}

	private String validateProfileUpdateForm(HttpServletRequest req) {
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String userEmail = req.getParameter("email");

		// Check for null or empty fields first
		if (ValidationUtil.isNullOrEmpty(fullName))
			return "First name is required.";
		if (ValidationUtil.isNullOrEmpty(username))
			return "Username is required.";
		if (ValidationUtil.isNullOrEmpty(userEmail))
			return "Email is required.";

		if (!ValidationUtil.isValidEmail(userEmail))
			return "Invalid email format.";

		return null; // All validations passed
	}

	private UserModel extractUpdatedUserModel(HttpServletRequest req) throws Exception {
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String userEmail = req.getParameter("email");

		return new UserModel(fullName, username, userEmail);

	}

	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "user");
	}

	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("success", message);

		String username = (String) SessionUtil.getAttribute(req, "username");
		UserModel userProfile = profileService.getUserProfile(username);

		req.setAttribute("userProfile", userProfile);

		req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);

		req.setAttribute("fullName", req.getParameter("fullName"));
		req.setAttribute("email", req.getParameter("email"));

		String username = (String) SessionUtil.getAttribute(req, "username");
		UserModel userProfile = profileService.getUserProfile(username);

		req.setAttribute("userProfile", userProfile);

		req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
	}

}
