package com.shinwootns.swt.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SyslogParser {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	public class ExtractResult {
		public boolean resultFlag;
		public String value;
		public int lastIndex;
	}
	
	@Test
	public void testDHCP()
	{
		//String message = "DHCPACK to 192.168.1.115 (28:e3:47:4c:45:14) via eth1";
        String message = "DHCPACK on 192.168.1.19 to 30:52:cb:0c:f8:17 (JS) via eth1 relay eth1 lease-duration 10 (RENEW) uid 01:30:52:cb:0c:f8:17";
        // String message = "DHCPACK on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW) uid 01:20:55:31:89:89:ed"
		
		processDHCPAck(message);
	}
	
	private ExtractResult ExtractData(String sMessage, int nStartIndex, String sTokenKey, int nLastIndex)
    {
		ExtractResult result = new ExtractResult();
		
		result.resultFlag = false;
		result.value = "";
		result.lastIndex = -1;
		
        if (nStartIndex < 0)
            nStartIndex = 0;

        int nIndex = sMessage.indexOf(sTokenKey + " ", nStartIndex);
        if (nIndex >= 0)
        {
            int nIndex2 = sMessage.indexOf(" ", nIndex + sTokenKey.length() + 1);

            if (nIndex2 >= 0)
            {
            	int startIndex = nIndex + sTokenKey.length() + 1;
            	int valueLength = nIndex2 - (nIndex + sTokenKey.length() + 1);
            	
            	result.value = sMessage.substring(startIndex, startIndex + valueLength);
            	result.lastIndex = nIndex2 + 1;

                if (result.value != null && result.value.length() > 0)
                	result.resultFlag = true;
            }
            else
            {
            	result.value = sMessage.substring(nIndex + sTokenKey.length() + 1);
            	result.lastIndex = -1;

                if (result.value !=null && result.value.length() > 0)
                	result.resultFlag = true;
            }
        }

        return result;
    }
	
	public int process_DISCOVER(String sMsg, int nLastIndex) {
        String sIP="", sMac="";

        // [DHCPDISCOVER]
        // DHCPDISCOVER from 00:26:66:d1:69:69 via eth1 : network 192.168.1.0 / 24: no free leases
        // DHCPDISCOVER from 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 uid 01:00:19:99:e4:ea:7f

        // DHCPDISCOVER from [MAC] ~~
        /*
        if (ExtractData(sMsg, "DHCPDISCOVER".length(), "from", out sMac, ref nLastIndex))
        {
            // Find OUI
            String sCoperation="";
            //SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is Renew ?
            boolean bRenew = (nLastIndex > 0 && sMsg.indexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("DISCOVER", bRenew, sIP, sMac, sCoperation);
        }
        */
        return nLastIndex;
    }

	public int process_OFFER(String sMsg, int nLastIndex) {
        String sIP = "", sMac = "";

        // [DHCPOFFER]
        // DHCPOFFER on 192.168.1.20 to 00:26:66:d1:69:69 via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:00:26:66:d1:69:69
        // DHCPOFFER on 192.168.1.12 to 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 relay eth1 lease-duration 10 uid 01:00:19:99:e4:ea:7f
        // DHCPOFFER on 192.168.1.13 to d0:7e:35:7e:93:1b (LDK-PC) via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:d0:7e:35:7e:93:1b

        /*
        // DHCPOFFER on [IP] ~ to [MAC] ~~
        if (ExtractData(sMsg, "DHCPOFFER".Length, "on", out sIP, ref nLastIndex)
            && ExtractData(sMsg, nLastIndex, "to", out sMac, ref nLastIndex))
        {
            // Find OUI
            string sCoperation;
            SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is automatic registion OUI?
            bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

            // Is Renew ?
            bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("OFFER", bRenew, sIP, sMac, bAutoRegist, sCoperation);
        }
        */
        
        return nLastIndex;
	}
	
	public int _Process_REQUEST(String sMsg, int nLastIndex)
    {
        String sIP = "", sMac = "";

        // [DHCPREQUEST]
        // DHCPREQUEST for 192.168.1.169 from 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 uid 01:20:55:31:89:89:ed(RENEW)
        // DHCPREQUEST for 192.168.1.114 (192.168.1.254) from 00:0c:29:8a:bb:a7 via eth1 : unknown lease 192.168.1.114.
        // DHCPREQUEST for 192.168.1.192 from 18:f6:43:24:06:4d via eth1 : unknown lease 192.168.1.192.
        // DHCPREQUEST for 192.168.1.101 from 98:83:89:14:4f:9e via eth1

        /*
        // DHCPREQUEST for [IP] ~ from [MAC] ~~
        if (ExtractData(sMsg, "DHCPREQUEST".Length, "for", out sIP, ref nLastIndex)
            && ExtractData(sMsg, nLastIndex, "from", out sMac, ref nLastIndex))
        {

            // Find OUI
            string sCoperation;
            SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is automatic registion OUI?
            bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

            // Is Renew ?
            bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("REQUEST", bRenew, sIP, sMac, bAutoRegist, sCoperation);
        }
        */
        
        return nLastIndex;
    }
	
	public void processDHCPAck(String message)
    {
        String ipAddr = "", macAddr = "";

        // [DHCPACK]
        // DHCPACK to 192.168.1.115 (28:e3:47:4c:45:14) via eth1
        // DHCPACK on 192.168.1.19 to 30:52:cb:0c:f8:17 (JS) via eth1 relay eth1 lease-duration 10 (RENEW) uid 01:30:52:cb:0c:f8:17
        // DHCPACK on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW) uid 01:20:55:31:89:89:ed
        
        // #1. DHCPACK on [IP] ~ to [MAC] ~     // Inform-Ack
        // #2. DHCPACK to [IP] ~                // Request-Ack
        
        int lastIndex = -1;
        
        ExtractResult result1 = ExtractData(message, "DHCPACK".length(), "on", lastIndex);
        
        if (result1.resultFlag == true)
        {
        	lastIndex = result1.lastIndex;
        	ipAddr = result1.value;
        	
        	ExtractResult result2 = ExtractData(message, lastIndex, "to", lastIndex);
        	if ( result2.resultFlag == true ) {
        		
        		macAddr = result2.value;
        		
        		System.out.println("IP="+ipAddr + ", MAC="+macAddr);
        	}
        }
        
            // Is Target IP?
            /*bool bTargetIP = SyslogManager.Instance.IsTargetIP(sIP);

            if (bTargetIP)  // Only Target Range
            {
                // Find OUI
                string sCoperation;
                SyslogManager.Instance.FindOUI(sMac, out sCoperation);

                // Is automatic registion OUI?
                bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

                // Is Renew ?
                bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

                if (bRenew == false)    // Exclude Renew
                {
                    // Print
                    //DebugPrint("ACK", bRenew, sIP, sMac, bAutoRegist, sCoperation);

                    PushToUI(sIP, sMac, bRenew, bAutoRegist, sCoperation);
                }
            }
            */
    }

	private int _Process_NACK(String sMsg, int nLastIndex)
    {
        String sIP = "", sMac = "";

        // [DHCPNAK]
        // DHCPNAK on 192.168.1.103 to d0:7e:35:7e:93:1b via eth1

        /*
        // DHCPNAK on [IP] ~ to [MAC] ~
        if (ExtractData(sMsg, "DHCPNACK".Length, "on", out sIP, ref nLastIndex)
            && ExtractData(sMsg, nLastIndex, "to", out sMac, ref nLastIndex))
        {
            // Find OUI
            string sCoperation;
            SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is automatic registion OUI?
            bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

            // Is Renew ?
            bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("NACK", bRenew, sIP, sMac, bAutoRegist, sCoperation);
        }
        */
        return nLastIndex;
    }

	private int _Process_INFORM(String sMsg, int nLastIndex)
    {
        String sIP = "", sMac = "";

        // [DHCPINFORM]
        // DHCPINFORM from 192.168.1.115 via eth1

        /*
        // DHCPINFORM from [IP] ~
        if (ExtractData(sMsg, "DHCPINFORM".Length, "from", out sIP, ref nLastIndex))
        {
            // Find OUI
            string sCoperation;
            SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is automatic registion OUI?
            bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

            // Is Renew ?
            bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("INFORM", bRenew, sIP, sMac, bAutoRegist, sCoperation);
        }
        */
        
        return nLastIndex;
    }

	private int _Process_EXPIRE(String sMsg, int nLastIndex)
    {
        String sIP = "", sMac = "";

        // [DHCPEXPIRE]
        // DHCPEXPIRE on 192.168.1.19 to 30:52:cb:0c:f8:17

        /*
        // DHCPEXPIRE on [IP] ~ to [MAC] ~
        if (ExtractData(sMsg, "DHCPEXPIRE".Length, "on", out sIP, ref nLastIndex)
            && ExtractData(sMsg, nLastIndex, "to", out sMac, ref nLastIndex))
        {
            // Find OUI
            string sCoperation;
            SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is automatic registion OUI?
            bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

            // Is Renew ?
            bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("EXPIRE", bRenew, sIP, sMac, bAutoRegist, sCoperation);
        }
        */
        
        return nLastIndex;
    }

	private int _Process_RELEASE(String sMsg, int nLastIndex)
    {
        String sIP = "", sMac = "";

        // [DHCPRELEASE]
        // DHCPRELEASE of 192.168.1.101 from 98:83:89:14:4f:9e (JS) via eth1 (found)uid 01:98:83:89:14:4f:9e

        /*
        // DHCPRELEASE of [IP] ~ from [MAC] ~
        if (ExtractData(sMsg, "DHCPRELEASE".Length, "of", out sIP, ref nLastIndex)
            && ExtractData(sMsg, nLastIndex, "from", out sMac, ref nLastIndex))
        {
            // Find OUI
            string sCoperation;
            SyslogManager.Instance.FindOUI(sMac, out sCoperation);

            // Is automatic registion OUI?
            bool bAutoRegist = SyslogManager.Instance.IsAutoRegistOUI(sMac);

            // Is Renew ?
            bool bRenew = (nLastIndex > 0 && sMsg.IndexOf("(RENEW)", nLastIndex) > 0) ? true : false;

            // Print
            DebugPrint("RELEASE", bRenew, sIP, sMac, bAutoRegist, sCoperation);
        }
        */
        
        return nLastIndex;
    }
}
