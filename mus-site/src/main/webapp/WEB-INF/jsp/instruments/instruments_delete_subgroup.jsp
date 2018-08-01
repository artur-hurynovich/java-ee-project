<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<html>
	<head>
		<title><fmt:message key = "menu.instruments" /></title>
		<script src = "https://code.jquery.com/jquery-3.3.1.js"></script>
		<script src = "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
		<script src = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div class = "wrapper">
			<div class = "left">
				<p><b><fmt:message key = "instruments.delete_subgroup" /></b>
			</div>
			<div class = "right" style = "margin-top: 10px">
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
								<form action = "controller" id = "deleteSubgroupForm" style = "margin: 0"
								method = "post"></form>
								<select id = "subgroupId" name = "subgroupId" form = "deleteSubgroupForm" style = "width: 30%">
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
				<form action = "controller" id = "cancelForm" style = "margin: 0" method = "post"></form>
				<button class = "btn" data-toggle = "modal" data-target = "#subgroupDeleteModal">
					<fmt:message key = "delete" />
				</button>
				<button class = "btn" style = "margin-left: 10px" name = "command" value = "instruments_manage_groups_page"
				form = "cancelForm">
					<fmt:message key = "cancel" />
				</button>
			</div>
		</div>
		<div class = "modal fade" id = "subgroupDeleteModal" role = "dialog">
  			<div class = "modal-dialog modal-dialog-centered" role = "document">
  				<div class = "modal-content">
      				<div class = "modal-body">
        				<p><b><fmt:message key = "instruments.delete_subgroup_confirm" /></b>
      				</div>
      				<div class = "modal-footer">
      					<button class = "btn" type = "submit" name = "command" value = "instruments_delete_subgroup" 
						form = "deleteSubgroupForm">
							<fmt:message key = "delete" />
						</button>
        				<button class = "btn" style = "margin-left: 10px" type = "button" data-dismiss = "modal">
							<fmt:message key = "cancel" />
						</button>
      				</div>
    			</div>
  			</div>
		</div>
		<script type = "text/javascript">
			$('#groupId').change(function(){
				var group = $("#groupId").val();
				var showDivId = 'div' + group;
				$("#subgroups > div").hide();
				$("#" + showDivId).show();
			});
		</script>
	</body>
</html>