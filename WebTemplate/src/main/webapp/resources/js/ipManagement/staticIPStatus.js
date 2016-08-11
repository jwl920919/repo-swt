var table;
$(document).ready(function() {
	try
	{
		//console.log($('#datatable'));
//		$("#layDiv").css("visibility","hidden");		
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
				                   { className: "essential-td-left", "targets": [ 2,3,10 ] },
				                   { className: "essential-td-right", "targets": [ 4,5,6,7,8,9 ] }],
	                "order" : [ [ 0, 'asc' ] ],
	                "columns" : [ {"data" : "network"},
	                              {"data" : "ip_type"},
	                              {"data" : "start_ip"},
	                              {"data" : "end_ip"},
	                              {"data" : "used_ip",
										"render":function(data,type,full,meta){
											return Format_comma(data);
										}},
			                      {"data" : "ip_total",
										"render":function(data,type,full,meta){
											var ret = Format_comma(data);
											if (ret == "●●●") {
												return "<span style=\"font-weight: normal;\" data-toggle=\"tooltip\" title=\"data : " + data + "\" >" + ret + "</span>";
											}
											else{												
												return ret;
											}
										}},
			                      {"data" : "ip_usage",
										"render":function(data,type,full,meta){	
											return data + "%";
										}},
			                      {"data" : "range_used",
										"render":function(data,type,full,meta){	
											return Format_comma(data);
										}},
			                      {"data" : "range_total",
										"render":function(data,type,full,meta){	
											return Format_comma(data);
										}},
			                      {"data" : "range_usage",
										"render":function(data,type,full,meta){	
											return data + "%";
										}},
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
		    tdClickEvent(this);
		});
	} catch (e) {
		console.log("staticIPStatus.js $(document).ready Error Log : " + e.message);
	}
	
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
	try
	{
		//systemAlertNotify("divAlertArea", "alert-danger", getLanguage("warning"), $(obj).html());
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
	
			
			var ipCClass = String.format("{0} ~ {1}", $(obj).parent().children().eq(2).html(), $(obj).parent().children().eq(3).html());
			$("#segmentLabel").text(ipCClass);
			var network = $(obj).parent().children().eq(0).html();

			//탭 숨기기
			if ($(obj).parent().children().eq(1).html() == "IPV6") {
				console.log($(obj).parent().children().eq(1).html());
				$("#tapheader").css("visibility","collapse");
			}
			else{
				$("#tapheader").css("visibility","visible");
			} 
			
			$("#defaultDiv").css("display","none");
			$("#detailDiv").css("display","block");
			$("#selectSegment").text(network);
			//console.log("ipCClass : " + ipCClass);
			//console.log("network : " + network);
			
			var table = $('#datatable_detail').DataTable(
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
		                "sScrollXInner": "2300",
		                "bScrollCollapse": true,
		                "ajax" : {
		                    url : 'ipManagement/staticIPStatus_Segment_Detail_Select',
		                    "dataType" : "jsonp",
		                    "type" : "POST",
		                    "jsonp" : "callback",
		                    "data" : function(data,type) {
		                        data.search_key = data.search.value;
		                        data.network = network;
		                        data.timezone = getClientTimeZoneName();
		                    }
		                },
					    "columnDefs": [{ className: "essential-td-left", "targets": [ 0,2,3,5,6,9 ] }],
	//	                "aoColumns": [
	//	                              { "data" : "ipaddr", "sWidth": "300%" }, // 1st column width 
	//	                              { "data" : "ip_type", "sWidth": "1000%" }, // 2nd column width 
	//	                              { "data" : "macaddr", "sWidth": "100%" }, // 3rd column width
	//	                              { "data" : "duid", "sWidth": "80%" }, // 4th column width
	//	                              { "data" : "is_conflict",
	//										"render":function(data,type,full,meta){	                              
	//										if(data){
	//											return data;
	//										}else{
	//												//return "<button class='btn btn-block btn-info btn-sm' id='pdsSelect'> 선택</button>";
	//												return data;
	//											}
	//										}, "sWidth": "100%" }, // 5th column width and so on 
	//	                              { "data" : "status", "sWidth": "100%" }, // 1st column width 
	//	                              { "data" : "lease_state", "sWidth": "100%" }, // 2nd column width 
	//	                              { "data" : "obj_types", "sWidth": "200%" }, // 3rd column width
	//	                              { "data" : "discover_status", "sWidth": "100%" }, // 4th column width
	//	                              { "data" : "usage", "sWidth": "100%" }, // 5th column width and so on 
	//	                              { "data" : "host_name", "sWidth": "300%" }, // 1st column width 
	//	                              { "data" : "host_os", "sWidth": "100%" }, // 2nd column width 
	//	                              { "data" : "fingerprint", "sWidth": "300%" }, // 3rd column width
	//	                              { "data" : "is_never_ends", "sWidth": "100%" }, // 4th column width
	//	                              { "data" : "is_never_start","sWidth": "100%" }, // 5th column width and so on 
	//	                              { "data" : "lease_start_time", "sWidth": "100%" }, // 1st column width 
	//	                              { "data" : "lease_end_time", "sWidth": "100%" }, // 2nd column width 
	//	                              { "data" : "last_discovered", "sWidth": "100%" }, // 3rd column width
	//	                              { "data" : "user_description", "sWidth": "320%" }
	//	                        ],
	//
		                "order" : [ [ 0, 'asc' ] ],
		                "columns" : [ {"data" : "ipaddr"},
									  {"data" : "ip_type"},
									  {"data" : "macaddr"},
									  {"data" : "duid"},
									  {"data" : "ip_status"},
									  {"data" : "host_name"},
									  {"data" : "fingerprint"},
									  {"data" : "lease_start_time"},
									  {"data" : "lease_end_time"},
									  {"data" : "user_description"}]
		            });
			//$('div.dataTables_scrollBody').css('maxHeight', 600);
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
	} catch (e) {
		console.log("staticIPStatus.js tdClickEvent() Error Log : " + e.message);
	}
}

