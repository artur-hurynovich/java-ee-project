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
			<div style = "margin-bottom: 10px">
				<p><b><fmt:message key = "profile.user_information" /></b>
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
		</div>
		<div style = "margin-left: 10px; margin-top: 10px">
			<form action = "controller" method = "post">
				<input type = "hidden" name = "userId" value = "${user.id}">
				<div class = "form-group row">
					<label class = "col-2"><b><fmt:message key = "profile.choose_new_role" /></b></label>
					<select style = "width: 200px" name = "newRole" required>
						<option>
						<option value = "USER">
							USER
						</option>
						<option value = "MANAGER">
							MANAGER
						</option>
					</select>
				</div>
				<div class = "form-inline">
					<button class = "btn" type = "submit" name = "command" value = "profile_change_role">
						<fmt:message key = "save" />
					</button>
					<button class = "btn" type = "submit" style = "margin-left: 10px" name = "command" 
					value = "profile_change_role_page">
						<fmt:message key = "cancel" />
					</button>
				</div>
			</form>
		</div>
	</body>
</html>