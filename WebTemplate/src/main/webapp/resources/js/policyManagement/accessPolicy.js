$("#layDiv").css("visibility", "hidden");
var table;
$(document)
        .ready(
                function() {
                    // alert screen 감추기

                    table = $('#accessPolicyTable')
                            .DataTable(
                                    {
                                        "destroy" : true,
                                        "paging" : true,
                                        "searching" : false,
                                        "lengthChange" : true,
                                        "ordering" : true,
                                        "info" : false,
                                        "autoWidth" : true,
                                        "processing" : true,
                                        "serverSide" : true,
                                        "ajax" : {
                                            url : 'policyManagement/getAccessPolicyTableDatas',
                                            "dataType" : "jsonp",
                                            "type" : "POST",
                                            "jsonp" : "callback",
                                            "data" : function(data, type) {
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
                                                if (type === 'display') {
                                                    return '<input type="checkbox" name="checkbox-active" class="editor-active">';
                                                }
                                                return data;
                                            }
                                        } ],
                                        'order' : [ [ 1, 'asc' ] ],
                                        "columns" : [
                                                {},
                                                {
                                                    "data" : "access_policy_id"
                                                },
                                                {
                                                    "data" : "site_name"
                                                },
                                                {
                                                    "data" : "vendor"
                                                },
                                                {
                                                    "data" : "model"
                                                },
                                                {
                                                    "data" : "device_type"
                                                },
                                                {
                                                    "data" : "os"
                                                },
                                                {
                                                    "data" : "hostname"
                                                },
                                                {
                                                    "data" : "desc",
                                                    'searchable' : false,
                                                    'orderable' : false
                                                },
                                                {
                                                    "width" : "100px",
                                                    "data" : "is_permit",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'render' : function(data,
                                                            type, row) {
                                                        if (data)
                                                            return '<span class="permit">Permit</span>';
                                                        else
                                                            return '<span class="deny">Deny</span>';
                                                    }
                                                }, {
                                                    "data" : "site_id",
                                                    "visible" : false
                                                } ]
                                    });
                    $(function() {
                        var d_wrap = $('#accessPolicyTable_wrapper .row:first');
                        var d_length = $('#accessPolicyTable_wrapper .row:first .col-sm-6:eq(0)');
                        var d_filter = $('#accessPolicyTable_wrapper .row:first .col-sm-6:eq(1)');
                        d_length.append(d_filter);
                        d_wrap.prepend(d_filter);
                        // 삭제버튼 위치변경
                        $('#accessPolicyTable_paginate').parent().prepend(
                                $('#delete-button').parent());
                    });

                });

function trClickEvent(clickedTr) {
    console.log('TTT!!');
}
