var prodImg;
var product, keywords, currentImg, xMultiplier, yMultiplier, currentHotspot;
var tempId = 1;

/**
 * Function to retrieve image from a URL
 */
function getImage() {
	if(jQuery('#productImageURL').val().trim()==""){
		alert("No image URL provided.");
		jQuery('#productImageURL').val("");
		jQuery('#productImageURL').focus();
	}
	else{
		var image = {
			ID : 'Temp_' + tempId,
			URL : jQuery('#productImageURL').val(),
			Hotspots : [],
			Height : 0,
			Width : 0
		};
		var img = new Image();
		img.onload =function() { 
	    	image.Width = img.width;
			image.Height = img.height;
			product.Images.push(image);
			tempId++;
			jQuery("#productImages")
				.append($('<li id="Img_' + image.ID + '"><img src="' + image.URL
					+ '" onclick="setImage(this)" width="80px" height="80px" /></li>'));
	    }
	    img.onerror = function() { 
	    	alert("No Valid Image found at given URL");
			jQuery('#productImageURL').val("");
			jQuery('#productImageURL').focus();
	    }
	    img.src = image.URL;
	}
}

function imageExists(image_url){

    var http = new XMLHttpRequest();

    http.open('HEAD', image_url, false);
    http.send();

    return http.status != 404;

}

/**
 * Function to set Current Active image.
 * @param img Image
 */
function setImage(img) {
	jQuery("#productImage").empty();
	jQuery("#keywordList input:checkbox").prop('checked', false);
	jQuery('#productImage').append("<div></div>");
	prodImg = jQuery('#productImage div');
	prodImg.css('background-image', "url(" + img.src + ")");
	prodImg.addClass("productimage");
	currentImg = getImageByURL(img.src);
	xMultiplier = 600 / currentImg.Width;
	yMultiplier = 400 / currentImg.Height;
	jQuery(".hotspot").remove();
	currentImg.Hotspots.forEach(function(hotspot) {
		setHotspot(hotspot);
	});
}

/**
 * Function to search image using URL from Product object.
 * @param url
 * @returns {___anonymous_currentImg}
 */
function getImageByURL(url) {
	var selectedImage = {};
	product.Images.forEach(function(obj) {
		if (obj.URL == url) {
			selectedImage = obj;
		}
	});
	return selectedImage;
}

/**
 * Function to search Hotspot using Id from Current Image
 * @param id
 * @returns {___anonymous_currentHotspot}
 */
function getHotspotById(id) {
	var selectedHotspot = {};
	currentImg.Hotspots.forEach(function(hotspot) {
		if (hotspot.ID == id)
			selectedHotspot = hotspot;
	});
	return selectedHotspot;
}

/**
 * Function to set existing Hotspots on Current Image.
 * @param hotspot
 */
function setHotspot(hotspot) {
	prodImg.append("<div class='hotspot ui-widget-content'  id='"
		+ hotspot.ID + "' onmouseup = 'configureHotspot(this)'></div>");
	newHotspot = prodImg.find("div.hotspot:last");
	var left = (hotspot.Left * xMultiplier) + 'px';
	var top = (hotspot.Top * yMultiplier) + 'px';
	newHotspot.css({left : left, top : top});
		$(".hotspot").draggable({containment : "parent"});
}

/**
 * Function to initialize page
 */
function initpage() {
	jQuery('#productImageURL').focus();
	jQuery.ajax(
		{url : "/ImageHotspot/api/service/getProductAjax?productid=" + $.urlParam("productid")})
			.done(function(data) {
				product = data.Product;
				keywords = data.Keywords;
				setProduct();
				setKeywords();
	});
}

/**
 * Function to retrieve parameter from current URL
 */
$.urlParam = function(name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results == null) {
		return null;
	} else {
		return results[1] || 0;
	}
}

/**
 * Function to configure Current Hotspot and its associated Keywords.
 * @param obj
 */
function configureHotspot(obj) {
	jQuery(".activehotspot").removeClass("activehotspot");
	jQuery("#" + obj.id).addClass("activehotspot");
	currentHotspot = getHotspotById(obj.id);
	jQuery("#keywordList input:checkbox").prop('checked', false);
	var left = jQuery(obj).css('left');
	var top = jQuery(obj).css('top');
	currentHotspot.Left = Math.round(left.substring(0, left.length - 2) / xMultiplier);
	currentHotspot.Top = Math.round(top.substring(0, top.length - 2) / yMultiplier);
	currentHotspot.Keywords.forEach(function(keyword) {
		jQuery('#Keyword_' + keyword.ID).prop('checked', true);
	});
}

