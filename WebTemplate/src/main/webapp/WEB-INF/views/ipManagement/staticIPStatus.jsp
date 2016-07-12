<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>

<link rel="stylesheet" href="resources/css/essential.css">
<script src="resources/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="resources/js/common/Common.js"></script>
<script src="resources/js/common/multyLanguages.js"></script>
<script src="resources/js/common/Datatable-Essential.js"></script>
<script src="resources/js/ipManagement/staticIPStatus.js"></script>

<section class="white-paper">
	<div class="row">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="box-body">
<!-- 					<center> -->
						<table id="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th><input type="checkbox" /></th>
									<th width="25%"><%=LanguageHelper.GetLanguage("search")%></th>
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
<!-- 					</center>	 -->
				</div>
			</div>
		</div>
	</div>
</section>