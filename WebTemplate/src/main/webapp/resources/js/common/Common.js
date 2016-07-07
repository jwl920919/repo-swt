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
	try {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value = escape(value)
				+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
		document.cookie = c_name + "=" + c_value;
	} catch (e) {
	 	alert(e.message);
	}	
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

String.format = function() {
	var s = arguments[0];
	for (var i = 0; i < arguments.length - 1; i++) {       
    	 var reg = new RegExp("\\{" + i + "\\}", "gm");             
         s = s.replace(reg, arguments[i + 1]);
	}
	return s;
}

// DateTime format function
/**
*-2011년 09월 11일 오후 03시 45분 42초
*console.log(new Date().format("yyyy년 MM월 dd일 a/p hh시 mm분 ss초")); 
*-2011-09-11
*console.log(new Date().format("yyyy-MM-dd")); 
*-'11 09.11
*console.log(new Date().format("'yy MM.dd")); 
*-2011-09-11 일요일
*console.log(new Date().format("yyyy-MM-dd E")); 
*-현재년도 : 2011
*console.log("현재년도 : " + new Date().format("yyyy"));
**/
Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
    
    var weekName = [getLanguage("sunday"), getLanguage("monday"), getLanguage("tuesday"), getLanguage("wednesday"),
                    getLanguage("thursday"), getLanguage("friday"), getLanguage("saturday")];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? getLanguage("am") : getLanguage("pm");
            default: return $1;
        }
    });
}; 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};