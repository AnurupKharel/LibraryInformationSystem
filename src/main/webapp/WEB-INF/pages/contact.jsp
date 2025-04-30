<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Contact Us</title>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contact.css" />
  <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>

  <jsp:include page="header.jsp" />

  <main>
    <div class="contact-container">
      
      <form class="contact-form">
        <h2>Contact Us</h2>
        <input type="text" placeholder="Your Name" required />
        <input type="email" placeholder="Your Email" required />
        <input type="text" placeholder="Subject" required />
        <textarea placeholder="Your Message" required></textarea>
        <button type="submit">Send Message</button>
      </form>

      <div class="contact-info">
        <h3>Get in Touch</h3>
        <p><strong>Address:</strong> 123 Fictional Road, Cityville, Country</p>
        <p><strong>Phone:</strong> +1 (555) 123-4567</p>
        <p><strong>Email:</strong> support@example.com</p>
        <p><strong>Working Hours:</strong> Mon - Fri, 9AM - 6PM</p>
      </div>
      
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
