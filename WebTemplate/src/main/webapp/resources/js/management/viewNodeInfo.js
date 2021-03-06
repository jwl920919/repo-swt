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
                                    "data" : "macaddr"
//                                        function(data) {
//                                        if (data.macaddr != undefined) {
//                                            return data.macaddr;
//                                        }
//                                        if (data.duid != undefined) {
//                                            return data.duid;
//                                        }

//                                    }
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
                            var parentNodeStr = '';
                            for(i=0 ;i< settings.json.parent_node.length; i++){
                                parentNodeStr += settings.json.parent_node[i]+' > ';
                            };
                            if(parentNodeStr.length>0){
                                $("#depth-title").css("margin-left","4px");
                            } else {
                                $("#depth-title").css("margin-left","0px");
                            }
                            $('#parentNodes').text(parentNodeStr);
                            $('#currentNode').text(settings.json.current_node);
                            
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
//                    if (ip_type == 'IPV4') {
//                        $('#mac-or-duid').html('MAC');
//                        $('#mac-or-duid').css('min-width', '180px');
//                        $('#mac-or-duid').css('width', '180px');
//                    } else {
//                        $('#mac-or-duid').html('DUID');
//                        $('#mac-or-duid').css('min-width', '360px');
//                        $('#mac-or-duid').css('width', '360px');
//                    }
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
    var jObj = new Object();
    jObj.ipaddr = $this.parent().parent().siblings().eq(0).text();
    jObj.macaddr = $this.parent().parent().siblings().eq(1).text();
    $.ajax({
        url : '/management/getDeviceInfo',
        type : "POST",
        data : JSON.stringify(jObj),
        dataType : "text",
        success : function(data) {
            var jsonObj = eval('(' + data + ')');
            if (jsonObj.result == true) {
                var rv = jsonObj.resultValue;
                console.log(rv);
                $("#site-txt").val(rv.site_name);
                $("#vendor-txt").val(rv.vendor);
                $("#model-txt").val(rv.model);
                $("#os-txt").val(rv.os);
                $("#dtype-txt").val(rv.device_type);
                $("#category-txt").val(rv.category);
                $("#switch-txt").val(rv.last_switch);
                $("#port-txt").val(rv.last_port);
                $("#ipstatus-txt").val(rv.ip_status);
                modalShow("modify-modal");
            }
        }
    });
}

/**
 * tr 이벤트 핸들러
 */
function trClickEvent(obj) {
    return false;
}

/**
 * splitter 코드 부분
 * */
var splitter, cont1, cont2;
var last_x, window_width;
$(document).ready(function() {
    window_width = $("#content_frame").width();
    splitter = document.getElementById("content-tree-line");
    cont1 = document.getElementById("content-tree");
    cont2 = document.getElementById("content-tree-wrapper");
    var dx = cont1.clientWidth;
    splitter.style.marginLeft = dx + "px";
    dx += splitter.clientWidth;
    cont2.style.marginLeft = dx + "px";
    dx = window_width - dx;
    cont2.style.width = dx + "px";
    splitter.addEventListener("mousedown", spMouseDown);
//    $("#content-tree").css("height",window.innerHeight-148+"px");
//    $("#content-tree-line").css("height",window.innerHeight-148+"px");
//    $("#content-tree-wrapper").css("height",window.innerHeight-148+"px");
//    $("#content-tree").css("max-height",window.innerHeight-148+"px");
//    $("#content-tree-line").css("max-height",window.innerHeight-148+"px");
//    $("#content-tree-wrapper").css("max-height",window.innerHeight-148+"px");
    $(".tree-warper").css("max-height",window.innerHeight-152+"px");
    $(".tree-warper").css("max-height",window.innerHeight-152+"px");
});
resize = function() {
    window_width = $("#content_frame").width();
    splitter = document.getElementById("content-tree-line");
    cont1 = document.getElementById("content-tree");
    cont2 = document.getElementById("content-tree-wrapper");
    var dx = cont1.clientWidth;
    splitter.style.marginLeft = dx + "px";
    dx += splitter.clientWidth;
    cont2.style.marginLeft = dx + "px";
    dx = window_width - dx;
    cont2.style.width = dx + "px";
//    setTimeout(function() {
//        $("#content-tree-line").height($("#white-paper").height()+12);    
//    },100);  
//    $("#content-tree").css("height",window.innerHeight-148+"px");
//    $("#content-tree-line").css("height",window.innerHeight-148+"px");
//    $("#content-tree-wrapper").css("height",window.innerHeight-148+"px");
//    $("#content-tree").css("max-height",window.innerHeight-148+"px");
//    $("#content-tree-line").css("max-height",window.innerHeight-148+"px");
//    $("#content-tree-wrapper").css("max-height",window.innerHeight-148+"px");
    $(".tree-warper").css("max-height",window.innerHeight-152+"px");
    $(".tree-warper").css("max-height",window.innerHeight-152+"px");
} 
window.onresize = resize;

function spMouseDown(e) {
    splitter.removeEventListener("mousedown", spMouseDown);
    window.addEventListener("mousemove", spMouseMove);
    window.addEventListener("mouseup", spMouseUp);
    last_x = e.clientX;
}

function spMouseUp(e) {
    window.removeEventListener("mousemove", spMouseMove);
    window.removeEventListener("mouseup", spMouseUp);
    splitter.addEventListener("mousedown", spMouseDown);
    resetPosition(last_x);
}