/**
 * IP Map Data Select
**/
//IP의 D 클래스 값을 담을 배열 선언
function mapDataCall(network){
	try
	{
		//console.log("mapDataCall : "+network);
		var DHCP_RangeArr = [];	
		var BROADCAST_Arr = [], NETWORK_Arr = [];
		var UNUSED_Arr = [], FIXED_Arr = [], RESERVATION_Arr = [], CONFLICT_Arr = [], LEASE_Arr = [], USED_Arr = [], ETC_Arr = [];
		var startipNumber, endipNumber;
		var jObj = Object();
	    jObj.network = network;
	    jObj.timezone = getClientTimeZoneName();
	
	    $('#divLoading').attr("style","visibility: visible");
	    
	    $.ajax({
	        url : 'ipManagement/staticIPStatus_Segment_MapData',
	        type : "POST",
	        data : JSON.stringify(jObj),
	        dataType : "text",
	        success : function(data) {
	            var jsonObj = eval("(" + data + ')');
		            if (jsonObj.result == true) {
	//	            	console.log("jsonObj.data : " + jsonObj.data);
	//	            	console.log("jsonObj.Value : " + jsonObj.resultValue);
		            	
	            	$.each(jsonObj.data, function (index, obj) {
	            		if (obj.KEY == "DHCP_RANGE") {
	            			//DHCP의 IP대역대 Stat, End영역과 Range 영역 설정을 위한 데이터
							startipNumber = ipToNumber(obj.Network_Start);
							endipNumber = ipToNumber(obj.Network_End);
							var row = Math.ceil((endipNumber - startipNumber + 1)/16); //0~255까지인데 255에서 0을 빼니까 항상 1을 더해줘야 256이 된다.
							//console.log("startipNumber: " + obj.Network_Start + ", endipNumber: " + obj.Network_End + ", row: " + row);
							ipMapSettings.rows = row; //ip영역에 따라 Row를 변경해준다.
							
		            		mapDataHelper(obj.DHCP_Range, DHCP_RangeArr);
	            		}
	            		else {							
						}
		            });
	            	
	            	$.each(jsonObj.resultValue, function (index, obj) {
	            		//console.log("jsonObj.Value : " + obj.ipaddr);
	            		
	            		// Javascript Class에 데이터 담기
	            	    var	classData = new MapDataClass (obj.ipaddr, obj.iptype, obj.macaddr, obj.duid, obj.ip_status, obj.host_name, obj.host_os,
	            	    		obj.fingerprint, obj.lease_start_time, obj.lease_end_time, obj.user_description);

	            		if (obj.ip_status == "BROADCAST") {
	            			BROADCAST_Arr.push(classData); 
						}
	            		else if (obj.ip_status == "NETWORK") {
	            			NETWORK_Arr.push(classData); 
						}
	            		else if (obj.ip_status ==  "UNUSED") {
	            			UNUSED_Arr.push(classData); 
						}
						else if (obj.ip_status ==  "FIXED") {
							FIXED_Arr.push(classData);
						}
						else if (obj.ip_status ==  "RESERVATION") {
							RESERVATION_Arr.push(classData);
						}
						else if (obj.ip_status ==  "CONFLICT") {
							CONFLICT_Arr.push(classData);
						}
						else if (obj.ip_status ==  "LEASE") {
							LEASE_Arr.push(classData);
						}
						else if (obj.ip_status ==  "USED") {
							USED_Arr.push(classData); 
						}
						else {
							ETC_Arr.push(classData); 
						}
		            });
	            	
	            	fnIPMapDraw(startipNumber, DHCP_RangeArr, BROADCAST_Arr, NETWORK_Arr, UNUSED_Arr, FIXED_Arr, RESERVATION_Arr, CONFLICT_Arr, LEASE_Arr, USED_Arr, ETC_Arr);
	            }
	        },
	        complete: function(data) {
	            $('#divLoading').attr("style","visibility: hidden");
	        }
	    });
	} catch (e) {
		console.log("staticIPStatus.js mapDataCall() Error Log : " + e.message);
	}
}

