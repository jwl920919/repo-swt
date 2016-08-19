var ipTable;
var regExp = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\/([1-9]|[1-2][0-9]|3[0-2]))$/;
$(document)
        .ready(
                function() {
                    ipTable = $('#ip-table')
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
                                                data.ip_type = $(
                                                        "#ip-table_select option:selected")
                                                        .text();
                                                data.search_key = data.search.value;
                                            }
                                        },
                                        'order' : [ [ 0, 'asc' ] ],
                                        "columns" : [
                                                {
                                                    "data" : "network"
                                                },
                                                {
                                                    "data" : "group_name"
                                                },
                                                {
                                                    "data" : "ip_type",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    "data" : "group_id",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    "data" : "site_id",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'className' : 'hidden'
                                                },
                                                {
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'render' : function(type,
                                                            row) {
                                                        return '<div style="width:35px;"><i class="fa fa-edit essential-cursor-pointer" onclick="ipModifyBtnClickEvent(this)" data-toggle="tooltip" title="" data-original-title="'
                                                                + getLanguage("modify")
                                                                + '"></i>'
                                                                + " <i class='fa fa-trash-o essential-cursor-pointer' onclick=\"ipDeleteBtnClickEvent(this)\" data-toggle='tooltip' title='"
                                                                + getLanguage("delete")
                                                                + "' ></i> </div>";
                                                    }
                                                } ]
                                    });
                    $(function() {
                        var d_wrap = $('#ip-table_wrapper .row:first');
                        var d_length = $('#ip-table_wrapper .row:first .col-sm-6:eq(0)');
                        $('#ip-table_length').css("margin-right", "0px");
                        var d_filter = $('#ip-table_wrapper .row:first .col-sm-6:eq(1)');
                        var filter = $('#ip-table_filter');
                        filter.css("margin-left", "0px");
                        var ip_table_select = $('#ip-table_select');
                        filter.append(ip_table_select);
                        ip_table_select.css("visibility", "visible");
                        d_length.append(d_filter);
                        d_wrap.prepend(d_filter);
                        // 테이블 하단 페이지 부분 좌우에 버튼 위치 지정
                        $ipTable_paginate_parent = $('#ip-table_paginate')
                                .parent();
                        $('#page-box').append($('#ip-table_paginate'));
                        $ipTable_paginate_parent.append($('#bottom-btn-row'));
                        $('#bottom-btn-row').parent().removeClass();
                        $('#bottom-btn-row').parent().addClass("col-sm-12");
                    });
                    if (siteMaster == 't') {
                        changeSiteNames();
                        $("#group-table-select-body").removeClass("hidden");
                        $("#group-table-site-body").removeClass("hidden");
                    } else {
                        $("#group-table-select-body").html('');
                        $("#group-table-site-body").html('');
                    }
                });

$('#ip-table_select')
        .change(
                function() {
                    if ($("#ip-table_select option:selected").text() == 'IPV4') {
                        // IPv4 CIDR range
                        regExp = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\/([1-9]|[1-2][0-9]|3[0-2]))$/;
                    } else {
                        // IPv6 CIDR range
                        regExp = /^s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:)))(\/([1-9]|[1-9][0-9]|1[0-1][0-9]|12[0-8]))$/;
                    }
                    ipTable.ajax.reload();
                });
var isMadeRecord = false;
var $tr;
function trClickEvent(tr) {
    $tr = $(tr);
}
function ipModifyBtnClickEvent(t) {
    var $this = $(t);
    if ($this.parent().parent().siblings().eq(3).text() == '') {
        isMadeRecord = false;
    } else {
        isMadeRecord = true;
    }
    modalShow("modify-modal");
    $("#network-txt").val($this.parent().parent().siblings().eq(0).text());
    if (isMadeRecord) {
        $("#name-txt").val($this.parent().parent().siblings().eq(1).text());
    } else {
        $("#name-txt").val('');
    }
    if (siteMaster == 't') {
        getSiteName($this.parent().parent().siblings().eq(4).text(), function(
                output) {
            $("#site-txt").val(output);
        });

    }
}
function ipDeleteBtnClickEvent(t) {
    var $this = $(t);

    modalShow("delete-modal");
}
$("#add-button").click(function() {
    if ($("#ip-table_select option:selected").text() == 'IPV4')
        $("#add-network-txt").attr("placeholder", "ex) 192.168.1.0/24");
    else
        $("#add-network-txt").attr("placeholder", "ex) fe80::c0a8:100/120");
    modalShow("add-modal");
});

