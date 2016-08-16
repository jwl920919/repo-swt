<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Smart IPM Plus</title>
</head>

<body>

	<h1>Smart IPM Plus - API documentation</h1>
	
    <table  cellpadding=5 cellspacing=0 border=1 style='border-collapse:collapse; border:1px gray solid;font-size:10pt;' width='100%'>
        
        <tr style='background-color: cyan; font-weight: bold;'>
	        <td width='80' align='center'>Type</td>
	        <td width='200' align='center'>URL</td>
	        <td width='20' align='center'>GET<br>(Read)</td>
	        <td width='20' align='center'>PUT<br>(Create)</td>
	        <td width='20' align='center'>DELETE<br>(Delete)</td>
	        <td width='20' align='center'>POST<br>(Update)</td>
	        <td width='400' align='center'>Parameters</td>
	        <td  align='center'>Description</td>
        </tr>
	
	    <c:forEach items="${contents}" var="item">
	     <tr>
	     	<td style='font-weight: bold;' align='center'>${item.typeName}</td>
	     	<td style='font-weight: bold;'>${item.url}</td>
	     	<td align='center'>
	     		<c:if test="${fn:contains(item.exec, 'R')}">O</c:if>
	     	</td>
	     	<td align='center'>
	        	<c:if test="${fn:contains(item.exec, 'C')}">O</c:if>
	        </td>
	        <td align='center'>
	        	<c:if test="${fn:contains(item.exec, 'U')}">O</c:if>
			</td>
	        <td align='center'>
	        	<c:if test="${fn:contains(item.exec, 'D')}">O</c:if>
	        </td>
	        <td>${item.params}</td>
	        <td>${item.desc}</td>
	     </tr>
		</c:forEach>
    
</body>

</html>