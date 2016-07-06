var m_systemStatusAjaxCall;
var m_guestIPAssignStatusAjaxCall;
var m_certifyProcessAjaxCall;
var m_dnsStatusAjaxCall;
var m_segmentLeasingIPAssignedAjaxCall;
var m_assignmentIPStatusAjaxCall;
var m_hwUsedStatusAjaxCall;
var m_osUsedStatusAjaxCall;
var m_serviceUsedStatusAjaxCall;
var m_vendorUsedStatusAjaxCall;

$(document).ready(
		function() {
//			$("#contentHeaderDepth1li").attr('style', 'display: none;');
//			$("#contentHeaderDepth2li").attr('style', 'display: none;');

			ivalue = 0;
			AllClearAjaxCall();
			m_systemStatusAjaxCall = setInterval(systemStatusAjaxCall, 0);// 페이지 로딩 후 바로 데이터 조회
			m_guestIPAssignStatusAjaxCall = setInterval(
					guestIPAssignStatusAjaxCall, 0);
			certifyProcessAjaxCall();
			m_dnsStatusAjaxCall = setInterval(dnsStatusAjaxCall, 0);
			m_segmentLeasingIPAssignedAjaxCall = setInterval(segmentLeasingIPAssignedAjaxCall, 0);
			m_assignmentIPStatusAjaxCall = setInterval(assignmentIPStatusAjaxCall, 0);

			m_hwUsedStatusAjaxCall = setInterval(hwUsedStatusAjaxCall, 0);
			m_osUsedStatusAjaxCall = setInterval(osUsedStatusAjaxCall, 0);
			m_serviceUsedStatusAjaxCall = setInterval(serviceUsedStatusAjaxCall, 0);
			m_vendorUsedStatusAjaxCall = setInterval(vendorUsedStatusAjaxCall, 0);

			jQueryKnob(); // jQueryKnob 차트관련 스크립트

			//Pie Chart Tooltip Bind
			fnPieChartTooltipBind($("#osPieChart"));
			fnPieChartTooltipBind($("#hwPieChart"));
			fnPieChartTooltipBind($("#servicePieChart"));
			fnPieChartTooltipBind($("#vendorPieChart"));
});

// jQueryKnob 차트관련 스크립트
function jQueryKnob() {
	/* jQueryKnob 차트관련 스크립트 */
	$(".knob")
			.knob(
					{
						/*
						 * change : function (value) { //console.log("change : " +
						 * value); }, release : function (value) {
						 * console.log("release : " + value); }, cancel :
						 * function () { console.log("cancel : " + this.value); },
						 */
						draw : function() {

							// "tron" case
							if (this.$.data('skin') == 'tron') {

								var a = this.angle(this.cv) // Angle
								, sa = this.startAngle // Previous start angle
								, sat = this.startAngle // Start angle
								, ea // Previous end angle
								, eat = sat + a // End angle
								, r = true;

								this.g.lineWidth = this.lineWidth;

								this.o.cursor && (sat = eat - 0.3)
										&& (eat = eat + 0.3);

								if (this.o.displayPrevious) {
									ea = this.startAngle
											+ this.angle(this.value);
									this.o.cursor && (sa = ea - 0.3)
											&& (ea = ea + 0.3);
									this.g.beginPath();
									this.g.strokeStyle = this.previousColor;
									this.g.arc(this.xy, this.xy, this.radius
											- this.lineWidth, sa, ea, false);
									this.g.stroke();
								}

								this.g.beginPath();
								this.g.strokeStyle = r ? this.o.fgColor
										: this.fgColor;
								this.g.arc(this.xy, this.xy, this.radius
										- this.lineWidth, sat, eat, false);
								this.g.stroke();

								this.g.lineWidth = 2;
								this.g.beginPath();
								this.g.strokeStyle = this.o.fgColor;
								this.g.arc(this.xy, this.xy, this.radius
										- this.lineWidth + 1 + this.lineWidth
										* 2 / 3, 0, 2 * Math.PI, false);
								this.g.stroke();

								return false;
							}
						}
					});
	/* END JQUERY KNOB */
}

