var table;
$(document).ready(function() {
console.log($('#datatable'));
	$("#layDiv").css("visibility","hidden");		
	table = $('#datatable').DataTable(
            {
                "destroy" : true,
                "paging" : true,
                "searching" : true,
                "lengthChange" : true,
                "ordering" : true,
                "info" : false,
                "autoWidth" : true,
                "processing" : true,
                "serverSide" : true,
                "ajax" : {
                    url : 'ipManagement/staticIPStatus_Segment_Select',
                    "dataType" : "jsonp",
                    "type" : "POST",
                    "jsonp" : "callback",
                    "data" : function(data,type) {
                        data.search_key = data.search.value;
                    }
                },
			    "columnDefs": [
//			                   { className: "essential-td-display_none", "targets": [ 0 ] },
			                   { className: "essential-td-left essential-td-cursor-pointer", "targets": [ 0 ] },
			                   { className: "essential-td-left", "targets": [ 1 ] },
			                   { className: "essential-td-left", "targets": [ 2 ] },
			                   { className: "essential-td-left", "targets": [ 3 ] }],
                "order" : [ [ 0, 'asc' ] ],
                "columns" : [ {"data" : "network"},
                              {"data" : "start_ip"},
                              {"data" : "end_ip"},
                              {"data" : "comment"}, ],
//                              dom: 'Bfrtip',
//                              buttons: [{extend: 'copyHtml5',exportOptions: {columns: [ 1, ':visible' ]}},
//                                        {extend: 'excelHtml5',exportOptions: {columns: ':visible'}},
//                                        {extend: 'pdfHtml5',exportOptions: {columns: [ 0, 1, 2, 3 ]}},
//                                  	'colvis'
//                              ]
            });
	
	//검색, 엔트리 위치 정렬
	$(function() {
	    var d_wrap = $('#datatable_wrapper .row:first');
	    var d_length = $('#datatable_wrapper .row:first .col-sm-6:eq(0)');
	    var d_filter = $('#datatable_wrapper .row:first .col-sm-6:eq(1)');
	    d_length.append(d_filter);
	    d_wrap.prepend(d_filter);
	});	

	//datatable 첫번째 td 클릭 이벤트 바인딩
	$('#datatable').delegate('tbody>tr>td:first-child', 'click', function() {
		//$('#datatable').delegate('tbody>tr>td:nth-child(2)', 'click', function() {
		//console.log("td click event : " + this);
	    tdClickEvent(this);
	});
	
	//ip map 초기화
	fnIPMapSetting();
});
//$(document).ajaxStart(function() {
//    // show loader on start
//	alert("ajaxStart");
//    //$("#divLoading").css("display","block");
//}).ajaxSuccess(function() {
//    // hide loader on success
//	alert("ajaxSuccess");
//    //$("#divLoading").css("display","none");
//});
/**
 * tr 이벤트 핸들러
**/
function trClickEvent (obj){
	return false;
}

