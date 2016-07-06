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
					</center>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="box box-primary">
				<div class="box-body">test</div>
			</div>
		</div>
	</div>
	</section>
	<!--
		 </div>
	</div> -->
	<script src="resources/js/configMangement/systemUserManagement.js"></script>
	<script>
        var table;
        $(document)
                .ready(
                        function() {
                            table = $('#datatable')
                                    .DataTable(
                                            {
                                                "destroy" : true,
                                                "paging" : true,
                                                "searching" : true,
                                                "lengthChange" : true,
                                                "ordering" : true,
                                                "info" : true,
                                                "autoWidth" : true,
                                                "processing" : true,
                                                "serverSide" : true,
                                                "ajax" : {
                                                    url : 'configManagement/getSystemUserManagementDatatableDatas',
                                                    "dataType" : "jsonp",
                                                    "type":"POST",
                                                    "jsonp" : "callback",
                                                    "data" : function(data) {
                                                    }

                                                },
                                                'columnDefs' : [ {
                                                    'targets' : 0,
                                                    'data' : "active",
                                                    'searchable' : false,
                                                    'orderable' : false,
                                                    'width' : '1%',
                                                    'className' : "dt-body-center",
                                                    'render' : function(data,
                                                            type, row) {
                                                        if (type === 'display') {
                                                            return '<input type="checkbox" name="checkbox-active" class="editor-active">';
                                                        }
                                                        return data;
                                                    }
                                                } ],
                                                'order' : [ [ 1, 'asc' ] ],
                                                "columns" : [ {}, {
                                                    "data" : "user_id"
                                                }, {
                                                    "data" : "user_name"
                                                }, ],
                                            });
                        });
    </script>
</body>
</html>