<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Browse</title>
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/browse.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<main>
		<h1>Browse Libraries</h1>
		<form action="browse" method="post">
			<div class="search-container">
				<input type="text" id="librarySearch" name=searchValue
					placeholder="Search libraries..." /> <input type="hidden"
					name="action" value="search" />
				<button id="searchButton">Search</button>
			</div>
		</form>

		<div class="table-container">
			<!-- Display error message if available -->
			<c:if test="${not empty error}">
				<p class="error-message">${error}</p>
			</c:if>

			<!-- Display success message if available -->
			<c:if test="${not empty success}">
				<p class="success-message">${success}</p>
			</c:if>
			<br>
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
								<form action="browse" method="get">
									<input type="hidden" name="action" value="viewReviews" /> <input
										type="hidden" name="libraryId" value="${library.libraryId}" />
									<button class="edit-btn view-reviews-btn" type="submit"
										data-libraryid="${library.libraryId}">View Reviews</button>

								</form>

								<form action="browse" method="post">
									<input type="hidden" name="action" value="rate" /> <input
										type="hidden" name="libraryId" value="${library.libraryId}" />
									<button class="delete-btn rate-btn" type="button"
										data-libraryid="${library.libraryId}">Rate</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- Rating Modal -->
			<div id="ratingModal" class="modal">
				<div class="modal-content">
					<span class="close">&times;</span>
					<h2>Rate Library</h2>
					<form id="ratingForm" action="browse" method="post">
						<input type="hidden" name="action" value="rate" /> <input
							type="hidden" name="libraryId" id="modalLibraryId" /> <label
							for="rating">Rating (1 to 5):</label> <select name="rating"
							id="rating">
							<option value="" disabled selected>Select rating</option>
							<c:forEach var="i" begin="1" end="5">
								<option value="${i}">${i}</option>
							</c:forEach>
						</select> <label for="review">Review:</label>
						<textarea name="review" id="review" rows="4"
							placeholder="Write your review here..."></textarea>

						<button type="submit" class="submit-btn">Submit</button>
					</form>
				</div>
			</div>

			<!-- View Reviews Modal -->
			<div id="viewReviewsModal" class="modal">
				<div class="modal-content">
					<span class="close-view-reviews">&times;</span>
					<h2>Reviews</h2>

					<c:if test="${not empty reviewList}">
						<div class="average-rating">
							<h3>
								Average Rating:
								<fmt:formatNumber value="${avgRating}" type="number"
									minFractionDigits="1" maxFractionDigits="2" />
								/ 5
							</h3>
						</div>
					</c:if>


					<!--reviews -->
					<c:forEach var="review" items="${reviewList}">
						<div class="review-entry">
							<div class="review-header">
								<span class="review-username">${review.username}</span> <span
									class="review-rating">⭐ ${review.rating}/5</span><span
									class="review-rating"> ${review.createdTime}</span>
									
							</div>
							<div class="review-message">${review.reviewMessage}</div>
						</div>
					</c:forEach>

				</div>
			</div>





		</div>
	</main>
	<jsp:include page="footer.jsp" />
	<script>
  const modal = document.getElementById("ratingModal");
  const closeBtn = document.querySelector(".modal .close");

  // Show modal when any Rate button is clicked
  document.querySelectorAll(".rate-btn").forEach(button => {
    button.addEventListener("click", function (event) {
      event.preventDefault();
      const libraryId = this.getAttribute("data-libraryid");
      document.getElementById("modalLibraryId").value = libraryId;
      modal.style.display = "block";
    });
  });

  // Close modal on X click
  closeBtn.onclick = function () {
    modal.style.display = "none";
  };

  // Close modal if clicked outside
  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };
</script>

	<script>
  const viewReviewsModal = document.getElementById("viewReviewsModal");
  const closeViewReviewsBtn = document.querySelector(".close-view-reviews");

 


  
  window.onload = function() {
  
      if (${not empty reviewList}) { 
          viewReviewsModal.style.display = "block"; 
      }
       
  };


  // Close view reviews modal on X click
  closeViewReviewsBtn.addEventListener("click", () => {
    viewReviewsModal.style.display = "none";
  });

  // Close view reviews modal if clicked outside
  window.addEventListener("click", event => {
    if (event.target === viewReviewsModal) {
      viewReviewsModal.style.display = "none";
    }
  });
</script>



</body>
</html>