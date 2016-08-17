var ipTable;
$(document)
        .ready(
                function() {
                    // alert screen 감추기

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
                });

$('#ip-table_select').change(function() {
    ipTable.ajax.reload();
});
var isMadeRecord = false;
var $tr;
function trClickEvent(tr) {
    $tr = $(tr);
}
function ipModifyBtnClickEvent(t) {
    var $this = $(t);
    if ($this.parent().siblings().eq(3).text() == '') {
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
}
function ipDeleteBtnClickEvent(t) {
    var $this = $(t);

    modalShow("delete-modal");
}
$("#add-button").click(
        function() {
            if($("#ip-table_select option:selected").text() == 'IPV4')
                $("#add-network-txt").attr("placeholder","ex) 192.168.1.0/24");
            else $("#add-network-txt").attr("placeholder","ex) fe80::c0a8:100/120");
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
$("#delete-save-btn").click(function() {
    console.log("del-btn clicked");
});
fnShowEvent = function() {
    // if (popupClass == "modify") {
    // }
    // popupClass = "";
}