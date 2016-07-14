<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page import="Common.ServiceInterface.SITE_INFO_Service_interface"%>
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
		<div class="col-md-6">
			<div id="placeOfBusinessBox" class="box">
				<table id="placeOfBusinessTable" class="essential-table"
					style="width: 98%">
					<thead>
						<tr>
							<th width="10%"><input name="placeOfBusinessTable_select_all"
								id="placeOfBusinessTable_checkbox_controller" type="checkbox" /></th>
							<th width="30%">사업장명</th>
							<th width="30%">사업장코드</th>
							<th width="30%">설명</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-6">
			<div id="userGroupBox" class="box">
				<table id="userGroupTable" class="essential-table"
					style="width: 98%">
					<thead>
						<tr>
							<th width="10%"><input name="userGroupTable_select_all"
								id="userGroupTable_checkbox_controller" type="checkbox" /></th>
							<th width="30%">사업장명</th>
							<th width="30%">그룹명</th>
							<th width="30%">설명</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</section>
<script src="resources/js/configMangement/systemGroupManagement.js"></script>
