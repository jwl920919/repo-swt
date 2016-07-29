$("#layDiv").css("visibility", "hidden");
// iMemoryUsg iSwapUsg iDbUsg iDiskUsg iCapacityUsg
var m_hardwareStateAjaxCall;
var m_redundancyStateAjaxCall;
var m_DchpMessageAjaxCall;
var hardwareStateAjaxCallTime = 5000;
var redundancyStateAjaxCallTime = 5000;
var DchpMessageAjaxCallTime = 5000;
var hwData;
var rData;
var dhcpData;

$(document).ready(function() {
    AllClearAjaxCall();
    m_hardwareStateAjaxCall = setInterval(HardwareStateAjaxCall, 0);
    m_redundancyStateAjaxCall = setInterval(redundancyStateAjaxCall, 0);
    m_DchpMessageAjaxCall = setInterval(DchpMessageAjaxCall, 0);
    jQueryKnob(); // jQueryKnob 차트관련 스크립트
    fnPieChartTooltipBind($("#dhcpPieChart"));
});
$(window).resize(function() {
    $('#dhcp-table').css('width', $('#dhcp-body').width() - 150);
}).resize();
// jQueryKnob 차트관련 스크립트
function jQueryKnob() {
    /* jQueryKnob 차트관련 스크립트 */
    $(".knob")
            .knob(
                    {
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
}
// DHCP 메세지 정보를 불러오기 위한 AjaxCall
function DchpMessageAjaxCall() {
    try {
        $.getJSON("/systemManagement/getDhcpMessagesInfo", function(json) {
            dhcpData = json.resultValue;
            if (dhcpData != "") {
                if (dhcpData.dhcp_msg_info != "") {
                    $.plot("#dhcpPieChart", dhcpData.dhcp_msg_info, {
                        series : {
                            pie : {
                                show : true,
                                radius : 1,
                                // innerRadius: 0.5,
                                label : {
                                    show : true,
                                    radius : 2 / 3,
                                    formatter : labelFormatter,
                                    threshold : 0.1
                                }
                            },
                            trendline : {
                                show : true
                            }
                        },
                        legend : {
                            show : false
                        },
                        grid : {
                            hoverable : true,
                            clickable : true
                        }
                    });
                    
                    //dhcp-td-1
                    $('#dhcp-td-1').html(dhcpData.dhcp_msg_info[0].data);
                    $('#dhcp-td-2').html(dhcpData.dhcp_msg_info[1].data);
                    $('#dhcp-td-3').html(dhcpData.dhcp_msg_info[2].data);
                    $('#dhcp-td-4').html(dhcpData.dhcp_msg_info[3].data);
                    $('#dhcp-td-5').html(dhcpData.dhcp_msg_info[4].data);
                    $('#dhcp-td-6').html(dhcpData.dhcp_msg_info[5].data);
                    $('#dhcp-td-7').html(dhcpData.dhcp_msg_info[6].data);
                    $('#dhcp-td-8').html(dhcpData.dhcp_msg_info[7].data);
                    
                    

                    clearDchpMessageAjaxCall();
                    m_DchpMessageAjaxCall = setInterval(DchpMessageAjaxCall,
                            DchpMessageAjaxCallTime);// 페이지 로딩 데이터 조회 후 polling
                    // 시간 변경
                }
            }
        });

        
    } catch (e) {
        console.log("infobloxStatus.js DchpMessageAjaxCall() Error Log : "
                + e.message);
    }
}
function clearDchpMessageAjaxCall() {
    clearInterval(m_DchpMessageAjaxCall);
}

// 하드웨어 정보를 불러오기 위한 AjaxCall
function HardwareStateAjaxCall() {
    try {

        $.getJSON("/systemManagement/getHwInfo", function(json) {
            hwData = json.resultValue;
            if (hwData != "") {
                setInfobloxCpuUsgStatus();
                setInfobloxMemoryUsgStatus();
                setInfobloxSwapUsgStatus();
                setInfobloxDbUsgStatus();
                setInfobloxDiskUsgStatus();
                setInfobloxCapacityUsgStatus();
                setInfobloxHost();
                setInfobloxHostName();
                setInfobloxHardwareID();
                setInfobloxSysUptime();
                setInfobloxSysTmep();
                setInfobloxPowerStatus();
                setInfobloxFanStatus();
                setInfobloxLicenseStatus();
                setInfobloxServiceStatus();
                setInfobloxOS();
                setInfobloxCollectTime();
                clearHardwareStateAjaxCall();
                m_hardwareStateAjaxCall = setInterval(HardwareStateAjaxCall,
                        hardwareStateAjaxCallTime);// 페이지 로딩 데이터 조회 후 polling
                                                    // 시간 변경
            }
        });

    } catch (e) {
        console.log("infobloxStatus.js HardwareStateAjaxCall() Error Log : "
                + e.message);
    }
}

function clearHardwareStateAjaxCall() {
    clearInterval(m_hardwareStateAjaxCall);
}

function redundancyStateAjaxCall() {
    try {
        $.getJSON("/systemManagement/getRedundancyStatus", function(json) {
            rData = json.resultValue;
            if (rData != "") {
                setPortRedundancyEnabled();
                setHaEnabled();
                setVrrpAddrsetting();
                setVrrpSubnetsetting();
                setVrrpGatewaysetting();
                setGridEnabled();
                setGridStatus();
                setInfobloxRedundancyCollectTime();
                clearRedundancyStateAjaxCall();
                m_redundancyStateAjaxCall = setInterval(redundancyStateAjaxCall,
                        redundancyStateAjaxCallTime);// 페이지 로딩 데이터 조회 후 polling
                // 시간 변경
            }
        });
       
    } catch (e) {
        console.log("infobloxStatus.js redundancyStateAjaxCall() Error Log : "
                + e.message);
    }
}

function clearRedundancyStateAjaxCall() {
    clearInterval(m_redundancyStateAjaxCall);
}
// cpu setting
function setInfobloxCpuUsgStatus() {
    try {
        if (hwData.cpu_usage != "") {
            $('#iCpuUsg').trigger('configure', {
                "min" : 0,
                "max" : 100,
                "fgColor" : '#00c0ef',
                "inputColor" : '#00c0ef'
            });
            if (hwData.cpu_usage >= 80) {
                $('#iCpuUsg').trigger('configure', {
                    "fgColor" : '#ff0000',
                    "inputColor" : '#ff0000'
                });
            }
            $('#iCpuUsg').val(hwData.cpu_usage).trigger('change');

        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxCpuUsgStatus() Error Log : "
                + e.message);
    }
}
// memory setting
function setInfobloxMemoryUsgStatus() {
    try {
        if (hwData.memory_usage != "") {
            $('#iMemoryUsg').trigger('configure', {
                "min" : 0,
                "max" : 100,
                "fgColor" : '#00c0ef',
                "inputColor" : '#00c0ef'
            });
            if (hwData.memory_usage >= 80) {
                $('#iMemoryUsg').trigger('configure', {
                    "fgColor" : '#ff0000',
                    "inputColor" : '#ff0000'
                });
            }
            $('#iMemoryUsg').val(hwData.memory_usage).trigger('change');

        }
    } catch (e) {
        console
                .log("infobloxStatus.js setInfobloxMemoryUsgStatus() Error Log : "
                        + e.message);
    }
}
// swap setting
function setInfobloxSwapUsgStatus() {
    try {
        if (hwData.swap_usage != "") {
            $('#iSwapUsg').trigger('configure', {
                "min" : 0,
                "max" : 100,
                "fgColor" : '#00c0ef',
                "inputColor" : '#00c0ef'
            });
            if (hwData.swap_usage >= 80) {
                $('#iSwapUsg').trigger('configure', {
                    "fgColor" : '#ff0000',
                    "inputColor" : '#ff0000'
                });
            }
            $('#iSwapUsg').val(hwData.swap_usage).trigger('change');

        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSwapUsgStatus() Error Log : "
                + e.message);
    }
}
// db setting
function setInfobloxDbUsgStatus() {
    try {
        if (hwData.db_usage != "") {
            $('#iDbUsg').trigger('configure', {
                "min" : 0,
                "max" : 100,
                "fgColor" : '#00c0ef',
                "inputColor" : '#00c0ef'
            });
            if (hwData.db_usage >= 80) {
                $('#iDbUsg').trigger('configure', {
                    "fgColor" : '#ff0000',
                    "inputColor" : '#ff0000'
                });
            }
            $('#iDbUsg').val(hwData.db_usage).trigger('change');

        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxDbUsgStatus() Error Log : "
                + e.message);
    }
}
// disk setting
function setInfobloxDiskUsgStatus() {

    try {
        if (hwData.disk_usage != "") {
            $('#iDiskUsg').trigger('configure', {
                "min" : 0,
                "max" : 100,
                "fgColor" : '#00c0ef',
                "inputColor" : '#00c0ef'
            });
            if (hwData.disk_usage >= 80) {
                $('#iDiskUsg').trigger('configure', {
                    "fgColor" : '#ff0000',
                    "inputColor" : '#ff0000'
                });
            }
            $('#iDiskUsg').val(hwData.disk_usage).trigger('change');

        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxDiskUsgStatus() Error Log : "
                + e.message);
    }
}
// capacity setting
function setInfobloxCapacityUsgStatus() {

    try {
        if (hwData.capacity_usage != "") {
            $('#iCapacityUsg').trigger('configure', {
                "min" : 0,
                "max" : 100,
                "fgColor" : '#00c0ef',
                "inputColor" : '#00c0ef'
            });
            if (hwData.capacity_usage >= 80) {
                $('#iDiskUsg').trigger('configure', {
                    "fgColor" : '#ff0000',
                    "inputColor" : '#ff0000'
                });
            }
            $('#iCapacityUsg').val(hwData.capacity_usage).trigger('change');
        }
    } catch (e) {
        console
                .log("infobloxStatus.js setInfobloxCapacityUsgStatus() Error Log : "
                        + e.message);
    }
}

// host setting
function setInfobloxHost() {
    try {
        if (hwData.host != "") {
            $('#host-info').html(hwData.host);
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxHost() Error Log : "
                + e.message);
    }
}
// host name setting
function setInfobloxHostName() {
    try {
        if (hwData.host_name != "") {
            $('#host-name-info').html(hwData.host_name);
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxHostName() Error Log : "
                + e.message);
    }
}
// hwid setting
function setInfobloxHardwareID() {
    try {
        if (hwData.hwid != "") {
            $('#id-info').html(hwData.hwid);
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxHardwareID() Error Log : "
                + e.message);
    }
}
// sys uptime setting
function setInfobloxSysUptime() {
    try {
        if (hwData.sys_uptime != "") {
            var sys_uptime = parseInt(hwData.sys_uptime);
            var d = parseInt(sys_uptime / 24 / 3600);
            var h = parseInt((sys_uptime - d * 24 * 3600) / 3600);
            var m = parseInt((sys_uptime - (d * 24 + h) * 3600) / 60);
            var s = parseInt((sys_uptime - (d * 24 + h) * 3600 - m * 60));
            $('#uptime-info').html(d + '일 ' + h + '시 ' + m + '분 ' + s + '초');
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysUptime() Error Log : "
                + e.message);
    }
}
// sys temp setting
function setInfobloxSysTmep() {
    try {
        if (hwData.sys_temp != "") {
            $('#sys-temp-info').html(hwData.sys_temp);
            $('#cpu-temp-info').html(hwData.cpu_temp);
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysTmep() Error Log : "
                + e.message);
    }
}
// power setting
function setInfobloxPowerStatus() {
    try {
        if (hwData.power1_status != "" && hwData.power2_status != "") {
            $('#power1-status').attr("class", "fa fa-circle");
            $('#power2-status').attr("class", "fa fa-circle");
            if (hwData.power1_status.toUpperCase() == 'OK') {
                $('#power1-status').addClass('normal');
            } else if (hwData.power1_status.toUpperCase() == 'EMPTY') {
                $('#power1-status').addClass('empty');
            } else {
                $('#power1-status').addClass('warning');
            }
            if (hwData.power2_status.toUpperCase() == 'OK') {
                $('#power2-status').addClass('normal');
            } else if (hwData.power2_status.toUpperCase() == 'EMPTY') {
                $('#power2-status').addClass('empty');
            } else {
                $('#power2-status').addClass('warning');
            }
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysTmep() Error Log : "
                + e.message);
    }
}
// fan setting
function setInfobloxFanStatus() {
    try {
        if (hwData.fans != "") {
            var tag = '';
            var isStart = true, isEnd = false;
            for (idx = 0; idx < hwData.fans.length; idx++) {
                if (isStart) {
                    tag += '<tr>';
                    isStart = false;
                }

                tag += '<td>';
                if (hwData.fans[idx].status == 'WORKING') {
                    tag += '<i class="fa fa-circle normal"></i>';
                } else {
                    tag += '<i class="fa fa-circle error"></i>';
                }
                tag += ('&nbsp;&nbsp;</td><td>FAN');
                tag += (hwData.fans[idx].index);
                tag += (':&nbsp;</td><td>');
                tag += (hwData.fans[idx].rpm);
                tag += ('RPM&nbsp;&nbsp;</td>');
                if (idx >= 1 && (idx + 1) % 2 == 0 && !isEnd) {
                    isEnd = true;
                }
                if (isEnd) {
                    tag += '</tr>';
                    isEnd = false;
                    isStart = true;
                }

            }
            $('#fan-info').html(tag);
            $('#fan-info').parent().attr('style',
                    'height:' + $('#fan-info').css('height'));
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxFanStatus() Error Log : "
                + e.message);
    }
}
// license setting
function setInfobloxLicenseStatus() {
    try {
        if (hwData.licenses != "") {
            var tag = '';
            var isStart = true, isEnd = false;
            for (idx = 0; idx < hwData.licenses.length; idx++) {
                if (isStart) {
                    tag += '<tr>';
                    isStart = false;
                }
                var type = hwData.licenses[idx].type;
                tag += '<td>';
                if (hwData.licenses[idx].result) {
                    tag += '<i class="fa fa-circle normal custom-tooltip"><span class="tooltiptext">';
                    tag += 'Now: '
                            + new Date(hwData.licenses[idx].now)
                                    .format('yyyy/MM/dd');
                    tag += '<br/>'
                    tag += 'Expiry Date: ' + hwData.licenses[idx].gap_day
                            + 'D ' + hwData.licenses[idx].gap_hour + 'H '
                            + hwData.licenses[idx].gap_minute + 'M '
                            + hwData.licenses[idx].gap_second + 'S';
                    tag += '</span></i>';
                } else {
                    tag += '<i class="fa fa-circle error custom-tooltip"><span class="tooltiptext">Expire This License</span></i>';
                }
                tag += '&nbsp;&nbsp;</td><td>';
                tag += (hwData.licenses[idx].type);
                tag += '&nbsp;&nbsp;</td>';
                if (idx >= 4 && (idx + 1) % 5 == 0 && !isEnd) {
                    isEnd = true;
                }
                if (isEnd) {
                    tag += '</tr>';
                    isEnd = false;
                    isStart = true;
                }

            }
            $('#license-info').html(tag);
            $('#license-info').parent().attr('style',
                    'height:' + $('#license-info').css('height'));
        }

    } catch (e) {
        console.log("infobloxStatus.js setInfobloxFanStatus() Error Log : "
                + e.message);
    }
}
// os setting
function setInfobloxOS() {
    try {
        if (hwData.os != "") {
            $('#os-info').html(hwData.os);
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysTmep() Error Log : "
                + e.message);
    }
}

// collect time setting
function setInfobloxCollectTime() {
    try {
        if (hwData.collect_time != "") {
            $('#hw-collect-time').html(
                    new Date(hwData.collect_time * 1000)
                            .format('yyyy/MM/dd HH:mm:ss'));
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxCollectTime() Error Log : "
                + e.message);
    }
};

// service setting
function setInfobloxServiceStatus() {
    try {
        $('#dhcp-is-enable').attr("class", "fa fa-circle");
        $('#dns-is-enable').attr("class", "fa fa-circle");
        if (hwData.service_status.enable_dhcp) {
            $('#dhcp-is-enable').addClass('normal');
        } else {
            $('#dhcp-is-enable').addClass('error');
        }
        if (hwData.service_status.enable_dns) {
            $('#dns-is-enable').addClass('normal');
        } else {
            $('#dns-is-enable').addClass('error');
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysTmep() Error Log : "
                + e.message);
    }
}

function setPortRedundancyEnabled() {
    try {
        $('#lan2-port-setting').attr("class", "fa fa-circle normal");
        if (rData.lan2_port_setting) {
            $('#lan2-port-setting').addClass('normal');
        } else {
            $('#lan2-port-setting').addClass('error');
        }
    } catch (e) {
        console.log("infobloxStatus.js setPortRedundancyEnabled() Error Log : "
                + e.message);
    }
}
function setHaEnabled() {
    try {
        console.log(rData);
        $('#enable-ha').attr("class", "fa fa-circle normal");
        if (rData.enable_ha) {
            $('#enable-ha').addClass('normal');
        } else {
            $('#enable-ha').addClass('error');
        }
    } catch (e) {
        console
                .log("infobloxStatus.js setHaEnabled() Error Log : "
                        + e.message);
    }
}
function setVrrpAddrsetting() {
    try {
        if (rData.vrrp_address != "") {
            $('#vrrp-address').html(rData.vrrp_address);
        }
    } catch (e) {
        console.log("infobloxStatus.js setVrrpAddrsetting() Error Log : "
                + e.message);
    }
}
function setVrrpSubnetsetting() {
    try {
        if (rData.vrrp_subnet != "") {
            $('#vrrp-subnet').html(rData.vrrp_subnet);
        }
    } catch (e) {
        console.log("infobloxStatus.js setVrrpSubnetsetting() Error Log : "
                + e.message);
    }
}
function setVrrpGatewaysetting() {
    try {
        if (rData.vrrp_gateway != "") {
            $('#vrrp-gateway').html(rData.vrrp_gateway);
        }
    } catch (e) {
        console.log("infobloxStatus.js setVrrpGatewaysetting() Error Log : "
                + e.message);
    }
}
function setGridEnabled() {
    try {
        $('#master-candidate').attr("class", "fa fa-circle");
        if (rData.master_candidate) {
            $('#master-candidate').addClass('normal');
        } else {
            $('#master-candidate').addClass('error');
        }
    } catch (e) {
        console.log("infobloxStatus.js setGridEnabled() Error Log : "
                + e.message);
    }
}
function setGridStatus() {
    try {
        if (rData.group_position != "") {
            $('#grid-status').html(rData.group_position);
        }
    } catch (e) {
        console.log("infobloxStatus.js setGridStatus() Error Log : "
                + e.message);
    }
}

// collect time setting
function setInfobloxRedundancyCollectTime() {
    try {
        if (rData.collect_time != "") {
            $('#reduncdancy-collect-time').html(
                    new Date(rData.collect_time * 1000)
                            .format('yyyy/MM/dd HH:mm:ss'));
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxCollectTime() Error Log : "
                + e.message);
    }
};

// DateFormat setting
Date.prototype.format = function(f) {
    if (!this.valueOf())
        return " ";

    var weekName = [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ];
    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
        case "yyyy":
            return d.getFullYear();
        case "yy":
            return (d.getFullYear() % 1000).zf(2);
        case "MM":
            return (d.getMonth() + 1).zf(2);
        case "dd":
            return d.getDate().zf(2);
        case "E":
            return weekName[d.getDay()];
        case "HH":
            return d.getHours().zf(2);
        case "hh":
            return ((h = d.getHours() % 12) ? h : 12).zf(2);
        case "mm":
            return d.getMinutes().zf(2);
        case "ss":
            return d.getSeconds().zf(2);
        case "a/p":
            return d.getHours() < 12 ? "AM" : "PM";
        default:
            return $1;
        }
    });
};

