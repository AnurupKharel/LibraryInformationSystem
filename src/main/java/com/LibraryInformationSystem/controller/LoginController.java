package com.LibraryInformationSystem.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.LibraryInformationSystem.model.UserModel;
import com.LibraryInformationSystem.service.LoginService;
import com.LibraryInformationSystem.util.CookieUtil;
import com.LibraryInformationSystem.util.SessionUtil;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/login", "/"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final LoginService loginService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
		this.loginService = new LoginService();
        
    }

    /**
	 * Handles GET requests to the login page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for user login.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserModel userModel = new UserModel(username, password);
		Boolean loginStatus = loginService.loginUser(userModel);
		

		if (loginStatus != null && loginStatus) {
			SessionUtil.setAttribute(request, "username", username);
			int userId = loginService.getUserIdByName(username);
			SessionUtil.setAttribute(request, "userId", userId);
			


			if (userModel.getIsAdmin()) {
				CookieUtil.addCookie(response, "role", "admin", 5 * 30 * 6);
				
				response.sendRedirect(request.getContextPath() + "/dashboard"); // Redirect to /dashboard
			} else {
				CookieUtil.addCookie(response, "role", "customer", 5 * 30 * 6);
				response.sendRedirect(request.getContextPath() + "/home"); // Redirect to /home
			}
		} else {
			handleLoginFailure(request, response, loginStatus);
		}

		}
	
	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         HttpServletRequest object
	 * @param resp        HttpServletResponse object
	 * @param loginStatus Boolean indicating the login status
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}

}
