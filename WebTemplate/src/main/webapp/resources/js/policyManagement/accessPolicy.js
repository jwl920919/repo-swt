$("#layDiv").css("visibility", "hidden");
var table;
$(document)
        .ready(
                function() {
                    selectInit();
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
                                                },
                                                {
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'render' : function(type, row) {
                                                        return '<i class="fa fa-edit essential-cursor-pointer" onclick="createModifyPopup()" data-toggle="tooltip" title="" data-original-title="수정"></i>';
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

var access_policy_id, site_id, site_name, vendor, device_type, os, hostname, os_like, device_type_like, hostname_like, model_like, tr;
function trClickEvent(clickedTr) {
    tr = $(clickedTr).children();
}

function createModifyPopup() {
    access_policy_id = tr.eq(9).text();
    site_id = tr.eq(10).text();
    os_like = tr.eq(11).text();
    device_type_like = tr.eq(12).text();
    hostname_like = tr.eq(13).text();
    model_like = tr.eq(14).text();
    // console.log(access_policy_id+"/"+site_id+"/"+os_like+"/"+device_type_like+"/"+hostname_like+"/"+model_like);
    $("#priority").val(tr.eq(1).text());
    $("#site_eq").val(site_id);
    site_name = tr.eq(2).text();
    vendor = tr.eq(3).text();
    model = tr.eq(4).text();
    device_type = tr.eq(5).text();
    os = tr.eq(6).text();
    hostname = tr.eq(7).text();
    $("#desc").val(tr.eq(8).text());
    $("#policy").val(tr.eq(15).text());
    changeSiteNames(site_id);
    changeVendorNames();
    changeModelNames(vendor);
    changeDeviceType();
    changeOs();
    changeHostname();
    $("#select2-site_eq-container").text(site_name);
    $("#select2-vendor_eq-container").text(vendor);
    $("#select2-model_eq-container").text(model);
    $("#select2-device-type_eq-container").text(device_type);
    $("#select2-os_eq-container").text(os);
    $("#select2-hostname_eq-container").text(hostname);
    popupClass = "modify";
    modalShow("modify-modal");
}
// init default select to select2 object
function selectInit() {
    $("#site_eq").select2();
    $("#vendor_eq").select2();
    $("#model_eq").select2();
    $("#device-type_eq").select2();
    $("#os_eq").select2();
    $("#hostname_eq").select2();
    $("#policy").select2();
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
    $
            .ajax({
                url : "policyManagement/getVendor",
                type : "POST",
                success : function(data) {
                    var jsonObj = eval("(" + data + ')');
                    if (jsonObj.result == true) {
                        $('#vendor_eq').find('option').remove().end();
                        for (var i = 0; i < jsonObj.data.length; i++) {
                            if (vendor == jsonObj.data[i].vendor)
                                $('#vendor_eq').append(
                                        '<option selected="selected">'
                                                + jsonObj.data[i].vendor
                                                + '</option>');
                            else
                                $('#vendor_eq').append(
                                        '<option>' + jsonObj.data[i].vendor
                                                + '</option>');
                        }
                    }
                }
            });
}
function changeModelNames(v) {
    var obj = new Object();
    obj.vendor = v;
    $.ajax({
        url : "policyManagement/getModel",
        type : "POST",
        data : JSON.stringify(obj),
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#model_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (model == jsonObj.data[i].model)
                        $('#model_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].model + '</option>');
                    else
                        $('#model_eq').append(
                                '<option>' + jsonObj.data[i].model
                                        + '</option>');
                }
            }
        }
    });
}
function changeDeviceType() {
    $.ajax({
        url : "policyManagement/getDeviceType",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#device-type_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (device_type == jsonObj.data[i].device_type)
                        $('#device-type_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].device_type
                                        + '</option>');
                    else
                        $('#device-type_eq').append(
                                '<option>' + jsonObj.data[i].device_type
                                        + '</option>');
                }
            }
        }
    });
}
function changeOs() {
    $.ajax({
        url : "policyManagement/getOs",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#os_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (os == jsonObj.data[i].os)
                        $('#os_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].os + '</option>');
                    else
                        $('#os_eq').append(
                                '<option>' + jsonObj.data[i].os + '</option>');
                }
            }
        }
    });
}
function changeHostname() {
    $.ajax({
        url : "policyManagement/getHostname",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#hostname_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (os == jsonObj.data[i].hostname)
                        $('#hostname_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].hostname
                                        + '</option>');
                    else
                        $('#hostname_eq').append(
                                '<option>' + jsonObj.data[i].hostname
                                        + '</option>');
                }
            }
        }
    });
}

fnShowEvent = function(){
    if (popupClass == "add") {
            console.log("add");
    }
    else if (popupClass == "modify") {
            console.log("modify");
    }
    popupClass = "";
}
