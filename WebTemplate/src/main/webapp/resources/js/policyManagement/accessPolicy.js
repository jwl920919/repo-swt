//$("#layDiv").css("visibility", "hidden");
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
                                        'columnDefs' : [
                                                {
                                                    'targets' : 0,
                                                    'data' : "active",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'width' : '1%',
                                                    'className' : "dt-body-center",
                                                    'render' : function(data,
                                                            type, row) {
                                                        if (type === 'display') {
                                                            return '<input type="checkbox" name="checkbox-active" class="editor-active">';
                                                        }
                                                        return data;
                                                    }
                                                },
                                                {
                                                    'targets' : 1,
                                                    "data" : "priority"
                                                },
                                                {
                                                    'targets' : 2,
                                                    "data" : "site_name"
                                                },
                                                {
                                                    'targets' : 3,
                                                    "data" : "vendor"
                                                },
                                                {
                                                    'targets' : 4,
                                                    "data" : "model"
                                                },
                                                {
                                                    'targets' : 5,
                                                    "data" : "device_type"
                                                },
                                                {
                                                    'targets' : 6,
                                                    "data" : "os"
                                                },
                                                {
                                                    'targets' : 7,
                                                    "data" : "hostname"
                                                },
                                                {
                                                    'targets' : 8,
                                                    "data" : "desc",
                                                    'searchable' : false,
                                                    'orderable' : false
                                                },
                                                {
                                                    'targets' : 9,
                                                    "data" : "access_policy_id",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 10,
                                                    "data" : "site_id",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 11,
                                                    "data" : "os_like",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 12,
                                                    "data" : "device_type_like",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 13,
                                                    "data" : "hostname_like",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 14,
                                                    "data" : "model_like",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 15,
                                                    "data" : "vendor_like",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'targets' : 16,
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
                                                    'targets' : 17,
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'render' : function(type,
                                                            row) {
                                                        return '<i class="fa fa-edit essential-cursor-pointer" onclick="modifyBtnClickEvent(this)" data-toggle="tooltip" title="" data-original-title="수정"></i>';
                                                    }
                                                } ],
                                        'order' : [ [ 1, 'asc' ] ]
                                    });
                    $(function() {
                        var d_wrap = $('#accessPolicyTable_wrapper .row:first');
                        var d_length = $('#accessPolicyTable_wrapper .row:first .col-sm-6:eq(0)');
                        var d_filter = $('#accessPolicyTable_wrapper .row:first .col-sm-6:eq(1)');
                        d_length.append(d_filter);
                        d_wrap.prepend(d_filter);
                        // 테이블 하단 페이지 부분 좌우에 버튼 위치 지정
                        $accessPolicyTable_paginate_parent = $(
                                '#accessPolicyTable_paginate').parent();
                        $('#page-box').append($('#accessPolicyTable_paginate'));
                        $accessPolicyTable_paginate_parent
                                .append($('#bottom-btn-row'));
                        $('#bottom-btn-row').parent().removeClass();
                        $('#bottom-btn-row').parent().addClass("col-sm-12");
                    });
                });
/** 추가 변경을 위한 변수 * */
var access_policy_id, site_id, site_name, vendor, device_type, os, hostname, os_like, device_type_like, hostname_like, model_like, vendor_like, tr;
/** 테이블 row click evt * */
function trClickEvent(clickedTr) {
    tr = $(clickedTr).children();
}

/** 변경 * */
function modifyBtnClickEvent(t) {
    var $this = $(t);
    tr = $this.parent().parent().children();
    access_policy_id = tr.eq(9).text();
    site_id = tr.eq(10).text();
    os_like = tr.eq(11).text() == 'true' ? true : false;
    device_type_like = tr.eq(12).text() == 'true' ? true : false;
    hostname_like = tr.eq(13).text() == 'true' ? true : false;
    vendor_like = tr.eq(15).text() == 'true' ? true : false;
    model_like = tr.eq(14).text() == 'true' ? true : false;
    site_name = tr.eq(2).text();
    vendor = tr.eq(3).text();
    model = tr.eq(4).text();
    device_type = tr.eq(5).text();
    os = tr.eq(6).text();
    hostname = tr.eq(7).text();
    createModifyPopup(function() {
        modifyPopupSetting();
    });
}

