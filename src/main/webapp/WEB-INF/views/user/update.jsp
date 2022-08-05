<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../includes/header.jsp"%>
<div class="container">
		<h1>회원 정보 수정</h1>
		<br>
		<div class="form-group">
			<label for="username">아이디</label> <input type="text" class="form-control" id="username" name="username"  value="${user.id}" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="password">비밀번호</label> <input type="password" class="form-control" id="password" name="password" value="" >
		</div>

		<div class="form-group">
			<label for="email">이메일</label> <input type="text" id="email" name="email" class="form-control" value="${user.email}"  >
		</div>
		<div class="form-group">
			<label for="address">주소</label> <input type="text" id="address" name="address" class="form-control" value="${user.address}" >
		</div>
		<button type="button" class="btn btn-primary" id="btnModify">수정하기</button>
		<button type="button" class="btn btn-primary" id="btnDelete">탈퇴하기</button>
		<button type="button" class="btn btn-primary" onclick="location.href='/'">돌아가기</button>
		
</div>
<script>
$("#btnModify").click(function(){
	data = {
		"id" : ${user.id},	 
		"password" : $("#password").val(),	 
		"email" : $("#email").val(),
		"address" : $("#address").val()
	}
	console.log(data);
	
	$.ajax({
		type : "PUT",
		url : "/update/${user.id}",
		contentType: "application/json;charset=utf-8",
		data : JSON.stringify(data)
	})
	.done(function(resp){
		if(resp=="success"){
			console.log(resp);
			alert("수정성공");
			location.href="/";
		}
	})
})


$("#btnDelete").click(function(){
	if(!confirm("정말 삭제할까요?") )  return false
	$.ajax({
		type :"DELETE",
		url : "/delete/${user.id}",
		success:function(resp){
			if(resp=="success"){
				alert("삭제성공");
				location.href="/logout";
			}
		} //success
	})//ajax
})  //btnDelete
</script>

</body>
</html>