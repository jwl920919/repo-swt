<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ include file="/tags/jquery-lib.jsp"%>
        
        <script type="text/javascript">
            $(function () {
            
                (function longPolling() {
                
                    $.ajax({
                        url: "${pageContext.request.contextPath}/communication/user/ajax.mvc",
                        data: {"timed": new Date().getTime()},
                        dataType: "text",
                        timeout: 5000,
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $("#state").append("[state: " + textStatus + ", error: " + errorThrown + " ]<br/>");
                            if (textStatus == "timeout") { // 요청
                                    longPolling(); // 재귀적 호출
                                
                                // 기타 버그, 오류 등 같은 네트워크
                                } else { 
                                    longPolling();
                                }
                            },
                        success: function (data, textStatus) {
                            $("#state").append("[state: " + textStatus + ", data: { " + data + "} ]<br/>");
                            
                            if (textStatus == "success") { // 요청 성공
                                longPolling();
                            }
                        }
                    });
                })();
                
            });
        </script>
    </head>

    
    <body>        
        <div id="logs"></div>
    </body>
</html>

