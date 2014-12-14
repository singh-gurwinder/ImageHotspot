/**
 * Function to submit Logout Form.
 */
function formSubmit() {
	document.getElementById("logoutForm").submit();
}

/**
 * Function to handle Tabs activation.
 */
function tabActivateHandler() {
	switch ($("#tabs").tabs("option", "active")) {
	case 0:
		window.location = "http://localhost:8080/ImageHotspot/admin/product";
		break;
	case 1:
		window.location = "http://localhost:8080/ImageHotspot/admin/main";
		break;
	case 2:
		window.location = "http://localhost:8080/ImageHotspot/admin/keyword";
		break;
	}
}
	