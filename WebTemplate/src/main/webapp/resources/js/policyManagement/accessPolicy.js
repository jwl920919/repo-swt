$("#layDiv").css("visibility", "hidden");
var table;
$(document)
        .ready(
                function() {
                    // alert screen 감추기
                    $("#site_eq").select2();
                    $("#vendor_eq").select2();
                    // policyManagement/getSiteNames"
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
                                                    "data" : "os_like",
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    "data" : "device_type_like",
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    "data" : "hostname_like",
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    "data" : "model_like",
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
                });
var access_policy_id, site_id, os_like, device_type_like, hostname_like, model_like;
function trClickEvent(clickedTr) {
    var tr = $(clickedTr).children();
    access_policy_id = tr.eq(9).text();
    site_id = tr.eq(10).text();
    os_like = tr.eq(11).text();
    device_type_like = tr.eq(12).text();
    hostname_like = tr.eq(13).text();
    model_like = tr.eq(14).text();
    // console.log(access_policy_id+"/"+site_id+"/"+os_like+"/"+device_type_like+"/"+hostname_like+"/"+model_like);
    $("#priority").val(tr.eq(1).text());
    $("#site_eq").val(site_id);
    $("#vendor").val(tr.eq(3).text());
    $("#model").val(tr.eq(4).text());
    $("#device-type").val(tr.eq(5).text());
    $("#os").val(tr.eq(6).text());
    $("#hostname").val(tr.eq(7).text());
    $("#desc").val(tr.eq(8).text());
    $("#policy").val(tr.eq(15).text());
    changeSiteNames(site_id);
    changeVendorNames();
    $("#select2-site_eq-container").text(tr.eq(2).text());
}

function changeSiteNames(index) {
    $.ajax({
        url : "policyManagement/getSiteNames",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#site_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (jsonObj.data[i].site_id == index) {
                        $('#site_eq').append(
                                '<option selected="selected" val='
                                        + jsonObj.data[i].site_id + '>'
                                        + jsonObj.data[i].site_name
                                        + '</option>');

                    } else {
                        $('#site_eq').append(
                                '<option val=' + jsonObj.data[i].site_id + '>'
                                        + jsonObj.data[i].site_name
                                        + '</option>');
                    }
                }
            }
        }
    });
}
function changeVendorNames() {
    $.ajax({
        url : "policyManagement/getVendor",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#vendor_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    $('#vendor_eq').append(
                            '<option>'
                                    + jsonObj.data[i].vendor + '</option>');
                }
            }
        }
    });
}
