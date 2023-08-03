<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>login.jsp</h1>
	
	
<!-- userPrincioal : 사용자 권한 => 사용자 권한이 비어있지 않다면 => 누군가 로그인을 했다면 -->
<c:if test="${not empty pageContext.request.userPrincipal}">	<!-- 로그인 상태인가? -->
	<p>
		<!-- userPrincipal.name : 시큐리티 기능으로 로그인한 아이디를 얻어온다. -->
		${pageContext.request.userPrincipal.name} Log-In
	</p>
</c:if>

<!-- spring-security-taglibs를 사용해 ROLE_USER 그룹으로 들어온 사용자가 있나 확인한다. -->
<s:authorize ifAnyGranted="ROLE_USER">
	<p>
		<s:authentication property="name"/> Log-In
	</p>
</s:authorize>


<c:if test="${empty pageContext.request.userPrincipal}">	<!-- 로그아웃 상태인가? -->
	<p>Log-Out</p>
</c:if>

<!-- logout -->
<a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a>

</body>
</html>