var table;
$(document).ready(function() {
	
	$("#layDiv").css("visibility","hidden");

	var currentdate = new Date();
	var previewdate = new Date(currentdate.getFullYear(),currentdate.getMonth(),currentdate.getDate()-1, currentdate.getHours(), currentdate.getMinutes(), currentdate.getSeconds());
	$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 1, format: 'YYYY-MM-DD h:mm A'});
	$('#reservationtime').data('daterangepicker').setStartDate(previewdate);
	$('#reservationtime').data('daterangepicker').setEndDate(currentdate);

//	//세그먼트 Selectbox change 이벤트
//	$('#sbSegment').change(function() {
//	    if ($(this).val() != '') {
//	    	$('#sbSegment').removeClass("selectoption_grey_color").addClass("selectoption_black_color");
//        	fnSelectData($("#sbSegment option:selected").val(), $("#txtSearch").val());
//	     }
//	});
	
	//조회 버튼 클릭 이벤트
	$('#btnSearch').click(function() {
		var startDate = new Date($('#reservationtime').data('daterangepicker').startDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");
		var endDate = new Date($('#reservationtime').data('daterangepicker').endDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");		
	    if ($("#sbCerifyStatus option:selected").val() != '') {
        	fnSelectData(startDate, endDate, $("#sbCerifyStatus option:selected").val(), $("#txtSearch").val());
	    }
	});
	
	/**
	 * fnSelectData function
	**/
	fnSelectData = function(statrdatetime, enddatetime, status, searchText) {
		console.log(statrdatetime, enddatetime, status, searchText);
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
					    "columnDefs": [{ className: "essential-td-left", "targets": [ 0,2,3,7,9,10 ] },
					                   { className: "essential-td-left", "targets": [ 11,12,16 ] }],
		                "order" : [ [ 0, 'asc' ] ],
		                "columns" : [	{"data" : "user_id"},
										{"data" : "user_site_id"},
										{"data" : "site_name"},
										{"data" : "user_name"},
										{"data" : "user_phone_num"},
										{"data" : "apply_static_ip_type"},
										{"data" : "apply_static_ipaddr"},
										{"data" : "apply_static_ip_num"},
										{"data" : "apply_start_time"},
										{"data" : "apply_end_time"},
										{"data" : "apply_description"},
										{"data" : "apply_time"},
										{"data" : "settlement_status"},
										{"data" : "settlement_chief_id"},
										{"data" : "settlement_description"},
										{"data" : "settlement_time"},
										{"data" : "issuance_ip_type"},
										{"data" : "issuance_ipaddr"}]	            
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
function trClickEvent (obj){
	return false;
}
