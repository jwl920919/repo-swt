<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="Common.Helper.LanguageHelper"%>


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="../resources/bootstrap/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="../resources/bootstrap/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="../resources/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="../resources/dist/css/skins/_all-skins.min.css">
<!-- jQuery Knob -->
<script src="../resources/plugins/knob/jquery.knob.js"></script>
<!-- ChartJS 1.0.1 -->
<script src="../resources/plugins/chartjs/Chart.min.js"></script>
<!-- <!-- FLOT CHARTS -->
<!-- <script src="../resources/plugins/flot/jquery.flot.min.js"></script> -->
<!-- <!-- FLOT RESIZE PLUGIN - allows the chart to redraw when the window is resized -->
<!-- <script src="../resources/plugins/flot/jquery.flot.resize.min.js"></script> -->
<!-- <!-- FLOT PIE PLUGIN - also used to draw donut charts -->
<!-- <script src="../resources/plugins/flot/jquery.flot.pie.min.js"></script> -->
<!-- <!-- FLOT CATEGORIES PLUGIN - Used to draw bar charts -->
<!-- <script src="../resources/plugins/flot/jquery.flot.categories.min.js"></script> -->

<script src="../resources/js/dashboard/dashboard.js"></script>

<!-- Small boxes (System Status box) -->
<div class="row" id="dashboardSystemStatus">
	<!-- CPU 사용량 -->
	<div class="col-lg-20per col-xs-6" id="systemCPUStatus">
		<!-- small box -->
		<div class="small-box bg-aqua">
			<div class="inner" style="height: 115px">
				<%-- 				<p><%=LanguageHelper.GetLanguage("cpu")%> <%=LanguageHelper.GetLanguage("utilization")%></p> --%>
				<%=LanguageHelper.GetLanguage("cpu")%>
				<%=LanguageHelper.GetLanguage("utilization")%></br> <LABEL id="lCPUValue"
					style="font-size: 25px; margin-top: 5px;">0</LABEL>
			</div>
			<div class="icon" style="height: 115px">
				<i class="fa fa-desktop"></i>
			</div>
			<%-- 			<a href="#" class="small-box-footer"><%=LanguageHelper.GetLanguage("moreInfo")%><i --%>
			<!-- 				class="fa fa-arrow-circle-right"></i></a> -->
		</div>
	</div>
	<!-- ./CPU 사용량  -->
	<!-- Memory 사용량 -->
	<div class="col-lg-20per col-xs-6" id="systemMemoryStatus">
		<!-- small box -->
		<div class="small-box bg-green">
			<div class="inner" style="height: 115px">
				<%=LanguageHelper.GetLanguage("memory")%>
				<%=LanguageHelper.GetLanguage("utilization")%></br> <LABEL
					id="lMemoryValue" style="font-size: 30px; margin-top: 15px;">0</LABEL>
				<!-- 					<sup style="font-size: 15px">%</sup> -->
			</div>
			<div class="icon">
				<i class="fa fa-joomla"></i>
			</div>
			<%-- 			<a href="#" class="small-box-footer"><%=LanguageHelper.GetLanguage("moreInfo")%><i --%>
			<!-- 				class="fa fa-arrow-circle-right"></i></a> -->
		</div>
	</div>
	<!-- ./Memory 사용량 -->
	<!-- Disk 사용량 -->
	<div class="col-lg-20per col-xs-6" id="systemDiskStatus">
		<!-- small box -->
		<div class="small-box bg-blue">
			<div class="inner" style="height: 115px">
				<%=LanguageHelper.GetLanguage("disk")%>
				<%=LanguageHelper.GetLanguage("usage")%></br> <LABEL id="lDiskValue"
					style="font-size: 25px; margin-top: 5px;">0</LABEL>
			</div>
			<div class="icon">
				<i class="fa fa-save"></i>
			</div>
			<%-- 			<a href="#" class="small-box-footer"><%=LanguageHelper.GetLanguage("moreInfo")%><i --%>
			<!-- 				class="fa fa-arrow-circle-right"></i></a> -->
		</div>
	</div>
	<!-- ./Disk 사용량 -->
	<!-- Network 사용량 -->
	<div class="col-lg-20per col-xs-6" id="systemNetworkStatus">
		<!-- small box -->
		<div class="small-box bg-yellow">
			<div class="inner" style="height: 115px;">
				<%=LanguageHelper.GetLanguage("network")%>
				<%=LanguageHelper.GetLanguage("utilization")%></br> 
				<LABEL id="lNetworkValue" style="font-size: 25px; margin-top: 5px;">0</LABEL>
			</div>
			<div class="icon">
				<i class="fa fa-exchange"></i>
			</div>
			<%-- 			<a href="#" class="small-box-footer"><%=LanguageHelper.GetLanguage("moreInfo")%><i --%>
			<!-- 				class="fa fa-arrow-circle-right"></i></a> -->
		</div>
	</div>
	<!-- ./Network 사용량 -->
	<!-- HA 상태 -->
	<div class="col-lg-20per col-xs-6" id="systemHAStatus">
		<!-- small box -->
		<div class="small-box bg-teal">
			<div class="inner" style="height: 115px">
				<%=LanguageHelper.GetLanguage("h/a")%>
				<%=LanguageHelper.GetLanguage("status")%></br> <LABEL id="lHAValue"
					style="font-size: 25px; margin-top: 15px;">0</LABEL>
			</div>
			<div class="icon">
				<i class="fa fa-object-ungroup"></i>
			</div>
			<%-- 			<a href="#" class="small-box-footer"><%=LanguageHelper.GetLanguage("moreInfo")%><i --%>
			<!-- 				class="fa fa-arrow-circle-right"></i></a> -->
		</div>
	</div>
	<!-- ./HA 상태  -->
