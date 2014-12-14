<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${result == 'success' }">
		<h3>Product updated.</h3>
	</c:when>
	<c:otherwise>
		<springForm:form method="post" commandName="product"
			action="modifyProduct">
			<input type="hidden" name="formType" value="modify" />
			<table>
				<tr>
					<td>Product ID:</td>
					<td colspan="2"><springForm:input path="ProductId" readonly="true" /></td>
				</tr>
				<tr>
					<td>Product Name:</td>
					<td><springForm:input path="productName" /></td>
					<td><springForm:errors path="productName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Product Description:</td>
					<td><springForm:input path="description" /></td>
					<td><springForm:errors path="description" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="Update"></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</springForm:form>
	</c:otherwise>
</c:choose>
