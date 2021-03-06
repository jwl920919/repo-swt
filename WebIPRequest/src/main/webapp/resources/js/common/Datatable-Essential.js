$('table[name=datatable]').on('draw.dt', function() {
//$('#datatable').on('draw.dt', function() {
    //기본 Datatable 스크립트에서 지정한 크기를 변경해줌
	var tagId = $(this).attr('id');	//name으로 검색한 데이터 테이블의  id값을 조회
	console.log("tagId" + tagId);
    $('#'+tagId+'_paginate').parent().addClass("col-sm-12").removeClass("col-sm-7");//paginate 크기변경
    $('#'+tagId+'_paginate').css('text-align','center');//paginate text-align 변경
    
    //중복 TR 클릭  델리게이트 이벤트 생성을 방지하기 위해 기존 델리게이트 이벤트를 삭제한다.
    $('#'+tagId).undelegate('tbody>tr', 'click');
    
    $('#'+tagId).delegate('tbody>tr', 'click', function() {
        $(this).addClass("selected").siblings().removeClass("selected");
        trClickEvent(this);
    });
});
//trClickEvent는 만들어져있는  Method가 아니기 때문에 직접 구현해주어야함 

