//$("#layDiv").css("visibility", "hidden");
var placeOfBusinessTable, userGroupTable;
var pob = '';
$(function() {
    placeOfBusinessTable = $('#placeOfBusinessTable')
            .DataTable(
                    {
                        "destroy" : true,
                        "paging" : false,
                        "searching" : true,
                        "lengthChange" : true,
                        "ordering" : true,
                        "info" : false,
                        "autoWidth" : true,
                        "processing" : true,
                        "bPaginate" : false,
                        "bFilter" : false,
                        "scrollY" : "570px",
                        "scrollCollapse" : true,
                        "serverSide" : true,
                        "responsive" : true,
                        "ajax" : {
                            url : 'configManagement/getPlaceOfBusinessDatatableDatas',
                            "dataType" : "jsonp",
                            "type" : "POST",
                            "jsonp" : "callback",
                            "data" : function(data, type) {
                                data.search_key = data.search.value;
                            }

                        },
                        'columnDefs' : [
                                {
                                    "width" : "10%",
                                    'targets' : 0,
                                    'data' : "active",
                                    'searchable' : false,
                                    'orderable' : false,
                                    'className' : "dt-body-center",
                                    'render' : function(data, type, row) {
                                        if (type === 'display') {
                                            return '<input type="checkbox" name="pob-checkbox-active" class="editor-active">';
                                        }
                                        return data;
                                    }
                                }, {
                                    "targets" : 4,
                                    "className" : "hide_column",
                                    "searchable" : false,
                                    'orderable' : false,
                                    "data" : "site_id",

                                } ],
                        'order' : [ [ 1, 'asc' ] ],
                        "columns" : [ {}, {
                            "width" : "30%",
                            "data" : "site_name"
                        }, {
                            "width" : "30%",
                            "data" : "site_code"
                        }, {
                            "width" : "30%",
                            "data" : "description"
                        }, ],
                    });

    userGroupTable = $('#userGroupTable')
            .DataTable(
                    {
                        "destroy" : true,
                        "paging" : false,
                        "searching" : true,
                        "lengthChange" : true,
                        "ordering" : true,
                        "info" : false,
                        "autoWidth" : true,
                        "processing" : true,
                        "bPaginate" : false,
                        "bFilter" : false,
                        "scrollY" : "570px",
                        "scrollCollapse" : true,

                        "serverSide" : true,
                        "ajax" : {
                            url : 'configManagement/getUserGroupDatatableDatas',
                            "dataType" : "jsonp",
                            "type" : "POST",
                            "jsonp" : "callback",
                            "data" : function(data, type) {
                                data.search_key = data.search.value;
                            }

                        },
                        'columnDefs' : [
                                {
                                    "width" : "10%",
                                    'targets' : 0,
                                    'data' : "active",
                                    'searchable' : false,
                                    'orderable' : false,
                                    'className' : "dt-body-center",
                                    'render' : function(data, type, row) {
                                        if (type === 'display') {
                                            return '<input type="checkbox" name="group-checkbox-active" class="editor-active">';
                                        }
                                        return data;
                                    }
                                }, {
                                    "targets" : 4,
                                    "className" : "hide_column",
                                    "searchable" : false,
                                    'orderable' : false,
                                    "data" : "group_id",

                                } ],
                        'order' : [ [ 1, 'asc' ] ],
                        "columns" : [ {}, {
                            "width" : "30%",
                            "data" : "site_name"
                        }, {
                            "width" : "30%",
                            "data" : "group_name"
                        }, {
                            "width" : "30%",
                            "data" : "group_desc"
                        }, ],
                    });
    // datatable의 scroll-x 숨김
    $('div.dataTables_scrollBody').css('overflow-x', 'hidden');

    // 우측테이블 margin
    $('div#userGroupTable_wrapper').children().eq(1).first().css('margin-left',
            '3px !important');
    var pobfp = $('#placeOfBusinessTable_filter').parent();
    pobfp.removeClass('col-sm-6').addClass('col-sm-12');
    pobfp.css('display', 'flex');
    // addClass removeClass
    var groupfp = $('#userGroupTable_filter').parent();
    groupfp.removeClass('col-sm-6').addClass('col-sm-12');
    groupfp.css('display', 'flex');
    // var dds =
    // $('div#placeOfBusinessTable_wrapper').children().eq(1).children()
    // .eq(0).children().eq(0).children().eq(1);
});
// 크기에 따라서 scoll표시여부
$('#placeOfBusinessTable').on(
        'draw.dt',
        function() {
            $('#placeOfBusinessTable_checkbox_controller').prop("checked",
                    false);
            var dds = $('div#placeOfBusinessTable_wrapper').children().eq(1)
                    .children().eq(0).children().eq(0).children().eq(1);
            if (dds.height() < 565) {
                dds.css('overflow-y', 'hidden');
            } else {
                dds.css('overflow-y', 'auto');
            }
        });
