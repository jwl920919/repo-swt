/** Load and parse .properties files */
function systemAlert(message) {
    systemAlert("layDiv", message)
}

/**
 * divElement : alert을 가지고 있는 div ID type : alert-warning, alert-danger,
 * alert-info, alert-success title : 경고, 주의 등의 팝업 타이틀 message : 팝업 문구
 */
function systemAlert(divElement, type, title, message) {
    $("#layDiv").attr("style", "visibility: visible");
    $("#alertTitle").text(title);
    $("#alertMessage").text(message);

    // .alert-warning
    // .alert-danger
    // .alert-info
    // .alert-success
    var tag = "";
    tag += "<div id='custom-alert' class='alert " + type
            + " alert-dismissible fade in' role='alert'>";
    tag += "     <button onclick='fnAlertClose(\"layDiv\")' type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
    tag += "     <h4><i class='icon fa fa-warning'></i><label>" + title
            + "</label></h4>";
    tag += "     <label >" + message + "</label> ";
    tag += "</div> ";
    $("#" + divElement).html('');
    $("#" + divElement).append(tag);

}

/**
 * date : 2016-07-21 creator : 이재원 divElement : alert을 가지고 있는 div ID boxColor :
 * alert-info, alert-success title : 경고, 주의 등의 팝업 타이틀 message : 팝업 문구
 * confirmButtonValue : 버튼 메세지 buttonColor : ex1)#fff ex2)rgba(60, 141, 188,
 * 0.68) fnName : button클릭시 실행 될 function 이름 message : 팝업 문구 confirm 버튼 추가
 */

/*
 * alert의 위치를 중앙에 띄우기 위해 사용
 */
var windowHeight, scrollTop;
function getWindowPoint() {
    windowHeight = $(window).height();
    scrollTop = $(document).scrollTop();
};

function systemAlert(divElement, type, title, message, confirmButtonValue, buttonColor, fnName) {
    getWindowPoint();
    $("#layDiv").attr("style", "visibility: visible");
    $("#alertTitle").text(title);
    $("#alertMessage").text(message);

    // .alert-warning
    // .alert-danger
    // .alert-info
    // .alert-success
    var tag = "";
    tag += "<div id='custom-alert' class='alert " + type
            + " alert-dismissible fade in' role='alert' >";
    tag += "     <button onclick='fnAlertClose(\"layDiv\")' type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
    tag += "     <h4><i class='icon fa fa-warning'></i><label>" + title
            + "</label></h4>";
    tag += "     <label >" + message + "</label> ";
    tag += "     <div style='width:100%;'><input onclick='alertButtonEvent()' class='btn' type='button' value='" + confirmButtonValue
            + "' style='position: relative;left: 282px;background:"
            + buttonColor + ";outline: none;color:#fff' /></div><script>function alertButtonEvent(){;" + fnName
            + "(); fnAlertClose(\"layDiv\");}</script>";
    tag += "</div> ";
    $("#" + divElement).html('');
    $("#" + divElement).append(tag);
    getWindowPoint();
    var alertPositionHeight = (windowHeight / 2 + scrollTop - 60) + 'px';
    $('#custom-alert').css('top', alertPositionHeight);
}

/**
 * 스크롤 변경 이벤트
 */
$(window).scroll(function() {
    //alert 창 위치 지정
    if ($('#layDiv').css('visibility') == 'visible') {
        getWindowPoint();
        var alertPositionHeight = (windowHeight / 2 + scrollTop - 60) + 'px';
        $('#custom-alert').css('top', alertPositionHeight);
    }
});

/**
 * systemAlert 팝업 close 핸들러
 */
function fnAlertClose(divElement) {

    $("#" + divElement).attr("style", "visibility: hidden");
}

/**
 * 쿠키값 설정
 */
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

/**
 * 쿠키값 조회
 */
function getCookie(c_name) {
    var i, x, y, ARRcookies = document.cookie.split(";");
    for (i = 0; i < ARRcookies.length; i++) {
        x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
        y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
        // alert("x : " + x + ", y : " + y);
        x = x.replace(/^\s+|\s+$/g, "");
        if (x == c_name) {
            return unescape(y);
        }
    }
}

/**
 * String Format string.String.Format("aaa {0}-{1}", 값1, 값2);
 */
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
 * http://stove99.tistory.com/46 
 * -2011년 09월 11일 오후 03시 45분 42초 console.log(new Date().format("yyyy년 MM월 dd일 a/p hh시 mm분 ss초"));
 * -2011-09-11 console.log(new Date().format("yyyy-MM-dd"));
 * -'11 09.11 console.log(new Date().format("yy.MM.dd"));
 * -2011-09-11 일요일 console.log(new Date().format("yyyy-MM-dd E"));
 * -현재년도 : 2011 console.log("현재년도 : " + new Date().format("yyyy"));
 */
Date.prototype.format = function(f) {
    if (!this.valueOf())
        return " ";

    var weekName = [ getLanguage("sunday"), getLanguage("monday"),
            getLanguage("tuesday"), getLanguage("wednesday"),
            getLanguage("thursday"), getLanguage("friday"),
            getLanguage("saturday") ];
    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
        case "yyyy":
            return d.getFullYear();
        case "yy":
            return (d.getFullYear() % 1000).zf(2);
        case "MM":
            return (d.getMonth() + 1).zf(2);
        case "dd":
            return d.getDate().zf(2);
        case "E":
            return weekName[d.getDay()];
        case "HH":
            return d.getHours().zf(2);
        case "hh":
            return ((h = d.getHours() % 12) ? h : 12).zf(2);
        case "mm":
            return d.getMinutes().zf(2);
        case "ss":
            return d.getSeconds().zf(2);
        case "a/p":
            return d.getHours() < 12 ? getLanguage("am") : getLanguage("pm");
        default:
            return $1;
        }
    });
};
String.prototype.string = function(len) {
    var s = '', i = 0;
    while (i++ < len) {
        s += this;
    }
    return s;
};
String.prototype.zf = function(len) {
    return "0".string(len - this.length) + this;
};
Number.prototype.zf = function(len) {
    return this.toString().zf(len);
};

/**
 * Client Timezone Name을 반환한다.
 */
function getClientTimeZoneName() {
    var timezone = jstz.determine();
    return timezone.name();
}

/**
 * IPv4 유효성 검사
 */
function checkIPv4(strIp) {
    var arrIp = strIp.split(".");

    if (arrIp.length != 4)
        return false;

    for (var i = 0; i < arrIp.length; ++i) {
        if (arrIp[i] < 0 || arrIp[i] > 255)
            return false;
    }
    return true;
}

/**
 * IPv4를 number로 리턴
**/
function ipToNumber(ip) {
	var d = ip.split('.');
	return ((((((+d[0])*256)+(+d[1]))*256)+(+d[2]))*256)+(+d[3]);
}

/**
 * number를 IPv4로 리턴
**/
function numberToIp(number) {		
	var ip=num%256;
	for (var i=1;i<=3;i++)
	{
		number=Math.floor(num/256);
		ip=number%256+'.'+ip;
	}
	return ip; // As string
}


/**
 * Javascript Sleep function
 */
function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds) {
            break;
        }
    }
}