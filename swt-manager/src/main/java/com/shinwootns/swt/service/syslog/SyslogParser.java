package com.shinwootns.swt.service.syslog;

import org.json.simple.JSONObject;

import com.shinwootns.common.network.SyslogEntity;

public class SyslogParser {
	
	public static JSONObject processSyslog(String rawMessage)
	{
    	// ex) Feb 24 17:38:33 192.168.1.11 dhcpd[22231]: DHCPREQUEST for 192.168.1.12 from 6c:29:95:05:38:a4 (BJPARK) via eth1 uid 01:6c:29:95:05:38:a4 (RENEW)
    	
    	int nIndex1 = rawMessage.indexOf("dhcpd[", 23);
    	if (nIndex1 <= 0)
    		return null;
    	
    	int nIndex2 = rawMessage.indexOf("]: ", nIndex1);
        if (nIndex2 <= 0)
        	return null;
        
        int nIndex3 = rawMessage.indexOf(" ", nIndex2+3);
        if (nIndex3 <= 0)
        	return null;

        // Keyword (DHCPDISCOVER, DHCPOFFER, ...)
        String keyword = rawMessage.substring(nIndex2+3, nIndex3);
        
        JSONObject result = null;
        
        // Discover (C->S)
        if (keyword.equals("DHCPDISCOVER"))
        {
        	result = processDISCOVER(rawMessage.substring(nIndex3+1));
        }
        // Offer (S->C)
        else if (keyword.equals("DHCPOFFER"))
        {
        	result = processOFFER(rawMessage.substring(nIndex3+1));
        }
        // Reqeust (C->S)
        else if (keyword.equals("DHCPREQUEST"))
        {
        	result = processDHCPREQUEST(rawMessage.substring(nIndex3+1));
        }
        // Ack (S->C)
        else if (keyword.equals("DHCPACK"))
        {
        	result = processDHCPACK(rawMessage.substring(nIndex3+1));
        }
        // Not-Ack (S->C)
        else if (keyword.equals("DHCPNACK"))
        {
        	result = processNACK(rawMessage.substring(nIndex3+1));
        }
        // Inform (C->S)
        else if (keyword.equals("DHCPINFORM"))
        {
        	result = processINFORM(rawMessage.substring(nIndex3+1));
        }
        // Expire (S->C)
        else if (keyword.equals("DHCPEXPIRE"))
        {
        	result = processEXPIRE(rawMessage.substring(nIndex3+1));
        }
        // Release (C->S)
        else if (keyword.equals("DHCPRELEASE"))
        {
        	result = processRELEASE(rawMessage.substring(nIndex3+1));
        }
        
        return result;
	}
	
	private static SyslogExtractEntity ExtractData(String message, int startIndex, String startToken, String endToken)
    {
		SyslogExtractEntity result = new SyslogExtractEntity();
	
        if (startIndex < 0)
        	startIndex = 0;

        int index1 = message.indexOf(startToken + " ", startIndex);
        if (index1 >= 0)
        {
            int nIndex2 = message.indexOf(endToken, index1 + startToken.length() + 1);

            if (nIndex2 >= 0 && endToken != null)
            {
            	int tmpStartIndex = index1 + startToken.length() + 1;
            	int valueLength = nIndex2 - (index1 + startToken.length() + 1);
            	
            	result.setValue( message.substring(tmpStartIndex, tmpStartIndex + valueLength) );
            	result.setLastIndex(nIndex2 + 1);

                if (result.getValue() != null && result.getValue().length() > 0)
                	result.setFindFlag(true);
            }
            else
            {
            	result.setValue(message.substring(index1 + startToken.length() + 1));
            	result.setLastIndex(-1);

                if (result.getValue() !=null && result.getValue().length() > 0)
                	result.setFindFlag( true );
            }
        }

        return result;
    }
	
