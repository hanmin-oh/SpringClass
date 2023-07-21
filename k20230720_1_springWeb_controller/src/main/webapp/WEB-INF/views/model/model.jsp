<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
model.jsp입니다.<br/>

<!-- 컨트롤러에서 Model 인터페이스 객체에 저장되서 넘어오는 데이터는 EL을 사용해서 받는다. -->
id: ${id}<br/>
pw: ${pw}<br/>
name : ${name}
</body>
</html>