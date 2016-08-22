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
	<!-- Add, Edit modal -->
	<div class="modal modal-dialog" id="modify-modal">
		<div class="modal-content" name="modalContent">
			<div class="modal-header" div_mordal_header>
				<input type="button" class="close" name="modalClose"
					data-dismiss="modal" aria-label="Close" value="&times;" />
				<h4 class="modal-title">Network Name Modify</h4>
			</div>
			<div id="modify-body" class="modal-body">
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Network</div>
					<div class="modal-content-body">
						<input type="text" class="form-control" id="network-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Name</div>
					<div class="modal-content-body">
						<input type="text" class="form-control" id="name-txt"
							placeholder="Name">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-left"
					name="modalClose" data-dismiss="modal"
					value="<%=LanguageHelper.GetLanguage("close")%>" />
				<button type="button" class="btn btn-primary" id="modify-save-btn"><%=LanguageHelper.GetLanguage("saveandclose")%></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>

	<!-- modalbackDiv -->
	<div id="modalbackDiv" class="mordal-back-box"></div>
	<div class="content-tree"
		style="position: absolute; top: 0; left: 0; min-height: 100%; width: 230px; z-index: 810; border-radius: 3px; background: #ffffff; border-top: 3px solid #3c8dbc; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1); white-space: nowrap; overflow: auto;">
		<div class="treebox-header" style="margin: 6px;">
			<i class="fa fa-cog"
				style="display: inline-block; font-size: 18px; margin: 0; line-height: 1;"></i>
			<h3
				style="display: inline-block; font-size: 16px; margin: 0; line-height: 1;">Network</h3>
		</div>
		<div class="treebox-body">
			<div id="container" style="text-align: left;"></div>
		</div>
	</div>
	<div class="content-tree-wrapper"
		style="margin-left: 230px; min-height: 100%;">
		<section class="white-paper"
			style="text-align: center; min-height: 100%; margin-top: 0px;">
			<!-- 			<div style="width: 100%; height: 34px;"> -->
			<!-- 				<div> -->
			<!-- 					<select class="form-control "></select> -->
			<!-- 				</div> -->
			<!-- 				<div> -->
			<!-- 					<table class="form-control "> -->
			<!-- 						<tr> -->
			<!-- 							<th width=>Device Search Status</th> -->
			<!-- 							<td>Success</td> -->
			<!-- 							<td>10</td> -->
			<!-- 							<td>Failure</td> -->
			<!-- 							<td>2</td> -->
			<!-- 							<td>Total</td> -->
			<!-- 							<td>4</td> -->
			<!-- 						</tr> -->
			<!-- 					</table> -->
			<!-- 				</div> -->
			<!-- 			</div> -->
			<table name="datatable" id="parent-table" class="essential-table" style="width: 100%;">
				<thead>
					<tr>
						<th>Network</th>
						<th>Name</th>
						<th>Children</th>
					</tr>
				</thead>
			</table>
		</section>
	</div>

</body>
<!-- jQuery 2.2.0 -->
<script src="/resources/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/resources/bootstrap/js/bootstrap.min.js"></script>
<!-- InputMask -->
<!-- <script src="/resources/plugins/input-mask/jquery.inputmask.js"></script> -->
<!-- <script -->
<!-- 	src="/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script> -->
<!-- <script -->
<!-- 	src="/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script> -->
<!-- <script -->
<!-- 	src="/resources/plugins/input-mask/jquery.inputmask.regex.extensions.js"></script> -->
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
<script src="/resources/js/common/modalPopup.js"></script>
<script src="/resources/js/management/viewNodeInfo.js"></script>
<script>
    
</script>

</html>