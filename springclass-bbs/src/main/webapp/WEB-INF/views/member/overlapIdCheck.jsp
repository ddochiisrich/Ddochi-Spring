<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/bootstrap/bootstrap.min.css" rel="stylesheet">
<script src="resources/js/jquery-3.7.1.min.js"></script>
<script src="resources/js/member.js"></script>
<!-- content -->
<div class="row my-5" id="global-content">
	<!-- 아이디가 중복되는 경우 - 사용할 수 없는 경우-->
	<c:choose>
		<c:when test="${ overlap }">
			<div class="col">
				<div class="row">
					<div class="col">
						<h2 class="fs-3 fw-bold text-center">사용할 수가 없는 아이디다 이좌식아!!!!</h2>
					</div>
				</div>
				<div class="row my-3 text-center">
					<div class="col ">입력한 아이디 ${ id }는 중복이야 이좌식아!!!!</div>
				</div>
				<div class="row my-3 text-center">
					<div class="col">다른 아이디를 입력해라 이말이야!!</div>
				</div>
				<form action="overlapIdCheck" name="idCheckForm" method="post"
					id="checkForm" class="row mt-5">
					<div class="col-10 offset-1">
						<div class="input-group">
							<span class="input-group-text">아이디</span> 
							<input type="text" class="form-control" name="id" id="checkId"> 
							<input type="submit" class="btn btn-danger" value="중복확인">
						</div>
					</div>
				</form>
			</div>
		</c:when>
		<c:otherwise>
			<!--  아이디가 사용할 수 있는 경우 -->
			<div class="col">
				<div class="row">
					<div class="col">
						<h2 class="fs-3 fw-bold text-center">사용할 수 있는 아이디다 이좌식아!!!!</h2>
					</div>
				</div>
				<div class="row my-3">
					<div class="col text-center">입력한 아이디 ${ id }는 사용할수있어 이좌식아!!!!
					</div>
				</div>
				<div class="row mt-5">
					<div class="col text-center">
						<input type="button" value="${ id }를(을) 아이디로 사용하기"
							id="btnIdCheckClose" class="btn btn-danger" data-id-value="${ id }" />
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<script src="resources/bootstrap/bootstrap.bundle.min.js"></script>