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
				<c:choose>
					<c:when test = "${not empty instruments}">
						<b><fmt:message key = "instruments.total" /> ${instrumentsNumber}</b>
					</c:when>
					<c:otherwise>
						<b><fmt:message key = "nothing_found" /></b>
					</c:otherwise>
				</c:choose>
				<form action = "controller" style = "margin: 0" method = "post" id = "filterForm">
					<input type = "hidden" name = "subgroupId" value = "${subgroup.id}">
					<b><i><fmt:message key = "filter.by" /></i></b>
					<c:forEach var = "field" items = "${fields}">
						<c:choose>
							<c:when test = "${sessionScope.lang eq 'en'}">
								<c:set var = "fieldName" value = "${field.nameEn}"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var = "fieldName" value = "${field.nameRu}"></c:set>
							</c:otherwise>
						</c:choose>
						<div>
							${fieldName}:
						</div>
						<select name = "field_${field.id}" style = "width: 95%" required>
							<option value = "empty">
								<fmt:message key = "any_value" />
							</option>
							<c:forEach var = "fieldValue" items = "${field.existingValues}">
								<option value = "value_${fieldValue.id}">
									<c:choose>
										<c:when test = "${sessionScope.lang eq 'en'}">
											${fieldValue.nameEn}
										</c:when>
										<c:otherwise>
											${fieldValue.nameRu}
										</c:otherwise>
									</c:choose>
								</option>
							</c:forEach>
						</select>
					</c:forEach>
				</form>
				<button class = "btn" style = "margin-top: 10px" type = "submit" name = "command" form = "filterForm"
				value = "instruments_subgroup_filter_page">
					<fmt:message key = "filter" />
				</button>
				<button class = "btn" style = "margin-top: 10px; margin-left: 10px" id = "reset">
					<fmt:message key = "reset_filter" />
				</button>
			</div>
			<div class = "right" style = "margin-top: 10px">
				<c:if test = "${not empty user && (user.role eq 'MANAGER' || user.role eq 'ADMIN')}">
					<form action = "controller" method = "post" style = "margin: 0">
						<button class = "btn" type = "submit" name = "command" 
						value = "instruments_new_page">
							<fmt:message key = "instruments.new" />
						</button>
					</form>
				</c:if>
				<c:set var = "lang" value = "${sessionScope.lang}"></c:set>
				<c:choose>
					<c:when test = "${lang eq 'en'}">
						<p><b>${subgroup.nameEn}</b>
					</c:when>
					<c:otherwise>
						<p><b>${subgroup.nameRu}</b>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test = "${empty instruments}">
						<p><b><fmt:message key = "nothing_found" /></b>
					</c:when>
					<c:otherwise>
						<ul class = "pagination">
							<c:forEach var = "page" begin = "1" end = "${pagesNumber}">	
								<c:choose>
									<c:when test = "${page == pageNumber}">
										<li class = "page-item" >
											<a class = "page-link" style = "color: #ffffff; background-color: #000000" 
											href = "controller?command=instruments_subgroup_page&page_number=${page}&subgroupId=${subgroup.id}">
												${page}
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class = "page-item" >
											<a class = "page-link" style = "color: #000000; background-color: #ffffff" 
											href = "controller?command=instruments_subgroup_page&page_number=${page}&subgroupId=${subgroup.id}">
												${page}
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
	  					</ul>
						<c:forEach var = "instrument" items = "${instruments}">
							<div class="card" style = "width: 100%; margin-bottom: 10px" >
								<ul class = "list-group list-group-flush">
									<li class = "list-group-item">
										<c:forEach var = "field" items = "${instrument.fields}">
											<c:if test = "${field.nameEn eq 'Mark'}">
												<c:set var = "markEn" value = "${field.currentValue.nameEn}"></c:set>
												<c:set var = "markRu" value = "${field.currentValue.nameRu}"></c:set>
											</c:if>
											<c:if test = "${field.nameEn eq 'Model'}">
												<c:set var = "modelEn" value = "${field.currentValue.nameEn}"></c:set>
												<c:set var = "modelRu" value = "${field.currentValue.nameRu}"></c:set>
											</c:if>
										</c:forEach>
										<c:choose>
											<c:when test = "${empty instrument.imgSource}">
												<img src = "img/treble.jpeg" height = "50px" width = "auto">
											</c:when>
											<c:otherwise>
												<img src = "${instrument.imgSource}" height = "50px" width = "auto">
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test = "${lang eq 'en'}">
												<h6 class = "card-title">${markEn} ${modelEn} </h6>
											</c:when>
											<c:otherwise>
												<h6 class = "card-title">${markRu} ${modelRu} </h6>
											</c:otherwise>
										</c:choose>
										<i><fmt:message key = "rating" /></i> ${instrument.rating}
									</li>
								</ul>
								<div class = "card-block form-inline" style = "margin-left: 10px">
									<form action = "controller" method = "post">
										<input type = "hidden" name = "instrumentId" value = "${instrument.id}">
										<button class = "btn" name = "command" value = "instruments_read_page" 
										type = "submit" style = "margin-left: 10px; margin-top:10px">
											<fmt:message key = "instruments.more" />
										</button>
									</form>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<script type = "text/javascript">
			$('#reset').click(function(){
				$('#filterForm select option[value="empty"]').attr("selected", "selected");
				}
			);
		</script>
	</body>
</html>