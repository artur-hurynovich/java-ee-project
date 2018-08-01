<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key = "menu.instruments" /></title>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div class = "wrapper">
			<div class = "left">
				<p><b><fmt:message key = "instruments.new_group" /></b>
			</div>
			<div class = "right" style = "margin-top: 10px">
				<form action = "controller" id = "newGroupForm" style = "margin: 0" method = "post">
					<div class = "form-group row">
						<label class = "col-4"><fmt:message key = "instruments.group_name_in_en" /></label>
						<input type = "text" maxLength = "145" style = "width: 30%" name = "group_name_en" required>
					</div>
					<div class = "form-group row">
						<label class = "col-4"><fmt:message key = "instruments.group_name_in_ru" /></label>
						<input type = "text" maxLength = "145" style = "width: 30%" name = "group_name_ru" required>
						<c:if test = "${not empty groupNameValid and groupNameValid eq false}">
							<b>
								<label class = "col-form-label" style = "margin-left: 10px; color: #ff0000">
									<fmt:message key = "instruments.group_exists" />
								</label>
							</b>
						</c:if>
					</div>
				</form>
				<form action = "controller" id = "cancelForm" style = "margin: 0" method = "post"></form>
				<button class = "btn"  name = "command" form = "newGroupForm"
				value = "instruments_new_group">
					<fmt:message key = "add" />
				</button>
				<button class = "btn" style = "margin-left: 10px" name = "command" form = "cancelForm"
				value = "instruments_manage_groups_page">
					<fmt:message key = "cancel" />
				</button>
			</div>
		</div>
	</body>
</html>