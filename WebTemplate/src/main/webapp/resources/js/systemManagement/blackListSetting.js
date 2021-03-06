var table;
var popupClass;
var initParam;
$(document).ready(function() {	

	modalClose("modal");

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
											return data + " " + getLanguage("second");
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
		console.log("blackListSetting.js document.ready Error Log : " + e.message);
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

/**
 * 추가 버튼 클릭 이벤트 핸들러
**/
fnAdd = function(obj){
	popupClass = "add";
	modalShow("modal");
}

/**
 * Datatable Row의 수정 버튼 클릭 이벤트 핸들러
**/
fnModify = function(obj){
	popupClass = "modify";
	initParam = obj;
	modalShow("modal");
}

/**
 * Datatable Row의 삭제 버튼 클릭 이벤트 핸들러
**/
fnDelete = function(obj){
	initParam = obj;
//	var res = obj.split(",");
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
	
	//삭제 여부를 묻고 fnDeleteEvent 함수에서 처리
	systemAlertConfirm("divAlertArea", "alert-warning", getLanguage("delete"), getLanguage("areyousureyouwanttodelete"), getLanguage("delete"), "#ce891c", 'fnDeleteEvent');
}

/**
 * 추가,수정 모달 팝업 Show 이벤트 핸들러
 * popupClass에 따라 추가, 수정으로 나뉨
**/
fnShowEvent = function(){
	var param = Object();
	try {
		if (popupClass == "add") {
			//추가 팝업 초기화
			$("#selectSite").val(siteid);
			$('input:radio[name=rEnable]:input[value=true]').prop("checked", true);
			$("#inputFilter").val("");
			$("#selectTime").val(60);
			$("#txtareaDesc").val("");
			
			$('#btnSave').unbind( "click" );		
			$('#btnSave').click(function() {
				//추가 기능 수행				
				if (checkVaridation()) {
					param.functionclass = "add";
					param.time_zone = getClientTimeZoneName();
					param.siteid = $("#selectSite").val();
					param.blackenable = $(":input:radio[name=rEnable]:checked").val();
					param.filtername = $("#inputFilter").val();
					param.filtertime = $("#selectTime").val();
					param.description = $("#txtareaDesc").val();
					param.blacklistid = "-1"; //추가시는 의미없는 데이터임
				    
				    $.ajax({
				        url : 'systemManagement/blackListSetting_Data_Insert_Update_Delete',
				        type : "POST",
				        data : JSON.stringify(param),
				        dataType : "text",
				        success : function(data) {
				            var jsonObj = eval("(" + data + ')');
				            if (jsonObj.result == true) {
				            	table.ajax.reload(); //데이터 제 조회
				            	systemAlertNotify("divAlertArea", "alert-success", getLanguage("add"), getLanguage("saved"));
				            }
				        },
				        complete: function(data) {
				        }
				    });
					modalClose("modal");	
				}
			});	
		}
		else if (popupClass == "modify") {
			//수정 팝업 초기화
			var res = initParam.split(",");			
			var blacklist_id = res[0];
			$("#selectSite").val(res[1]);
			$('input:radio[name=rEnable]:input[value='+res[3]+']').prop("checked", true);
			$("#inputFilter").val(res[4]);
			$("#selectTime").val(res[5]);
			$("#txtareaDesc").val(res[6]);
	
			
			$('#btnSave').unbind( "click" );
			$('#btnSave').click(function() {
				//수정 기능 수행
				if (checkVaridation()) {
					param.functionclass = "modify";
					param.time_zone = getClientTimeZoneName();
					param.siteid = $("#selectSite").val();
					param.blackenable = $(":input:radio[name=rEnable]:checked").val();
					param.filtername = $("#inputFilter").val();
					param.filtertime = $("#selectTime").val();
					param.description = $("#txtareaDesc").val();
					param.blacklistid = blacklist_id;
					
				    $.ajax({
				        url : 'systemManagement/blackListSetting_Data_Insert_Update_Delete',
				        type : "POST",
				        data : JSON.stringify(param),
				        dataType : "text",
				        success : function(data) {
				            var jsonObj = eval("(" + data + ')');
				            if (jsonObj.result == true) {
				            	table.ajax.reload(); //데이터 제 조회
				            	systemAlertNotify("divAlertArea", "alert-warning", getLanguage("modify"), getLanguage("changed"));
				            }
				        },
				        complete: function(data) {
				        }
				    });
					modalClose("modal");	
				}
			});
		}
		
		popupClass = "";		
	} catch (e) {
		e.message
	}
}

/**
 * 삭제 이벤트 핸들러
**/
fnDeleteEvent = function(){
	var param = Object();
	var res = initParam.split(",");	
	param.functionclass = "delete";
	param.blacklistid = res[0];
	param.siteid = "0";
	param.filtertime = "0";
	
	try {		
	    $.ajax({
	        url : 'systemManagement/blackListSetting_Data_Insert_Update_Delete',
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

/**
 * 모달 팝업의 사업장 정보 조회 이벤트 핸들러
**/
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

/**
 * 추가, 수정 전 항목 체크
**/
checkVaridation = function(){
	var ret = true;
	if ($("#selectSite").val() == "") {
		systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("selectsite"));
		ret = false;
	}
	else if ($(":input:radio[name=rEnable]:checked").val() == "") {
		systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("selectwhetherornottouse"));
		ret = false;
	}
	else if ($("#inputFilter").val() == "") {
		systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("enterthefiltername"));
		ret = false;
	}
	else if ($("#selectTime").val() == "") {
		systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("selectthefiltertime"));
		ret = false;
	}
	else if ($("#txtareaDesc").val() == "") {
		systemAlertNotify("divAlertArea", "alert-warning", getLanguage("warning"), getLanguage("enterdescriptionoftheregisterfilter"));
		ret = false;
	}
	return ret;
}
