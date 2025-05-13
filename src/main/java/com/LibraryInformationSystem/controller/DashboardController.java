package com.LibraryInformationSystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.LibraryInformationSystem.model.LibraryModel;
import com.LibraryInformationSystem.service.DashboardService;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/dashboard"})
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action"); // Get the action parameter

		    if ("edit".equals(action)) {
		        // This is an edit request
		        String libraryIdStr = request.getParameter("libraryId");
		        if (libraryIdStr != null && !libraryIdStr.isEmpty()) {
		            try {
		                int libraryId = Integer.parseInt(libraryIdStr);
		                // Call the service to get the specific library
		                LibraryModel editingLibrary = dashboardService.getLibraryById(libraryId);

		                if (editingLibrary != null) {
		                    // Put the library object into the request scope
		                    request.setAttribute("editingLibrary", editingLibrary);
		                } else {
		                    // Library not found - set an error message
		                    request.setAttribute("errorMessage", "Library with ID " + libraryId + " not found.");
		                }

		            } catch (NumberFormatException e) {
		                // Handle case where libraryId parameter is not a valid number
		                request.setAttribute("errorMessage", "Invalid library ID format.");
		                e.printStackTrace(); // Log the error
		            } catch (Exception e) {
		                 // Catch any other errors from the service
		                 request.setAttribute("errorMessage", "Error retrieving library for editing.");
		                 e.printStackTrace(); // Log the error
		            }
		        } else {
		             // Handle case where libraryId parameter is missing
		             request.setAttribute("errorMessage", "Library ID is missing for editing.");
		        }
		    }
		List<LibraryModel> libraryList = dashboardService.getAllLibraryInfo();
		request.setAttribute("libraryList", libraryList);
		request.getRequestDispatcher("WEB-INF/pages/dashboard.jsp").forward(request, response);	
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

	    if ("add".equals(action)) {
	        // Add Library
	        String name = request.getParameter("name");
	        String location = request.getParameter("location");
	        String email = request.getParameter("email");
	        String contact = request.getParameter("contact");
	        int totalBooks = Integer.parseInt(request.getParameter("totalBooks"));

	        LibraryModel libraryModel = new LibraryModel(name, location, email, contact, totalBooks);
	        Boolean result = dashboardService.addLibrary(libraryModel);

	        if (result) {
	            response.sendRedirect(request.getContextPath() + "/dashboard");
	        } else {
	            response.getWriter().println("Error: Library not inserted");
	        }

	    } else if ("update".equals(action)) {
	        // Update Library
	        int libraryId = Integer.parseInt(request.getParameter("libraryId"));
	        String name = request.getParameter("name");
	        String location = request.getParameter("location");
	        String email = request.getParameter("email");
	        String contact = request.getParameter("contact");
	        int totalBooks = Integer.parseInt(request.getParameter("totalBooks"));

	        LibraryModel libraryModel = new LibraryModel(libraryId, name, location, email, contact, totalBooks);
	        Boolean result = dashboardService.updateLibrary(libraryModel);

	        if (result) {
	            response.sendRedirect(request.getContextPath() + "/dashboard");
	        } else {
	            response.getWriter().println("Error: Library not updated");
	        }

	    } else if ("delete".equals(action)) {
	        // Delete Library
	        int libraryId = Integer.parseInt(request.getParameter("libraryId"));
	        boolean result = dashboardService.deleteLibrary(libraryId);

	        if (result) {
	            response.sendRedirect(request.getContextPath() + "/dashboard");
	        } else {
	            response.getWriter().println("Error: Library not deleted");
	        }
	    } else {
	        response.getWriter().println("Invalid action!");
	    }

}
	}
