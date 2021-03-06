<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="stylesheet"
	href="resources/css/policyManagement/accessPolicy.css">
<!-- Select2 -->
<link rel="stylesheet" href="resources/plugins/select2/select2.min.css">
<!-- Alert Start -->
<div id="layDiv" style="visibility: hidden;">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>

<!-- Modify modal -->
<div class="modal modal-dialog" id="modify-modal">
	<div class="modal-content" name="modalContent">
		<div class="modal-header" div_mordal_header>
			<input type="button" class="close" name="modalClose"
				data-dismiss="modal" aria-label="Close" value="&times;" />
			<h4 class="modal-title">정책 수정</h4>
		</div>
		<div id="modify-body" class="modal-body"></div>
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
			<h4 class="modal-title">정책 추가</h4>
		</div>
		<div id="add-body" class="modal-body"></div>
		<div class="modal-footer">
			<input type="button" class="btn btn-default pull-left"
				name="modalClose" data-dismiss="modal"
				value="<%=LanguageHelper.GetLanguage("close")%>" />
			<button type="button" class="btn btn-primary" id="add-save-btn"><%=LanguageHelper.GetLanguage("saveandclose")%></button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<!-- /.Add modal -->
<!-- DELETE modal -->
<div class="modal modal-dialog" id="delete-modal">
	<div class="modal-content" name="modalContent">
		<div class="modal-header" div_mordal_header>
			<input type="button" class="close" name="modalClose"
				data-dismiss="modal" aria-label="Close" value="&times;" />
			<h4 class="modal-title">정책 삭제</h4>
		</div>
		<div id="delete-body" class="modal-body">선택하신 정책을 삭제 하시겠습니까?</div>
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
<!-- /.modalbackDiv -->
<section class="white-paper" style="text-align: center">
	<div class="row">
		<div class="col-xs-12"
			style="padding-left: 10px; padding-right: 10px;">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">제한 정책</h3>
				</div>
				<div class="box-body">
					<center>
						<table name="datatable" id="accessPolicyTable"
							class="essential-table" style="width: 100%;">
							<thead>
								<tr>
									<th><input name="select_all"
										id="accessPolicyTable_checkbox_controller" type="checkbox" /></th>
									<th>우선순위</th>
									<th>사업장</th>
									<th>벤더</th>
									<th>모델</th>
									<th>장비종류</th>
									<th>OS</th>
									<th>Hostname</th>
									<th>설명</th>
									<th>access_policy_id</th>
									<th>site_id</th>
									<th>os_like</th>
									<th>device_type_like</th>
									<th>hostname_like</th>
									<th>model_like</th>
									<th>vendor_like</th>
									<th>정책</th>
									<th></th>
								</tr>
							</thead>
						</table>
						<!-- 					style='bottom: 13px; position: absolute; width: 98.4%; left: 9px;' -->
						<div id="bottom-btn-row" class='row'>
							<div class='col-sm-1'
								style='padding-left: 14px; margin-top: 4px;'>
								<input id='delete-button' class='btn btn-primary'
									style="float: left;" type="button"
									value="<%=LanguageHelper.GetLanguage("delete")%>" />
							</div>
							<div class='col-sm-10' id='page-box'></div>
							<div class='col-sm-1'
								style='padding-right: 14px; margin-top: 4px;'>
								<input id='add-button' class='btn btn-primary'
									style="float: right;" type="button"
									value="<%=LanguageHelper.GetLanguage("add")%>" />
							</div>
						</div>
					</center>
					<div style="margin-bottom: 17px;"></div>
				</div>
			</div>
		</div>
	</div>
</section>
<script src="resources/js/common/modalPopup.js"></script>
<script src="resources/plugins/select2/select2.full.min.js"></script>
<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/policyManagement/accessPolicy.js"></script>