</div>
<!-- ./Small boxes (System Status box) -->

<!-- main row -->
<div class="row">
	<!-- main section -->
	<section class="col-lg-12-noPadding connectedSortable">
		<!-- main top row -->
		<div>
			<!-- left col -->
			<section class="col-lg-6-noPadding">
				<!-- left col - 1 row -->
				<div>
					<!-- Guest IP 할당현황 -->
					<section class="col-lg-6">
						<div class="box box-success">
							<div class="box-header">
								<i class="fa fa-bar-chart-o"></i>
	
								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("guestIPAssinment")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body">
								<!-- ./col -->
				                <div class="col-xs-12 text-center" style="height: 120px;">
				                	<input id="iGuestIPAssing" type="text" class="knob" data-thickness="0.3" data-angleArc="250" data-angleOffset="-125" 
				                		data-max="100" value="0" data-width="110" data-height="100" 
				                		data-inputColor="#00c0ef" 
				                		data-bgColor="#e6e6e6" 
				                		data-fgColor="#00c0ef" data-readonly="true">
				
									<div class="knob-label" style="margin-top: -24px;">
				                		<LABEL id="lGuestIPAssingTotal" style="text-align: center; font-weight: normal;"></LABEL>
				                	</div>
				                	<div class="knob-label" >
				                		<LABEL id="lGuestIPAssingValue" style=";text-align: center; font-size:13px; font-weight: normal;"></LABEL>
			                		</div>
				                </div>
				                <!-- ./col -->
							</div>
						</div>
					</section>
					<!-- ./Guest IP 할당현황 -->
					
					<!-- DNS 할당 현황  -->
					<section class="col-lg-6">
						<div class="box box-info">
							<div class="box-header">
								<i class="fa fa-envelope"></i>

								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("dnsAssignment")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body">
								<table class="col-xs-12 table-bordered" style="text-align: center; height: 135px; margin-top: -15px;">
									<tr style="height: 30%; background-color:  #404040;">
										<td class="col-xs-4" style="vertical-align: bottom"><font color="#ffffff" size="4"><label id=lSuccess></label></font>
										</td>
										<td class="col-xs-4" style="vertical-align: bottom"><font color="#ffffff" size="4"><label id=lReferral></label></font>
										</td>
										<td class="col-xs-4" style="vertical-align: bottom"><font color="#ffffff" size="4"><label id=lNXRRSet></label></font>
										</td>
									</tr>
									<tr style="height: 20%;">
										<td style="background-color:  #bfff80;">Success
										</td>
										<td style="background-color:  #ffc266;">Referral
										</td>
										<td style="background-color:  #e6e600;">NXRRSet
										</td>
									</tr>
									<tr style="height: 30%; background-color:  #404040;">
										<td class="col-xs-4" style="vertical-align: bottom"><font color="#ffffff" size="4"><label id=lNXDomain></label></font>
										</td>
										<td class="col-xs-4" style="vertical-align: bottom"><font color="#ffffff" size="4"><label id=lRecursion></label></font>
										</td>
										<td class="col-xs-4" style="vertical-align: bottom"><font color="#ffffff" size="4"><label id=lFailure></label></font>
										</td>
									</tr>
									<tr style="height: 20%;">
										<td style="background-color:  #008080;">NXDomain
										</td>
										<td style="background-color:  #b3ffb3;">Recursion
										</td>
										<td style="background-color:  #ff4d88;">Failure
										</td>
									</tr>
								</table>
							</div>
						</div>
					</section>
					<!-- ./DNS 할당 현황  -->
				</div>
				<!-- ./left col - 1 row -->
				<!-- left col - 2 row -->
				<div class="col-lg-12">				
					<!-- 인증 처리 현황 -->
					<div class="box box-warning">
						<div class="box-header">
							<i class="fa fa-envelope"></i>

							<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("certifyProcess")%></h3>
							<!-- tools box -->
							<div class="box-tools pull-right">
							  Real time
								<div class="btn-group" id="realtime" data-toggle="btn-toggle">
									<button type="button" class="btn btn-default btn-xs active" data-toggle="on">On</button>
									<button type="button" class="btn btn-default btn-xs" data-toggle="off">Off</button>
								</div>
								<button type="button" class="btn bg-teal btn-sm"
									data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button>
								<button type="button" class="btn bg-teal btn-sm"
									data-widget="remove" data-toggle="tooltip" title="Remove">
									<i class="fa fa-times"></i>
								</button>
							</div>
							<!-- ./ tools -->
						</div>
						<div class="box-body">
		                  	<!-- chart-responsive -->
							<p class="text-center">
								<strong>Sales: 1 Jan, 2014 - 30 Jul, 2014</strong>
							</p>

		                  	<div class="chart">
			                    <canvas id="certifyProcessChart" style="height: 150px;"></canvas>
			                    
