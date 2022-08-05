<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<h1>LOGIN</h1>
	<hr>
	<br>
	<form id="myform" action="/login" method="post">
		<div class="form-group">
			<label for="username">ID</label> <input type="text"
				class="form-control" id="username" placeholder="Enter Username"
				name="username">
		</div>
		<div class="form-group">
			<label for="password">PASSWORD</label> <input type="password"
				class="form-control" id="password" placeholder="Enter Password"
				name="password">
		</div>
		<button class="btn btn-primary" id="btnlogin">LOGIN</button>
		<button type="button" class="btn btn-primary"
			onclick="location.href='/'">RETURN</button>
	</form>
	<a href="/joinForm">If you are not a member yet, please sign up</a>
</div>

<script>
$("#btnlogin").click(function(e) {
		e.preventDefault() //submit 제한, 아래 유효성 진행시킴
		console.log("asdasd")
		if ($("#username").val() == "") {
			alert("아이디를 입력하세요");
			$("#username").focus();
			return false;
		}
		if ($("#password").val() == "") {
			alert("비밀번호를 입력하세요");
			$("#password").focus();
			return false;
		}
		$("#myform").submit(); // 다시 submit을 시킴 
	}) 
</script>
</body>
</html>