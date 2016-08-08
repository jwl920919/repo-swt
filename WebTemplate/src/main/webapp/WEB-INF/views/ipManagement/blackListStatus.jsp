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
<script src="resources/js/common/modalPopup.js"></script>
<script src="resources/js/ipManagement/blackListStatus.js"></script>

<!-- Add, Edit modal -->
<div class="modal modal-info modal-dialog" id="modal" style="width:600px">
  <div class="modal-content" name="modalContent">
    <div class="modal-header">
      <input type="button" class="close" name="modalClose" data-dismiss="modal" aria-label="Close" value="&times;" />
      <h4 class="modal-title">Primary Modal</h4>
    </div>
    <div class="modal-body">
      <p>One fine body&hellip;</p>
    </div>
    <div class="modal-footer">
      <input type="button" class="btn btn-outline pull-left" name="modalClose" data-dismiss="modal" value="Close" />
      <button type="button" class="btn btn-outline" id="btnSave">Save changes</button>
    </div>
  </div>
  <!-- /.modal-content -->
</div>
<!-- /.Add, Edit modal -->

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
<!-- 				<div class="pagebox-header with-border">				 -->
<!-- 				<div class="box-header"> -->
<!-- 					<div>						 -->
<!-- 		                select -->
<!-- 		                <table> -->
<!-- 		                	<tr> -->
<!-- 		                		<td> -->
<%-- 		                			<label style="width:80px; margin-right:10px; text-align:right"><%=LanguageHelper.GetLanguage("inquiryTime")%> : </label> --%>
<!-- 		                		</td> -->
<!-- 		                		<td class="input-group date"> -->
<!-- 									Date and time range	 -->
<!-- 									<input style="width:330px;" type="text" class="form-control" id="reservationtime" name="reservationtime"> -->
<!-- 									/.Date and time range -->
<!-- 		                		</td> -->
<!-- 		                		<td> -->
<%-- 		                			<label style="width:80px; margin-right:10px; text-align:right"><%=LanguageHelper.GetLanguage("status")%> : </label> --%>
<!-- 		                		</td> -->
<!-- 		                		<td> -->
<!-- 			                		<select id="sbCerifyStatus" class="form-control selectoption_grey_color" style="width:120px"> -->
<%-- 			                			<option value="ALL" selected><%=LanguageHelper.GetLanguage("all")%></option> --%>
<%-- 			                			<option value="0"><%=LanguageHelper.GetLanguage("requestApproval")%></option> --%>
<%-- 			                			<option value="1"><%=LanguageHelper.GetLanguage("approval")%></option> --%>
<%-- 			                			<option value="2"><%=LanguageHelper.GetLanguage("return")%></option> --%>
<!-- 									</select> -->
<!-- 		                		</td> -->
<!-- 		                		<td> -->
<!-- 		                			<input type="text" style="width:250px; margin-left:15px;" class="form-control" id="txtSearch" -->
<%-- 										placeholder="<%=LanguageHelper.GetLanguage("inputSearchWord")%>"> --%>
<!-- 		                		</td> -->
<!-- 		                		<td style="width:100%; text-align:right; padding-right:10px"> -->
<%-- 		                			<button type="button" class="btn btn-primary" id="btnSearch"><%=LanguageHelper.GetLanguage("inquiry")%></button> --%>
<!-- 		                		</td> -->
<!-- 		                	</tr> -->
<!-- 		                </table> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="box-header">
					<center>
						<table id="datatable" name="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th width="0%" style="display:none;">blacklist_id</th>
									<th width="0%" style="display:none;">site_id</th>
									<th width="20%"><%=LanguageHelper.GetLanguage("site")%></th>
									<th width="20%">blacklist_enable</th>
									<th width="20%">blacklist_filter_name</th>
									<th width="20%">blacklist_time_sec</th>
									<th width="20%">description</th>
									<th width="20%"></th>
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

