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
		<div class="col-xs-6">
			<div class="box-header" style="text-align: left;">
				<i class="fa fa-cog"></i>
				<h3 class="box-title-small">제한 정책</h3>
			</div>
			<div class="box-body">
				<center>
					<table name="datatable" id="accessPolicyTable" class="essential-table"
						style="width: 98%">
						<thead>
							<tr>
								<th></th>
								<th></th>
								<th>사업장</th>
								<th>벤더</th>
								<th>모델</th>
								<th>장비 종류</th>
								<th>OS</th>
								<th>Hostname</th>
								<th>설명</th>
								<th>정책</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</center>
				<div style="margin-bottom: 10px;"></div>
			</div>

		</div>
		<div class="col-xs-6">
			<div class="box-header" style="text-align: left;">
				<i class="fa fa-cog"></i>
				<h3 class="box-title-small">-</h3>
			</div>
			<div class="box-body"></div>
		</div>
	</div>
</section>
<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/policyManagement/accessPolicy.js"></script>
