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
				<div class="stat-card">Stat 1</div>
				<div class="stat-card">Stat 2</div>
				<div class="stat-card">Stat 3</div>
				<div class="stat-card">Stat 4</div>
				<div class="stat-card">Stat 5</div>
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
										<input type="hidden" name="action" value="edit" />
										<input
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