<!--               					<div id="interactive" style="height: 120px;"></div> -->
		                  	</div>
		                  	<!-- /.chart-responsive -->
						</div>
					</div>
					<!-- ./인증 처리 현황 -->
				</div>
				<!-- ./left col - 2 row -->
				<!-- left col - 3 row -->
				<div>
					<!-- HW별 사용 현황 -->
					<section class="col-lg-6">
						<div class="box box-blue">
							<div class="box-header">
								<i class="fa fa-envelope"></i>
	
								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("hardwareByUsed")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body"></div>
						</div>
					</section>
					<!-- ./HW별 사용 현황 -->
					<!-- OS별 사용 현황 -->
					<section class="col-lg-6">
						<div class="box box-red">
							<div class="box-header">
								<i class="fa fa-envelope"></i>

								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("osByUsed")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body"></div>
						</div>
					</section>
					<!-- ./OS별 사용 현황 -->
				</div>
				<!-- ./left col - 3 row -->
			</section>
			<!-- ./left col -->
			
			<!-- right col -->
			<section class="col-lg-6-noPadding">
				<!-- right col - 1 row -->
				<div >
					<!-- 세그먼트별 Lease IP 할당 현황 -->
					<section class="col-lg-6">
						<div class="box box-yellow">
							<div class="box-header">
								<i class="fa fa-envelope"></i>

								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("segmentByLeaseIP")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body">
								<div class="col-md-4" id="divleaseIPAvailable">
								</div>
							</div>
						</div>
					</section>
					<!-- ./세그먼트별 Lease IP 할당 현황 -->
					
					<!-- 고정, 리스, 미사용 IP 할당 현황  -->
					<section class="col-lg-6">
						<div class="box box-green">
							<div class="box-header">
								<i class="fa fa-envelope"></i>

								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("fixedleaseunusedipallocationstatus")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body"></div>
						</div>
					</section>
					<!-- ./고정, 리스, 미사용 IP 할당 현황  -->
				</div>
				<!-- ./right col - 1 row -->
				<!-- right col - 2 row -->
				<div class="col-lg-12">
					<!-- IP 신청 현황  -->
					<div class="box box-gray">
						<div class="box-header">
							<i class="fa fa-envelope"></i>

							<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("askIPStatus")%></h3>
							<!-- tools box -->
							<div class="pull-right box-tools">
								<button type="button" class="btn bg-teal btn-sm"
									data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button>
								<button type="button" class="btn bg-teal btn-sm"
									data-widget="remove" data-toggle="tooltip" title="Remove">
									<i class="fa fa-times"></i>
								</button>
							</div>
							<!-- ./ tools -->
						</div>
						<div class="box-body"></div>
					</div>
					<!-- ./IP 신청 현황  -->
				</div>					
				<!-- ./right col - 2 row -->
				<!-- right col - 3 row -->
				<div>
					<!-- 서비스별 사용 현황 -->
					<section class="col-lg-6">
						<div class="box box-cyan">
							<div class="box-header">
								<i class="fa fa-envelope"></i>

								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("serviceByUsed")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body"></div>
						</div>
					</section>
					<!-- ./서비스별 사용 현황 -->
					<!-- 벤더별 사용 현황 -->
					<section class="col-lg-6-noPadding">
						<div class="box box-brown">
							<div class="box-header">
								<i class="fa fa-envelope"></i>

								<h3 class="box-title-small"><%=LanguageHelper.GetLanguage("VendorByUsed")%></h3>
								<!-- tools box -->
								<div class="pull-right box-tools">
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn bg-teal btn-sm"
										data-widget="remove" data-toggle="tooltip" title="Remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
								<!-- ./ tools -->
							</div>
							<div class="box-body"></div>
						</div>
					</section>
					<!-- ./더별 사용 현황 -->
				</div>
				<!-- ./right col - 3 row -->
			</section>
			<!-- ./right col -->			
		</div>
		<!-- ./main top row -->
		
		<!-- main bottom row -->		
		<!-- 통합이벤트 -->
		<div class="col-lg-12">
			<div class="box box-purple">
				<div class="box-header">
					<i class="fa fa-envelope"></i>
	
					<h3 class="box-title"><%=LanguageHelper.GetLanguage("eventLog")%></h3>
					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
						<button type="button" class="btn bg-teal btn-sm"
							data-widget="remove" data-toggle="tooltip" title="Remove">
							<i class="fa fa-times"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body"></div>
			</div>
		</div>
		<!-- ./통합이벤트 -->		
		<!-- ./main bottom row -->
	</section>
	<!-- ./main section -->
</div>
<!-- ./main row -->

