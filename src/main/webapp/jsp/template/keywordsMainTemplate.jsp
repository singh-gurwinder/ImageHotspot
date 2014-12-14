<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="navigation">
	<ul>
		<li><a href="keyword">Keywords Home</a></li>
		<li><a href="addKeyword">Add Keyword</a></li>
		<li><a href="editKeyword">Edit Keyword</a></li>
	</ul>
</div>
<div class="content">
	<tiles:insertAttribute name="keywordContent" />
</div>