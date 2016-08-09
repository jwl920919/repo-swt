<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Log in</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="../resources/bootstrap/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="../resources/bootstrap/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="../resources/dist/css/AdminLTE.min.css">
<!-- font style -->
<link rel="stylesheet" href="../resources/dist/css/css.css">
<!-- iCheck -->
<link rel="stylesheet" href="../resources/plugins/iCheck/square/blue.css">
<!-- default style -->
<link rel="stylesheet" href="../resources/css/default.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <![endif]-->

<!-- jQuery 2.2.0 -->
<script src="../resources/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="../resources/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="../resources/plugins/iCheck/icheck.min.js"></script>

<script src="../resources/js/base/jquery-2.2.4.min.js"></script>
<script src="../resources/js/common/Common.js"></script>
<script src="../resources/js/common/multyLanguages.js"></script>
<script src="../resources/js/login/login.js"></script>

<script type="text/javascript">
</script>
</head>
<body class="hold-transition login-page">
		
	<!-- Alert Start -->
	<div id="layDiv" style="visibility : hidden;">
		<div class="alert-box" ></div>
		<div id="divAlertArea"></div>
	</div>
	<div style="display:none" id="hiddenError" >${errorMessage}</div>
	<!-- Alert End -->
	
	<div class="login-box">
		<div class="login-logo">
			<a href="#"><b><%=LanguageHelper.GetLanguage("Login")%></b></a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg"><%=LanguageHelper.GetLanguage("Signintostartyoursession")%></p>

			<form id="loginForm" method="post">
				<div class="form-group has-feedback">
					<!-- 					<input type="email" class="form-control" -->
					<%-- 						placeholder="<%=Language.GetLanguage("ID")%>"> <span --%>
					<!-- 						class="glyphicon glyphicon-envelope form-control-feedback"></span> -->
					<input type="text" class="form-control" id="txtID" name="txtID"
						placeholder="<%=LanguageHelper.GetLanguage("ID")%>" value=${txtID}><span
						class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" id="txtPassword"
						name="txtPassword"
						placeholder="<%=LanguageHelper.GetLanguage("Password")%>">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<!-- 						<div class="checkbox icheck"> -->
						<!-- 							<label> <input type="checkbox"> Remember Me -->
						<!-- 							</label> -->
						<!-- 						</div> -->
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat"
							id="bLogin" name="bLogin"><%=LanguageHelper.GetLanguage("Login")%></button>
					</div>
				</div>
			</form>

			<a href="#"><%=LanguageHelper.GetLanguage("Iforgotmypassword")%></a><br>
			<a href="register.html" class="text-center"><%=LanguageHelper.GetLanguage("Registermembership")%></a>
			<br/>
			<input name="rLanguage" type="radio" value="ko-KR"> <%=LanguageHelper.GetLanguage("ko-KR")%>
			<input name="rLanguage" type="radio" value="en-US"> <%=LanguageHelper.GetLanguage("en-US")%>

		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->
</body>

</html>