/**
 * td 이벤트 핸들러
**/
var selectedRow;
function tdClickEvent(obj){
	//systemAlert("divAlertArea", "alert-danger", getLanguage("warning"), $(obj).html());
	//alert($(obj).html().trim());
	if (obj != "") {
		selectedRow = obj;
//		
//		var arrIp = $(obj).html().trim().split( "/" );
//		if (checkIPv4(arrIp[0])){
//			arrIps = arrIp[0].split( "." );
//			var ipCClass = String.format("{0}.{1}.{2}.0 ~ {0}.{1}.{2}.255", arrIps[0], arrIps[1], arrIps[2]);
//			$("#segmentLabel").text(ipCClass);
//		}
//		alert("Parent1 : " + $(obj).parent().children().eq(2).html());

		
		var ipCClass = String.format("{0} ~ {1}", $(obj).parent().children().eq(1).html(), $(obj).parent().children().eq(2).html());
		$("#segmentLabel").text(ipCClass);
		var network = $(obj).parent().children().eq(0).html();
				
		
		$("#defaultDiv").css("display","none");
		$("#detailDiv").css("display","block");
		$("#selectSegment").text(network);
		console.log("ipCClass : " + ipCClass);
		console.log("network : " + network);
	
		$('#datatable_detail').DataTable(
	            {
	                "destroy" : true,
	                "paging" : true,
	                "searching" : true,
	                "lengthChange" : true,
	                "ordering" : true,
	                "info" : false,
	                "autoWidth" : true,
	                "processing" : true,
	                "serverSide" : true,
	                "ajax" : {
	                    url : 'ipManagement/staticIPStatus_Segment_Detail_Select',
	                    "dataType" : "jsonp",
	                    "type" : "POST",
	                    "jsonp" : "callback",
	                    "data" : function(data,type) {
	                        data.search_key = data.search.value;
	                        data.network = network;
	                        //console.log(data.search_key);
	                    }
	                },
				    "columnDefs": [{ className: "essential-td-left", "targets": [ 0 ] },
				                   { className: "essential-td-left", "targets": [ 1 ] },
				                   { className: "essential-td-left", "targets": [ 2 ] },
				                   { className: "essential-td-left", "targets": [ 3 ] },
				                   { className: "essential-td-left", "targets": [ 4 ] },
				                   { className: "essential-td-left", "targets": [ 5 ] },
				                   { className: "essential-td-left", "targets": [ 6 ] },
				                   { className: "essential-td-left", "targets": [ 7 ] },
				                   { className: "essential-td-left", "targets": [ 8 ] },
				                   { className: "essential-td-left", "targets": [ 9 ] },
				                   { className: "essential-td-left", "targets": [ 10 ] },
				                   { className: "essential-td-left", "targets": [ 11 ] },
				                   { className: "essential-td-left", "targets": [ 12 ] }],
	                "order" : [ [ 0, 'asc' ] ],
	                "columns" : [ {"data" : "ipaddr"},
	                              {"data" : "ip_type"},
	                              {"data" : "macaddr"},
	                              {"data" : "duid"},
	                              {"data" : "hostname"},
	                              {"data" : "state"},
	                              {"data" : "username"},
	                              {"data" : "fingerprint"},
	                              {"data" : "os"},
	                              {"data" : "lease_start_time"},
	                              {"data" : "lease_end_time"},
	                              {"data" : "last_discovered"},
	                              {"data" : "description"}]
	            });
		$('div.dataTables_scrollBody').css('maxHeight', 600);
		//$("#datatable_detail tbody").css('maxHeight', 650);
		//검색, 엔트리 위치 정렬
		$(function() {
		    var d_wrap = $('#datatable_detail_wrapper .row:first');
		    var d_length = $('#datatable_detail_wrapper .row:first .col-sm-6:eq(0)');
		    var d_filter = $('#datatable_detail_wrapper .row:first .col-sm-6:eq(1)');
		    d_length.append(d_filter);
		    d_wrap.prepend(d_filter);
		});
		
		
		mapDataCall(network);
	}
}

/**
 * IP Map Data Select
**/
//IP의 D 클래스 값을 담을 배열 선언
function mapDataCall(network){
	console.log("mapDataCall : "+network);
	var DHCP_RangeArr = [], ActiveLeaseArr = [], ConflictArr = [], ExclusionArr = [], FixedArr = [], HostnotindnsArr = [];
	var ObjectArr = [], PendingArr = [], ReservedrangeArr = [], UnmanagedArr = [], UnusedArr = [], UsedArr = []; 
	var jObj = Object();
    jObj.network = network;

    $('#divLoading').attr("style","visibility: visible");
    
    $.ajax({
        url : 'ipManagement/staticIPStatus_Segment_MapData',
        type : "POST",
        data : JSON.stringify(jObj),
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
	            if (jsonObj.result == true) {
	            	console.log("jsonObj.data: "+jsonObj.data);
	            	
            	$.each(jsonObj.data, function (index, obj) {
            		if (obj.KEY == "DHCP_RANGE") {
            			//DHCP의 IP대역대 Stat, End영역과 Range 영역 설정을 위한 데이터
						var startipNumber = ipToNumber(obj.Network_Start);
						var endipNumber = ipToNumber(obj.Network_End);
						var row = Math.ceil((endipNumber - startipNumber + 1)/32); //0~255까지인데 255에서 0을 빼니까 항상 1을 더해줘야 256이 된다.
						//console.log("startipNumber: " + obj.Network_Start + ", endipNumber: " + obj.Network_End + ", row: " + row);
						ipMapSettings.rows = row; //ip영역에 따라 Row를 변경해준다.
						
	            		mapDataHelper(obj.DHCP_Range, DHCP_RangeArr);
	            		mapDataHelper(obj.activeLease, ActiveLeaseArr);
	            		mapDataHelper(obj.conflict, ConflictArr);
						mapDataHelper(obj.exclusion, ExclusionArr);
						mapDataHelper(obj.fixed, FixedArr);
						mapDataHelper(obj.hostnotindns, HostnotindnsArr);
						mapDataHelper(obj.object, ObjectArr);
						mapDataHelper(obj.pending, PendingArr);
						mapDataHelper(obj.reservedrange, ReservedrangeArr);
						mapDataHelper(obj.unmanaged, UnmanagedArr);
						mapDataHelper(obj.unused, UnusedArr);
						mapDataHelper(obj.used, UsedArr);
						
						fnIPMapInit(obj.cClassIPAddress, DHCP_RangeArr, ActiveLeaseArr, ConflictArr, ExclusionArr, FixedArr, HostnotindnsArr, ObjectArr,
								PendingArr,	ReservedrangeArr, UnmanagedArr, UnusedArr, UsedArr);
            		}
            		else {
						
					}
	            });
            }
        },
        complete: function(data) {
            $('#divLoading').attr("style","visibility: hidden");
        }
    });
}

