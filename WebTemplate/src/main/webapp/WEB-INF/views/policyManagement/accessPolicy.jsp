<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="stylesheet"
	href="resources/css/policyManagement/accessPolicy.css">
<!-- Alert Start -->
<div id="layDiv">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>
<section class="white-paper" style="text-align: center">
	<div class="row">
		<div class="col-xs-12"
			style="padding-left: 10px; padding-right: 10px;">
			<div class="box-header" style="text-align: left;">
				<i class="fa fa-cog"></i>
				<h3 class="box-title-small">제한 정책</h3>
			</div>
			<div class="box-body">
				<div class="col-xs-12" id="modify-area">
					<label class="modify-label">우선순위</label><input type="text"
						id="priority" /> <label class="modify-label js-example-disabled-results">사업장</label><select id="site"></select><label class="modify-label">벤더</label><input
						type="text" id="vendor" /><label class="modify-label">모델</label><input
						type="text" id="model" /><label class="modify-label">장비종류</label><input
						type="text" id="device-type" /><label class="modify-label">OS</label><input
						type="text" id="os" /><label class="modify-label">Hostname</label><input
						type="text" id="hostname" /><label class="modify-label">설명</label><input
						type="text" id="desc" /><label class="modify-label">정책</label><select id="policy">
						<option>Permit</option><option>Deny</option></select>&nbsp;&nbsp;<input type="button"
						value="수정" id="mod-btn" />
				</div>
				<center>
					<table name="datatable" id="accessPolicyTable"
						class="essential-table" style="width: 100%;">
						<thead>
							<tr>
								<th></th>
								<th>우선순위</th>
								<th>사업장</th>
								<th>벤더</th>
								<th>모델</th>
								<th>장비종류</th>
								<th>OS</th>
								<th>Hostname</th>
								<th>설명</th>
								<th></th>
								<th></th>
								<th>정책</th>
							</tr>
						</thead>
					</table>
					<div>
						<input id='delete-button' class='btn btn-primary'
							style="float: left;" type="button"
							value="<%=LanguageHelper.GetLanguage("delete")%>" />
					</div>
				</center>
				<div style="margin-bottom: 10px;"></div>
			</div>

		</div>
	</div>
</section>
<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/policyManagement/accessPolicy.js"></script>
