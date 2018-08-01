<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key = "profile.registration" /></title>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div style = "margin-top: 10px; margin-left: 10px">
			<form action = "controller" method = "post">
				<div class = "form-group row">
					<label class = "col-2"><fmt:message key = "profile.user_information.name" /></label>
					<input style = "width: 200px" type = "text" name = "name" value = "${name}" 
					maxlength = "30" required>
				</div>
				<div class = "form-group row">
					<label class = "col-2"><fmt:message key = "profile.user_information.email" /></label>
					<input style = "width: 200px" type = "text" name = "email" maxlength = "40" 
					value = "${email}" pattern = "\w{1,20}[\._]?\w{1,20}@[a-zA-Z]{3,10}\.[a-zA-Z]{2,3}" required>	
					<c:if test = "${not empty validEmail and validEmail eq false}">
						<b>
							<label class = "col-form-label" style = "margin-left: 10px; color: #ff0000">
								<fmt:message key = "profile.registration.email_exists" />
							</label>
						</b>
					</c:if>
				</div>
				<div class = "form-group row">
					<label class = "col-2"><fmt:message key = "profile.user_information.password" /></label>
					<input style = "width: 200px" type = "password" name = "password" maxlength = "20" 
					required>
				</div>
				<div class = "form-group row">
					<label class = "col-2"><fmt:message key = "profile.user_information.confirm_password" /></label>
					<input style = "width: 200px" type = "password" name = "confirmPassword" 
					maxlength = "20" required>
					<c:if test = "${not empty validPassword and validPassword eq false}">
						<b>
							<label class = "col-form-label" style = "margin-left: 10px; color: #ff0000">
								<fmt:message key = "profile.registration.different_passwords" />
							</label>
						</b>
					</c:if>
				</div>
				<div class = "form-group row">
					<label class = "col-2"><fmt:message key = "profile.user_information.phone" /></label>
					<input style = "width: 200px" type = "text" placeholder = "+123(12)1234567" 
					title = "+123(12)1234567" name = "phone" value = "${phone}" pattern = "\+\d{3}\(\d{2}\)\d{7}" 
					maxlength = "15" required>
				</div>
				<div>
					<button class = "btn" type = "submit" name = "command" value = "profile_registration">
						<fmt:message key = "profile.registration" />
					</button>
					<input type = "button" style = "margin-left: 10px" class = "btn" value = "<fmt:message key = "back" />" 
					onclick = "history.back()">
				</div>
			</form>
		</div>
	</body>
</html>