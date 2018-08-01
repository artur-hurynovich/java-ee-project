<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key = "menu.instruments" /></title>
		<script src = "https://code.jquery.com/jquery-3.3.1.js"></script>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div class = "wrapper">
			<div class = "left">
				<p><b><fmt:message key = "instruments.new" /></b>
			</div>
			<div class = "right" style = "margin-top: 10px">
				<form action = "controller" id = "newInstrumentForm" style = "margin: 0" method = "post">
					<div class = "form-group row">
						<label class = "col-3"><fmt:message key = "instruments.choose_group" /></label>
						<c:set var = "lang" value = "${sessionScope.lang}" />
						<select id = "groupId" style = "width: 30%">
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
					<c:forEach var = "group" items = "${groups}">
						<div id = "subgroups">
							<div id = "div${group.id}" style = "display: none">
								<div class = "form-group row">
									<label class = "col-3"><fmt:message key = "instruments.choose_subgroup" /></label>
									<select id = "subgroupId${group.id}" name = "subgroupId" style = "width: 30%">
										<option>
										<c:forEach var = "subgroup" items = "${group.subgroups}">
											<option value = "${subgroup.id}">
												<c:choose>
													<c:when test = "${lang eq 'en'}">
														${subgroup.nameEn}
													</c:when>
													<c:otherwise>
														${subgroup.nameRu}
													</c:otherwise>
												</c:choose>
											</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</c:forEach>
				</form>
				<form action = "controller" id = "cancelForm" style = "margin: 0" method = "post"></form>
				<button class = "btn" name = "command" form = "newInstrumentForm"
				value = "instruments_new_continue_page">
					<fmt:message key = "continue" />
				</button>
				<button class = "btn" style = "margin-left: 10px" name = "command" form = "cancelForm"
				value = "instruments_page">
					<fmt:message key = "cancel" />
				</button>
			</div>
		</div>
		<script type = "text/javascript">
			$('#groupId').change(function(){
				var groupId = $("#groupId").val();
				var showDivId = 'div' + groupId;
				$("#subgroups > div").hide();
				$("#" + showDivId).show();
				$("#subgroups > div > div > select").prop('disabled', true);
				$("select[id=subgroupId" + groupId + "]").prop('disabled', false);
			});
		</script>
	</body>
</html>