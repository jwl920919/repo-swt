$("#layDiv").css("visibility", "hidden");
var placeOfBusinessTable, userGroupTable, placeOfBusinessTable_body_width;
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

                        // "serverSide" : true,
                        // "ajax" : {
                        // url :
                        // 'configManagement/getSystemUserManagementDatatableDatas',
                        // "dataType" : "jsonp",
                        // "type" : "POST",
                        // "jsonp" : "callback",
                        // "data" : function(data, type) {
                        // data.search_key =
                        // data.search.value;
                        // }
                        //
                        // },
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
                            "data" : "desc"
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
                "scrollY" : "550",
                "scrollCollapse" : true,

                // "serverSide" : true,
                // "ajax" : {
                // url :
                // 'configManagement/getSystemUserManagementDatatableDatas',
                // "dataType" : "jsonp",
                // "type" : "POST",
                // "jsonp" : "callback",
                // "data" : function(data, type) {
                // data.search_key =
                // data.search.value;
                // }
                //
                // },
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
                    "data" : "group_code"
                }, {
                    "width" : "30%",
                    "data" : "desc"
                } ],
            });
    // datatable의 scroll-x 숨김
    $('div.dataTables_scrollBody').css('overflow-x', 'hidden');
    // body 부분은 변경이 되기때문에 변경된 부분에서 width를 받아옴
//    placeOfBusinessTable_body_width = $('div.dataTables_scrollBody').width();
    placeOfBusinessTable_body_width = $('div.dataTables_scrollHead').width();
    // 받아온 width를 적용 -17은 margin-right 값으로 17px이 되어있어서 크기를 맞추기위하여 줌
    header_table = $('div.dataTables_scrollHeadInner').css('width',
            placeOfBusinessTable_body_width - 17);
    header_table_title = $('table.essential-table').css('width',
            placeOfBusinessTable_body_width - 17);
    //우측테이블 margin
    $('div#userGroupTable_wrapper').children().eq(1).first().css('margin-left','3px !important');
});

window.onresize = function() {
    placeOfBusinessTable_body_width = $('div.dataTables_scrollBody').width();
    header_table = $('div.dataTables_scrollHeadInner').css('width',
            placeOfBusinessTable_body_width - 17);
    header_table_title = $('table.essential-table').css('width',
            placeOfBusinessTable_body_width - 17);
    $('div#userGroupTable_wrapper').children().eq(1).first().css('margin-left','3px !important');
     $('.white-paper').css('height', $("div.content-wrapper").height() - 50);
     $('.scroll-box').css('height', $("div.content-wrapper").height() - 62);
};
/** ******************************************************************************************************************************************* */
/**
 * ******************************** datatable이 2개이기때문에 2개의 아이디 값이 필요해서 개별적으로
 * js에서 구현해줌 ************************
 */
/** ******************************************************************************************************************************************* */
function trClickEvent(data) {

}
// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
$('#placeOfBusinessTable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    console.log('#placeOfBusinessTable > tr');
    trClickEvent(this);
});
// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
$('#userGroupTable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    console.log('#userGroupTable > tr');
    trClickEvent(this);
});
/** ******************************************************************************************************************************************* */