function createModifyPopup(callback) {
    var html = '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">우선순위';
    html += '</span><div class="modal-content-body"><input type="text" id="priority" class="form-control modify-text" />';
    html += '</div></div><div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">';
    html += '사업장</span><div class="modal-content-body"><select id="site_eq" class="form-control select2"></select></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">벤더</span><div class="modal-content-body">';

    if (vendor_like) {
        html += '<input type="text" id="vendor-like" class="form-control modify-text" />';
    } else {
        html += '<select id="vendor_eq" class="form-control select2"></select>';
    }
    html += '<a class="custom-btn custom-btn-app" id="vendor-mode-change" onclick="vendorToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">모델</span><div class="modal-content-body">';
    if (model_like) {
        html += '<input type="text" id="model-like" class="form-control modify-text" />';
    } else {
        html += '<select id="model_eq" class="form-control select2" style="display: none;"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="model-mode-change" onclick="modelToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">장비종류</span><div class="modal-content-body">';

    if (device_type_like) {
        html += '<input type="text" id="device-type-like" class="form-control modify-text" />';
    } else {
        html += '<select id="device-type_eq" class="form-control select2" style="display: none;"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="device-type-mode-change" onclick="deviceTypeToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">OS</span><div class="modal-content-body">';

    if (os_like) {
        html += '<input type="text" id="os-like" class="form-control modify-text" />';
    } else {
        html += '<select id="os_eq" class="form-control select2" style="display: none;"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="os-mode-change" onclick="osToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">Hostname</span><div class="modal-content-body">';

    if (hostname_like) {
        html += '<input type="text" id="hostname-like" class="form-control modify-text" />';
    } else {
        html += '<select id="hostname_eq" class="form-control select2" style="display: none;"></select>';
    }

    html += '<a class="custom-btn custom-btn-app" id="hostname-mode-change" onclick="hostnameToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';

    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">설명</span>';
    html += '<div class="modal-content-body"><input type="text" id="desc" class="form-control modify-text" /></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">정책</span>';
    html += '<div class="modal-content-body"><select class="form-control select2" id="policy"><option>Permit</option>';
    html += '<option>Deny</option></select></div></div>';
    $("#modify-body").html(html);
    selectInit(function() {
        if (typeof callback === 'function') {
            callback();
        }
    });
}

function modifyPopupSetting() {
    $("#priority").val(tr.eq(1).text());
    $("#site_eq").val(site_id);
    $("#desc").val(tr.eq(8).text());
    $("#policy").val(tr.eq(16).text());
    changeSiteNames(site_id, function() {
        changeVendorNames(function() {
            if (vendor_like) {
                $("#vendor-like").val(vendor);
            } else {
                var model_length = $("#vendor_eq").length;
                if (model_length > 1) {
                    $("#select2-vendor_eq-container").text(model);
                } else if (model_length == 1) {
                    $("#select2-vendor_eq-container").text(
                            $("#vendor_eq option:selected").val())
                }
            }
            changeModelNames(function() {
                if (model_like) {
                    $("#model-like").val(model);
                } else {
                    var model_length = $("#model_eq").length;
                    if (model_length > 1) {
                        $("#select2-model_eq-container").text(model);
                    } else if (model_length == 1) {
                        $("#select2-model_eq-container").text(
                                $("#model_eq option:selected").val())
                    }
                }
                changeDeviceType(function() {
                    if (device_type_like) {
                        $("#device-type-like").val(device_type);
                    } else {
                        var device_type_length = $("#device-type_eq").length;
                        if (device_type_length > 1) {
                            $("#select2-device-type_eq-container").text(
                                    device_type);
                        } else if (device_type_length == 1) {
                            $("#select2-device-type_eq-container").text(
                                    $("#device-type_eq option:selected").val())
                        }
                    }
                    changeOs(function() {
                        if (os_like) {
                            $("#os-like").val(os);
                        } else {
                            var os_length = $("#os_eq").length;
                            if (os_length > 1) {
                                $("#select2-os_eq-container").text(os);
                            } else if (os_length == 1) {
                                $("#select2-os_eq-container").text(
                                        $("#os_eq option:selected").val())
                            }
                        }
                        changeHostname(function() {
                            if (hostname_like) {
                                $("#hostname-like").val(hostname);
                            } else {
                                var hostname_length = $("#hostname_eq").length;
                                if (hostname_length > 1) {
                                    $("#select2-hostname_eq-container").text(
                                            hostname);
                                } else if (hostname_length == 1) {
                                    $("#select2-hostname_eq-container").text(
                                            $("#hostname_eq option:selected")
                                                    .val())
                                }
                            }
                        });
                    });
                });
            });
        });
    });

    $("#select2-site_eq-container").text(site_name);
    // $("#select2-vendor_eq-container").text(vendor);

    popupClass = "modify";
    modalShow("modify-modal", function() {
        $('#modify-body').html('');
    });
}

$('#modify-save-btn').click(function() {
    var jObj = new Object();
    jObj.access_policy_id = access_policy_id;
    jObj.priority = $('#priority').val();
    jObj.site_id = $('#site_eq').val();
    if (vendor_like) {
        jObj.vendor = $('#vendor-like').val();
    } else {
        jObj.vendor = $('#select2-vendor_eq-container').text();
    }
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
    jObj.is_permit = ($('#policy').val().toLowerCase() == "permit");
    jObj.model_like = model_like;
    jObj.vendor_like = vendor_like;
    jObj.device_type_like = device_type_like;
    jObj.os_like = os_like;
    jObj.hostname_like = hostname_like;
    $.ajax({
        url : "policyManagement/accessPolicyModify",
        type : "POST",
        dataType : "text",
        data : JSON.stringify(jObj),
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                accessPolicyTable.ajax.reload();
            }
            modalClose("modify-modal", function() {
                $('#modify-body').html('');
            });
        }
    });
});
/** 추가 * */
$('#add-button')
        .click(
                function() {
                    createAddPopup(function() {
                        popupClass = "add";
                        $("#priority").val(100);
                        $("#desc").val('');
                        $("#policy").val('Permit');
                        site_id = '';
                        site_name = '';
                        vendor = '';
                        model = '';
                        device_type = '';
                        os = '';
                        hostname = '';
                        changeSiteNames(
                                '',
                                function() {
                                    if ($("#site_eq").length == 1) {
                                        $("#select2-site_eq-container").text(
                                                $("#site_eq option:selected")
                                                        .text());
                                    }
                                    changeVendorNames(function() {
                                        if ($("#vendor_eq").length == 1) {
                                            $("#select2-vendor_eq-container")
                                                    .text(
                                                            $(
                                                                    "#vendor_eq option:selected")
                                                                    .val());
                                        }
                                        changeModelNames(function() {
                                            if ($("#model_eq").length == 1) {
                                                $("#select2-model_eq-container")
                                                        .text(
                                                                $(
                                                                        "#model_eq option:selected")
                                                                        .val());
                                            }
                                            changeDeviceType(function() {
                                                if ($("#device-type_eq").length == 1) {
                                                    $(
                                                            "#select2-device-type_eq-container")
                                                            .text(
                                                                    $(
                                                                            "#device-type_eq option:selected")
                                                                            .val());
                                                }
                                                changeOs(function() {
                                                    if ($("#os_eq").length == 1) {
                                                        $(
                                                                "#select2-os_eq-container")
                                                                .text(
                                                                        $(
                                                                                "#os_eq option:selected")
                                                                                .val());
                                                    }
                                                    changeHostname(function() {
                                                        if ($("#hostname_eq").length == 1) {
                                                            $(
                                                                    "#select2-hostname_eq-container")
                                                                    .text(
                                                                            $(
                                                                                    "#hostname_eq option:selected")
                                                                                    .val());
                                                        }
                                                        modalShow(

                                                                "add-modal",
                                                                function() {
                                                                    $(
                                                                            '#add-body')
                                                                            .html(
                                                                                    '');
                                                                });
                                                    });
                                                });
                                            });
                                        });

                                    });
                                });
                    });
                });

function createAddPopup(callback) {
    var html = '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">우선순위';
    html += '</span><div class="modal-content-body"><input type="text" id="priority" class="form-control modify-text" />';
    html += '</div></div><div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">';
    html += '사업장</span><div class="modal-content-body"><select id="site_eq" class="form-control select2"></select></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">벤더</span>';
    html += '<div class="modal-content-body"><input type="text" id="vendor-like" class="form-control modify-text" />';
    html += '<a class="custom-btn custom-btn-app" id="vendor-mode-change" onclick="vendorToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">모델</span><div class="modal-content-body">';
    html += '<input type="text" id="model-like" class="form-control modify-text" />';
    html += '<a class="custom-btn custom-btn-app" id="model-mode-change" onclick="modelToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">장비종류</span><div class="modal-content-body">';
    html += '<input type="text" id="device-type-like" class="form-control modify-text" />';
    html += '<a class="custom-btn custom-btn-app" id="device-type-mode-change" onclick="deviceTypeToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">OS</span><div class="modal-content-body">';
    html += '<input type="text" id="os-like" class="form-control modify-text" />';
    html += '<a class="custom-btn custom-btn-app" id="os-mode-change" onclick="osToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">Hostname</span><div class="modal-content-body">';
    html += '<input type="text" id="hostname-like" class="form-control modify-text" />';
    html += '<a class="custom-btn custom-btn-app" id="hostname-mode-change" onclick="hostnameToggleEvent(this)"><i class="fa fa-exchange"></i></a></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">설명</span>';
    html += '<div class="modal-content-body"><input type="text" id="desc" class="form-control modify-text" /></div></div>';
    html += '<div class="input-group modal-input-group"><span class="input-group-addon modal-content-header">정책</span>';
    html += '<div class="modal-content-body"><select class="form-control select2" id="policy"><option>Permit</option>';
    html += '<option>Deny</option></select></div></div>';
    $("#add-body").html(html);
    os_like = true;
    device_type_like = true;
    hostname_like = true;
    model_like = true;
    vendor_like = true;
    selectInit(function() {
        if (typeof callback === 'function') {
            callback();
        }
    });
}

$('#add-save-btn').click(function() {
    var jObj = new Object();
    jObj.priority = $('#priority').val();
    jObj.site_id = $('#site_eq').val();

    if (vendor_like) {
        jObj.vendor = $('#vendor-like').val();
    } else {
        jObj.vendor = $('#select2-vendor_eq-container').text();
    }
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
    jObj.is_permit = ($('#policy').val().toLowerCase() == "permit");
    jObj.model_like = model_like;
    jObj.vendor_like = vendor_like;
    jObj.device_type_like = device_type_like;
    jObj.os_like = os_like;
    jObj.hostname_like = hostname_like;
    $.ajax({
        url : "policyManagement/accessPolicyInsert",
        type : "POST",
        dataType : "text",
        data : JSON.stringify(jObj),
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                accessPolicyTable.ajax.reload();
            }
            modalClose("add-modal", function() {
                $('#add-body').html('');
            });
        }
    });
});
/** 삭제 * */
$('#delete-button').click(function() {
    popupClass = "delete";
    modalShow("delete-modal", function() {
    });
});

