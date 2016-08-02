<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>


<script src="resources/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="resources/plugins/datatables/buttons.flash.min.js"></script>
<script src="resources/plugins/datatables/buttons.html5.min.js"></script>
<script src="resources/plugins/datatables/buttons.colVis.min.js"></script>
<script src="resources/plugins/datatables/pdfmake.min.js"></script>
<script src="resources/plugins/datatables/vfs_fonts.js"></script>
<script src="resources/plugins/datatables/jszip.min.js"></script>
<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/ipManagement/ipCertifyStatus.js"></script>

<!-- Alert Start -->
<div id="layDiv">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>		
<!-- Alert End -->

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
		                			<label style="width:80px; margin-right:10px; text-align:right"><%=LanguageHelper.GetLanguage("inquiryTime")%> : </label>
		                		</td>
		                		<td class="input-group date">
									<!-- Date and time range -->	
									<input style="width:330px;" type="text" class="form-control" id="reservationtime" name="reservationtime">
									<!-- /.Date and time range -->
		                		</td>
		                		<td>
		                			<label style="width:80px; margin-right:10px; text-align:right"><%=LanguageHelper.GetLanguage("status")%> : </label>
		                		</td>
		                		<td>
			                		<select id="sbCerifyStatus" class="form-control selectoption_grey_color" style="width:80px">
			                			<option value="ALL" selected>전체</option>
			                			<option value="0">요청</option>
			                			<option value="1">승인</option>
			                			<option value="2">반려</option>
									</select>
		                		</td>
		                		<td>
		                			<input type="text" style="width:250px; margin-left:15px;" class="form-control" id="txtSearch"
										placeholder="<%=LanguageHelper.GetLanguage("inputSearchWord")%>">
		                		</td>
		                		<td style="width:100%; text-align:right; padding-right:10px">
		                			<button type="button" class="btn btn-primary" id="btnSearch"><%=LanguageHelper.GetLanguage("inquiry")%></button>
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
									<th width="4%">user_id</th>
									<th width="4%">user_site_id</th>
									<th width="6%">site_name</th>
									<th width="4%">user_name</th>
									<th width="4%">user_phone_num</th>
									<th width="4%">apply_static_ip_type</th>
									<th width="4%">apply_static_ipaddr</th>
									<th width="7%">apply_static_ip_num</th>
									<th width="4%">apply_start_time</th>
									<th width="4%">apply_end_time</th>
									<th width="7%">apply_description</th>
									<th width="4%">apply_time</th>
									<th width="4%">settlement_status</th>
									<th width="5%">settlement_chief_id</th>
									<th width="5%">settlement_description</th>
									<th width="4%">settlement_time</th>
									<th width="6%">issuance_ip_type</th>
									<th width="6%">issuance_ipaddr</th>
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