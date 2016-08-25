var minWidth = 100;

$(".resizable1").resizable({
  autoHide: false,
  handles: 'e',
  minWidth: minWidth,
  resize: function(e, ui){
    var parentWidth = ui.element.parent().width();
    var remainingSpace = parentWidth - ui.element.outerWidth();
    
    if(remainingSpace < minWidth){
      ui.element.width((parentWidth - minWidth)/parentWidth*100+"%");
      remainingSpace = minWidth;
    }
    var divTwo = ui.element.next(),
        divTwoWidth = (remainingSpace - (divTwo.outerWidth() - divTwo.width()))/parentWidth*100+"%";
    divTwo.width(divTwoWidth);
  },
  stop: function(e, ui){
    var parentWidth = ui.element.parent().width();
    ui.element.css({
      width: ui.element.width()/parentWidth*100+"%"
    });
  }
});
