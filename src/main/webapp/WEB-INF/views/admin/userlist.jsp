<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<h1>User List</h1>
	<hr>
		<form action="/admin/search" method="GET">
		<select name="searchgubun">
				<option value="username">회원</option>
				</select>
			<div class="saerch">
				<input name="keyword" type="text" placeholder="회원 조회">
				<button>검색</button>
			</div>
		</form>
			
			
			
			
		<c:forEach items="${lists.content}" var="user" varStatus="st">
		<c:if test="${user.id ne principal.user.id }">
			<tr>
				<td>User ID: ${user.id } </td>
				</br>
				<td>User's Username: ${user.username}</td>
				</br>
				<td>User's Email: ${user.email}</td>
				</br>
				<td>User's Address: ${user.address}</td>
				</br>
			</tr>
			<button type="button" onclick="funDelete(${user.id})">Delete</button>
			<br>
			<br>
			</c:if>
		</c:forEach>
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
</div>
<script>
function funDelete(id){
	$.ajax({
		type:"DELETE",
		url:"/admin/delete/"+id
	})
	.done(function(resp){
		alert("Success")
		location.href="/admin/userlist"
	})
}
</script>