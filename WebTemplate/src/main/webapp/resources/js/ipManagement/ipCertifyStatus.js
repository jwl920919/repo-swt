var table;
$(document).ready(function() {
	$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'YYYY-MM-DD h:mm A'});
	
	$("#layDiv").css("visibility","hidden");

	try
	{
//		var tag = "";
//		var jObj = Object();
//	    jObj.search_key = "";
//		    
//	    $.ajax({
//	        url : 'ipManagement/dhcp_Network_Select',
//	        type : "POST",
//	        data : JSON.stringify(jObj),
//	        dataType : "text",
//	        success : function(data) {
//	            var jsonObj = eval("(" + data + ')');
//		            if (jsonObj.result == true) {
//		            	//console.log("jsonObj.data : " + jsonObj.data);
//		            	
//		            	$.each(jsonObj.data, function (index, obj) {		            		
//		            		if (tag == '') {
//		            			tag += "<option selected class='placeholderSelectOption' value=''>" + getLanguage("chooseasegment") + "</option>";
//			            		tag += "<option value='ALL' class='selectoption_black_color'>" + getLanguage("all") + "</option>";
//		            			fnSelectData('ALL', ''); //첫 로딩 시 첫번재 데이터로 조회
//							}
//		            		tag += "<option value='" + obj.network + "' class='selectoption_black_color'>";
//		                    tag += obj.network;
//		                    tag += "</option>";
//			            });
//	            }
//	        },
//	        complete: function(data) {
//	        	//console.log(tag);
//	        	$('#sbSegment').append(tag);
//	        }
//	    });
	    
	} catch (e) {
		console.log("leaseIPStatus.js $(document).ready Error Log : " + e.message);
	}

//	//세그먼트 Selectbox change 이벤트
//	$('#sbSegment').change(function() {
//	    if ($(this).val() != '') {
//	    	$('#sbSegment').removeClass("selectoption_grey_color").addClass("selectoption_black_color");
//        	fnSelectData($("#sbSegment option:selected").val(), $("#txtSearch").val());
//	     }
//	});
	
	//조회 버튼 클릭 이벤트
	$('#btnSearch').click(function() {		
	    if ($("#sbSegment option:selected").val() != '') {
        	//console.log("btnSearch.click value true : " + $("#sbSegment option:selected").val());
        	//console.log("txtSearch value : " + $("#txtSearch").val());
        	fnSelectData($("#sbSegment option:selected").val(), $("#txtSearch").val());
	    }
	    else {
        	fnSelectData('ALL', $("#txtSearch").val());
		}	    	
	});
});

/**
 * fnSelectData function
**/
fnSelectData = function(value, search) {
	
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
	                    url : 'ipManagement/leaseIPStatus_Data_Select',
	                    "dataType" : "jsonp",
	                    "type" : "POST",
	                    "jsonp" : "callback",
	                    "data" : function(data,type) {
	                    	data.search.value = search;
	                        data.search_key = data.search.value;
	                        data.network = value;
	                        data.timezone = getClientTimeZoneName();
	                    }
	                },	                
				    "columnDefs": [{ className: "essential-td-left", "targets": [ 0,2,3,7,9,10 ] },
				                   { className: "essential-td-left", "targets": [ 11,12,16 ] }],
	                "order" : [ [ 0, 'asc' ] ],
	                "columns" : [	{"data" : "ipaddr"},
									{"data" : "macaddr"},
									{"data" : "host_name"},
									{"data" : "host_os"},
									{"data" : "duid"},
									{"data" : "status"},
									{"data" : "lease_state"},
									{"data" : "obj_types"},
									{"data" : "discover_status"},
									{"data" : "usage"},
									{"data" : "fingerprint"},
									{"data" : "is_never_ends"},
									{"data" : "is_never_start"},
									{"data" : "lease_start_time"},
									{"data" : "lease_end_time"},
									{"data" : "last_discovered"},
									{"data" : "user_description"}]
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
		console.log("leaseIPStatus.js fnSelectData Error Log : " + e.message);
	}
}

/**
 * tr 이벤트 핸들러
**/
function trClickEvent (obj){
	return false;
}