/**
 * 서버에서 받은 IP리스트들에서 D 클래스 정보만 배열에 담기 위한 function
**/
function mapDataHelper(stringIP, array){
	try
	{
		if (stringIP != undefined && stringIP.length > 0) {
			var arrIPs = stringIP.split( "," );
			
			for (var i = 0; i < arrIPs.length; i++) {
				if (checkIPv4(arrIPs[i])) {
					var arrip = arrIPs[i].split( "." );
					array.push(parseInt(arrip[3], 10)); 
				}
			}
		}
	} catch (e) {
		console.log("staticIPStatus.js mapDataHelper() Error Log : " + e.message);
	}
}

/**
 * IP에서 D 클래스 정보 return function
**/
function dClassIPHelper(stringIP){
	if (stringIP != undefined && stringIP.length > 0) {
		if (checkIPv4(stringIP)) {
			var arrip = stringIP.split( "." );
			return arrip[3];
		}
		return null;
	}
}

/**
 * IP Map Setting
**/
var ipMapSettings;
function fnIPMapSetting(){
    ipMapSettings = {
//        rows: 8,
//        cols: 32,
        rows: 16,
        cols: 16,
        rowCssPrefix: 'row-',
        colCssPrefix: 'col-',
//        rectangleWidth: 19,35
//        rectangleHeight: 19,
        rectangleWidth: 35,
        rectangleHeight: 35,
        rectangleCss: 'rectangle',
        selectingCss: 'selecting',
        dhcpRangeCss: 'dhcpRange',
        
        unusedCss: 'unused',
        networkCss: 'network',
        broadcastCss: 'broadcast',
        fixedCss: 'fixed',
        reservationCss: 'reservation',
        conflictCss: 'conflict',
        leaseCss: 'lease',
        usedCss: 'used',
        
        dhcp_unusedCss: 'dhcp_unused',
        dhcp_networkCss: 'dhcp_network',
        dhcp_broadcastCss: 'dhcp_broadcast',
        dhcp_fixedCss: 'dhcp_fixed',
        dhcp_reservationCss: 'dhcp_reservation',
        dhcp_conflictCss: 'dhcp_conflict',
        dhcp_leaseCss: 'dhcp_lease',
        dhcp_usedCss: 'dhcp_used',
                
        selected_unusedCss: 'selected_unused',
        selected_networkCss: 'selected_network',
        selected_broadcastCss: 'selected_broadcast',
        selected_fixedCss: 'selected_fixed',
        selected_reservationCss: 'selected_reservation',
        selected_conflictCss: 'selected_conflict',
        selected_leaseCss: 'selected_lease',
        selected_usedCss: 'selected_used'
    }
}

