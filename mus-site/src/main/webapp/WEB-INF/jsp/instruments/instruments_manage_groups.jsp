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
				<b><fmt:message key = "choose_action" /></b>
				<div>
	      			<a href = "controller?command=instruments_new_group_page">
	      			<fmt:message key = "instruments.new_group" /></a>
	      		</div>
	      		<div>
	      			<a href = "controller?command=instruments_new_subgroup_page">
	      			<fmt:message key = "instruments.new_subgroup" /></a>
	      		</div>
	      		<div>
	      			<a href = "controller?command=instruments_delete_group_page">
	      			<fmt:message key = "instruments.delete_group" /></a>
	      		</div>
	      		<div>
	      			<a href = "controller?command=instruments_delete_subgroup_page">
	      			<fmt:message key = "instruments.delete_subgroup" /></a>
	      		</div>
				<form action = "controller" id = "backForm" style = "margin: 0" method = "post"></form>
				<button class = "btn" name = "command" form = "backForm" style = "margin-top: 10px"
				value = "instruments_page">
					<fmt:message key = "back" />
				</button>
			</div>
			<div class = "right" style = "margin-top: 10px">
			</div>
		</div>
	</body>
</html>