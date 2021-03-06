<%@ page import="Common.Helper.LanguageHelper"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet"
	href="resources/css/systemManagement/infobloxStatus.css">

<!-- Alert Start -->
<div id="layDiv" style="visibility : hidden;">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>
<section class="white-paper" style="text-align: center">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">장비상태</h3>
					<div class='hw-collect-time collect-time'></div>
					<div id="reflesh-bottom">
						<button type="button" class="btn dashboard-box-btn btn-info btn-sm"
							style="margin: 4px 8px;" onclick="refleshDatas();">
							<i class="fa fa-refresh"></i>
						</button>
						<LABEL id="segmentLabel"
							style="font-size: 12px; float: right; margin: 10px;"> <!--192.168.1.0 - 192.168.1.255 -->
						</LABEL>
					</div>
					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn dashboard-box-btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body">
					<div class="col-xs-12" style="height: 15px"></div>
					<div class="col-xs-2">
						<input id="iCpuUsg" type="text" class="knob" data-thickness="0.3"
							data-angleArc="250" data-angleOffset="-125" data-max="100"
							value="0" data-width="160" data-height="140"
							data-inputColor="#00c0ef" data-bgColor="#e6e6e6"
							data-fgColor="#00c0ef" data-readonly="true">

						<div class="knob-label" style="margin-top: -24px;">
							<LABEL style="text-align: center; font-weight: normal;">CPU</LABEL>
						</div>
					</div>
					<div class="col-xs-2">
						<input id="iMemoryUsg" type="text" class="knob"
							data-thickness="0.3" data-angleArc="250" data-angleOffset="-125"
							data-max="100" value="0" data-width="160" data-height="140"
							data-inputColor="#00c0ef" data-bgColor="#e6e6e6"
							data-fgColor="#00c0ef" data-readonly="true">

						<div class="knob-label" style="margin-top: -24px;">
							<LABEL style="text-align: center; font-weight: normal;">Memory</LABEL>
						</div>
					</div>
					<div class="col-xs-2">
						<input id="iSwapUsg" type="text" class="knob" data-thickness="0.3"
							data-angleArc="250" data-angleOffset="-125" data-max="100"
							value="0" data-width="160" data-height="140"
							data-inputColor="#00c0ef" data-bgColor="#e6e6e6"
							data-fgColor="#00c0ef" data-readonly="true">

						<div class="knob-label" style="margin-top: -24px;">
							<LABEL style="text-align: center; font-weight: normal;">Swap</LABEL>
						</div>
					</div>
					<div class="col-xs-2">
						<input id="iDbUsg" type="text" class="knob" data-thickness="0.3"
							data-angleArc="250" data-angleOffset="-125" data-max="100"
							value="0" data-width="160" data-height="140"
							data-inputColor="#00c0ef" data-bgColor="#e6e6e6"
							data-fgColor="#00c0ef" data-readonly="true">

						<div class="knob-label" style="margin-top: -24px;">
							<LABEL style="text-align: center; font-weight: normal;">Database</LABEL>
						</div>
					</div>
					<div class="col-xs-2">
						<input id="iDiskUsg" type="text" class="knob" data-thickness="0.3"
							data-angleArc="250" data-angleOffset="-125" data-max="100"
							value="0" data-width="160" data-height="140"
							data-inputColor="#00c0ef" data-bgColor="#e6e6e6"
							data-fgColor="#00c0ef" data-readonly="true">

						<div class="knob-label" style="margin-top: -24px;">
							<LABEL style="text-align: center; font-weight: normal;">Disk</LABEL>
						</div>
					</div>
					<div class="col-xs-2">
						<input id="iCapacityUsg" type="text" class="knob"
							data-thickness="0.3" data-angleArc="250" data-angleOffset="-125"
							data-max="100" value="0" data-width="160" data-height="140"
							data-inputColor="#00c0ef" data-bgColor="#e6e6e6"
							data-fgColor="#00c0ef" data-readonly="true">

						<div class="knob-label" style="margin-top: -24px;">
							<LABEL style="text-align: center; font-weight: normal;">Capacity</LABEL>
						</div>
					</div>
					<div class="col-xs-12" style="height: 14px"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">장비정보</h3>
					<div class='hw-collect-time collect-time'
						style="visibility: hidden;"></div>
					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn dashboard-box-btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body" style="text-align: left">
					<div class="col-xs-6">
						<div class="custom-box">
							<label class="info-label">Hardware ID</label><b
								class="info-label-b">:</b><span id='id-info'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Host</label><b class="info-label-b">:</b><span
								id='host-info'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Host Name</label><b
								class="info-label-b">:</b><span id='host-name-info'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">System up-time</label><b
								class="info-label-b">:</b><span id='uptime-info'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Temperature</label><b
								class="info-label-b">:</b>System&nbsp;<span id='sys-temp-info'></span>&#x2103&nbsp;&nbsp;/&nbsp;&nbsp;CPU&nbsp;<span
								id='cpu-temp-info'></span>&#x2103
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">OS</label><b class="info-label-b">:</b><span
								id='os-info'></span>
							<div class="custom-border p100"></div>
						</div>

					</div>
					<div class="col-xs-6">
						<div class="custom-box">
							<label class="info-label">Power</label><b class="info-label-b">:</b><i
								id="power1-status" class="fa fa-circle"></i>&nbsp;&nbsp;POWER1&nbsp;&nbsp;<i
								id="power2-status" class="fa fa-circle"></i>&nbsp;&nbsp;POWER2
							<div class="custom-border p100"></div>

						</div>
						<div style="margin-top: 8px;">
							<!-- 						style="float: left" -->
							<table id='fan-info'>
								<tr>
									<td><label class="info-label">Fan</label><b
										class="info-label-b">:</b></td>
								</tr>
							</table>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<table id='license-info'>
								<tr>
									<td><label class="info-label">License</label><b
										class="info-label-b">:</b></td>
								</tr>
							</table>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Service Status</label><b
								class="info-label-b">:</b><i id="dhcp-is-enable"
								class="fa fa-circle"></i>&nbsp;&nbsp;DHCP&nbsp;&nbsp;<i
								id="dns-is-enable" class="fa fa-circle"></i>&nbsp;&nbsp;DNS
							<div class="custom-border p100"></div>
						</div>
					</div>
					<div class="col-xs-12" style="height: 38px"></div>
				</div>

			</div>

		</div>

		<div class="col-xs-4">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">이중화정보</h3>
					<div class='collect-time' id='reduncdancy-collect-time'
						style="visibility: hidden;"></div>
					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn dashboard-box-btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body" style="text-align: left">
					<div class="col-xs-12">
						<div class="custom-box">
							<label class="info-label">Port Redundancy</label>&nbsp;&nbsp;<i
								id="lan2-port-setting" class="fa fa-circle"></i>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Enabled HA</label>&nbsp;&nbsp;<i
								id="enable-ha" class="fa fa-circle"></i>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Vrrp IpAddress</label><b
								class="info-label-b">:</b><span id='vrrp-address'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Vrrp SubnetMask</label><b
								class="info-label-b">:</b><span id='vrrp-subnet'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Vrrp Gateway</label><b
								class="info-label-b">:</b><span id='vrrp-gateway'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Enabled Grid</label>&nbsp;&nbsp;<i
								id="master-candidate" class="fa fa-circle"></i>
							<div class="custom-border p100"></div>
						</div>
						<div class="custom-box">
							<label class="info-label">Grid Status</label><b
								class="info-label-b">:</b><span id='grid-status'></span>
							<div class="custom-border p100"></div>
						</div>
						<div class="col-xs-12" style="height: 8px"></div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-xs-6">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">DHCP Messages</h3>
					<div class='collect-time' id='dhcp-collect-time'
						style="visibility: hidden;"></div>

					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn dashboard-box-btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body" style="text-align: left">
					<div id="dhcp-body" class="chart-responsive">
						<div id="dhcpPieChart"
							style="height: 180px; width: 180px; float: left;"></div>
						<div id="dhcp-table" style="float: left; height: 180px;">
							<table id="dhcp-info-table" style="width: 98%; height: 100%">
								<!-- Discovers  Offers  Requests  ACKs  -->
								<tr class="dhcp-table-top-column">
									<td id="dhcp-td-1" style="text-align: right;"></td>
									<td id="dhcp-td-2" style="text-align: right;"></td>
									<td id="dhcp-td-3" style="text-align: right;"></td>
									<td id="dhcp-td-4" style="text-align: right;"></td>
								</tr>
								<!-- NACKs  Declines  Informs  Releases  -->
								<tr class="dhcp-table-top-column">
									<td id="dhcp-td-5" style="text-align: right;"></td>
									<td id="dhcp-td-6" style="text-align: right;"></td>
									<td id="dhcp-td-7" style="text-align: right;"></td>
									<td id="dhcp-td-8" style="text-align: right;"></td>
								</tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xs-6">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">DNS Messages</h3>
					<div class='collect-time' id='dns-collect-time'
						style="visibility: hidden;"></div>

					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn dashboard-box-btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body" style="text-align: left">
					<div id="dns-body" class="chart-responsive">
						<div id="dnsPieChart"
							style="height: 180px; width: 180px; float: left;"></div>
						<div id="dns-table" style="float: left; height: 180px;">
							<table id="dns-info-table" style="width: 98%; height: 100%">
								<!-- Discovers  Offers  Requests  ACKs  -->
								<tr class="dns-table-top-column">
									<td id="dns-td-1" style="text-align: right;"></td>
									<td id="dns-td-2" style="text-align: right;"></td>
									<td id="dns-td-3" style="text-align: right;"></td>
								</tr>
								<!-- NACKs  Declines  Informs  Releases  -->
								<tr class="dns-table-top-column">
									<td id="dns-td-4" style="text-align: right;"></td>
									<td id="dns-td-5" style="text-align: right;"></td>
									<td id="dns-td-6" style="text-align: right;"></td>
								</tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- <script src="resources/plugins/jQuery/jquery-3.1.0.min.js"></script> -->
<!-- jQuery Knob -->
<script src="../resources/plugins/knob/jquery.knob.js"></script>
<!-- ChartJS 1.0.1 -->
<script src="../resources/plugins/chartjs/Chart.min.js"></script>

<!-- FLOT CHARTS -->
<script src="../resources/plugins/flot/jquery.flot.min.js"></script>
<!-- FLOT RESIZE PLUGIN - allows the chart to redraw when the window is resized -->
<script src="../resources/plugins/flot/jquery.flot.resize.min.js"></script>
<!-- FLOT PIE PLUGIN - also used to draw donut charts -->
<script src="../resources/plugins/flot/jquery.flot.pie.min.js"></script>
<!-- FLOT CATEGORIES PLUGIN - Used to draw bar charts -->
<script src="../resources/plugins/flot/jquery.flot.categories.min.js"></script>

<script src="../resources/js/common/Common.js"></script>
<script src="resources/js/systemManagement/infobloxStatus.js"></script>

