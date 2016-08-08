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
<div class="modal modal-dialog" id="modal" style="width:600px">
  <div class="modal-content" name="modalContent">
  <!-- modal-content -->
    <div class="modal-header">
      <input type="button" class="close" name="modalClose" data-dismiss="modal" aria-label="Close" value="&times;" />
      <h4 class="modal-title">Black List 추가</h4>
    </div>
    <style type="text/css">
    	.input-group {
    	width:100%;
    	}
    	.test {
    	width:15%;
    	float:left;
    	}
    	.test3 {
    	}
/*     	.test2 { */
/*     	    border-right: 1px !important; */
/* 		    border-right-style: solid !important; */
/* 		    border-right-color: #d2d6de !important; */
/* 		    width: 84px; */
/*     	} */
    </style>
    <div class="modal-body">
    	<div class="input-group">
			<span class="input-group-addon test">사업장</span>
			<div class="test3">
			<select class="form-control" id="selectSite">
                   <option>option 1</option>
                   <option>option 2</option>
                   <option>option 3</option>
                   <option>option 4</option>
                   <option>option 5</option>
			</select>
			</div>
		</div>
    	<div class="input-group">
			<span class="input-group-addon test test2">활성화</span>
			<div class="test3">
			<input type="checkbox" class="minimal" style="margin-left:10px">활성
			<input type="checkbox" class="minimal" style="margin-left:10px">비활성
			</div>
		</div>
    	<div class="input-group">
    		<span class="input-group-addon test">필터</span>
    		<div class="test3">
				<input type="email" class="form-control" id="exampleInputEmail1" placeholder="Filter">
			</div>
		</div>
    	<div class="input-group">
			<span class="input-group-addon test">시간</span>
			<div class="test3">
				<select class="form-control">
	                   <option>option 1 초</option>
	                   <option>option 2</option>
	                   <option>option 3</option>
	                   <option>option 4</option>
	                   <option>option 5</option>
				</select>
			</div>
		</div>
    	<div class="input-group">
			<span class="input-group-addon test">설명</span>
			<div class="test3">
				<textarea class="form-control" rows="3" placeholder="Enter ..."></textarea>
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

