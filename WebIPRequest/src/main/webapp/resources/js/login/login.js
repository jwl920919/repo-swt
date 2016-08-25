$(document).ready(function() {
	//쿠키에 언어 설정이 되어 있지 않다면 기본적으로 한국어로 radio 버튼 체크
	if (getCookie("Language") != "undefined") {
		$('input:radio[name=rLanguage]:input[value='+getCookie("Language")+']').attr("checked", true);
		
		var vUserLanguae = $(":input:radio[name='rLanguage']:checked").val();
		setCookie("Language", vUserLanguae, 365);
	}

	//다국어 설정 radio 버튼 이벤트 핸들러
	$(":input:radio[name='rLanguage']").bind("change", function() {
		var vUserLanguae = $(":input:radio[name='rLanguage']:checked").val();		
		setCookie("Language", vUserLanguae, 365);
		history.go(-1);
	});
	
	//서버 Controller에서 넘어온 Message 출력해주기
	if($("#hiddenError").text() != ''){
		systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), $("#hiddenError").text());
		$("#hiddenError").text('');
	}

	//로그인
	$("#bLogin").bind("click", function() {
		var bValidation = false;

		// validation check Start
		var vID = $("#txtID").val();
		var vPW = $("#txtPassword").val();
		if (vID == "") {
			systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("PleaseenteryourID"));
		} else if (vPW == "") {
			systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("Pleaseenterapassword"));
		} else {
			bValidation = true;
		}
		// validation check End

		if (bValidation) {
			var param = {
				id : vID,
				pw : vPW
			};
		} else {
			return bValidation;
		}
	});
	
	//로그인 버튼을 default 버튼으로 이벤트 생성한다.
	$(document).bind('keypress', function(e) {
		if(e.keyCode==13){
            $('#bLogin').trigger('click');
		}
    });
});
