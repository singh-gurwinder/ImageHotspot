/**
 * Function to initialize page
 */
function initpage(){
	jQuery.ajax({
		url : "http://localhost:8080/ImageHotspot/api/service/"
	})
	.done(
			function(data) {
				data.Result.forEach(function(result) {
					result.Product.Images.forEach(function(image) {
						jQuery('<img />').attr({src : image.URL, width : 250, height : 200})
							.addClass("imageframe").appendTo($('<a />').attr(
								{href : 'http://localhost:8080/ImageHotspot/client/product.html?product_id=' + result.Product.ID + '&image_id=' + image.ID})
									.appendTo($('#prodContainer')));
									});
						});
			})
}