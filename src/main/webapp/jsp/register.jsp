<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Image Hotspot: Register</title>
<script type="text/javascript" src="includes/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" href="includes/register.css">
<script type="text/javascript">
	$(document).ready(function() {
		initpage();
	});
	function initpage() {
		jQuery('#username').focus();
	}
</script>
</head>
<body>
	<h1>Image Hotspot Registration</h1>
	<c:choose>
		<c:when test="${result == 'success' }">
			<div id="login-box">
				<h3>Registration successful.</h3>
				<p>
					Continue to <a href="<c:url value='/login' />">Login</a> page.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<div id="login-box">
				<h3>Register:</h3>
				<springForm:form method="post" commandName="user" action="register">
					<input type="hidden" name="formType" value="add" />
					<table>
						<tr>
							<td>User Name:</td>
							<td><springForm:input id="username" path="username" /></td>
							<td><springForm:errors path="username" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><springForm:password path="password" /></td>
							<td><springForm:errors path="password" cssClass="error" /></td>
						</tr>
						<tr>
							<td colspan="3"><input type="submit" value="Save"></td>
						</tr>
					</table>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</springForm:form>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>