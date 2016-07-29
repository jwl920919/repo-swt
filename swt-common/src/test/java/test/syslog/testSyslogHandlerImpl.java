package test.syslog;

import org.apache.log4j.Level;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogReceiveHandler;
import com.shinwootns.common.utils.TimeUtils;

public class testSyslogHandlerImpl implements SyslogReceiveHandler {

	public void processSyslog(SyslogEntity syslog) {

		System.out.println(String.format("[%s, %s] - %s", syslog.getHost(),
				TimeUtils.convertToStringTime(syslog.getRecvTime()), syslog.getData()));
		
		// ...
	}
}
