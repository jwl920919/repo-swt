
//-- [다국어]코드별 다국어(아래 스크립트 추가)
//<script src="../resources/js/common/multyLanguages.js"></script>
//<script src="../resources/js/main/main.js"></script>
function getLanguage(key_cd) {
	
  var url = "/resources/Language/ko-KR.txt";

//  alert("multyLanguage");
//  alert(getCookie("Language"));
  if (getCookie("Language") == "en-US")
      url = "/resources/Language/en-US.txt";
  else if (getCookie("Language") == "ch-CN") {
      url = "/resources/Language/ch-CN.txt";
  }
  else {
      url = "/resources/Language/ko-KR.txt";
  }
  var rtn_data;

  $.ajax({
      dataType: "json",
      url: url,
      async: false,
      success: function (data) {
          rtn_data = eval("data." + key_cd);
      },
      error: function (request, status, error) {
          //alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);;
          alert("message:" + error);
      }
  });

  return rtn_data
}