<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">


<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/home.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />

	
</head>
<body>
<jsp:include page = "header.jsp"/>

<main>
 

  <!-- Main Intro Text -->
  <section class="banner">
    <img src="${pageContext.request.contextPath}/resources/images/banner.png" alt="Banner" />
  </section>

  <!-- CTA Section -->
  <section class="cta-section">
    <div class="cta-card">
      <img src="${pageContext.request.contextPath}/resources/images/profile.jpg" alt="Update Profile" />
      <h3>Update Your Profile</h3>
      <a href="#" class="cta-button">Update</a>
    </div>

    <div class="cta-card">
      <img src="${pageContext.request.contextPath}/resources/images/browse.jpg" alt="Browse Libraries" />
      <h3>Browse Libraries</h3>
      <a href="#" class="cta-button">Browse</a>
    </div>

    <div class="cta-card">
      <img src="${pageContext.request.contextPath}/resources/images/about.jpg" alt="About Us" />
      <h3>Learn About Us</h3>
      <a href="${pageContext.request.contextPath}/about" class="cta-button">Learn More</a>
    </div>
  </section>


</main>

  

<jsp:include page = "footer.jsp"/>


</body>
</html>