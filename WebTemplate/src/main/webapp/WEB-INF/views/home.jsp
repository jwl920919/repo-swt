<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="../resources/js/base/jquery-2.2.4.min.js"></script>

<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script>
	function fnload() {
	};

	function getLogin() {
		alert($("#testButton").val());
	}
	

	$(document).ready(function() {
		alert("document).ready");
		
	 	$("input[name=rLanguage]").bind("click", function () {
	 	 	alert("rLanguage");
	 	});
		
		
		
	});
</script>
</head>
<body onload='fnload();'>
<form action="summit.do" method="post">
	<h1>Home 1</h1>

	<P>The time on the server is ${serverTime}.</P>


	<P>Title - ${Title}.  (messageSource에서 가져온 값)</P>

	<P>MultyLanguage - ${sTitle}.  (LanguageMap에서 가져온 값)</P>

	<input type="button" id="testButton" name="testButton" value="테스트" onclick="getLogin();" />
	
	</P>
	
	<select id="languageSelect" name="languageSelect" >
		<option value="ko_KR" >한국어</option>
		<option value="en_US">영어</option>
	</select>
	</P>
	<input type="submit" id="ibuttons" name="ibuttons" value="Submit" />
	
	
				<input name="rLanguage" type="radio" value="ko-KR">ko-KR
				<input name="rLanguage" type="radio" value="en-US">en-US
</form>
</body>
</html>
