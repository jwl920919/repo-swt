$("#layDiv").css("visibility", "hidden");
var table;
$(document)
        .ready(
                function() {
                    // alert screen 감추기

                    table = $('#datatable')
                            .DataTable(
                                    {
                                        "destroy" : true,
                                        "paging" : true,
                                        "searching" : true,
                                        "lengthChange" : true,
                                        "ordering" : true,
                                        "info" : false,
                                        "autoWidth" : true,
                                        "processing" : true,
                                        "serverSide" : true,
                                        "ajax" : {
                                            url : 'configManagement/getSystemUserManagementDatatableDatas',
                                            "dataType" : "jsonp",
                                            "type" : "POST",
                                            "jsonp" : "callback",
                                            "data" : function(data, type) {
                                                data.search_key = data.search.value;
                                            }

                                        },
                                        'columnDefs' : [ {
                                            'targets' : 0,
                                            'data' : "active",
                                            'searchable' : false,
                                            'orderable' : false,
                                            'width' : '1%',
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
                                            "data" : "user_id"
                                        }, {
                                            "data" : "user_name"
                                        }, ],
                                    });
                    $(function() {
                        var d_wrap = $('#datatable_wrapper .row:first');
                        var d_length = $('#datatable_wrapper .row:first .col-sm-6:eq(0)');
                        var d_filter = $('#datatable_wrapper .row:first .col-sm-6:eq(1)');
                        d_length.append(d_filter);
                        d_wrap.prepend(d_filter);
                    });

                    changeGroupNames();
                });

function changeGroupNames() {
    $
            .ajax({
                url : "/configManagement/getGroupNames",
                type : "POST",
                success : function(data) {
                    var jsonObj = eval("(" + data + ')');
                    if (jsonObj.result == true) {
                        $('#ggroupSel').find('option').remove().end();
                        for (var i = 0; i < jsonObj.data.length; i++) {
                            $('#groupSel').append(
                                    '<option value=' + jsonObj.data[i].group_id
                                            + '>' + jsonObj.data[i].group_name
                                            + '</option>');
                        }
                    }
                }
            });

}
// 체크박스 전체선택
$('#checkbox_controller').click(function() {
    if ($(this).is(':checked')) {
        $('tbody>tr>td>input:checkbox').each(function() {
            this.checked = true;
        });
    } else {
        $('tbody>tr>td>input:checkbox').each(function() {
            this.checked = false;
        });
    }
});

$('#delete-button').click(
        function() {
            systemAlert("divAlertArea", "alert-warning", "삭제",
                    "정말 유저 정보를 삭제하시겠습니까?", "삭제", "#ce891c",
                    'userInformDeleteEvent');

        });
function userInformDeleteEvent() {
    var rows = $('input[name=checkbox-active]:checkbox:checked');
    var jsonArray = new Array();
    for (var i = 0; i < rows.length; i++) {
        var tr = $(rows[i]).parent().parent();
        var td = tr.children().next();
        var jObj = Object();
        jObj.user_id = td.html();
        jsonArray.push(jObj);
    }
    var jsonInfo = JSON.stringify(jsonArray);
    $.ajax({
        url : 'configManagement/deleteUsers',
        type : 'POST',
        data : jsonInfo,
        dataType : 'text',
        success : function(data) {
            var jsonObj = eval('(' + data + ')');
            if (jsonObj.result == true) {
                table.ajax.reload();
                console.log('삭제성공');
            } else {
                console.log('삭제실패');
            }
            fnAlertClose("layDiv")
        }
    });
}
// switching [ add(1), modify(2) ]
var sw = 1;
var idState = false;
function switching(num) {
    sw = num;
    changeGroupNames();
}
// trClickEvent 구현 ( Datatable-Essential.js에서 사용하기 위하여 )
function trClickEvent(clickedTr) {
    switching(2);
    var user_id = $(clickedTr).children(':eq(1)').text();
    $('#idTxt').val(user_id);
    $('#modify-label').addClass("selected-label").siblings().removeClass(
            "selected-label");
    $('#idTxt').attr("readOnly", true);
    $('#id-check-button').addClass("hidden");
    $('#id-state-label').addClass("hidden");
    $('#passwordTxt').val('');
    $('#passwordChkTxt').val('');
    var jObj = Object();
    jObj.user_id = user_id;
    $.ajax({
        url : "/configManagement/getUserInfo",
        type : "POST",
        data : JSON.stringify(jObj),
        dataType : "text",
        success : function(data) {
            var jsonObj = eval("(" + data + ')');
            if (jsonObj.result == true) {
                $.each(jsonObj.data, function(index, obj) {
                    $('#nameTxt').val(obj.user_name);
                    $('#groupSel').val(obj.group_id);
                    $('#departmentTxt').val(obj.dept_name);
                    $('#positionTxt').val(obj.position_name);
                    $('#emailTxt').val(obj.email);
                    $('#phoneTxt').val(obj.phone_num);
                    $('#mobileTxt').val(obj.mobile_num);
                });
            }
        }
    });
}