$('#userGroupTable').on(
        'draw.dt',
        function() {
            $('#userGroupTable_checkbox_controller').prop("checked", false);
            var dds = $('div#userGroupTable_wrapper').children().eq(1)
                    .children().eq(0).children().eq(0).children().eq(1);
            if (dds.height() < 565) {
                dds.css('overflow-y', 'hidden');
            } else {
                dds.css('overflow-y', 'auto');
            }
        });
// 테이블 리사이징
window.onresize = function() {
    tableResizing();
};
var pob_table_body = $('#placeOfBusinessTable').parent();
var group_table_body = $('#userGroupTable').parent();
var pob_scrollHeadInner = $('#placeOfBusinessTable').parent().prev().children()
        .first();
var group_scrollHeadInner = $('#userGroupTable').parent().prev().children()
        .first();
function tableResizing() {
    var pob_scroll_width;
    var group_scroll_width;

    if (pob_table_body.height() > 565)
        pob_scroll_width = 17;
    else
        pob_scroll_width = 0;
    if (group_table_body.height() > 565)
        group_scroll_width = 17;
    else
        group_scroll_width = 0;

    pob_scrollHeadInner.css('width', pob_table_body.width() - pob_scroll_width);
    pob_scrollHeadInner.children().eq(0).css('width',
            pob_table_body.width() - pob_scroll_width);
    pob_scrollHeadInner.css('width', pob_table_body.width() - pob_scroll_width);
    pob_scrollHeadInner.children().eq(0).css('width',
            pob_table_body.width() - pob_scroll_width);

    group_scrollHeadInner.css('width', group_table_body.width()
            - group_scroll_width);
    group_scrollHeadInner.children().eq(0).css('width',
            group_table_body.width() - group_scroll_width);
    group_scrollHeadInner.css('width', group_table_body.width()
            - group_scroll_width);
    group_scrollHeadInner.children().eq(0).css('width',
            group_table_body.width() - group_scroll_width);

}
// modify event
$('#placeOfBusinessTable-modify-button').click(function() {
    var jObj = Object();
    jObj.site_name = $('#pob-table-pob-text').val();
    jObj.site_code = $('#pob-table-code-text').val();
    jObj.description = $('#pob-table-desc-text').val();
    jObj.site_id = $('#pob-id-text').val();
    if (jObj.site_name == '') {
        alert('사업장명을 입력하세요');
    } else if (jObj.site_code == '') {
        alert('사업장코드를 입력하세요');
    } else {
        $.ajax({
            url : "/configManagement/updateSite",
            data : JSON.stringify(jObj),
            dataType : "text",
            type : "POST",
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    console.log('updateSite : ' + true);
                    placeOfBusinessTable.ajax.reload();
                    changeSiteNames();
                } else {
                    console.log('updateSite : ' + false);
                }
            }
        });
    }
});
$('#userGroupTable-modify-button').click(function() {
    var jObj = Object();
    // site_id=#{site_id},group_name=#{group_name},group_desc=#{group_desc}
    jObj.site_id = $('#group-table-pob-select option:selected').val();
    jObj.group_name = $('#group-table-group-text').val();
    jObj.group_desc = $('#group-table-desc-text').val();
    jObj.group_id = $('#group-id-text').val();
    if (jObj.group_name == '') {
        alert('그룹명을 입력하세요');
    } else {
        $.ajax({
            url : "/configManagement/updateGroup",
            data : JSON.stringify(jObj),
            dataType : "text",
            type : "POST",
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    console.log('updateSite : ' + true);
                    userGroupTable.ajax.reload();
                } else {
                    console.log('updateSite : ' + false);
                }
            }
        });
    }
});

//delete event
$('#placeOfBusinessTable-delete-button')
        .click(
                function() {
                	systemAlertConfirm(
                            "divAlertArea",
                            "alert-warning",
                            "삭제",
                            "정말 사업장 정보를 삭제하시겠습니까? ","삭제","#ce891c",'pobDeleteEvent');

                });
$('#userGroupTable-delete-button')
        .click(
                function() {
                	systemAlertConfirm(
                            "divAlertArea",
                            "alert-warning",
                            "삭제",
                            "정말 그룹 정보를 삭제하시겠습니까?","삭제","#ce891c",'groupDeleteEvent');

                });

