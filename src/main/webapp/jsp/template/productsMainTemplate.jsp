<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="navigation">
	<ul>
		<li><a href="product">Products Home</a></li>
		<li><a href="addProduct">Add Product</a></li>
	</ul>
</div>
<div class="content">
	<tiles:insertAttribute name="productContent" />
</div>