var ivalue = 0;
// 시스템 현황 Ajax Call 메서드
function systemStatusAjaxCall() {

	try {
		ivalue = ivalue + 1;
		// console.log("ajaxCall count : " + ivalue);

		clearSystemStatusAjaxCall();
		m_systemStatusAjaxCall = setInterval(systemStatusAjaxCall, 1000);// 페이지로딩데이터 조회 후 polling 시간 변경

		var vCPU1 = Math.floor(Math.random() * 101);
		var vCPU2 = Math.floor(Math.random() * 101);
		var vMem = Math.floor(Math.random() * 9);
		var vNet1 = Math.floor(Math.random() * 201);
		var vNet2 = Math.floor(Math.random() * 201);
		var vDisk1 = Math.floor(Math.random() * 101);
		var vDisk2 = Math.floor(Math.random() * 101);

		var data = "{\"CPU\":[" + "{\"seq\":\"core1\",\"value\":" + vCPU1
				+ ",\"unit\":\"%\"}," + "{\"seq\":\"core2\",\"value\":" + vCPU2
				+ ",\"unit\":\"%\"}]," + "\"MEMORY\":" + "{\"value\":" + vMem
				+ ",\"total\":8,\"unit\":\"G\"}," + "\"DISK\":["
				+ "{\"seq\":\"C\",\"usage\":\"" + vDisk1
				+ "\",\"total\":100,\"unit\":\"G\"},"
				+ "{\"seq\":\"D\",\"usage\":\"" + vDisk2
				+ "\",\"total\":100,\"unit\":\"G\"}]," + "\"NETWORK\":["
				+ "{\"seq\":\"IN\",\"usage\":\"" + vNet1
				+ "\",\"total\":100,\"unit\":\"bps\"},"
				+ "{\"seq\":\"OUT\",\"usage\":\"" + vNet2
				+ "\",\"total\":100,\"unit\":\"bps\"}],"
				+ "\"HA\":{\"STATE\":\"TRUE\"}}";

		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경

		if (jsonObj != "") {
			// CPU (core1:90%)
			if (jsonObj.CPU != '') {
				var vCpu = '<table style=\"border-color:red;\" border=\"0\">';
				$.each(jsonObj.CPU, function(index, obj) {
					// vCpu = vCpu + obj.seq + " : " + obj.value + "<LABEL
					// style=\"font-size: 20px\">"+ obj.unit + "</LABEL></br>";

					vCpu = vCpu + "<tr><td>" + obj.seq + ":"
							+ "</td><td style=\"width:65px;text-align:right\">"
							+ obj.value + "<LABEL style=\"font-size: 20px\">"
							+ obj.unit + "</LABEL></td></tr>"
				});
				vCpu = vCpu + "</table>";
				$('#lCPUValue').html(vCpu);
			}
			// Memory (4G / 8G )
			if (jsonObj.MEMORY != '') {
				// var vMEMORY = jsonObj.MEMORY.value + "<LABEL
				// style=\"font-size: 20px\">"+ jsonObj.MEMORY.unit + "</LABEL>"
				// + " / "
				// + jsonObj.MEMORY.total + "<LABEL style=\"font-size: 20px\">"+
				// jsonObj.MEMORY.unit + "</LABEL>";

				var vMEMORY = '<table style=\"border-color:red;\" border=\"0\">';
				vMEMORY = vMEMORY
						+ "<tr><td style=\"width:40px;text-align:right\">"
						+ jsonObj.MEMORY.value
						+ "<LABEL style=\"font-size: 20px\">"
						+ jsonObj.MEMORY.unit
						+ "</LABEL>"
						+ "</td><td style=\"width:20px;text-align:center\"> / </td>"
						+ "<td style=\"width:40px;\">" + jsonObj.MEMORY.total
						+ "<LABEL style=\"font-size: 20px\">"
						+ jsonObj.MEMORY.unit + "</LABEL>"
						+ "</td></tr></table>";
				$('#lMemoryValue').html(vMEMORY);
			}
			// DISK (C:16G/100G)
			if (jsonObj.DISK != '') {
				var vDisk = '<table style=\"border-color:red;\" border=\"0\">';
				$
						.each(
								jsonObj.DISK,
								function(index, obj) {
									// vDisk = vDisk + obj.seq + ": - " +
									// obj.usage + "<LABEL style=\"font-size:
									// 15px\">"+ obj.unit + "</LABEL>" + " / "
									// + obj.total + "<LABEL style=\"font-size:
									// 15px\">"+ obj.unit + "</LABEL>" +
									// "</br>";

									vDisk = vDisk
											+ "<tr><td>"
											+ obj.seq
											+ ":"
											+ "</td><td style=\"width:60px;text-align:right\">"
											+ obj.usage
											+ "<LABEL style=\"font-size: 15px\">"
											+ obj.unit
											+ "</LABEL>"
											+ "</td><td style=\"width:20px;text-align:center\"> / </td>"
											+ "<td style=\"width:60px;\">"
											+ obj.total
											+ "<LABEL style=\"font-size: 15px\">"
											+ obj.unit + "</LABEL>"
											+ "</td></tr>";
								});
				vDisk = vDisk + "</table>";
				$('#lDiskValue').html(vDisk);
			}
			// Network (IN:16G/100G)
			if (jsonObj.NETWORK != '') {
				// var vNetwork = '';
				// $.each(jsonObj.NETWORK, function(index, obj) {
				// if (obj.seq == "IN") {
				// obj.seq += " ";
				// }
				// vNetwork = vNetwork + obj.seq + " : " + obj.usage + "<LABEL
				// style=\"font-size: 15px\">"+ obj.unit + "</LABEL>" + " / "
				// + obj.total + "<LABEL style=\"font-size: 15px\">"+ obj.unit +
				// "</LABEL>" + "</br>";

				var vNetwork = '<table style=\"border-color:red;\" border=\"0\">';
				$
						.each(
								jsonObj.NETWORK,
								function(index, obj) {
									if (obj.seq == "IN") {
										obj.seq += "　";
									}
									vNetwork = vNetwork
											+ "<tr><td>"
											+ obj.seq
											+ " : "
											+ "</td><td style=\"width:80px;text-align:right\">"
											+ obj.usage
											+ "<LABEL style=\"font-size: 15px\">"
											+ obj.unit
											+ "</LABEL>"
											+ "</td><td style=\"width:20px;text-align:center\"> / </td>"
											+ "<td style=\"width:80px;\">"
											+ obj.total
											+ "<LABEL style=\"font-size: 15px\">"
											+ obj.unit + "</LABEL>"
											+ "</td></tr>";
								});
				vNetwork = vNetwork + "</table>";
				$('#lNetworkValue').html(vNetwork);
			}
			// HA (정상)
			if (jsonObj.HA != '') {
				var vHA = jsonObj.HA.STATE;

				if (vHA.toUpperCase() == "TRUE") {
					$('#lHAValue').text(getLanguage("set"));
				} else {
					$('#lHAValue').text(getLanguage("notset"));
				}
			}
		}
	} catch (e) {
		console.log("dashboard.js systemStatusAjaxCall() Error Log c: "
				+ e.message);
	}
}

// 시스템 현황 Ajax Call Clear 메서드
function clearSystemStatusAjaxCall() {
	clearInterval(m_systemStatusAjaxCall);
}