// datatable_paginate의 위치 조정
$('#datatable_paginate').css('margin-right', '60px');
// 삭제버튼 위치변경
$('#datatable_paginate').parent().prepend($('#delete-button').parent());

$('#add-button').click(
        function() {
            switching(1);
            idState = false;
            $('#add-label').addClass("selected-label").siblings().removeClass(
                    "selected-label");
            $('#idTxt').attr("readOnly", false);
            $('#id-state-label').text('');
            $('#id-check-button').removeClass("hidden");
            $('#id-state-label').removeClass("hidden");
            $('#idTxt').val('');
            clear();
        });

var pwd1 = $("#passwordTxt");
var pwd2 = $("#passwordChkTxt");
$(pwd1).change(function() {
    console.log(passwordCheck());
});
$(pwd2).change(function() {
    console.log(passwordCheck());
});
$('#emailTxt')
        .change(
                function() {
                    var emailCheckRegex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
                    if (emailCheckRegex.test($('#emailTxt').val())) {
                        $('#email-state-label').text('올바른 이메일 형식이 맞습니다.')
                        $('#email-state-label').css('color', "#3c8dbc");
                    } else {
                        $('#email-state-label').text('올바른 이메일 형식이 아닙니다.');
                        $('#email-state-label').css('color', "#f72929");
                    }
                });
var pwDesc = [ "8자리 ~ 20자리 이내로 입력해주세요.", "비밀번호는 공백없이 입력해주세요.",
        "영문,숫자, 특수문자를 혼합하여 입력해주세요.", "사용가능한 패스워드입니다.", "패스워드가 일치하지 않습니다." ];
function passwordCheck() {
    var validate = passwordValidation();
    var sameCheck = samePasswordCheck();
    if (pwd1.val() == '' && pwd2.val() == '') {
        $('#pw-state-label').addClass("hidden");
        return true;
    } else if (validate.stat && sameCheck) {
        $('#pw-state-label').removeClass("hidden");
        $('#pw-state-label').css('color', "#3c8dbc");
        $('#pw-state-label').text(pwDesc[validate.desc]);
        return true;
    } else {
        $('#pw-state-label').removeClass("hidden");
        $('#pw-state-label').css('color', "#f72929");
        if (validate.desc == 3)
            $('#pw-state-label').text(pwDesc[validate.desc + 1]);
        else
            $('#pw-state-label').text(pwDesc[validate.desc]);
        return false;
    }
}

function passwordValidation() {
    var pw = $(pwd1).val();
    var num = pw.search(/[0-9]/g);
    var eng = pw.search(/[a-z]/ig);
    var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    var obj = new Object();
    if (pw.length < 8 || pw.length > 20) {
        obj.stat = false;
        obj.desc = 0;
    } else if (pw.search(/₩s/) != -1) {
        obj.stat = false;
        obj.desc = 1;
    } else if (num < 0 || eng < 0 || spe < 0) {
        obj.stat = false;
        obj.desc = 2;
    } else {
        obj.stat = true;
        obj.desc = 3;
    }
    return obj;

}

