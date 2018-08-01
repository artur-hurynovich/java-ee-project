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
				<form action = "controller" id = "newSubgroupForm" style = "margin: 0" method = "post">
					<div class = "form-group row">
						<label class = "col-5"><fmt:message key = "instruments.subgroup_name_in_en" /></label>
						<input type = "text" maxLength = "145" 
						style = "width: 30%" name = "subgroup_name_en" required>
					</div>
					<div class = "form-group row">
						<label class = "col-5"><fmt:message key = "instruments.subgroup_name_in_ru" /></label>
						<input type = "text" maxLength = "145" 
						style = "width: 30%" name = "subgroup_name_ru" required>
						<c:if test = "${not empty subgroupNameValid and subgroupNameValid eq false}">
							<b>
								<span style = "margin-left: 10px; color: #ff0000">
									<fmt:message key = "instruments.subgroup_exists" />
								</span>
							</b>
						</c:if>
					</div>
					<div class = "form-group row">
						<label class = "col-5"><fmt:message key = "instruments.add_subgroup_to" /></label>
						<c:set var = "lang" value = "${sessionScope.lang}" />
    					<select style = "width: 30%" name = "groupId" required>
    						<option>
							<c:forEach var = "group" items = "${groups}">
								<option value = "${group.id}">
									<c:choose>
										<c:when test = "${lang eq 'en'}">
											${group.nameEn}
										</c:when>
										<c:otherwise>
											${group.nameRu}
										</c:otherwise>
									</c:choose>
								</option>
		    				</c:forEach>
						</select>
					</div>
					<div class = "form-group row">
						<i><label style = "color: #ff0000" class = "col-10 col-form-label">
						<fmt:message key = "instruments.default_fields" /></label></i>
					</div>
					<div class = "form-group row align-items-start">
						<label class = "col-5"><fmt:message key = "instruments.fields_number" /></label>
						<select style = "width: 30%" name = "fieldsNumber" required>
		    				<option>
							<c:forEach var = "number" begin = "0" end = "10">
								<option value = "${number}">
									${number}
								</option>
			    			</c:forEach>
						</select>
					</div>
				</form>
				<form action = "controller" id = "cancelForm" style = "margin: 0" method = "post"></form>
				<button class = "btn" name = "command" form = "newSubgroupForm"
				value = "instruments_new_subgroup_continue_page">
					<fmt:message key = "continue" />
				</button>
				<button class = "btn" style = "margin-left: 10px" name = "command" form = "cancelForm"
				value = "instruments_manage_groups_page">
					<fmt:message key = "cancel" />
				</button>
			</div>
		</div>
	</body>
</html>