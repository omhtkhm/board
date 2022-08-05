<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<h1>커뮤니티</h1>
	<hr>
	<br>
	<table class="table table-hover" style="text-align: center">
		<form action="/board/search" method="GET">
			<select name="searchgubun">
				<option value="title">제목</option>
				<option value="content">내용</option>
				<option value="writer">작성자</option>
				<option value="titleplustwriter">제목+작성자</option>
			</select>
			<div class="saerch">
				<input name="keyword" type="text" placeholder="검색어를 입력해주세요">
				<button>검색</button>
			</div>
		</form>
		<br>
		<thead>
			<tr>
				<th>글 번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>좋아요</th>
			</tr>
		<thead>
		<tbody>
			<c:forEach items="${lists.content }" var="board" varStatus="st">
				<tr>
					<td>${board.num }</td>
					<td><a href="/board/detail/${board.num}">${board.title }</a></td>
					<td>${board.writer }</td>
					<td><fmt:formatDate value="${board.regdate }"
							pattern="yyyy-MM-dd" /></td>
					<td>${board.hitcount }</td>
					<td>${board.replycount }</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
	<div class="d-flex justify-content-between mt-3">
		<ul class="pagination">
			<c:if test="${lists.first ==  false }">
				<li class="page-item"><a class="page-link" href="?page=${lists.number-1 }">Previous</a></li>
			</c:if>
			<c:if test="${lists.last ==  false }">
				<li class="page-item"><a class="page-link" href="?page=${lists.number+1 }">Next</a></li>
			</c:if>
		</ul>
	</div>
	<div class="form-group text-right">
		<a href="/board/insert">
			<button type="button" class="btn btn-secondary btn-sm" id="writeBtn">글쓰기</button>
		</a>
	</div>
</div>
</body>
</html>