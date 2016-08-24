var table;
$(document).ready(function() {

	modalClose("modal");

	var prevday = 1;
	var currentdate = new Date();
	var previewdate = new Date(currentdate.getFullYear(),currentdate.getMonth(),currentdate.getDate()-prevday, currentdate.getHours(), currentdate.getMinutes(), currentdate.getSeconds());
	$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 1, format: 'YYYY-MM-DD h:mm A'});
	$('#reservationtime').data('daterangepicker').setStartDate(previewdate);
	$('#reservationtime').data('daterangepicker').setEndDate(currentdate);

	//조회 버튼 클릭 이벤트
	$('#btnSearch').click(function() {
		var startDate = new Date($('#reservationtime').data('daterangepicker').startDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");
		var endDate = new Date($('#reservationtime').data('daterangepicker').endDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");		
	    if ($("#sbCerifyStatus option:selected").val() != '') {
        	fnSelectData(startDate, endDate, $("#sbCerifyStatus option:selected").val(), $("#txtSearch").val());
	    }
	});
	
	//조회버튼을 default 버튼으로 이벤트 생성한다.
	$(document).bind('keypress', function(e) {
		if(e.keyCode==13){
			$(document).focus();
            $('#btnSearch').trigger('click');
		}
    });
	
	/**
	 * fnSelectData function
	**/
	fnSelectData = function(statrdatetime, enddatetime, status, searchText) {
		try
		{	    
			table = $('#datatable').DataTable(
		            {
		            	"bJQueryUI": true,	
		                "destroy" : true,
		                "paging" : true,
		                "searching" : false,
		                "lengthChange" : true,
		                "ordering" : true,
		                "info" : false,
		                "bAutoWidth": false,
		                "processing" : true,
		                "serverSide" : true,
		                "sScrollX": "100%",
		                "sScrollXInner": "3000",
		                "bScrollCollapse": true,
		                "ajax" : {
		                    url : 'ipManagement/ipCertifyStatus_Data_Select',
		                    "dataType" : "jsonp",
		                    "type" : "POST",
		                    "jsonp" : "callback",
		                    "data" : function(data,type) {
		                    	data.search.value = searchText;
		                        data.search_key = data.search.value;
		                        data.startTime = statrdatetime;
		                        data.endTime = enddatetime;
		                        data.settlementstatus = status == "ALL" ? -1 : status;
		                        data.timezone = getClientTimeZoneName();
		                    }
		                },	                
					    "columnDefs": [{ className: "essential-td-left", "targets": [ 8,11,15,18 ] },
					               	   { className: "essential-td-display_none", "targets": [ 0,3,9,13,19,21 ] },],
		                "order" : [ [ 0, 'asc' ] ],
		                "columns" : [	{"data" : "settlement_status"},			//0
		                             	{"data" : "settlement_status_text"},	//1
		                             	{"data" : "user_id"},					//2
										{"data" : "user_site_id"},				//3
										{"data" : "site_name"},					//4
										{"data" : "user_name"},					//5
										{"data" : "user_phone_num"},			//6
										{"data" : "apply_static_ip_type"},		//7
										{"data" : "apply_static_ipaddr"},		//8
										{"data" : "apply_static_ip_num"},		//9
										{"data" : "apply_use_time"},			//10
										{"data" : "apply_description"},			//11
										{"data" : "apply_time"},				//12
										{"data" : "settlement_chief_id"},		//13
										{"data" : "settlement_chief_name"},		//14
										{"data" : "settlement_description"},	//15
										{"data" : "settlement_time"},			//16
										{"data" : "issuance_ip_type"},			//17
										{"data" : "issuance_ipaddr"},			//18
										{"data" : "issuance_ip_num"},			//19
										{"data" : "issuance_use_time"},			//20
										{"data" : "seq"}],		//21	
		                "createdRow": function( row, data, dataIndex ) {
		                	//alert($(row).children(0).html());
		                	//Datatable TR Create Event
		    			    if ( $(row).children(0).html() == "0" ) {
		    			        $(row).addClass( 'essential-tr-cursor-pointer' );
		    			    }
		    			  }
						});
			
			//검색, 엔트리 위치 정렬
			$(function() {
			    var d_wrap = $('#datatable_wrapper .row:first');
			    var d_length = $('#datatable_wrapper .row:first .col-sm-6:eq(0)');
			    var d_filter = $('#datatable_wrapper .row:first .col-sm-6:eq(1)');
			    d_length.append(d_filter);
			    d_wrap.prepend(d_filter);
			});
		} catch (e) {
			console.log("ipCertifyStatus.js fnSelectData Error Log : " + e.message);
		}
	};

	//초기 데이터 조회
	fnSelectData(previewdate.format("yyyy-MM-dd HH:mm:ss"), currentdate.format("yyyy-MM-dd HH:mm:ss"), $("#sbCerifyStatus option:selected").val(), $("#txtSearch").val());

});
/**
 * tr 이벤트 핸들러
**/
var initParam;
function trClickEvent (obj){
	initParam = obj;
	var settlement_status = $(obj).children(':eq(0)').text();
	if (settlement_status == 0) {
		//결제팝업 오픈
		modalShow("modal");
	}
	else{
		initParam = "";
		return false;
	}
}
/**
 * 추가,수정 모달 팝업 Show 이벤트 핸들러
**/
fnShowEvent = function(){
	try {
		//추가 팝업 초기화
		$("#txtUserName").text($(initParam).children(':eq(5)').text() + " (" + $(initParam).children(':eq(2)').text() + ")");
		if ($(initParam).children(':eq(8)').text() == "") {
			//자동IP요청
			$("#txtApplyIP").text(getLanguage("autoIP"));
			$("#txtIssuanceIP").val("");
		}
		else {
			//고정IP요청
			$("#txtApplyIP").text(getLanguage("staticIP") + " (" + $(initParam).children(':eq(8)').text() + ")");
			$("#txtIssuanceIP").val($(initParam).children(':eq(8)').text());
		}
		
		$("#txtApplyTime").text($(initParam).children(':eq(10)').text());

		var datePeriod = $(initParam).children(':eq(10)').text().split(" ~ ");
		var startdate = new Date(datePeriod[0]);
		var enddate = new Date(datePeriod[1]);
		$('#issuanceUseTime').daterangepicker({timePicker: false, format: 'YYYY-MM-DD'});
		$('#issuanceUseTime').data('daterangepicker').setStartDate(startdate);
		$('#issuanceUseTime').data('daterangepicker').setEndDate(enddate);
		
//		$('input:radio[name=rSettlement]:input[value=true]').prop("checked", true);		
//		$("#inputFilter").val("");
//		$("#selectTime").val(60);
//		$("#txtareaDesc").val("");
		
		$('#btnSave').unbind( "click" );		
		$('#btnSave').click(function() {
			//추가 기능 수행
			var param = Object();
			param.timezone = getClientTimeZoneName();
			param.seq = $(initParam).children(':eq(21)').text();
		    if ($(":input:radio[name=rSettlement]:checked").val() == "true"){
		    	param.settlement_status = 1;
		    }
		    else {
		    	param.settlement_status = 2;
			}
		    param.settlement_description = $("#txtareaDesc").val();

			param.issuance_ip_type = $(initParam).children(':eq(7)').text();
			param.issuance_ipaddr = $("#txtIssuanceIP").val();
			if ($("#txtIssuanceIP").val() == "") {
				param.issuance_ip_num = 0;
			}
			else {
				param.issuance_ip_num = ipToNumber($("#txtIssuanceIP").val()).toString();
			}
			param.issuance_start_time = new Date($('#issuanceUseTime').data('daterangepicker').startDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");
			param.issuance_end_time = new Date($('#issuanceUseTime').data('daterangepicker').endDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");	
				
		    $.ajax({
		        url : 'ipManagement/ipCertifyStatus_Data_Update',
		        type : "POST",
		        data : JSON.stringify(param),
		        dataType : "text",
		        success : function(data) {
		            var jsonObj = eval("(" + data + ')');
		            if (jsonObj.result == true) {
		            	table.ajax.reload(); //데이터 제 조회
		            	systemAlertNotify("divAlertArea", "alert-warnning", getLanguage("add"), getLanguage("saved"));
		            }
		        },
		        complete: function(data) {
		        }
		    });
			modalClose("modal");
		});				
	} catch (e) {
		e.message
	}
}
