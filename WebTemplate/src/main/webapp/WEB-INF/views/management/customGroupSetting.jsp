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
					<div class="box-body">
						<div id="container" style="text-align:left;">
							<ul>
								<li>Root node
									<ul>
										<li>Child node 1</li>
										<li>Child node 2</li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>


	<!-- 
						<div class="form-group">
							<label>IPv4 mask:</label>
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-laptop"></i>
								</div>
								<input type="text" class="form-control"
									data-inputmask="'alias': 'ipv4_subnet'" ipv4>
							</div>
						</div>
						<div class="form-group">
							<label>IPv6 mask:</label>
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-laptop"></i>
								</div>
								<input type="text" class="form-control"
									data-inputmask="'alias': 'ipv6_subnet'" ipv6>
							</div>
						</div>

 -->

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
<script src="/resources/plugins/jstree/jstree.min.js"></script>
<script src="/resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/resources/js/common/Datatable-Essential.js"></script>
<script>
    //     $(function() {
    //         var ipv4 = $("[ipv4]").inputmask();
    //         var ipv6 = $("[ipv6]").inputmask();
    //     });
    $(function() {
        $('#container').jstree();
    });
</script>

</html>