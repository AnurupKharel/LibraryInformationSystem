<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>About Us</title>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
  
  <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/about.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
<jsp:include page = "header.jsp"/>
<main>

  <div class="about-container">
    <h1>About Us</h1>
    
    <section class="about-section">
      <h2>Who We Are</h2>
      <p>
        We are a passionate team dedicated to delivering a seamless and engaging experience to our users. 
        Our platform is designed with simplicity, usability, and elegance in mind.
      </p>
    </section>

    <section class="about-section">
      <h2>Our Mission</h2>
      <p>
        Our mission is to empower individuals through easy access to powerful tools, connecting them with 
        the resources they need to grow, explore, and succeed.
      </p>
    </section>

    <section class="about-section">
      <h2>What We Value</h2>
      <ul>
        <li>💡 Innovation</li>
        <li>⚖️ Integrity</li>
        <li>🧑‍🤝‍🧑 User-Centered Design</li>
        <li>🚀 Continuous Growth</li>
      </ul>
    </section>
  </div>
</main>

<jsp:include page = "footer.jsp"/>

<script>
	const hamburger = document.querySelector(".hamburger");
	const navMobile = document.querySelector(".nav-mobile");
	
	hamburger.addEventListener("click", () => {
	  hamburger.classList.toggle("active");
	  navMobile.classList.toggle("active");
})

</script>
</body>
</html>
