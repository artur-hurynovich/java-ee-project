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
				<c:choose>
					<c:when test = "${empty instrument.imgSource}">
						<img src = "img/treble.jpeg" align = "right" height = "250px" width = "auto">
					</c:when>
					<c:otherwise>
						<img src = "${instrument.imgSource}" align = "right" height = "250px" width = "auto">
					</c:otherwise>
				</c:choose>
				<c:forEach var = "field" items = "${instrument.fields}">
					<c:choose>
						<c:when test = "${lang eq 'en'}">
							${field.nameEn} : ${field.currentValue.nameEn}<br>
						</c:when>
						<c:otherwise>
							${field.nameRu} : ${field.currentValue.nameRu}<br>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<b><fmt:message key = "rating" /> ${instrument.rating}</b><br>
				<c:set var = "rated" value = "${rated}"></c:set>
				<c:set var = "user" value = "${sessionScope.user}"></c:set>
				<c:if test = "${not empty user}">
					<c:choose>
						<c:when test = "${rated eq true}">
							<i><fmt:message key = "instruments.rated" /></i><br>
						</c:when>
						<c:otherwise>
							<form action = "controller" method = "post">
								<input type = "hidden" name = "instrumentId" value = "${instrument.id}">
								<input type = "hidden" name = "userId" value = "${user.id }">
								<div class = "form-inline">
									<select name = "ratingValue" style = "width: 20%" required>
										<option>
										<c:forEach var = "val" begin = "1" end = "5">
											<option value = "${val}">
												${val}
											</option>
										</c:forEach>
									</select>
									<button class = "btn" type = "submit" name = "command" style = "margin-left: 10px" 
									value = "instruments_rate">
										<fmt:message key = "rate" />
									</button>
								</div>
							</form>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test = "${not empty reviews}">
					<b><fmt:message key = "instruments.connected_reviews" /></b><br>
					<c:forEach var = "review" items = "${reviews}">
						<a href = "controller?command=reviews_read_page&reviewId=${review.id}"><i>${review.titlePreview}</i></a>
					</c:forEach>
				</c:if>
				<c:if test = "${not empty user && (user.role eq 'MANAGER' || user.role eq 'ADMIN') && 
				empty instrument.imgSource}">
					<form action = "upload" style = "margin: 0" method = "post" enctype = "multipart/form-data">
						<fmt:message key = "instruments.add_photo" />
						<input type = "hidden" name = "instrumentId" value = "${instrument.id}">
						<input type = "file" name = "img" id = "uploadInput" accept = "image/jpg,image/jpeg"/>
						<button class = "btn" type = "submit" id = "uploadButton" disabled>
							<fmt:message key = "add" />
						</button>
					</form>
				</c:if>
				<div class = "form-inline" style = "margin-top: 10px">
					<c:choose>
						<c:when test = "${not empty user && (user.role eq 'MANAGER' || user.role eq 'ADMIN')}">
							<button class = "btn" data-toggle = "modal" data-target = "#instrumentsDeleteModal" 
							style = "margin-right: 10px">
								<fmt:message key = "delete" />
							</button>
						</c:when>
					</c:choose>
					<input type = "button" class = "btn" value = "<fmt:message key = "back" />" 
					onclick = "history.back()">
				</div>
			</div>
		</div>
		<div class = "modal fade" id = "instrumentsDeleteModal" role = "dialog">
  			<div class = "modal-dialog modal-dialog-centered" role = "document">
  				<div class = "modal-content">
     				<div class = "modal-body">
     					<c:forEach var = "field" items = "${instrument.fields}">
     						<c:if test = "${field.nameEn eq 'Mark'}">
								<c:choose>
									<c:when test = "${lang eq 'en'}">
										<c:set var = "mark" value = "${field.currentValue.nameEn}" />
									</c:when>
									<c:otherwise>
										<c:set var = "mark" value = "${field.currentValue.nameRu}" />
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test = "${field.nameEn eq 'Model'}">
								<c:choose>
									<c:when test = "${lang eq 'en'}">
										<c:set var = "model" value = "${field.currentValue.nameEn}" />
									</c:when>
									<c:otherwise>
										<c:set var = "model" value = "${field.currentValue.nameRu}" />
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
       					<p><b><fmt:message key = "instruments.delete_confirm" /></b>
						${mark} ${model}
     				</div>
     				<div class = "modal-footer">
     					<form action = "controller" method = "post" style = "margin: 0">
							<input type = "hidden" name = "instrumentId" value = "${instrument.id}">
							<button class = "btn" type = "submit" name = "command" value = "instruments_delete">
								<fmt:message key = "delete" />
							</button>
						</form>
       					<button class = "btn" style = "margin-left: 10px" type = "button" data-dismiss = "modal">
							<fmt:message key = "cancel" />
						</button>
    				</div>
    			</div>
  			</div>
		</div>
		<script type = "text/javascript">
			$("#uploadInput").change(function() {
		    	$("#uploadButton").prop("disabled", false);
		    });
		</script>
	</body>
</html>