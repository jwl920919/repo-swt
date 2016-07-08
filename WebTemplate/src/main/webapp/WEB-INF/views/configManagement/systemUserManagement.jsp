<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SystemUserManagement</title>
<!-- DataTables -->
<link rel="stylesheet" href="resources/css/essential.css">
<link rel="stylesheet"
	href="resources/css/configMangement/systemUserManagement.css">
<script src="resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- script src="resources/plugins/datatables/jquery.tabledit.min.js"></script -->
</head>
<body>
	<!-- 
	<div class="box box-primary">
		<div class="box-body">
		 -->
	<section class="white-paper">
	<div class="row">
		<div class="col-md-6">
			<div class="box box-primary">
				<div class="box-body">
					<center>
						<table id="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th><input type="checkbox" /></th>
									<th width="48%"><%=LanguageHelper.GetLanguage("ID")%></th>
									<th width="48%"><%=LanguageHelper.GetLanguage("Name")%></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="col-sm-1">
							<input id='delete-button' class='btn btn-primary' type="button"
								value="<%=LanguageHelper.GetLanguage("delete")%>" />
						</div>
					</center>

				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="box box-primary">
				<div class="box-body">
					<form id="send-to-server">

						<!-- 최상단 Line -->
						<div class="send-rows-top">
							<div class="row">
								<div class="col-sm-6 send-top-row-left">
									<label id="add-label" class="selected-label"><%=LanguageHelper.GetLanguage("add")%></label>&nbsp;&nbsp;/&nbsp;&nbsp;
									<label id="modify-label"><%=LanguageHelper.GetLanguage("modify")%></label>
								</div>
								<div class="col-sm-6 send-top-row-right">
									<input id='add-button' class='btn btn-primary' type="button"
										value="<%=LanguageHelper.GetLanguage("add")%>" />
								</div>
							</div>
						</div>
						<div class="send-body">
							<!-- 아이디,패스워드,이름 -->
							<div class="send-rows-room">
								<div id="id-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("ID")%></label>
									<input type="text" id="idTxt" name="idTxt">
									<input type="button" value="<%=LanguageHelper.GetLanguage("checkRepetition")%>" />
								</div>
								<div id="pw-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("Password")%></label>
									<input type="password" id="passwordTxt" name="passwordTxt">
									<label class="send-label"><%=LanguageHelper.GetLanguage("checkPassword")%></label>
									<input type="password" id="passwordChkTxt" name="passwordChkTxt">
								</div>
								<div id="name-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("Name")%></label>
									<input type="text" id="nameTxt" name="nameTxt">
								</div>
							</div>
							<!-- 사용자그룹,사업장,부서,직급 -->
							<div class="send-rows-room">
								<div id="group-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("systemGroup")%></label>
									<input type="text" id="groupTxt" name="groupTxt">
								</div>
								<div id="pob-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("placeOfBusiness")%></label>
									<input type="text" id="placeOfBusinessTxt"
										name="placeOfBusinessTxt">
								</div>
								<div id="dept-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("department")%></label>
									<input type="text" id="departmentTxt" name="departmentTxt">
								</div>
								<div id="position-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("position")%></label>
									<input type="text" id="positionTxt" name="positionTxt">
								</div>
							</div>
							<!-- 전화,휴대폰,이메일 -->
							<div class="send-rows-room">
								<div id="email-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("email")%></label>
									<input type="text" id="emailTxt" name="emailTxt">
								</div>
								<div id="phone-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("phone")%></label>
									<input type="text" id="phoneTxt" name="phoneTxt">
								</div>
								<div id="mobile-row" class="send-row">
									<label class="send-label"><%=LanguageHelper.GetLanguage("mobile")%></label>
									<input type="text" id="mobileTxt" name="mobileTxt">
								</div>
							</div>
						</div>
						<div id="save-row" style="margin-top: 5px; text-align: right;">
							<button type="submit" class="btn btn-primary" id="save-button"
								name="save-button"><%=LanguageHelper.GetLanguage("save")%></button>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>
	</section>
	<!--
		 </div>
	</div> -->
	<script src="resources/js/common/Datatable-Essential.js"></script>
	<script src="resources/js/configMangement/systemUserManagement.js"></script>
</body>
</html>