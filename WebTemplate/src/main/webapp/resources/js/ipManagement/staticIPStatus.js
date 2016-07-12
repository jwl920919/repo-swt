var table;
$(document).ready(function() {

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
                                "data" : function(data) {
                                    data.search_key = data.search.value;
                                }
                            },
                            'columnDefs' : [ {
                                'targets' : 0,
                                'data' : "active",
                                'searchable' : false,
                                'orderable' : false,
                                'width' : '1%',
                                'className' : "dt-body-center",
                                'render' : function(data, type, row) {
//                                    if (type === 'display') {
//                                        return '<input type="checkbox" name="checkbox-active" class="editor-active">';
//                                    }
//                                    return data;
                                }
                            } ],
                            'order' : [ [ 1, 'asc' ] ],
                            "columns" : [ {},
										  {"data" : "network"},
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
