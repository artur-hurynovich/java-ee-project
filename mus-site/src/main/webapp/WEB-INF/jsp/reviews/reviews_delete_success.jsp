<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<c:set var = "user" value = "${sessionScope.user}" />
<html>
	<head>
		<title><fmt:message key = "menu.reviews" /></title>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div style = "text-align: center; margin-top: 10px">
			<p><b><fmt:message key = "reviews.delete_success" /></b>
			<form action = "controller" method = "post">
				<button class = "btn" type = "submit" name = "command" value = "reviews_page">
					<fmt:message key = "menu.reviews" />
				</button>
			</form>
		</div>
	</body>
</html>