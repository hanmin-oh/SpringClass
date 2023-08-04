<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 로그인 성공</title>
<script type="text/javascript"	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"	charset="utf-8"></script>
<script type="text/javascript"	src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	$( () => {
		let name = ${result}.response.name;
		let email = ${result}.response.email;
		$('#name').html('환영합니다. ' + name + '님');
		$('#email').html(email);
	});
</script>

</head>
<body>

<h3>로그인 성공</h3>

<h2 id="name" style="text-align: center;"></h2>
<h2 id="email" style="text-align: center;"></h2>
${sessionId}

<h3>
	<a href="logout">로그아웃</a>
</h3>


</body>
</html>