<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>

<!-- IP Map style -->
<link rel="stylesheet" href="resources/css/ipmap.css">

<script src="resources/js/main/main.js"></script>
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
				<div class="box-header" style="margin-bottom: -10px">
					<h1 class="box-title-small">
						<LABEL>default</LABEL>
						</h2>
				</div>
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
									<table id="datatable_detail" class="essential-table"
										style="width: 98%">
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
								    </div>
									<div id="holder-bottom">
								        <LABEL id="segmentLabel" style="font-size: 12px; float:right; margin: 10px;">192.168.1.0 - 192.168.1.255</LABEL>
								    </div>
						        </section>
								<section class="col-lg-6-noPadding">
									<div id="ib-ipmap-legend-advanced">  
							        	<span id="ipmap-legend-advanced"></span>
							        	<table>
							        		<tr>
							        			<td>
										            <ul class="ipmap-legend-ul">
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
							        			<td>
										            <ul class="ipmap-legend-ul">
										               <li class="li_Selecting"><span class="li_Label">Selected IP Address</span></li>
										           	   <li class="li_range"><span class="li_Label">DHCP Range</span></li>
										           	   <li class="li_exclusion"><span class="li_Label">DHCP Exclusion Range</span></li>
										           	   <li class="li_reservedrange"><span class="li_Label">Reserved Range</span></li>
										            </ul>
							        			</td>
							        		</tr>
							        	</table>
							        </div>
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