function pobDeleteEvent() {
    var rows = $('input[name=pob-checkbox-active]:checkbox:checked');
    var jsonArray = new Array();
    for (var i = 0; i < rows.length; i++) {
        var tr = $(rows[i]).parent().parent();
        var td = tr.children().eq(4);
        var jObj = Object();
        jObj.site_id = td.html();
        jsonArray.push(jObj);
    }
    var jsonInfo = JSON.stringify(jsonArray);
    $.ajax({
        url : 'configManagement/deleteSites',
        type : 'POST',
        data : jsonInfo,
        dataType : 'text',
        success : function(data) {
            var jsonObj = eval('(' + data + ')');
            if (jsonObj.result == true) {
                placeOfBusinessTable.ajax.reload();
                changeSiteNames();
                console.log('pobDelete: ' + true);
            } else {
                console.log('pobDelete: ' + false);
            }

        }
    });
}
function groupDeleteEvent() {
    var rows = $('input[name=group-checkbox-active]:checkbox:checked');
    var jsonArray = new Array();
    for (var i = 0; i < rows.length; i++) {
        var tr = $(rows[i]).parent().parent();
        var td = tr.children().eq(4);
        var jObj = Object();
        jObj.group_id = td.html();
        console.log(jObj.group_id);
        jsonArray.push(jObj);
    }
    var jsonInfo = JSON.stringify(jsonArray);
    $.ajax({
        url : 'configManagement/deleteGroups',
        type : 'POST',
        data : jsonInfo,
        dataType : 'text',
        success : function(data) {
            var jsonObj = eval('(' + data + ')');
            if (jsonObj.result == true) {
                userGroupTable.ajax.reload();
                console.log('groupDelete: ' + true);
            } else {
                console.log('groupDelete: ' + false);
            }

        }
    });
}

// add event
$('#placeOfBusinessTable-add-button').click(function() {
    var jObj = Object();
    jObj.site_name = $('#pob-table-pob-text').val();
    jObj.site_code = $('#pob-table-code-text').val();
    jObj.description = $('#pob-table-desc-text').val();
    if (jObj.site_name == '') {
        alert('사업장명을 입력하세요');
    } else if (jObj.site_code == '') {
        alert('사업장코드를 입력하세요');
    } else {
        $.ajax({
            url : "/configManagement/addSite",
            data : JSON.stringify(jObj),
            dataType : "text",
            type : "POST",
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    console.log('addSite : ' + true);
                    placeOfBusinessTable.ajax.reload();
                    changeSiteNames();
                } else {
                    console.log('addSite : ' + false);
                }
            }
        });
    }
});
$('#userGroupTable-add-button').click(function() {
    var jObj = Object();
    jObj.site_id = $('#group-table-pob-select option:selected').val();
    jObj.group_name = $('#group-table-group-text').val();
    jObj.group_desc = $('#group-table-desc-text').val();
    if (jObj.group_name == '') {
        alert('그룹명을 입력하세요');
    } else {
        $.ajax({
            url : "/configManagement/addGroup",
            data : JSON.stringify(jObj),
            dataType : "text",
            type : "POST",
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                if (jsonObj.result == true) {
                    console.log('addSite : ' + true);
                    userGroupTable.ajax.reload();
                } else {
                    console.log('addSite : ' + false);
                }
            }
        });
    }
});
// 체크박스 전체선택
$('#placeOfBusinessTable_checkbox_controller').click(function() {
    if ($(this).is(':checked')) {
        $('#placeOfBusinessTable>tbody>tr>td>input:checkbox').each(function() {
            this.checked = true;
        });
    } else {
        $('#placeOfBusinessTable>tbody>tr>td>input:checkbox').each(function() {
            this.checked = false;
        });
    }
});
$('#userGroupTable_checkbox_controller').click(function() {
    if ($(this).is(':checked')) {
        $('#userGroupTable>tbody>tr>td>input:checkbox').each(function() {
            this.checked = true;
        });
    } else {
        $('#userGroupTable>tbody>tr>td>input:checkbox').each(function() {
            this.checked = false;
        });
    }
});

function placeOfBusinessTableClickEvent(data) {
    userGroupTable.search($(data).children(':eq(1)').text()).draw();
    $('#pob-table-pob-text').val($(data).children(':eq(1)').text());
    $('#pob-table-code-text').val($(data).children(':eq(2)').text());
    $('#pob-table-desc-text').val($(data).children(':eq(3)').text());
    $('#pob-id-text').val($(data).children(':eq(4)').text());
    $('#group-table-pob-select').val($(data).children(':eq(4)').text());
    $('#group-table-pob-text').val('');
    $('#group-table-group-text').val('');
    $('#group-table-desc-text').val('');
}
function userGroupTableClickEvent(data) {
    $('#group-table-pob-select').val(
            $('#group-table-pob-select option').filter(function() {
                return $(this).html() == $(data).children(':eq(1)').text();
            }).val());
    $('#group-table-group-text').val($(data).children(':eq(2)').text());
    $('#group-table-desc-text').val($(data).children(':eq(3)').text());
    $('#group-id-text').val($(data).children(':eq(4)').text());
}
// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
$('#placeOfBusinessTable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    placeOfBusinessTableClickEvent(this);
});
// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
$('#userGroupTable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    userGroupTableClickEvent(this);
});

function changeSiteNames() {
    $.ajax({
        url : "/configManagement/getSiteNames",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $('#group-table-pob-select').find('option').remove().end();
                for (var i = 0; i < jsonObj.data.length; i++) {
                    $('#group-table-pob-select').append(
                            '<option value=' + jsonObj.data[i].site_id + '>'
                                    + jsonObj.data[i].site_name + '</option>');
                }
            }
        }
    });

}
$(document).ready(function() {
    changeSiteNames();
});