function spMouseMove(e) {
    resetPosition(e.clientX);
}

function resetPosition(nowX) {
    var dx = nowX - last_x;
    dx += cont1.clientWidth;
    cont1.style.width = dx + "px";
    splitter.style.marginLeft = dx + "px";
    dx += splitter.clientWidth;
    cont2.style.marginLeft = dx + "px";
    dx = window_width - dx;
    cont2.style.width = dx + "px";
    last_x = nowX;
}


//getIpTreeNode Data
//[{"text":"IPV4(0.0.0.0/0)","a_attr":{"network":"0.0.0.0/0","ip_type":"IPV4"},"children":[{"text":"IPT(1.0.0.0/8)","a_attr":{"network":"1.0.0.0/8","ip_type":"IPV4"},"children":[{"text":"1.0.1.0/24","a_attr":{"network":"1.0.1.0/24","ip_type":"IPV4"}},{"text":"1.1.1.0/24","a_attr":{"network":"1.1.1.0/24","ip_type":"IPV4"}}]},{"text":"2.0.2.0/24","a_attr":{"network":"2.0.2.0/24","ip_type":"IPV4"}},{"text":"2.2.2.0/24","a_attr":{"network":"2.2.2.0/24","ip_type":"IPV4"}},{"text":"3.0.3.0(3.0.3.0/24)","a_attr":{"network":"3.0.3.0/24","ip_type":"IPV4"}},{"text":"3.3.3.0/24","a_attr":{"network":"3.3.3.0/24","ip_type":"IPV4"}},{"text":"10.10.10.0/24","a_attr":{"network":"10.10.10.0/24","ip_type":"IPV4"}},{"text":"168.0.0.0/10","a_attr":{"network":"168.0.0.0/10","ip_type":"IPV4"}},{"text":"LINE1(172.1.0.0/16)","a_attr":{"network":"172.1.0.0/16","ip_type":"IPV4"},"children":[{"text":"172.1.0.0/24","a_attr":{"network":"172.1.0.0/24","ip_type":"IPV4"}},{"text":"172.1.1.0/24","a_attr":{"network":"172.1.1.0/24","ip_type":"IPV4"}},{"text":"172.1.2.0/23","a_attr":{"network":"172.1.2.0/23","ip_type":"IPV4"}}]},{"text":"LINE2(172.2.0.0/16)","a_attr":{"network":"172.2.0.0/16","ip_type":"IPV4"},"children":[{"text":"172.2.0.0/24","a_attr":{"network":"172.2.0.0/24","ip_type":"IPV4"}},{"text":"172.2.1.0/24","a_attr":{"network":"172.2.1.0/24","ip_type":"IPV4"}}]},{"text":"LINE3(172.3.0.0/16)","a_attr":{"network":"172.3.0.0/16","ip_type":"IPV4"}},{"text":"172.4.0.0/15","a_attr":{"network":"172.4.0.0/15","ip_type":"IPV4"},"children":[{"text":"LINE4(172.4.0.0/16)","a_attr":{"network":"172.4.0.0/16","ip_type":"IPV4"}},{"text":"LINE5(172.5.0.0/16)","a_attr":{"network":"172.5.0.0/16","ip_type":"IPV4"}}]},{"text":"LINE6(172.6.0.0/16)","a_attr":{"network":"172.6.0.0/16","ip_type":"IPV4"}},{"text":"172.20.0.0/16","a_attr":{"network":"172.20.0.0/16","ip_type":"IPV4"}},{"text":"사무실(192.168.1.0/24)","a_attr":{"network":"192.168.1.0/24","ip_type":"IPV4"},"children":[{"text":"사무실1(192.168.1.0/25)","a_attr":{"network":"192.168.1.0/25","ip_type":"IPV4"},"children":[{"text":"사무실1-1(192.168.1.0/26)","a_attr":{"network":"192.168.1.0/26","ip_type":"IPV4"}},{"text":"사무실1-2(192.168.1.64/26)","a_attr":{"network":"192.168.1.64/26","ip_type":"IPV4"}}]},{"text":"사무실2(192.168.1.128/25)","a_attr":{"network":"192.168.1.128/25","ip_type":"IPV4"},"children":[{"text":"사무실2-1(192.168.1.128/26)","a_attr":{"network":"192.168.1.128/26","ip_type":"IPV4"}},{"text":"사무실2-2(192.168.1.192/26)","a_attr":{"network":"192.168.1.192/26","ip_type":"IPV4"}}]}]}]},{"text":"IPV6(::/0)","a_attr":{"network":"::/0","ip_type":"IPV6"},"children":[{"text":"2002:CAFE:FEED::/112","a_attr":{"network":"2002:CAFE:FEED::/112","ip_type":"IPV6"}},{"text":"2002:CAFE:FEED::1:0/112","a_attr":{"network":"2002:CAFE:FEED::1:0/112","ip_type":"IPV6"}},{"text":"2002:CAFE:FEED::2:0/112","a_attr":{"network":"2002:CAFE:FEED::2:0/112","ip_type":"IPV6"}},{"text":"ff01(FF01::/32)","a_attr":{"network":"FF01::/32","ip_type":"IPV6"}},{"text":"FF02::/32","a_attr":{"network":"FF02::/32","ip_type":"IPV6"}}]}]