<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page session="false"%>
<html>
<head>
<title>Home2</title>
<meta http-equiv="content-script-type" content="text/javascript" />
<script type="text/javascript" src="resources/Scripts/jquery-1.10.1.js"></script>
<script type="text/javascript"
	src="resources/Scripts/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="resources/Scripts/jquery-2.2.4.js"></script>
<script type="text/javascript"
	src="resources/Scripts/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="resources/Scripts/Common.js"></script>
<script type="text/javascript"
	src="resources/Scripts/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript"
	src="resources/Scripts/jquery.i18n.properties.min.js"></script>
<script type="text/javascript" src="resources/Scripts/MultyLanguages.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"
	type="text/javascript"></script>
<script>
	function fnload() {
	};

	function getLogin() {
		alert($("#testButton").val());
	}
</script>
</head>
<body onload='fnload();'>
<form action="summit.do" method="post">	
	<h1> <div id="home" name="home" >Home 2</div></h1>

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
</form>
</body>
</html>
