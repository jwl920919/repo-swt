var childrenTable;
var ip_type = "IPV4";
var network = "0.0.0.0/0";
var device_type = "ALL";
var searchValue = "";
var isFirst = true;
function createIPv4Datatable() {
    childrenTable = $('#parent-table')
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
                            url : '/management/getNodeTableDatas',
                            "dataType" : "jsonp",
                            "type" : "POST",
                            "jsonp" : "callback",
                            "data" : function(data, type) {
                                data.ip_type = ip_type;
                                data.network = network;
                                data.device_type = device_type;
                                data.search.value = searchValue;
                                data.search_key = data.search.value;
                            }
                        },
                        'order' : [ [ 0, 'asc' ] ],
                        "columns" : [
                                {
                                    "data" : "last_ip"
                                },
                                {
                                    "data" : function(data) {
                                        if (data.macaddr != undefined) {
                                            return data.macaddr;
                                        }
                                        if (data.duid != undefined) {
                                            return data.duid;
                                        }

                                    }
                                },
                                {
                                    "data" : "hostname"
                                },
                                {
                                    "data" : "os"
                                },
                                {
                                    "data" : function(data) {
                                        if (data.device_type == '') {
                                            return 'nomatch'
                                        } else {
                                            return data.device_type;
                                        }
                                    }
                                },
                                {
                                    'searchable' : false,
                                    'orderable' : false,
                                    'render' : function(type, row) {
                                        return '<div><i class="fa fa-edit essential-cursor-pointer" onclick="modifyBtnClickEvent(this)" data-toggle="tooltip" title="" data-original-title="'
                                                + getLanguage("modify")
                                                + '"></i>' + "</div>";
                                    }
                                } ],
                        "drawCallback" : function(settings) {
                            console.log($('#parentNodes'));
                            console.log($('#currentNode'));
                        }
                    });
    // 검색, 엔트리 위치 정렬
    $(function() {
        var d_wrap = $('#parent-table_wrapper .row:first');
        var d_length = $('#parent-table_wrapper .row:first .col-sm-6:eq(0)');
        var d_filter = $('#parent-table_wrapper .row:first .col-sm-6:eq(1)');
        d_length.append(d_filter);
        d_wrap.prepend(d_filter);
    });
    getDeviceNames();
};
$("#btnSearch").click(function() {
    searchValue = $("#txtSearch").val();
    device_type = $("#deviceSel option:selected").text();
    childrenTable.ajax.reload();
});
// IpTreeNode 정보 받아오는 부분
$(function() {
    $.ajax({
        url : "/management/getIpTreeNode",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                var jt = $('#container').jstree({
                    'core' : {
                        'data' : eval(jsonObj.resultValue)
                    }
                });
                jt.delegate("a", "click", function(event, data) {
                    event.preventDefault();
                    ip_type = $(this).attr('ip_type');
                    network = $(this).attr('network');
                    $('#defaultDiv').removeClass('hidden');
                    if (ip_type == 'IPV4') {
                        $('#mac-or-duid').html('MAC');
                        $('#mac-or-duid').css('min-width', '180px');
                        $('#mac-or-duid').css('width', '180px');
                    } else {
                        $('#mac-or-duid').html('DUID');
                        $('#mac-or-duid').css('min-width', '360px');
                        $('#mac-or-duid').css('width', '360px');
                    }
                    if (isFirst) {
                        createIPv4Datatable();
                        isFirst = false;
                    } else {
                        childrenTable.ajax.reload();
                    }
                })

            }
        }
    });
});

function getDeviceNames() {
    $.ajax({
        url : "/management/getDeviceTypes",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#deviceSel').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    $('#deviceSel').append(
                            '<option value=>' + jsonObj.data[i].device_type
                                    + '</option>');
                }
            }
        }
    });

}

function modifyBtnClickEvent(obj) {
    var $this = $(obj);
}

/**
 * tr 이벤트 핸들러
 */
function trClickEvent(obj) {
    return false;
}
var prevX;
var currX;
var chgStat = false;
var line = $("#content-tree-line");
var wrapper = $("#content-tree-wrapper");
var tree = $("#content-tree");
var isHover = false;
line.offset({});
line.mousedown(function(e) {
    if(!chgStat) {
        prevX = currX;
        chgStat = true;
    }
});
wrapper.mouseenter(function() {
    isHover = true;
});
wrapper.mouseup(function(e) {
    console.log(1);
    if(isHover)
    if(chgStat){
        var size = $("#content-tree").width() + (currX-prevX)-3;
        $("#content-tree").width(size);
        $("#content-tree-wrapper").css("margin-left",size+"px");
        chgStat = false;
        isHover = false;
    }
});
tree.mouseenter(function() {
    isHover = true;
});
tree.mouseup(function(e) {
    console.log(2);
    if(isHover)
    if(chgStat){
        var size = $("#content-tree").width() + (currX-prevX)-3;
        $("#content-tree").width(size);
        $("#content-tree-wrapper").css("margin-left",size+"px");
        chgStat = false;
        isHover = false;
    }
});
$(document).mousemove(function(e) {
    currX = e.pageX;
});
