//$(document).ready(function() {
//
//	// Javascript에서 Language갑 가져오기 예제 1
//	// var aTitle = '<%=LanguageHelper.GetLanguage("Title")%> ';
//	// alert(aTitle);
//
//	// Javascript에서 Language갑 가져오기 예제 2
//	// alert(getLanguage("Title"));
//});
changeframe = function(url, acticeid, masterKey, subKey) {

	try {
		if (url != "#") {
			// alert(url);
			// alert(acticeid);

			console.log("url:" + url + ", acticeid :" + acticeid);
			$("#content_frame").load(url);

			var menu = $("[id^='menu_']");
			if (acticeid != '') {
				var menu = $("[id^='menu_']");
				if (acticeid == 'M01') {
					console.log("log - acticeid == 'M01'");
					// 데시보드 메뉴가 선택 되었을 경우 모든 active를 지워준다
					for (var i = 0; i < menu.length; i++) {
						$(menu[i]).attr('class', 'treeview');
					}
					$("#menu_" + acticeid).attr('class', 'treeview active');

					$("#contentHeaderDepth1li").attr('style', 'display: none;');
					$("#contentHeaderDepth2li").attr('style', 'display: none;');
					
					// 데시보드가 아닌 다른 메뉴의 ul테그 닫기
					var vUlMenu = $("[name='ulMenu']");
					for (var i = 0; i < vUlMenu.length; i++) {
						$(vUlMenu[i]).attr('class', 'treeview-menu');
						$(vUlMenu[i]).attr('style', 'display: none;');
					}
				} else {
					console.log("log - acticeid == ''");
					// sub메뉴 선택 일 경우 해당 메뉴를 active시켜주고 다른 sub메뉴는 모두 지워준다.
					var menusub = $("[id^='menu_S']");
					for (var i = 0; i < menusub.length; i++) {
						if (menusub[i].id == 'menu_' + acticeid) {
							$(menusub[i]).attr('class', 'active');
						} else {
							$(menusub[i]).attr('class', '');
						}

						$("#contentHeaderDepth1li").attr('style', 'display: display;');
						$("#contentHeaderDepth2li").attr('style', 'display: display;');
						$("#contentHeaderDepth1").text(getLanguage(masterKey));
						$("#contentHeaderDepth2").text(getLanguage(subKey));
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
