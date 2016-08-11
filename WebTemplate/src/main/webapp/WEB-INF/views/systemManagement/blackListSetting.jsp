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
<script src="resources/js/systemManagement/blackListSetting.js"></script>
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
	
// 	var mouseStatus="UP";
// 	$( "#div_mordal_header" )
// 	  .mouseup(function() {
// 		  mouseStatus="UP";
// 		  $( "#modal" ).droppable({ accept: "not(.notdragabble)" });
// 	  })
// 	  .mousedown(function() {
// 		  mouseStatus="DOWN";
// 	  });
	
// 	$( "#div_mordal_header" )
// 	  .mouseenter(function() {
// 		  if(mouseStatus == "DOWN")
// 		  	$( "#modal" ).draggable();
// 	  })
// 	  .mouseleave(function() {
// 		  $( "#modal" ).droppable({ accept: "not(.notdragabble)" });
// 	  });


});
</script>

<!-- Add, Edit modal -->
<div class="modal modal-dialog" id="modal">
	<div class="modal-content" name="modalContent" id="modalContent">
	<!-- modal-content -->
  		<div class="modal-header" id="div_mordal_header">
    		<input type="button" class="close" name="modalClose" data-dismiss="modal" aria-label="Close" value="&times;" />
    		<h4 class="modal-title"><%=LanguageHelper.GetLanguage("addblacklist")%></h4>
  		</div>
  
	  	<div class="modal-body">
	  		<div class="input-group modal-input-group">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("site")%></span>
				<div class="modal-content-body">
					<select class="form-control" id="selectSite" ></select>
				</div>
			</div>
		  	<div class="form-group input-group modal-input-group">
				<div class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("usedclass")%></div>
				<div class="modal-content-body modal-content-box">
					<input type="radio" class="minimal" name="rEnable" value="true" style="margin-left:10px"> <%=LanguageHelper.GetLanguage("used")%>
					<input type="radio" class="minimal" name="rEnable" value="false" style="margin-left:10px"> <%=LanguageHelper.GetLanguage("unused")%>
				</div>
			</div>
		  	<div class="input-group modal-input-group">
				<div class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("filtername")%></div>
		  		<div class="modal-content-body">
					<input type="text" class="form-control" id="inputFilter" placeholder="Filter">
				</div>
			</div>
		  	<div class="input-group modal-input-group">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("datetime")%></span>
				<div class="modal-content-body">
					<select class="form-control" id="selectTime" >
		            	<option value='60'>60 <%=LanguageHelper.GetLanguage("second")%></option>
		           		<option value='180'>180 <%=LanguageHelper.GetLanguage("second")%></option>
		            	<option value='300'>300 <%=LanguageHelper.GetLanguage("second")%></option>
		           		<option value='600'>600 <%=LanguageHelper.GetLanguage("second")%></option>
					</select>
				</div>
			</div>
		  	<div class="input-group modal-input-group" style="margin-bottom: 0px">
				<span class="input-group-addon modal-content-header"><%=LanguageHelper.GetLanguage("desctription")%></span>
			
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
				<div class="box-header">
					<center>
						<table id="datatable" name="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th width="0%" style="display:none;">blacklist_id</th>
									<th width="0%" style="display:none;">site_id</th>
									<th width="15%"><%=LanguageHelper.GetLanguage("site")%></th>
									<th width="10%"><%=LanguageHelper.GetLanguage("usedclass")%></th>
									<th width="20%"><%=LanguageHelper.GetLanguage("filtername")%></th>
									<th width="10%"><%=LanguageHelper.GetLanguage("datetime")%></th>
									<th width="45%"><%=LanguageHelper.GetLanguage("desctription")%></th>
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

