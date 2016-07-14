var table;
$(document).ready(function() {

	$("#layDiv").css("visibility","hidden");
//	table = $('#datatable');
	
//	$.ajax({
//		url : "/ipManagement/staticIPStatus_Segment_Select",
//		type : "POST",
//		data : "",
//		dataType : "text",
//		success : function(data) {
//			var jsonObj = eval("(" + data + ')');
//			if (jsonObj.result == true) {
//				
//				table.dataTable().fnClearTable();
//				table.dataTable().fnDestroy();				
//				table.dataTable( {
//				    data: jsonObj.data,
//				    columnDefs: [{ className: "essential-td-left", "targets": [ 0 ] },
//				                 { className: "essential-td-left", "targets": [ 1 ] }],
//				    columns: [
//	                   { data: "network"},
//	                   { data: "comment"},
//	                   { data: "utilization"},
//	                   { data: "site"}
//	               ],
//                "destroy" : false,
//                "paging" : true,
//                "searching" : true,
//                "lengthChange" : true,
////                "pageLength": 5,
//                "ordering" : true,
//                "info" : false,
//                "autoWidth" : false,
//                "processing" : true,
//                "serverSide" : false,
//                "order" : [ [ 1, 'desc' ] ]
//				} );
//			}
//		}
//	})
		
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
});

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
	
	$('#datatable_detail').DataTable(
            {
            	"scrollY": "auto",
//              "scrollCollapse": false,
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
}
