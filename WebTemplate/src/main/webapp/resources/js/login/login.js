$(document).ready(function() {

	// Javascript에서 Language갑 가져오기 예제 1
	// var aTitle = '<%=LanguageHelper.GetLanguage("Title")%> ';
	// alert(aTitle);

	// Javascript에서 Language갑 가져오기 예제 2
	//alert(getLanguage("Title"));
	

//	$("#layDiv").css("top", Math.max(0, (($(window).height() - $("#layDiv").outerHeight()) / 2) + $(window).scrollTop()) + "px");
//	$("#layDiv").css("left", Math.max(0, (($(window).width() - $("#layDiv").outerWidth()) / 2) + $(window).scrollLeft()) + "px");
//	$("#layDiv").attr("style", "visibility: hidden");
	$("#layDiv").css("visibility","hidden");

	//쿠키에 언어 설정이 되어 있지 않다면 기본적으로 한국어로 radio 버튼 체크
	if (getCookie("Language") != "undefined") {
		$('input:radio[name=rLanguage]:input[value='+getCookie("Language")+']').attr("checked", true);
		
		var vUserLanguae = $(":input:radio[name='rLanguage']:checked").val();
		setCookie("Language", vUserLanguae, 365);
	}

	//다국어 설정 radio 버튼 이벤트 핸들러
	$(":input:radio[name='rLanguage']").bind("change", function() {

//		 alert("설정 전 : " + getCookie("Language"));
//		 alert("설정 값 : " + $(":input:radio[name='rLanguage']:checked").val());
		var vUserLanguae = $(":input:radio[name='rLanguage']:checked").val();		
		setCookie("Language", vUserLanguae, 365);
		history.go(-1);
		//window.reload();
	});

	$("#testbutton").bind("click", function() {
		alert("Click");
	});
	
	//서버 Controller에서 넘어온 Message 출력해주기
	if($("#hiddenError").text() != ''){
		systemAlert("divAlertArea", "alert-warning", getLanguage("warning"), $("#hiddenError").text());
		$("#hiddenError").text('');
	}

	//로그인
	$("#bLogin").bind("click", function() {
		var bValidation = false;

		// validation check Start
		var vID = $("#txtID").val();
		var vPW = $("#txtPassword").val();
		if (vID == "") {
			systemAlert("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("PleaseenteryourID"));
		} else if (vPW == "") {
			systemAlert("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("Pleaseenterapassword"));
		} else {
			bValidation = true;
		}
		// validation check End

		if (bValidation) {
			var param = {
				id : vID,
				pw : vPW
			};

			// $.ajax({
			// type: "POST",
			// url: "/commonGW/getDeviceInfoMultySelectAllList",
			// async: false,
			// data: param,
			// success: function (data) {
			// fnADDDataBind(data, "#LIST_2");
			//
			// //추가 버튼 클릭 시 일괄 체크박스 체크되록하는 기능
			// $("#checkAllModify").prop('checked', true);
			// $("input[name=checkbox_ADD]").prop('checked', true);
			// },
			// error: function (msg) {
			// alert(getMenuData('Communicationfailure') + "\n" +
			// msg.responseText);
			// }
			// });
		} else {
			return bValidation;
		}
	});

});
