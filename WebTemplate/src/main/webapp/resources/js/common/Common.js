/** Load and parse .properties files */
function systemAlert(message) {
    systemAlert("layDiv", message)
}

/**
 * divElement : alert을 가지고 있는 div ID type : alert-warning, alert-danger,
 * alert-info, alert-success title : 경고, 주의 등의 팝업 타이틀 message : 팝업 문구
 */
function systemAlertNotify(divElement, type, title, message) {
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
            + " alert-dismissible fade in' role='alert'>";
    tag += "     <button onclick='fnAlertClose(\"layDiv\")' type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
    tag += "     <h4><i class='icon fa fa-warning'></i><label>" + title
            + "</label></h4>";
    tag += "     <label >" + message + "</label> ";
    tag += "</div> ";
    $("#" + divElement).html('');
    $("#" + divElement).append(tag);

}

/*
 * alert의 위치를 중앙에 띄우기 위해 사용
 */
var windowHeight, scrollTop;
function getWindowPoint() {
    windowHeight = $(window).height();
    scrollTop = $(document).scrollTop();
};

/**
 * date : 2016-07-21 creator : 이재원 divElement : alert을 가지고 있는 div ID boxColor :
 * alert-info, alert-success title : 경고, 주의 등의 팝업 타이틀 message : 팝업 문구
 * confirmButtonValue : 버튼 메세지 buttonColor : ex1)#fff ex2)rgba(60, 141, 188,
 * 0.68) fnName : button클릭시 실행 될 function 이름 (function이 없을시 ''로 공백문자열을 parameter값으로 주면됨)
 * message : 팝업 문구 confirm 버튼 추가
 */
