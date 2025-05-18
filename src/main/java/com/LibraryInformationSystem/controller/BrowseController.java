package com.LibraryInformationSystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.model.ReviewModel;
import com.LibraryInformationSystem.service.BrowseService;
import com.LibraryInformationSystem.service.DashboardService;
import com.LibraryInformationSystem.util.SessionUtil;

/**
 * Servlet implementation class BrowseController
 */
@WebServlet("/browse")
public class BrowseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BrowseService browseService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BrowseController() {
		this.browseService = new BrowseService();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action"); // Get the action parameter

		if ("viewReviews".equals(action)) {
			// This is an edit request
			String libraryIdStr = request.getParameter("libraryId");

			int libraryId = Integer.parseInt(libraryIdStr);
			// Call the service to get the specific library
			List<ReviewModel> reviewList = browseService.getAllReviews(libraryId);
			double avgRating = 0.0;
			if (reviewList != null && !reviewList.isEmpty()) {
			    int total = 0;
			    for (ReviewModel review : reviewList) {
			        total += review.getRating();
			    }
			    avgRating = (double) total / reviewList.size();
			}

			request.setAttribute("reviewList", reviewList);
			request.setAttribute("avgRating", avgRating);

		}

		List<LibraryModel> libraryList = browseService.getAllLibraryInfo();
		request.setAttribute("libraryList", libraryList);

		request.getRequestDispatcher("WEB-INF/pages/browse.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("rate".equals(action)) {

			try {
				int libraryId = Integer.parseInt(request.getParameter("libraryId"));
				int userId = (int) SessionUtil.getAttribute(request, "userId");
				ReviewModel reviewModel = extractReviewModel(request);

				boolean isAddedHalf = browseService.addReview(reviewModel);
				int reviewId = browseService.getReviewId();

				boolean isAdded = isAddedHalf && browseService.addReviewBridge(userId, libraryId, reviewId);

				if (isAdded == false) {
					handleError(request, response, "Our server is under maintenance. Please try again later!", action);
				} else if (isAdded) {
					handleSuccess(request, response, "Review successfully added!", action);
				} else {
					handleError(request, response, "Could not add review. Please try again later!", action);
				}
			} catch (Exception e) {
				handleError(request, response, "An unexpected error occurred. Please try again later!", action);
				e.printStackTrace(); // Log the exception
			}

		} else if ("search".equals(action)) {

			try {
				String searchValue = request.getParameter("searchValue");
				List<LibraryModel> libraryList = browseService.extractLibrarySearch(searchValue);

				if (libraryList != null && libraryList.isEmpty()) {
					handleError(request, response, "No results found", action);
					

				} else {
					request.setAttribute("libraryList", libraryList);
					handleSuccess(request, response, "Library found", action);
				}
			} catch (Exception e) {
				handleError(request, response, "An unexpected error occurred. Please try again later!", action);
				e.printStackTrace(); // Log the exception
			}

		} else {
			handleError(request, response, "Invalid Action", action);
		}

	}

	

	private ReviewModel extractReviewModel(HttpServletRequest req) throws Exception {
		int rating = Integer.parseInt(req.getParameter("rating"));
		String review = req.getParameter("review");

		return new ReviewModel(rating, review);

	}

	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String action)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		if (!"search".equals(action)) {
			List<LibraryModel> libraryList = browseService.getAllLibraryInfo();
			req.setAttribute("libraryList", libraryList);
		}

		req.getRequestDispatcher("/WEB-INF/pages/browse.jsp").forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message, String action)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		
		if (!"search".equals(action)) {
			List<LibraryModel> libraryList = browseService.getAllLibraryInfo();
			req.setAttribute("libraryList", libraryList);
		}


		req.getRequestDispatcher("/WEB-INF/pages/browse.jsp").forward(req, resp);
	}

}
