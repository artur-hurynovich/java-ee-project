<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key = "error" /></title>
	</head>
	<body>
		<%@include file = "header.jsp" %>
		<div style = "text-align: center; margin-top: 10px">
			<p><b><fmt:message key = "access_denied" /></b>
			<input type = "button" style = "margin-left: 10px" class = "btn" value = "<fmt:message key = "back" />" 
			onclick = "history.back()">
		</div>
	</body>
</html>