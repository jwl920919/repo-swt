package com.shinwootns.swt.service.syslog;

import org.apache.log4j.Logger;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.TimeUtils;

public class SyslogTask implements Runnable {

	private Logger _logger = null;

	private SyslogEntity _syslog;
	
	public SyslogTask(Logger logger, SyslogEntity syslog) {
		this._logger = logger;
		this._syslog = syslog;
	}

	@Override
	public void run() {

		// System.out.println(String.format("[%s, %s] - %s", _syslog.getHost(),
		// TimeUtils.convertToStringTime(_syslog.getRecvTime()),
		// _syslog.getData()));

		ParseSyslog();
	}

	private void ParseSyslog()
	{
		// Syslog Samples
        // Feb 24 17:38:33 192.168.1.11 dhcpd[22231]: DHCPREQUEST for 192.168.1.12 from 6c:29:95:05:38:a4 (BJPARK) via eth1 uid 01:6c:29:95:05:38:a4 (RENEW)

        try
        {
        	String message = _syslog.getData(); 
        	
            int nIndex = message.indexOf("]: ", 26);

            if (nIndex > 0)
            {
                String tmpMessage = message.substring(nIndex + 3);

                //Console.WriteLine(tmpMessage);

                int nLastIndex = 0;

                int nIndex2 = tmpMessage.indexOf(" ");
                if (nIndex2 > 4)
                {
                    String sKeyword = tmpMessage.substring(0, nIndex2);

                    // Discover (C->S)
                    if (sKeyword.equals("DHCPDISCOVER"))
                    {
                    	//nLastIndex = _Process_DISCOVER(tmpMessage, nLastIndex);
                    }
                    // Offer (S->C)
                    else if (sKeyword.equals("DHCPOFFER"))
                    {
                    	//nLastIndex = _Process_OFFER(tmpMessage, nLastIndex);
                    }
                    // Reqeust (C->S)
                    else if (sKeyword.equals("DHCPREQUEST"))
                    {
                    	//nLastIndex = _Process_REQUEST(tmpMessage, nLastIndex);
                    }
                    // Ack (S->C)
                    else if (sKeyword.equals("DHCPACK"))
                    {
                    	//SyslogParser.processDHCPAck(tmpMessage);
                    }
                    // Not-Ack (S->C)
                    else if (sKeyword.equals("DHCPNACK"))
                    {
                    	//nLastIndex = _Process_NACK(tmpMessage, nLastIndex);
                    }
                    // Inform (C->S)
                    else if (sKeyword.equals("DHCPINFORM"))
                    {
                    	//nLastIndex = _Process_INFORM(tmpMessage, nLastIndex);
                    }
                    // Expire (S->C)
                    else if (sKeyword.equals("DHCPEXPIRE"))
                    {
                    	//nLastIndex = _Process_EXPIRE(tmpMessage, nLastIndex);
                    }
                    // Release (C->S)
                    else if (sKeyword.equals("DHCPRELEASE"))
                    {
                    	//nLastIndex = _Process_RELEASE(tmpMessage, nLastIndex);
                    }
                }

                return;
            }
            else
            {
                //Console.WriteLine(message);
            }
        }
        catch (Exception ex)
        {
        	_logger.error(ex.getMessage(), ex);
        }
	}
	
	private void DebugPrint(String sKeyword, boolean bRenew, String sIP, String sMac, String sCoperation)
    {
        String sDHCP = sKeyword;
        sDHCP += (bRenew) ? "-RE" : "";

        System.out.println(String.format("{0,-10}, {1,-12}, {2,-15}, {3}, {4}"
        	, TimeUtils.currentTimeString("HH:mm:ss")
        	, sDHCP, sIP, sMac, sCoperation)
        );
    }
}
