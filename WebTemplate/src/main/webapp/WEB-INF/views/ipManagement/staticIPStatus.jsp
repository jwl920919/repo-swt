<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>

<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/ipManagement/staticIPStatus.js"></script>

<!-- Alert Start -->
<div id="layDiv" >
	<div class="alert-box" ></div>
	<div id="divAlertArea"></div>
</div>

<section class="white-paper">
	<div class="row" id="defaultDiv">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="box-body">
					<center>
						<table id="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th style="display: none;"></th>
									<th width="25%"><%=LanguageHelper.GetLanguage("network")%></th>
									<th width="25%"><%=LanguageHelper.GetLanguage("comment")%></th>
									<th width="25%"><%=LanguageHelper.GetLanguage("utilization")%></th>
									<th width="25%"><%=LanguageHelper.GetLanguage("site")%></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
<!-- 						<div class="col-sm-1"> -->
<!-- 							<input id='delete-button' class='btn btn-primary' type="button" -->
<%-- 								value="<%=LanguageHelper.GetLanguage("delete")%>" /> --%>
<!-- 						</div> -->
					</center>	
				</div>
			</div>
		</div>
	</div>

	<div class="row" id="detailDiv" style="display:none;">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="box-body">
					<center>
						<table id="datatable_detail" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th width="16%"><%=LanguageHelper.GetLanguage("network")%></th>
									<th width="16%"><%=LanguageHelper.GetLanguage("comment")%></th>
									<th width="16%"><%=LanguageHelper.GetLanguage("utilization")%></th>
									<th width="16%"><%=LanguageHelper.GetLanguage("site")%></th>
									<th width="16%"><%=LanguageHelper.GetLanguage("utilization")%></th>
									<th width="*"><%=LanguageHelper.GetLanguage("site")%></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
<!-- 						<div class="col-sm-1"> -->
<!-- 							<input id='delete-button' class='btn btn-primary' type="button" -->
<%-- 								value="<%=LanguageHelper.GetLanguage("delete")%>" /> --%>
<!-- 						</div> -->
					</center>	
				</div>
			</div>
		</div>
	</div>

</section>