<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="../resources/js/base/jquery-2.2.4.min.js"></script>
<script src="../resources/js/base/jstz-1.0.4.min.js"></script>
<script src="../resources/js/common/splitter.js"></script>
<script src="../resources/js/common/Common.js"></script>

<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script>
	function fnload() {
		var timezone = jstz.determine();
		// 		alert(timezone.name());

		// 		alert(getClientTimeZoneName());

	};

	function getLogin() {
		// 		alert($("#testButton").val());
	}

	
	
	var splitter,cont1,cont2;
	var last_x,window_width;
	$(document).ready(function() {
		// 		alert("document).ready");

		// 		$("input[name=rLanguage]").bind("click", function() {
		// 			alert("rLanguage");
		// 		});

		
		  window_width=window.innerWidth;
		  splitter=document.getElementById("splitter");
		  cont1=document.getElementById("div1");
		  cont2=document.getElementById("div2");
		  var dx=cont1.clientWidth;
		  splitter.style.marginLeft=dx+"px";
		  dx+=splitter.clientWidth;
		  cont2.style.marginLeft=dx+"px";
		  dx=window_width-dx;
		  cont2.style.width=dx+"px";
		  splitter.addEventListener("mousedown",spMouseDown);

	});
	
	function spMouseDown(e)
	{
	  splitter.removeEventListener("mousedown",spMouseDown);
	  window.addEventListener("mousemove",spMouseMove);
	  window.addEventListener("mouseup",spMouseUp);
	  last_x=e.clientX;
	}

	function spMouseUp(e)
	{
	  window.removeEventListener("mousemove",spMouseMove);
	  window.removeEventListener("mouseup",spMouseUp);
	  splitter.addEventListener("mousedown",spMouseDown);
	  resetPosition(last_x);
	}

	function spMouseMove(e)
	{
	  resetPosition(e.clientX);
	}

	function resetPosition(nowX)
	{
	  var dx=nowX-last_x;
	  dx+=cont1.clientWidth;
	  cont1.style.width=dx+"px";
	  splitter.style.marginLeft=dx+"px";
	  dx+=splitter.clientWidth;
	  cont2.style.marginLeft=dx+"px";
	  dx=window_width-dx;
	  cont2.style.width=dx+"px";
	  last_x=nowX;
	}
</script>
<style>
#div1
{
  width:45%;
  background-color:#220000;
  float:left;
  height:100%;
  position:absolute;
}
#div2
{
  width:15%;
  background-color:#000022;
  float:left;
  height:100%;
  position:absolute;
}
#splitter
{
  width:10px;
  background-color:#002200;
  float:left;
  height:100%;
  position:absolute;
  cursor:w-resize;
}
</style>
</head>
<body onload='fnload();'>
	<form action="summit.do" method="post">
		<h1>Home 1</h1>

		<P>The time on the server is ${serverTime}.</P>


		<P>Title - ${Title}. (messageSource에서 가져온 값)</P>

		<P>MultyLanguage - ${sTitle}. (LanguageMap에서 가져온 값)</P>

		<input type="button" id="testButton" name="testButton" value="테스트"
			onclick="getLogin();" />

		</P>

		<select id="languageSelect" name="languageSelect">
			<option value="ko_KR">한국어</option>
			<option value="en_US">영어</option>
		</select>
		</P>
		<input type="submit" id="ibuttons" name="ibuttons" value="Submit" />


		<input name="rLanguage" type="radio" value="ko-KR">ko-KR <input
			name="rLanguage" type="radio" value="en-US">en-US


  <div id="div1"></div>
  <div id="splitter"></div>
  <div id="div2"></div>


	</form>
</body>
</html>
