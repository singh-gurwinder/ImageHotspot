<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../includes/jquery-ui.css">
<script type="text/javascript" src="../includes/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../includes/jquery-ui.js"></script>
<link rel="stylesheet" href="../includes/register.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
<script type="text/javascript" src="../includes/maintemplate.js"></script>
<script>
	$(function() {
		$("#tabs").tabs({
			active : <tiles:getAsString name="activeTab"/>
		}, {
			activate : function(event, ui) {
				tabActivateHandler();
			}
		});
	});
</script>
</head>
<body>
	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<h1>
		<tiles:getAsString name="heading" />
	</h1>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>

	<div id="tabs">
		<ul>
			<li><a href="#Product">Products</a></li>
			<li><a href="#ListProducts">List Products</a></li>
			<li><a href="#Keywords">Keywords</a></li>
		</ul>
		<div id="Product">
			<tiles:insertAttribute name="productMainLayout"></tiles:insertAttribute>
		</div>
		<div id="ListProducts">
			<tiles:insertAttribute name="listProductsLayout"></tiles:insertAttribute>
		</div>
		<div id="Keywords">
			<tiles:insertAttribute name="keywordMainLayout"></tiles:insertAttribute>
		</div>
	</div>

</body>
</html>