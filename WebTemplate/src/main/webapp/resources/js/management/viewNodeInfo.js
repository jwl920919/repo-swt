var parentTable;
var ip_type;
$(document)
.ready(
        function() {
            parentTable = $('#parent-table')
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
//                                "serverSide" : true,
//                                "ajax" : {
//                                    url : '/management/getIpTableDatas',
//                                    "dataType" : "jsonp",
//                                    "type" : "POST",
//                                    "jsonp" : "callback",
//                                    "data" : function(data, type) {
//                                        data.ip_type = $(
//                                                "#ip-table_select option:selected")
//                                                .text();
//                                        data.search_key = data.search.value;
//                                    }
//                                },
                                'order' : [ [ 0, 'asc' ] ],
                                "columns" : [
                                        {
                                            "data" : "last_ip"
                                        },
                                        {
                                            "data" : "macaddr"
                                        },
                                        {
                                            "data" : "hostname"
                                        },
                                        {
                                            "data" : "os"
                                        },
                                        {
                                            "data" : "device_type"
                                        }]
                            });
          //검색, 엔트리 위치 정렬
            $(function() {
                var d_wrap = $('#parent-table_wrapper .row:first');
                var d_length = $('#parent-table_wrapper .row:first .col-sm-6:eq(0)');
                var d_filter = $('#parent-table_wrapper .row:first .col-sm-6:eq(1)');
                d_length.append(d_filter);
                d_wrap.prepend(d_filter);
            });
            getDeviceNames();
        });
//IpTreeNode 정보 받아오는 부분
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
                    ip_type= $(this).attr('ip_type');
                    $('#defaultDiv').removeClass('hidden');
                    if ($(this).attr('is_lastchild') == 'true') {
                        
                    } else {
                        var jObj = new Object();
                        jObj.network = $(this).attr('network');
                        jObj.ip_type = ip_type;
                        $.ajax({
                            url : "/management/getNodeChildren",
                            type : "POST",
                            dataType : "text",
                            data : JSON.stringify(jObj),
                            success : function(data) {
                                var jsonObj = eval("(" + data + ')');
                                if (jsonObj.result == true) {
                                } else {
                                }
                            }
                        });
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
                        for (var i =0 ; i<jsonObj.data.length;i++) {
                            $('#deviceSel').append(
                                    '<option value=>' + jsonObj.data[i].device_type
                                            + '</option>');
                        }
                    }
                }
            });

}

/**
 * tr 이벤트 핸들러
**/
function trClickEvent (obj){
        return false;
}
