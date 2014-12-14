/**
 * Function to initialize page
 */
function initpage(){
	jQuery.ajax({url : "http://localhost:8080/ImageHotspot/api/service/" + $.urlParam("product_id")})
	.done(function(data) {
		jQuery('#prodName').text(data.Product.Description);
		var hotspotImage = "http://localhost:8080" + data.HotspotImage;
		var imageID = $.urlParam("image_id");
		data.Product.Images.forEach(function(image) {
			if (imageID == image.ID) {
				jQuery('#prodContainer').append("<div class='imageframe'></div>");
				var prodImg = jQuery('#prodContainer div.imageframe');
				prodImg.addClass("prodContainer");
				prodImg.css('background-image', "url(" + image.URL + ")");
				prodImg.width(image.Width);
				prodImg.height(image.Height);
				image.Hotspots.forEach(function(hotspot) {
					prodImg.append("<div></div>");
					var divElement = jQuery('.imageframe div:last');
					divElement.attr('id', 'Hotspot_' + hotspot.ID);
					divElement.addClass('hotspot');
					var pos = prodImg.offset();
					var left = (hotspot.Left) + 'px';
					var top = (hotspot.Top) + 'px';
					divElement.css({left : left, top : top});
					jQuery(divElement.get(0)).attr('onmouseenter', 'toggleState(this, "expand")');
					jQuery(divElement.get(0)).attr('onmouseleave', 'toggleState(this, "collapse")');
					jQuery(divElement).append('<div>');
					var formElement = jQuery('#prodContainer div:last');
					formElement.attr('id', 'Hotspot_Form_' + hotspot.ID);
					jQuery(formElement).append("<h5>Search:</h5><p>Select keywords to Search similar products.");
					hotspot.Keywords.forEach(function(keyword) {
						jQuery(formElement.last()).append("<label><input type='checkbox' name='searchKey' value='" + keyword.Description + "'>"
																				+ keyword.Description + "</label><br>");
					});
					jQuery(formElement).append("<input type='button' value='Search' onclick = 'searchProducts(this)'>");
					jQuery(divElement).children().hide();
					});
				}
			});
		});
}
 /**
  * Function to retrieve parameter from URL.
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
 * Function to toggle state of a Hotspot on onmouseenter/onmouseleave events.
 * @param obj Hotspot Object
 * @param state State (collapse/expand)
 */
function toggleState(obj, state) {
	if (state == 'collapse') {
		jQuery(obj).animate({
			height : '0px',
			width : '0px'
		}, "slow");
		jQuery(obj).children().hide();
	} else {
		jQuery(obj).animate({
			height : '200px',
			width : '150px'
		}, "slow");
		jQuery(obj).children().show();
	}
}

/**
 * Function to search Products using Keywords from Server through Ajax request
 * @param obj Hotspot Object
 */
function searchProducts(obj) {
	var searchKey = jQuery(obj).parent().find('input[type="checkbox"]:checked');
	var searchURL = "http://localhost:8080/ImageHotspot/api/service/ajax/search?keywords=";
	searchKey.each(function(e) {
		searchURL += this.value + ',';
	})
	searchURL = searchURL.slice(0, -1);
	jQuery.ajax({
		url : searchURL
	}).done(function(data) {
		showResult(data);
	});
}

/**
 * Function to Show Products retrieved by searchProducts function.
 * @param data Data received from Server
 */
function showResult(data) {
	data.Result.forEach(function(result) {
		result.Product.Images.forEach(function(image) {
			jQuery('<img />').attr({src : image.URL, width : 250, height : 200}).addClass("imageframe")
				.appendTo($('<a />').attr({href : 'http://localhost:8080/ImageHotspot/client/product.html?product_id=' + result.Product.ID + '&image_id=' + image.ID})
					.appendTo($('#iframeSearchResult')));
		});
	});
	jQuery("#iframeSearchResult").addClass("resultframe");
	jQuery("#iframeSearchResult").append('<div id="closeButton" onclick="closeButton()"/>');
	jQuery("#closeButton").addClass("closebutton");
	jQuery("#iframeSearchResult").css("display", "block");
}

/**
 * Close the Search Result frame.
 */
function closeButton() {
	jQuery("#iframeSearchResult").empty();
	jQuery("#iframeSearchResult").css("display", "none");
}