<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="nav">
    

    <ul class="nav-left">
        <li class="nav-item"><a href="${pageContext.request.contextPath}/home">Home</a></li>
        

        <c:if test = "${cookie.role.value == 'customer'}">
		<li class="nav-item"><a href="#">Browse</a></li>
        </c:if>
        
        
     
		<c:if test = "${cookie.role.value == 'admin'}">
		<li class="nav-item"><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
        </c:if>
        
        

        <li class="nav-item"><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
    </ul>

    <a href="${pageContext.request.contextPath}/home"><img class="logo" src="${pageContext.request.contextPath}/resources/images/logo.png" /></a>

    <ul class="nav-right">
        <li class="nav-item"><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/about">About</a></li>
        <li class="nav-item">
        <form action = "logout" method = "post">
        <button class="logout-nav" type = "submit">Logout</button>
        </form>
        </li>
    </ul>
    
    <div class="hamburger">
          <span class="bar"></span>
          <span class="bar"></span>
          <span class="bar"></span>
       </div>
       
    <ul class="nav-mobile">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="nav-item"><a class="nav-link" href="#">Browse</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/about">About</a></li>
        
        <li class="nav-item">
        <form action = "logout" method = "post">
        <button class="logout-nav" type = "submit">Logout</button>
        </form>
        </li>
        
        
    </ul>
        
        
<script>
	const hamburger = document.querySelector(".hamburger");
	const navMobile = document.querySelector(".nav-mobile");
	
	hamburger.addEventListener("click", () => {
	  hamburger.classList.toggle("active");
	  navMobile.classList.toggle("active");
})

</script>    
</header>
