<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>노드 그룹 설정</title>
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
<%
	String siteMaster = (String) session.getAttribute("site_master");
	String siteID = (String) session.getAttribute("site_id");
%>
<style type="text/css">
.test-success {
	background: #b2f9cf;
}

.test-fail {
	background: #f9b2b2;
}

#progress_bar {
	margin: 10px 0;
	padding: 3px;
	border: 1px solid #000;
	font-size: 14px;
	clear: both;
	opacity: 0;
	-moz-transition: opacity 1s linear;
	-o-transition: opacity 1s linear;
	-webkit-transition: opacity 1s linear;
}

#progress_bar.loading {
	opacity: 1.0;
}

#progress_bar .percent {
	background-color: #99ccff;
	height: auto;
	width: 0;
}
</style>
<script>
	var siteMaster = "<%=siteMaster%>";
	var siteID = "<%=siteID%>";
</script>
</head>
<body>
	<!-- Edit modal -->
	<div class="modal modal-dialog" id="modify-modal">
		<div class="modal-content" name="modalContent">
			<div class="modal-header" div_mordal_header>
				<input type="button" class="close" name="modalClose"
					data-dismiss="modal" aria-label="Close" value="&times;" />
				<h4 class="modal-title">Network Name Modify</h4>
			</div>
			<div id="modify-body" class="modal-body">
				<div class="input-group modal-input-group hidden"
					id="group-table-site-body">
					<div class="input-group-addon modal-content-header">Site</div>
					<div class="modal-content-body">
						<input type="text" class="form-control" id="site-txt" readOnly>
					</div>
				</div>
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
	<!-- Add modal -->
	<div class="modal modal-dialog" id="add-modal">
		<div class="modal-content" name="modalContent">
			<div class="modal-header" div_mordal_header>
				<input type="button" class="close" name="modalClose"
					data-dismiss="modal" aria-label="Close" value="&times;" />
				<h4 class="modal-title">Network Name Add</h4>
			</div>

			<div id="modify-body" class="modal-body">
				<div class="input-group modal-input-group hidden"
					id="group-table-select-body">
					<div class="input-group-addon modal-content-header">Site</div>
					<div class="modal-content-body">
						<select id='group-table-select' class="form-control"></select>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Network</div>
					<div class="modal-content-body">
						<input type="text" class="form-control" id="add-network-txt">
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Name</div>
					<div class="modal-content-body">
						<input type="text" class="form-control" id="add-name-txt"
							placeholder="Name">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-left"
					name="modalClose" data-dismiss="modal"
					value="<%=LanguageHelper.GetLanguage("close")%>" />
				<button type="button" class="btn btn-primary" id="add-save-btn"><%=LanguageHelper.GetLanguage("add")%></button>
			</div>
		</div>
		<!-- /.Add-content -->
	</div>
	<!-- DELETE modal -->
	<div class="modal modal-dialog" id="delete-modal">
		<div class="modal-content" name="modalContent">
			<div class="modal-header" div_mordal_header>
				<input type="button" class="close" name="modalClose"
					data-dismiss="modal" aria-label="Close" value="&times;" />
				<h4 class="modal-title">Network Name Delete</h4>
			</div>
			<div id="delete-body" class="modal-body">선택하신 그룹정보를 삭제 하시겠습니까?</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-default pull-left"
					name="modalClose" data-dismiss="modal"
					value="<%=LanguageHelper.GetLanguage("close")%>" />
				<button type="button" class="btn btn-primary" id="delete-save-btn"><%=LanguageHelper.GetLanguage("delete")%></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.DELETE modal -->
	<!-- modalbackDiv -->
	<div id="modalbackDiv" class="mordal-back-box"></div>


	<section class="white-paper" style="text-align: center">
		<div class="row">
			<div class="col-xs-6">
				<div class="box box-primary">
					<div class="box-header" style="text-align: left;">
						<i class="fa fa-cog"></i>
						<h3 class="box-title-small">사용자정의 노드 그룹 설정</h3>
					</div>
					<div class="box-body" style="margin: 0px 5px;">
						<select id=ip-table_select
							style="vertical-align: bottom; height: 30px; visibility: hidden;">
							<option value=4 selected>IPV4</option>
							<option value=6>IPV6</option>
						</select>
						<table name="datatable" id="ip-table" class="essential-table"
							style="width: 100%;">
							<thead>
								<tr>
									<th width=50%>Network</th>
									<th width=50%>Name</th>
									<th class="hidden"></th>
									<th class="hidden"></th>
									<th class="hidden"></th>
									<th></th>
								</tr>
							</thead>
						</table>
						<div id="bottom-btn-row" class='row'>
							<div class='col-sm-1'
								style='padding-left: 14px; margin-top: 4px;'></div>
							<div class='col-sm-10' id='page-box'></div>
							<div class='col-sm-1'
								style='padding-right: 14px; margin-top: 4px;'>
								<input id='add-button' class='btn btn-primary'
									style="float: right;" type="button"
									value="<%=LanguageHelper.GetLanguage("add")%>" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="box box-primary">
					<div class="box-header" style="text-align: left;">
						<i class="fa fa-cog"></i>
						<h3 class="box-title-small">마이그레이션</h3>
					</div>
					<div class="box-body">
						<div style="text-align: left;">
							<input type="file" id="file" name="file" onchange='openFile(event)' /> 
							<div id="progress_bar"><div class="percent">0%</div></div>
							<input type="button" id="migration-btn" value="Migration" />
						</div>
					</div>
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
<script src="/resources/js/management/customGroupSetting.js"></script>
<script type="text/javascript">

</script>

</html>