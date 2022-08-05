<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<html>
<head>
<meta charset="UTF-8">
<head>
<title>Welcome!</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark mb-3">
		<ul class="navbar-nav">
		<c:choose>
		<c:when test="${'ROLE_USER' eq principal.user.role }">
			<sec:authorize access="isAuthenticated()">
			<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
				<li class="nav-item"><a class="nav-link"
					href="/update/${principal.user.id}">회원정보수정</a></li>
				<li>
				<li class="nav-item"><a class="nav-link" href="/board/list">게시판</a></li>
				<li><a class="nav-link" href="/logout"> 로그아웃(<sec:authentication
							property="principal.user.username" />)
				</a></li>
			</sec:authorize>
			</c:when>
			<c:when test="${'ROLE_ADMIN' eq principal.user.role }">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="/admin/userlist">회원
						관리</a></li>
				<li>
				<li class="nav-item"><a class="nav-link"
					href="/board/list">게시판 관리</a></li>
				<li><a class="nav-link" href="/logout"> 로그아웃(<sec:authentication
							property="principal.user.username" />)
				</a></li>
			</sec:authorize>
			</c:when>
			<c:otherwise>
			<sec:authorize access="isAnonymous()">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="/joinForm">회원가입</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="/loginForm">로그인</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="/board/list">게시판</a>
					</li>
				</ul>
			</sec:authorize>
			</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</body>
</html>