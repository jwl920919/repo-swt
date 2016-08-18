var table;
var popupClass;
var initParam;
$(document).ready(function() {
	
	
	//$("#layDiv").css("visibility","hidden");

	//modalShow("modal");
	modalClose("modal");
	
//	//세그먼트 Selectbox change 이벤트
//	$('#sbSegment').change(function() {
//	    if ($(this).val() != '') {
//	    	$('#sbSegment').removeClass("selectoption_grey_color").addClass("selectoption_black_color");
//        	fnSelectData($("#sbSegment option:selected").val(), $("#txtSearch").val());
//	     }
//	});
	
	//조회 버튼 클릭 이벤트
//	$('#btnSearch').click(function() {
//		var startDate = new Date($('#reservationtime').data('daterangepicker').startDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");
//		var endDate = new Date($('#reservationtime').data('daterangepicker').endDate.toLocaleString()).format("yyyy-MM-dd HH:mm:ss");		
//	    if ($("#sbCerifyStatus option:selected").val() != '') {
//        	fnSelectData(startDate, endDate, $("#sbCerifyStatus option:selected").val(), $("#txtSearch").val());
//	    }
//	});

	return;
	//추가 버튼 클릭 이벤트
	$('#add-row').click(function() {
		fnAdd();
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
	                "ajax" : {
	                    url : 'systemManagement/blackListSetting_Data_Select',
	                    "dataType" : "jsonp",
	                    "type" : "POST",
	                    "jsonp" : "callback",
	                    "data" : function(data,type) {
	                    	//data.search.value = searchText;
	                        data.search_key = data.search.value;
	                    }
	                },	                
				    "columnDefs": [{ className: "essential-td-left", "targets": [ 4,6 ] },
				                   { className: "essential-td-right", "targets": [ 5 ] },
				               	   { className: "essential-td-display_none", "targets": [ 0,1 ] },],
	                "order" : [ [ 2, 'asc' ] ],
	                "columns" : [	{"data" : "blacklist_id"},			//0
	                             	{"data" : "site_id"},				//1
	                             	{"data" : "site_name"},				//2
									{"data" : "blacklist_enable",
										"render":function(data,type,full,meta){	                              
											if(data){
												return getLanguage("enable");
											}else{
												return getLanguage("disable");
											}
										}},	//3
									{"data" : "blacklist_filter_name"},	//4
									{"data" : "blacklist_time_sec",
										"render":function(data,type,full,meta){	                              
											return data + getLanguage("second");
										}},	//5
									{"data" : "description"},
                                    {"data" : "button",
                                        'searchable' : false,
                                        'orderable' : false,
                                        'render' : function(data, type, row) {
                                        	//alert(row.blacklist_time_sec)

                                    		var jObj = [ row.blacklist_id, 
                                    		             row.site_id, 
                                    		             row.site_name,
                                    		             row.blacklist_enable,
                                    		             row.blacklist_filter_name,
                                    		             row.blacklist_time_sec,
                                    		             row.description ];
                                    	    //console.log(jObj);
                                                return "<div class='tools'>"+
//                                                " <i class='fa fa-plus essential-cursor-pointer' onclick='fnAdd();' data-toggle='tooltip' title='" + getLanguage("add") + "'></i>" + 
                                                " <i class='fa fa-edit essential-cursor-pointer' onclick=\"fnModify('" + jObj + "');\" data-toggle='tooltip' title='" + getLanguage("modify") + "'></i>"+
                                                " <i class='fa fa-trash-o essential-cursor-pointer' onclick=\"fnDelete('" + jObj + "');\" data-toggle='tooltip' title='" + getLanguage("delete") + "' ></i> </div>";                                        }
                                    }],			//6	
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
		console.log("ipCertifyStatus.js fnSelectData Error Log : " + e.message);
	}

//
//	//세그먼트 Selectbox change 이벤트
//	$('#btnSave').click(function() {
//		if (popupClass == "add") {
//			console.log("add");
//		}
//		else if (popupClass == "modify") {
//			console.log("modify");
//		}
//		
//		modalClose("modal");
//	});	
});

/**
 * tr 이벤트 핸들러
**/
function trClickEvent (obj){
//	var settlement_status = $(obj).children(':eq(0)').text();
//	if (settlement_status == 0) {
//		alert("승인 팝업 연동 준비 중입니다.");
//	}
//	else{
//		return false;
//	}
}

fnAdd = function(obj){
	popupClass = "add";
	modalShow("modal");
}

fnModify = function(obj){
	popupClass = "modify";
	initParam = obj;
	modalShow("modal");
}

fnDelete = function(obj){
	var res = obj.split(",");
//	console.log("res[0] : " + res[0]);
//	console.log("res[1] : " + res[1]);
//	console.log("res[2] : " + res[2]);
//	console.log("res[3] : " + res[3]);
//	console.log("res[4] : " + res[4]);
//	console.log("res[5] : " + res[5]);
//	console.log("res[6] : " + res[6]);

//	blacklist_id, 
//  site_id, 
//  site_name,
//  blacklist_enable,
//  blacklist_filter_name,
//  blacklist_time_sec,
//  description
}

fnShowEvent = function(){
	try {
		if (popupClass == "add") {
			//추가 팝업 초기화
			$("#selectSite").val(0);
			$('input:radio[name=rEnable]:input[value='+res[3]+']').attr("checked", true);
			$("#inputFilter").val('');
			$("#selectTime").val(60);
			$("#txtareaDesc").val('');
			
			$('#btnSave').unbind( "click" );		
			$('#btnSave').click(function() {
				//추가 기능 수행
	
				modalClose("modal");
	
			});	
		}
		else if (popupClass == "modify") {
			//수정 팝업 초기화
			var res = initParam.split(",");
			
			var blacklist_id = res[0];
			$("#selectSite").val(res[1]);
			$('input:radio[name=rEnable]:input[value='+res[3]+']').attr("checked", true);
			$("#inputFilter").val(res[4]);
			$("#selectTime").val(res[5]);
			$("#txtareaDesc").val(res[6]);
	
			
			$('#btnSave').unbind( "click" );
			$('#btnSave').click(function() {
				//수정 기능 수행
				modalClose("modal");
			});
		}
		
		popupClass = "";		
	} catch (e) {
		e.message
	}
}

fnSiteInfoSearch = function(){
	var tag = "";
    $.ajax({
        url : 'select_site_info',
        type : "POST",
        data : null,
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
	            if (jsonObj.result == true) {
            	$.each(jsonObj.resultValue, function (index, obj) {   
            		if (obj.site_id == siteid) {
            			tag += "<option value=" + obj.site_id + " selected>" + obj.site_name + "</option>";
					}
            		else {
            			tag += "<option value=" + obj.site_id + ">" + obj.site_name + "</option>";
            		}
        		});
            	if (siteMaster == 'f') {
            		$('#selectSite').attr("disabled","true");
				}
            	$('#selectSite').html(tag);
            }
        },
        complete: function(data) {
        }
    });
}
