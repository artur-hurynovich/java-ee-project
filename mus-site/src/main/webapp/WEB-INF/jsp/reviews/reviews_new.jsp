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
		<form action = "controller" method = "post">
			<div style = "margin: 10px">
				<div class = "form-inline">
					<b><fmt:message key = "reviews.lang" /></b>
					<select style = "width: 10%; margin-left: 10px"name = "reviewLang" required>
						<option>
						</option>
						<option value = "en">
							<fmt:message key = "reviews.lang.en" />
						</option>
						<option value = "ru">
							<fmt:message key = "reviews.lang.ru" />
						</option>
					</select>
				</div>
				<div>
					<b><fmt:message key = "reviews.title" /></b>
					<div>
						<input type = "text" name = "title" style = "width: 100%" maxlength = "145" required>
					</div>
				</div>
				<div>
					<b><fmt:message key = "reviews.text" /></b>
					<div>
						<textarea name = "text" style = "width: 100%" rows = "15" required></textarea>
					</div>
				</div>
				<div>
					<b><fmt:message key = "reviews.hashtags" /></b>
					<div>
						<input type = "text" style = "width: 100%" name = "hashtags" maxlength = "145" required>
					</div>
				</div>
				<button type = "submit" style = "margin-top: 10px" class = "btn" name = "command" value = "reviews_new">
					<fmt:message key = "reviews.new" />
				</button>
				<input type = "button" style = "margin-left: 10px; margin-top: 10px" class = "btn" 
				value = "<fmt:message key = "back" />" onclick = "history.back()">
			</div>
		</form>
	</body>
</html>