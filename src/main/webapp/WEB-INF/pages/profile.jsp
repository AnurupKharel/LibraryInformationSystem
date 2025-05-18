<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Profile</title>
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/profile.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
<c:set var="userProfile" value="${requestScope.userProfile}"></c:set>

</head>
<body>
	<jsp:include page="header.jsp" />

	<main>

		<!-- Display error message if available -->
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
			<br>
		</c:if>
		<div class="profile-wrapper">
			<div class="profile-picture-section">
				<div class="profile-image-container">
					<img
						src="${pageContext.request.contextPath}/resources/imagesuser/${userProfile.imageUrl}"
						alt="Profile Picture" id="profilePreview" name = "profilePreview">
				</div>
				<form action="profile" method="post" enctype="multipart/form-data">
					<input type="hidden" name="action" value="photo" /> 
					<input
						type="file" name="image" id="image" 
						onchange="previewImage(event)">
					<button type="submit" class="upload-btn">Change Picture</button>
				</form>
			</div>

			<div class="profile-form-section">


				<h3>My Profile</h3>


				<form action="profile" method="post">
					<input type="hidden" name="action" value="profile" />


					<!-- Username (not editable) -->
					<div class="input-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/user.png"
							class="input-icon" alt="icon" /> <input type="text"
							name="username" id="username" value="${username}" readonly />
					</div>

					<!-- Full Name -->
					<div class="input-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/user.png"
							class="input-icon" alt="icon" /> <input type="text"
							name="fullName" id="fullName" value="${userProfile.fullName}"
							placeholder="Full Name" />
					</div>

					<!-- Email -->
					<div class="input-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/email.png"
							class="input-icon" alt="icon" /> <input type="email"
							name="email" id="email" value="${userProfile.userEmail}"
							placeholder="Email" />
					</div>
					<button type="submit" class="save-btn">Update Profile</button>
				</form>
				<br>
				<h3>Change password</h3>
				
				<form action="profile" method="post">
					<input type="hidden" name="action" value="password" />

					<!-- Password -->
					<div class="input-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/padlock.png"
							class="input-icon" alt="icon" /> <input type="password"
							name="oldPassword" id="oldPassword"
							placeholder="Enter old password" />
					</div>

					<div class="input-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/padlock.png"
							class="input-icon" alt="icon" /> <input type="password"
							name="newPassword" id="newPassword"
							placeholder="Enter new password" />
					</div>

					<div class="input-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/padlock.png"
							class="input-icon" alt="icon" /> <input type="password"
							name="retypePassword" id="retypePassword"
							placeholder="Retype password" />
					</div>


					<button type="submit" class="save-btn">Change Password</button>
				</form>
			</div>
		</div>


	</main>
	<script>
		function previewImage(event) {
			const reader = new FileReader();
			reader.onload = function() {
				document.getElementById('profilePreview').src = reader.result;
			};
			reader.readAsDataURL(event.target.files[0]);
		}
	</script>

	<jsp:include page="footer.jsp" />


</body>
</html>