/**
 * 서버에서 받은 IP리스트들에서 D 클래스 정보만 배열에 담기 위한 function
**/
function mapDataHelper(stringIP, array){
	if (stringIP != undefined && stringIP.length > 0) {
		var arrIPs = stringIP.split( "," );
		
		for (var i = 0; i < arrIPs.length; i++) {
			if (checkIPv4(arrIPs[i])) {
				var arrip = arrIPs[i].split( "." );
				array.push(parseInt(arrip[3], 10)); 
			}
		}
	}
}

/**
 * IP Map Setting
**/
var ipMapSettings;
function fnIPMapSetting(){
    ipMapSettings = {
        rows: 8,
        cols: 32,
        rowCssPrefix: 'row-',
        colCssPrefix: 'col-',
        rectangleWidth: 19,
        rectangleHeight: 19,
        rectangleCss: 'rectangle',
        dhcpRangeCss: 'dhcpRange',
        activeLeaseCss: 'activeLease',
        conflictCss: 'conflict',
        exclusionCss: 'exclusion',
        fixedCss: 'fixed',
        hostnotindnsCss: 'hostnotindns',
        objectCss: 'object',
        pendingCss: 'pending',
        reservedrangeCss: 'reservedrange',
        unmanagedCss: 'unmanaged',
        unusedCss: 'unused',
        usedCss: 'used',
        selectingCss: 'selecting'
    };
}


