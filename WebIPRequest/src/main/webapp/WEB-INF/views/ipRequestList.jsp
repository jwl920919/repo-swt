<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>
<%
	String userID = (String) session.getAttribute("user_id");
	String userName = (String) session.getAttribute("user_name");
	String siteid = (String) session.getAttribute("site_id");
	String siteName = (String) session.getAttribute("site_name");
	String siteMaster = (String) session.getAttribute("site_master");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Smart IPM+</title>
<meta http-equiv="Content-Type" content="text/html">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="resources/bootstrap/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="resources/bootstrap/css/ionicons.min.css">
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="resources/plugins/iCheck/all.css">
<!-- Select2 -->
<link rel="stylesheet" href="resources/plugins/select2/select2.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="resources/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="resources/dist/css/skins/_all-skins.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="resources/plugins/iCheck/flat/blue.css">
<!-- Morris chart -->
<!-- <link rel="stylesheet" href="resources/plugins/morris/morris.css"> -->
<!-- jvectormap -->
<link rel="stylesheet"
	href="resources/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<!-- Date Picker -->
<link rel="stylesheet"
	href="resources/plugins/datepicker/datepicker3.css">
<!-- Daterange picker -->
<link rel="stylesheet"
	href="resources/plugins/daterangepicker/daterangepicker-bs3.css">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet"
	href="resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<!-- page essential.css -->
<link rel="stylesheet" href="resources/css/essential.css">
<!-- default style -->
<link rel="stylesheet" href="resources/css/default.css">

<script src="resources/js/base/jquery-1.12.3.js"></script>
<script src="resources/js/base/jstz-1.0.4.min.js"></script>
<script src="resources/js/common/Common.js"></script>
<script src="resources/js/common/multyLanguages.js"></script>
<!-- jQuery 2.2.0 -->
<script src="resources/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->

<script src="resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
	$.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.6 -->
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<!-- Morris.js charts -->
<!-- <script -->
<!-- 	src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script> -->
<!-- <script src="resources/plugins/morris/morris.min.js"></script> -->
<!-- Sparkline -->
<script src="resources/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script
	src="resources/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script
	src="resources/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- jQuery Knob Chart -->
<script src="resources/plugins/knob/jquery.knob.js"></script>
<!-- daterangepicker -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<script src="resources/plugins/daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="resources/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script
	src="resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="resources/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="resources/plugins/fastclick/fastclick.js"></script>
<script src="resources/plugins/iCheck/icheck.min.js"></script>
<!-- AdminLTE App -->
<script src="resources/dist/js/app.min.js"></script>
<script src="resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">
	var siteid = "<%= siteid %>";
	var siteMaster = "<%= siteMaster %>";
</script>
<script src="resources/js/main/ipRequestList.js"></script>
</head>
<body class="hold-transition skin-purple-light sidebar-mini">
	<!-- Alert Start -->
	<div id="layDiv" style="visibility : hidden;">
		<div class="alert-box"></div>
		<div id="divAlertArea"></div>
	</div>

	<div class="wrapper">
		<header class="main-header"> <!-- 상단 우측 로고 영역 시작 --> <!-- Logo -->
			<a href="/ipRequestMain" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><div
						style="margin-top: 15px; margin-left: 7px;"
						class="glyphicon glyphicon-forward"></div></span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>Shinwoo</b>TNS</span>
			</a> <!-- Header Navbar: style can be found in header.less --> <!-- 상단 우측 로고 영역 끝 -->
	
			<!-- Sidebar toggle button Start -->
			<nav
				class="navbar navbar-static-top"> <a href="#"
				class="sidebar-toggle" data-toggle="offcanvas" role="button"> <span
				class="sr-only">Toggle navigation</span></a> <!-- Sidebar toggle button End -->
			</nav>
		</header>

		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar">		
		<ul class="sidebar-menu">
			<li class="treeview">
				<a href="/ipRequestMain"> <i class="fa fa-edit"></i>
					<span><%=LanguageHelper.GetLanguage("askIP")%></span>
				</a>
			</li>
			<li class="treeview">
				<a href="/ipRequestList"> <i class="fa fa-table"></i>
					<span><%=LanguageHelper.GetLanguage("askIPStatus")%></span>
				</a>
			</li>
		</ul>
		</section> <!-- /.sidebar --> </aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper" >
			<!-- Content Header (Page header) -->
			<section class="content-header">
			<h1>
				<LABEL id="contentTitle"></LABEL> <small><LABEL id="contentTitleSmall"></LABEL></small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="/ipRequestMain"> <i class="fa fa-edit"></i><%=LanguageHelper.GetLanguage("askIP")%>
				</a></li>
			</ol>
			</section>

			<!-- Main content -->
			<section class="content">
			<div id="content_frame" style="width: 100%; height: 100%">				
				
				<section class="white-paper">
					<div class="row" id="defaultDiv">
						<div class="col-lg-12">
							<div class="box box-primary">
								<div class="box-header">
									<table id="datatable" name="datatable" class="essential-table" style="width: 98%" align="center">
										<thead>
											<tr>