// Pie Chart Tooltip Bind
function fnPieChartTooltipBind(chart) {
    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position : 'absolute',
            display : 'none',
            top : y + 10,
            left : x + 10,
            border : '1px solid #fdd',
            padding : '2px',
            'background-color' : '#f2f2f2',
            color : '#000000',
            opacity : 0.9
        }).appendTo("body").fadeIn(200);
    }

    var previousPoint = null;
    chart.bind("plothover", function(event, pos, item) {
        $("#x").text(pos.pageX);
        $("#y").text(pos.pageY);
        if (item) {

            // alert(typeof item.series.label === 'string');
            // alert(item.series.label + ", " +item.series.data);
            if (previousPoint != item.datapoint) {
                previousPoint = item.datapoint;
                $("#tooltip").remove();
                // showTooltip(pos.pageX, pos.pageY, "Hover @" + pos.pageX + " ,
                // " + pos.pageY);
                showTooltip(pos.pageX, pos.pageY, item.series.label + " : "
                        + item.series.data[0][1]);
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });
    chart.bind("plotclick", function(event, pos, item) {
        if (item) {
            $("#clickdata").text(
                    "You clicked point " + item.dataIndex + " in "
                            + item.series.label + ".");
            // plot.highlight(item.series, item.datapoint);

        }
    });
}

function AllClearAjaxCall() {
    clearInterval(m_hardwareStateAjaxCall);
    clearInterval(m_redundancyStateAjaxCall);
    clearInterval(m_DchpMessageAjaxCall);
}