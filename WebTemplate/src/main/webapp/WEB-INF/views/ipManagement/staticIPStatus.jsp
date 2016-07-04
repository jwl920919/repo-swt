<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../resources/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="../resources/js/common/Common.js"></script>
<script src="../resources/js/common/multyLanguages.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//Content Header 설정
		$("#contentHeaderDepth1li").attr('style', 'display: display;');
		$("#contentHeaderDepth2li").attr('style', 'display: display;');
		$("#contentHeaderDepth1").text(getLanguage("ipManagement"));
		$("#contentHeaderDepth2").text(getLanguage("staticIPStatus"));
	});
</script>
</head>
<body>고정 아이피 현황
</body>
</html>