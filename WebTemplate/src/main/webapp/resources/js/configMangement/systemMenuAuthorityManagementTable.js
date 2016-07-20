$('td[name^=group_grant]').bind('click', function() {
    getGroupTdData(this)
});

$(document).ready(function() {
//    var a = $('td[name^=group_grant]');
//
//    console.log(a.length);
//    for (var b = 0; b < a.length; b++) {
//        getGroupTdData(a[b]);
//    }
});

function getGroupTdData(data) {
    console.log(26);
    var $data = $(data);
    var main_menu = $('td[name="'
            + $data.attr("name").replace('group_grant', 'main_menu') + '"]');
    var sub_menu = $data.parent().children().eq(0);
    var main_menu_val = main_menu.children().eq(0).val();
    var sub_menu_val = sub_menu.children().eq(0).val();
    var auth_menu_id_val = $data.children().eq(0).val();
    var auth_state_val = $data.children().eq(1).val();
    var site_id_val = $data.children().eq(2).val();
    var group_id_val = $data.children().eq(3).val();
    
    console.log(main_menu_val);
    console.log(sub_menu_val);
    console.log(auth_menu_id_val);
    console.log(auth_state_val);
    console.log(site_id_val);
    console.log(group_id_val);
}