$("#layDiv").css("visibility", "hidden");
// iMemoryUsg iSwapUsg iDbUsg iDiskUsg iCapacityUsg
var m_hardwareStateAjaxCall;
var hardwareStateAjaxCallTime = 3000;

var hwData;

$(document).ready(function() {
    HardwareStateAjaxCall();
    jQueryKnob(); // jQueryKnob 차트관련 스크립트
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
//하드웨어 정보를 불러오기 위한 AjaxCall
function HardwareStateAjaxCall() {
    try {

        $.getJSON("resources/js/systemManagement/infobloxStatus.json",
                function(json) {
                    hwData = json;
                });

        if (hwData != "") {
            setInfobloxCpuUsgStatus();
            setInfobloxMemoryUsgStatus();
            setInfobloxSwapUsgStatus();
            setInfobloxDbUsgStatus();
            setInfobloxDiskUsgStatus();
            setInfobloxCapacityUsgStatus();
            clearHardwareStateAjaxCall();
            setInfobloxHardwareID();
            setInfobloxSysUptime();
            setInfobloxSysTmep();
            setInfobloxPowerStatus();
            setInfobloxFanStatus();
            setInfobloxLicenseStatus();
            setInfobloxServiceStatus();
            setInfobloxOS();
            m_hardwareStateAjaxCall = setInterval(HardwareStateAjaxCall,
                    hardwareStateAjaxCallTime);// 페이지 로딩 데이터 조회 후 polling 시간 변경
        }
    } catch (e) {
        console.log("infobloxStatus.js HardwareStateAjaxCall() Error Log : "
                + e.message);
    }
}

function clearHardwareStateAjaxCall() {
    clearInterval(m_hardwareStateAjaxCall);
}
//cpu setting
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
//memory setting
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
//swap setting
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
//db setting
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
//disk setting
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
//capacity setting
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
//hwid setting
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
//sys uptime setting
function setInfobloxSysUptime() {
    try {
        if (hwData.sys_uptime != "") {
            var sys_uptime = parseInt(hwData.sys_uptime / 100);
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
            $('#temp-info').html(hwData.sys_temp);
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysTmep() Error Log : "
                + e.message);
    }
}
//power setting
function setInfobloxPowerStatus() {
    try {
        if (hwData.power1_status != "" && hwData.power2_status != "") {
            $('#power1-status').attr("class", "fa fa-circle");
            $('#power2-status').attr("class", "fa fa-circle");
            if (hwData.power1_status == 'OK') {
                $('#power1-status').addClass('normal');
            } else if (hwData.power1_status == 'empty') {
                $('#power1-status').addClass('empty');
            } else {
                $('#power1-status').addClass('warning');
            }
            if (hwData.power2_status == 'OK') {
                $('#power2-status').addClass('normal');
            } else if (hwData.power2_status == 'empty') {
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
//fan setting
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
                if(hwData.fans[idx].status=='WORKING'){
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
//license setting
function setInfobloxLicenseStatus() {
    try {
        if (hwData.licenses != "") {
            $.ajax({
                url : "/systemManagement/expiryCheck",
                data : JSON.stringify(hwData.licenses),
                dataType : "text",
                type : "POST",
                success : function(data) {
//                    var jsonObj = eval("(" + data + ')');
//                    if (jsonObj.result == true) {
//                        var tag = '';
//                        var isStart = true, isEnd = false;
//                        for (idx = 0; idx < jsonObj.data.length; idx++) {
//                            if (isStart) {
//                                tag += '<tr>';
//                                isStart = false;
//                            }
//                            var type = jsonObj.data[idx].type;
//                            tag += '<td>';
//                            if(jsonObj.data[idx].result){
//                                tag += '<i class="fa fa-circle normal" ></i>';
//                            } else {
//                                tag += '<i class="fa fa-circle error" </i>';
//                            }
//                            tag += '&nbsp;&nbsp;</td><td>';
//                            tag += (jsonObj.data[idx].type);
//                            tag += '&nbsp;&nbsp;</td>';
//                            if (idx >= 4 && (idx + 1) % 5 == 0 && !isEnd) {
//                                isEnd = true;
//                            }
//                            if (isEnd) {
//                                tag += '</tr>';
//                                isEnd = false;
//                                isStart = true;
//                            }
//                            
//                        }
//                        $('#license-info').html(tag);
//                        $('#license-info').parent().attr('style',
//                                'height:' + $('#license-info').css('height'));
//                    }
                }
            });
            
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxFanStatus() Error Log : "
                + e.message);
    }
}
//os setting
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
//service setting
function setInfobloxServiceStatus() {
    try {
        if (hwData.service_status.enable_dhcp != ""
                && hwData.service_status.enable_dns != "") {
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
        }
    } catch (e) {
        console.log("infobloxStatus.js setInfobloxSysTmep() Error Log : "
                + e.message);
    }
}

//DateFormat setting
Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "AM" : "PM";
            default: return $1;
        }
    });
};
