<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="../includes/addhotspot.css">

<script type="text/javascript" src="../includes/addhotspot.js"></script>
<script type="text/javascript">
	window.onload = initpage;
</script>


<table>
	<tr>
		<td>Product ID:</td>
		<td><div id="productId"></div></td>
	</tr>
	<tr>
		<td>Product Name:</td>
		<td><div id="productName"></div></td>
	</tr>
	<tr>
		<td>Product Description:</td>
		<td><div id="productDescription"></div></td>
	</tr>
	<tr>
		<td>Product Image:</td>
		<td><input type="text" id="productImageURL" placeholder="Enter Image URL"></td>
	</tr>
	<tr>
		<td>
			<input type="button" value="Add Image" onclick="getImage()">
			<input type="button" value="Delete Image" onclick="deleteImage()">
		</td>
		<td><input type="button" value="Save Product" onclick="saveProduct()"></td>
	</tr>
	<tr>
		<td><ul id="productImages"></ul></td>
	</tr>
</table>
<table>
	<tr>
		<td><div id="productImage" class="imageframe"></div></td>
		<td><div id="keywordList" class="keywordsframe"></div></td>
	</tr>
	<tr>
		<td><input type="button" value="Add Hotspot" onclick="addHotspot()"> <input type="button"
				value="Delete Hotspot" onclick="deleteHotspot()">
		</td>
		<td><input id="csrf_token" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></td>
	</tr>
</table>
