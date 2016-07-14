<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page import="java.util.List"%>
<%@ page import="Common.DTO.SYSTEM_USER_GROUP_DTO"%>
<%@ page import="Common.DTO.SITE_INFO_DTO"%>
<%@ page import="org.springframework.beans.factory.annotation.Value"%>
<%@ page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page
	import="org.springframework.web.context.support.SpringBeanAutowiringSupport"%>
<%@ page
	import="Common.ServiceInterface.SYSTEM_USER_GROUP_INFO_Service_interface"%>
<%@ page import="Common.ServiceInterface.SITE_INFO_Service_interface"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%!public void jspInit() {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}

	@Autowired
	private SYSTEM_USER_GROUP_INFO_Service_interface systemUserGroupInfoService;
	@Autowired
	private SITE_INFO_Service_interface siteInfoService;%>
<%
	final List<SYSTEM_USER_GROUP_DTO> systemUserGroupInfoList = systemUserGroupInfoService
			.select_SYSTEM_USER_GROUP_INFO();
	final List<SITE_INFO_DTO> siteInfoList = siteInfoService.select_SITE_INFO();
%>
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
							<th width="30%">그룹명</th>
							<th width="30%">설명</th>
						</tr>
					</thead>
					<tbody>
						<%
							for (int i = 0; i < 100; i++) {
						%>
						<tr>
							<td></td>
							<td>1</td>
							<td>2</td>
							<td>3</td>
						</tr>
						<%
							}
						%>
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
						<%
							for (int i = 0; i < 100; i++) {
						%>
						<tr>
							<td></td>
							<td>1</td>
							<td>2</td>
							<td>3</td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</section>
<script src="resources/js/configMangement/systemGroupManagement.js"></script>
