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
<div id="layDiv" style="visibility : hidden;">
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
				<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="tapheader">
							<li class="active" name="ListTap"><a href="#tab_1" data-toggle="tab">List</a></li>
							<li name="MapTap"><a href="#tab_2" data-toggle="tab">Map</a></li>
						</ul>
						<!-- tab-content -->
						<div class="tab-content">
							<!-- tab_1-pane -->
							<div class="tab-pane active" id="tab_1" name="ListTap">
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
												<th width="140px"><%=LanguageHelper.GetLanguage("rangeipcount")%></th>
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
							<!-- /.tab_1-pane -->
							<!-- tab_2-pane -->
							<div class="tab-pane" id="tab_2" name="MapTap">
								<div class="row" id="segmentHolder" style="padding: 0px 10px">
									<!-- /segmentHolderbox -->
<!-- 									<div class="col-md-20-minwidth-200" onclick="tdClickEvent('192.168.1.0/24,IPV4,192.168.1.0,192.168.1.255');"> -->
<!-- 										<div class="box box-success essential-cursor-pointer">		 -->
<!-- 											/box-header -->
<!-- 											<div class="box-header with-border-lightgray"> -->
<!-- 												<h3 class="box-title"><small>192.168.1.0/24</small></h3> -->
<!-- 												<div class="box-tools pull-right"> -->
<!-- 													<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 											/box-body -->
<!-- 											<div class="box-body-blackLine"> -->
<!-- 												<div class="progress-group"> -->
<!-- 													<small> -->
<!-- 														<table style="width: 100%"> -->
<!-- 															<tr> -->
<!-- 																<td><span>start</span></td> -->
<!-- 																<td><span>192.168.1.0</span></td> -->
<!-- 															</tr>															 -->
<!-- 															<tr> -->
<!-- 																<td><span>end</span></td> -->
<!-- 																<td><span>192.168.1.255</span></td> -->
<!-- 															</tr> -->
<!-- 															<tr> -->
<!-- 																<td colspan="2" style="height: 40px"> -->
<!-- 																	<span>사용률</span> -->
<!-- 																	<span class="progress-number"><b>480</b>/800</span> -->
<!-- 																	<div class="progress sm" style="height: 6px"> -->
<!-- 																		<div class="progress-bar progress-bar-green" style="width: 80%"></div> -->
<!-- 																	</div> -->
<!-- 																</td> -->
<!-- 															</tr> -->
<!-- 															<tr> -->
<!-- 																<td><span>commont :</span></td> -->
<!-- 																<td><span>isolation</span></td> -->
<!-- 															</tr> -->
<!-- 														</table>														 -->
<!-- 													</small> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
									<!-- /.segmentHolderbox -->
								</div>
							</div>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row" id="detailDiv" style="display: none;">
		<div class="col-lg-12">
			<div class="box box-primary">
				<div class="pagebox-header with-border">
					<h2 class="pagebox-title-small">
						<LABEL><a href="javascript:void(0)"
							onclick="contentLoad('/ipManagement/staticIPStatus');">default</a></LABEL>
						<LABEL> > </LABEL> <LABEL id="selectSegment"></LABEL>
					</h2>
				</div>
				<div class="box-body">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="detailtapheader">
							<li class="active" name="ListTap"><a href="#tab_1_detail" data-toggle="tab">List</a></li>
							<li name="MapTap"><a href="#tab_2_detail" data-toggle="tab">Map</a></li>
						</ul>
						<!-- tab-content -->
						<div class="tab-content">
							<!-- tab_1_detail-pane -->
							<div class="tab-pane active" id="tab_1_detail" name="ListTap">
								<center>
									<table id="datatable_detail" name="datatable" class="essential-table" style="width: 98%">
										<thead>
											<tr>
												<th width="8%"><%=LanguageHelper.GetLanguage("ip")%></th>
												<th width="7%">IP type</th>
												<th width="9%"><%=LanguageHelper.GetLanguage("mac")%></th>
												<th width="8%">Duid</th>
												<th width="8%"><%=LanguageHelper.GetLanguage("status")%></th>
												<th width="11%"><%=LanguageHelper.GetLanguage("hostname")%></th>
												<th width="15%"><%=LanguageHelper.GetLanguage("hostos")%></th>
												<th width="9%"><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("starttime")%></th>
												<th width="9%"><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("endtime")%></th>
												<th width="16%">user description</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</center>
							</div>
							<!-- /.tab_1_detail-pane -->
							<!-- tab_2_detail-pane -->
							<div class="tab-pane" id="tab_2_detail" name="MapTap">
								<div class="row">