// Guest IP 할당 현황 Ajax Call 메서드
function guestIPAssignStatusAjaxCall() {

	try {
		// console.log("guestIPAssignStatusAjaxCall count : " + ivalue);
		var vUsed = Math.floor(Math.random() * 1001);

		var data = "{\"GUEST_IP\":{" + "\"used\":" + vUsed
				+ ",\"total\":1000,\"unit\":\"count\"}}";

		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경

		if (jsonObj != "") {
			if (jsonObj.MEMORY != '') {
				var vUsingAbleIP = parseInt(jsonObj.GUEST_IP.total)
						- parseInt(jsonObj.GUEST_IP.used);
				var vGuestIPTotal = getLanguage("total") + " : "
						+ jsonObj.GUEST_IP.total; // 예)Total = 250

				var vGuestIPValue = "<table style=\"border-color:red;\" border=\"0\">";
				vGuestIPValue += "<tr><td style=\"width:80px;text-align:right; vertical-align:text-top;\">"
						+ getLanguage("ipAssignCount") + " :</td>"
				vGuestIPValue += "<td style=\"width:35px;text-align:right; vertical-align:text-bottom;\">"
						+ jsonObj.GUEST_IP.used + ",</td>"
				vGuestIPValue += "<td style=\"width:85px;text-align:right; vertical-align:text-top;\">"
						+ getLanguage("usingAbleIPCount") + " :</td>"
				vGuestIPValue += "<td style=\"width:35px;text-align:right; vertical-align:text-bottom;\">"
						+ vUsingAbleIP + "</td></tr></table>";

				// console.log("$('#iGuestIPAssing').val : " +
				// jsonObj.GUEST_IP.used);
				var percent = (parseInt(jsonObj.GUEST_IP.used) / parseInt(jsonObj.GUEST_IP.total)) * 100;
				$('#iGuestIPAssing').trigger('configure', {
					"min" : 0,
					"max" : jsonObj.GUEST_IP.total,
					"fgColor" : '#00c0ef',
					"inputColor" : '#00c0ef'
				});
				if (percent >= 80) {
					$('#iGuestIPAssing').trigger('configure', {
						"fgColor" : '#ff0000',
						"inputColor" : '#ff0000'
					});
				}
				$('#iGuestIPAssing').val(jsonObj.GUEST_IP.used).trigger('change');

				$('#lGuestIPAssingTotal').text(vGuestIPTotal);
				$('#lGuestIPAssingValue').html(vGuestIPValue);
			}

			clearGuestIPAssignStatusAjaxCall();
			m_guestIPAssignStatusAjaxCall = setInterval(guestIPAssignStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
		}
	} catch (e) {
		console.log("dashboard.js guestIPAssignStatusAjaxCall() Error Log : "
				+ e.message);
	}
}

// Guest IP 할당 현황 Ajax Call Clear 메서드
function clearGuestIPAssignStatusAjaxCall() {
	clearInterval(m_guestIPAssignStatusAjaxCall);
}

// 인증 처리 현황 Ajax Call 메서드
function certifyProcessAjaxCall() {
	try {
		// console.log("certifyProcessAjaxCall");

		var data = tempChartData();
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경

		if (jsonObj != '') {

			if (jsonObj.CERTIFYPRECESS != '') {
				$.each(jsonObj.CERTIFYPRECESS, function(index, obj) {
					// console.log("certifyProcessAjaxCall data : " + obj.time +
					// ", 요청 : " + obj.request + ", 처리 :" + obj.complete);
				});
			};

			/*
			 * RealTime Chart (Flot Interactive Chart) -----------------------
			 */
			// We use an inline data source in the example, usually data would
			// be fetched from a server
			// Get context with jQuery - using jQuery's .get() method.

			$("#certifyProcessChart").html('');
			var certifyProcessChartCanvas = $("#certifyProcessChart").get(0).getContext("2d");
			// This will get the first returned node in the jQuery collection.
			var certifyProcessChart = new Chart(certifyProcessChartCanvas);
			var data1 = [], data2 = [], labels = [], totalPoints = 100;

			function getData() {
				var certifyProcessChartData = {
					labels : getRandomLabels(labels),
					datasets : [ {
						label : "Electronics",
						fillColor : "rgb(210, 214, 222)",
						strokeColor : "rgb(210, 214, 222)",
						pointColor : "rgb(210, 214, 222)",
						pointStrokeColor : "#c1c7d1",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgb(220,220,220)",
						data : getRandomData(data1)
					}, {
						label : "Digital Goods",
						fillColor : "rgba(60,141,188,0.9)",
						strokeColor : "rgba(60,141,188,0.8)",
						pointColor : "#3b8bba",
						pointStrokeColor : "rgba(60,141,188,1)",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(60,141,188,1)",
						data : getRandomData(data2)
					} ]
				};
				return certifyProcessChartData;
			}

			function getRandomLabels(labels) {
				if (labels.length > 0) {
					labels.shift();// shift()는 맨 앞의 원소를 제거합니다.
					// console.log("labels.length : " + labels.length + ",
					// value0 : " + labels[0]+ ", value : " +
					// (parseInt(labels[labels.length-1]) + 1));
					labels.push(parseInt(labels[labels.length - 1]) + 1);
				} else {
					// Zip the generated y values with the x values
					for (var i = 0; i < totalPoints; ++i) {
						labels.push(i);
					}
				}
				return labels;
			}
			function getRandomData(data) {
				if (data.length > 0) {
					data.shift();// shift()는 맨 앞의 원소를 제거합니다.
				}
				// Do a random walk
				while (data.length < totalPoints) {
					var prev = data.length > 0 ? data[data.length - 1] : 50, y = prev
							+ Math.random() * 10 - 5;

					if (y < 0) {
						y = 0;
					} else if (y > 100) {
						y = 100;
					}

					data.push(y);
				}
				return data;
			}

			var updateInterval = 1000; // Fetch data ever x milliseconds
			var realtime = "on"; // If == to on then fetch data every x
									// seconds. else stop fetching
			function update() {

				// Since the axes don't change, we don't need to call
				// plot.setupGrid()
				//console.log("getdata : " + data1.length);
				certifyProcessChart.Line(getData(), chartOption());
				if (realtime === "on")
					setTimeout(update, updateInterval);
			}

			// INITIALIZE REALTIME DATA FETCHING
			if (realtime === "on") {
				update();
			}
			// REALTIME TOGGLE
			$("#realtime .btn").click(function() {
				if ($(this).data("toggle") === "on") {
					realtime = "on";
				} else {
					realtime = "off";
				}
				update();
			});
			/*
			 * END INTERACTIVE CHART
			 */

			// Create the line chart
			certifyProcessChart.Line(getData(), chartOption());

			// clearCertifyProcessAjaxCall();
			// m_certifyProcessAjaxCall = setInterval(certifyProcessAjaxCall,
			// 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
		}
	} catch (e) {
		console.log("dashboard.js certifyProcessAjaxCall() Error Log : "
				+ e.message);
	}
}

// 인증 처리 현황 Ajax Call Clear 메서드
function clearCertifyProcessAjaxCall() {
	clearInterval(m_certifyProcessAjaxCall);
}

// DNS 현황 Ajax Call 메서드
function dnsStatusAjaxCall() {

	try {
		var vSucess = Math.floor(Math.random() * 9999);
		var vReferr = Math.floor(Math.random() * 9999);
		var vNXRRSe = Math.floor(Math.random() * 9999);
		var vNXDoma = Math.floor(Math.random() * 9999);
		var vRecurs = Math.floor(Math.random() * 9999);
		var vFailur = Math.floor(Math.random() * 9999);

		var data = "{\"DNSSTATUS\": [{\"class\": \"Sucess\",\"value\": "+vSucess+"},{"+
								     "\"class\": \"Referral\",\"value\": "+vReferr+"},{"+
								     "\"class\": \"NXRRSet\",\"value\": "+vNXRRSe+"},{"+
								     "\"class\": \"NXDomain\",\"value\": "+vNXDoma+"},{"+
								     "\"class\": \"Recursion\",\"value\": "+vRecurs+"},{"+
								     "\"class\": \"Failure\",\"value\": "+vFailur+"}]}";
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {

			if (jsonObj.DNSSTATUS != '') {
				$.each(jsonObj.DNSSTATUS, function(index, obj) {
					if (obj.class == "Sucess") {
						$('#lSuccess').text(obj.value);
					}
					else if (obj.class == "Referral") {
						$('#lReferral').text(obj.value);
					}
					else if (obj.class == "NXRRSet") {
						$('#lNXRRSet').text(obj.value);
					}
					else if (obj.class == "NXDomain") {
						$('#lNXDomain').text(obj.value);
					}
					else if (obj.class == "Recursion") {
						$('#lRecursion').text(obj.value);
					}
					else if (obj.class == "Failure") {
						$('#lFailure').text(obj.value);
					}
				});
			};
		};

		cleardnsStatusAjaxCall();
		m_dnsStatusAjaxCall = setInterval(dnsStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js dnsStatusAjaxCall() Error Log : " + e.message);
	}
}

// DNS 현황 Ajax Call Clear 메서드
function cleardnsStatusAjaxCall() {
	clearInterval(m_dnsStatusAjaxCall);
}

// 세그먼트별 lease IP 할당 현황  Ajax Call 메서드
function segmentLeasingIPAssignedAjaxCall() {

	try {
		var vCritical = "progress-bar-red";
		var vWarnning = "progress-bar-warning";
		var vNormal = "progress-bar-aqua";
		
		var vTop1 = Math.floor(Math.random() * 101);
		var vTop2 = Math.floor(Math.random() * 101);
		var vTop3 = Math.floor(Math.random() * 101);
		var vTop4 = Math.floor(Math.random() * 101);
		var vTop5 = Math.floor(Math.random() * 101);

		var data = "{\"LeaseIPAvailable\": [{\"segment\": \"10.10.10.0/24\",\"value\": "+vTop1+"},{"+
								     		"\"segment\": \"192.168.1.0/24\",\"value\": "+vTop2+"},{"+
								     		"\"segment\": \"100.100.100.100/255\",\"value\": "+vTop3+"},{"+
								     		"\"segment\": \"12.12.15.0/24\",\"value\": "+vTop4+"},{"+
								     		"\"segment\": \"25.25.1.0/24\",\"value\": "+vTop5+"}]}";
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {
			if (jsonObj.LeaseIPAvailable != '') {
				var vhtml = "<div class=\"progress-group\"><table class=\"col-xs-12\" border=0 style=\"height:135px\">";
				$.each(jsonObj.LeaseIPAvailable, function(index, obj) {
					var vSeverity = vNormal;
					if (obj.value >= 80) {
						vSeverity = vCritical;
					}
					else if (obj.value < 80 && obj.value >= 60) {
						vSeverity = vWarnning;
					}
					
					vhtml = vhtml + "<tr><td style=\"width:42%; text-align:left\"><span class=\"progress-text\">" + obj.segment + "</span></td>" +
									"<td style=\"width:43%\">" +
									"	<div class=\"progress sm\">" +
									"		<div class=\"progress-bar " + vSeverity + " \" style=\"width: " + obj.value + "%;\"></div>" +
									"	</div>"+
									"</td>" +
									"<td style=\"width:15%\"><span class=\"progress-number\"><b>" + obj.value + " %</b></span></td></tr>";					
				});
			};			
			html = vhtml + "</table></div>";
			$('#divleaseIPAvailable').html(vhtml);
		};

		clearsegmentLeasingIPAssignedAjaxCall();
		m_segmentLeasingIPAssignedAjaxCall = setInterval(segmentLeasingIPAssignedAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js segmentLeasingIPAssignedAjaxCall() Error Log : " + e.message);
	}
}

// 세그먼트별 lease IP 할당 현황  Ajax Call Clear 메서드
function clearsegmentLeasingIPAssignedAjaxCall() {
	clearInterval(m_segmentLeasingIPAssignedAjaxCall);
}

// 고정, 리스, 미사용 IP 할당 현황  Ajax Call 메서드
function assignmentIPStatusAjaxCall() {

	try {		
		var vTop1 = Math.floor(Math.random() * 1001);
		var vTop2 = Math.floor(Math.random() * 1001);
		var vTop3 = Math.floor(Math.random() * 1001);

		var data = "{\"ASSIGNMENTIPSTATUS\": {" +
						"\"total\": 1000," +
						"\"staticip\": "+vTop1+"," +
						"\"leaseip\": "+vTop2+"," +
						"\"unusedip\": "+vTop3+"}}";
		
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {
			if (jsonObj.ASSIGNMENTIPSTATUS != '') {
				var vhtml;				
				var vformat = "<span class=\"progress-description\">{0}</span>" +
								"<div class=\"progress\">" +
								"<div class=\"{1}\" style=\"width: {2}%\"></div></div>";
				
				var vTotalValue, vStaticipValue, vLeaseipValue, vUnusedipValue = 0;

				if (jsonObj.ASSIGNMENTIPSTATUS.total != '') {
					vTotalValue = parseInt(jsonObj.ASSIGNMENTIPSTATUS.total);
				}
				
//				if (vTotalValue > 0) {						
//					if (jsonObj.ASSIGNMENTIPSTATUS.staticip != '') {
//						vStaticipValue = (parseInt(jsonObj.ASSIGNMENTIPSTATUS.staticip) * 100) / vTotalValue;
//						vhtml = String.format(vformat, getLanguage("staticIP"), getProgressSeverity(vStaticipValue), vStaticipValue);
//						console.log("divStaticIP.html : " + vhtml + ", 토탈 : " + parseInt(jsonObj.ASSIGNMENTIPSTATUS.staticip) + ", 페센트 : " + parseInt(vStaticipValue));
//						$('#divStaticIP').html(vhtml);
//					}
//					if (jsonObj.ASSIGNMENTIPSTATUS.leaseip != '') {
//						vLeaseipValue = (parseInt(jsonObj.ASSIGNMENTIPSTATUS.leaseip) * 100) / vTotalValue;
//						vhtml = String.format(vformat, getLanguage("leaseIP"), getProgressSeverity(vLeaseipValue), vLeaseipValue);
//						console.log("divLeaseIP.html : " + vhtml + ", 토탈 : " + parseInt(jsonObj.ASSIGNMENTIPSTATUS.leaseip) + ", 페센트 : " + parseInt(vLeaseipValue));
//						$('#divLeaseIP').html(vhtml);
//					}
//					if (jsonObj.ASSIGNMENTIPSTATUS.unusedip != '') {
//						vUnusedipValue = (parseInt(jsonObj.ASSIGNMENTIPSTATUS.unusedip) * 100) / vTotalValue;
//						vhtml = String.format(vformat, getLanguage("unUsedIP"), getProgressSeverity(vUnusedipValue), vUnusedipValue);
//						console.log("divUnusedIP.html : " + vhtml + ", 토탈 : " + parseInt(jsonObj.ASSIGNMENTIPSTATUS.unusedip) + ", 페센트 : " + parseInt(vUnusedipValue));
//						$('#divUnusedIP').html(vhtml);
//					}				
//				}
			};
		};


		function getProgressSeverity(value) {
			var vRet = "progress-bar";
			if (value >= 80) {
				vRet = "progress-bar progress-bar-red";
			}
			else if (60 <= value && value < 80) {
				vRet = "progress-bar progress-bar-warning";
			}		
			return vRet;
		}
		
		clearassignmentIPStatus();
		m_assignmentIPStatusAjaxCall = setInterval(assignmentIPStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js assignmentIPStatusAjaxCall() Error Log : " + e.message);
	}
}

// 고정, 리스, 미사용 IP 할당 현황  Ajax Call Clear 메서드
function clearassignmentIPStatus() {
	clearInterval(m_assignmentIPStatusAjaxCall);
}










// HW별 사용 현황  Ajax Call 메서드
function hwUsedStatusAjaxCall() {

	try {		
		var vTop1 = Math.floor(Math.random() * 100) + 1;
		var vTop2 = Math.floor(Math.random() * 100) + 1;
		var vTop3 = Math.floor(Math.random() * 100) + 1;
		var vTop4 = Math.floor(Math.random() * 100) + 1;
		var vTop5 = Math.floor(Math.random() * 100) + 1;

		var data = "{\"USEDSTATUS\": [{\"label\": \"Router\",\"data\": "+vTop1+"},{"+
								      "\"label\": \"Swich\",\"data\": "+vTop2+"},{"+
								      "\"label\": \"Dhub\",\"data\": "+vTop3+"},{"+
								      "\"label\": \"AP\",\"data\": "+vTop4+"},{"+
								      "\"label\": \"Server\",\"data\": "+vTop5+"}]}";
		
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {
			if (jsonObj.USEDSTATUS != '') {
		
				$.plot("#hwPieChart", jsonObj.USEDSTATUS, {
					series: {
							pie: {
								show: true,
								radius: 1,
//								innerRadius: 0.5,
								label: {
									show: true,
									radius: 2 / 3,
									formatter: labelFormatter,
									threshold: 0.1
								}
							}
					},
					legend: {
						show: true
					},
				    grid: {
				        hoverable: true,
				        clickable: true
				    }
				});
			};
		};

		clearHWUsedStatusAjaxCall();
		m_hwUsedStatusAjaxCall = setInterval(hwUsedStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js hwUsedStatusAjaxCall() Error Log : " + e.message);
	}
}

// HW별 사용  현황  Ajax Call Clear 메서드
function clearHWUsedStatusAjaxCall() {
	clearInterval(m_hwUsedStatusAjaxCall);
}

//OS별 사용 현황  Ajax Call 메서드
function osUsedStatusAjaxCall() {

	try {		
		var vTop1 = Math.floor(Math.random() * 100) + 1;
		var vTop2 = Math.floor(Math.random() * 100) + 1;
		var vTop3 = Math.floor(Math.random() * 100) + 1;
		var vTop4 = Math.floor(Math.random() * 100) + 1;
		var vTop5 = Math.floor(Math.random() * 100) + 1;

		var data = "{\"USEDSTATUS\": [{\"label\": \"Windows\",\"data\": "+vTop1+"},{"+
								      "\"label\": \"Server 2012\",\"data\": "+vTop2+"},{"+
								      "\"label\": \"Linux\",\"data\": "+vTop3+"},{"+
								      "\"label\": \"IOS\",\"data\": "+vTop4+"},{"+
								      "\"label\": \"ETC\",\"data\": "+vTop5+"}]}";
		
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {
			if (jsonObj.USEDSTATUS != '') {
		
				$.plot("#osPieChart", jsonObj.USEDSTATUS, {
					series: {
							pie: {
								show: true,
								radius: 1,
	//							innerRadius: 0.5,
								label: {
									show: true,
									radius: 2 / 3,
									formatter: labelFormatter,
									threshold: 0.1
								}
							}
					},
					legend: {
						show: true
					},
				    grid: {
				        hoverable: true,
				        clickable: true
				    }
				});
			};
		};

		clearOSUsedStatusAjaxCall();
		m_osUsedStatusAjaxCall = setInterval(osUsedStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js osUsedStatusAjaxCall() Error Log : " + e.message);
	}
}

//OS별 사용  현황  Ajax Call Clear 메서드
function clearOSUsedStatusAjaxCall() {
	clearInterval(m_osUsedStatusAjaxCall);
}

//Service별 사용 현황  Ajax Call 메서드
function serviceUsedStatusAjaxCall() {

	try {		
		var vTop1 = Math.floor(Math.random() * 100) + 1;
		var vTop2 = Math.floor(Math.random() * 100) + 1;
		var vTop3 = Math.floor(Math.random() * 100) + 1;
		var vTop4 = Math.floor(Math.random() * 100) + 1;
		var vTop5 = Math.floor(Math.random() * 100) + 1;

		var data = "{\"USEDSTATUS\": [{\"label\": \"Mobile\",\"data\": "+vTop1+"},{"+
								      "\"label\": \"Desktop\",\"data\": "+vTop2+"},{"+
								      "\"label\": \"Server\",\"data\": "+vTop3+"},{"+
								      "\"label\": \"Network\",\"data\": "+vTop4+"},{"+
								      "\"label\": \"ETC\",\"data\": "+vTop5+"}]}";
		
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {
			if (jsonObj.USEDSTATUS != '') {
		
				$.plot("#servicePieChart", jsonObj.USEDSTATUS, {
						series: {
							pie: {
								show: true,
								radius: 1,
	//							innerRadius: 0.5,
								label: {
									show: true,
									radius: 2 / 3,
									formatter: labelFormatter,
									threshold: 0.1
								}
							}
					},
					legend: {
						show: true
					},
				    grid: {
				        hoverable: true,
				        clickable: true
				    }
				});
			};
		};

		clearServiceUsedStatusAjaxCall();
		m_serviceUsedStatusAjaxCall = setInterval(serviceUsedStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js serviceUsedStatusAjaxCall() Error Log : " + e.message);
	}
}

//Service별 사용  현황  Ajax Call Clear 메서드
function clearServiceUsedStatusAjaxCall() {
	clearInterval(m_serviceUsedStatusAjaxCall);
}

//Vendor별 사용 현황  Ajax Call 메서드
function vendorUsedStatusAjaxCall() {

	try {		
		var vTop1 = Math.floor(Math.random() * 100) + 1;
		var vTop2 = Math.floor(Math.random() * 100) + 1;
		var vTop3 = Math.floor(Math.random() * 100) + 1;
		var vTop4 = Math.floor(Math.random() * 100) + 1;
		var vTop5 = Math.floor(Math.random() * 100) + 1;

		var data = "{\"USEDSTATUS\": [{\"label\": \"Samsung\",\"data\": "+vTop1+"},{"+
								      "\"label\": \"Cisco\",\"data\": "+vTop2+"},{"+
								      "\"label\": \"Apple\",\"data\": "+vTop3+"},{"+
								      "\"label\": \"HP\",\"data\": "+vTop4+"},{"+
								      "\"label\": \"Juniper\",\"data\": "+vTop5+"}]}";
		
		//console.log(data);
		var jsonObj = eval("(" + data + ')'); // JSonString 형식의 데이터를
		// Ojbect형식으로 변경
		if (jsonObj != '') {
			if (jsonObj.USEDSTATUS != '') {
		
				$.plot("#vendorPieChart", jsonObj.USEDSTATUS, {
						series: {
							pie: {
								show: true,
								radius: 1,
	//							innerRadius: 0.5,
								label: {
									show: true,
									radius: 2 / 3,
									formatter: labelFormatter,
									threshold: 0.1
								}
							}
					},
					legend: {
						show: true
					},
				    grid: {
				        hoverable: true,
				        clickable: true
				    }
				});
			};
		};

		clearVendorUsedStatusAjaxCall();
		m_vendorUsedStatusAjaxCall = setInterval(vendorUsedStatusAjaxCall, 5000);// 페이지 로딩 데이터 조회 후 polling 시간 변경
	} catch (e) {
		console.log("dashboard.js vendorUsedStatusAjaxCall() Error Log : " + e.message);
	}
}

//Vendor별 사용  현황  Ajax Call Clear 메서드
function clearVendorUsedStatusAjaxCall() {
	clearInterval(m_vendorUsedStatusAjaxCall);
}




//Flot Pie 차트  Label Format
function labelFormatter(label, series) {
	return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
}

// Ajax Call All Clear 메서드
function AllClearAjaxCall() {
	clearInterval(m_systemStatusAjaxCall);
	clearInterval(m_guestIPAssignStatusAjaxCall);
	clearInterval(m_certifyProcessAjaxCall);
	clearInterval(m_dnsStatusAjaxCall);
	clearInterval(m_segmentLeasingIPAssignedAjaxCall);
	clearInterval(m_hwUsedStatusAjaxCall);
	clearInterval(m_osUsedStatusAjaxCall);
	clearInterval(m_serviceUsedStatusAjaxCall);
	clearInterval(m_vendorUsedStatusAjaxCall);
}

// 인증 처리 현황 차트 옵션
function chartOption() {
	var chartOptions = {
		animation : false,
		// Boolean - If we should show the scale at all
		showScale : true,

		scaleOverride : true,
		scaleSteps : 2,
		scaleStepWidth : 50,
		scaleStartValue : 0,

		// 안됨
		// scales: {
		// xAxes: [{
		// position: "top",
		// ticks: {
		// autoSkip: false,
		// }
		// }]
		// },

		// Y축 Label 출력 여부
		scaleShowLabels : true,
		// Boolean - 차트 눈금 표시 여부 설정
		scaleShowGridLines : false,
		// String - 차트 눈금 색 설정
		scaleGridLineColor : "rgba(0,0,0,.05)",
		// Number - 차트 눈금 선 굵기 설정
		scaleGridLineWidth : 1,
		// Boolean - Whether to show horizontal lines (except X axis)
		scaleShowHorizontalLines : true,
		// Boolean - Whether to show vertical lines (except Y axis)
		scaleShowVerticalLines : true,
		// Boolean - 차트 선이 꺽이는 부분을 곡선 처리 여부 설정
		bezierCurve : true,
		// Number - 차트 선의 곡선의 정도 설정
		bezierCurveTension : 0.3,
		// Boolean - 차트 선의 데이터 부분 점 표시 여부 설정
		pointDot : false,
		// Number - 차트 선의 데이터 부분 점 크기 설정
		pointDotRadius : 4,
		// Number - Pixel width of point dot stroke
		pointDotStrokeWidth : 1,
		// Number - amount extra to add to the radius to cater for hit
		// Number - 차트 선의 데이터 부분 팝업 노출 범위 설정
		pointHitDetectionRadius : 20,
		// Boolean - Whether to show a stroke for datasets
		datasetStroke : true,
		// Number - 차트 선의 데이터 부분 점 테두리 너비 설정
		datasetStrokeWidth : 2,
		// Boolean - 차트에서 선만 노출 여부 설정
		datasetFill : true,
		// String - A legend template
		legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%=datasets[i].label%></li><%}%></ul>",
		// Boolean - whether to maintain the starting aspect ratio or
		// not when responsive, if set to false, will take up entire
		// container
		maintainAspectRatio : true,
		// Boolean - whether to make the chart responsive to window
		// resizing
		responsive : true,

		skipXLabels : 50
	// DATE SCALE

	// // String - scale type: "number" or "date"
	// scaleType: "number",
	// // Boolean - Whether to use UTC dates instead local
	// useUtc: true,
	// // String - short date format (used for scale labels)
	// scaleDateFormat: "mmm d",
	// // String - short time format (used for scale labels)
	// scaleTimeFormat: "h:MM",
	// // String - full date format (used for point labels)
	// scaleDateTimeFormat: "mmm d, yyyy, hh:MM",
	};
	return chartOptions;
}

//Pie Chart Tooltip Bind
function fnPieChartTooltipBind(chart){
	function showTooltip(x, y, contents) { 
	    $('<div id="tooltip">' + contents + '</div>').css( { 
	        position: 'absolute', 
	        display: 'none', 
	        top: y + 10, 
	        left: x + 10, 
	        border: '1px solid #fdd', 
	        padding: '2px', 
	        'background-color': '#f2f2f2', 
	        color: '#000000', 
	        opacity: 0.9 
	    }).appendTo("body").fadeIn(200); 
	}
	
	var previousPoint = null; 
	chart.bind("plothover", function (event, pos, item) { 
	    $("#x").text(pos.pageX); 
	    $("#y").text(pos.pageY);
	        if (item) { 

	//          alert(typeof item.series.label === 'string');
	//        	alert(item.series.label + ", " +item.series.data);
	        	if (previousPoint != item.datapoint) { 
	                previousPoint = item.datapoint; 
	                $("#tooltip").remove(); 
	//                showTooltip(pos.pageX, pos.pageY, "Hover @" + pos.pageX + " , " + pos.pageY); 
	                showTooltip(pos.pageX, pos.pageY, item.series.label + " : " + item.series.data); 
	            } 
	        } 
	        else { 
	            $("#tooltip").remove(); 
	            previousPoint = null; 
	        } 
	}); 
	
	chart.bind("plotclick", function (event, pos, item) { 
	    if (item) { 
	        $("#clickdata").text("You clicked point " + item.dataIndex + " in " + item.series.label + "."); 
	        //plot.highlight(item.series, item.datapoint); 
	
	    } 
	}); 
}
// 차트 임시데이터 생성 메서드
function tempChartData() {
	var data = "{\"CERTIFYPRECESS\": ["
			+ "{\"time\": \"2016-06-30 01:00:00\",\"request\": 126,\"complete\": 602},"
			+ "{\"time\": \"2016-06-30 01:01:00\",\"request\": 222,\"complete\": 483},"
			+ "{\"time\": \"2016-06-30 01:02:00\",\"request\": 27,\"complete\": 725},"
			+ "{\"time\": \"2016-06-30 01:03:00\",\"request\": 427,\"complete\": 423},"
			+ "{\"time\": \"2016-06-30 01:04:00\",\"request\": 791,\"complete\": 22},"
			+ "{\"time\": \"2016-06-30 01:05:00\",\"request\": 290,\"complete\": 563},"
			+ "{\"time\": \"2016-06-30 01:06:00\",\"request\": 408,\"complete\": 479},"
			+ "{\"time\": \"2016-06-30 01:07:00\",\"request\": 27,\"complete\": 480},"
			+ "{\"time\": \"2016-06-30 01:08:00\",\"request\": 122,\"complete\": 342},"
			+ "{\"time\": \"2016-06-30 01:09:00\",\"request\": 833,\"complete\": 71},"
			+ "{\"time\": \"2016-06-30 01:10:00\",\"request\": 522,\"complete\": 308},"
			+ "{\"time\": \"2016-06-30 01:11:00\",\"request\": 391,\"complete\": 555},"
			+ "{\"time\": \"2016-06-30 01:12:00\",\"request\": 524,\"complete\": 343},"
			+ "{\"time\": \"2016-06-30 01:13:00\",\"request\": 697,\"complete\": 8},"
			+ "{\"time\": \"2016-06-30 01:14:00\",\"request\": 820,\"complete\": 170},"
			+ "{\"time\": \"2016-06-30 01:15:00\",\"request\": 568,\"complete\": 72},"
			+ "{\"time\": \"2016-06-30 01:16:00\",\"request\": 550,\"complete\": 270},"
			+ "{\"time\": \"2016-06-30 01:17:00\",\"request\": 893,\"complete\": 38},"
			+ "{\"time\": \"2016-06-30 01:18:00\",\"request\": 211,\"complete\": 4},"
			+ "{\"time\": \"2016-06-30 01:19:00\",\"request\": 803,\"complete\": 133},"
			+ "{\"time\": \"2016-06-30 01:20:00\",\"request\": 12,\"complete\": 119},"
			+ "{\"time\": \"2016-06-30 01:21:00\",\"request\": 751,\"complete\": 109},"
			+ "{\"time\": \"2016-06-30 01:22:00\",\"request\": 251,\"complete\": 154},"
			+ "{\"time\": \"2016-06-30 01:23:00\",\"request\": 768,\"complete\": 195},"
			+ "{\"time\": \"2016-06-30 01:24:00\",\"request\": 110,\"complete\": 591},"
			+ "{\"time\": \"2016-06-30 01:25:00\",\"request\": 952,\"complete\": 16},"
			+ "{\"time\": \"2016-06-30 01:26:00\",\"request\": 344,\"complete\": 571},"
			+ "{\"time\": \"2016-06-30 01:27:00\",\"request\": 374,\"complete\": 447},"
			+ "{\"time\": \"2016-06-30 01:28:00\",\"request\": 102,\"complete\": 359},"
			+ "{\"time\": \"2016-06-30 01:29:00\",\"request\": 656,\"complete\": 280},"
			+ "{\"time\": \"2016-06-30 01:30:00\",\"request\": 805,\"complete\": 30},"
			+ "{\"time\": \"2016-06-30 01:31:00\",\"request\": 973,\"complete\": 21},"
			+ "{\"time\": \"2016-06-30 01:32:00\",\"request\": 994,\"complete\": 6},"
			+ "{\"time\": \"2016-06-30 01:33:00\",\"request\": 198,\"complete\": 690},"
			+ "{\"time\": \"2016-06-30 01:34:00\",\"request\": 940,\"complete\": 26},"
			+ "{\"time\": \"2016-06-30 01:35:00\",\"request\": 773,\"complete\": 118},"
			+ "{\"time\": \"2016-06-30 01:36:00\",\"request\": 213,\"complete\": 271},"
			+ "{\"time\": \"2016-06-30 01:37:00\",\"request\": 447,\"complete\": 301},"
			+ "{\"time\": \"2016-06-30 01:38:00\",\"request\": 795,\"complete\": 38},"
			+ "{\"time\": \"2016-06-30 01:39:00\",\"request\": 399,\"complete\": 87},"
			+ "{\"time\": \"2016-06-30 01:40:00\",\"request\": 221,\"complete\": 409},"
			+ "{\"time\": \"2016-06-30 01:41:00\",\"request\": 910,\"complete\": 6},"
			+ "{\"time\": \"2016-06-30 01:42:00\",\"request\": 762,\"complete\": 100},"
			+ "{\"time\": \"2016-06-30 01:43:00\",\"request\": 361,\"complete\": 14},"
			+ "{\"time\": \"2016-06-30 01:44:00\",\"request\": 961,\"complete\": 5},"
			+ "{\"time\": \"2016-06-30 01:45:00\",\"request\": 58,\"complete\": 603},"
			+ "{\"time\": \"2016-06-30 01:46:00\",\"request\": 494,\"complete\": 395},"
			+ "{\"time\": \"2016-06-30 01:47:00\",\"request\": 827,\"complete\": 168},"
			+ "{\"time\": \"2016-06-30 01:48:00\",\"request\": 765,\"complete\": 134},"
			+ "{\"time\": \"2016-06-30 01:49:00\",\"request\": 49,\"complete\": 690},"
			+ "{\"time\": \"2016-06-30 01:50:00\",\"request\": 937,\"complete\": 30},"
			+ "{\"time\": \"2016-06-30 01:51:00\",\"request\": 111,\"complete\": 89},"
			+ "{\"time\": \"2016-06-30 01:52:00\",\"request\": 15,\"complete\": 852},"
			+ "{\"time\": \"2016-06-30 01:53:00\",\"request\": 487,\"complete\": 319},"
			+ "{\"time\": \"2016-06-30 01:54:00\",\"request\": 445,\"complete\": 290},"
			+ "{\"time\": \"2016-06-30 01:55:00\",\"request\": 931,\"complete\": 53},"
			+ "{\"time\": \"2016-06-30 01:56:00\",\"request\": 9,\"complete\": 13},"
			+ "{\"time\": \"2016-06-30 01:57:00\",\"request\": 455,\"complete\": 272},"
			+ "{\"time\": \"2016-06-30 01:58:00\",\"request\": 372,\"complete\": 142},"
			+ "{\"time\": \"2016-06-30 01:59:00\",\"request\": 648,\"complete\": 182},"
			+ "{\"time\": \"2016-06-30 02:00:00\",\"request\": 473,\"complete\": 228},"
			+ "{\"time\": \"2016-06-30 02:01:00\",\"request\": 265,\"complete\": 95},"
			+ "{\"time\": \"2016-06-30 02:02:00\",\"request\": 656,\"complete\": 263},"
			+ "{\"time\": \"2016-06-30 02:03:00\",\"request\": 682,\"complete\": 305},"
			+ "{\"time\": \"2016-06-30 02:04:00\",\"request\": 408,\"complete\": 239},"
			+ "{\"time\": \"2016-06-30 02:05:00\",\"request\": 9,\"complete\": 425},"
			+ "{\"time\": \"2016-06-30 02:06:00\",\"request\": 827,\"complete\": 46},"
			+ "{\"time\": \"2016-06-30 02:07:00\",\"request\": 492,\"complete\": 457},"
			+ "{\"time\": \"2016-06-30 02:08:00\",\"request\": 251,\"complete\": 494},"
			+ "{\"time\": \"2016-06-30 02:09:00\",\"request\": 996,\"complete\": 2},"
			+ "{\"time\": \"2016-06-30 02:10:00\",\"request\": 206,\"complete\": 340},"
			+ "{\"time\": \"2016-06-30 02:11:00\",\"request\": 920,\"complete\": 55},"
			+ "{\"time\": \"2016-06-30 02:12:00\",\"request\": 228,\"complete\": 250},"
			+ "{\"time\": \"2016-06-30 02:13:00\",\"request\": 429,\"complete\": 157},"
			+ "{\"time\": \"2016-06-30 02:14:00\",\"request\": 589,\"complete\": 395},"
			+ "{\"time\": \"2016-06-30 02:15:00\",\"request\": 52,\"complete\": 152},"
			+ "{\"time\": \"2016-06-30 02:16:00\",\"request\": 450,\"complete\": 137},"
			+ "{\"time\": \"2016-06-30 02:17:00\",\"request\": 324,\"complete\": 237},"
			+ "{\"time\": \"2016-06-30 02:18:00\",\"request\": 905,\"complete\": 82},"
			+ "{\"time\": \"2016-06-30 02:19:00\",\"request\": 441,\"complete\": 376},"
			+ "{\"time\": \"2016-06-30 02:20:00\",\"request\": 509,\"complete\": 122},"
			+ "{\"time\": \"2016-06-30 02:21:00\",\"request\": 888,\"complete\": 29},"
			+ "{\"time\": \"2016-06-30 02:22:00\",\"request\": 763,\"complete\": 201},"
			+ "{\"time\": \"2016-06-30 02:23:00\",\"request\": 783,\"complete\": 176},"
			+ "{\"time\": \"2016-06-30 02:24:00\",\"request\": 833,\"complete\": 40},"
			+ "{\"time\": \"2016-06-30 02:25:00\",\"request\": 364,\"complete\": 633},"
			+ "{\"time\": \"2016-06-30 02:26:00\",\"request\": 83,\"complete\": 849},"
			+ "{\"time\": \"2016-06-30 02:27:00\",\"request\": 777,\"complete\": 25},"
			+ "{\"time\": \"2016-06-30 02:28:00\",\"request\": 880,\"complete\": 77},"
			+ "{\"time\": \"2016-06-30 02:29:00\",\"request\": 950,\"complete\": 40},"
			+ "{\"time\": \"2016-06-30 02:30:00\",\"request\": 151,\"complete\": 55},"
			+ "{\"time\": \"2016-06-30 02:31:00\",\"request\": 117,\"complete\": 255},"
			+ "{\"time\": \"2016-06-30 02:32:00\",\"request\": 813,\"complete\": 161},"
			+ "{\"time\": \"2016-06-30 02:33:00\",\"request\": 123,\"complete\": 666},"
			+ "{\"time\": \"2016-06-30 02:34:00\",\"request\": 659,\"complete\": 194},"
			+ "{\"time\": \"2016-06-30 02:35:00\",\"request\": 644,\"complete\": 99},"
			+ "{\"time\": \"2016-06-30 02:36:00\",\"request\": 134,\"complete\": 21},"
			+ "{\"time\": \"2016-06-30 02:37:00\",\"request\": 543,\"complete\": 11},"
			+ "{\"time\": \"2016-06-30 02:38:00\",\"request\": 625,\"complete\": 158},"
			+ "{\"time\": \"2016-06-30 02:39:00\",\"request\": 703,\"complete\": 39},"
			+ "{\"time\": \"2016-06-30 02:40:00\",\"request\": 242,\"complete\": 741},"
			+ "{\"time\": \"2016-06-30 02:41:00\",\"request\": 653,\"complete\": 143},"
			+ "{\"time\": \"2016-06-30 02:42:00\",\"request\": 657,\"complete\": 35},"
			+ "{\"time\": \"2016-06-30 02:43:00\",\"request\": 979,\"complete\": 6},"
			+ "{\"time\": \"2016-06-30 02:44:00\",\"request\": 365,\"complete\": 411},"
			+ "{\"time\": \"2016-06-30 02:45:00\",\"request\": 819,\"complete\": 90},"
			+ "{\"time\": \"2016-06-30 02:46:00\",\"request\": 459,\"complete\": 249},"
			+ "{\"time\": \"2016-06-30 02:47:00\",\"request\": 859,\"complete\": 31},"
			+ "{\"time\": \"2016-06-30 02:48:00\",\"request\": 127,\"complete\": 551},"
			+ "{\"time\": \"2016-06-30 02:49:00\",\"request\": 546,\"complete\": 405},"
			+ "{\"time\": \"2016-06-30 02:50:00\",\"request\": 346,\"complete\": 232},"
			+ "{\"time\": \"2016-06-30 02:51:00\",\"request\": 965,\"complete\": 29},"
			+ "{\"time\": \"2016-06-30 02:52:00\",\"request\": 394,\"complete\": 262},"
			+ "{\"time\": \"2016-06-30 02:53:00\",\"request\": 188,\"complete\": 57},"
			+ "{\"time\": \"2016-06-30 02:54:00\",\"request\": 984,\"complete\": 15},"
			+ "{\"time\": \"2016-06-30 02:55:00\",\"request\": 46,\"complete\": 76},"
			+ "{\"time\": \"2016-06-30 02:56:00\",\"request\": 67,\"complete\": 931},"
			+ "{\"time\": \"2016-06-30 02:57:00\",\"request\": 584,\"complete\": 313},"
			+ "{\"time\": \"2016-06-30 02:58:00\",\"request\": 843,\"complete\": 126},"
			+ "{\"time\": \"2016-06-30 02:59:00\",\"request\": 814,\"complete\": 134},"
			+ "{\"time\": \"2016-06-30 03:00:00\",\"request\": 668,\"complete\": 308},"
			+ "{\"time\": \"2016-06-30 03:01:00\",\"request\": 532,\"complete\": 60},"
			+ "{\"time\": \"2016-06-30 03:02:00\",\"request\": 592,\"complete\": 401},"
			+ "{\"time\": \"2016-06-30 03:03:00\",\"request\": 1,\"complete\": 886},"
			+ "{\"time\": \"2016-06-30 03:04:00\",\"request\": 765,\"complete\": 134},"
			+ "{\"time\": \"2016-06-30 03:05:00\",\"request\": 945,\"complete\": 47},"
			+ "{\"time\": \"2016-06-30 03:06:00\",\"request\": 814,\"complete\": 37},"
			+ "{\"time\": \"2016-06-30 03:07:00\",\"request\": 706,\"complete\": 278},"
			+ "{\"time\": \"2016-06-30 03:08:00\",\"request\": 895,\"complete\": 36},"
			+ "{\"time\": \"2016-06-30 03:09:00\",\"request\": 658,\"complete\": 317},"
			+ "{\"time\": \"2016-06-30 03:10:00\",\"request\": 922,\"complete\": 21},"
			+ "{\"time\": \"2016-06-30 03:11:00\",\"request\": 358,\"complete\": 271},"
			+ "{\"time\": \"2016-06-30 03:12:00\",\"request\": 763,\"complete\": 182},"
			+ "{\"time\": \"2016-06-30 03:13:00\",\"request\": 271,\"complete\": 398},"
			+ "{\"time\": \"2016-06-30 03:14:00\",\"request\": 460,\"complete\": 449},"
			+ "{\"time\": \"2016-06-30 03:15:00\",\"request\": 117,\"complete\": 507},"
			+ "{\"time\": \"2016-06-30 03:16:00\",\"request\": 890,\"complete\": 105},"
			+ "{\"time\": \"2016-06-30 03:17:00\",\"request\": 148,\"complete\": 821},"
			+ "{\"time\": \"2016-06-30 03:18:00\",\"request\": 73,\"complete\": 528},"
			+ "{\"time\": \"2016-06-30 03:19:00\",\"request\": 282,\"complete\": 159},"
			+ "{\"time\": \"2016-06-30 03:20:00\",\"request\": 177,\"complete\": 484},"
			+ "{\"time\": \"2016-06-30 03:21:00\",\"request\": 696,\"complete\": 128},"
			+ "{\"time\": \"2016-06-30 03:22:00\",\"request\": 716,\"complete\": 197},"
			+ "{\"time\": \"2016-06-30 03:23:00\",\"request\": 656,\"complete\": 4},"
			+ "{\"time\": \"2016-06-30 03:24:00\",\"request\": 273,\"complete\": 554},"
			+ "{\"time\": \"2016-06-30 03:25:00\",\"request\": 395,\"complete\": 312},"
			+ "{\"time\": \"2016-06-30 03:26:00\",\"request\": 498,\"complete\": 11},"
			+ "{\"time\": \"2016-06-30 03:27:00\",\"request\": 761,\"complete\": 158},"
			+ "{\"time\": \"2016-06-30 03:28:00\",\"request\": 544,\"complete\": 212},"
			+ "{\"time\": \"2016-06-30 03:29:00\",\"request\": 671,\"complete\": 53},"
			+ "{\"time\": \"2016-06-30 03:30:00\",\"request\": 168,\"complete\": 134},"
			+ "{\"time\": \"2016-06-30 03:31:00\",\"request\": 924,\"complete\": 14},"
			+ "{\"time\": \"2016-06-30 03:32:00\",\"request\": 960,\"complete\": 37},"
			+ "{\"time\": \"2016-06-30 03:33:00\",\"request\": 764,\"complete\": 11},"
			+ "{\"time\": \"2016-06-30 03:34:00\",\"request\": 794,\"complete\": 139},"
			+ "{\"time\": \"2016-06-30 03:35:00\",\"request\": 72,\"complete\": 592},"
			+ "{\"time\": \"2016-06-30 03:36:00\",\"request\": 665,\"complete\": 294},"
			+ "{\"time\": \"2016-06-30 03:37:00\",\"request\": 453,\"complete\": 180},"
			+ "{\"time\": \"2016-06-30 03:38:00\",\"request\": 569,\"complete\": 336},"
			+ "{\"time\": \"2016-06-30 03:39:00\",\"request\": 186,\"complete\": 587},"
			+ "{\"time\": \"2016-06-30 03:40:00\",\"request\": 793,\"complete\": 69},"
			+ "{\"time\": \"2016-06-30 03:41:00\",\"request\": 667,\"complete\": 74},"
			+ "{\"time\": \"2016-06-30 03:42:00\",\"request\": 100,\"complete\": 785},"
			+ "{\"time\": \"2016-06-30 03:43:00\",\"request\": 14,\"complete\": 324},"
			+ "{\"time\": \"2016-06-30 03:44:00\",\"request\": 854,\"complete\": 30},"
			+ "{\"time\": \"2016-06-30 03:45:00\",\"request\": 663,\"complete\": 309},"
			+ "{\"time\": \"2016-06-30 03:46:00\",\"request\": 694,\"complete\": 195},"
			+ "{\"time\": \"2016-06-30 03:47:00\",\"request\": 501,\"complete\": 231},"
			+ "{\"time\": \"2016-06-30 03:48:00\",\"request\": 334,\"complete\": 586},"
			+ "{\"time\": \"2016-06-30 03:49:00\",\"request\": 631,\"complete\": 283},"
			+ "{\"time\": \"2016-06-30 03:50:00\",\"request\": 134,\"complete\": 49},"
			+ "{\"time\": \"2016-06-30 03:51:00\",\"request\": 852,\"complete\": 145},"
			+ "{\"time\": \"2016-06-30 03:52:00\",\"request\": 459,\"complete\": 186},"
			+ "{\"time\": \"2016-06-30 03:53:00\",\"request\": 30,\"complete\": 865},"
			+ "{\"time\": \"2016-06-30 03:54:00\",\"request\": 925,\"complete\": 22},"
			+ "{\"time\": \"2016-06-30 03:55:00\",\"request\": 132,\"complete\": 647},"
			+ "{\"time\": \"2016-06-30 03:56:00\",\"request\": 295,\"complete\": 8},"
			+ "{\"time\": \"2016-06-30 03:57:00\",\"request\": 845,\"complete\": 61},"
			+ "{\"time\": \"2016-06-30 03:58:00\",\"request\": 87,\"complete\": 852},"
			+ "{\"time\": \"2016-06-30 03:59:00\",\"request\": 344,\"complete\": 66}]}";
	return data;
}