$('#delete-save-btn').click(function() {
    var rows = $('input[name=checkbox-active]:checkbox:checked');
    var jsonArray = new Array();
    for (var i = 0; i < rows.length; i++) {
        var tr = $(rows[i]).parent().parent();
        var td = tr.children().eq(9);
        var jObj = Object();
        jObj.access_policy_id = td.html();
        jsonArray.push(jObj);
    }
    var jsonInfo = JSON.stringify(jsonArray);
    $.ajax({
        url : 'policyManagement/accessPolicyDelete',
        type : 'POST',
        data : jsonInfo,
        dataType : 'text',
        success : function(data) {
            var jsonObj = eval('(' + data + ')');
            if (jsonObj.result == true) {
                accessPolicyTable.ajax.reload();
                console.log('accessPolicyDelete: ' + true);
            } else {
                console.log('accessPolicyDelete: ' + false);
            }
            modalClose("delete-modal", function() {
            });

        }
    });
});

/** 함수들 * */
function changeSiteNames(index, callback) {
    $.ajax({
        url : "policyManagement/getSiteNames",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#site_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (jsonObj.data[i].site_id == index
                            || jsonObj.data.length == 1) {
                        $('#site_eq').append(
                                '<option selected="selected" value='
                                        + jsonObj.data[i].site_id + '>'
                                        + jsonObj.data[i].site_name
                                        + '</option>');
                    } else {
                        if (index == '' && i == 0) {
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
                if (typeof callback === 'function') {
                    callback();
                }
            }
        }
    });
}
function changeVendorNames(callback) {
    $
            .ajax({
                url : "policyManagement/getVendor",
                type : "POST",
                success : function(data) {
                    var jsonObj = eval("(" + data + ')');
                    if (jsonObj.result == true) {
                        $('#vendor_eq').find('option').remove().end();
                        for (var i = 0; i < jsonObj.data.length; i++) {
                            if (vendor == jsonObj.data[i].vendor
                                    || jsonObj.data.length == 1) {
                                $('#vendor_eq').append(
                                        '<option selected="selected">'
                                                + jsonObj.data[i].vendor
                                                + '</option>');
                            } else {
                                if (i == 0 && vendor == '') {
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
                        if (typeof callback === 'function') {
                            callback();
                        }
                    }
                }
            });
}
function changeModelNames(callback) {
    $.ajax({
        url : "policyManagement/getModel",
        type : "POST",
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#model_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (model == jsonObj.data[i].model
                            || jsonObj.data.length == 1) {
                        $('#model_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].model + '</option>');
                    } else {
                        if (i == 0 && model == '') {
                            $('#model_eq').append(
                                    '<option selected="selected">'
                                            + jsonObj.data[i].model
                                            + '</option>');
                        } else {
                            $('#model_eq').append(
                                    '<option>' + jsonObj.data[i].model
                                            + '</option>');
                        }
                    }
                }
                if (typeof callback === 'function') {
                    callback();
                }
            }
        }
    });
}
function changeDeviceType(callback) {
    $.ajax({
        url : "policyManagement/getDeviceType",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#device-type_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (device_type == jsonObj.data[i].device_type
                            || jsonObj.data.length == 1) {
                        $('#device-type_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].device_type
                                        + '</option>');
                    } else {
                        if (i == 0 && device_type == '') {
                            $('#device-type_eq').append(
                                    '<option selected="selected">'
                                            + jsonObj.data[i].device_type
                                            + '</option>');
                        } else {
                            $('#device-type_eq').append(
                                    '<option>' + jsonObj.data[i].device_type
                                            + '</option>');
                        }
                    }
                }
                if (typeof callback === 'function') {
                    callback();
                }
            }
        }
    });
}
function changeOs(callback) {
    $
            .ajax({
                url : "policyManagement/getOs",
                type : "POST",
                success : function(data) {
                    var jsonObj = eval("(" + data + ')');
                    if (jsonObj.result == true) {
                        $('#os_eq').find('option').remove().end();
                        for (var i = 0; i < jsonObj.data.length; i++) {
                            if (os == jsonObj.data[i].os
                                    || jsonObj.data.length == 1) {
                                $('#os_eq').append(
                                        '<option selected="selected">'
                                                + jsonObj.data[i].os
                                                + '</option>');
                            } else {
                                if (i == 0 && os == '') {
                                    $('#os_eq').append(
                                            '<option selected="selected">'
                                                    + jsonObj.data[i].os
                                                    + '</option>');
                                } else {
                                    $('#os_eq').append(
                                            '<option>' + jsonObj.data[i].os
                                                    + '</option>');
                                }
                            }
                        }
                        if (typeof callback === 'function') {
                            callback();
                        }
                    }
                }
            });
}
function changeHostname(callback) {
    $.ajax({
        url : "policyManagement/getHostname",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#hostname_eq').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    if (hostname == jsonObj.data[i].hostname
                            || jsonObj.data.length == 1) {
                        $('#hostname_eq').append(
                                '<option selected="selected">'
                                        + jsonObj.data[i].hostname
                                        + '</option>');
                    } else {
                        if (i == 0 && hostname == '') {
                            $('#hostname_eq').append(
                                    '<option selected="selected">'
                                            + jsonObj.data[i].hostname
                                            + '</option>');
                        } else {
                            $('#hostname_eq').append(
                                    '<option>' + jsonObj.data[i].hostname
                                            + '</option>');
                        }
                    }
                }
                if (typeof callback === 'function') {
                    callback();
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
    } else if (popupClass == "delete") {
        console.log("delete");
    }
    popupClass = "";
}

