<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key = "menu.profile" /></title>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div style = "margin-left: 10px; margin-top: 10px">
			<form action = "controller" method = "post">
				<div style = "margin-bottom: 10px">
					<b><fmt:message key = "profile.user_information" /></b>
				</div>
				<div class = "form-group row">
					<label class = "col-1"><fmt:message key = "profile.user_information.name" /></label>
					<input style = "width: 200px" type = "text" value = "${user.name}" disabled>
				</div>
				<div class = "form-group row">
					<label class = "col-1"><fmt:message key = "profile.user_information.email" /></label>
					<input style = "width: 200px" type = "text" value = "${user.email}" disabled>
				</div>
				<div class = "form-group row">
					<label class = "col-1"><fmt:message key = "profile.user_information.role" /></label>
					<input style = "width: 200px" type = "text" value = "${user.role}" disabled>
				</div>
				<div class = "form-group row">
					<label class = "col-1"><fmt:message key = "profile.user_information.phone" /></label>
					<input style = "width: 200px" type = "text" value = "${user.phone}" disabled>
				</div>
				<div>
					<button class = "btn" type = "submit" name = "command" value = "profile_logout">
						<fmt:message key = "profile.logout" />
					</button>
				</div>
			</form>
		</div>
		<div style = "margin-left: 10px; margin-top: 10px">
			<c:set var = "user" value = "${sessionScope.user}"></c:set>
			<c:if test = "${not empty user and user.role eq 'ADMIN'}">
				<div style = "margin-bottom: 10px">
					<b><fmt:message key = "choose_action" /></b>
				</div>
				<div>
					<a href = "controller?command=profile_change_role_page"><fmt:message key = "profile.change_role" /></a>
				</div>
			</c:if>
		</div>
	</body>
</html>