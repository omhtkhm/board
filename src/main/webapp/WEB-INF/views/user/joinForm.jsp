<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<h1>SIGN-UP</h1>
	<br>
	
	<div class="form-group">
		<label for="username">Username</label> <input type="text"
			class="form-control" id="username" placeholder="Enter Username"
			name="username">
			
	</div>
	<div class="d-flex justify-content-end">
			<button id="duplicate_check" type="button" class="btn btn-info" onClick="check()">아이디 중복확인</button>
		</div>
		<span class="helper-text" id="idDupMessage"></span>
	<div class="form-group">
		<label for="password">Password</label> <input type="password"
			class="form-control" id="password" placeholder="Enter Password"
			name="password">
	</div>

	<div class="form-group">
		<label for="email">E-mail</label> <input type="text" id="email"
			name="email" class="form-control" placeholder="Enter E-mail">
	</div>
	<div class="form-group"></div>
	
	
	<div class="form-group">
	<label for="address">Address</label>
		<input type="text" class="form-control" id="address"
			placeholder="Enter Address" name="address" readonly="readonly">
			<br>
				<div class="d-flex justify-content-end">
			<button type="button" class="btn btn-info" onClick="goPopup();">주소검색</button>
		</div>
	</div>

	<button type="button" class="btn btn-primary" id="btnJoin">회원가입</button>
	<button type="button" class="btn btn-primary"
		onclick="location.href='/'">돌아가기</button>
</div>

<script>
//아이디 중복확인 버튼
function check() {
	var inputId = $("#username").val();
	
	if(inputId.length == 0 ) { //inputId == '' 
		alert('아이디를 입력하세요.');
		$('#username').focus();
		return;
	}
	//window.name = "parentForm";
	
	$.ajax({
		url:'/joinIdDupChk',
		data: {id: inputId}, // 키(id):벨류(inputId)
		method: 'GET',
		success:function (data) {
			console.log(typeof data);
			console.log(data);
			
			if(data == "1")
			$('#idDupMessage').html("<font color='green'> 사용가능한 아이디입니다");
			
			if(data == "0")
				$('#idDupMessage').html("<font color='red'> 사용불가능한 아이디입니다");
		}
	});
	
}




//회원가입버튼
	$("#btnJoin").click(function() {
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
		var data = {
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"email" : $("#email").val(),
			"address" : $("#address").val()
		}
		$.ajax({
			type : "POST",
			url : "/join",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(data)
		}).done(function(resp) {
			if (resp == "success") {
				alert("회원가입 성공");
				location.href = "/loginForm";
			} else if (resp == "fail") {
				alert("아이디 중복");
				$("#username").val("");
			}
		}).fail(function() {
			alert("회원가입실패");
		})

	})

	function goPopup() {
		// 주소검색을 수행할 팝업 페이지를 호출합니다.
		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
		var pop = window.open("/jusoPopup", "pop",
				"width=570,height=420, scrollbars=yes, resizable=yes");
		// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
		//var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
	}
	function jusoCallBack(roadFullAddr) {
		var addressEl = document.querySelector("#address")
		addressEl.value = roadFullAddr;
	}
</script>
</body>
</html>
