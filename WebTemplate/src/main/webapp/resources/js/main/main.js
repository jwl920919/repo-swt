//$(document).ready(function() {
//
//	// Javascript에서 Language갑 가져오기 예제 1
//	// var aTitle = '<%=LanguageHelper.GetLanguage("Title")%> ';
//	// alert(aTitle);
//
//	// Javascript에서 Language갑 가져오기 예제 2
//	// alert(getLanguage("Title"));
//});
var currentURL;
changeframe = function(url, acticeid, masterKey, subKey) {

	try {
		if (url != "#") {
			// alert(url);
			// alert(acticeid);
			
			//console.log("url:" + url + ", acticeid :" + acticeid);
			$("#content_frame").load(url);
			currentURL = url;
			
			var menu = $("[id^='menu_']");
			if (acticeid != '') {
				var menu = $("[id^='menu_']");
				if (acticeid == 'M01') {
					//console.log("log - acticeid == 'M01'");
					// 데시보드 메뉴가 선택 되었을 경우 모든 active를 지워준다
					for (var i = 0; i < menu.length; i++) {
						$(menu[i]).attr('class', 'treeview');
					}
					$("#menu_" + acticeid).attr('class', 'treeview active');
					
					//ContentHeader 변경
					$("#contentHeaderDepth1li").attr('style', 'display: display;');
					$("#contentHeaderDepth2li").attr('style', 'display: none;');

					$("#contentHeaderDepth1").text(getLanguage(masterKey));
					$("#contentTitle").text(getLanguage(masterKey));

					// 데시보드가 아닌 다른 메뉴의 ul테그 닫기
					var vUlMenu = $("[name='ulMenu']");
					for (var i = 0; i < vUlMenu.length; i++) {
						$(vUlMenu[i]).attr('class', 'treeview-menu');
						$(vUlMenu[i]).attr('style', 'display: none;');
					}
				} else {
					//console.log("log - acticeid == ''");
					// sub메뉴 선택 일 경우 해당 메뉴를 active시켜주고 다른 sub메뉴는 모두 지워준다.
					var menusub = $("[id^='menu_S']");
					for (var i = 0; i < menusub.length; i++) {
						if (menusub[i].id == 'menu_' + acticeid) {
							$(menusub[i]).attr('class', 'active');
						} else {
							$(menusub[i]).attr('class', '');
						}

						//ContentHeader 변경
						$("#contentHeaderDepth1li").attr('style', 'display: display;');
						$("#contentHeaderDepth2li").attr('style', 'display: display;');
						$("#contentHeaderDepth1").text(getLanguage(masterKey));
						$("#contentHeaderDepth2").text(getLanguage(subKey));
						$("#contentTitle").text(getLanguage(subKey));
					    $("#contentTitleSmall").text("");
					}
					if (acticeid != '') {
						// 데시보드가 아닌 다른 메뉴 선택 시 데시보드 Polling데이터 조회 기능 정지
						AllClearAjaxCall();
					}
				}
			}
		}
	} catch (e) {
		console.log("main.js changeframe() Error Log : " + e.message);
	}
}

contentLoad = function(url){
//	alert("url : " + url);
//	alert("$(#content_frame) : " + $("#content_frame"));
	$("#content_frame").load(url);	
}

/**
 * 메인페이지 상단의 사업장 선택 dropdown-menu 설정
**/
fnGetSiteInfo = function(){
	var str = [];
	
    $.ajax({
        url : 'select_site_info',
        type : "POST",
        data : null,
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
	            if (jsonObj.result == true) {
            	$.each(jsonObj.resultValue, function (index, obj) {   
            		
            		str.push('<li><a href="javascript:void(0);" onclick="fnChangeSiteInfo(' + obj.site_id + ',\'' + obj.site_name + '\',\'' + obj.site_master + '\');">'+
//					str.push('<li><a href="javascript:void(0);" onclick="fnChangeSiteInfo(\"'+ obj.site_id + '\",\"' + obj.site_name + '\",\"' + obj.site_master +'\);">'+
							 '<h3>' + obj.site_name + '	<small>(' + obj.description + ')</small>'+
							 '</h3></a></li>');	

	            });
            	$('#mainPageSiteInfo').html(str.join(''));
            }
        },
        complete: function(data) {
        }
    });
}

/**
 * 메인페이지 상단의 사업장 선택 dropdown-menu의 사이트 선택 클릭 이벤트
 * 세션의 사업장 정보를 변경해준다.
**/
fnChangeSiteInfo = function(siteid, siteName, master){
var param = '';
param += "site_id=" + siteid;
param += "&site_name=" + siteName;
param += "&site_master=" + master;
	
    $.ajax({
        url : 'set_Sesstion',
        type : "POST",
        data : param,
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
            	console.log(jsonObj.resultValue.newSite_name);
            	console.log($('#siteName').text());
        		$('#siteName').text(jsonObj.resultValue.newSite_name); 
            }
        },
        complete: function(data) {
        	$("#content_frame").load(currentURL);
        }
    });
}
