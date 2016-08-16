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
                                                        return '<i class="fa fa-edit essential-cursor-pointer" onclick="ipModifyBtnClickEvent(this)" data-toggle="tooltip" title="" data-original-title="수정"></i>';
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
    if ($this.parent().siblings().eq(2).text() == '') {
        isMadeRecord = false;
    } else {
        isMadeRecord = true;
    }
    modalShow("modify-modal");
    $("#network-txt").val($this.parent().siblings().eq(0).text());
    if (isMadeRecord) {
        $("#name-txt").val($this.parent().siblings().eq(1).text());
    } else {
        $("#name-txt").val('');
    }
}
$("#modify-save-btn").click(function() {
    var jObj = new Object();
    if (isMadeRecord) {
        jObj.group_name = $("#name-txt").val();
        jObj.group_id = $tr.children().eq(2).text();
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
        jObj.group_name = $("#name-txt").val();
        $.ajax({
            url : "/management/insertIpCustomGroup",
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
fnShowEvent = function() {
    // if (popupClass == "modify") {
    // }
    // popupClass = "";
}