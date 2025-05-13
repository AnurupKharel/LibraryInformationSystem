<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Profile</title>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />

</head>
<body>
<jsp:include page = "header.jsp"/>

<main>
<div class="profile-container">
    <div class="profile-box">
      <h2>My Profile</h2>
      <form action="updateProfile" method="post">
        <div class="form-group">
          <label>Username</label>
          <input type="text" name="username" value="user123" readonly />
        </div>

        <div class="form-group">
          <label>Full Name</label>
          <input type="text" name="fullname" value="John Doe" />
        </div>

        <div class="form-group">
          <label>Email</label>
          <input type="email" name="email" value="john@example.com" />
        </div>

        <div class="form-group">
          <label>Password</label>
          <input type="password" name="password" placeholder="Enter new password" />
        </div>

        <button type="submit" class="save-btn">Update Profile</button>
      </form>
    </div>
  </div>
  </main>
  
<jsp:include page = "footer.jsp"/>

</body>
</html>
