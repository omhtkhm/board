<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<h3>${board.writer }의글 수정</h3>
	<div class="form-group">
		<div class="form-group">
			<label for="num">글 번호:</label> <input type="text"
				class="form-control" id="num" name="num" value="${board.num}"
				readonly="readonly">

		</div>
		<div class="form-group">
			<label for="writer">작성자:</label> <input type="text"
				class="form-control" id="writer" name="writer"
				value="${board.writer}" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="title">제목:</label> <input type="text"
				class="form-control" id="title" name="title" value="${board.title}">
		</div>
		<div class="form-group">
			<label for="content">내용:</label>
			<textarea id="content" name="content" id="content"
				class="form-control">${board.content }</textarea>
		</div>
		<div class="form-group">
			<label for="price">가격:</label> <input type="text"
				class="form-control" id="price" name="price" value="${board.price}">
		</div>
			<br> <br>
			<button type="button" id="btnModify" class="btn btn-primary  btn-sm">수정 완료</button>
			<button type="button" id="btnDelete" class="btn btn-primary  btn-sm"
				onclick="location.href='/board/detail/${board.num}'">돌아가기</button>
	</div>
</div>
<script>
	$("#btnModify").click(function() {
		data = {
			"num" : $("#num").val(),
			"title" : $("#title").val(),
			"content" : $("#content").val(),
			"price" : $("#price").val()
			
		}
		$.ajax({
			type : "PUT",
			url : "/board/update/${board.num}",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify(data)
		})
		.done(function(resp) {
			if (resp == "success") {
				console.log(resp);
				alert("수정성공");
				location.href = "/board/detail/${board.num}"
			}
		})
	})
</script>

</body>
</html>