<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register</title>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css" />
</head>
<body>

  <div class="register-container">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png" class="register-logo" alt="Logo" />
    
    <!-- Display error message if available -->
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>

    <form class="register-form" action="register" method="post"
			enctype="multipart/form-data">
      <h2>Create Account</h2>

      <div class="input-wrapper">
        <img src="${pageContext.request.contextPath}/resources/images/user.png" class="input-icon" alt="icon" />
        <input type="text" placeholder="Full Name" id="fullName" name="fullName" value="${fullName}" required/>
      </div>

      <div class="input-wrapper">
        <img src="${pageContext.request.contextPath}/resources/images/email.png" class="input-icon" alt="icon" />
        <input type="email" placeholder="Email" id="userEmail" name="userEmail" value="${email}" required/>
      </div>

      <div class="input-wrapper">
        <img src="${pageContext.request.contextPath}/resources/images/user.png" class="input-icon" alt="icon" />
        <input type="text" placeholder="Username" id="username" name="username" value="${username}" required/>
      </div>

      <div class="input-wrapper">
        <img src="${pageContext.request.contextPath}/resources/images/padlock.png" class="input-icon" alt="icon" />
        <input type="password" placeholder="Password" id="password" name="password" required/>
      </div>

      <div class="input-wrapper">
        <img src="${pageContext.request.contextPath}/resources/images/padlock.png" class="input-icon" alt="icon" />
        <input type="password" placeholder="Confirm Password" id="retypePassword" name="retypePassword" required/>
      </div>

      <button type="submit">Register</button>

      <p class="alt-option">Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
    </form>
  </div>

</body>
</html>