/**
 * IP Map Initialize
**/
var chargePerSheet;
function fnIPMapInit (ipaddress, DHCP_RangeArr, ActiveLeaseArr, ConflictArr, ExclusionArr, FixedArr, HostnotindnsArr, ObjectArr, 
		PendingArr,	ReservedrangeArr, UnmanagedArr, UnusedArr, UsedArr) {
    var str = [], ipNo = -1, className, address, status;
    console.log("fnIPMapInit");
    
    for (i = 0; i < ipMapSettings.rows; i++) {
        for (j = 0; j < ipMapSettings.cols; j++) {
            //ipNo = (i + j * ipMapSettings.rows + 1);
            ipNo += 1;
            className = ipMapSettings.rectangleCss + ' ' + ipMapSettings.rowCssPrefix + i.toString() + ' ' + ipMapSettings.colCssPrefix + j.toString();

            // IP대역 설정 및 DHCP Range설정 시작
            if ($.isArray(DHCP_RangeArr) && $.inArray(ipNo, DHCP_RangeArr) != -1) {
                className += ' ' + ipMapSettings.dhcpRangeCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Range";
            }
            else {
                className += ' ' + ipMapSettings.unusedCss;
	            address = String.format("{0}.{1}", ipaddress, ipNo); 
	            status = "Unused";
            }
            // IP대역 설정 및 DHCP Range설정 끝
            
            if ($.isArray(ActiveLeaseArr) && $.inArray(ipNo, ActiveLeaseArr) != -1) {
                className += ' ' + ipMapSettings.activeLeaseCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "ActiveLease";
            }
            if ($.isArray(ConflictArr) && $.inArray(ipNo, ConflictArr) != -1) {
                className += ' ' + ipMapSettings.conflictCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Conflict";
            }
            if ($.isArray(ExclusionArr) && $.inArray(ipNo, ExclusionArr) != -1) {
                className += ' ' + ipMapSettings.exclusionCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Exclusion";
            }
            if ($.isArray(FixedArr) && $.inArray(ipNo, FixedArr) != -1) {
                className += ' ' + ipMapSettings.fixedCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Fixed";
            }
            if ($.isArray(HostnotindnsArr) && $.inArray(ipNo, HostnotindnsArr) != -1) {
                className += ' ' + ipMapSettings.hostnotindnsCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Hostnotindns";
            }
            if ($.isArray(ObjectArr) && $.inArray(ipNo, ObjectArr) != -1) {
                className += ' ' + ipMapSettings.objectCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "bject";
            }
            if ($.isArray(PendingArr) && $.inArray(ipNo, PendingArr) != -1) {
                className += ' ' + ipMapSettings.pendingCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Pending";
            }
            if ($.isArray(ReservedrangeArr) && $.inArray(ipNo, ReservedrangeArr) != -1) {
                className += ' ' + ipMapSettings.reservedrangeCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Reservedrange";
            }
            if ($.isArray(UnmanagedArr) && $.inArray(ipNo, UnmanagedArr) != -1) {
                className += ' ' + ipMapSettings.unmanagedCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Unmanaged";
            }
            if ($.isArray(UnusedArr) && $.inArray(ipNo, UnusedArr) != -1) {
                className += ' ' + ipMapSettings.unusedCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Unused";
            }
            if ($.isArray(UsedArr) && $.inArray(ipNo, UsedArr) != -1) {
                className += ' ' + ipMapSettings.usedCss;
                address = String.format("{0}.{1}", ipaddress, ipNo); 
                status = "Used";
            }

            str.push('<li class="' + className + '" data-toggle=\"tooltip\" data-html=\"true\"' +
            			  'data-title=\"<table><tr><td style=\'text-align:left;\'>IP Address: '+address+'</td></tr>' +
            			  '<tr><td style=\'text-align:left;\'>Ststus: '+status+'</td></tr></table>\"' +
                          'style="top:' + (i * ipMapSettings.rectangleHeight).toString() + 'px;left:' + (j * ipMapSettings.rectangleWidth).toString() + 'px"' + 
                          'onclick=rectangleClick(this); align=center>' +
//                          '<a title="' + ipNo + '" style=\"line-height:50px\">' + ipNo + '</a>' +
                          '</li>');
        }
    }
    $('#place').html(str.join(''));
    //chargePerSheet = $('#<%=txtAmount.ClientID %>').val();
};

/**
 * IP Map Rectangel Click Event - Class 변경해주기
**/
rectangleClick = function (obj) {
	//console.log($(obj).attr('class'));
    if ($(obj).hasClass(ipMapSettings.usedCss)) {
        alert('This ip is already reserved');
    }
    else if ($(obj).hasClass(ipMapSettings.activeLeaseCss)) {
    	//console.log("hasClass : " + $(obj).hasClass(ipMapSettings.selectingCss));
    	$(obj).removeClass(ipMapSettings.activeLeaseCss).addClass(ipMapSettings.activeLeaseCss+ "ORG").addClass(ipMapSettings.selectingCss);
    }
    else if ($(obj).hasClass(ipMapSettings.activeLeaseCss+"ORG")) {
    	//console.log("hasClass : " + $(obj).hasClass(ipMapSettings.selectingCss));
    	$(obj).removeClass(ipMapSettings.activeLeaseCss+ "ORG").removeClass(ipMapSettings.selectingCss).addClass(ipMapSettings.activeLeaseCss);
    }
    else if ($(obj).hasClass(ipMapSettings.selectingCss)) {
    	//console.log("hasClass : " + $(obj).hasClass(ipMapSettings.selectingCss));
    	$(obj).removeClass(ipMapSettings.selectingCss);//.addClass(ipMapSettings.unusedCss);
    }
    else if ($(obj).hasClass(ipMapSettings.unusedCss)) {
        $(obj).removeClass(ipMapSettings.unusedCss).addClass(ipMapSettings.selectingCss);
    }
};

/**
 * Map 데이터 재 조회 function
**/
function mapRefresh(){
	tdClickEvent(selectedRow);
}

function excelExport(){
	console.log("excelExport");
	console.log(table.dataTableSettings[0]);
//	if (true) {
//		var myTableSettings = table.dataTableSettings[0];
//
//		$.ajax(
//		    {
//		        url: "GenerateCsv",
//		        type: "POST",
//		        data: myTableSettings.oAjaxData,
//		        success: function (data) {
//		            document.location.href = 'DownloadCsv';
//		        },
//		        error: function () {}
//		        });
//	}
	
}