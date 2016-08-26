<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!-- <html> -->
<!-- <head> -->
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!-- <title>사용자 정의 그룹 설정</title> -->
<!-- Bootstrap 3.3.6 -->
<!-- <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css"> -->
<!-- Font Awesome -->
<!-- <link rel="stylesheet" -->
<!-- 	href="/resources/bootstrap/css/font-awesome.min.css"> -->
<!-- Ionicons -->
<!-- <link rel="stylesheet" href="/resources/bootstrap/css/ionicons.min.css"> -->
<!-- iCheck for checkboxes and radio inputs -->
<!-- <link rel="stylesheet" href="/resources/plugins/iCheck/all.css"> -->
<!-- Theme style -->
<!-- <link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css"> -->
<!-- <!-- AdminLTE Skins. Choose a skin from the css/skins -->
<!--        folder instead of downloading all of them to reduce the load. -->
<!-- <link rel="stylesheet" -->
<!-- 	href="/resources/dist/css/skins/_all-skins.min.css"> -->
<!-- page essential.css -->
<!-- <link rel="stylesheet" href="/resources/css/essential.css"> -->
<!-- default style -->
<!-- <link rel="stylesheet" href="/resources/css/default.css"> -->
<!-- jstree -->
<link rel="stylesheet"
	href="/resources/plugins/jstree/themes/default/style.min.css">
<style type="text/css">
#content-tree-line { 
	width: 2px; 
	background-color: #05639a; 
	float: left; 
	height: 100%; 
	position: absolute; 
	cursor: w-resize; 
} 

#content-tree { 
	min-width: 230px; 
	background-color: #FFFFFF; 
	float: left; 
	height: 100%; 
	position: absolute;
	overflow-y: auto; 
} 

#content-tree-wrapper { 
	background-color: #FFFFFF; 
	float: left; height : 100%; 
	position: absolute; 
	height: 100%; 
	overflow-y: auto;
} 
td {
vertical-align: middle !important;
}
.modal-content-header {
width:20%;
}
.modal-content-body {
width:80%;
}
</style>
<!-- </head> -->
<!-- <body> -->
	<!-- Edit modal -->
	<div class="modal modal-dialog" id="modify-modal">
		<div class="modal-content" name="modalContent">
			<div class="modal-header" div_mordal_header>
				<input type="button" class="close" name="modalClose"
					data-dismiss="modal" aria-label="Close" value="&times;" />
				<h4 class="modal-title">Device Information</h4>
			</div>
			<div id="modify-body" class="modal-body">
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Site</div>
					<div class="modal-content-body">
						<input type="text" class="form-control" id="site-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Vendor</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="vendor-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Model</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="model-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">OS</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="os-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Device</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="dtype-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Category</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="category-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Switch</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="switch-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">Port</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="port-txt" readOnly>
					</div>
				</div>
				<div class="input-group modal-input-group">
					<div class="input-group-addon modal-content-header">IP Status</div>
					<div class="modal-content-body">
						<input type="text" class="form-control"  id="ipstatus-txt" readOnly>
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
		<div id="content-tree">
			<div id="content-tree-body">
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
		</div>
		<div id="content-tree-line"></div>
		<div id="content-tree-wrapper">
			<section class="white-paper" id="white-paper"
				style="text-align: center; min-height: 100%; margin-top: 0px;">
				<div class="row hidden" id="defaultDiv">
					<div class="col-lg-12">
						<div class="box box-primary">
							<div class="pagebox-header with-border">
								<div>
									<table>
										<tr>
											<td><label id="parentNodes"
												style="font-size: 14px; color: #777;"></label> <label
												id="currentNode" style="font-size: 14px;"></label></td>
										</tr>
									</table>
								</div>
								<div class="box-header">
									<div>
										<table>
											<tr>
												<td><label
													style="width: 80px; margin-right: 10px; text-align: right">디바이스
														: </label></td>
												<td><select id="deviceSel"
													class="form-control selectoption_grey_color"
													style="width: 210px">
												</select></td>
												<td><input type="text"
													style="width: 250px; margin-left: 15px;"
													class="form-control" id="txtSearch" placeholder="검색"></td>
												<td
													style="width: 100%; text-align: right; padding-right: 10px">
													<button type="button" class="btn btn-primary"
														id="btnSearch"><%=LanguageHelper.GetLanguage("inquiry")%></button>
												</td>
											</tr>
										</table>
									</div>
								</div>
								<table name="datatable" id="parent-table"
									class="essential-table" style="width: 98%;" align="center">
									<thead>
										<tr>
											<th style="min-width: 180px;">IP</th>
											<th id="mac-or-duid">MAC</th>
											<th width="40%">Hostname</th>
											<th width="40%">OS</th>
											<th width="20%">Device</th>
											<th style="min-width: 50px;"></th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>

<!-- </body> -->
<!-- jQuery 2.2.0 -->
<!-- <script src="/resources/plugins/jQuery/jQuery-2.2.0.min.js"></script> -->
<!-- Bootstrap 3.3.6 -->
<!-- <script src="/resources/bootstrap/js/bootstrap.min.js"></script> -->
<!-- AdminLTE App -->
<!-- <script src="/resources/dist/js/app.min.js"></script> -->
<!-- iCheck 1.0.1 -->
<!-- <script src="/resources/plugins/iCheck/icheck.min.js"></script> -->

<!-- <script src="/resources/js/base/jstz-1.0.4.min.js"></script> -->
<!-- <script src="/resources/js/common/Common.js"></script> -->
<!-- <script src="/resources/js/common/multyLanguages.js"></script> -->

<script src="/resources/plugins/jstree/jstree.min.js"></script>
<!-- <script src="/resources/plugins/datatables/jquery.dataTables.min.js"></script> -->
<!-- <script src="/resources/plugins/datatables/dataTables.bootstrap.min.js"></script> -->
<script src="/resources/js/common/Datatable-Essential.js"></script>
<!-- <script src="/resources/js/common/modalPopup.js"></script> -->
<script src="/resources/js/management/viewNodeInfo.js"></script>

<!-- </html> -->