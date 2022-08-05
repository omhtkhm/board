<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>



<c:choose>
	<c:when test="${ isIdDup eq true }">
		<p>아이디가 중복, 사용중인 아이디 입니다. </p>
	</c:when>
	<c:otherwise>
		<p>
			사용가능한 아이디 입니다.
			<button type="button" id="btnUseId">아이디 사용</button>
		</p>
	</c:otherwise>
</c:choose>


<form action="/joinIdDupChk" method="get" name="wfrm">
	<input type="text" name="id" value="${id}">
	<button type="submit">아이디 중복확인</button>

</form>

<script>
	var btnUseId = document.querySelector('#btnUseId');
	btnUseId.addEventListener('click', function(){
		alert("아이디사용")
		
		//window생략가능
		//현재 창의 id 입력값을 부모창의 id 입력상자에 배치하기
		window.opener.document.frm.id.value = wfrm.id.value;
		
		window.close(); 
	});
	
</script>

</body>
</html>