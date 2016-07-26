var table;
$(document).ready(function() {
	try
	{
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
		                "sScrollXInner": "3000",
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
					    "columnDefs": [{ className: "essential-td-left", "targets": [ 0,2,3,7,9,10 ] },
					                   { className: "essential-td-left", "targets": [ 11,12,17,18 ] }],
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
		                "columns" : [	{"data" : "ipaddr"},
										{"data" : "ip_type"},
										{"data" : "macaddr"},
										{"data" : "duid"},
										{"data" : "is_conflict",
												"render":function(data,type,full,meta){	                              
															if(data){
																return data;
															}else{
																	//return "<button class='btn btn-block btn-info btn-sm' id='pdsSelect'> 선택</button>";
																return data;
															}
														}
												},
										{"data" : "status"},
										{"data" : "lease_state"},
										{"data" : "obj_types"},
										{"data" : "discover_status"},
										{"data" : "usage"},
										{"data" : "host_name"},
										{"data" : "host_os"},
										{"data" : "fingerprint"},
										{"data" : "is_never_ends"},
										{"data" : "is_never_start"},
										{"data" : "lease_start_time"},
										{"data" : "lease_end_time"},
										{"data" : "last_discovered"},
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
		console.log("mapDataCall : "+network);
		var DHCP_RangeArr = [];	
		var BROADCAST_Arr = [], NETWORK_Arr = [];
		var ABANDONED_Arr = [], ACTIVE_Arr = [], BACKUP_Arr = [], DECLINED_Arr = [], EXPIRED_Arr = [];
		var FREE_Arr = [], OFFERED_Arr = [], RELEASED_Arr = [], RESET_Arr = [], STATIC_Arr = [], FIXED_Arr = [], ETC_Arr = [];
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
							var row = Math.ceil((endipNumber - startipNumber + 1)/32); //0~255까지인데 255에서 0을 빼니까 항상 1을 더해줘야 256이 된다.
							//console.log("startipNumber: " + obj.Network_Start + ", endipNumber: " + obj.Network_End + ", row: " + row);
							ipMapSettings.rows = row; //ip영역에 따라 Row를 변경해준다.
							
		            		mapDataHelper(obj.DHCP_Range, DHCP_RangeArr);
		            		
	//	            		mapDataHelper(obj.activeLease, ActiveLeaseArr);
	//	            		mapDataHelper(obj.conflict, ConflictArr);
	//						mapDataHelper(obj.exclusion, ExclusionArr);
	//						mapDataHelper(obj.fixed, FixedArr);
	//						mapDataHelper(obj.hostnotindns, HostnotindnsArr);
	//						mapDataHelper(obj.object, ObjectArr);
	//						mapDataHelper(obj.pending, PendingArr);
	//						mapDataHelper(obj.reservedrange, ReservedrangeArr);
	//						mapDataHelper(obj.unmanaged, UnmanagedArr);
	//						mapDataHelper(obj.unused, UnusedArr);
	//						mapDataHelper(obj.used, UsedArr);
	//						
	//						fnIPMapInit(obj.cClassIPAddress, DHCP_RangeArr, ActiveLeaseArr, ConflictArr, ExclusionArr, FixedArr, HostnotindnsArr, ObjectArr,
	//								PendingArr,	ReservedrangeArr, UnmanagedArr, UnusedArr, UsedArr);
	            		}
	            		else {
							
						}
		            });
	            	
	            	$.each(jsonObj.resultValue, function (index, obj) {
	            		//console.log("jsonObj.Value : " + obj.ipaddr);
	            		
	            		// Javascript Class에 데이터 담기
	            	    var	classData = new MapDataClass (obj.ipaddr, obj.macaddr, obj.is_conflict, obj.conflict_types, obj.status,
	            	    		obj.lease_state, obj.obj_types, obj.usage, obj.host_name, obj.host_os, obj.fingerprint,
	            	    		obj.is_never_ends, obj.is_never_start, obj.lease_start_time, obj.lease_end_time);
	
	            	    //console.log("classData.ipaddr.toUpperCase() : " + classData.ipaddr.toUpperCase());
	            		if (classData.obj_types.toUpperCase() == "BROADCAST") {
	            			BROADCAST_Arr.push(classData); 
						}
	            		else if (obj.obj_types.toUpperCase() == "NETWORK") {
	            			NETWORK_Arr.push(classData); 
						}
	            		else {
	            			//ABANDONED_Arr = [], ACTIVE_Arr = [], BACKUP_Arr = [], DECLINED_Arr = [], EXPIRED_Arr = [],
	            			//FREE_Arr = [], OFFERED_Arr = [], RELEASED_Arr = [], RESET_Arr = [], STATIC_Arr = [], FIXED_Arr = [], ETC_Arr = [];
							if (obj.lease_state.toUpperCase() ==  "ABANDONED") {
								ABANDONED_Arr.push(classData); 
							}
							else if (obj.lease_state.toUpperCase() ==  "ACTIVE") {
								ACTIVE_Arr.push(classData);
							}
							else if (obj.lease_state.toUpperCase() ==  "BACKUP") {
								BACKUP_Arr.push(classData);
							}
							else if (obj.lease_state.toUpperCase() ==  "DECLINED") {
								DECLINED_Arr.push(classData);
							}
							else if (obj.lease_state.toUpperCase() ==  "EXPIRED") {
								EXPIRED_Arr.push(classData);
							}
							else if (obj.lease_state.toUpperCase() ==  "FREE") {
								FREE_Arr.push(classData); 
							}
							else if (obj.lease_state.toUpperCase() ==  "OFFERED") {
								OFFERED_Arr.push(classData); 
							}
							else if (obj.lease_state.toUpperCase() ==  "RELEASED") {
								RELEASED_Arr.push(classData); 
							}
							else if (obj.lease_state.toUpperCase() ==  "RESET") {
								RESET_Arr.push(classData); 
							}
							else if (obj.lease_state.toUpperCase() ==  "STATIC") {
								STATIC_Arr.push(classData); 
							}
							else if (obj.lease_state.toUpperCase() ==  "FIXED") {
								FIXED_Arr.push(classData); 
							}
							else {
								ETC_Arr.push(classData); 
							}
						}
		            });
	            	
	            	fnIPMapDraw(startipNumber, DHCP_RangeArr, BROADCAST_Arr, NETWORK_Arr, ABANDONED_Arr, ACTIVE_Arr, BACKUP_Arr, DECLINED_Arr, EXPIRED_Arr,
	            			FREE_Arr, OFFERED_Arr, RELEASED_Arr, RESET_Arr, STATIC_Arr, FIXED_Arr, ETC_Arr);
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
        rows: 8,
        cols: 32,
        rowCssPrefix: 'row-',
        colCssPrefix: 'col-',
        rectangleWidth: 19,
        rectangleHeight: 19,
        rectangleCss: 'rectangle',
        selectingCss: 'selecting',

        dhcpRangeCss: 'dhcpRange',
        activeLeaseCss: 'activeLease',
        conflictCss: 'conflict',
        fixedCss: 'fixed',
        hostnotindnsCss: 'hostnotindns',
        dnsObjectCss: 'dnsObject',
        pendingCss: 'pending',
        unmanagedCss: 'unmanaged',
        unusedCss: 'unused',
        usedCss: 'used',
        
        dhcpRange_activeLeaseCss: 'dhcpRange_activeLease',
        dhcpRange_conflictCss: 'dhcpRange_conflict',
        dhcpRange_fixedCss: 'dhcpRange_fixed',
        dhcpRange_hostnotindnsCss: 'dhcpRange_hostnotindns',
        dhcpRange_dnsObjectCss: 'dhcpRange_dnsObject',
        dhcpRange_pendingCss: 'dhcpRange_pending',
        dhcpRange_unmanagedCss: 'dhcpRange_unmanaged',
        dhcpRange_unusedCss: 'dhcpRange_unused',
        dhcpRange_usedCss: 'dhcpRange_used',

        selected_activeLeaseCss: 'selected_activeLease',
        selected_conflictCss: 'selected_conflict',
        selected_fixedaddressCss: 'selected_fixedaddress',
        selected_hostnotindnsCss: 'selected_hostnotindns',
        selected_dnsobjectCss: 'selected_dnsobject',
        selected_pendingCss: 'selected_pending',
        selected_unmanagedCss: 'selected_unmanaged',
        selected_unusedCss: 'selected_unused',
        selected_usedCss: 'selected_used',
    };
}


