var table;
$(document).ready(function() {
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
                        //console.log(data.search_key);
                    }
                },
			    "columnDefs": [{ className: "essential-td-left", "targets": [ 0 ] },
			                 { className: "essential-td-left", "targets": [ 1 ] }],
                "order" : [ [ 1, 'asc' ] ],
                "columns" : [ {"data" : "network"},
                              {"data" : "comment"},
                              {"data" : "utilization"},
                              {"data" : "site"}, ],
            });
	
	
	
	
	
	
	
	
	
	$(function() {
	    var d_wrap = $('#datatable_wrapper .row:first');
	    var d_length = $('#datatable_wrapper .row:first .col-sm-6:eq(0)');
	    var d_filter = $('#datatable_wrapper .row:first .col-sm-6:eq(1)');
	    d_length.append(d_filter);
	    d_wrap.prepend(d_filter);
	});
});
