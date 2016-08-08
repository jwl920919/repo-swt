/** Modal Popup Javascript */
var windowHeight, scrollTop, windowWidth;
function getWindowPoint() {
	windowHeight = $(window).height();
	windowWidth = $(window).width();
	scrollTop = $(document).scrollTop();
};

function modalShow(id) {
	$("#" + id).css("display","block");

	getWindowPoint();
    var alertPositionHeight = (windowHeight / 2 + scrollTop - 150 - ($('input[name=modalContent]').innerHeight() / 2)) + 'px';
    var alertPositionWidth = (windowWidth / 2 - ($('input[name=modalContent]').innerWidth() / 2))+ 'px';
        
    $("#" + id).css('top', alertPositionHeight);    
    $("#" + id).css('left', alertPositionWidth + " !important");

	$("#layDiv").css("visibility","visible");
    
    $('input[name=modalClose]').click(function(){
    	modalClose(id);
    });
    
    try {
    	fnShowEvent();
	} catch (e) {
		console.log(e.message);
	}
}
function modalClose(id) {
	$("#" + id).css("display","none");
	$("#layDiv").css("visibility","hidden");
}