function fnIPMapDraw(startipNumber, DHCP_RangeArr, BROADCAST_Arr, NETWORK_Arr, ABANDONED_Arr, ACTIVE_Arr, BACKUP_Arr, DECLINED_Arr, EXPIRED_Arr,
		FREE_Arr, OFFERED_Arr, RELEASED_Arr, RESET_Arr, STATIC_Arr, FIXED_Arr, ETC_Arr){
	try
	{
		var str = [], ipNo = -1, className;
		var ipNumber = parseInt(startipNumber,10);
	
		if (!isNaN(ipNumber)) {
		    console.log("org : " + ipNumber);
		    
		    for (i = 0; i < ipMapSettings.rows; i++) {
		        for (j = 0; j < ipMapSettings.cols; j++) {
		            ipNo += 1;
		            var address, status, conflict, usage, lease_state, fingerprint;
		            className = ipMapSettings.rectangleCss + ' ' + ipMapSettings.rowCssPrefix + i.toString() + ' ' + ipMapSettings.colCssPrefix + j.toString();
		
		            // IP대역 설정 및 DHCP Range설정 시작            
		            if ($.isArray(DHCP_RangeArr) && $.inArray(ipNo, DHCP_RangeArr) != -1) {
		                className += ' ' + ipMapSettings.dhcpRangeCss;
		                console.log(address);
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
		                		if (className.includes(ipMapSettings.dhcpRangeCss)) {
		                    		if (BROADCAST_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.dhcpRange_conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.dhcpRange_usedCss;
		    						}
								}
		                		else {
		                    		if (BROADCAST_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.usedCss;
		    						}
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = BROADCAST_Arr[index].ipaddr; 
		                		status = BROADCAST_Arr[index].status;
		                		conflict = BROADCAST_Arr[index].is_conflict;
		                		usage = BROADCAST_Arr[index].usage;
		                		lease_state = BROADCAST_Arr[index].lease_state;
		                		fingerprint = BROADCAST_Arr[index].fingerprint;
		                		BROADCAST_Arr.splice(index, 1);
		                		break;
		            		}
						}
		            }
		            
		            if (NETWORK_Arr.length > 0) {
		            	for (var index = NETWORK_Arr.length - 1; index >= 0; index--) {					
		                	var NETWORK_dClassIP = dClassIPHelper(NETWORK_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(NETWORK_dClassIP,10)) {
		                		if (className.includes(ipMapSettings.dhcpRangeCss)) {
		                    		if (NETWORK_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.dhcpRange_conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.dhcpRange_usedCss;
		    						}
								}
		                		else {
		                    		if (NETWORK_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.usedCss;
		    						}
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = NETWORK_Arr[index].ipaddr; 
		                		status = NETWORK_Arr[index].status;
		                		conflict = NETWORK_Arr[index].is_conflict;
		                		usage = NETWORK_Arr[index].usage;
		                		lease_state = NETWORK_Arr[index].lease_state;
		                		fingerprint = NETWORK_Arr[index].fingerprint;
		    	                NETWORK_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (ABANDONED_Arr.length > 0) {
		            	for (var index = ABANDONED_Arr.length - 1; index >= 0; index--) {					
		                	var ABANDONED_dClassIP = dClassIPHelper(ABANDONED_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(ABANDONED_dClassIP,10)) {
		                		if (className.includes(ipMapSettings.dhcpRangeCss)) {
		                    		if (ABANDONED_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.dhcpRange_conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.dhcpRange_unusedCss;
		                    			console.log(className);
		    						}
								}
		                		else {
		                    		if (ABANDONED_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.unused;
		    						}
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = ABANDONED_Arr[index].ipaddr; 
		                		status = ABANDONED_Arr[index].status;
		                		conflict = ABANDONED_Arr[index].is_conflict;
		                		usage = ABANDONED_Arr[index].usage;
		                		lease_state = ABANDONED_Arr[index].lease_state;
		                		fingerprint = ABANDONED_Arr[index].fingerprint;
		    	                ABANDONED_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (ACTIVE_Arr.length > 0) {
		            	for (var index = ACTIVE_Arr.length - 1; index >= 0; index--) {					
		                	var ACTIVE_dClassIP = dClassIPHelper(ACTIVE_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(ACTIVE_dClassIP,10)) {
		                		if (className.includes(ipMapSettings.dhcpRangeCss)) {
		                    		if (ACTIVE_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.dhcpRange_conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.dhcpRange_activeLeaseCss;
		                    			console.log(className);
		    						}
								}
		                		else {
		                    		if (ACTIVE_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.activeLeaseCss;
		    						}
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = ACTIVE_Arr[index].ipaddr; 
		                		status = ACTIVE_Arr[index].status;
		                		conflict = ACTIVE_Arr[index].is_conflict;
		                		usage = ACTIVE_Arr[index].usage;
		                		lease_state = ACTIVE_Arr[index].lease_state;
		                		fingerprint = ACTIVE_Arr[index].fingerprint;
		    	                ACTIVE_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (FREE_Arr.length > 0) {
		            	for (var index = FREE_Arr.length - 1; index >= 0; index--) {					
		                	var FREE_dClassIP = dClassIPHelper(FREE_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(FREE_dClassIP,10)) {
		                		if (className.includes(ipMapSettings.dhcpRangeCss)) {
		                    		if (FREE_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.dhcpRange_conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.dhcpRange_unusedCss;
		                    			console.log(className);
		    						}
								}
		                		else {
		                    		if (FREE_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.conflictCss;
		    						}
		                    		else {
		                    			className += ' ' + ipMapSettings.unusedCss;
		    						}
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = FREE_Arr[index].ipaddr; 
		                		status = FREE_Arr[index].status;
		                		conflict = FREE_Arr[index].is_conflict;
		                		usage = FREE_Arr[index].usage;
		                		lease_state = FREE_Arr[index].lease_state;
		                		fingerprint = FREE_Arr[index].fingerprint;
		    	                FREE_Arr.splice(index, 1);
		    	                break;
		    	            }
						}
		            }
		            
		            if (ETC_Arr.length > 0) {
		            	for (var index = ETC_Arr.length - 1; index >= 0; index--) {					
		                	var ETC_dClassIP = dClassIPHelper(ETC_Arr[index].ipaddr);
		
		                	if (ipNo == parseInt(ETC_dClassIP,10)) {
		                		if (className.includes(ipMapSettings.dhcpRangeCss)) {
		                    		if (ETC_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.dhcpRange_conflictCss;
		    						}
		                    		else if (ETC_Arr[index].status.toUpperCase() == "USED") {
		                    			className += ' ' + ipMapSettings.dhcpRange_usedCss;
		    						}
								}
		                		else {
		                    		if (ETC_Arr[index].is_conflict) {
		                    			className += ' ' + ipMapSettings.conflictCss;
		    						}
		                    		else if (ETC_Arr[index].status.toUpperCase() == "USED") {
		                    			className += ' ' + ipMapSettings.usedCss;
		    						}
								}
		                		className = className.replace(' ' + ipMapSettings.dhcpRangeCss, '').replace(' ' + ipMapSettings.unusedCss, '');
		                		address = ETC_Arr[index].ipaddr; 
		                		status = ETC_Arr[index].status;
		                		conflict = ETC_Arr[index].is_conflict;
		                		usage = ETC_Arr[index].usage;
		                		lease_state = ETC_Arr[index].lease_state;
		                		fingerprint = ETC_Arr[index].fingerprint;
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
	
	    //activeLeaseCss
	    if ($(obj).hasClass(ipMapSettings.activeLeaseCss)) {
	    	$(obj).removeClass(ipMapSettings.activeLeaseCss).addClass(ipMapSettings.activeLeaseCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_activeLeaseCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_activeLeaseCss).addClass(ipMapSettings.dhcpRange_activeLeaseCss+ "ORG").addClass(ipMapSettings.selected_activeLeaseCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.activeLeaseCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.activeLeaseCss+ "ORG").addClass(ipMapSettings.activeLeaseCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_activeLeaseCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_activeLeaseCss).removeClass(ipMapSettings.dhcpRange_activeLeaseCss+ "ORG").addClass(ipMapSettings.dhcpRange_activeLeaseCss);
	    }
	    
	    //conflictCss
	    else if ($(obj).hasClass(ipMapSettings.conflictCss)) {
	    	$(obj).removeClass(ipMapSettings.conflictCss).addClass(ipMapSettings.conflictCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_conflictCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_conflictCss).addClass(ipMapSettings.dhcpRange_conflictCss+ "ORG").addClass(ipMapSettings.selected_conflictCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.conflictCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.conflictCss+ "ORG").addClass(ipMapSettings.conflictCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_conflictCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_conflictCss).removeClass(ipMapSettings.dhcpRange_conflictCss+ "ORG").addClass(ipMapSettings.dhcpRange_conflictCss);
	    }
	    
	    //fixedCss
	    else if ($(obj).hasClass(ipMapSettings.fixedCss)) {
	    	$(obj).removeClass(ipMapSettings.fixedCss).addClass(ipMapSettings.fixedCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_fixedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_fixedCss).addClass(ipMapSettings.dhcpRange_fixedCss+ "ORG").addClass(ipMapSettings.selected_fixedaddressCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.fixedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.conflictCss+ "ORG").addClass(ipMapSettings.conflictCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_fixedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_fixedaddressCss).removeClass(ipMapSettings.dhcpRange_fixedCss+ "ORG").addClass(ipMapSettings.dhcpRange_fixedCss);
	    }
	    
	    //hostnotindnsCss
	    else if ($(obj).hasClass(ipMapSettings.hostnotindnsCss)) {
	    	$(obj).removeClass(ipMapSettings.hostnotindnsCss).addClass(ipMapSettings.hostnotindnsCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_hostnotindnsCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_hostnotindnsCss).addClass(ipMapSettings.dhcpRange_hostnotindnsCss+ "ORG").addClass(ipMapSettings.selected_hostnotindnsCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.hostnotindnsCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.hostnotindnsCss+ "ORG").addClass(ipMapSettings.hostnotindnsCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_hostnotindnsCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_hostnotindnsCss).removeClass(ipMapSettings.dhcpRange_hostnotindnsCss+ "ORG").addClass(ipMapSettings.dhcpRange_hostnotindnsCss);
	    }
	    
	    //dnsObjectCss
	    else if ($(obj).hasClass(ipMapSettings.dnsObjectCss)) {
	    	$(obj).removeClass(ipMapSettings.dnsObjectCss).addClass(ipMapSettings.dnsObjectCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_dnsObjectCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_dnsObjectCss).addClass(ipMapSettings.dhcpRange_dnsObjectCss+ "ORG").addClass(ipMapSettings.selected_dnsobjectCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dnsObjectCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.dnsObjectCss+ "ORG").addClass(ipMapSettings.dnsObjectCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_dnsObjectCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_dnsobjectCss).removeClass(ipMapSettings.dhcpRange_dnsObjectCss+ "ORG").addClass(ipMapSettings.dhcpRange_dnsObjectCss);
	    }
	    
	    //pendingCss
	    else if ($(obj).hasClass(ipMapSettings.pendingCss)) {
	    	$(obj).removeClass(ipMapSettings.pendingCss).addClass(ipMapSettings.pendingCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_pendingCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_pendingCss).addClass(ipMapSettings.dhcpRange_pendingCss+ "ORG").addClass(ipMapSettings.selected_pendingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.pendingCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.pendingCss+ "ORG").addClass(ipMapSettings.pendingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_pendingCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_pendingCss).removeClass(ipMapSettings.dhcpRange_pendingCss+ "ORG").addClass(ipMapSettings.dhcpRange_pendingCss);
	    }
	    
	    //unmanagedCss
	    else if ($(obj).hasClass(ipMapSettings.unmanagedCss)) {
	    	$(obj).removeClass(ipMapSettings.unmanagedCss).addClass(ipMapSettings.unmanagedCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_unmanagedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_unmanagedCss).addClass(ipMapSettings.dhcpRange_unmanagedCss+ "ORG").addClass(ipMapSettings.selected_unmanagedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.unmanagedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.unmanagedCss+ "ORG").addClass(ipMapSettings.unmanagedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_unmanagedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_unmanagedCss).removeClass(ipMapSettings.dhcpRange_unmanagedCss+ "ORG").addClass(ipMapSettings.dhcpRange_unmanagedCss);
	    }
	    
	    //unusedCss
	    else if ($(obj).hasClass(ipMapSettings.unusedCss)) {
	    	$(obj).removeClass(ipMapSettings.unusedCss).addClass(ipMapSettings.unusedCss+ "ORG").addClass(ipMapSettings.selectingCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_unusedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_unusedCss).addClass(ipMapSettings.dhcpRange_unusedCss+ "ORG").addClass(ipMapSettings.selected_unusedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.unusedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selectingCss).removeClass(ipMapSettings.unusedCss+ "ORG").addClass(ipMapSettings.unusedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_unusedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_unusedCss).removeClass(ipMapSettings.dhcpRange_unusedCss+ "ORG").addClass(ipMapSettings.dhcpRange_unusedCss);
	    }
	    
	    //usedCss
	    else if ($(obj).hasClass(ipMapSettings.usedCss)) {
	    	$(obj).removeClass(ipMapSettings.usedCss).addClass(ipMapSettings.usedCss+ "ORG").addClass(ipMapSettings.selected_usedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_usedCss)) {
	    	$(obj).removeClass(ipMapSettings.dhcpRange_usedCss).addClass(ipMapSettings.dhcpRange_usedCss+ "ORG").addClass(ipMapSettings.selected_usedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.usedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_usedCss).removeClass(ipMapSettings.usedCss+ "ORG").addClass(ipMapSettings.usedCss);
	    }
	    else if ($(obj).hasClass(ipMapSettings.dhcpRange_usedCss+ "ORG")) {
	    	$(obj).removeClass(ipMapSettings.selected_usedCss).removeClass(ipMapSettings.dhcpRange_usedCss+ "ORG").addClass(ipMapSettings.dhcpRange_usedCss);
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
function MapDataClass (ipaddr, macaddr, is_conflict, conflict_types, status, lease_state, obj_types, usage, host_name, host_os,
				 fingerprint, is_never_ends, is_never_start, lease_start_time, lease_end_time) {
    this.ipaddr = ipaddr;
    this.macaddr = macaddr;
    this.is_conflict = is_conflict;
    this.conflict_types = conflict_types;
    this.status = status;
    this.lease_state = lease_state;
    this.obj_types = obj_types;
    this.usage = usage;
    this.host_name = host_name;
    this.host_os = host_os;
    this.fingerprint = fingerprint;
    this.is_never_ends = is_never_ends;
    this.is_never_start = is_never_start;
    this.lease_start_time = lease_start_time;
    this.lease_end_time = lease_end_time;
}

function excelExport(){
	console.log("excelExport");
	console.log(table.dataTableSettings[0]);	
}