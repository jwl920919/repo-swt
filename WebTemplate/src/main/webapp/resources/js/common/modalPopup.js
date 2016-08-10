/** Modal Popup Javascript */
var windowHeight, scrollTop, windowWidth;
function getWindowPoint() {
	windowHeight = $(window).height();
	windowWidth = $(window).width();
	scrollTop = $(document).scrollTop();
};

function modalShow(id, closeCallback) {
	try {
		$("#" + id).css("display","block");
		$("#modalbackDiv").css("visibility","visible");
	} catch (e) {
		console.log(e.message);
	}

	getWindowPoint();
    var alertPositionHeight = (windowHeight / 2 + scrollTop - 60 - ($('#'+id+' div[name=modalContent]').innerHeight() / 2)) + 'px';
    var alertPositionWidth = (windowWidth / 2 - ($('#'+id+' div[name=modalContent]').innerWidth() / 2))+ 'px';
    //console.log($('input[name=modalContent]').innerHeight());
    
    $("#" + id).css('top', alertPositionHeight);    
    $("#" + id).css('left', alertPositionWidth + " !important");

    
    $('input[name=modalClose]').click(function(){
        if(closeCallback==null){
            modalClose(id);
        } else {
            modalClose(id, closeCallback);
        }
    	
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
function modalClose(id, closeCallback) {
	$("#" + id).css("display","none");
	$("#modalbackDiv").css("visibility","hidden");
	if(closeCallback!=null){
    	    if (typeof closeCallback === 'function' && closeCallback != null) {
    	        closeCallback();
            }
	}
}
