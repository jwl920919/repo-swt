$("#layDiv").css("visibility", "hidden");
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
                        "scrollY" : "550",
                        "scrollCollapse" : true,
                        "serverSide" : true,
                        "responsive": true,
                        "ajax" : {
                            url : 'configManagement/getPlaceOfBusinessDatatableDatas',
                            "dataType" : "jsonp",
                            "type" : "POST",
                            "jsonp" : "callback",
                            "data" : function(data, type) {
                                data.search_key = data.search.value;
                            }

                        },
                        'columnDefs' : [ {
                            "width" : "10%",
                            'targets' : 0,
                            'data' : "active",
                            'searchable' : false,
                            'orderable' : false,
                            'className' : "dt-body-center",
                            'render' : function(data, type, row) {
                                if (type === 'display') {
                                    return '<input type="checkbox" name="checkbox-active" class="editor-active">';
                                }
                                return data;
                            }
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
                        } ],
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
                        "scrollY" : "550px",
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
                        'columnDefs' : [ {
                            "width" : "10%",
                            'targets' : 0,
                            'data' : "active",
                            'searchable' : false,
                            'orderable' : false,
                            'className' : "dt-body-center",
                            'render' : function(data, type, row) {
                                if (type === 'display') {
                                    return '<input type="checkbox" name="checkbox-active" class="editor-active">';
                                }
                                return data;
                            }
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
                        } ],
                    });
    // datatable의 scroll-x 숨김
    $('div.dataTables_scrollBody').css('overflow-x', 'hidden');

    // 우측테이블 margin
    $('div#userGroupTable_wrapper').children().eq(1).first().css('margin-left',
            '3px !important');

    // var dds =
    // $('div#placeOfBusinessTable_wrapper').children().eq(1).children()
    // .eq(0).children().eq(0).children().eq(1);
});
// 크기에 따라서 scoll표시여부
$('#placeOfBusinessTable').on(
        'draw.dt',
        function() {
            $('#placeOfBusinessTable_checkbox_controller').prop("checked", false);
            var dds = $('div#placeOfBusinessTable_wrapper').children().eq(1)
                    .children().eq(0).children().eq(0).children().eq(1);
            if (dds.height() < 545) {
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
            if (dds.height() < 545) {
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
var pob_scrollHeadInner = $('#placeOfBusinessTable').parent().prev().children().first();
var group_scrollHeadInner = $('#userGroupTable').parent().prev().children().first();
function tableResizing() {
    var pob_scroll_width = 0;
    var group_scroll_width = 0;

    if (pob_table_body.height() > 545)
        pob_scroll_width = 17;
    else
        pob_scroll_width = 0;
    if (group_table_body.height() > 545)
        group_scroll_width = 17;
    else
        group_scroll_width = 0;

    
    
    pob_scrollHeadInner.css('width', pob_table_body.width() - pob_scroll_width);
    pob_scrollHeadInner.children().eq(0).css('width',
            pob_table_body.width() - pob_scroll_width);
    pob_scrollHeadInner.css('width', pob_table_body.width() - pob_scroll_width);
    pob_scrollHeadInner.children().eq(0).css('width',
            pob_table_body.width() - pob_scroll_width);
    group_scrollHeadInner.css('width', group_table_body.width() - group_scroll_width);
    group_scrollHeadInner.children().eq(0).css('width',
            group_table_body.width() - group_scroll_width);
    group_scrollHeadInner.css('width', group_table_body.width() - group_scroll_width);
    group_scrollHeadInner.children().eq(0).css('width',
            group_table_body.width() - group_scroll_width);
    
}
//체크박스 전체선택
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
}
function userGroupTableClickEvent(data) {
    
}
// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
$('#placeOfBusinessTable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    placeOfBusinessTableClickEvent(this);
});
// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
$('#userGroupTable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    console.log('#userGroupTable > tr');
    userGroupTableClickEvent(this);
});
/** ******************************************************************************************************************************************* */
