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
				<p><b><fmt:message key = "instruments.new_subgroup" /></b>
			</div>
			<div class = "right" style = "margin-top: 10px">
				<form action = "controller" style = "margin: 0" method = "post">
					<input type = "hidden" name = "groupId" value = "${groupId}">
					<input type = "hidden" name = "subgroupNameEn" value = "${subgroupNameEn}">
					<input type = "hidden" name = "subgroupNameRu" value = "${subgroupNameRu}">
					<input type = "hidden" name = "fieldsNumber" value = "${fieldsNumber}">
					<b><fmt:message key = "instruments.subgroup_name_in_en" /> <i>${subgroupNameEn}</i></b><p>
					<b><fmt:message key = "instruments.subgroup_name_in_ru" /> <i>${subgroupNameRu}</i></b>
					<div class = "form-group row">
						<span class = "col-2"></span>
						<span class = "col-4"><fmt:message key = "instruments.field_name_in_en" /></span>
						<span class = "col-4" style = "margin-left: 10px"><fmt:message key = "instruments.field_name_in_ru" />
						</span>
					</div>
					<div class = "form-group row">
						<span class = "col-2"></span>
						<input class = "col-4" type = "text" value = "Mark" readonly>
						<input class = "col-4" style = "margin-left: 10px" type = "text" value = "Марка" readonly>
					</div>
					<div class = "form-group row">
						<span class = "col-2"></span>
						<input class = "col-4" type = "text" value = "Model" readonly>
						<input class = "col-4" style = "margin-left: 10px" type = "text" value = "Модель" readonly>
					</div>
					<c:forEach var = "fieldNumber" begin = "1" end = "${fieldsNumber}">
						<div class = "form-group row">
							<span class = "col-2"><fmt:message key = "instruments.field" /> ${fieldNumber}</span>
							<input class = "col-4" type = "text" maxLength = "145" 
							name = "field_name_en_${fieldNumber}" required>
							<input class = "col-4" style = "margin-left: 10px" type = "text" maxLength = "145" 
							name = "field_name_ru_${fieldNumber}" required>
						</div>
					</c:forEach>
					<button class = "btn" name = "command" value = "instruments_new_subgroup">
						<fmt:message key = "add" />
					</button>
					<input type = "button" class = "btn" style = "margin-left: 10px" value = "<fmt:message key = "cancel" />" 
					onclick = "history.back()">
				</form>
			</div>
		</div>
	</body>
</html>