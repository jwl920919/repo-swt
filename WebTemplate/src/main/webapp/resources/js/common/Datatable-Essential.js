var rows;
$('#datatable').on('draw.dt', function() {
    rows = $('#datatable').find("tbody > tr ");
    // for (var i = 0; i < values.length; i++) {
    // if ($(values[i]).children(':eq(2)').text() == '192.168.1.157') {
    // $(values[i]).children(':eq(2)').parent().css(
    // 'background-color', 'gray');
    // }
    // }
});
//trClickEvent는 만들어져있는  Method가 아니기 때문에 직접 구현해주어야함 
$('#datatable').delegate('tbody>tr', 'click', function() {
    $(this).addClass("selected").siblings().removeClass("selected");
    trClickEvent(this);
});