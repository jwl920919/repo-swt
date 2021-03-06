/** Modal Popup Javascript */
var windowHeight, scrollTop, windowWidth;
function getWindowPoint() {
	windowHeight = $(window).height();
	windowWidth = $(window).width();
	scrollTop = $(document).scrollTop();
};

function modalShow(id) {
	try {
		$("#" + id).css("display","block");
		$("#modalbackDiv").css("visibility","visible");
	} catch (e) {
		console.log(e.message);
	}

	getWindowPoint();
    var alertPositionHeight = (windowHeight / 2 + scrollTop - 60 - ($('div[name=modalContent]').innerHeight() / 2)) + 'px';
    var alertPositionWidth = (windowWidth / 2 - ($('div[name=modalContent]').innerWidth() / 2))+ 'px';
    //console.log($('input[name=modalContent]').innerHeight());
    
    $("#" + id).css('top', alertPositionHeight);    
    $("#" + id).css('left', alertPositionWidth + " !important");

    
    $('input[name=modalClose]').click(function(){
    	modalClose(id);
    });
    
    try {
    	fnShowEvent();
    	var t = $('.modal-content-header');
        for(var t2=0 ;t2 < t.length; t2++){
            $(t[t2]).css("height",$(t[t2]).parent().height());
        }
    } catch (e) {
    	console.log(e.message);
    }
}
function modalClose(id) {
	$("#" + id).css("display","none");
	$("#modalbackDiv").css("visibility","hidden");
}
