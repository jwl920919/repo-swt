<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SystemUserManagement</title>
<!-- DataTables -->
<link rel="stylesheet"
	href="resources/plugins/datatables/dataTables.bootstrap.css">
<script src="resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
</head>
<body>
	<div class="box box-primary">
		<div class="box-body">
			<table id="datatable" class="table table-bordered table-striped">
				<thead>
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>그룹</th>
						<th>부서</th>
						<th>직함</th>
						<th>전화</th>
						<th>휴대전화</th>
						<th>E-Mail</th>
						<th>생성시간</th>
						<th>접속시간</th>
						<th></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script>
		$(function() {
			$.post('/getFilterInformation', function(data) {
				$("#filterNames").empty();
				$("#filterNames").append(data);
			});
		});
		var table;
		$(document)
				.ready(
						function() {
							table = $('#datatable')
									.DataTable(
											{
												"paging" : true,
												"searching" : true,
												"lengthChange" : true,
												"ordering" : true,
												"info" : true,
												"autoWidth" : true,
												"processing" : true,
												"serverSide" : true,
												"ajax" : {
													url : 'getTableDatas',
													"dataType" : "jsonp",
													"jsonp" : "callback",
													"data" : function(d) {
														d.search_key = d.search.value;

														d.searchColumn = $(
																'#search_select option')
																.index(
																		$('#search_select option:selected'));
														var checkedValues = $('input:checkbox[name="checkbox-active"]:checked');
														var uncheckedValues = $('input:checkbox[name="checkbox-active"]:not(:checked)');
														var checkedValuesArray = new Array();
														var uncheckedValuesArray = new Array();
														for (var i = 0; i < checkedValues.length; i++) {
															var checkedValuesObj = Object();
															checkedValuesObj.ip = $(checkedValues[i]).parent().parent().children(":eq(2)").text();
															checkedValuesObj.mac = $(checkedValues[i]).parent().parent().children(":eq(3)").text();
															checkedValuesArray.push(checkedValuesObj);
														}															
														for (var i = 0; i < uncheckedValues.length; i++) {
															var uncheckedValuesObj = Object();
															uncheckedValuesObj.ip = $(uncheckedValues[i]).parent().parent().children(":eq(2)").text();
															uncheckedValuesObj.mac = $(uncheckedValues[i]).parent().parent().children(":eq(3)").text();
															uncheckedValuesArray.push(uncheckedValuesObj);
														}					
														d.checkedValuesJsonArrayInfo = JSON.stringify(checkedValuesArray);
														d.uncheckedValuesJsonArrayInfo = JSON.stringify(uncheckedValuesArray);
														
													}

												},
												'columnDefs' : [ {
													'targets' : 0,
													'data' : "active",
													'searchable' : false,
													'orderable' : false,
													'width' : '1%',
													'className' : "dt-body-center",
													//'render' : function(data,type, full, meta) {
													'render' : function(data,
															type, row) {
														//return '<input type="checkbox" name="row_chk">';
														if (type === 'display') {
															return '<input type="checkbox" name="checkbox-active" class="editor-active">';
														}
														return data;
													}
												} ],
												'order' : [ [ 1, 'asc' ] ],
												"columns" : [ {}, {
													"data" : "time"
												}, {
													"data" : "ipAddr"
												}, {
													"data" : "macAddr"
												}, {
													"data" : "manufacturer"
												} ],

											});
							$(function() {
								var d_wrap = $('#datatable_wrapper .row:first');
								var d_length = $('#datatable_wrapper .row:first .col-sm-6:eq(0)');
								var d_filter = $('#datatable_wrapper .row:first .col-sm-6:eq(1)');
								d_length.append(d_filter);
								d_wrap.prepend(d_filter);
								d_filter.children().append($('#search_select'));
							});

						});

		$('#datatable').on(
				'change',
				'input.editor-active',
				function() {
					editor.edit($(this).closest('tr'), false).set('active',
							$(this).prop('checked') ? 1 : 0).submit();
				});
		$('#datatable').on( 'page.dt', function () {
			var chk_box = $('#checkbox_controller');			
		    if(chk_box.is(':checked'))
		    	chk_box.prop("checked",false);
		} );
	</script>
</body>
</html>