$(document).ready(function() {	
	$("#divVersion").append("<b>Version</b> 1.0.0");	
	$("#contentTitle").text(getLanguage("askIP"));

	//사이트정보 조회
	fnSiteInfoSearch();
	$("#divStaticIP").hide();
   	$(":input:radio[name='radioIP']").bind("change", function() {
   		var vUserLanguae = $(":input:radio[name='radioIP']:checked").val();

        if($(this).val() == "Auto") {
        	$("#divStaticIP").hide();
        }
        else {
        	$("#divStaticIP").show();
        }
    });
   	
    //Money Euro
    $("[data-mask]").inputmask();
    
    //Flat red color scheme for iCheck
    //체크박스나 라디오버튼 스타일을 변경하는 이벤트 이나 이걸 적용하면 위와 같은 "change"이벤트 바인딩이 안됨
    
    
//     $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
//       checkboxClass: 'icheckbox_flat-purple',
//       radioClass: 'iradio_flat-purple'
//     });
    

   	$(":input:radio[name='radioIPType']").bind("change", function() {
//    		var vUserLanguae = $(":input:radio[name='radioIP']:checked").val();
        if($(this).val() == "ipv4") {
        	$("#txtip").attr("data-inputmask", "'alias': 'ipv4'");
        }
        else {
        	$("#txtip").attr("data-inputmask", "'alias': 'ipv6'");
        }

        $("[data-mask]").inputmask();
    });
    
	var prevday = 7;
	var currentdate = new Date();
	var nextdate = new Date(currentdate.getFullYear(),currentdate.getMonth(),currentdate.getDate()+prevday, currentdate.getHours(), currentdate.getMinutes(), currentdate.getSeconds());
	$('#asktime').daterangepicker({timePicker: true, timePickerIncrement: 1, format: 'YYYY-MM-DD h:mm A'});
	$('#asktime').data('daterangepicker').setStartDate(currentdate);
	$('#asktime').data('daterangepicker').setEndDate(nextdate);


	
	//조회 버튼 클릭 이벤트
	$('#btnAsk').click(function() {
		
	    //varidation
	    if ($(":input:radio[name='radioIP']:checked").val() == "Static") {
	    	if (($("#txtip").val().replace(/_/gi, '').replace(".", '').replace(".", '').replace(".", '')) == "") {
		    	systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("entertheip"));
			    return false;
	    	}
	    }
	    if ($("#txtaskDesc").val() == "") {
	    	systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("entertheaskedinformation"));
		    return false;
		}
	    //varidatoin
	    
	    var vIPType = $(":input:radio[name='radioIPType']:checked").val();
	    var vIPStatic = $(":input:radio[name='radioIP']:checked").val();
	    var vIP = $("#txtip").val().replace("_", "");
		var vStartDate = new Date($('#asktime').data('daterangepicker').startDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");
		var vEndDate = new Date($('#asktime').data('daterangepicker').endDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");
		var vDesc = $("#txtaskDesc").val();
		
		//IP 신청 데이터 저장
	    fnAskIPSave(vIPType, vIPStatic, vIP, vStartDate, vEndDate, vDesc);
	});
	
	//조회버튼을 default 버튼으로 이벤트 생성한다.
	$(document).bind('keypress', function(e) {
		if(e.keyCode==13){
			$(document).focus();
            $('#btnAsk').trigger('click');
		}
    });
});

//Site Info 데이터 조회
fnSiteInfoSearch = function(){
	
	var tag = "";
    $.ajax({
        url : 'select_site_info',
        type : "POST",
        data : null,
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            console.log(jsonObj);
	            if (jsonObj.result == true) {
            	$.each(jsonObj.resultValue, function (index, obj) {   
            		if (obj.site_id == siteid) {
            			tag += "<option value=" + obj.site_id + " selected>" + obj.site_name + "</option>";
					}
            		else {
            			tag += "<option value=" + obj.site_id + ">" + obj.site_name + "</option>";
            		}
        		});
//            	if (siteMaster == 'f') {
//            		$('#selectSite').attr("disabled","true");
//				}
            	$('#selectSite').html(tag);
            }
        },
        complete: function(data) {
        }
    });
}

//IP 신청 데이터 저장
function fnAskIPSave(vIPType, vIPStatic, vIP, vStartDate, vEndDate, vDesc){

	var param = Object();
	param.time_zone = getClientTimeZoneName();
	param.apply_site_id = $("#selectSite").val();
	param.apply_static_ip_type = vIPType;
	
	if (vIPStatic == "Auto") {
		param.apply_static_ipaddr = "";
		param.apply_static_ip_num = "0";
	}
	else {
		param.apply_static_ipaddr = vIP;
		param.apply_static_ip_num = ipToNumber(vIP).toString();
	}
	
	param.apply_start_time = vStartDate;
	param.apply_end_time = vEndDate;
	param.apply_description = vDesc;
	
	var tag = "";
    $.ajax({
        url : 'insert_USER_APPLY_IP_INFO',
        type : "POST",
        data : JSON.stringify(param),
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            console.log(jsonObj);
	            if (jsonObj.result == true) {
	            	systemAlertConfirm("divAlertArea", "alert-info", getLanguage("requestApproval"),
	            			getLanguage("ithasapprovedtherequest") + "\n" + getLanguage("wouldyouliketochecktherequeststatus"),
	            			getLanguage("check"), "rgba(60, 141, 188, 0.68)", 'IPRequestListOpen()');
	            }
	            else{
	            	systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("anerrorhasoccurredintheapprovalreques"));
	            }
        }
    });
}

function IPRequestListOpen() {
alert("IPRequestListOpen");
}