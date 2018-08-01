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
				<form action = "controller" style = "margin: 0" method = "post" >
					<input type = "hidden" name = "subgroupId" value = "${subgroupId}">
					<c:forEach var = "field" items = "${fields}">
						<c:choose>
							<c:when test = "${sessionScope.lang eq 'en'}">
								<c:set var = "fieldName" value = "${field.nameEn}"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var = "fieldName" value = "${field.nameRu}"></c:set>
							</c:otherwise>
						</c:choose>
						<div class = "container">
							<div class = "row align-items-end">
								<div class = "col-sm-3">
									<b>${fieldName}:</b>
								</div>
								<div class = "col-sm-4">
									<span id = "${field.id}_new_en_label" style = "display: none">
										<fmt:message key = "instruments.value_name_in_en" />
									</span>
								</div>
								<div class = "col-sm-4">
									<span id = "${field.id}_new_ru_label" style = "display: none">
										<fmt:message key = "instruments.value_name_in_ru" />
									</span>
								</div>
							</div>
							<div class = "row align-items-start">
								<div class = "col-sm-3">
									<div class = "form-check">
			  							<label class = "form-check-label">
				    						<input type = "radio" class = "form-check-input" name = "${field.id}" 
				    						value = "${field.id}_existing" checked>
				    						<fmt:message key = "instruments.existing_value" />
				  						</label>
			  						</div>
			  						<div class = "form-check">
			  							<label class = "form-check-label">
				    						<input type = "radio" class = "form-check-input" name = "${field.id}" 
				    						value = "${field.id}_new">
				    						<fmt:message key = "instruments.new_value" />
				  						</label>
			  						</div>
								</div>
								<div class = "col-sm-4">
									<select name = "field_existing_${field.id}" style = "width: 100%"
									id = "${field.id}_existing" required>
										<option>
										<c:forEach var = "fieldValue" items = "${field.existingValues}">
											<option value = "${fieldValue.id}">
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
									<input id = "${field.id}_new_en" type = "text" maxLength = "145" size = "35%"
									name = "field_new_${field.id}_en" style = "display: none" disabled>
								</div>
								<div class = "col-sm">
									<input id = "${field.id}_new_ru" type = "text" maxLength = "145" size = "35%"
									name = "field_new_${field.id}_ru" style = "display: none" disabled>
								</div>
							</div>
						</div>
					</c:forEach>
					<button class = "btn" style = "margin-top: 10px" name = "command" value = "instruments_new">
						<fmt:message key = "add" />
					</button>
					<input type = "button" class = "btn" style = "margin-left: 10px; margin-top: 10px" 
					value = "<fmt:message key = "cancel" />" onclick = "history.back()">
				</form>
			</div>
		</div>
		<script type = "text/javascript">
			$('input[type=radio]').change(function(){
				var radioName = $(this).attr('name');
				var radioValue = this.value;
				if (radioValue == radioName + '_existing') {
					$('#' + radioName + '_existing').prop('disabled', false);
					$('#' + radioName + '_existing').prop('required', true);
					$('#' + radioName + '_existing').show();
					$('#' + radioName + '_new_en').prop('disabled', true);
					$('#' + radioName + '_new_ru').prop('disabled', true);
					$('#' + radioName + '_new_en').prop('required', false);
					$('#' + radioName + '_new_ru').prop('required', false);
					$('#' + radioName + '_new_en_label').hide();
					$('#' + radioName + '_new_ru_label').hide();
					$('#' + radioName + '_new_en').hide();
					$('#' + radioName + '_new_ru').hide();
				} else {
					$('#' + radioName + '_existing').prop('disabled', true);
					$('#' + radioName + '_existing').prop('required', false);
					$('#' + radioName + '_existing').hide();
					$('#' + radioName + '_new_en').prop('disabled', false);
					$('#' + radioName + '_new_ru').prop('disabled', false);
					$('#' + radioName + '_new_en').prop('required', true);
					$('#' + radioName + '_new_ru').prop('required', true);
					$('#' + radioName + '_new_en_label').show();
					$('#' + radioName + '_new_ru_label').show();
					$('#' + radioName + '_new_en').show();
					$('#' + radioName + '_new_ru').show();
				}
			}); 
		</script>
	</body>
</html>