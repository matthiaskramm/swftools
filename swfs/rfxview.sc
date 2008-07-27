.flash filename=rfxview.swf fps=30 bbox=600x800 version=8 compress background=#CCCCCC export=0

.define width 600
.define height 800

#======================================== buttons ===========================================================

.gradient grad1 radial x=4 y=4 r=28:
 0% white
 50% #666666
 100% #202020
.end
.gradient grad2 linear width=8 height=6 rotate=120:
 0% white
 70% #666666
 100% #202020  
.end
.gradient grad4 linear width=8 height=6 rotate=120:
 0% white
 70% #999999
 100% #404040
.end

.gradient grad2l linear width=8 height=6 rotate=120:
 0% #e0e0e0
 100% #000000  
.end
.gradient grad4l linear width=8 height=6 rotate=120:
 0% #ffffff
 100% #404040
.end

.gradient grad3 radial x=4 y=4 r=28:
 0% white
 70% #999999
 100% #404040
.end

.gradient grad7 linear rotate=180:
 0% #777777
 100% #666666
.end

.gradient grad72 linear rotate=45:
 0% #999999
 100% #666666
.end

.outline arrowoutline:
    moveTo -8,-8
    lineTo -8, 8
    lineTo  8, 0
    lineTo -8,-8
.end

.outline larrowoutline:
    moveTo 8,-8
    lineTo 8, 8
    lineTo -8, 0
    lineTo 8,-8
.end

.font arialbold filename="../viewer/ArialBold.ttf" glyphs="1:"
.font arial filename="../viewer/Arial.ttf" glyphs="0123456789 /:"

.textshape oneoneoutline text="1:1" font=arialbold size=14pt

.outline plusoutline:
    moveTo  2,-8
    lineTo -2,-8
    lineTo -2,-2
    lineTo -8,-2
    lineTo -8,2
    lineTo -2,2
    lineTo -2,8
    lineTo  2,8
    lineTo  2,2
    lineTo  8,2
    lineTo  8,-2
    lineTo  2,-2
    lineTo  2,-8
.end

.outline minusoutline:
    moveTo -7,2
    lineTo -7,-2
    lineTo  7,-2
    lineTo  7,2
    lineTo -7,2
.end

.circle shadcircle line=0 fill=#00000033 r=11.5

.circle outcircle line=2 color=#00000060 fill=grad1 r=11
.circle outcircle_over line=2 color=#00000060 fill=grad3 r=11

.filled incircle outline=arrowoutline fill=grad2 line=1 color=#00000060
.filled incircle_over outline=arrowoutline fill=grad4 line=1 color=#00000060
.filled lincircle outline=larrowoutline fill=grad2l line=1 color=#00000060
.filled lincircle_over outline=larrowoutline fill=grad4l line=1 color=#00000060
.filled pincircle outline=plusoutline fill=grad2l line=1 color=#00000060
.filled pincircle_over outline=plusoutline fill=grad4l line=1 color=#00000060
.filled mincircle outline=minusoutline fill=grad2l line=1 color=#00000060
.filled mincircle_over outline=minusoutline fill=grad4l line=1 color=#00000060
.filled oincircle outline=oneoneoutline fill=grad2l line=1.2 color=#00000060
.filled oincircle_over outline=oneoneoutline fill=grad4l line=1.2 color=#00000060

.sprite plusface
 .put shadcircle 11 13 pin=center .put outcircle 11 11 pin=center .put pincircle 11 11 pin=center scale=90%
.end
.sprite plusfaceover 
 .put shadcircle 11 13 pin=center .put outcircle_over 11 11 pin=center .put pincircle_over 11 11 pin=center scale=90%
.end
.sprite plusfacedown 
 .put shadcircle 11 13 pin=center scale=90% .put outcircle_over 11 11 pin=center scale=90% .put pincircle_over 11 11 pin=center scale=79%
.end

.sprite minusface
 .put shadcircle 11 13 pin=center .put outcircle 11 11 pin=center .put mincircle 11 11 pin=center scale=90% luminance=60%
.end
.sprite minusfaceover 
 .put shadcircle 11 13 pin=center .put outcircle_over 11 11 pin=center .put mincircle_over 11 11 pin=center scale=90%
.end
.sprite minusfacedown 
 .put shadcircle 11 13 pin=center scale=90% .put outcircle_over 11 11 pin=center scale=90% .put mincircle_over 11 11 pin=center scale=79%
.end

.sprite rbface
 .put shadcircle 11 13 pin=center .put outcircle 11 11 pin=center .put incircle 13 11 pin=center scale=90%
