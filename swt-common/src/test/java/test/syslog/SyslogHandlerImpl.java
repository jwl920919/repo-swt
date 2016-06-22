package test.syslog;

import org.apache.log4j.Level;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogHandler;
import com.shinwootns.common.utils.LogUtils;
import com.shinwootns.common.utils.TimeUtils;

public class SyslogHandlerImpl implements SyslogHandler {

	public void processSyslog(SyslogEntity syslog) {
		
		LogUtils.WriteLog(null, Level.DEBUG , String.format("[%s, %s] - %s", syslog.getHost(), TimeUtils.convertToStringTime(syslog.getRecvTime()), syslog.getData()));
		
	}
}
