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
<div id="layDiv">
	<div class="alert-box"></div>
	<div id="divAlertArea"></div>
</div>
<section class="white-paper" style="text-align: center">
	<div class="row box box-primary">
		<div class="box-header" style="text-align: left;">
			<i class="fa fa-cog"></i>
			<h3 class="box-title-small">장비상태</h3>
			<!-- tools box -->
			<div class="pull-right box-tools">
				<button type="button" class="btn bg-teal btn-sm"
					data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
			</div>
			<!-- ./ tools -->
		</div>
		<div class="box-body">
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
				<input id="iMemoryUsg" type="text" class="knob" data-thickness="0.3"
					data-angleArc="250" data-angleOffset="-125" data-max="100"
					value="0" data-width="160" data-height="140"
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
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6">
			<div class="box box-primary">
				<div class="box-header" style="text-align: left;">
					<i class="fa fa-cog"></i>
					<h3 class="box-title-small">장비정보</h3>
					<!-- tools box -->
					<div class="pull-right box-tools">
						<button type="button" class="btn bg-teal btn-sm"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- ./ tools -->
				</div>
				<div class="box-body" style="text-align: left">
					<div>
						<label class="info-label">Hardware ID</label><b class="info-label-b">:</b><span id='id-info'></span>
					</div>
					<div>
						<label class="info-label">System up-time</label><b class="info-label-b">:</b><span id='uptime-info'></span>
					</div>
					<div>
						<label class="info-label">Temperature</label><b class="info-label-b">:</b><span id='temp-info'></span>&#x2103
					</div>
					<div>
						<label class="info-label">Power</label><b class="info-label-b">:</b><i id="power1-status" class="fa fa-circle"></i>&nbsp;&nbsp;POWER1&nbsp;&nbsp;<i id="power2-status" class="fa fa-circle"></i>&nbsp;&nbsp;POWER2
					</div>
					<div style="min-height: 25px;">
						<label class="info-label" style="float:left">Fan</label><b class="info-label-b" style="float:left">:</b><table id='fan-info' style="float:left"></table>
					</div>
					<div style="min-height: 25px;">
						<label class="info-label" style="float:left">License</label><b class="info-label-b" style="float:left">:</b><table id='license-info' style="float:left"></table>
					</div>
					<div>
						<label class="info-label">OS</label><b class="info-label-b">:</b><span id='os-info'></span>
					</div>
					<div>
						<label class="info-label">Service Status</label><b class="info-label-b">:</b><i id="dhcp-is-enable" class="fa fa-circle"></i>&nbsp;&nbsp;DHCP&nbsp;&nbsp;<i id="dns-is-enable" class="fa fa-circle"></i>&nbsp;&nbsp;DNS
					</div>
				</div>
			</div>
		</div>

	</div>
</section>


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
