$('#datatable > tbody > tr').click(function() {
    $(this).addClass("selected").siblings().removeClass("selected");
})
