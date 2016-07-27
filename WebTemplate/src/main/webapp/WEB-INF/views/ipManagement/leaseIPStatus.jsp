<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>
<!-- IP Map style -->
<link rel="stylesheet" href="resources/css/default.css">
<link rel="stylesheet" href="resources/plugins/datatables/buttons.dataTables.min.css">

<script src="resources/js/main/main.js"></script>
<script src="resources/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="resources/plugins/datatables/buttons.flash.min.js"></script>
<script src="resources/plugins/datatables/buttons.html5.min.js"></script>
<script src="resources/plugins/datatables/buttons.colVis.min.js"></script>
<script src="resources/plugins/datatables/pdfmake.min.js"></script>
<script src="resources/plugins/datatables/vfs_fonts.js"></script>
<script src="resources/plugins/datatables/jszip.min.js"></script>
<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/ipManagement/leaseIPStatus.js"></script>

<!-- Alert Start -->
<div id="layDiv">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>

<section class="white-paper">
	<div class="row" id="defaultDiv">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="pagebox-header with-border">				
<!-- 				<div class="box-header"> -->
					<div>						
		                <!-- select -->
		                <table>
		                	<tr>
		                		<td>
		                			<label style="width:90px; padding-left:10px;">세그먼트 : </label>
		                		</td>
		                		<td>
			                		<select id="sbSegment" class="form-control selectoption_grey_color" style="width:210px">
									</select>
		                		</td>
		                		<td>
		                			<input type="text" style="width:250px; margin-left:15px;" class="form-control" id="txtSearch" placeholder="검색어를 입력해주세요">
		                		</td>
		                		<td style="width:100%; text-align:right; padding-right:10px">
		                			<button type="button" class="btn btn-primary" id="btnSearch"><%=LanguageHelper.GetLanguage("save")%></button>
		                		</td>
		                	</tr>
		                </table>
					</div>
				</div>
				<div class="box-header">
					<center>
						<table id="datatable" name="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<%--<th><%=LanguageHelper.GetLanguage("ipaddr")%></th> --%>
									<th width="4%">ipaddr</th>
									<th width="4%">macaddr</th>
									<th width="6%">host_name</th>
									<th width="4%">host_os</th>
									<th width="4%">duid</th>
									<th width="4%">status</th>
									<th width="4%">lease_state</th>
									<th width="7%">obj_types</th>
									<th width="4%">discover_status</th>
									<th width="4%">usage</th>
									<th width="7%">fingerprint</th>
									<th width="4%">is_never_ends</th>
									<th width="4%">is_never_start</th>
									<th width="5%">lease_start_time</th>
									<th width="5%">lease_end_time</th>
									<th width="4%">last_discovered</th>
									<th width="6%">user_description</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</center>
				</div>
			</div>
		</div>
	</div>
</section>