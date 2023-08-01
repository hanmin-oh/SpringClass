<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jstl -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- bootstrap icon -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
	<table class="table" style="width: 700px; margin-left: auto; margin-right: auto;">
		<tr class="table-primary">
			<th colspan="5" style="font-size: 30px; text-align: center;">게시판 보기</th>
		</tr>
		<tr>
			<td colspan="5" align="right">
				${boardList.totalCount}(${boardList.currentPage} / ${boardList.totalPage})
			</td>
		</tr>
		<tr class="table-success">
			<td style="width: 100px; text-align: center;">글번호</td>
			<td style="width: 300px; text-align: center;">제목</td>
			<td style="width: 100px; text-align: center;">이름</td>
			<td style="width: 150px; text-align: center;">작성일</td>
			<td style="width: 70px; text-align: center;">조회수</td>
		</tr>
		 
		<c:set var="list" value="${boardList.list}"/>
		<!-- 메인글이 1건도 없으면 글이 없다고 출력한다. -->
		<c:if test="${list.size() == 0}">
		<tr>
			<td colspan="5">
				<marquee>테이블에 저장된 글이 없습니다.</marquee>
			</td>
		</tr>
		</c:if> 
		<c:if test="${list.size() != 0}">
			<c:forEach var="vo" items="${list}">
			<tr>
				<td align="center">${vo.idx}</td>
				<td>
	                <c:if test="${vo.lev > 0}" >
	                    <c:forEach var="i" begin="1" end="${vo.lev}" step="1">
	                        &nbsp;&nbsp;&nbsp;&nbsp;
	                    </c:forEach>
	                    Re.
	                    <img src="images/arrow.png"/>
	                </c:if>
					<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt;')}"/>
					<c:set var="subject" value="${fn:replace(subject, '>', '&gt;')}"/>
					<!-- 하이퍼링크를 클릭하면 조회수를 증가시키고 클릭한 메인글의 내용을 표시한다. -->
					<a href="increment?idx=${vo.idx}&currentPage=${boardList.currentPage}">
						${subject}
					</a>
					
					<c:if test="${vo.hit > 10 }">
						<img alt="HOT" src="images/hot.gif"/>
					</c:if>
				</td>
				<td align="center">
					<!-- 이름에 태그를 적용할 수 없게 한다. -->
					<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"/>
					<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
					${name}
				</td>
				<td align="center">
					<jsp:useBean id="date" class="java.util.Date"/>
					<c:if test="${date.year == vo.writeDate.year && date.month == vo.writeDate.month &&
						date.date == vo.writeDate.date}">
						<fmt:formatDate value="${vo.writeDate}" pattern="a h:mm:ss"/>
					</c:if>
					<c:if test="${date.year != vo.writeDate.year || date.month != vo.writeDate.month ||
						date.date != vo.writeDate.date}">
						<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/>
					</c:if>
				</td>
				<td align="center">${vo.hit}</td>
				</tr>
			</c:forEach>
		</c:if>
		
		<!-- 페이지 이동 버튼 -->
		<tr>
			<td colspan="5" align="center">
				<!-- 처음으로 -->
				<c:if test="${boardList.currentPage > 1}">
					<button 
						class='button button1' 
						type="button" 
						title="첫 페이지로 이동합니다." 
						onclick="location.href='?currentPage=1'"
					>처음</button>
				</c:if>
				<c:if test="${boardList.currentPage <= 1}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 첫 페이지 입니다."
					>처음</button>
				</c:if>
				
				<!-- 10페이지 앞으로 -->
				<c:if test="${boardList.startPage > 1}">
					<button 
						class='button button1' 
						type="button" 
						title="이전 10페이지로 이동합니다." 
						onclick="location.href='?currentPage=${boardList.startPage - 1}'"
					>이전</button>
				</c:if>
				<c:if test="${boardList.startPage <= 1}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 첫 10페이지 입니다."
					>이전</button>
				</c:if>
				
				<!-- 페이지 이동 버튼 -->
				<c:forEach var="i" begin="${boardList.startPage}" end="${boardList.endPage}" step="1">
					<c:if test="${boardList.currentPage == i}">
						<button class='button button2' type='button' disabled='disabled'>${i}</button>
					</c:if>
					<c:if test="${boardList.currentPage != i}">
						<button 
							class='button button1' 
							type='button' 
							title="${i}페이지로 이동합니다."
							onclick="location.href='?currentPage=${i}'"
						>${i}</button>
					</c:if>
				</c:forEach>
				
				<!-- 10페이지 뒤로 -->
				<c:if test="${boardList.endPage < boardList.totalPage}">
					<button 
						class='button button1' 
						type="button" 
						title="다음 10페이지로 이동합니다." 
						onclick="location.href='?currentPage=${boardList.endPage + 1}'"
					>다음</button>
				</c:if>
				<c:if test="${boardList.endPage >= boardList.totalPage}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 마지막 10페이지 입니다."
					>다음</button>
				</c:if>
				
				<!-- 마지막으로 -->
				<c:if test="${boardList.currentPage < boardList.totalPage}">
				<button
					class='button button1'  
					type="button" 
					title="마지막 페이지로 이동합니다." 
					onclick="location.href='?currentPage=${boardList.totalPage}'"
				>마지막</button>
				</c:if>
				<c:if test="${boardList.currentPage >= boardList.totalPage}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 마지막 페이지 입니다."
					>마지막</button>
				</c:if>
				
			</td>
		</tr>
		<!-- 글쓰기 버튼 -->
		<tr class="table-secondary">
			<td colspan="5" align="right">
				<input 
					class="btn btn-outline-primary btn-sm" 
					type="button" 
					value="글쓰기"
					style="font-size: 13px;"
					onclick="location.href='insert'"/>
			</td>
		</tr>
	
	</table>

</body>
</html>