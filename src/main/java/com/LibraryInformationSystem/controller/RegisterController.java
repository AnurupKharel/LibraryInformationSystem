package com.LibraryInformationSystem.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;

import com.LibraryInformationSystem.model.UserModel;
import com.LibraryInformationSystem.service.RegisterService;
import com.LibraryInformationSystem.util.PasswordUtil;
import com.LibraryInformationSystem.util.ValidationUtil;



/**
 * Servlet implementation class RegisterController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB


public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final RegisterService registerService = new RegisterService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Validate and extract user model
			String validationMessage = validateRegistrationForm(request);
			if (validationMessage != null) {
				handleError(request, response, validationMessage);
				return;
				
			}
			                              

			UserModel userModel = extractUserModel(request);
			Boolean isAdded = registerService.addUser(userModel);
			

			if (isAdded == null) {
				
				handleError(request, response, "Our server is under maintenance. Please try again later!");
			}
			else if(isAdded){
				handleSuccess(request, response, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
			}
			else {
				handleError(request, response, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			handleError(request, response, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}
	}
	
	private String validateRegistrationForm(HttpServletRequest req) {
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String userEmail = req.getParameter("userEmail");
		String password = req.getParameter("password");
		String retypePassword = req.getParameter("retypePassword");

		// Check for null or empty fields first
		if (ValidationUtil.isNullOrEmpty(fullName))
			return "First name is required.";
		if (ValidationUtil.isNullOrEmpty(username))
			return "Username is required.";
		if (ValidationUtil.isNullOrEmpty(userEmail))
			return "Email is required.";
		if (ValidationUtil.isNullOrEmpty(password))
			return "Password is required.";
		if (ValidationUtil.isNullOrEmpty(retypePassword))
			return "Please retype the password.";


		// Validate fields
		if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
			return "Username must start with a letter and contain only letters and numbers.";
		if (!ValidationUtil.isValidEmail(userEmail))
			return "Invalid email format.";
		if (!ValidationUtil.isValidPassword(password))
			return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
		if (!ValidationUtil.doPasswordsMatch(password, retypePassword))
			return "Passwords do not match.";

		
		return null; // All validations passed
	}
	
	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String userEmail = req.getParameter("userEmail");

		String password = req.getParameter("password");

		// Assuming password validation is already done in validateRegistrationForm
		password = PasswordUtil.encrypt(username, password);
		
		

		return new UserModel(fullName, username, userEmail, password);
		
	}
	
	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher(redirectPage).forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("fullName", req.getParameter("fullName"));
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("email", req.getParameter("email"));
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}


}