<!-- 												<th width="0%" style="display:none;">seq</th> -->
<!-- 												<th width="0%">"seq"</th> -->
<!-- 												<th width="0%">"user_site_id"</th> -->
<!-- 												<th width="0%">"user_id"</th> -->
												<th width="6%"><%=LanguageHelper.GetLanguage("applicant")%></th>
												<th width="7%"><%=LanguageHelper.GetLanguage("applyPhone")%></th>
<!-- 												<th width="0%">"apply_site_id"</th> -->
												<th width="7%"><%=LanguageHelper.GetLanguage("ask")%> <%=LanguageHelper.GetLanguage("site")%></th>
<!-- 												<th width="5%">"apply_static_ip_type"</th> -->
												<th width="9%"><%=LanguageHelper.GetLanguage("ask")%> <%=LanguageHelper.GetLanguage("ip")%></th>
<!-- 												<th width="0%">"apply_static_ip_num"</th> -->
												<th width="8%"><%=LanguageHelper.GetLanguage("ask")%> <%=LanguageHelper.GetLanguage("starttime")%></th>
												<th width="8%"><%=LanguageHelper.GetLanguage("ask")%> <%=LanguageHelper.GetLanguage("endtime")%></th>
												<th width="14%"><%=LanguageHelper.GetLanguage("askDesc")%></th>
												<th width="7%"><%=LanguageHelper.GetLanguage("ask")%> <%=LanguageHelper.GetLanguage("datetime")%></th>
												<th width="7%"><%=LanguageHelper.GetLanguage("ask")%> <%=LanguageHelper.GetLanguage("status")%></th>
<!-- 												<th width="0%">"settlement_chief_id"</th> -->
<%-- 												<th width="5%"><%=LanguageHelper.GetLanguage("settlementChief")%></th> --%>
<%-- 												<th width="10%"><%=LanguageHelper.GetLanguage("settlementDesc")%></th> --%>
<%-- 												<th width="6%"><%=LanguageHelper.GetLanguage("settlementTime")%></th> --%>
<!-- 												<th width="5%">"issuance_ip_type"</th> -->
												<th width="9%"><%=LanguageHelper.GetLanguage("issuanceIP")%></th>
<!-- 												<th width="0%">"issuance_ip_num"</th> -->
												<th width="8%"><%=LanguageHelper.GetLanguage("approval")%> <%=LanguageHelper.GetLanguage("starttime")%></th>
												<th width="8%"><%=LanguageHelper.GetLanguage("approval")%> <%=LanguageHelper.GetLanguage("endtime")%></th>
												<th width="2%"></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</section>

			</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<footer class="main-footer">
		<div id="divVersion" name="divVersion" class="pull-right hidden-xs">
		</div>
		<div style="text-align: center">
			<strong>Copyright &copy; 2016 <a
				href="http://www.shinwootns.com/">Shinwoo Total Network System
					co,LTD</a>.
			</strong> All rights reserved.
		</div>
		</footer>		
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->
		
	<!-- 하위 Datatable-Essential.js파일은 page의 datatable테그보다 하위에 위치해야 한다. -->
	<script src="resources/js/common/Datatable-Essential.js"></script>
</body>
</html>