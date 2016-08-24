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
<script src="resources/js/ipManagement/ipCertifyStatus.js"></script>

<!-- Add, Edit modal -->
<div class="modal modal-dialog" id="modal">
	<div class="modal-content" name="modalContent" id="modalContent">
	<!-- modal-content -->
  		<div class="modal-header" id="div_mordal_header">
    		<input type="button" class="close" name="modalClose" data-dismiss="modal" aria-label="Close" value="&times;" />
    		<h4 class="modal-title"><%=LanguageHelper.GetLanguage("askIPsettlement")%></h4>
  		</div>
  
	  	<div class="modal-body">
	  		<div class="input-group modal-input-group">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("applicant")%></span>
				<div class="modal-content-body modal-content-box">
					<span id="txtUserName" style="margin-left: 10px;"></span>
				</div>
			</div>
	  		<div class="input-group modal-input-group">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("applyIP")%></span>
				<div class="modal-content-body modal-content-box">
					<span id="txtApplyIP" style="margin-left: 10px;"></span>
				</div>
			</div>
	  		<div class="input-group modal-input-group">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("applyUseTime")%></span>
				<div class="modal-content-body modal-content-box">
					<span id="txtApplyTime" style="margin-left: 10px;"></span>
				</div>
			</div>
		  	<div class="form-group input-group modal-input-group">
				<div class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("settlement")%></div>
				<div class="modal-content-body modal-content-box">
					<input type="radio" class="minimal" name="rSettlement" value="true" style="margin-left:10px" checked> <%=LanguageHelper.GetLanguage("approval")%>
					<input type="radio" class="minimal" name="rSettlement" value="false" style="margin-left:10px"> <%=LanguageHelper.GetLanguage("return")%>
				</div>
			</div>
		  	<div class="input-group modal-input-group">
				<div class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("issuanceIP")%></div>
		  		<div class="modal-content-body">
					<input type="text" class="form-control" id="txtIssuanceIP" placeholder="IP ...">
				</div>
			</div>
		  	<div class="input-group modal-input-group">
				<div class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("issuanceUseTime")%></div>
		  		<div class="modal-content-body">
					<!-- Date and time range -->	
					<input style="width:484px;" type="text" class="form-control" id="issuanceUseTime" name="daterangepicker">
					<!-- /.Date and time range -->
				</div>
			</div>
		  	<div class="input-group modal-input-group" style="margin-bottom: 0px">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("settlementDesc")%></span>			
				<div class="modal-content-body">
					<textarea class="form-control" id="txtareaDesc" rows="3" placeholder="Enter ..."></textarea>
				</div>
			</div>
		</div>
  		<div class="modal-footer">
    		<input type="button" class="btn btn-default pull-left" name="modalClose" data-dismiss="modal" value="<%=LanguageHelper.GetLanguage("close")%>" />
    		<button type="button" class="btn btn-primary" id="btnSave"><%=LanguageHelper.GetLanguage("settlement")%></button>
  		</div>
	</div>
	<!-- /.modal-content -->
</div>
<div id="modalbackDiv" class="mordal-back-box"></div>
<!-- /.Add, Edit modal -->

<!-- Alert Start -->
<div id="layDiv" style="visibility : hidden;">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>		
<!-- Alert End -->

<section class="white-paper">
	<div class="row" id="defaultDiv">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="pagebox-header with-border">	
					<div>
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
			                		<select id="sbCerifyStatus" class="form-control selectoption_grey_color" style="width:120px">
			                			<option value="ALL" selected><%=LanguageHelper.GetLanguage("all")%></option>
			                			<option value="0"><%=LanguageHelper.GetLanguage("requestApproval")%></option>
			                			<option value="1"><%=LanguageHelper.GetLanguage("approval")%></option>
			                			<option value="2"><%=LanguageHelper.GetLanguage("return")%></option>
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
									<th width="0%" style="display:none;">settlement_status</th>
									<th width="4%"><%=LanguageHelper.GetLanguage("status")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("applicant")%> <%=LanguageHelper.GetLanguage("ID")%></th>
									<th width="0%" style="display:none;">user_site_id</th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("applicant")%> <%=LanguageHelper.GetLanguage("site")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("applicant")%> <%=LanguageHelper.GetLanguage("Name")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("applicant")%> <%=LanguageHelper.GetLanguage("contactnum")%></th>
									<th width="4%"><%=LanguageHelper.GetLanguage("applyIPType")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("applyIP")%></th>
									<th width="0%" style="display:none;">apply_static_ip_num</th>
									<th width="7.25%"><%=LanguageHelper.GetLanguage("applyUseTime")%></th>
									<th width="8.75%"><%=LanguageHelper.GetLanguage("applyDesc")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("applyTime")%></th>
									<th width="0%" style="display:none;">settlement_chief_id</th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("settlementChief")%></th>
									<th width="8.5%"><%=LanguageHelper.GetLanguage("settlementDesc")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("settlementTime")%></th>
									<th width="4%"><%=LanguageHelper.GetLanguage("issuanceIPType")%></th>
									<th width="6.25%"><%=LanguageHelper.GetLanguage("issuanceIP")%></th>
									<th width="0%" style="display:none;">issuance_ip_num</th>
									<th width="7.25%"><%=LanguageHelper.GetLanguage("issuanceUseTime")%></th>
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