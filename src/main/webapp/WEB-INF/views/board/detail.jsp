<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<h3>글 상세 보기</h3>
	<hr>
	<br />
	<div class="form-group">
		<label for="num">글 번호:</label> <input type="text" class="form-control"
			id="num" name="num" value="${board.num}" readonly="readonly">
	</div>
	<div class="form-group">
		<label for="writer">작성자:</label> <input type="text"
			class="form-control" id="writer" name="writer"
			value="${board.writer}" readonly="readonly">
	</div>
	<div class="form-group">
		<label for="title">제목:</label> <input type="text" class="form-control"
			id="title" name="title" value="${board.title}" readonly="readonly">

	</div>

	<div class="form-group">
		<label for="content">내용:</label>
		<textarea id="content" name="content" id="content"
			class="form-control" readonly="readonly">${board.content }</textarea>
	</div>

	<div class="form-group">
		<label for="price">가격:</label> <input type="text" class="form-control"
			id="price" name="price" value="${board.price}" readonly="readonly">
	</div>


	<div class="form-group">
		사진: </br>
		<c:forEach items="${board.imgs }" var="img" varStatus="st">
			<%-- <c:set var="imagenametest" value=" ${fn:substring(img.fileName, 9,12)}" />
			${imagenametest} --%>
			<c:if
				test="${fn:substring(img.fileName, 9,12) eq 'jpg' || fn:substring(img.fileName, 9,12) eq 'png' 
			|| fn:substring(img.fileName, 9,12) eq 'gif'}">
				<c:set var="path" value="/static/upload/${img.fileName}" />
				<img style="width: 200px; height: 200px" src="${path}">
			</c:if>
		</c:forEach>
	</div>

	<div class="form-group">
		첨부파일: </br>
		<c:forEach items="${board.imgs }" var="img" varStatus="st">
			<c:if
				test="${fn:substring(img.fileName, 9,12) ne 'jpg' && fn:substring(img.fileName, 9,12) ne 'png' 
			&& fn:substring(img.fileName, 9,12) ne 'gif'}">
				<c:set var="path" value="/static/upload/${img.fileName}" />
				<a style="width: 200px; height: 200px" href="${path}" download>${img.originalFileName}</a>
			</c:if>
		</c:forEach>
	</div>

	<br>
	<c:if test="${principal.user.username==board.writer}">
		<button type="button" id="btnUpdate" class="btn btn-primary  btn-sm">수정</button>
		</c:if>
		<c:if test="${principal.user.username==board.writer || principal.user.role == 'ROLE_ADMIN'}" >
		<button type="button" id="btnDelete" class="btn btn-danger  btn-sm">삭제</button>
		<button type="button" id="btnDelete" class="btn btn-primary  btn-sm"
			onclick="location.href='/board/list'">돌아가기</button>
	</c:if>
	<c:if test="${isExit == 'false'}">
	<button type="button" id="btnLike" class="btn btn-danger  btn-sm" >좋아요!</button>
	</c:if>
	<span>♥</span><span id="replycount">${board.replycount}</span>
	
</div>
<br>
<hr>
<div>
	<table class="table table-borderless">
 		<c:forEach items="${board.comments}" var="comment">
		<tr>
			<td>${comment.cnum }</td>
			<td>${comment.content }</td>
			<td><fmt:formatDate value="${comment.regdate }" pattern="yyyy-MM-dd" /></td>
		</tr>
		</c:forEach> 
	</table>
</div>
<div align="center">
<textarea rows="3" cols="50" id="msg"></textarea>
<input type="button" value="댓글쓰기" class="btn btn-secondary  btn-sm" id="btnComment">
</div>
<hr/>
<div id="replyResult"></div>






<script>
//댓글리스트
var init =function(){
	$.ajax({
		type:"get",
		url :"/reply/list/"+$("#num").val()
		
	})
	.done(function(resp){
		console.log(resp)
		//alert(resp.length);
		var str="";
		$.each(resp, function(key, val){
			str += val.user.id+"  "
			str += val.content+" "
			str += val.regdate+"<br>"
		})
		/* $("#replyResult").html(str); */
	})
	.fail(function(e){
		console.log(e)
	})
}

//댓글쓰기
$("#btnComment").click(function(){
	if(${empty principal.user}){
		alert("로그인하세요");
		location.href="/loginForm";
		return;
	}
	if($("#msg").val()==""){
		alert("댓글을 적으세요");
		return;
	}
	data = {
			"bnum" : $("#num").val(),
			"content" : $("#msg").val()
	}
	$.ajax({
		type:"POST",
		url:"/reply/insert/"+$("#num").val(),
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(data)
     })
     .done(function(){
    	 alert("댓글 추가 성공");
    	 location.href="/board/detail/${board.num}"
     })
     .fail(function(){
    	 alert("댓글 추가 실패")
     })
})





//글삭제
	$("#btnDelete").click(function() {
		if (!confirm("정말 삭제할까요?"))
			return false
		$.ajax({
			type : "DELETE",
			url : "/board/delete/${board.num}",
			success : function(resp) {
				/* console.log(resp); */
				if (resp == "success") {
					alert("삭제성공");
					location.href = "/board/list"
				}
			} //success
		})//ajax
	}) //btnDelete
	//수정폼

	$("#btnUpdate").click(function() {
		if (!confirm("정말 수정할까요?"))
			return false
		location.href = "/board/update/${board.num}"

	});
	
	//좋아요 버튼 
	$("#btnLike").click(function() {
		if (!confirm("정말 좋아요를 누르시겠습니까?"))
			return false
		$.ajax({
			type: "post",
			url: "/board/like/${board.num}",
			success: function(resp){
				console.log(resp)
		 		if(resp.result == "success"){
					alert("좋아요를 누르셨습니다!");
					$("#replycount").text(resp.value)
					$("#btnLike").hide()
				} 
			}
		})
	})
init();
</script>
</body>
</html>