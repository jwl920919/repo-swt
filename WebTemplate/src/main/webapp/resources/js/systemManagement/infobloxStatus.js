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
            m_hardwareStateAjaxCall = setInterval(HardwareStateAjaxCall,
                    hardwareStateAjaxCallTime);// 페이지 로딩 데이터 조회 후 polling 시간 변경
        }
    } catch (e) {
        console.log("dashboard.js guestIPAssignStatusAjaxCall() Error Log : "
                + e.message);
    }
}

function clearHardwareStateAjaxCall() {
    clearInterval(m_hardwareStateAjaxCall);
}

function setInfobloxCpuUsgStatus() {
    try {
        hwData.cpu_usage = Math.floor(Math.random() * 101);
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
        console.log("infobloxStatus.js iCpuUsgStatusAjaxCall() Error Log : "
                + e.message);
    }
}

function setInfobloxMemoryUsgStatus() {
    try {
        hwData.memory_usage = Math.floor(Math.random() * 101);
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
        console.log("infobloxStatus.js iMemoryUsgStatusAjaxCall() Error Log : "
                + e.message);
    }
}

function setInfobloxSwapUsgStatus() {
    try {
        hwData.swap_usage = Math.floor(Math.random() * 101);
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
        console.log("infobloxStatus.js iSwapUsgStatusAjaxCall() Error Log : "
                + e.message);
    }
}

function setInfobloxDbUsgStatus() {
    try {
        hwData.db_usage = Math.floor(Math.random() * 101);
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
        console.log("infobloxStatus.js iDbUsgStatusAjaxCall() Error Log : "
                + e.message);
    }
}

function setInfobloxDiskUsgStatus() {

    try {
        hwData.disk_usage = Math.floor(Math.random() * 101);
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
        console.log("infobloxStatus.js iDiskUsgStatusAjaxCall() Error Log : "
                + e.message);
    }
}

function setInfobloxCapacityUsgStatus() {

    try {
        hwData.capacity_usage = Math.floor(Math.random() * 101);
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
                .log("infobloxStatus.js iCapacityUsgStatusAjaxCall() Error Log : "
                        + e.message);
    }
}
