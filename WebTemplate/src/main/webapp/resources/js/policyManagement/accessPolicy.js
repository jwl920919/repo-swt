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
                                                    "data" : "priority"
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
                                                    "data" : "access_policy_id",
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    "data" : "site_id",
                                                    'className' : 'hidden'
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
                    
                    changeSiteNames()

                });
var access_policy_id,site_id;
function trClickEvent(clickedTr) {
    var test = $(clickedTr).children();
    access_policy_id =test.eq(9).text();
    site_id = test.eq(10).text();
    $("#priority").val(test.eq(1).text());
    $("#site").val(site_id);
    $("#vendor").val(test.eq(3).text());
    $("#model").val(test.eq(4).text());
    $("#device-type").val(test.eq(5).text());
    $("#os").val(test.eq(6).text());
    $("#hostname").val(test.eq(7).text());
    $("#desc").val(test.eq(8).text());
    $("#policy").val(test.eq(11).text());
    
}

function changeSiteNames() {
    $.ajax({
                url : "policyManagement/getSiteNames",
                type : "POST",
                success : function(data) {
                    var jsonObj = eval("(" + data + ')');
                    if (jsonObj.result == true) {
                        $('#site').find('option').remove().end();
                        for (var i = 0; i < jsonObj.data.length; i++) {
                            $('#site').append(
                                    '<option value=' + jsonObj.data[i].site_id
                                            + '>' + jsonObj.data[i].site_name
                                            + '</option>');
                        }
                    }
                }
            });
}
