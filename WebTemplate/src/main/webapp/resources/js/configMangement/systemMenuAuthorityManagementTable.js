var lock = false;
var selectTd;
var hiddenHtml;
$('td[name^=group_grant]')
        .hover(
                function() {
                    if (lock == false) {
                        selectTd = $(this);
                        hiddenHtml = selectTd.html()
                                .substring(
                                        0,
                                        selectTd.html().length
                                                - selectTd.text().length);
                        selectTd.html(hiddenHtml);
                        selectTd
                                .append('<select id=authen_select style="border: none;outline:none;text-align-last: center;width: 100%;height:100%;position: relative;padding-left: 14px;padding-bottom: 2px;"><option value="N">권한없음</option><option value="R">읽기</option><option value="W">읽기/쓰기</option></select><script>$("select option[value="+selectTd.children().eq(1).val()+"]").attr("selected", true);$("#authen_select").change(function(){authenSelectChange()});</script>');
                        lock = true;
                        selectTd.toggleClass('hovered');
                    } else {
                        authenSelectChange();
                    }
                });

function authenSelectChange() {
    var selectedText = $('#authen_select option:selected').text();
    var selectedValue = $('#authen_select option:selected').val();
    selectTd.html(hiddenHtml + selectedText);
    selectTd.children().eq(1).val(selectedValue);
    selectTd.toggleClass('hovered');
    lock = false;
}

function getGroupTdData(data) {
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
    var jObj = new Object();
    jObj.auth_menu_id = parseInt(auth_menu_id_val);
    jObj.auth_state = auth_state_val;
    jObj.master_cd = main_menu_val;
    jObj.sub_cd = sub_menu_val;
    jObj.site_id = parseInt(site_id_val);
    jObj.group_id = parseInt(group_id_val);
    return jObj;
}

$('.save-button').click(function() {
    var a = $('td[name^=group_grant]');
    var jArray = new Array();
    
    for (var b = 0; b < a.length; b++) {
        jArray.push(getGroupTdData(a[b]));
    }
    $.ajax({
        url : "/configManagement/changeGroupsAuthority",
        data : JSON.stringify(jArray),
        dataType : "text",
        type : "POST",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                console.log('changeGroupsAuthority : ' + true);
//                $("#content_frame").load('/configManagement/systemMenuAuthorityManagement');
            } else {
                console.log('changeGroupsAuthority : ' + false);
            }
        }
    });
});