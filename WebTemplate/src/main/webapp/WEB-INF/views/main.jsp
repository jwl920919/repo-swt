<%@ page language="java" contentType="text/html; charset=utf-8" %>
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
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="resources/bootstrap/css/font-awesome.min.css">
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
<link rel="stylesheet" href="resources/dist/css/skins/_all-skins.min.css">
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
<!-- resources/js/common/Datatable-Essential.js은 각 페이지에 넣어주지 않으면 #datatable 객체에 접근이 불가능 하여 각 페이지에 입력해주어야됨. -->
<script type="text/javascript" type="text/javascript">
$(document).ready(function() {
	var sessionMaintainCallTime = (1000 * 60) * 10;
	var m_sessionMaintainCallTime;
	
	//console.log("menu : " + "${menuHTML}");
	$("#ulMenuArea").append("${menuHTML}");
	$("#divVersion").append("<b>Version</b> 1.0.0");
	
	$("#contentTitle").text(getLanguage("dashboard"));
    //$("#contentTitleSmall").text("Control panel");
 	changeframe("/dashboard/dashboard", 'M01', 'dashboard', '');
 	
 	//로그인 사용사의 사업장 정보에 따라 메인 상단의 사업장 dropdown-menu 설정 
 	var siteMaster = "<%= siteMaster %>";
 	if (siteMaster == "t") {
		fnGetSiteInfo();
	}
 	else{
        $('#siteDropdown').attr("style","visibility: hidden");
        $('#siteDropdownLi').attr("disabled","true");
        $('#siteDropdownLi').removeClass("dropdown tasks-menu");
 	}

	clearInterval(m_sessionMaintainCallTime);
	m_sessionMaintainCallTime = setInterval(sessionMaintainAjaxCall, sessionMaintainCallTime);	
});

/**
 * 세션유지를 위해 서버에 주기적으로 Ajax Call을 하는 function
**/
function sessionMaintainAjaxCall() {	
	try
	{
		var tag = "";		    
	    $.ajax({
	        url : '/session_Maintain',
	        type : "POST",
	        data : null,
	        dataType : "text",
	        success : function(data) {
	            var jsonObj = eval("(" + data + ')');
		            if (jsonObj.result == true) {
		            	//console.log("Session Maintain true!.");
		            }
		            else{
		        		console.log("main.js sessionMaintainAjaxCall Error Log : Session Maintain fail!.");
		            }
	        },
	        complete: function(data) {
	        	//console.log(tag);
	        	$('#sbSegment').append(tag);
	        }
	    });
	    
	} catch (e) {
		console.log("leaseIPStatus.js $(document).ready Error Log : " + e.message);
	}
}
</script>
<script src="resources/js/main/main.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header"> <!-- 상단 우측 로고 영역 시작 --> <!-- Logo -->
		<a href="/main" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini"><div
					style="margin-top: 15px; margin-left: 7px;"
					class="glyphicon glyphicon-forward"></div></span> <!-- logo for regular state and mobile devices -->
			<span class="logo-lg"><b>Shinwoo</b>TNS</span>
		</a> <!-- Header Navbar: style can be found in header.less --> <!-- 상단 우측 로고 영역 끝 -->

		<!-- Sidebar toggle button Start --> <nav
			class="navbar navbar-static-top"> <a href="#"
			class="sidebar-toggle" data-toggle="offcanvas" role="button"> <span
			class="sr-only">Toggle navigation</span></a> <!-- Sidebar toggle button End -->

		<!-- 상단 좌측 메일, 공지, 작업 , 프로필 관련 영역 시작 -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- Messages: style can be found in dropdown.less-->
