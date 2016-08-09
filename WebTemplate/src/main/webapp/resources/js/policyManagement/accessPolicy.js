$("#layDiv").css("visibility", "hidden");
var accessPolicyTable;
$(document)
        .ready(
                function() {
                    // policyManagement/getSiteNames"
                    accessPolicyTable = $('#accessPolicyTable')
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
                                                    'render' : function(type,
                                                            row) {
                                                        return '<i class="fa fa-edit essential-cursor-pointer" onclick="modifyBtnClickEvent(this)" data-toggle="tooltip" title="" data-original-title="수정"></i>';
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
function modifyBtnClickEvent(t) {
    var $this = $(t);
    tr = $this.parent().parent().children();
    access_policy_id = tr.eq(9).text();
    site_id = tr.eq(10).text();
    os_like = tr.eq(11).text() == 'true' ? true : false;
    device_type_like = tr.eq(12).text() == 'true' ? true : false;
    hostname_like = tr.eq(13).text() == 'true' ? true : false;
    model_like = tr.eq(14).text() == 'true' ? true : false;
    site_name = tr.eq(2).text();
    vendor = tr.eq(3).text();
    model = tr.eq(4).text();
    device_type = tr.eq(5).text();
    os = tr.eq(6).text();
    hostname = tr.eq(7).text();
    createModifyPopup();
    modifyPopupSetting();
}
function createModifyPopup() {
    var html = '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">우선순위';
    html += '</span><div class="modal-content-body"><input type="text" id="priority" class="form-control modify-text" />';
    html += '</div></div><div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">';
    html += '사업장</span><div class="modal-content-body"><select id="site_eq" class="form-control select2"></select></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">벤더</span>';
    html += '<div class="modal-content-body"><select id="vendor_eq" class="form-control select2"></select></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">모델</span><div class="modal-content-body">';
    if (model_like) {
        html += '<input type="text" id="model-like" class="form-control modify-text" />';
    } else {
        html += '<select id="model_eq" class="form-control select2"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="model-mode-change" onclick="modelToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">장비종류</span><div class="modal-content-body">';

    if (device_type_like) {
        html += '<input type="text" id="device-type-like" class="form-control modify-text" />';
    } else {
        html += '<select id="device-type_eq" class="form-control select2"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="device-type-mode-change" onclick="deviceTypeToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">OS</span><div class="modal-content-body">';

    if (os_like) {
        html += '<input type="text" id="os-like" class="form-control modify-text" />';
    } else {
        html += '<select id="os_eq" class="form-control select2"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="os-mode-change" onclick="osToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">Hostname</span><div class="modal-content-body">';

    if (hostname_like) {
        html += '<input type="text" id="hostname-like" class="form-control modify-text" />';
    } else {
        html += '<select id="hostname_eq" class="form-control select2"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="hostname-mode-change" onclick="hostnameToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';

    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">설명</span>';
    html += '<div class="modal-content-body"><input type="text" id="desc" class="form-control modify-text" /></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">정책</span>';
    html += '<div class="modal-content-body"><select class="form-control select2" id="policy"><option>Permit</option>';
    html += '<option>Deny</option></select></div></div>';
    $("#modify-body").html(html);
    selectInit();
}
function modifyPopupSetting() {
    $("#priority").val(tr.eq(1).text());
    $("#site_eq").val(site_id);
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
    if (!model_like)
        $("#model_eq").select2();
    if (!device_type_like)
        $("#device-type_eq").select2();
    if (!os_like)
        $("#os_eq").select2();
    if (!hostname_like)
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
                                '<option selected="selected" value='
                                        + jsonObj.data[i].site_id + '>'
                                        + jsonObj.data[i].site_name
                                        + '</option>');

                    } else {
                        $('#site_eq').append(
                                '<option value=' + jsonObj.data[i].site_id
                                        + '>' + jsonObj.data[i].site_name
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
                            if (vendor == jsonObj.data[i].vendor) {
                                $('#vendor_eq').append(
                                        '<option selected="selected">'
                                                + jsonObj.data[i].vendor
                                                + '</option>');
                            } else {
                                $('#vendor_eq').append(
                                        '<option>' + jsonObj.data[i].vendor
                                                + '</option>');
                            }
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
                    if (hostname == jsonObj.data[i].hostname)
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

fnShowEvent = function() {
    if (popupClass == "add") {
        console.log("add");
    } else if (popupClass == "modify") {
        console.log("modify");
    }
    popupClass = "";
}

function modelToggleEvent($this) {
    if (model_like) {
        removeSiblings($this);
        prependCodeToParent($this,
                '<select id="model_eq" class="form-control select2"></select>');
        changeModelNames(vendor);
        $("#model_eq").select2();
        $("#select2-model_eq-container").text(model);
        model_like = false;
    } else {
        removeSiblings($this);
        prependCodeToParent($this,
                '<input type="text" id="model-like" class="form-control modify-text" />');
        model_like = true;
    }
}
function deviceTypeToggleEvent($this) {
    if (device_type_like) {
        removeSiblings($this);
        prependCodeToParent($this,
                '<select id="device-type_eq" class="form-control select2"></select>');
        changeDeviceType();
        $("#device-type_eq").select2();
        $("#select2-device-type_eq-container").text(device_type);
        device_type_like = false;
    } else {
        removeSiblings($this);
        prependCodeToParent($this,
                '<input type="text" id="device-type-like" class="form-control modify-text" />');
        device_type_like = true;
    }
}

function osToggleEvent($this) {
    if (os_like) {
        removeSiblings($this);
        prependCodeToParent($this,
                '<select id="os_eq" class="form-control select2"></select>');
        changeOs();
        $("#os_eq").select2();
        $("#select2-os_eq-container").text(os);
        os_like = false;
    } else {
        removeSiblings($this);
        prependCodeToParent($this,
                '<input type="text" id="os-like" class="form-control modify-text" />');
        os_like = true;
    }
}

function hostnameToggleEvent($this) {
    if (hostname_like) {
        removeSiblings($this);
        prependCodeToParent($this,
                '<select id="hostname_eq" class="form-control select2"></select>');
        changeHostname();
        $("#hostname_eq").select2();
        $("#select2-hostname_eq-container").text(hostname);
        hostname_like = false;
    } else {
        removeSiblings($this);
        prependCodeToParent($this,
                '<input type="text" id="hostname-like" class="form-control modify-text" />');
        hostname_like = true;
    }
}

function removeSiblings(obj) {
    var siblings = $(obj).siblings();
    for (i = 0; i < siblings.length; i++) {
        $(siblings[i]).remove();
    }
}

function prependCodeToParent(obj, code) {
    var parent = $(obj).parent();
    parent.prepend(code);
}

$('#modify-save-btn').click(function() {
    var jObj = new Object();
    jObj.access_policy_id = access_policy_id;
    jObj.priority = $('#priority').val();
    jObj.site_id = $('#site_eq').val();
    jObj.vendor = $('#vendor_eq').val();
    if (model_like) {
        jObj.model = $('#model-like').val();
    } else {
        jObj.model = $('#select2-model_eq-container').text();
    }
    if (device_type_like) {
        jObj.device_type = $('#device-type-like').val();
    } else {
        jObj.device_type = $('#select2-device-type_eq-container').text();
    }
    if (os_like) {
        jObj.os = $('#os-like').val();
    } else {
        jObj.os = $('#select2-os_eq-container').text();
    }
    if (hostname_like) {
        jObj.hostname = $('#hostname-like').val();
    } else {
        jObj.hostname = $('#select2-hostname_eq-container').text();
    }
    jObj.desc = $('#desc').val();
    jObj.is_permit = ($('#policy').val().toLowerCase()=="permit");
    jObj.model_like = model_like;
    jObj.device_type_like = device_type_like;
    jObj.os_like = os_like;
    jObj.hostname_like = hostname_like;
    console.log(jObj);
    $.ajax({
        url : "policyManagement/access_policy_modify",
        type : "POST",
        dataType : "text",
        data : JSON.stringify(jObj),
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                accessPolicyTable.ajax.reload();
            }
            modalClose("modify-modal");
        }
    });
});

//체크박스 전체선택
$('#accessPolicyTable_checkbox_controller').click(function() {
    if ($(this).is(':checked')) {
        $('#accessPolicyTable>tbody>tr>td>input:checkbox').each(function() {
            this.checked = true;
        });
    } else {
        $('#accessPolicyTable>tbody>tr>td>input:checkbox').each(function() {
            this.checked = false;
        });
    }
});
