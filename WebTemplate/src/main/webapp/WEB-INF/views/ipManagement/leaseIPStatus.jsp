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
		                			<label style="width: 80px; margin-right:10px; text-align:right"><%=LanguageHelper.GetLanguage("segment")%> : </label>
		                		</td>
		                		<td>
			                		<select id="sbSegment" class="form-control selectoption_grey_color" style="width:210px">
									</select>
		                		</td>
		                		<td>
		                			<input type="text" style="width:250px; margin-left:15px;" class="form-control" id="txtSearch"
										placeholder="<%=LanguageHelper.GetLanguage("ipmachostnameinput")%>">
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
									<th width="4%"><%=LanguageHelper.GetLanguage("ip")%></th>
									<th width="4%"><%=LanguageHelper.GetLanguage("mac")%></th>
									<th width="6%"><%=LanguageHelper.GetLanguage("hostname")%></th>
									<th width="4%"><%=LanguageHelper.GetLanguage("hostos")%></th>
									<th width="4%">Duid</th>
									<th width="4%"><%=LanguageHelper.GetLanguage("status")%></th>
									<th width="4%"><%=LanguageHelper.GetLanguage("leasestatus")%></th>
									<th width="7%">Obj types</th>
									<th width="4%">Discover status</th>
									<th width="4%">Usage</th>
									<th width="7%">Fingerprint</th>
									<th width="4%">Never ends</th>
									<th width="4%">Never start</th>
									<th width="5%"><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("starttime")%></th>
									<th width="5%"><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("endtime")%></th>
									<th width="4%">Last discovered</th>
									<th width="6%">User description</th>
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