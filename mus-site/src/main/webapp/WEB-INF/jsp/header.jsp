<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<link rel = "stylesheet" href = "css/bootstrap.min.css" type = "text/css">
		<link rel = "stylesheet" href = "css/main.css" type = "text/css">
	</head>
	<body>
		<a href = "controller?command=main_page"><img src = "img/header.jpg"></a>
		<nav class = "navbar navbar-custom">
			<ul class = "nav nav-justified">
				<li class = "nav-item"><a class = "nav-link" href = "controller?command=reviews_page">
					<fmt:message key = "menu.reviews" /></a>
				</li>
				<li class = "nav-item"><a class = "nav-link" href = "controller?command=instruments_page">
					<fmt:message key = "menu.instruments" /></a>
				</li>
				<li class = "nav-item"><a class = "nav-link" href = "controller?command=profile_page">
					<fmt:message key = "menu.profile" /></a>
				</li>
			</ul>
			<ul class = "nav justify-content-end">
				<li class = "nav-item"><a class = "nav-link" href = "controller?command=switch_lang&lang=en">
					<fmt:message key = "menu.lang.en" /></a>
				</li>
				<li class = "nav-item"><a class = "nav-link" href = "controller?command=switch_lang&lang=ru">
					<fmt:message key = "menu.lang.ru" /></a>
				</li>
			</ul>
		</nav>
	</body>
</html>