.end
.sprite rbfaceover 
 .put shadcircle 11 13 pin=center .put outcircle_over 11 11 pin=center .put incircle_over 13 11 pin=center scale=90%
.end
.sprite rbfacedown 
 .put shadcircle 11 13 pin=center scale=90% .put outcircle_over 11 11 pin=center scale=90% .put incircle_over 13 11 pin=center scale=79%
.end

.sprite lbface
 .put shadcircle 11 13 pin=center .put outcircle 11 11 pin=center .put lincircle 9 11 pin=center scale=90%
.end
.sprite lbfaceover 
 .put shadcircle 11 13 pin=center .put outcircle_over 11 11 pin=center .put lincircle_over 9 11 pin=center scale=90%
.end
.sprite lbfacedown 
 .put shadcircle 11 13 pin=center scale=90% .put outcircle_over 11 11 pin=center scale=90% .put lincircle_over 9 11 pin=center scale=79%
.end

.sprite obface
 .put shadcircle 11 13 pin=center .put outcircle 11 11 pin=center .put oincircle 9 11 pin=center scale=90% luminance=50%
.end
.sprite obfaceover 
 .put shadcircle 11 13 pin=center .put outcircle_over 11 11 pin=center .put oincircle_over 9 11 pin=center scale=90%
.end
.sprite obfacedown 
 .put shadcircle 11 13 pin=center scale=90% .put outcircle_over 11 11 pin=center scale=90% .put oincircle_over 9 11 pin=center scale=79%
.end

.button rightbutton
  .show rbface as=idle
  .show outcircle as=area
  .show rbfacedown as=pressed
  .show rbfaceover as=hover
.end

.button leftbutton
  .show lbface as=idle
  .show outcircle as=area
  .show lbfacedown as=pressed
  .show lbfaceover as=hover
.end

.button plusbutton
  .show plusface as=idle
  .show outcircle as=area
  .show plusfacedown as=pressed
  .show plusfaceover as=hover
.end

.button minusbutton
  .show minusface as=idle
  .show outcircle as=area
  .show minusfacedown as=pressed
  .show minusfaceover as=hover
.end

.button oneonebutton
  .show obface as=idle
  .show outcircle as=area
  .show obfacedown as=pressed
  .show obfaceover as=hover
.end

#======================================== frame ===========================================================
  
.frame 1

.put p1=plusbutton x=width-25-20 y=20 pin=center
.put m1=minusbutton x=width-50-20 y=20 pin=center
.put l1=leftbutton x=width/2-100 y=20 pin=center
.put r1=rightbutton x=width/2+100 y=20 pin=center

.put o1=oneonebutton x=width-75-20 y=20 pin=center

.sprite background
.end
.put background

.sprite vscrollbar
    .box vscroll2_shadow line=0 fill=#00000033 width=14 height=100
    .sprite vshadow
    .put vscroll2_shadow x=0 y=0 luminance=1.8
    .end
    .put vshadow

    .box vscroll2 width=14 height=100 line=1 color=#00000060 fill=grad7
    .sprite vbox
    .put vscroll2 x=0 y=0 luminance=1.5
    .end
    .put vbox

    .box tick line=1 color=black width=8 height=0

    .sprite t1 .put tt1=tick y=0 x=3 .end .put t1
    .sprite t2 .put tt2=tick y=0 x=3 .end .put t2 y=50
    .sprite t3 .put tt3=tick y=0 x=3 .end .put t3
    
    .button vscrollbutton
	.show vscroll2 as=area
	.on_press inside:
	    startDrag(false, _parent.fullwidth-22, 40, 
	                     _parent.fullwidth-22, 40+_parent.scrollbaryrange);
	    _parent.Dragging = "v";
	.end
	.on_release:
	    stopDrag();
	    _parent.refreshDrag();
	    _parent.Dragging = "";
	.end
    .end
    .put vscrollbutton
.end

.sprite hscrollbar
    .box hscroll2_shadow width=100 line=0 fill=#00000033 height=14
    .sprite hshadow
	.put hscroll2_shadow x=0 y=0 luminance=1.5
    .end
    .put hshadow

    .box hscroll2 width=100 height=14 line=1 color=#00000060 fill=grad72
    .sprite hbox
    .put hscroll2 x=0 y=0 luminance=1.5
    .end 
    .put hbox

    .box htick line=1 color=black width=0 height=8

    .sprite th1 .put tht1=htick x=0 y=3 .end .put th1
    .sprite th2 .put tht2=htick x=0 y=3 .end .put th2 x=50
    .sprite th3 .put tht3=htick x=0 y=3 .end .put th3

    .button hscrollbutton
	.show hscroll2 as=area
	.on_press inside:
	    startDrag(false, 10                        , _parent.fullheight-22, 
	                     10+_parent.scrollbarxrange, _parent.fullheight-22);
	    _parent.Dragging = "h";
	.end
	.on_release:
	    stopDrag();
	    _parent.refreshDrag();
	    _parent.Dragging = "";
	.end
    .end
    .put hscrollbutton
