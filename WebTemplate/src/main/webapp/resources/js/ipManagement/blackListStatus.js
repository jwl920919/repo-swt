var table;
var popupClass;
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
	                    url : 'ipManagement/blackListStatus_Data_Select',
	                    "dataType" : "jsonp",
	                    "type" : "POST",
	                    "jsonp" : "callback",
	                    "data" : function(data,type) {
	                    	//data.search.value = searchText;
	                        data.search_key = data.search.value;
	                    }
	                },	                
				    "columnDefs": [{ className: "essential-td-left", "targets": [ 4 ] },
				                   { className: "essential-td-right", "targets": [ 5,6 ] },
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
                                    	    console.log(jObj);
                                                return "<div class='tools'>"+
                                                " <i class='fa fa-plus essential-cursor-pointer' onclick='fnAdd();' data-toggle='tooltip' title='" + getLanguage("add") + "'></i>" + 
                                                " <i class='fa fa-edit essential-cursor-pointer' onclick=\"fnModify('" + jObj + "');\" data-toggle='tooltip' title='" + getLanguage("modify") + "'></i>"+
//                                                ' <i class="fa fa-trash-o essential-cursor-pointer" onclick="fnDelete("'+jsonString+'");" data-toggle="tooltip" title="' + getLanguage("delete") + '" ></i> </div>";
                                                " <i class='fa fa-trash-o essential-cursor-pointer' onclick=\"fnDelete('" + jObj + "');\" data-toggle='tooltip' title='" + getLanguage("delete") + "' ></i> </div>";                                        }
                                    }],			//6	
	                "createdRow": function( row, data, dataIndex ) {
	                	//alert($(row).children(0).html()); 
	                	//Datatable TR Create Event
//		    			    if ( $(row).children(0).html() == "0" ) {
//		    			        $(row).addClass( 'essential-tr-cursor-pointer' );
//		    			    }
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


	//세그먼트 Selectbox change 이벤트
	$('#btnSave').click(function() {
		console.log("fnSave");
		modalClose("modal");
	});

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
}

fnShowEvent = function(){
	if (popupClass == "add") {
		console.log("add");
	}
	else if (popupClass == "modify") {
		console.log("modify");
	}
	popupClass = "";
}


