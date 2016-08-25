var table;
$(document).ready(function() {	
	$("#divVersion").append("<b>Version</b> 1.0.0");	
	$("#contentTitle").text(getLanguage("askIPStatus"));
	
	
	/**
	 * data Select
	**/
	try
	{	    
		table = $('#datatable').DataTable(
	            {
	            	"bJQueryUI": true,	
	                "destroy" : true,
	                "paging" : true,
	                "searching" : true,
	                "lengthChange" : true,
	                "ordering" : true,
	                "info" : false,
	                "bAutoWidth": false,
	                "processing" : true,
	                "serverSide" : true,
	                "sScrollX": "100%",
	                "sScrollXInner": "2500",
	                "bScrollCollapse": true,
	                "ajax" : {
	                    url : '/select_USER_APPLY_IP_INFO',
	                    "dataType" : "jsonp",
	                    "type" : "POST",
	                    "jsonp" : "callback",
	                    "data" : function(data,type) {
	                        data.search_key = data.search.value;
	                        data.timezone = getClientTimeZoneName();
	                    }
	                },	                
				    "columnDefs": [{ className: "essential-td-left", "targets": [ 8,12,17 ] },
				                   { className: "essential-td-right", "targets": [ ] },
				               	   { className: "essential-td-display_none", "targets": [ 0,1,2,5,7,9,15,16,17,18,19,21,22,23 ] },],
	                "order" : [ [ 0, 'asc' ] ],
	                "columns" : [	{"data" : "seq"},					//-0
	                             	{"data" : "user_site_id"},			//-1
	                             	{"data" : "user_id"},				//-2
	                             	{"data" : "user_name"},				//3
	                             	{"data" : "user_phone_num"},		//4
	                             	{"data" : "apply_site_id"},			//-5
	                             	{"data" : "apply_site_name"},		//6
	                             	{"data" : "apply_static_ip_type"},	//-7
	                             	{"data" : "apply_static_ipaddr"},	//8
	                             	{"data" : "apply_static_ip_num"},	//-9
	                             	{"data" : "apply_start_time"},		//10
	                             	{"data" : "apply_end_time"},		//11
	                             	{"data" : "apply_description"},		//12
	                             	{"data" : "apply_time"},			//13
	                             	{"data" : "settlement_status",
                                        'render' : function(data, type, row) {
                                        	if (data == 0) {
												return getLanguage("requestApproval");
											}
                                        	else if (data == 1) {
												return getLanguage("approval");
											}
                                        	else if (data == 2) {
												return getLanguage("return");
											}
                                        }},		//14
	                             	{"data" : "settlement_chief_id"},	//-15
	                             	{"data" : "settlement_chief_name"},	//-16
	                             	{"data" : "settlement_description"},//-17
	                             	{"data" : "settlement_time"},		//-18
	                             	{"data" : "issuance_ip_type"},		//-19
	                             	{"data" : "issuance_ipaddr"},		//20
	                             	{"data" : "issuance_ip_num"},		//-21
	                             	{"data" : "issuance_start_time"},	//22
	                             	{"data" : "allcount"},              //-24
	                             	{"data" : "issuance_end_time"},		//23
                                    {"data" : "button",
                                        'searchable' : false,
                                        'orderable' : false,
                                        'render' : function(data, type, row) {
                                                return "<div class='tools'>"+
                                                " <i class='fa fa-trash-o essential-cursor-pointer' onclick=\"fnDelete('" + row.seq + "');\" data-toggle='tooltip' title='" + getLanguage("delete") + "' ></i> </div>";                                        }
                                    }],	            //25
	                "createdRow": function( row, data, dataIndex ) {
//	                	alert("createdRow : " + $(row).children(0).html()); 
	                	//Datatable TR Create Event
//		    			    if ( $(row).children(0).html() == "0" ) {
//		    			        $(row).addClass( 'essential-tr-cursor-pointer' );
//		    			    }
	    			},
	    			"drawCallback": function( settings ) {
	    				var api = this.api();
	    				// Output the data for the visible rows to the browser's console
	    				//console.log( api.rows( {page:'current'} ).data().length );
//	    				if (api.rows( {page:'current'} ).data().length > 0) {
//	    					$('#add-row').css("visibility","collapse")
//						}
//	    				else {
//	    					$('#add-row').css("visibility","visible")
//						}
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
		console.log("blackListSetting.js document.ready Error Log : " + e.message);
	}
});

var initParam;
fnDelete = function(obj){
	initParam = obj;	
	//삭제 여부를 묻고 fnDeleteEvent 함수에서 처리
	systemAlertConfirm("divAlertArea", "alert-warning", getLanguage("delete"), getLanguage("areyousureyouwanttodelete"), getLanguage("delete"), "#ce891c", 'fnDeleteEvent');
}
/**
 * 삭제 이벤트 핸들러
**/
fnDeleteEvent = function(){
	var param = Object();
	param.seq = initParam;
	
	try {		
	    $.ajax({
	        url : '/delete_USER_APPLY_IP_INFO',
	        type : "POST",
	        data : JSON.stringify(param),
	        dataType : "text",
	        success : function(data) {
	            var jsonObj = eval("(" + data + ')');
	            if (jsonObj.result == true) {
	            	table.ajax.reload(); //데이터 제 조회
	            	systemAlertNotify("divAlertArea", "alert-warning", getLanguage("delete"), getLanguage("Removed"));
	            }
	        },
	        complete: function(data) {
	        }
	    });	
	} catch (e) {
		console.log(e.message);
	}
}