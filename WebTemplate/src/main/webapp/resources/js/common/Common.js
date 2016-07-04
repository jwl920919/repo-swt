/** Load and parse .properties files */
function systemAlert(message) {
	systemAlert("layDiv", message)
}
function systemAlert(divElement, title, message) {
	$("#layDiv").attr("style", "visibility: visible");
	$("#alertTitle").text(title);
	$("#alertMessage").text(message);
	
	var tag = "";
	tag += "<div class='alert alert-warning alert-dismissible fade in' role='alert'>";    
    tag += "     <button onclick='fnAlertClose(\"layDiv\")' type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
    tag += "     <h4><i class='icon fa fa-warning'></i><label>"+title+"</label></h4>";
    tag += "     <label >"+message+"</label> ";
    tag += "</div> ";
	
    $("#divAlertArea").append(tag);
	
}

function fnAlertClose(divElement) {
	
	$("#" + divElement).attr("style", "visibility: hidden");
}

function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value)
			+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}

function getCookie(c_name) {
	var i, x, y, ARRcookies = document.cookie.split(";");
	for (i = 0; i < ARRcookies.length; i++) {
		x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
		y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
		//alert("x : " + x + ", y : " + y);
		x = x.replace(/^\s+|\s+$/g, "");
		if (x == c_name) {
			return unescape(y);
		}
	}
}
