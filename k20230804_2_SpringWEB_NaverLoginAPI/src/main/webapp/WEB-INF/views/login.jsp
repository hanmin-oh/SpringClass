<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 로그인 API</title>
<script type="text/javascript"	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"	charset="utf-8"></script>
<script type="text/javascript"	src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>

<div>
	<h3>네이버 로그인</h3>
</div>
<br />

<!-- 네이버 로그인 버튼 노출 영역 -->
<div id="naver_id_login">
	<a href="${url}"> <img
		src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png"
		width="233" />
	</a>
</div>

${url}

</body>
</html>