<!-- 				<li class="dropdown messages-menu"><a href="#" -->
<!-- 					class="dropdown-toggle" data-toggle="dropdown"> <i -->
<!-- 						class="fa fa-envelope-o"></i> <span class="label label-success">4</span> -->
<!-- 				</a> -->
<!-- 					<ul class="dropdown-menu"> -->
<!-- 						<li class="header">You have 4 messages</li> -->
<!-- 						<li> -->
<!-- 							inner menu: contains the actual data -->
<!-- 							<ul class="menu"> -->
<!-- 								<li> -->
<!-- 									start message <a href="#"> -->
<!-- 										<div class="pull-left"> -->
<!-- 											<img src="resources/dist/img/user2-160x160.jpg" -->
<!-- 												class="img-circle" alt="User Image"> -->
<!-- 										</div> -->
<!-- 										<h4> -->
<!-- 											Support Team <small><i class="fa fa-clock-o"></i> 5 -->
<!-- 												mins</small> -->
<!-- 										</h4> -->
<!-- 										<p>Why not buy a new awesome theme?</p> -->
<!-- 								</a> -->
<!-- 								</li> -->
<!-- 								end message -->
<!-- 								<li><a href="#"> -->
<!-- 										<div class="pull-left"> -->
<!-- 											<img src="resources/dist/img/user3-128x128.jpg" -->
<!-- 												class="img-circle" alt="User Image"> -->
<!-- 										</div> -->
<!-- 										<h4> -->
<!-- 											AdminLTE Design Team <small><i class="fa fa-clock-o"></i> -->
<!-- 												2 hours</small> -->
<!-- 										</h4> -->
<!-- 										<p>Why not buy a new awesome theme?</p> -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> -->
<!-- 										<div class="pull-left"> -->
<!-- 											<img src="resources/dist/img/user4-128x128.jpg" -->
<!-- 												class="img-circle" alt="User Image"> -->
<!-- 										</div> -->
<!-- 										<h4> -->
<!-- 											Developers <small><i class="fa fa-clock-o"></i> Today</small> -->
<!-- 										</h4> -->
<!-- 										<p>Why not buy a new awesome theme?</p> -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> -->
<!-- 										<div class="pull-left"> -->
<!-- 											<img src="resources/dist/img/user3-128x128.jpg" -->
<!-- 												class="img-circle" alt="User Image"> -->
<!-- 										</div> -->
<!-- 										<h4> -->
<!-- 											Sales Department <small><i class="fa fa-clock-o"></i> -->
<!-- 												Yesterday</small> -->
<!-- 										</h4> -->
<!-- 										<p>Why not buy a new awesome theme?</p> -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> -->
<!-- 										<div class="pull-left"> -->
<!-- 											<img src="resources/dist/img/user4-128x128.jpg" -->
<!-- 												class="img-circle" alt="User Image"> -->
<!-- 										</div> -->
<!-- 										<h4> -->
<!-- 											Reviewers <small><i class="fa fa-clock-o"></i> 2 days</small> -->
<!-- 										</h4> -->
<!-- 										<p>Why not buy a new awesome theme?</p> -->
<!-- 								</a></li> -->
<!-- 							</ul> -->
<!-- 						</li> -->
<!-- 						<li class="footer"><a href="#">See All Messages</a></li> -->
<!-- 					</ul></li> -->
<!-- 				Notifications: style can be found in dropdown.less -->
<!-- 				<li class="dropdown notifications-menu"><a href="#" -->
<!-- 					class="dropdown-toggle" data-toggle="dropdown"> <i -->
<!-- 						class="fa fa-bell-o"></i> <span class="label label-warning">10</span> -->
<!-- 				</a> -->
<!-- 					<ul class="dropdown-menu"> -->
<!-- 						<li class="header">You have 10 notifications</li> -->
<!-- 						<li> -->
<!-- 							inner menu: contains the actual data -->
<!-- 							<ul class="menu"> -->
<!-- 								<li><a href="#"> <i class="fa fa-users text-aqua"></i> -->
<!-- 										5 new members joined today -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> <i class="fa fa-warning text-yellow"></i> -->
<!-- 										Very long description here that may not fit into the page and -->
<!-- 										may cause design problems -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> <i class="fa fa-users text-red"></i> 5 -->
<!-- 										new members joined -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> <i -->
<!-- 										class="fa fa-shopping-cart text-green"></i> 25 sales made -->
<!-- 								</a></li> -->
<!-- 								<li><a href="#"> <i class="fa fa-user text-red"></i> -->
<!-- 										You changed your username -->
<!-- 								</a></li> -->
<!-- 							</ul> -->
<!-- 						</li> -->
<!-- 						<li class="footer"><a href="#">View all</a></li> -->
<!-- 					</ul></li> -->
				
				<!-- Tasks: style can be found in dropdown.less -->
				<li class="dropdown tasks-menu" id="siteDropdownLi"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"><span class="hidden-xs" id="siteName"><%=siteName%></span>
				</a>
					<ul class="dropdown-menu" id="siteDropdown">
						<li class="header">사업장 정보</li>
						<li>
							<!-- inner menu: contains the actual data -->
							<ul class="menu" id="mainPageSiteInfo">
								<!-- 사업장 정보가 추가 될 영역 -->
							</ul>
						</li>