	private static JSONObject processDISCOVER(String message) {

		// [DHCPDISCOVER]
        // from 00:26:66:d1:69:69 via eth1 : network 192.168.1.0 / 24: no free leases
        // from 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 uid 01:00:19:99:e4:ea:7f

        // from [MAC] ~~
        
        SyslogExtractEntity result1 = ExtractData(message, 0, "from", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPDISCOVER");
		result.put("ip", "");
		result.put("mac", result1.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
        
        return result;
    }

	private static JSONObject processOFFER(String message) {

		// [DHCPOFFER]
        // DHCPOFFER on 192.168.1.20 to 00:26:66:d1:69:69 via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:00:26:66:d1:69:69
        // DHCPOFFER on 192.168.1.12 to 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 relay eth1 lease-duration 10 uid 01:00:19:99:e4:ea:7f
        // DHCPOFFER on 192.168.1.13 to d0:7e:35:7e:93:1b (LDK-PC) via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:d0:7e:35:7e:93:1b

        SyslogExtractEntity result1 = ExtractData(message, 0, "on", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		SyslogExtractEntity result2 = ExtractData(message, result1.getLastIndex(), "to", " ");
		if (result2.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPOFFER");
		result.put("ip", result1.getValue());
		result.put("mac", result2.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
        
        return result;
	}
	
	private static JSONObject processDHCPREQUEST(String message)
    {
        String sIP = "", sMac = "";

        // [DHCPREQUEST]
        // DHCPREQUEST for 192.168.1.169 from 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 uid 01:20:55:31:89:89:ed(RENEW)
        // DHCPREQUEST for 192.168.1.114 (192.168.1.254) from 00:0c:29:8a:bb:a7 via eth1 : unknown lease 192.168.1.114.
        // DHCPREQUEST for 192.168.1.192 from 18:f6:43:24:06:4d via eth1 : unknown lease 192.168.1.192.
        // DHCPREQUEST for 192.168.1.101 from 98:83:89:14:4f:9e via eth1

        SyslogExtractEntity result1 = ExtractData(message, 0, "for", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		SyslogExtractEntity result2 = ExtractData(message, result1.getLastIndex(), "from", " ");
		if (result2.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPREQUEST");
		result.put("ip", result1.getValue());
		result.put("mac", result2.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
        
        return result;
    }
	
	private static JSONObject processDHCPACK(String message)
    {
        // [DHCPACK]
        // to 192.168.1.115 (28:e3:47:4c:45:14) via eth1
        // on 192.168.1.19 to 30:52:cb:0c:f8:17 (JS) via eth1 relay eth1 lease-duration 10 (RENEW) uid 01:30:52:cb:0c:f8:17
        // on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW) uid 01:20:55:31:89:89:ed
        
        // #1. on [IP] ~ to [MAC] ~     // Inform-Ack   <-- Target Message
        // #2. to [IP] ~                // Request-Ack (Ignore)
		
		SyslogExtractEntity result1 = ExtractData(message, 0, "on", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		SyslogExtractEntity result2 = ExtractData(message, result1.getLastIndex(), "to", " ");
		if (result2.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPACK");
		result.put("ip", result1.getValue());
		result.put("mac", result2.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);	

		return result;
    }

	private static JSONObject processNACK(String message)
    {
        // [DHCPNAK]
        // on 192.168.1.103 to d0:7e:35:7e:93:1b via eth1

        SyslogExtractEntity result1 = ExtractData(message, 0, "on", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		SyslogExtractEntity result2 = ExtractData(message, result1.getLastIndex(), "to", " ");
		if (result2.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPNAK");
		result.put("ip", result1.getValue());
		result.put("mac", result2.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
		
		return result;
    }

	private static JSONObject processINFORM(String message)
    {
        // [DHCPINFORM]
        // from 192.168.1.115 via eth1

        SyslogExtractEntity result1 = ExtractData(message, 0, "from", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPINFORM");
		result.put("ip", result1.getValue());
		result.put("mac", "");
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
        
        return result;
    }

	private static JSONObject processEXPIRE(String message)
    {
        String sIP = "", sMac = "";

        // [DHCPEXPIRE]
        // on 192.168.1.19 to 30:52:cb:0c:f8:17

        SyslogExtractEntity result1 = ExtractData(message, 0, "on", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		SyslogExtractEntity result2 = ExtractData(message, result1.getLastIndex(), "to", " ");
		if (result2.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPEXPIRE");
		result.put("ip", result1.getValue());
		result.put("mac", result2.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
        
        return result;
    }

	private static JSONObject processRELEASE(String message)
    {
        // [DHCPRELEASE]
        // of 192.168.1.101 from 98:83:89:14:4f:9e (JS) via eth1 (found)uid 01:98:83:89:14:4f:9e

        SyslogExtractEntity result1 = ExtractData(message, 0, "of", " ");
		if (result1.isFindFlag() == false)
			return null;
		
		SyslogExtractEntity result2 = ExtractData(message, result1.getLastIndex(), "from", " ");
		if (result2.isFindFlag() == false)
			return null;
		
		JSONObject result = new JSONObject();
		result.put("type", "DHCPRELEASE");
		result.put("ip", result1.getValue());
		result.put("mac", result2.getValue());
		result.put("renew", (message.indexOf("(RENEW)") > 0)? true : false);
		result.put("result", true);
        
        return result;
    }
}
