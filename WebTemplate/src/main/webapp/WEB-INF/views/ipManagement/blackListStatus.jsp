<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>
<%
	String userID = (String) session.getAttribute("user_id");
	String userName = (String) session.getAttribute("user_name");
	String siteid = (String) session.getAttribute("site_id");
	String siteName = (String) session.getAttribute("site_name");
	String siteMaster = (String) session.getAttribute("site_master");
%>

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
<script type="text/javascript">
var siteid;
var siteMaster;
$(document).ready(function() {
	try {
		siteid = <%=siteid%>;
		siteMaster = '<%=siteMaster%>';
		fnSiteInfoSearch();		
	} catch (e) {
		console.log(e.message);
	}
});
</script>
    		
<!-- Add, Edit modal -->
<div class="modal modal-dialog" id="modal" style="width:600px">
  <div class="modal-content" name="modalContent">
  <!-- modal-content -->
    <div class="modal-header">
      <input type="button" class="close" name="modalClose" data-dismiss="modal" aria-label="Close" value="&times;" />
      <h4 class="modal-title">Black List 추가</h4>
    </div>
    
    <div class="modal-body">
    	<div class="input-group modal-input-group">
			<span class="input-group-addon modal-content-header">사업장</span>
			<div class="modal-content-body">
			<select class="form-control" id="selectSite" ></select>
			</div>
		</div>
    	<div class="form-group input-group modal-input-group">
			<div class="input-group-addon modal-content-header">활성화</div>
			<div class="modal-content-body modal-content-box">
			<input type="radio" class="minimal" name="rEnable" value="true" style="margin-left:10px"> 활성
			<input type="radio" class="minimal" name="rEnable" value="false" style="margin-left:10px" checked> 비활성
			</div>
		</div>
    	<div class="input-group modal-input-group">
    		<div class="input-group-addon modal-content-header">필터</div>
    		<div class="modal-content-body">
				<input type="text" class="form-control" id="inputFilter" placeholder="Filter">
			</div>
		</div>
    	<div class="input-group modal-input-group">
			<span class="input-group-addon modal-content-header">시간</span>
			<div class="modal-content-body">
				<select class="form-control" id="selectTime" >
	                   <option value='60'>60 초</option>
	                   <option value='180'>180 초</option>
	                   <option value='300'>300 초</option>
	                   <option value='600'>600 초</option>
				</select>
			</div>
		</div>
    	<div class="input-group modal-input-group" style="margin-bottom: 0px">
			<span class="input-group-addon modal-content-header">설명</span>
			
			<div class="modal-content-body">
				<textarea class="form-control" id="txtareaDesc" rows="3" placeholder="Enter ..."></textarea>
			</div>
		</div>
		
    </div>
    <div class="modal-footer">
      <input type="button" class="btn btn-default pull-left" name="modalClose" data-dismiss="modal" value="<%=LanguageHelper.GetLanguage("close")%>" />
      <button type="button" class="btn btn-primary" id="btnSave"><%=LanguageHelper.GetLanguage("saveandclose")%></button>
    </div>
  </div>
  <!-- /.modal-content -->
</div>
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
						<div id="add-row" style="margin-top: 5px; text-align: right;">
							<button type="button" class="btn btn-primary" id="add-button" name="save-button"><%=LanguageHelper.GetLanguage("add")%></button>
						</div>
					</center>
				</div>
			</div>
		</div>
	</div>
</section>

