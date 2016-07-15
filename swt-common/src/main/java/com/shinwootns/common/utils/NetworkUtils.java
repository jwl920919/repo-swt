package com.shinwootns.common.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkUtils {
	
	enum IP_TYPE {
		IPv4, IPv6
	};
	
	public static class InterfaceIP {
		public IP_TYPE ipType;
		public String ipaddr;
	};
	
	public static class InterfaceInfo {
		
		// ifnum
		public int index;
		
		// name
		public String name;
		public String displayName;
		
		// macAddr
		public String macAddr;
		
		// ipv4, ipv6
		public List<InterfaceIP> listIPAddr;
	};
	
	public static List<InterfaceInfo> getInterfaceInfo() {

		List<InterfaceInfo> listInterface = new ArrayList<InterfaceInfo>();
		
		Enumeration<NetworkInterface> interfaces;

		try {
			
			interfaces = NetworkInterface.getNetworkInterfaces();
			
			while (interfaces.hasMoreElements()){
			    
				NetworkInterface current = interfaces.nextElement();
			    
				if (!current.isUp() || current.isLoopback() || current.isVirtual()) 
						continue;
				
				InterfaceInfo inf = new InterfaceInfo();
				inf.index = current.getIndex();
				inf.macAddr = NetworkUtils.MacAddrToString(current.getHardwareAddress());
				inf.name = current.getName();
				inf.displayName = current.getDisplayName();
				inf.listIPAddr = new ArrayList<InterfaceIP>();
			    
			    Enumeration<InetAddress> addresses = current.getInetAddresses();
			    while (addresses.hasMoreElements()) {
			        InetAddress current_addr = addresses.nextElement();
			        if (current_addr.isLoopbackAddress())
			        	continue;
			        
			        
			        InterfaceIP ip = new InterfaceIP();
			        
			        if ( current_addr instanceof Inet6Address )
			        {
			        	// IPv6
			        	ip.ipType = IP_TYPE.IPv6;
			        	ip.ipaddr = current_addr.getHostAddress();
			        	
			        	int index = ip.ipaddr.indexOf("%");
			        	if ( index > 0 )
			        		ip.ipaddr = ip.ipaddr.substring(0, index); 
			        }
			        else
			        {
			        	// IPv4
			        	ip.ipType = IP_TYPE.IPv4;
			        	ip.ipaddr = current_addr.getHostAddress();
			        }
			        
			        inf.listIPAddr.add(ip);
			    }
			    
			    listInterface.add(inf);
			}
			
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		return listInterface;
	}
	
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

	public static IPv4Range getIPV4Range(String networkIP, String netmask) {
		
		long _network = IPv4ToLong(networkIP);
		long _netmask = IPv4ToLong(netmask);

		long startIP = (long) _network & _netmask;
		long endIP = (long) (_network | ~_netmask);

		return new IPv4Range(startIP, endIP);
	}

	public static IPv4Range getIPV4Range(String networkIP, int bits) {
		
		if (bits < 0 || bits > 32)
			return null;

		long _network = IPv4ToLong(networkIP);
		long _netmask = (-1L) << (32 - bits);

		long startIP = (long) _network & _netmask;
		long endIP = (long) (_network | ~_netmask);

		return new IPv4Range(startIP, endIP);
	}
}