$("#modify-save-btn").click(function() {
    var jObj = new Object();
    if (isMadeRecord) {
        jObj.group_name = $("#name-txt").val();
        jObj.ip_type = $tr.children().eq(2).text();
        jObj.group_id = $tr.children().eq(3).text();
        $.ajax({
            url : "/management/modifyIpCustomGroup",
            type : "POST",
            dataType : "text",
            data : JSON.stringify(jObj),
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    ipTable.ajax.reload();
                }
            }
        });
    } else {
        jObj.network = $("#network-txt").val();
        jObj.ip_type = $tr.children().eq(2).text();
        jObj.group_name = $("#name-txt").val();
        jObj.site_id = $tr.children().eq(4).text();
        $.ajax({
            url : "/management/addIpCustomGroup",
            type : "POST",
            dataType : "text",
            data : JSON.stringify(jObj),
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    ipTable.ajax.reload();
                }
            }
        });
    }
    modalClose("modify-modal");
});

$("#add-save-btn").click(function() {
    var jObj = new Object();
    var patt = new RegExp(regExp);
    if (patt.test($("#add-network-txt").val())) {
        jObj.network = $("#add-network-txt").val();
        jObj.ip_type = $("#ip-table_select option:selected").text();
        jObj.group_name = $("#add-name-txt").val();
        if (siteMaster == 't') {
            jObj.site_id = $('#group-table-select option:selected').val();
        } else {
            jObj.site_id = siteID;
        }
        $.ajax({
            url : "/management/addIpCustomGroup",
            type : "POST",
            dataType : "text",
            data : JSON.stringify(jObj),
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    ipTable.ajax.reload();
                    $("#add-network-txt").removeClass("test-success");
                    $("#add-network-txt").removeClass("test-fail");
                    $("#add-name-txt").val('');
                    $("#add-network-txt").val('');
                    modalClose("add-modal");
                }
            }
        });
    }
});
$("#delete-save-btn").click(function() {
    var jObj = new Object();
    jObj.group_id = $tr.children().eq(3).text();
    $.ajax({
        url : "/management/deleteIpCustomGroup",
        type : "POST",
        dataType : "text",
        data : JSON.stringify(jObj),
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                ipTable.ajax.reload();
                modalClose("delete-modal");
            }
        }
    });
});
$("#add-network-txt").change(function() {
    var patt = new RegExp(regExp);
    if (patt.test($("#add-network-txt").val())) {
        $("#add-network-txt").removeClass("test-fail");
        $("#add-network-txt").addClass("test-success");
    } else {
        $("#add-network-txt").removeClass("test-success");
        $("#add-network-txt").addClass("test-fail")
    }
});
fnShowEvent = function() {
    // if (popupClass == "modify") {
    // }
    // popupClass = "";
}
function changeSiteNames() {
    $.ajax({
        url : "/management/getSiteNames",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#group-table-select').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    $('#group-table-select').append(
                            '<option value=' + jsonObj.data[i].site_id + '>'
                                    + jsonObj.data[i].site_name + '</option>');
                }
            }
        }
    });

}

function getSiteName(site_id, handleData) {
    var jObj = new Object();
    jObj.site_id = site_id;
    $.ajax({
        url : "/management/getSiteName",
        type : "POST",
        dataType : "text",
        data : JSON.stringify(jObj),
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                handleData(jsonObj.resultValue.site_name);
            }
        }
    });
}

var uploadedMigrationData;

var openFile = function(event) {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function() {
        uploadedMigrationData = csvJSON(reader.result, function() {
            $("#migration-overlay").addClass('hidden');
        });
    };
    $("#migration-overlay").removeClass('hidden');
    try {
        reader.readAsText(input.files[0]);
    } catch (e) {
        $("#migration-overlay").addClass('hidden');
    }
};

$('#migration-btn').click(function() {
    console.log(uploadedMigrationData);
    $.ajax({
        url: "/management/migrationAction",
        type: "POST",
        dataType: "text",
        data : uploadedMigrationData,
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                ipTable.ajax.reload();
            } else {
                console.log(false);
            }
        }
    });
});

function csvJSON(csv, finEvt) {
    var lines = csv.split("\n");

    var result = [];

    var headers = lines[0].split(",");
    var prev = 0;
    for (var i = 1; i < lines.length; i++) {
        var obj = {};
        var currentline = lines[i].split(",");
        if (headers.length == currentline.length) {
            for (var j = 0; j < headers.length; j++) {
                obj[headers[j].replace(/\r/g, "")] = currentline[j].replace(/\r/g, "");
            }
            result.push(obj);
        }
    }
    var jsonStr = JSON.stringify(result);
    finEvt();
    return jsonStr; // JSON
}

function backupFileDown() {
    window.location.assign('/management/backupData.csv');
}
function deleteAllPopupEvt() {
    modalShow("delete-all-modal");
}
function allNodeDelEvt() {
    $.ajax({
        url: "/management/deleteAllCustomIpGroupInfo",
        type: "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                ipTable.ajax.reload();
                modalClose("delete-all-modal");
            } else {
                console.log(false);
            }
        }
    });
}
$("#delete-add-save-btn").click(function() {
    allNodeDelEvt();
});