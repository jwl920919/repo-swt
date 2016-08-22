var ipTable;
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
                    if ($(this).attr('is_lastchild') == 'true') {
                        
                    } else {
                        var jObj = new Object();
                        jObj.network = $(this).attr('network');
                        jObj.ip_type = $(this).attr('ip_type');
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
