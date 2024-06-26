<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<div id="logo">		
		<a href="${ pageContext.servletContext.contextPath }/first/main">
		<img src="https://via.placeholder.com/176x67" alt="Books2U" width="176" 
			height="67" /></a></div>			
	<div id="header_link">		
		<ul>
			<li>				
				<a href='#'>로그인</a>
			</li>				
			<li>
				<a href="#">게시글 리스트</a>
			</li>
			<li>
				<c:if test="${ not sessionScope.isLogin }" >	
					<a href="#">회원가입</a>
				</c:if>
				<c:if test="${ sessionScope.isLogin }" >
					<a href="#">정보수정</a>
				</c:if>
			</li>
			<li><a href="#">주문/배송조회</a></li>		
			<li class="no_line"><a href="#">고객센터</a></li>
		</ul>
	</div>
</header>