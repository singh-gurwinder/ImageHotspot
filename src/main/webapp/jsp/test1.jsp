<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Image Hotspot</title>
</head>
<body>
	<c:choose>
		<c:when test="${!empty Product }">
			${Product}
		</c:when>
		<c:when test="${!empty Statistics }">
			${Statistics}
		</c:when>
		<c:otherwise>
			Test Data added.
		</c:otherwise>
	</c:choose>
</body>
</html>