<!-- 									<section class="col-lg-6-noPadding" style="width: 620px; margin-right: 30px"> -->
									<div  class="col-lg-4" style="width: 620px; margin-right: 30px">
										<div id="holder">
									        <ul id="place">
									        </ul>
										    <div id="divLoading" class="overlay" style="visibility: hidden;">
												<i class="fa fa-refresh fa-spin"></i>
											</div>
									    </div>
										<div id="holder-bottom">
											<button type="button" class="btn btn-info btn-sm" style="margin: 4px 20px;" onclick="mapRefresh();">
												<i class="fa fa-refresh"></i>
											</button>
									        <LABEL id="segmentLabel" style="font-size: 12px; float:right; margin: 10px 20px 10px 10px;">
												<!--192.168.1.0 - 192.168.1.255 -->
									        </LABEL>
									    </div>
								    </div>
<!-- 							        </section> -->
<!-- 									<section class="col-lg-7" > -->
									<div class="col-lg-6">
							        	<table class="col-lg-12">
							        		<tr>
							        			<td style="width:150px">
										            <ul class="ul_legend">									               
										               <li class="li_legend"><img src="../resources/images/ipmap/network.png"><span class="li_Label">Network</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/broadcast.png"><span class="li_Label">Broadcast</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/abandoned.png"><span class="li_Label">Abandoned</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/fixed.png"><span class="li_Label">Fixed</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/reservation.png"><span class="li_Label">Reservation</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/conflict.png"><span class="li_Label">Conflict</span></li>
										            </ul>
							        			</td>
							        			<td class="ipmap-legend">
										            <ul>
										               <li class="li_legend"><img src="../resources/images/ipmap/lease.png"><span class="li_Label">Lease</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/unused.png"><span class="li_Label">Unused</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/used.png"><span class="li_Label">Used</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/selected-unused.png"><span class="li_Label">IP Selected</span></li>
										               <li class="li_legend"><img src="../resources/images/ipmap/dhcp-unused.png"><span class="li_Label">DHCP Range</span></li>
										            </ul>
							        			</td>
							        		</tr>
							        		<tr>
							        			<td colspan="2">
							        				<div class="box box-primary" style="margin-top: 20px; margin-left: -8px;">
														<div class="box-body box-profile">
															<ul class="list-group list-group-unbordered col-xs-6" style="padding-right: 10px">
																<li class="li_legend" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("ip")%></b>
																	<a class="pull-right" id="mapDetailip"></a>
																</li>
																<li class="list-group-item" style="overflow: auto;">
																	<b>duid</b> <a class="pull-right" id="mapDetailduid"></a>
																</li>
																<li class="list-group-item" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("hostname")%></b>
																	<a class="pull-right" id="mapDetailhostname"></a>
																</li>
																<li class="list-group-item" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("starttime")%></b>
																	<a class="pull-right" id="mapDetailleasestart"></a>
																</li>
															</ul>
															<ul class="list-group list-group-unbordered col-xs-6" style="padding-left: 10px">
																<li class="li_legend" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("mac")%></b>
																	<a class="pull-right" id="mapDetailmacaddr"></a>
																</li>
																<li class="list-group-item" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("status")%></b>
																	<a class="pull-right" id="mapDetailstatus"></a>
																</li>
																<li class="list-group-item" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("hostos")%></b>
																	<a class="pull-right" id="mapDetailhostos" style="overflow: hidden;"></a>
																</li>
																<li class="list-group-item" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("lease")%> <%=LanguageHelper.GetLanguage("endtime")%></b>
																	<a class="pull-right" id="mapDetailleaseend"></a>
																</li>
															</ul>
															<ul class="list-group list-group-unbordered col-xs-12" style="margin-top: -12px; margin-bottom: 0px;">
																<li class="li_legend" style="overflow: auto;">
																	<b><%=LanguageHelper.GetLanguage("desctription")%></b>
																	<a class="pull-right" id="mapDetaildescription"></a>
																</li>
															</ul>
														</div>
													</div>
							        			</td>
							        		</tr>
							        	</table>
<!-- 							        </section> -->
									</div>
								</div>
							</div>
							<!-- /.tab_2_detail-pane -->
						</div>
						<!-- /.tab-content -->
					</div>
					<!-- /.Custom Tabs -->
				</div>
			</div>
		</div>
	</div>
</section>