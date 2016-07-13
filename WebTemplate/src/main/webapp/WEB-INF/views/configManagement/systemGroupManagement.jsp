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
<link rel="stylesheet" href="resources/css/configMangement/systemGroupManagement.css">
<!-- Alert Start -->
<div id="layDiv">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>
<section class="white-paper">
	<div class="col-md-6">
		<div id="placeOfBusinessBox" class="scroll-box">
			placeOfBusinessBox
		</div>
	</div>
	<div class="col-md-6">
		<div id="userGroupBox" class="scroll-box">
			userGroupBox
		</div>
	</div>
</section>
<script src="resources/js/configMangement/systemGroupManagement.js"></script>
