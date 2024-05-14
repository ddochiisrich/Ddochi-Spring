<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Content -->
<div class="row my-5" id="global-content">
	<div class="col">
		<div class="row">
			<div class="col">
				<h2 class="fs-3 fw-bold text-center">🐞 Board List 🐞</h2>
			</div>
		</div>
		전체 게시글 : ${ listCount } 🐶
		<!--  검색 폼 -->
		<form name="searchForm" id="searchForm" action="boardList"
			class="row justify-content-center my-3">
			<div class="col-auto">
				<select name="type" class="form-select">
					<option value="title">Title</option>
					<option value="writer">writer</option>
					<option value="content">content</option>
				</select>
			</div>
			<div class="col-4">
				<input type="text" name="keyword" class="form-control" />
			</div>
			<div class="col-auto">
				<input type="submit" value="Search" class="btn btn-danger" />
			</div>
		</form>
		<c:if test="${ searchOption }">
			<div class="row my-3">
				<div class="col text-center">"${ keyword }" 검색결과</div>
			</div>
			<div class="row my-3">
				<div class="col">
					<a href="writeForm" class="btn btn-outline-danger">List</a>
				</div>
				<div class="col text-end">
					<a href="writeForm" class="btn btn-outline-danger">Post</a>
				</div>
			</div>
		</c:if>
		<!-- 검색이 아닌 경우 -->
		<c:if test="${ not searchOption }">
			<div class="row">
				<div class="col text-end">
					<a href="writeForm" class="btn btn-outline-danger">Post</a>
				</div>
			</div>
		</c:if>
		<div class="row my-3">
			<div class="col">
				<table class="table table-hover">
					<thead>
						<tr class="table-danger">
							<th>No</th>
							<th>Title</th>
							<th>Writer</th>
							<th>Date</th>
							<th>View</th>
						</tr>
					</thead>
					<tbody>
						<!-- 검색이면서 게시글이 있는 경우 -->
						<c:if test="${ searchOption and not empty bList }">
							<c:forEach var="b" items="${ bList }">
								<tr>
									<td>${ b.no }</td>
									<td><a
										href="boardDetail?no=${ b.no }&pageNum=${currentPage}&type=${type}&keyword=${keyword}"
										class="text-decoration-none link-light">${ b.title }</a></td>
									<td>${ b.writer }</td>
									<td>${ b.regDate }</td>
									<td>${ b.readCount }</td>
								</tr>
							</c:forEach>
						</c:if>
						<!-- 검색이 아니면서 게시글이 있는 경우 -->
						<c:if test="${ not searchOption and not empty bList }">
							<c:forEach var="b" items="${ bList }">
								<tr>
									<td>${ b.no }</td>
									<td><a
										href="boardDetail?no=${ b.no }&pageNum=${currentPage}"
										class="text-decoration-none link-light">${ b.title }</a></td>
									<td>${ b.writer }</td>
									<td>${ b.regDate }</td>
									<td>${ b.readCount }</td>
								</tr>
							</c:forEach>
						</c:if>
						<!-- 검색이면서 게시글이 없는 경우 -->
						<c:if test="${ searchOption and empty bList }">
							<tr>
								<td colspan="5">"${ keyword }" keyword is nothing.. T.T</td>
							</tr>
						</c:if>
						<!-- 검색이 아니면서 게시글이 없는 경우 -->
						<c:if test="${ not searchOption and empty bList }">
							<tr>
								<td colspan="5">Empty Post...</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 검색요청인 경우 -->
		<c:if test="${ searchOption and not empty bList }">
			<div class="row">
				<div class="col">
					<nav aria-label="Page navigation example">
						<ul class="pagination justify-content-center">
							<!-- 앞쪽에 이전 페이지 그룹이 있는 경우 -->
							<c:if test="${ startPage > pageGroup }">
								<li class="page-item"><a class="page-link"
									href="boardList?pageNum=${ startPage - pageGroup }&type=${ type }&keyword=${ keyword }">Previous</a></li>
							</c:if>
							<c:forEach var="i" begin="${ startPage }" end="${ endPage }">
								<!-- 현재 페이지라면 링크를 생략 -->
								<c:if test="${ i == currentPage }">
									<li class="page-item active" aria-current="page"><span
										class="page-link">${ i }</span></li>
								</c:if>
								<!--  현재페이지가 아니라면 링크하기 -->
								<c:if test="${ i != currentPage }">
									<li class="page-item"><a class="page-link"
										href="boardList?pageNum=${ i }&type=${ type }&keyword=${ keyword }">${ i }</a></li>
								</c:if>
							</c:forEach>
							<!-- 뒷쪽에 다음 페이지 그룹이 있는 경우 = 현재 endPage < pageCount -->
							<c:if test="${ endPage < pageCount }">
								<li class="page-item"><a class="page-link"
									href="boardList?pageNum=${ startPage + pageGroup }&type=${ type }&keyword=${ keyword }">Next</a></li>
							</c:if>
						</ul>
					</nav>
				</div>
			</div>
		</c:if>
		<!-- 검색요청이 아닌경우 -->
		<c:if test="${ not searchOption and not empty bList }">
			<div class="row">
				<div class="col">
					<nav aria-label="Page navigation example">
						<ul class="pagination justify-content-center">
							<!-- 앞쪽에 이전 페이지 그룹이 있는 경우 -->
							<c:if test="${ startPage > pageGroup }">
								<li class="page-item"><a class="page-link"
									href="boardList?pageNum=${ startPage - pageGroup }">Previous</a></li>
							</c:if>
							<c:forEach var="i" begin="${ startPage }" end="${ endPage }">
								<!-- 현재 페이지라면 링크를 생략 -->
								<c:if test="${ i == currentPage }">
									<li class="page-item active" aria-current="page"><span
										class="page-link">${ i }</span></li>
								</c:if>
								<!--  현재페이지가 아니라면 링크하기 -->
								<c:if test="${ i != currentPage }">
									<li class="page-item"><a class="page-link"
										href="boardList?pageNum=${ i }">${ i }</a></li>
								</c:if>
							</c:forEach>
							<!-- 뒷쪽에 다음 페이지 그룹이 있는 경우 = 현재 endPage < pageCount -->
							<c:if test="${ endPage < pageCount }">
								<li class="page-item"><a class="page-link"
									href="boardList?pageNum=${ startPage + pageGroup }">Next</a></li>
							</c:if>
						</ul>
					</nav>
				</div>
			</div>
		</c:if>
	</div>
</div>

<!-- Footer -->
