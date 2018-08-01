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
		<div>
			<div style = "margin-left: 10px; margin-top: 10px">
				<form action = "controller" method = "post" style = "margin: 0" id = "searchForm">
					<div style = "margin-bottom: 10px">
						<b><fmt:message key = "profile.enter_email" /></b>
					</div>
					<div class = "form-inline">
						<input type = "text" name = "email" maxlength = "40" required>
						<c:if test = "${not empty validEmail and validEmail eq false}">
							<b>
								<label style = "margin-left: 10px; color: #ff0000">
									<fmt:message key = "profile.email_not_found" />
								</label>
							</b>
						</c:if>
					</div>
				</form>
				<form action = "controller" method = "post" style = "margin: 0" id = "backForm"></form>
				<div class = "form-inline">
					<button class = "btn" type = "submit" style = "margin-top: 10px" name = "command" 
					value = "profile_change_role_continue_page" form = "searchForm">
						<fmt:message key = "search" />
					</button>
					<button class = "btn" type = "submit" style = "margin-top: 10px; margin-left: 10px" name = "command" 
					value = "profile_page"  form = "backForm">
						<fmt:message key = "back" />
					</button>
				</div>
			</div>
		</div>
	</body>
</html>