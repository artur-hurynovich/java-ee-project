<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>Start page</title>
	</head>
	<body>
		<c:set var = "lang" value = "en" scope = "session"/>
		<c:redirect url = "controller?command=reviews_page" />
	</body>
</html>