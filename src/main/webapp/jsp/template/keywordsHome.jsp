<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${empty keywordList }">
		<h3>No Keywords Found.</h3>
	</c:when>
	<c:otherwise>
		<table>
			<tr>
				<th>ID</th>
				<th>Description</th>
				<th>Group</th>
				<th>Ranking</th>
			</tr>

			<c:forEach items="${keywordList }" var="keyword">
				<tr>
					<td>${keyword.keywordId }</td>
					<td>${keyword.description }</td>
					<td>${keyword.keywordGroup }</td>
					<td>${keyword.ranking }</td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