<!-- 						<li class="footer"><a href="#">View all tasks</a></li> -->
					</ul></li>
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <img
						src="resources/dist/img/user2-160x160.jpg" class="user-image"
						alt="User Image"> <span class="hidden-xs"> <%=userName%>
							(<%=userID%>)
					</span>
				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
							src="resources/dist/img/user2-160x160.jpg" class="img-circle"
							alt="User Image">

							<p>
								신우TNS Test
								<small>Member since. 2016</small>
							</p></li>
						<!-- Menu Body -->
						<li class="user-body">
							<div class="row">
								<div class="col-xs-4 text-center">
									<a href="#">Button1</a>
								</div>
								<div class="col-xs-4 text-center">
									<a href="#">Button1</a>
								</div>
								<div class="col-xs-4 text-center">
									<a href="#">Button1</a>
								</div>
							</div> <!-- /.row -->
						</li>
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="#" class="btn btn-default btn-flat">Sample</a>
							</div>
							<div class="pull-right">
								<!-- <a href="#" class="btn btn-default btn-flat" onclick="fnSignOut();return false;" >Sign out</a> -->
								<a href="/signOut" class="btn btn-default btn-flat">Sign out</a>
							</div>
						</li>
					</ul></li>
				<!-- Control Sidebar Toggle Button -->
<!-- 				<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li> -->
			</ul>
		</div>
		<!-- 상단 좌측 메일, 공지, 작업 , 프로필 관련 영역 끝 --> </nav> </header>

		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- Sidebar user panel --> <!-- search form -->