function systemAlertConfirm(divElement, type, title, message, confirmButtonValue, buttonColor, fnName) {
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
            + buttonColor + ";outline: none;color:#fff' /></div><script>function alertButtonEvent(){" + (fnName==''?fnName:(fnName+'();'))
            + " fnAlertClose(\"layDiv\");}</script>";
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

/**
 * stringToBoolean
 */
stringToBoolean = function(string){
    switch(string.toLowerCase().trim()){
        case "true": case "yes": case "1": return true;
        case "false": case "no": case "0": case null: return false;
        default: return Boolean(string);
    }
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

    return f.replace(/(yyyy|yy|MM|dd|E|hh|h|mm|ss|a\/p)/gi, function($1) {
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
        case "h":
            return ((h = d.getHours() % 12) ? h : 12).zf(1);
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
	var ip=number%256;
	for (var i=1;i<=3;i++)
	{
		number=Math.floor(number/256);
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

function Format_comma(val){
	var formatValue="";
	
	try {
		//console.log("Format_Input : " + val+"");
		if (val != "0") {
			if (val >= 1000000) {
				console.log("val :" + val);
				return fnConvertNumberToUnitValue(val);
			}
			var newValue = val+""; //숫자를 문자열로 변환
			var len = newValue.length;  
			var ch="";
			var j=1;
		
			// 콤마제거  
			newValue = newValue.replace(/\,/gi, ' ');
		
			// comma제거된 문자열 길이
			len = newValue.length;
		
			for(i=len ; i>0 ; i--){
				ch = newValue.substring(i-1,i);
				formatValue = ch + formatValue;
				if ((j%3) == 0 && i>1 ){
					formatValue=","+formatValue;
				}
				j++
			}
		}
		else {
			formatValue = val;
		}
	} catch (e) {
		console.log("Format_comma Error : " + e.message);
	}

	//console.log("Format_comma : " + formatValue);
	return formatValue;
}

/**
 * bps 단위 변경
 */
function fncConvertUnitValue(piValue, psUnit) {
    var return_value = "";

    var vdValue = piValue;
    var vdUnitValue = 1000000.0;
    var viDivCount = 0;
    var vsUnit = "";

    if (psUnit == "bps") {
        vdUnitValue = 1000.0;
    }
    else if (psUnit == "pps")
    {
        vdUnitValue = 1000.0;        
    }
    else if (psUnit == "byte") {
        vdUnitValue = 1024.0;
    }


    while (vdValue / vdUnitValue >= 1) {
        vdValue = vdValue / vdUnitValue;
        viDivCount++;
    }

    switch (viDivCount) {
        case 0: break;
        case 1: vsUnit = "K"; break;
        case 2: vsUnit = "M"; break;
        case 3: vsUnit = "G"; break;
        case 4: vsUnit = "T"; break;
        case 5: vsUnit = "P"; break;
        case 6: vsUnit = "E"; break;
        case 7: vsUnit = "Z"; break;
        case 8: vsUnit = "Y"; break;
        default: vsUnit = "..."; break;
            //case 0: vsUnit = psUnit; break;
            //case 1: vsUnit = "K" + psUnit; break;
            //case 2: vsUnit = "M" + psUnit; break;
            //case 3: vsUnit = "G" + psUnit; break;
            //case 4: vsUnit = "T" + psUnit; break;
            //case 5: vsUnit = "P" + psUnit; break;
            //case 6: vsUnit = "E" + psUnit; break;
            //case 7: vsUnit = "Z" + psUnit; break;
            //case 8: vsUnit = "Y" + psUnit; break;
            //default: vsUnit = "..."; break;
    }

    return_value = Math.abs(vdValue).toFixed(1) + " " + vsUnit;

    return return_value;
}

/**
 * 숫자 단위 변경
 */
function fnConvertNumberToUnitValue(value) {
//	var won  = (pWon+"").replace(/,/g, "");
//	//var arrWon  = ["원", "만", "억", "조", "경", "해", "자", "양", "구", "간", "정"];
//	var arrWon  = ["", "만", "억", "조", "경", "해", "자", "양", "구", "간", "정"];
//	var changeWon = "";
//	var pattern = /(-?[0-9]+)([0-9]{4})/;
//	while(pattern.test(won)) {                   
//		won = won.replace(pattern,"$1,$2");
//	}
//	var arrCnt = won.split(",").length-1;
//	for(var ii=0; ii<won.split(",").length; ii++) {
//		if(arrWon[arrCnt] == undefined) {
//			alert("값의 수가 너무 큽니다.");
//			break;
//		}
//		var tmpwon=0;
//		for(i=0;i<won.split(",")[ii].length;i++){
//		var num1 = won.split(",")[ii].substring(i,i+1);
//		tmpwon = tmpwon+Number(num1);
//		}
//		if(tmpwon > 0){
//			changeWon += won.split(",")[ii]+arrWon[arrCnt]; //55억0000만0000원 이런 형태 방지 0000 다 짤라 버린다
//		}
//		arrCnt--;
//	}
//	return changeWon;
 
	
    var unit = ["", "만", "억", "조", "경", "해", "자", "양", "구", "간", "정"];
    
    
    //value = value.toFixed(2);

    if (value < 1000)
        return String.format("{0}" + "", value.toFixed(2));
    
    else if (value >= 1000 && value < 10000000)
        return String.format("{0}" + "만", (value / 1000).toFixed(2));

    else if (value >= 10000000 && value < 100000000)
        return String.format("{0}" + "천만", (value / 10000000).toFixed(2));
    
    else if (value >= 100000000 && value < 1000000000)
        return String.format("{0}" + "억", (value / 100000000).toFixed(2));
    
    else if (value >= 1000000000 && value < 10000000000)
        return String.format("{0}" + "십억", (value / 1000000000).toFixed(2));
    
    else if (value >= 10000000000 && value < 100000000000)
        return String.format("{0}" + "백억", (value / 10000000000).toFixed(2));
    
    else if (value >= 100000000000 && value < 1000000000000)
        return String.format("{0}" + "천억", (value / 100000000000).toFixed(2));
    
    else if (value >= 1000000000000 && value < 10000000000000)
        return String.format("{0}" + "조", (value / 1000000000000).toFixed(2));
    
    else if (value >= 10000000000000)
        return String.format("{0}", "●●●");
    
    else
        return String.format("{0}" + "조 단위를 넘었습니다.");
 
//    var return_value = "";
//
//    var vdValue = value;
//    var vdUnitValue = 1000000.0;
//    var viDivCount = 0;
//    var vsUnit = "";
//
//    while (vdValue / vdUnitValue >= 1) {
//        vdValue = vdValue / vdUnitValue;
//        viDivCount++;
//    }
//
//    switch (viDivCount) {
//        case 0: break;
//        case 1: vsUnit = "million 백만"; break;//백만
//        case 2: vsUnit = "ten million 천만"; break;//천만
//        case 3: vsUnit = "hundred million 억"; break;//억
//        case 4: vsUnit = "billion 십억"; break;//십억
//        case 5: vsUnit = "ten billion 백억"; break;//백억
//        case 6: vsUnit = "hundred billion 조"; break;//조
//        case 7: vsUnit = "trillion 십조"; break;//십조
//        case 8: vsUnit = "ten trillion 백조"; break;//백조
//        case 9: vsUnit = "hundred trillion 천조"; break;//천조
//        default: vsUnit = "..."; break;
////        case 1: vsUnit = "hundred 백"; break;//백
////        case 2: vsUnit = "thousand 천"; break;//천
////        case 3: vsUnit = "ten thousand 만"; break;//만
////        case 4: vsUnit = "hundred thousand 십만"; break;//십만
////        case 5: vsUnit = "million 백만"; break;//백만
////        case 6: vsUnit = "ten million 천만"; break;//천만
////        case 7: vsUnit = "hundred million 억"; break;//억
////        case 8: vsUnit = "billion 십억"; break;//십억
////        case 9: vsUnit = "ten billion 백억"; break;//백억
////        case 10: vsUnit = "hundred billion 조"; break;//조
////        case 11: vsUnit = "trillion 십조"; break;//십조
////        case 12: vsUnit = "ten trillion 백조"; break;//백조
////        case 13: vsUnit = "hundred trillion 천조"; break;//천조
////        default: vsUnit = "..."; break;
//    }
//
//    return_value = Math.abs(vdValue).toFixed(0) + " " + vsUnit;
//
//    return return_value;
}