.end

.put hscrollbar y=height-22 x=10
.put vscrollbar x=width-22 y=40

.edittext et width=110 height=20 font=arial size=18pt color=black noselect align=center

.font dbgarial filename="../doc/Courier.ttf"
#.edittext debugtxt width=width height=20 font=dbgarial size=18pt color=#004000 noselect
#.put debugtxt y=20

.put et x=width/2-30 y=8

#.swf swf filename=paper5.swf
.sprite swf
.end

.box f width=100 height=100 line=0 fill=black

.button areabutton
    .show f as=area
    .on_press inside:
	swf.startDrag(false, left-scrollxrange, top-scrollyrange, left,top);
	Dragging = "xy";
    .end
    .on_release:
	swf.stopDrag();
	Dragging = "";
    .end
.end
.put areabutton x=10 y=40

.sprite cf
    .put f
.end
.put cliparea=cf x=10 y=40
.put swf x=10 y=40

.action:

    swfwidth = swf._width;
    swfheight = swf._height;

    Dragging = "";
    
    Stage.scaleMode="noScale";
    Stage.align ="LT";
    
    //Stage.showMenu = false;

    fullwidth = Stage.width;
    fullheight = Stage.height;
    contentwidth = fullwidth - 40;
    contentheight = fullheight - 70;

    // move all objects to their proper positions
    vscrollbar._x = fullwidth-22;
    hscrollbar._y = fullheight-22;
    p1._x = fullwidth-25-20 - p1._width/2;
    o1._x = fullwidth-50-20 - o1._width/2;
    m1._x = fullwidth-75-20 - m1._width/2;

    l1._x = fullwidth/2-100 - l1._width/2;
    r1._x = fullwidth/2+100 - r1._width/2;

    et._x = fullwidth/2 - et._width/2;

    //.box f width=width-40 height=height-40-30 line=0 fill=black
    //.box vscroll1 width=10 height=height-40-30 line=1 color=#00000060 fill=grad7
    //.box hscroll1 height=10 width=width-40 line=1 color=#00000060 fill=grad72
    //.put vscroll1 x=width-20 y=40
    //.put hscroll1 x=10 y=height-20
	
    // horizontal scrollbar
    background.lineStyle(1, 0, 0x60);
    background.moveTo(10         ,fullheight-20);
    background.lineTo(10         ,fullheight-10);
    background.lineTo(10+fullwidth-40,fullheight-10);
    background.lineTo(10+fullwidth-40,fullheight-20);
    background.lineTo(10         ,fullheight-20);
    
    // vertical scrollbar
    background.moveTo(fullwidth-20 ,40);
    background.lineTo(fullwidth-20 ,fullheight-30);
    background.lineTo(fullwidth-10 ,fullheight-30);
    background.lineTo(fullwidth-10 ,40);
    background.lineTo(fullwidth-20 ,40);

    // content area
    background.lineStyle(1, 0);
    background.startFill(0x000000);
    background.moveTo(9, 39);
    background.lineTo(fullwidth-30+1, 39);
    background.lineTo(fullwidth-30+1, fullheight-29);
    background.lineTo(9, fullheight-29);
    background.lineTo(9, 39);
    background.endFill();
    
    areabutton._xscale = contentwidth;
    areabutton._yscale = contentheight;
    cliparea._xscale = contentwidth;
    cliparea._yscale = contentheight;
    swf.setMask(cliparea);
    

    lastzoom = 1;

    zoom = 1;
 	
    //debugtxt.text = Stage.width+ " x " + Stage.height;

    setPageNr = function() {
	et.text = "  "+pagenr+" / "+swf._totalframes;
	swf.gotoAndStop(pagenr);
    };
   
    setNoScrollZoomLevel = function() {
	// determine initial zoom level
	xscale = contentwidth / swfwidth;
	yscale = contentheight / swfheight;
	if(xscale < yscale) {
	    zoom = xscale;
	    setZoomLevel();
	} else {
	    zoom = yscale;
	    setZoomLevel();
	}
    };
    
    set11ZoomLevel = function() {
	zoom = 1.0;
	setZoomLevel();
    };

    swfpos2scrollbars = function() {
	if(scrollxrange) {
	    hscrollbar._x = 10 + (left-swf._x)*scrollbarxrange/scrollxrange;
	} else {
	    hscrollbar._x = 10;
	}
	if(scrollyrange) {
	    vscrollbar._y = 40 + (top-swf._y)*scrollbaryrange/scrollyrange;
	} else {
	    vscrollbar._y = 40;
	}
    };
    setZoomLevel = function() {

	width = contentwidth;
	height = contentheight;
	left = 10;
	top = 40;

	if(swfwidth * zoom < contentwidth) {
	    width = swfwidth*zoom;
	    left = 10+(contentwidth-width)/2;
	}
	if(swfheight * zoom < contentheight) {
	    height = swfheight*zoom;
	    top = 40+(contentheight-height)/2;
	}
	    
	scrollxrange = swfwidth*zoom-width;
	scrollyrange = swfheight*zoom-height;
   
	hscrollbar._xscale = (contentwidth*width) / (swfwidth*zoom);
	vscrollbar._yscale = (contentheight*height) / (swfheight*zoom);
	scrollbarxrange = contentwidth - hscrollbar._xscale;
	scrollbaryrange = contentheight - vscrollbar._yscale;
	if(scrollbarxrange<0) {
	    scrollbarxrange = 0;
	}
	if(scrollbaryrange<0) {
	    scrollbaryrange = 0;
	}

	swf._xscale = zoom*100;
	swf._yscale = zoom*100;
   
	focusx = contentwidth/2 - (swf._x-10);
	focusy = contentheight/2 - (swf._y-40);

	swf._x = left - focusx * zoom / lastzoom + width/2;
	swf._y = top - focusy * zoom / lastzoom + height/2;

	if(swf._x > left) {
	    swf._x = left;
	} else if(swf._x < left-scrollxrange) {
	    swf._x = left-scrollxrange;
	} 
	if(swf._y > top) {
	    swf._y = top;
	} else if(swf._y < top-scrollyrange) {
	    swf._y = top-scrollyrange;
	}

	swfpos2scrollbars();

	if(scrollxrange) {
	    hscrollbar._visible = 1;
	} else {
	    //hscrollbar._visible = 0;
	}
	if(scrollyrange) {
	    vscrollbar._visible = 1;
	} else {
	    //vscrollbar._visible = 0;
	}

	lastzoom = zoom;

	// update the ruler decorations

	vscrollbar.t1._y = vscrollbar.t2._y - 600.0/vscrollbar._yscale;
	vscrollbar.t3._y = vscrollbar.t2._y + 600.0/vscrollbar._yscale;
	hscrollbar.th1._x = hscrollbar.th2._x - 600.0/hscrollbar._xscale;
	hscrollbar.th3._x = hscrollbar.th2._x + 700.0/hscrollbar._xscale;

	vscrollbar.vshadow._x = vscrollbar.vbox._x + 500/vscrollbar._xscale;
	vscrollbar.vshadow._y = vscrollbar.vbox._y + 500/vscrollbar._yscale;
	
	hscrollbar.hshadow._x = hscrollbar.hbox._x + 500/hscrollbar._xscale;
	hscrollbar.hshadow._y = hscrollbar.hbox._y + 500/hscrollbar._yscale;
    };

    pagenr = 1;
    setPageNr();
    setNoScrollZoomLevel();
    //set11ZoomLevel();
	
    l1.onRelease = function(){ 
	if(pagenr > 1) {
	    pagenr = pagenr - 1;
	    setPageNr();
	}
    };
    r1.onRelease = function(){ 
	if(pagenr < swf._totalframes) {
	    pagenr = pagenr + 1;
	    setPageNr();
	}
    };
    p1.onRelease = function(){ 
	if(zoom < 4) {
	    zoom = zoom + 1;
	    setZoomLevel();
	}
    };
    m1.onRelease = function(){ 
	if(zoom > 1) {
	    zoom = zoom - 1;
	    if(zoom < 1)
		zoom = 1;
	    setZoomLevel();
	}
    };
    o1.onRelease = function(){ 
	setNoScrollZoomLevel();
    };
    refreshDrag = function(){
	if(Dragging == "h") {
	    swf._x = left + (10-hscrollbar._x)*scrollxrange/scrollbarxrange;
	} else if(Dragging == "v") {
	    swf._y = top + (40-vscrollbar._y)*scrollyrange/scrollbaryrange;
	} else if(Dragging == "xy") {
	    swfpos2scrollbars();
	}
    };
    dragrefresh = setInterval(refreshDrag, 20);
.end

.end