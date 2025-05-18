package com.LibraryInformationSystem.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.model.ReviewModel;
import com.LibraryInformationSystem.service.DashboardService;
import com.LibraryInformationSystem.util.ValidationUtil;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
		this.dashboardService = new DashboardService();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action"); // Get the action parameter

		if ("edit".equals(action)) {
			// This is an edit request
			String libraryIdStr = request.getParameter("libraryId");

			int libraryId = Integer.parseInt(libraryIdStr);
			// Call the service to get the specific library
			LibraryModel editingLibrary = dashboardService.getLibraryById(libraryId);

			// Put the library object into the request scope
			request.setAttribute("editingLibrary", editingLibrary);
		}
			 // --- Implement calls to statistics methods here ---

		stats(request);



		
		List<LibraryModel> libraryList = dashboardService.getAllLibraryInfo();
		request.setAttribute("libraryList", libraryList);
		request.getRequestDispatcher("WEB-INF/pages/dashboard.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {

			// Validate and extract user model

			

			

			if ("add".equals(action)) {
				
				String validationMessage = validateAdminForm(request);
				if (validationMessage != null) {
					handleError(request, response, validationMessage);
					return;

				}
				
				LibraryModel libraryModel = extractLibraryModel(request);

				boolean isAdded = dashboardService.addLibrary(libraryModel);

				if (isAdded == false) {
					handleError(request, response, "Our server is under maintenance. Please try again later!");
				} else if (isAdded) {
					handleSuccess(request, response, "Library successfully added!");
				} else {
					handleError(request, response, "Could not add library. Please try again later!");
				}

			} else if ("update".equals(action)) {
				
				String validationMessage = validateAdminForm(request);
				if (validationMessage != null) {
					handleError(request, response, validationMessage);
					return;

				}
				
				LibraryModel libraryModel = extractLibraryModel(request);

				int libraryId = Integer.parseInt(request.getParameter("libraryId"));
				libraryModel.setLibraryId(libraryId);
				boolean isUpdated = dashboardService.updateLibrary(libraryModel);

				if (isUpdated == false) {
					handleError(request, response, "Our server is under maintenance. Please try again later!");
				} else if (isUpdated) {
					handleSuccess(request, response, "Library successfully updated!");
				} else {
					handleError(request, response, "Could not update library. Please try again later!");
				}

			} else if ("delete".equals(action)) {
				// Delete Library
				int libraryId = Integer.parseInt(request.getParameter("libraryId"));
				boolean isDeleted = dashboardService.deleteLibrary(libraryId);

				if (isDeleted == false) {
					handleError(request, response, "Our server is under maintenance. Please try again later!");
				} else if (isDeleted) {
					handleSuccess(request, response, "Library successfully deleted!");
				} else {
					handleError(request, response, "Could not deleted library. Please try again later!");
				}

			} else {
				handleError(request, response, "Invalid Action");
			}


		} catch (Exception e) {
			handleError(request, response, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}

	}
	
	private void stats(HttpServletRequest request) {
		// 1. Get User Count
        int userCount = dashboardService.getUserCount();
        request.setAttribute("userCount", userCount); // Set as attribute

        // 2. Get Library Count
        int libraryCount = dashboardService.getLibraryCount();
        request.setAttribute("libraryCount", libraryCount); // Set as attribute

        // 3. Get Recent Reviews (e.g., top 3 as implemented)
        List<ReviewModel> recentReviews = dashboardService.getRecentReviews(); // Assuming getRecentReviews returns List<ReviewModel>
        request.setAttribute("recentReviews", recentReviews); // Set as attribute

        // 4. Get Top Reviewing Users (e.g., top 3)
        
         List<Map.Entry<String, Integer>> topReviewingUsers = dashboardService.getTopReviewingUsers();
        request.setAttribute("topReviewingUsers", topReviewingUsers); // Set as attribute

        // 5. Get Highest Rated Library
        Map.Entry<String, Double> highestRatedLibrary = dashboardService.getHighestRatedLibrary(); // Assuming it returns Map.Entry<String, Double>
        request.setAttribute("highestRatedLibrary", highestRatedLibrary); // Set as attribute
	}

	private String validateAdminForm(HttpServletRequest req) {
		String name = req.getParameter("name");
		String location = req.getParameter("location");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		String totalBooks = req.getParameter("totalBooks");

		// Check for null or empty fields first
		if (ValidationUtil.isNullOrEmpty(name))
			return "Library name is required.";
		if (ValidationUtil.isNullOrEmpty(location))
			return "Location is required.";
		if (ValidationUtil.isNullOrEmpty(email))
			return "Email is required.";
		if (ValidationUtil.isNullOrEmpty(contact))
			return "Contact is required.";
		if (ValidationUtil.isNullOrEmpty(totalBooks))
			return "Total books is required.";

		// Validate fields

		if (!ValidationUtil.isValidEmail(email))
			return "Invalid email format.";
		if (!ValidationUtil.isValidPhoneNumber(contact))
			return "Phone Number must be 10 digits and start with 98 or 97";
		if (!ValidationUtil.isValidTotalBooks(totalBooks))
			return "Invalid entry for total books";

		return null; // All validations passed
	}

	private LibraryModel extractLibraryModel(HttpServletRequest req) throws Exception {
		String name = req.getParameter("name");
		String location = req.getParameter("location");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		int totalBooks = Integer.parseInt(req.getParameter("totalBooks"));

		return new LibraryModel(name, location, email, contact, totalBooks);

	}

	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		stats(req);

		List<LibraryModel> libraryList = dashboardService.getAllLibraryInfo();
		req.setAttribute("libraryList", libraryList);
		req.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("name", req.getParameter("name"));
		req.setAttribute("location", req.getParameter("location"));
		req.setAttribute("email", req.getParameter("email"));
		req.setAttribute("contact", req.getParameter("contact"));
		req.setAttribute("totalBooks", req.getParameter("totalBooks"));
		
		stats(req);
		List<LibraryModel> libraryList = dashboardService.getAllLibraryInfo();
		req.setAttribute("libraryList", libraryList);

		req.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(req, resp);
	}
}
