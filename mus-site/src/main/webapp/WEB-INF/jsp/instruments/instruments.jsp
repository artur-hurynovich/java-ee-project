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
			<c:set var = "lang" value = "${sessionScope.lang}"></c:set>
			<div class = "left">
				<c:if test = "${not empty user && user.role eq 'ADMIN'}">
					<form action = "controller" method = "post" style = "margin: 0">
						<button class = "btn"  style = "margin-bottom: 10px" type = "submit" name = "command" 
						value = "instruments_manage_groups_page">
							<fmt:message key = "instruments.manage_groups" />
						</button>
					</form>
				</c:if>
				<c:choose>
					<c:when test = "${empty groups}">
						<p><b><fmt:message key = "nothing_found" /></b>
					</c:when>
					<c:otherwise>
						<c:forEach var = "group" items = "${groups}">
							<div class = "dropdown">
								<button class = "btn dropdown-toggle" type = "button" style = "width: 95%"
								data-toggle = "dropdown" aria-haspopup = "true" aria-expanded = "true">
									<c:choose>
		    							<c:when test = "${lang eq 'en'}">
		    								${group.nameEn}
		    							</c:when>
		    							<c:otherwise>
		    								${group.nameRu}
		    							</c:otherwise>
		    						</c:choose>
									<span class = "caret"></span>
								</button>
								<ul class = "dropdown-menu" style = "width: 95%">
									<c:forEach var = "subgroup" items = "${group.subgroups}">
										<li>
											<form action = "controller" method = "post" style = "margin: 0">
		            							<button class = "btn dropdown-item" 
		            							name = "command" value = "instruments_subgroup_page">
		            								<c:choose>
			            								<c:when test = "${lang eq 'en'}">
						    								${subgroup.nameEn}
						    							</c:when>
						    							<c:otherwise>
						    								${subgroup.nameRu}
						    							</c:otherwise>
						    						</c:choose>
		            							</button>
		            							<input type = "hidden" name = "subgroupId" value = "${subgroup.id}">
		            						</form>
		    							</li>
									</c:forEach>
								</ul>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				<c:if test = "${not empty instruments}">
					<p><b><fmt:message key = "instruments.total" /> ${instrumentsNumber}</b>
				</c:if>
			</div>
			<div class = "right" style = "margin-top: 10px">
				<c:if test = "${not empty user && (user.role eq 'MANAGER' || user.role eq 'ADMIN')}">
					<form action = "controller" method = "post" style = "margin: 0">
						<button class = "btn"  style = "margin-bottom: 10px" type = "submit" name = "command" 
						value = "instruments_new_page">
							<fmt:message key = "instruments.new" />
						</button>
					</form>
				</c:if>
			</div>
		</div>
	</body>
</html>