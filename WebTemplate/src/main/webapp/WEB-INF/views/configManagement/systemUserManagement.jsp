<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SystemUserManagement</title>
<!-- DataTables -->
<link rel="stylesheet" href="resources/css/essential.css">
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
									<th width="48%">아이디</th>
									<th width="48%">이름</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<select id="search_select" class="form-control input-sm">
							<option>아이디</option>
							<option>이름</option>
						</select>
					</center>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="box box-primary">
				<div class="box-body">
				<div id="test"></div>
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