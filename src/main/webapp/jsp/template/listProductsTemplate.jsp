<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${empty productList }">
		<h3>No Products Found.</h3>
	</c:when>
	<c:otherwise>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Description</th>
			</tr>

			<c:forEach items="${productList }" var="product">
				<tr>
					<td>${product.productId }</td>
					<td>${product.productName }</td>
					<td>${product.description }</td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
