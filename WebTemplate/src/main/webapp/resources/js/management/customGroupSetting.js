var table;
$(document)
        .ready(
                function() {
                    // alert screen 감추기

                    table = $('#ip-table')
                            .DataTable(
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
                                            url : '/management/getIpTableDatas',
                                            "dataType" : "jsonp",
                                            "type" : "POST",
                                            "jsonp" : "callback",
                                            "data" : function(data, type) {
                                                data.search_key = data.search.value;
                                            }
                                        },
                                        'order' : [ [ 0, 'asc' ] ],
                                        "columns" : [ {
                                            "data" : "network"
                                        }, {
                                            "data" : "group_name"
                                        }]
                                    });
                    $(function() {
                        var d_wrap = $('#ip-table_wrapper .row:first');
                        var d_length = $('#ip-table_wrapper .row:first .col-sm-6:eq(0)');
                        $('#ip-table_length').css("margin-right","0px");
                        var d_filter = $('#ip-table_wrapper .row:first .col-sm-6:eq(1)');
                        $('#ip-table_filter').css("margin-left","0px");
                        d_length.append(d_filter);
                        d_wrap.prepend(d_filter);
                    });
                });


function trClickEvent(tr) {
    console.log(tr);
}