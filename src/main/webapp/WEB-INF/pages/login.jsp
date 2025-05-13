
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <c:set var="contextPath" value="${pageContext.request.contextPath}" />
  <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>

  <div class="login-container">
    <img src="${contextPath}/resources/images/logo.png" class="login-logo" alt="Logo" />

    <form class="login-form" action="login" method="post">
      <h2>Welcome Back</h2>

      <div class="input-wrapper">
          <img src="${pageContext.request.contextPath}/resources/images/user.png" class="input-icon" alt="user icon" />
          <input type="text" placeholder="Username" id="username" name="username" required/>
      </div>

      <div class="input-wrapper">
          <img src="${pageContext.request.contextPath}/resources/images/padlock.png" class="input-icon" alt="lock icon" />
          <input type="password" placeholder="Password" id="password" name="password" required/>
      </div>


      <div class="forgot-password">
        <a href="#">Forgot Password?</a>
      </div>

      <button type="submit">Login</button>

      <p class="alt-option">Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up</a></p>
    </form>
  </div>

</body>
</html>

