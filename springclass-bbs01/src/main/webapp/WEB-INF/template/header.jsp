<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row border-bottom border-danger" id="global-header">
	<div class="col-4">
		<p>
			<img src="https://via.placeholder.com/200x100">
		</p>
	</div>
	<div class="col-8">
		<div class="row">
			<div class="col">
				<ul class="nav justify-content-end">
					<li class="nav-item"><a class="nav-link"
						href="${sessionScope.isLogin ? 'logout' : 'loginForm' }">${sessionScope.isLogin ? '로그아웃' : '로그인-폼' }</a></li>
					<li class="nav-item">
					
						<a class="nav-link" ${ not sessionScope.isLogin ? "data-bs-toggle='modal' data-bs-target='#loginModal'" : ""  }
						href="${ sessionScope.isLogin ? 'logout' : '#' }">${sessionScope.isLogin ? '로그아웃' : '로그인-모달' }</a></li>
						
						
						
					<li class="nav-item"><a class="nav-link" href="boardList">게시글리스트</a></li>
					<c:if test="${ not sessionScope.isLogin }">
						<li class="nav-item"><a class="nav-link" href="joinForm">회원가입</a></li>
					</c:if>
					<c:if test="${ sessionScope.isLogin }">
						<li class="nav-item"><a class="nav-link" href="memberUpdateForm">정보수정</a></li>
					</c:if>
					<li class="nav-item"><a class="nav-link" href="#">주문/배송조회</a></li>
					<li class="nav-item"><a class="nav-link" href="#">고객센터</a></li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col text-end">&nbsp;</div>
		</div>
		<div class="row pe-5 text-primary">
			<c:if test="${ sessionScope.isLogin }">
				<div class="col text-end">안녕하세요 ${ sessionScope.member.name }님</div>
			</c:if>
		</div>
	</div>
</div>