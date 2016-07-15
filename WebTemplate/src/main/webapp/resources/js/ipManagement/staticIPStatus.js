$(document).ready(function() {
console.log($('#datatable'));
	$("#layDiv").css("visibility","hidden");		
	$('#datatable').DataTable(
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
			    "columnDefs": [{ className: "essential-td-display_none", "targets": [ 0 ] },
			                   { className: "essential-td-left essential-td-cursor-pointer", "targets": [ 1 ] },
			                   { className: "essential-td-left", "targets": [ 2 ] }],
                "order" : [ [ 1, 'asc' ] ],
                "columns" : [ {"data" : "seq"},
                              {"data" : "network"},
                              {"data" : "comment"},
                              {"data" : "utilization"},
                              {"data" : "site"}, ],
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
	//$('#datatable').delegate('tbody>tr>td:first-child', 'click', function() {
	$('#datatable').delegate('tbody>tr>td:nth-child(2)', 'click', function() {
		//console.log("td click event : " + this);
	    tdClickEvent(this);
	});
	
//	fnIPMapSetting();
//	var varr = [5, 10, 25, 31, 43, 63];
//	fnIPMapInit(varr,varr,varr,varr,varr,varr,varr,varr,varr,varr,varr,varr);
});

/**
 * tr 이벤트 핸들러
**/
function trClickEvent (obj){
	return false;
}

/**
 * td 이벤트 핸들러
**/
function tdClickEvent(obj){
	//systemAlert("divAlertArea", "alert-danger", getLanguage("warning"), $(obj).html());
	//alert($(obj).html().trim());

	var segmentid = $(obj).parent().children().html();
	
	$("#defaultDiv").css("display","none");
	$("#detailDiv").css("display","block");
	$("#selectSegment").text($(obj).html().trim());

	$('#datatable_detail').DataTable(
            {
                "destroy" : true,
                "paging" : true, //"paging" : true,
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
                        data.segmentid = segmentid;
                        //console.log(data.search_key);
                    }
                },
			    "columnDefs": [{ className: "essential-td-left", "targets": [ 0 ] },
			                   { className: "essential-td-left", "targets": [ 1 ] },
			                   { className: "essential-td-left", "targets": [ 2 ] },
			                   { className: "essential-td-left", "targets": [ 4 ] },
			                   { className: "essential-td-left", "targets": [ 5 ] }],
                "order" : [ [ 0, 'asc' ] ],
                "columns" : [ {"data" : "ip"},
                              {"data" : "name"},
                              {"data" : "mac"},
                              {"data" : "status"},
                              {"data" : "type"},
                              {"data" : "client"} ],
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
	mapDataCall(segmentid);
}

/**
 * IP Map Setting
**/
var ipMapSettings;
function fnIPMapSetting(){
    ipMapSettings = {
        rows: 32,
        cols: 8,
        rowCssPrefix: 'row-',
        colCssPrefix: 'col-',
        rectangleWidth: 17,
        rectangleHeight: 17,
        rectangleCss: 'rectangle',
        activeLeaseCss: 'activeLease',
        conflictCss: 'conflict',
        exclusionCss: 'exclusion',
        fixedCss: 'fixed',
        hostnotindnsCss: 'hostnotindns',
        objectCss: 'object',
        pendingCss: 'pending',
        rangeCss: 'range',
        reservedrangeCss: 'reservedrange',
        unmanagedCss: 'unmanaged',
        unusedCss: 'unused',
        usedCss: 'used'  
    };
}

/**
 * IP Map Initialize
**/
var chargePerSheet;
function fnIPMapInit (ActiveLeaseArr, ConflictArr, ExclusionArr, FixedArr, HostnotindnsArr, ObjectArr, 
		PendingArr,	RangeArr, ReservedrangeArr, UnmanagedArr, UnusedArr, UsedArr) {
    var str = [], ipNo = 0, className;
    for (i = 0; i < ipMapSettings.rows; i++) {
        for (j = 0; j < ipMapSettings.cols; j++) {
            //ipNo = (i + j * ipMapSettings.rows + 1);
            ipNo += 1;
            className = ipMapSettings.rectangleCss + ' ' + ipMapSettings.rowCssPrefix + i.toString() + ' ' + ipMapSettings.colCssPrefix + j.toString();
            
            if ($.isArray(ActiveLeaseArr) && $.inArray(ipNo, ActiveLeaseArr) != -1) {
                className += ' ' + ipMapSettings.activeLeaseCss;
            }
            if ($.isArray(ConflictArr) && $.inArray(ipNo, ConflictArr) != -1) {
                className += ' ' + ipMapSettings.conflictCss;
            }
            if ($.isArray(ExclusionArr) && $.inArray(ipNo, ExclusionArr) != -1) {
                className += ' ' + ipMapSettings.exclusionCss;
            }
            if ($.isArray(FixedArr) && $.inArray(ipNo, FixedArr) != -1) {
                className += ' ' + ipMapSettings.fixedCss;
            }
            if ($.isArray(HostnotindnsArr) && $.inArray(ipNo, HostnotindnsArr) != -1) {
                className += ' ' + ipMapSettings.hostnotindnsCss;
            }
            if ($.isArray(ObjectArr) && $.inArray(ipNo, ObjectArr) != -1) {
                className += ' ' + ipMapSettings.objectCss;
            }
            if ($.isArray(PendingArr) && $.inArray(ipNo, PendingArr) != -1) {
                className += ' ' + ipMapSettings.pendingCss;
            }
            if ($.isArray(RangeArr) && $.inArray(ipNo, RangeArr) != -1) {
                className += ' ' + ipMapSettings.rangeCss;
            }
            if ($.isArray(ReservedrangeArr) && $.inArray(ipNo, ReservedrangeArr) != -1) {
                className += ' ' + ipMapSettings.reservedrangeCss;
            }
            if ($.isArray(UnmanagedArr) && $.inArray(ipNo, UnmanagedArr) != -1) {
                className += ' ' + ipMapSettings.unmanagedCss;
            }
            if ($.isArray(UnusedArr) && $.inArray(ipNo, UnusedArr) != -1) {
                className += ' ' + ipMapSettings.unusedCss;
            }
            if ($.isArray(UsedArr) && $.inArray(ipNo, UsedArr) != -1) {
                className += ' ' + ipMapSettings.usedCss;
            }

            str.push('<li class="' + className + '"' +
                          'style="top:' + (i * ipMapSettings.rectangleHeight).toString() + 'px;left:' + (j * ipMapSettings.rectangleWidth).toString() + 'px" align=center>' +
                          '<a title="' + ipNo + '" style=\"line-height:50px\">' + ipNo + '</a>' +
                          '</li>');
        }
    }
    $('#place').html(str.join(''));
    //chargePerSheet = $('#<%=txtAmount.ClientID %>').val();
};

function mapDataCall(segmentid){
	var jObj = Object();
    jObj.segmentid = segmentid;
    $.ajax({
        url : 'ipManagement/staticIPStatus_Segment_MapData',
        type : "POST",
        data : JSON.stringify(jObj),
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            
            $.each(data.result, function (index, obj) {
                console.log("obj.used : " + used);
            });
        }
    });
}



