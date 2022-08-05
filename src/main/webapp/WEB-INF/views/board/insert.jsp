<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<form action="/board/insert" method="post"
		enctype="multipart/form-data">
		<div class="form-group">
			<label for="title">제목:</label> <input type="text"
				class="form-control" id="title" name="title" placeholder="제목을 입력하세요">
		</div>
		<div class="form-group">
			<label for="writer">작성자:</label> <input type="text" id="writer"
				name="writer" class="form-control"
				value="${principal.user.username }" readonly="readonly" />
		</div>
		<div class="form-group">
			<label for="content">내용:</label>
			<textarea id="content" name="content" id="content"
				placeholder="내용을 입력하세요" class="form-control"></textarea>
		</div>
		<div class="form-group">
			<label for="price">가격:</label> <input type="number"
				class="form-control" id="price" placeholder="가격을 입력하세요" name="price">
		</div>
		<div class="form-group row mb-4">
			<label class="col-form-label ">사진 업로드</label>
			<div class="col-sm-12 col-md-7">
				<input type="file" name="img" id="img" multiple>
			</div>
		</div>

		<button type="submit" class="btn btn-primary">상품등록</button>
		<button type="button" class="btn btn-primary" onclick="location.href='/board/list'">돌아가기</button>
	</form>
</div>
<script>
$(function(){
    $("button[type='submit']").click(function(){
        var $fileUpload = $("input[type='file']");
        if (parseInt($fileUpload.get(0).files.length)>3){
        	alert("파일 개수는 최대 10개 까지 올릴수 있습니다.");
        }
    });    
}); 

</script>

</body>
</html>
