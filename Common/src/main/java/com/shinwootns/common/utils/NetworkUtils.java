package com.shinwootns.common.utils;

public class NetworkUtils {

	public static long ipToLong(String ipAddress) {

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

	public static String longToIP(long ipNumber) {

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

	public static IPRange getIPRange(String networkIP, String netmask) {
		
		long _network = ipToLong(networkIP);
		long _netmask = ipToLong(netmask);

		long startIP = (long) _network & _netmask;
		long endIP = (long) (_network | ~_netmask);

		return new IPRange(startIP, endIP);
	}

	public static IPRange getIPRange(String networkIP, int bits) {
		
		if (bits < 0 || bits > 32)
			return null;

		long _network = ipToLong(networkIP);
		long _netmask = (-1L) << (32 - bits);

		long startIP = (long) _network & _netmask;
		long endIP = (long) (_network | ~_netmask);

		return new IPRange(startIP, endIP);
	}
}

