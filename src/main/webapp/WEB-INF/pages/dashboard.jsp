<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/dashboard.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<main>


		<div class="dashboard-container">

			<h1>Admin Dashboard</h1>

			<div class="stats-section">
				<div class="stat-card small">
					<h2>Total Users</h2>
					<br>
					<p>${userCount != -1 ? userCount : 'Error'}</p>
				</div>
				<div class="stat-card small">
				
					<h2>Total Libraries</h2>
					<br>
					<p>${libraryCount != -1 ? libraryCount : 'Error'}</p>
				</div>
				<div class="stat-card small">
					<h2>Highest Rated Library</h2>
					<br>
					<c:if test="${not empty highestRatedLibrary}">
						<p>${highestRatedLibrary.key}</p>
						<p>
							"${highestRatedLibrary.value}"
							/ 5
						</p>
					</c:if>
					<c:if test="${empty highestRatedLibrary}">
						<p>No libraries reviewed yet.</p>
					</c:if>
				</div>
				<div class="stat-card small">
					<h2>Top Reviewing Users</h2>
					<c:if test="${not empty topReviewingUsers}">
						<table class="styled-table">
							<thead>
								<tr>
									<th>Username</th>
									<th>Review Count</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="userEntry" items="${topReviewingUsers}">
									<tr>
										<td>${userEntry.key}</td>
										<td>${userEntry.value}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty topReviewingUsers}">
						<p>No top reviewers yet.</p>
					</c:if>
				</div>

				<div class="stat-card wide">
					<h2>Recent Reviews</h2>
					<c:if test="${not empty recentReviews}">
						<table class="styled-table">
							<thead>
								<tr>
									<th>User</th>
									<th>Rating</th>
									<th>Message</th>
									<th>Time</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="review" items="${recentReviews}">
									<tr>
										<td>${review.username}</td>
										<td>⭐ ${review.rating}/5</td>
										<td>${review.reviewMessage}</td>
										<td><${review.createdTime}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty recentReviews}">
						<p>No recent reviews yet.</p>
					</c:if>
				</div>

				

				
			</div>

			<h2>Manage Libraries</h2>

			<div class="table-container">
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Library Name</th>
							<th>Location</th>
							<th>Email</th>
							<th>Contact</th>
							<th>Total Books</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>


						<!-- Using JSTL forEach loop to display library data -->
						<c:forEach var="library" items="${libraryList}">
							<tr>
								<td>${library.libraryId}</td>
								<td>${library.libraryName}</td>
								<td>${library.location}</td>
								<td>${library.libraryEmail}</td>
								<td>${library.libraryContact}</td>
								<td>${library.totalBooks}</td>
								<td>
									<form action="dashboard" method="get">
										<input type="hidden" name="action" value="edit" /> <input
											type="hidden" name="libraryId" value="${library.libraryId}" />
										<button class="edit-btn">Edit</button>
									</form>

									<form action="dashboard" method="post">
										<input type="hidden" name="action" value="delete" /> <input
											type="hidden" name="libraryId" value="${library.libraryId}" />
										<button class="delete-btn">Delete</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<form class="add-form" action="dashboard" method="post">
					<%-- Add a hidden input to indicate the action (add or update) --%>
					<%-- Also add a hidden input for libraryId ONLY when updating --%>
					<c:if test="${editingLibrary != null}">
						<input type="hidden" name="action" value="update" />
						<%-- Indicate update action --%>
						<input type="hidden" name="libraryId"
							value="${editingLibrary.libraryId}" />
						<%-- Pass the ID for updating --%>
					</c:if>
					<c:if test="${editingLibrary == null}">
						<input type="hidden" name="action" value="add" />
						<%-- Indicate add action --%>
					</c:if>


					<input type="text" placeholder="Library Name" name="name"
						value="${editingLibrary != null ? editingLibrary.libraryName : ''}" />
					<input type="text" placeholder="Location" name="location"
						value="${editingLibrary != null ? editingLibrary.location : ''}" />
					<input type="text" placeholder="Email" name="email"
						value="${editingLibrary != null ? editingLibrary.libraryEmail : ''}" />
					<input type="text" placeholder="Contact" name="contact"
						value="${editingLibrary != null ? editingLibrary.libraryContact : ''}" />
					<input type="text" placeholder="Total Books" name="totalBooks"
						value="${editingLibrary != null ? editingLibrary.totalBooks : ''}" />

					<!-- Display error message if available -->
					<c:if test="${not empty error}">
						<p class="error-message">${error}</p>
					</c:if>

					<!-- Display success message if available -->
					<c:if test="${not empty success}">
						<p class="success-message">${success}</p>
					</c:if>
					<br>
					<button type="submit" class="add-btn">
						<c:if test="${editingLibrary != null}">Update Library</c:if>
						<c:if test="${editingLibrary == null}">Add Library</c:if>
					</button>
				</form>
			</div>

		</div>


	</main>

	<jsp:include page="footer.jsp" />

</body>
</html>