function samePasswordCheck() {
    if ($(pwd1).val() == $(pwd2).val()) {
        return true;
    } else {
        return false;
    }
}
$('#id-check-button').click(function() {
    String
    regex = /^[a-z]+[a-z0-9]{5,19}$/g;
    var user_id = $('#idTxt').val();

    if (regex.test(user_id)) {
        var jObj = Object();
        jObj.user_id = user_id;

        $.ajax({
            url : "/configManagement/checkId",
            type : "POST",
            data : JSON.stringify(jObj),
            dataType : "text",
            success : function(data) {
                var jsonObj = eval("(" + data + ')');
                idState = jsonObj.result;
                if (idState == true) {
                    $('#id-state-label').text('사용 가능한 아이디입니다.');
                    $('#id-state-label').css('color', "#3c8dbc");
                } else {
                    $('#id-state-label').text('사용 불가능한 아이디입니다.');
                    $('#id-state-label').css('color', "#f72929");
                }
            }
        });
    } else {
        $('#id-state-label').text('아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.');
        $('#id-state-label').css('color', "#f72929");
    }
});

$('#save-button').click(function() {
    switch (sw) {
    case 1:
        if (passwordCheck()) {
            if (idState) {
                var jObj = Object();
                jObj.user_id = $('#idTxt').val();
                jObj.user_pw = $('#passwordTxt').val();
                jObj.user_name = $('#nameTxt').val();
                jObj.group_id = $('#groupSel').val();
                jObj.dept_name = $('#departmentTxt').val();
                jObj.position_name = $('#positionTxt').val();
                jObj.email = $('#emailTxt').val();
                jObj.phone_num = $('#phoneTxt').val();
                jObj.mobile_num = $('#mobileTxt').val();
                jObj.time_zone = getClientTimeZoneName();
                $.ajax({
                    url : "/configManagement/addUser",
                    type : "POST",
                    data : JSON.stringify(jObj),
                    dataType : "text",
                    success : function(data) {
                        var jsonObj = eval("(" + data + ')');
                        if (jsonObj.result == true) {
                            systemAlert("divAlertArea", "alert-info", "계정생성",
                                    "계정생성에 성공하셨습니다.", "확인", "rgba(60, 141, 188, 0.68)",
                                    '');
                            $('#idTxt').val('');
                            clear();
                        } else {
                            systemAlert("divAlertArea", "alert-warning", "계정생성",
                                    "계정생성에 실패하셨습니다.", "확인", "#ce891c",
                                    '');
                        }
                    }
                })
            } else {
                systemAlert("divAlertArea", "alert-warning", "아이디",
                        "아이디 중복 확인하세요", "확인", "#ce891c",
                        '');
            }
        } else {
            systemAlert("divAlertArea", "alert-warning", "패스워드",
                    "패스워드 일치 여부를 확인하세요", "확인", "#ce891c",
                    '');
        }
        break;
    case 2:
        if (passwordCheck()) {
            var jObj = Object();
            jObj.user_id = $('#idTxt').val();
            jObj.user_pw = $('#passwordTxt').val();
            jObj.user_name = $('#nameTxt').val();
            jObj.group_id = $('#groupSel').val();
            jObj.dept_name = $('#departmentTxt').val();
            jObj.position_name = $('#positionTxt').val();
            jObj.email = $('#emailTxt').val();
            jObj.phone_num = $('#phoneTxt').val();
            jObj.mobile_num = $('#mobileTxt').val();
            jObj.time_zone = getClientTimeZoneName();
            $.ajax({
                url : "/configManagement/updateUserInfo",
                type : "POST",
                data : JSON.stringify(jObj),
                dataType : "text",
                success : function(data) {
                    var jsonObj = eval("(" + data + ')');
                    if (jsonObj.result == true) {
                        systemAlert("divAlertArea", "alert-info", "계정변경",
                                "계정변경 성공하셨습니다.", "확인", "rgba(60, 141, 188, 0.68)",
                                '');
                    } else {
                        systemAlert("divAlertArea", "alert-warning", "계정변경",
                                "계정변경 실패하셨습니다.", "확인", "#ce891c",
                                '');
                    }
                }
            });
        } else {
            alert("패스워드를 확인하세요");
        }
        break;
    }

});

function clear() {
    $('#passwordTxt').val('');
    $('#passwordChkTxt').val('');
    $('#nameTxt').val('');
    $('#groupSel').val(1);
    $('#departmentTxt').val('');
    $('#positionTxt').val('');
    $('#emailTxt').val('');
    $('#phoneTxt').val('');
    $('#mobileTxt').val('');
    $('#pw-state-label').text('');
    $('#id-state-label').text('');
    $('#email-state-label').text('');
}
