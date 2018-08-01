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
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div style = "text-align: center; margin-top: 10px">
			<p><b><fmt:message key = "profile.access_denied" /></b>
			<div>
				<form action = "controller"  method = "post">
					<button class = "btn" type = "submit" name = "command" value = "profile_registration_page">
						<fmt:message key = "profile.registration" />
					</button>
					<button style = "margin-left: 10px" class = "btn" type = "submit" name = "command" 
					value = "profile_login_page">
						<fmt:message key = "profile.login" />
					</button>
				</form>
			</div>
		</div>
	</body>
</html>