function vendorToggleEvent($this) {
    if (vendor_like) {
        removeSiblings($this);
        prependCodeToParent(
                $this,
                '<select id="vendor_eq" class="form-control select2" style="display: none;"></select>');
        changeVendorNames(function() {
            $("#vendor_eq").select2();

            var vendor_length = $("#vendor_eq").length;
            if (vendor_length > 1) {
                $("#select2-vendor_eq-container").text(vendor);
            } else if (vendor_length == 1) {
                if ($("#select2-vendor_eq-container").text() == vendor) {
                    $("#select2-vendor_eq-container").text(vendor);
                }
            }
            vendor_like = false;
        });

    } else {
        removeSiblings($this);
        prependCodeToParent($this,
                '<input type="text" id="vendor-like" class="form-control modify-text" />');
        vendor_like = true;
    }
}

function modelToggleEvent($this) {
    if (model_like) {
        removeSiblings($this);
        prependCodeToParent(
                $this,
                '<select id="model_eq" class="form-control select2" style="display: none;"></select>');
        changeModelNames(function() {
            $("#model_eq").select2();

            var model_length = $("#model_eq").length;
            if (model_length > 1) {
                $("#select2-model_eq-container").text(model);
            } else if (model_length == 1) {
                if ($("#select2-model_eq-container").text() == model) {
                    $("#select2-model_eq-container").text(model);
                }
            }
            model_like = false;
        });

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
        changeDeviceType(function() {
            $("#device-type_eq").select2();

            var device_type_length = $("#device-type_eq").length;
            if (device_type_length > 1) {
                $("#select2-device-type_eq-container").text(device_type);
            } else if (device_type_length == 1) {
                if ($("#select2-device-type_eq-container").text() == device_type) {
                    $("#select2-device-type_eq-container").text(device_type);
                }
            }
            device_type_like = false;
        });
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
        changeOs(function() {
            $("#os_eq").select2();

            var os_length = $("#os_eq").length;
            if (os_length > 1) {
                $("#select2-os_eq-container").text(os);
            } else if (os_length == 1) {
                if ($("#select2-os_eq-container").text() == os) {
                    $("#select2-os_eq-container").text(os);
                }
            }
            os_like = false;
        });
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
        changeHostname(function() {
            $("#hostname_eq").select2();

            var hostname_length = $("#hostname_eq").length;
            if (hostname_length > 1) {
                $("#select2-hostname_eq-container").text(hostname);
            } else if (hostname_length == 1) {
                if ($("#select2-hostname_eq-container").text() == hostname) {
                    $("#select2-hostname_eq-container").text(hostname);
                }
            }
            hostname_like = false;
        });
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
function selectInit(callback) {
    $("#site_eq").select2();
    if (!vendor_like)
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
    if (typeof callback === 'function') {
        callback();
    }
}

// 체크박스 전체선택
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
