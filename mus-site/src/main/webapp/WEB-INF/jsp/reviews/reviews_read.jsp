<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<c:set var = "user" value = "${sessionScope.user}" />
<html>
	<head>
		<title><fmt:message key = "menu.reviews" /></title>	
		<script src = "https://code.jquery.com/jquery-3.3.1.js"></script>
		<script src = "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
		<script src = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div style = "margin: 10px">
			<p><b>${review.title}</b>
			<textarea style = "width: 100%" rows = "15" disabled>${review.text}</textarea>
			<b><fmt:message key = "reviews.hashtags" /> </b>${review.hashtagsView}<br>
			<b><fmt:message key = "reviews.date" /> </b>${review.dateView}<br>
			<b><fmt:message key = "reviews.author" /> </b>${review.userName}
			<div class = "form-inline">
				<c:choose>
					<c:when test = "${not empty user && (user.role eq 'MANAGER' || user.role eq 'ADMIN')}">
						<form action = "controller" method = "post" style = "margin: 0">
							<input type = "hidden" name = "reviewId" value = "${review.id}">
							<button class = "btn" style = "margin-right: 10px" name = "command" 
							value = "reviews_edit_page" type = "submit">
								<fmt:message key = "edit" />
							</button>
						</form>
						<button class = "btn" data-toggle = "modal" data-target = "#reviewDeleteModal" 
						style = "margin-right: 10px">
							<fmt:message key = "delete" />
						</button>
					</c:when>
				</c:choose>
				<input type = "button" style = "margin-right: 10px" class = "btn" value = "<fmt:message key = "back" />" 
				onclick = "history.back()">
			</div>
			<div class = "modal fade" id = "reviewDeleteModal" role = "dialog">
  				<div class = "modal-dialog modal-dialog-centered" role = "document">
  					<div class = "modal-content">
      					<div class = "modal-body">
        					<b><fmt:message key = "reviews.delete_confirm" /></b> "${review.title}"
      					</div>
      					<div class = "modal-footer">
      						<form action = "controller" method = "post" style = "margin: 0">
								<input type = "hidden" name = "reviewId" value = "${review.id}">
								<button class = "btn" type = "submit" name = "command" value = "reviews_delete">
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
		</div>
	</body>
</html>