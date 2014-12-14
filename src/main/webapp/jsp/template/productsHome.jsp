<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty id }">
	<h4>Product Deleted. Product ID: ${id }</h4>
</c:if>
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
				<th>Actions</th>
			</tr>

			<c:forEach items="${productList }" var="product">
				<tr>
					<td>${product.productId }</td>
					<td>${product.productName }</td>
					<td>${product.description }</td>
					<td>
						<a href="modifyProduct?productid=${product.productId }">Edit</a>&nbsp;
						<a href="product?deleteproduct=${product.productId }">Delete</a>&nbsp;
						<a href="addHotspot?productid=${product.productId }">Product Image/Hotspot</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