<!-- 		<form action="#" method="get" class="sidebar-form"> -->
<!-- 			<div class="input-group"> -->
<!-- 				<input type="text" name="q" class="form-control" -->
<!-- 					placeholder="Search..."> <span class="input-group-btn"> -->
<!-- 					<button type="submit" name="search" id="search-btn" -->
<!-- 						class="btn btn-flat"> -->
<!-- 						<i class="fa fa-search"></i> -->
<!-- 					</button> -->
<!-- 				</span> -->
<!-- 			</div> -->
<!-- 		</form> -->
		<!-- /.search form --> <!-- sidebar menu: : style can be found in sidebar.less -->
		<ul id="ulMenuArea" name="ulMenuArea" class="sidebar-menu">
		</ul>
		</section> <!-- /.sidebar --> </aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			<h1>
				<LABEL id="contentTitle">Dashboard</LABEL> <small><LABEL id="contentTitleSmall"></LABEL></small>
			</h1>
			<ol class="breadcrumb">
				<li>
					<a href="/main">
						<i class="fa fa-dashboard"></i><%=LanguageHelper.GetLanguage("home")%>
					</a>
				</li>
				<li class="active" id="contentHeaderDepth1li" >
					<LABEL id="contentHeaderDepth1"></LABEL>
				</li>
				<li class="active" id="contentHeaderDepth2li" >
					<LABEL id="contentHeaderDepth2"></LABEL>
				</li>
			</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div id="content_frame" style="width: 100%;height: 100%"></div>
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

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark"> <!-- Create the tabs -->
		<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
			<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i
					class="fa fa-home"></i></a></li>
			<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
					class="fa fa-gears"></i></a></li>
		</ul>
		<!-- Tab panes -->
		<div class="tab-content">
			<!-- Home tab content -->
			<div class="tab-pane" id="control-sidebar-home-tab">
				<h3 class="control-sidebar-heading">Recent Activity</h3>
				<ul class="control-sidebar-menu">
					<li><a href="javascript:void(0)"> <i
							class="menu-icon fa fa-birthday-cake bg-red"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

								<p>Will be 23 on April 24th</p>
							</div>
					</a></li>
					<li><a href="javascript:void(0)"> <i
							class="menu-icon fa fa-user bg-yellow"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Frodo Updated His
									Profile</h4>

								<p>New phone +1(800)555-1234</p>
							</div>
					</a></li>
					<li><a href="javascript:void(0)"> <i
							class="menu-icon fa fa-envelope-o bg-light-blue"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

								<p>nora@example.com</p>
							</div>
					</a></li>
					<li><a href="javascript:void(0)"> <i
							class="menu-icon fa fa-file-code-o bg-green"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Cron Job 254
									Executed</h4>

								<p>Execution time 5 seconds</p>
							</div>
					</a></li>
				</ul>
				<!-- /.control-sidebar-menu -->

				<h3 class="control-sidebar-heading">Tasks Progress</h3>
				<ul class="control-sidebar-menu">
					<li><a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Custom Template Design <span
									class="label label-danger pull-right">70%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-danger"
									style="width: 70%;"></div>
							</div>
					</a></li>
					<li><a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Update Resume <span class="label label-success pull-right">95%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-success"
									style="width: 95%;"></div>
							</div>
					</a></li>
					<li><a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Laravel Integration <span class="label label-warning pull-right">50%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-warning"
									style="width: 50%;"></div>
							</div>
					</a></li>
					<li><a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Back End Framework <span class="label label-primary pull-right">68%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-primary"
									style="width: 68%;"></div>
							</div>
					</a></li>
				</ul>
				<!-- /.control-sidebar-menu -->

			</div>
			<!-- /.tab-pane -->
			<!-- Stats tab content -->
			<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab
				Content</div>
			<!-- /.tab-pane -->
			<!-- 세팅 탭 컨텐츠 - Settings tab content -->
			<div class="tab-pane" id="control-sidebar-settings-tab">
				<form method="post">
					<h3 class="control-sidebar-heading">General Settings</h3>

					<div class="form-group">
						<label class="control-sidebar-subheading"> Report panel
							usage <input type="checkbox" class="pull-right" checked>
						</label>

						<p>Some information about this general settings option</p>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading"> Allow mail
							redirect <input type="checkbox" class="pull-right" checked>
						</label>

						<p>Other sets of options are available</p>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading"> Expose author
							name in posts <input type="checkbox" class="pull-right" checked>
						</label>

						<p>Allow the user to show his name in blog posts</p>
					</div>
					<!-- /.form-group -->

					<h3 class="control-sidebar-heading">Chat Settings</h3>

					<div class="form-group">
						<label class="control-sidebar-subheading"> Show me as
							online <input type="checkbox" class="pull-right" checked>
						</label>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading"> Turn off
							notifications <input type="checkbox" class="pull-right">
						</label>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading"> Delete chat
							history <a href="javascript:void(0)" class="text-red pull-right"><i
								class="fa fa-trash-o"></i></a>
						</label>
					</div>
					<!-- /.form-group -->
				</form>
			</div>
			<!-- /.tab-pane -->
		</div>
		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->
</body>
</html>