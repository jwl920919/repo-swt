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
	//console.log("modalClose");
	//$("input[name=daterangepicker]").removeClass('active');
}


//region mouse move event
var box = $('[div_mordal_header]');
box.offset({
});

var drag = {
    elem: null,
    x: 0,
    y: 0,
    state: false
};
var delta = {
    x: 0,
    y: 0
};

box.mouseover(function(e){
	box.css('cursor','move');
});

box.mousedown(function(e) {
    if (!drag.state) {
        drag.elem = $(this).parent().parent();
        drag.x = e.pageX;
        drag.y = e.pageY;
        drag.state = true;
    }
    return false;
});


$(document).mousemove(function(e) {
    if (drag.state) {
        delta.x = e.pageX - drag.x;
        delta.y = e.pageY - drag.y;

        var cur_offset = $(drag.elem).offset();

        $(drag.elem).offset({
            left: (cur_offset.left + delta.x),
            top: (cur_offset.top + delta.y)
        });

        drag.x = e.pageX;
        drag.y = e.pageY;
    }
});

$(document).mouseup(function() {
    if (drag.state) {
        drag.state = false;
    }
	box.css('cursor','default');
});
//endregion mouse move event