$(document).ready(function() {
	$("#countData").text("0");
	setIn();
});
function ajaxCall() {
	console.log('인터벌 테스트');
	var param = "";
	param += "param1=" + $("#countData").text();
    param += "&param2=" + $("#timeData").text();
	
	$.ajax({
		url : "/ajaxPollingTest.do",
		data: param,
		type : "POST",
		dataType : "text",
		success : function(data) {
			//$('#timeData').text(data);
			var jsonObj = eval("(" + data + ')'); //JSonString 형식의 데이터를 Ojbect형식으로 변경
			if (jsonObj.result == true) {

//				결과 값에 따라 리턴 데이터형식이 달라진다.
//				{"result":true,"code":0,"data":{"param1":1,"param2":"Thu Jun 23 17:36:39 KST 2016"}}
//				$('#countData').text(jsonObj.data.param1);
//				$('#timeData').text(jsonObj.data.param2);
				
//				{"result":true,"code":0,"data":[{"param1":1,"param2":"Thu Jun 23 17:53:05 KST 2016"}]}
//				$('#countData').text(jsonObj.data[0].param1);
//				$('#timeData').text(jsonObj.data[0].param2);
				
				$.each(jsonObj.data, function (index, obj) {
					$('#countData').text(obj.param1);
					$('#timeData').text(obj.param2);
	            });
			}
		}
	});
}

function setIn() {
	setInterval(ajaxCall, 100);
}
