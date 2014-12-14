<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${result == 'success' }">
		<h3>Keyword updated.</h3>
	</c:when>
	<c:otherwise>
		<springForm:form method="post" commandName="keyword"
			action="modifyKeyword">
			<input type="hidden" name="formType" value="modify" />
			<table>
				<tr>
					<td>Keyword ID:</td>
					<td colspan="2"><springForm:input path="keywordId" readonly="true" /></td>
				</tr>
				<tr>
					<td>Keyword Description:</td>
					<td><springForm:input path="description" /></td>
					<td><springForm:errors path="description" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Keyword Group:</td>
					<td><springForm:input path="keywordGroup" /></td>
					<td><springForm:errors path="keywordGroup" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Keyword Ranking:</td>
					<td><springForm:input path="ranking" /></td>
					<td><springForm:errors path="ranking" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="Update"></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</springForm:form>
	</c:otherwise>
</c:choose>
