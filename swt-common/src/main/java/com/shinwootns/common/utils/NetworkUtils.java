package com.shinwootns.common.utils;

import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

//import com.shinwootns.common.utils.ip.ipv4.IPv4Range;

public class NetworkUtils {

	//region MacAddr to String
	public static String MacAddrToString(byte[] macAddr) {
		
		StringBuilder sb = new StringBuilder(); 
		
		for(int i=0; i<macAddr.length; i++) {
			
			if (i > 5)
				break;
			
			if (sb.length() == 0)
				sb.append(String.format("%02X", macAddr[i]));
			else
				sb.append(String.format(":%02X", macAddr[i]));
				
		}
		
		return sb.toString();
	}
	//endregion
	
	
	/*
	//region IPv4 To Long
	public static long IPv4ToLong(String ipAddress) {

		long result = 0;

		String[] ipArray = ipAddress.split("\\.");

		if (ipArray.length == 4) {
			for (int i = 3; i >= 0; i--) {

				long ip = Long.parseLong(ipArray[3 - i]);

				// left shifting 24,16,8,0 and bitwise OR

				// 1. 192 << 24
				// 1. 168 << 16
				// 1. 1 << 8
				// 1. 2 << 0
				result |= ip << (i * 8);
			}
		}

		return result;
	}
	//endregion

	//region Long to IPv4
	public static String longToIPv4(long ipNumber) {

		StringBuilder sb = new StringBuilder();
		
		sb.append((ipNumber >> 24) & 0xFF);
		sb.append(".");
		sb.append((ipNumber >> 16) & 0xFF);
		sb.append(".");
		sb.append((ipNumber >> 8) & 0xFF);
		sb.append(".");
		sb.append((ipNumber & 0xFF));

		return sb.toString();
	}
	//endregion
	
	//region Get IPv4Range ("1.1.1.0/24")
	public static IPv4Range getIPV4Range(String network) {
		
		int index = network.indexOf("/");
		
		if (index > 0) {
			
			String networkIP = network.substring(0, index);
			String bitString = network.substring(index+1);
			
			int bit = Integer.parseInt(bitString);
			
			return getIPV4Range(networkIP, bit);
		}
		
		return null;
	}
	//endregion

	//region Get IPv4Range ("1.1.1.0", "255.255.255.0")
	public static IPv4Range getIPV4Range(String networkIP, String netmask) {
		
		long _network = IPv4ToLong(networkIP);
		long _netmask = IPv4ToLong(netmask);

		long startIP = (long) _network & _netmask;
		long endIP = (long) (_network | ~_netmask);

		return new IPv4Range(startIP, endIP);
	}
	//endregion
	
	//region Get IPv4Range ("1.1.1.0", 24)
	public static IPv4Range getIPV4Range(String networkIP, int bits) {
		
		if (bits < 0 || bits > 32)
			return null;

		long _network = IPv4ToLong(networkIP);
		long _netmask = (-1L) << (32 - bits);

		long startIP = (long) _network & _netmask;
		long endIP = (long) (_network | ~_netmask);

		return new IPv4Range(startIP, endIP);
	}
	//endregion

	//region IPv4 & IPv6 to BigInteger ("1.1.1.1" or "2607:f0d0:1002:0051:0000:0000:0000:0004")
	public static BigInteger IPToBigInteger(String ipaddr) {
		
		try {
			InetAddress ip = InetAddress.getByName(ipaddr);
			byte[] bytes = ip.getAddress();
		    return new BigInteger(1, bytes);
		    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	//endregion

	*/
}

