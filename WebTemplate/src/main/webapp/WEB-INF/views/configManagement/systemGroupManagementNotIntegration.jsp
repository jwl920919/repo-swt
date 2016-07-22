<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet"
	href="resources/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet"
	href="resources/css/configMangement/systemGroupManagement.css">
<!-- Alert Start -->
<div id="layDiv">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>
<section class="white-paper" style="text-align: center">
	<div class="row" style="width: 100%">
		<div class="col-md-12">
			<div id="userGroupBox" class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">그룹관리</h3>
					<!-- tools box -->
				</div>
				<table id="userGroupTable" class="essential-table"
					style="width: 98%">
					<thead>
						<tr>
							<th width="10%"><input name="userGroupTable_select_all"
								id="userGroupTable_checkbox_controller" type="checkbox" /></th>
							<th width="30%">사업장명</th>
							<th width="30%">그룹명</th>
							<th width="30%" style="border-right-style: hidden !important;">설명</th>
							<th class="hide_column">id</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>

				<div class="bottom-box">
					<div
						style="width: 100%; margin-top: 5px; margin-bottom: 5px; height: 30px;">
						<label style="position: absolute; left: 5px;"> <input
							id='userGroupTable-delete-button' class='btn btn-primary'
							type="button" value="<%=LanguageHelper.GetLanguage("delete")%>" />
						</label> <label style="position: absolute; right: 5px;"> <input
							id='userGroupTable-add-button' class='btn btn-primary'
							type="button" value="<%=LanguageHelper.GetLanguage("add")%>" />
							<input id='userGroupTable-modify-button' class='btn btn-primary'
							type="button" value="<%=LanguageHelper.GetLanguage("modify")%>" />
						</label>
					</div>
					<label class="box-row-label" style="margin-left: 5px;">사업장명
						: <select id='group-table-pob-select' class="box-row-textfiled"
						style="width: 180px; height: 26px;">
					</select>
					</label> <label class="box-row-label">그룹명 : <input
						id='group-table-group-text' class="box-row-textfiled" type="text" /></label>
					<label class="box-row-label">설명 : <input
						id='group-table-desc-text' class="box-row-textfiled" type="text" /></label>
					<input id="group-id-text" type="hidden" />
				</div>
			</div>


		</div>
	</div>
</section>
<script src="resources/js/configMangement/systemGroupManagement.js"></script>
