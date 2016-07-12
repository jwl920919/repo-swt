
$('#datatable').on('draw.dt', function() {
    //기본 Datatable 스크립트에서 지정한 크기를 변경해줌
    $('#datatable_paginate').parent().addClass("col-sm-12").removeClass("col-sm-7");
});
//trClickEvent는 만들어져있는  Method가 아니기 때문에 직접 구현해주어야함 
$('#datatable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    trClickEvent(this);
});