/**
 * Function to update Keywords from Active Hotspot.
 * @param keyword
 */
function updateCurrentHotspot(keyword) {
	if (currentHotspot != null) {
		if ($('#' + keyword.id).prop('checked')) {
			addKeywordToHotspot($('#' + keyword.id).val());
		} else {
			removeKeywordFromHotspot($('#' + keyword.id).val());
		}
	} else {
		alert("No Active Hotspot to update.");
	}
}

/**
 * Function to Add Keyword to Active Hotspot.
 * @param id
 */
function addKeywordToHotspot(id) {
	var newKeyword = {};
	keywords.forEach(function(keyword) {
		if (keyword.Keyword.ID == id)
			newKeyword = keyword.Keyword;
	});
	currentHotspot.Keywords.push(newKeyword);
}

/**
 * Function to Remove Keyword from Active Hotspot.
 * @param id
 */
function removeKeywordFromHotspot(id) {
	currentHotspot.Keywords.forEach(function(keyword) {
		if (keyword.ID == id)
			currentHotspot.Keywords.splice(currentHotspot.Keywords.indexOf(keyword), 1);
	});
}

/**
 * Function to Delete Current Image
 */
function deleteImage() {
	if (currentImg == null) {
		alert("No Current Image to delete.")
	} else {
		var result = confirm("Do you want to delete the Image?");
		if (result) {
			product.Images.forEach(function(img) {
				if (img.ID == currentImg.ID) {
					product.Images.splice(product.Images.indexOf(img), 1);
					jQuery("#Img_"+currentImg.ID).remove();
					jQuery("#productImage").empty();
					jQuery("#keywordList input:checkbox").prop('checked', false);
					currentImg = null;
					currentHotspot = null;
				}
			});
		}
	}
}

/**
 * Function to Delete Active Hotspot.
 */
function deleteHotspot() {
	if (currentHotspot == null) {
		alert("No Active Hotspot to delete.");
	} else {
		var result = confirm("Do you want to delete the Hotspot?");
		if (result) {
			currentImg.Hotspots.forEach(function(hotspot) {
				if (hotspot.ID == currentHotspot.ID) {
					currentImg.Hotspots.splice(currentImg.Hotspots.indexOf(hotspot), 1);
					jQuery("#" + currentHotspot.ID).remove();
					currentHotspot = null;
				}
			});
		}
	}
}

/**
 * Function to Set Product DOM elements.
 */
function setProduct() {
	jQuery('#productId').html(product.ID);
	jQuery('#productName').html(product.Name);
	jQuery('#productDescription').html(product.Description);
	product.Images = product.Images || [];
	product.Images.forEach(function(image) {
		jQuery("#productImages").append($('<li id = "Img_' + image.ID + '"><img src="' + image.URL
				+ '" onclick="setImage(this)" width="80px" height="80px" /></li>'));
			});
}

/**
 * Function to Set Keywords List.
 */
function setKeywords() {
	keywords.forEach(function(keyword) {
		jQuery("#keywordList").append('<input type="checkbox" value="' + keyword.Keyword.ID
				+ '" id = "Keyword_' + keyword.Keyword.ID + '" onclick = "updateCurrentHotspot(this)">'
				+ keyword.Keyword.Group + ':' + keyword.Keyword.Description + '<br>');
	});
}

/**
 * Function to Add new Hotspot.
 */
function addHotspot() {
	if (prodImg == null)
		alert("No Product Image.");
	else {
		prodImg.append("<div class='hotspot ui-widget-content'  id='Temp_"
				+ tempId + "' onmouseup = 'configureHotspot(this)'></div>");
		newHotspot = prodImg.find("div.hotspot:last");
		newHotspot.css({left : '10px', top : '10px'});
		var hotspot = {
			Left : 10,
			Top : 10,
			ID : newHotspot[0].id,
			Keywords : []
		};
		currentImg.Hotspots.push(hotspot);
		tempId++;
			$(".hotspot").draggable({containment : "parent"});
		configureHotspot(newHotspot[0]);
	}
}

/**
 * Function to Save Product using Ajax request.
 */
function saveProduct() {
	jQuery.ajax({
		url : "../api/service/updateProduct",
		type : "POST",
		data : {
			"product" : JSON.stringify(product),
			"_csrf" : jQuery("#csrf_token").val()
		},
		contentType : "application/x-www-form-urlencoded",
		dataType : "text"
	}).done(function(data) {
		alert(data);
	});
}