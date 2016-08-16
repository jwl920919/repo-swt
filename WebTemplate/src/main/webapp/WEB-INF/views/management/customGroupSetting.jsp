<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사용자 정의 그룹 설정</title>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="/resources/bootstrap/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="/resources/bootstrap/css/ionicons.min.css">
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="/resources/plugins/iCheck/all.css">
<!-- Theme style -->
<link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="/resources/dist/css/skins/_all-skins.min.css">
<!-- page essential.css -->
<link rel="stylesheet" href="/resources/css/essential.css">
<!-- default style -->
<link rel="stylesheet" href="/resources/css/default.css">
<!-- jstree -->
<link rel="stylesheet"
	href="/resources/plugins/jstree/themes/default/style.min.css">
</head>
<body>
	<section class="white-paper" style="text-align: center">
		<div class="row">
			<div class="col-xs-6">
				<div class="box box-primary">
					<div class="box-header" style="text-align: left;">
						<i class="fa fa-cog"></i>
						<h3 class="box-title-small">IP 기준</h3>
					</div>
					<div class="box-body" style="margin: 0px 5px;">
						<table name="datatable" id="ip-table" class="essential-table"
							style="width: 100%;">
							<thead>
								<tr>
									<th>Network</th>
									<th>그룹명</th>
									<!-- 								<th></th> -->
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="box box-primary">
					<div class="box-header" style="text-align: left;">
						<i class="fa fa-cog"></i>
						<h3 class="box-title-small">사용자 기준</h3>
					</div>
					<div class="box-body"></div>
				</div>
			</div>
		</div>
	</section>

</body>
<!-- jQuery 2.2.0 -->
<script src="/resources/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/resources/bootstrap/js/bootstrap.min.js"></script>
<!-- InputMask -->
<script src="/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script
	src="/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script
	src="/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script
	src="/resources/plugins/input-mask/jquery.inputmask.regex.extensions.js"></script>
<!-- AdminLTE App -->
<script src="/resources/dist/js/app.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="/resources/plugins/iCheck/icheck.min.js"></script>

<script src="/resources/js/base/jstz-1.0.4.min.js"></script>
<script src="/resources/js/common/Common.js"></script>
<script src="/resources/js/common/multyLanguages.js"></script>

<script src="/resources/plugins/jstree/jstree.min.js"></script>
<script src="/resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/resources/js/common/Datatable-Essential.js"></script>
<script src="/resources/js/management/customGroupSetting.js"></script>
<script>
    
</script>

</html>