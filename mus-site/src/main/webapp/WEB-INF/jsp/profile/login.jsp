<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key="profile.login" /></title>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div style = "margin-top: 10px; margin-left: 10px">
			<form action = "controller" method = "post">
				<div class = "form-group row">
					<label class = "col-1"><fmt:message key = "profile.user_information.email" /></label>
					<input style = "width: 200px" type = "email" name = "email" value = "${email}" 
					maxlength = "40" required>
					<c:if test = "${not empty validEmail and validEmail eq false}">
						<b>
							<label class = "col-form-label" style = "margin-left: 10px; color: #ff0000">
								<fmt:message key = "profile.login.incorrect_email" />
							</label>
						</b>
					</c:if>
				</div>
				<div class = "form-group row">
					<label class = "col-1"><fmt:message key = "profile.user_information.password" /></label>
					<input style = "width: 200px" type = "password" name = "password" maxlength = "20" 
					required>
					<c:if test = "${not empty validPassword and validPassword eq false}">
						<b>
							<label class = "col-form-label" style = "margin-left: 10px; color: #ff0000">
								<fmt:message key = "profile.login.incorrect_password" />
							</label>
						</b>
					</c:if>
				</div>
				<div>
					<button class = "btn" type = "submit" name = "command" value = "profile_login">
						<fmt:message key = "profile.login" />
					</button>
					<input type = "button" style = "margin-left: 10px" class = "btn" value = "<fmt:message key = "back" />" 
					onclick = "history.back()">
				</div>
			</form>
		</div>
	</body>
</html>