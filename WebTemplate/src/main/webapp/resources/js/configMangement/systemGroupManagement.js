/** ******************************************************************************************************************************************* */
/** ********************************  datatable이 2개이기때문에 2개의 아이디 값이 필요해서 개별적으로 js에서 구현해줌  ************************ */
/** ******************************************************************************************************************************************* */
//$('#datatable').on(
//        'draw.dt',
//        function() {
//            // 기본 Datatable 스크립트에서 지정한 크기를 변경해줌
//            $('#datatable_paginate').parent().addClass("col-sm-12")
//                    .removeClass("col-sm-7");
//        });
//// trClickEvent는 만들어져있는 Method가 아니기 때문에 직접 구현해주어야함
//$('#datatable').delegate('tbody>tr', 'click', function() {
//    $(this).addClass("selected").siblings().removeClass("selected");
//    trClickEvent(this);
//});
/** ******************************************************************************************************************************************* */

$(document).ready(function() {
    $("#layDiv").css("visibility", "hidden");
    //최초 크기 지정
    $('.white-paper').css('height', $("div.content-wrapper").height()-50);
    $('.scroll-box').css('height', $("div.content-wrapper").height()-62);
});

//window size변화에따른 크기 지정
window.onresize = function() {
    $('.white-paper').css('height', $("div.content-wrapper").height()-50);
    $('.scroll-box').css('height', $("div.content-wrapper").height()-62);
};