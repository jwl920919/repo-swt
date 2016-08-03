<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>
<!-- IP Map style -->
<link rel="stylesheet" href="resources/css/ipmap.css">
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
<script src="resources/js/ipManagement/staticIPStatus.js"></script>

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
					<h2 class="pagebox-title-small">
						<LABEL>default</LABEL>
					</h2>
				</div>
				<div class="box-body">
					<center>
						<table id="datatable" name="datatable" class="essential-table" style="width: 98%">
							<thead>
								<tr>
									<th width="180px"><%=LanguageHelper.GetLanguage("network")%></th>
									<th width="100px"><%=LanguageHelper.GetLanguage("ip")%> <%=LanguageHelper.GetLanguage("type")%></th>
									<th width="200px"><%=LanguageHelper.GetLanguage("start")%></th>
									<th width="200px"><%=LanguageHelper.GetLanguage("end")%></th>
									<th width="100px"><%=LanguageHelper.GetLanguage("usedipcount")%></th>
									<th width="100px"><%=LanguageHelper.GetLanguage("totalipcount")%></th>
									<th width="100px"><%=LanguageHelper.GetLanguage("ipUtilization")%></th>
									<th width="120px"><%=LanguageHelper.GetLanguage("rangeipcount")%></th>
									<th width="100px"><%=LanguageHelper.GetLanguage("totalrangecount")%></th>
									<th width="100px"><%=LanguageHelper.GetLanguage("rangeUtilization")%></th>
									<th width="*"><%=LanguageHelper.GetLanguage("comment")%></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
<!-- 												<div class="col-sm-1"> -->
<!-- 													<input id='delete-button' class='btn btn-primary' type="button" -->
<%-- 														value="<%=LanguageHelper.GetLanguage("delete")%>"  onclick="excelExport();" /> --%>
<!-- 												</div> -->
					</center>
				</div>
			</div>
		</div>
	</div>

	<div class="row" id="detailDiv" style="display: none;">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="box-header" style="margin-bottom: -10px">
					<h1 class="box-title-small">
						<LABEL><a href="javascript:void(0)"
							onclick="contentLoad('/ipManagement/staticIPStatus');">default</a></LABEL>
						<LABEL> > </LABEL> <LABEL id="selectSegment"></LABEL>
						</h2>
				</div>
				<div class="box-body">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">List</a></li>
							<li><a href="#tab_2" data-toggle="tab">Map</a></li>
						</ul>
						<!-- tab-content -->
						<div class="tab-content">
							<!-- tab_1-pane -->
							<div class="tab-pane active" id="tab_1">
								<center>
									<table id="datatable_detail" name="datatable" class="essential-table" style="width: 98%">
										<thead>
											<tr>
												<th width="7%"><%=LanguageHelper.GetLanguage("ip")%></th>
												<th width="6%">IP type</th>
												<th width="8%"><%=LanguageHelper.GetLanguage("mac")%></th>
												<th width="7%">Duid</th>
												<th width="7%"><%=LanguageHelper.GetLanguage("status")%></th>
												<th width="10%"><%=LanguageHelper.GetLanguage("hostname")%></th>
												<th width="12%"><%=LanguageHelper.GetLanguage("hostos")%></th>
												<th width="13%">Fingerprint</th>
												<th width="8%"><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("starttime")%></th>
												<th width="8%"><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("endtime")%></th>
												<th width="14%">user description</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</center>
							</div>
							<!-- /.tab_1-pane -->
							<!-- tab_2-pane -->
							<div class="tab-pane" id="tab_2">
							<div class="row">
								<section class="col-lg-6-noPadding" style="width: 650px">
									<div id="holder">
								        <ul id="place">
								        </ul>
									    <div id="divLoading" class="overlay" style="visibility: hidden;" >
											<i class="fa fa-refresh fa-spin"></i>
										</div>
								    </div>
									<div id="holder-bottom">
										<button type="button" class="btn btn-info btn-sm" style="margin: 4px 8px;" onclick="mapRefresh();">
											<i class="fa fa-refresh"></i>
										</button>
								        <LABEL id="segmentLabel" style="font-size: 12px; float:right; margin: 10px;">
											<!--192.168.1.0 - 192.168.1.255 -->
								        </LABEL>
								    </div>
						        </section>
								<section>
						        	<table>
						        		<tr>
						        			<td>
									            <ul>
									               <li class="li_unused"><span class="li_Label">Unused</span></li>
									               <li class="li_used"><span class="li_Label">Used</span></li>
									               <li class="li_conflict"><span class="li_Label">Conflict</span></li>
									               <li class="li_pending"><span class="li_Label">Pending</span></li>
									               <li class="li_unmanaged"><span class="li_Label">Unmanaged</span></li>
									               <li class="li_fixed"><span class="li_Label">Fixed Address / Reservation</span></li>
									               <li class="li_object"><span class="li_Label">DNS Object</span></li>
									               <li class="li_hostnotindns"><span class="li_Label">Host<span class="ib-ipam-freeware-hidden">Not In DNS/DHCP</span></span></li>
									               <li class="li_activeLease"><span class="li_Label">Active Lease</span></li>
									            </ul>
						        			</td>
						        			<td class="ipmap-legend">
									            <ul>
									               <li class="li_Selecting"><span class="li_Label">Selected IP Address</span></li>
									           	   <li class="li_range"><span class="li_Label">DHCP Range</span></li>
									           	   <li class="li_exclusion"><span class="li_Label">DHCP Exclusion Range</span></li>
									           	   <li class="li_reservedrange"><span class="li_Label">Reserved Range</span></li>
									            </ul>
						        			</td>
						        		</tr>
						        	</table>
						        </section>
							</div>
							</div>
							<!-- /.tab_2-pane -->
						</div>
						<!-- /.tab-content -->
					</div>
					<!-- /.Custom Tabs -->
				</div>
			</div>
		</div>
	</div>
</section>