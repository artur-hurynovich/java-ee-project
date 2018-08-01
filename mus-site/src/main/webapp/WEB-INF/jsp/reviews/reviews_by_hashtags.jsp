<%@ page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.lang}" />
<fmt:setBundle basename = "local" />
<c:set var = "user" value = "${sessionScope.user}" />
<html>
	<head>
		<title><fmt:message key = "menu.reviews" /></title>
	</head>
	<body>
		<%@include file = "/WEB-INF/jsp/header.jsp" %>
		<div class = "wrapper">
			<div class = "left">
				<p><b><fmt:message key = "reviews.found" /> ${reviewsNumber}</b>
				<c:if test = "${not empty hashtagsQuery}">
					<p><b><fmt:message key = "reviews.hashtags_query" /> <i>${hashtagsQuery}</i></b>
				</c:if>
				<form action = "controller" method = "post">
					<div class = "form-inline">
						<div>
							<input type = "text" name = "hashtags" 
							placeholder = "<fmt:message key = "reviews.by_hashtags" />">
						</div>
						<div style = "margin-left: 10px">
							<button class = "btn" type = "submit" name = "command" value = "reviews_by_hashtags_page">
								<fmt:message key = "search" />
							</button>
						</div>
					</div>
					<div  style = "margin-top: 10px">
						<button class = "btn" type = "submit" name = "command" value = "reviews_page">
							<fmt:message key = "reviews.all" />
						</button>
					</div>
					<c:if test = "${not empty user && (user.role eq 'MANAGER' || user.role eq 'ADMIN')}">
						<div style = "margin-top: 10px">
							<button class = "btn" type = "submit" name = "command" value = "reviews_new_page">
								<fmt:message key = "reviews.new" />
							</button>
						</div>
					</c:if>
				</form>
				<p><b><fmt:message key = "reviews.popular_hashtags" /></b>
				<c:forEach var = "popularHashtag" items = "${popularHashtags}">
					<i><a href = "controller?command=reviews_by_hashtags_page&hashtags=${popularHashtag}">
					#${popularHashtag}</a></i>
				</c:forEach>
			</div>
			<div class = "right" style = "margin-top: 10px">
				<c:choose>
					<c:when test = "${empty reviews}">
						<p><b><fmt:message key = "nothing_found" /></b>
					</c:when>
					<c:otherwise>
						<ul class = "pagination">
							<c:forEach var = "page" begin = "1" end = "${pagesNumber}">	
								<c:choose>
									<c:when test = "${page == pageNumber}">
										<li class = "page-item" >
											<a class = "page-link" style = "color: #ffffff; background-color: #000000" 
											href = "controller?command=reviews_by_hashtags_page&page_number=${page}">${page}</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class = "page-item" >
											<a class = "page-link" style = "color: #000000; background-color: #ffffff" 
											href = "controller?command=reviews_by_hashtags_page&page_number=${page}">${page}</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
  						</ul>
						<c:forEach var = "review" items = "${reviews}">
							<div class="card" style = "width: 100%; margin-bottom: 10px" >
								<ul class = "list-group list-group-flush">
									<li class = "list-group-item">	
										<h6 class = "card-title">${review.titlePreview}</h6>
										${review.textPreview}
									</li>
									<li class = "list-group-item">${review.dateView}, ${review.userName}</li>
								</ul>
								<div class = "card-block form-inline" style = "margin-left: 10px">
									<form action = "controller" method = "post">
										<input type = "hidden" name = "reviewId" value = "${review.id}">
										<button class = "btn" name = "command" value = "reviews_read_page" 
										type = "submit" style = "margin-left: 10px; margin-top:10px">
											<fmt:message key = "reviews.read" />
										</button>
									</form>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</body>
</html>