function fnIPMapDraw(startipNumber, DHCP_RangeArr, BROADCAST_Arr, NETWORK_Arr, UNUSED_Arr, FIXED_Arr, RESERVATION_Arr, CONFLICT_Arr, LEASE_Arr, USED_Arr, ETC_Arr){
	try
	{
		var str = [], ipNo = -1, className;
		var ipNumber = parseInt(startipNumber,10);
	
		if (!isNaN(ipNumber)) {
		    //console.log("org : " + ipNumber);
		    
		    for (i = 0; i < ipMapSettings.rows; i++) {
		        for (j = 0; j < ipMapSettings.cols; j++) {
		            ipNo += 1;
		            var address, status, conflict, usage, lease_state, fingerprint;
		            className = ipMapSettings.rectangleCss + ' ' + ipMapSettings.rowCssPrefix + i.toString() + ' ' + ipMapSettings.colCssPrefix + j.toString();
		
		            // IP대역 설정 및 DHCP Range설정 시작            
		            if ($.isArray(DHCP_RangeArr) && $.inArray(ipNo, DHCP_RangeArr) != -1) {
		                className += ' ' + ipMapSettings.dhcpRangeCss;
		                //console.log(address);
		            }
		            else {
		                className += ' ' + ipMapSettings.unusedCss;
		                address = numberToIp(ipNumber); 
			            status = "Unused";
			            usage = "None";
		            }
		            ipNumber += 1;
		            // IP대역 설정 및 DHCP Range설정 끝
		
		            
		            if (BROADCAST_Arr.length > 0) {
		            	for (var index = BROADCAST_Arr.length - 1; index >= 0; index--) {	
		                	var BROADCAST_dClassIP = dClassIPHelper(BROADCAST_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(BROADCAST_dClassIP,10)) {
		                		console.log("1 : " + ipMapSettings.dhcpRangeCss);
		                		console.log("2 : " + className);
		                		console.log("3 : " + className.indexOf(ipMapSettings.dhcpRangeCss) > 0);
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_broadcastCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.broadcastCss;
								}
		                		
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = BROADCAST_Arr[index].ipaddr; 
		                		status = BROADCAST_Arr[index].ip_status;
//		                		conflict = BROADCAST_Arr[index].is_conflict;
//		                		usage = BROADCAST_Arr[index].usage;
//		                		lease_state = BROADCAST_Arr[index].lease_state;
//		                		fingerprint = BROADCAST_Arr[index].fingerprint;
		                		BROADCAST_Arr.splice(index, 1);
		                		break;
		            		}
						}
		            }

		            if (NETWORK_Arr.length > 0) {
		            	for (var index = NETWORK_Arr.length - 1; index >= 0; index--) {					
		                	var NETWORK_dClassIP = dClassIPHelper(NETWORK_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(NETWORK_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_networkCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.networkCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = NETWORK_Arr[index].ipaddr; 
		                		status = NETWORK_Arr[index].ip_status;
//		                		conflict = NETWORK_Arr[index].is_conflict;
//		                		usage = NETWORK_Arr[index].usage;
//		                		lease_state = NETWORK_Arr[index].lease_state;
//		                		fingerprint = NETWORK_Arr[index].fingerprint;
		    	                NETWORK_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (UNUSED_Arr.length > 0) {
		            	for (var index = UNUSED_Arr.length - 1; index >= 0; index--) {					
		                	var UNUSED_dClassIP = dClassIPHelper(UNUSED_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(UNUSED_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_unusedCss;
								}
		                		else {
	                    			className.replace(' ' + ipMapSettings.unusedCss, '');
	                    			className += ' ' + ipMapSettings.unusedCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '');
		                		address = UNUSED_Arr[index].ipaddr; 
		                		status = UNUSED_Arr[index].ip_status;
//		                		conflict = UNUSED_Arr[index].is_conflict;
//		                		usage = UNUSED_Arr[index].usage;
//		                		lease_state = UNUSED_Arr[index].lease_state;
//		                		fingerprint = UNUSED_Arr[index].fingerprint;
		    	                UNUSED_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }

		            if (FIXED_Arr.length > 0) {
		            	for (var index = FIXED_Arr.length - 1; index >= 0; index--) {					
		                	var FIXED_dClassIP = dClassIPHelper(FIXED_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(FIXED_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_fixedCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.fixedCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = FIXED_Arr[index].ipaddr; 
		                		status = FIXED_Arr[index].ip_status;
//		                		conflict = FIXED_Arr[index].is_conflict;
//		                		usage = FIXED_Arr[index].usage;
//		                		lease_state = FIXED_Arr[index].lease_state;
//		                		fingerprint = FIXED_Arr[index].fingerprint;
		    	                FIXED_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (RESERVATION_Arr.length > 0) {
		            	for (var index = RESERVATION_Arr.length - 1; index >= 0; index--) {					
		                	var RESERVATION_dClassIP = dClassIPHelper(RESERVATION_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(RESERVATION_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_reservationCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.reservationCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = RESERVATION_Arr[index].ipaddr; 
		                		status = RESERVATION_Arr[index].ip_status;
//		                		conflict = RESERVATION_Arr[index].is_conflict;
//		                		usage = RESERVATION_Arr[index].usage;
//		                		lease_state = RESERVATION_Arr[index].lease_state;
//		                		fingerprint = RESERVATION_Arr[index].fingerprint;
		    	                RESERVATION_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            		            
		            if (CONFLICT_Arr.length > 0) {
		            	for (var index = CONFLICT_Arr.length - 1; index >= 0; index--) {					
		                	var CONFLICT_dClassIP = dClassIPHelper(CONFLICT_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(CONFLICT_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_conflictCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.conflictCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = CONFLICT_Arr[index].ipaddr; 
		                		status = CONFLICT_Arr[index].ip_status;
//		                		conflict = CONFLICT_Arr[index].is_conflict;
//		                		usage = CONFLICT_Arr[index].usage;
//		                		lease_state = CONFLICT_Arr[index].lease_state;
//		                		fingerprint = CONFLICT_Arr[index].fingerprint;
		    	                CONFLICT_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            		            
		            if (LEASE_Arr.length > 0) {
		            	for (var index = LEASE_Arr.length - 1; index >= 0; index--) {					
		                	var LEASE_dClassIP = dClassIPHelper(LEASE_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(LEASE_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_leaseCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.leaseCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = LEASE_Arr[index].ipaddr; 
		                		status = LEASE_Arr[index].ip_status;
//		                		conflict = LEASE_Arr[index].is_conflict;
//		                		usage = LEASE_Arr[index].usage;
//		                		lease_state = LEASE_Arr[index].lease_state;
//		                		fingerprint = LEASE_Arr[index].fingerprint;
		    	                LEASE_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            		            
		            if (USED_Arr.length > 0) {
		            	for (var index = USED_Arr.length - 1; index >= 0; index--) {					
		                	var USED_dClassIP = dClassIPHelper(USED_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(USED_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_usedCss;
								}
		                		else {
	                    			className += ' ' + ipMapSettings.usedCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = USED_Arr[index].ipaddr; 
		                		status = USED_Arr[index].status;
//		                		conflict = USED_Arr[index].is_conflict;
//		                		usage = USED_Arr[index].usage;
//		                		lease_state = USED_Arr[index].lease_state;
//		                		fingerprint = USED_Arr[index].fingerprint;
		    	                USED_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (ETC_Arr.length > 0) {
		            	for (var index = ETC_Arr.length - 1; index >= 0; index--) {					
		                	var ETC_dClassIP = dClassIPHelper(ETC_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(ETC_dClassIP,10)) {
		                		if (className.indexOf(ipMapSettings.dhcpRangeCss) > 0) {
	                    			className += ' ' + ipMapSettings.dhcp_unusedCss;
								}
		                		else {
	                    			className.replace(' ' + ipMapSettings.unusedCss, '');
	                    			className += ' ' + ipMapSettings.unusedCss;
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '');
		                		address = ETC_Arr[index].ipaddr; 
		                		status = ETC_Arr[index].ip_status;
//		                		conflict = ETC_Arr[index].is_conflict;
//		                		usage = ETC_Arr[index].usage;
//		                		lease_state = ETC_Arr[index].lease_state;
//		                		fingerprint = ETC_Arr[index].fingerprint;
		    	                ETC_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            var html = '<li class="' + className + '" data-toggle=\"tooltip\" data-html=\"true\"' +
		            			  'data-title=\"<table><tr><td style=\'text-align:left;\'>IP Address: ' + address + '</td></tr>';
		            			  if (status != "") {
		            				  html += '<tr><td style=\'text-align:left;\'>Ststus : ' + status + '</td></tr>';
		            			  }
		            			  if (conflict != "") {
		            				  html += '<tr><td style=\'text-align:left;\'>Conflict : ' + conflict + '</td></tr>';
		            			  }
		            			  if (usage != "") {
		            				  html += '<tr><td style=\'text-align:left;\'>Usage : ' + usage + '</td></tr>';
		            			  }
		            			  if (lease_state != "") {
		            				  html += '<tr><td style=\'text-align:left;\'>Lease state : ' + lease_state + '</td></tr>';
		            			  }
		            			  if (fingerprint != "") {
		            				  html += '<tr><td style=\'text-align:left;\'>Fingerprint : ' + fingerprint + '</td></tr>';
		            			  }
		            			  html += '</table>\"';
		            			  html += 'style="top:' + (i * ipMapSettings.rectangleHeight).toString() + 'px;left:' + (j * ipMapSettings.rectangleWidth).toString() + 'px"';
		            			  html += 'onclick=rectangleClick(this); align=center>';
		            			  html += '<a title="' + ipNo + '" style=\"line-height:50px\">' + ipNo + '</a>';
		            			  html += '</li>';
		            
		                          
		            str.push(html);
		        }
		    }
		    $('#place').html(str.join(''));
		}
		else {
			console.log("Check the DHCP Range setting - DB query is null!");
		}
	} catch (e) {
		console.log("staticIPStatus.js fnIPMapDraw() Error Log : " + e.message);
	}
}

/**
 * IP Map Rectangel Click Event - Class 변경해주기
**/
rectangleClick = function (obj) {
	try
	{
		console.log("map click : " + $(obj).attr('class'));
			
	    //unusedCss
	    if ($(obj).hasClass(ipMapSettings.unusedCss)) {
	    	$(obj).removeClass(ipMapSettings.unusedCss).addClass(ipMapSettings.unusedCss+ "ORG").addClass(ipMapSettings.selected_unusedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_unusedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_unusedCss).addClass(ipMapSettings.dhcp_unusedCss+ "ORG").addClass(ipMapSettings.selected_unusedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.unusedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_unusedCss).removeClass(ipMapSettings.unusedCss+ "ORG").addClass(ipMapSettings.unusedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_unusedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_unusedCss).removeClass(ipMapSettings.dhcp_unusedCss+ "ORG").addClass(ipMapSettings.dhcp_unusedCss);
	    }
	    
	    //networkCss
	    else if ($(obj).hasClass(ipMapSettings.networkCss)) {
	    	$(obj).removeClass(ipMapSettings.networkCss).addClass(ipMapSettings.networkCss+ "ORG").addClass(ipMapSettings.selected_networkCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_networkCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_networkCss).addClass(ipMapSettings.dhcp_networkCss+ "ORG").addClass(ipMapSettings.selected_networkCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.networkCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_networkCss).removeClass(ipMapSettings.networkCss+ "ORG").addClass(ipMapSettings.networkCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_networkCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_networkCss).removeClass(ipMapSettings.dhcp_networkCss+ "ORG").addClass(ipMapSettings.dhcp_networkCss);
	    }
	    
	    //broadcastCss
	    else if ($(obj).hasClass(ipMapSettings.broadcastCss)) {
	    	$(obj).removeClass(ipMapSettings.broadcastCss).addClass(ipMapSettings.broadcastCss+ "ORG").addClass(ipMapSettings.selected_broadcastCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_broadcastCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_broadcastCss).addClass(ipMapSettings.dhcp_broadcastCss+ "ORG").addClass(ipMapSettings.selected_broadcastCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.broadcastCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_broadcastCss).removeClass(ipMapSettings.broadcastCss+ "ORG").addClass(ipMapSettings.broadcastCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_broadcastCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_broadcastCss).removeClass(ipMapSettings.dhcp_broadcastCss+ "ORG").addClass(ipMapSettings.dhcp_broadcastCss);
	    }
	    
	    //fixedCss
	    else if ($(obj).hasClass(ipMapSettings.fixedCss)) {
	    	$(obj).removeClass(ipMapSettings.fixedCss).addClass(ipMapSettings.fixedCss+ "ORG").addClass(ipMapSettings.selected_fixedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_fixedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_fixedCss).addClass(ipMapSettings.dhcp_fixedCss+ "ORG").addClass(ipMapSettings.selected_fixedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.fixedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_fixedCss).removeClass(ipMapSettings.fixedCss+ "ORG").addClass(ipMapSettings.fixedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_fixedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_fixedCss).removeClass(ipMapSettings.dhcp_fixedCss+ "ORG").addClass(ipMapSettings.dhcp_fixedCss);
	    }
	    
	    //reservationCss
	    else if ($(obj).hasClass(ipMapSettings.reservationCss)) {
	    	$(obj).removeClass(ipMapSettings.reservationCss).addClass(ipMapSettings.reservationCss+ "ORG").addClass(ipMapSettings.selected_reservationCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_reservationCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_reservationCss).addClass(ipMapSettings.dhcp_reservationCss+ "ORG").addClass(ipMapSettings.selected_reservationCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.reservationCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_reservationCss).removeClass(ipMapSettings.reservationCss+ "ORG").addClass(ipMapSettings.reservationCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_reservationCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_reservationCss).removeClass(ipMapSettings.dhcp_reservationCss+ "ORG").addClass(ipMapSettings.dhcp_reservationCss);
	    }
	    
	    //conflictCss
	    else if ($(obj).hasClass(ipMapSettings.conflictCss)) {
	    	$(obj).removeClass(ipMapSettings.conflictCss).addClass(ipMapSettings.conflictCss+ "ORG").addClass(ipMapSettings.selected_conflictCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_conflictCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_conflictCss).addClass(ipMapSettings.dhcp_conflictCss+ "ORG").addClass(ipMapSettings.selected_conflictCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.conflictCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_conflictCss).removeClass(ipMapSettings.conflictCss+ "ORG").addClass(ipMapSettings.conflictCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_conflictCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_conflictCss).removeClass(ipMapSettings.dhcp_conflictCss+ "ORG").addClass(ipMapSettings.dhcp_conflictCss);
	    }
	    
	    //leaseCss
	    else if ($(obj).hasClass(ipMapSettings.leaseCss)) {
	    	$(obj).removeClass(ipMapSettings.leaseCss).addClass(ipMapSettings.leaseCss+ "ORG").addClass(ipMapSettings.selected_leaseCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_leaseCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_leaseCss).addClass(ipMapSettings.dhcp_leaseCss+ "ORG").addClass(ipMapSettings.selected_leaseCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.leaseCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_leaseCss).removeClass(ipMapSettings.leaseCss+ "ORG").addClass(ipMapSettings.leaseCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_leaseCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_leaseCss).removeClass(ipMapSettings.dhcp_leaseCss+ "ORG").addClass(ipMapSettings.dhcp_leaseCss);
	    }
	    
	    //usedCss
	    else if ($(obj).hasClass(ipMapSettings.usedCss)) {
	    	$(obj).removeClass(ipMapSettings.usedCss).addClass(ipMapSettings.usedCss+ "ORG").addClass(ipMapSettings.selected_usedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_usedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcp_usedCss).addClass(ipMapSettings.dhcp_usedCss+ "ORG").addClass(ipMapSettings.selected_usedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.usedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_usedCss).removeClass(ipMapSettings.usedCss+ "ORG").addClass(ipMapSettings.usedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcp_usedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_usedCss).removeClass(ipMapSettings.dhcp_usedCss+ "ORG").addClass(ipMapSettings.dhcp_usedCss);
	    }
	} catch (e) {
		console.log("staticIPStatus.js rectangleClick() Error Log : " + e.message);
	}
};

/**
 * Map 데이터 재 조회 function
**/
function mapRefresh(){
	tdClickEvent(selectedRow);
}

/**
 * Map 데이터 Class
**/
function MapDataClass (ipaddr, iptype, macaddr, duid, ip_status, host_name, host_os, fingerprint, lease_start_time, lease_end_time, user_description) {
    this.ipaddr = ipaddr;
    this.iptype = iptype;
    this.macaddr = macaddr;
    this.duid = duid;
    this.ip_status = ip_status;
    this.host_name = host_name;
    this.host_os = host_os;
    this.fingerprint = fingerprint;
    this.lease_start_time = lease_start_time;
    this.lease_end_time = lease_end_time;
    this.user_description = user_description;
}

function excelExport(){
	console.log("excelExport");
	console.log(table.dataTableSettings[0]);	
}