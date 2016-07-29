package com.shinwootns.common.utils.ip;

import java.math.BigInteger;
import java.net.UnknownHostException;

import com.shinwootns.common.utils.ip.ipv6.IPv6Network;

public class IPNetwork {
	
	private IPAddr _startIP = null;
	private IPAddr _endIP = null;
	
	private int prefixLength = 0;

	public IPNetwork(String network) throws UnknownHostException {

		// IPv6
		if (network.indexOf(":") >= 0)
		{
			IPv6Network ipNetwork = IPv6Network.fromString(network);
			
			if (ipNetwork != null) {
				_startIP = new IPAddr(ipNetwork.getFirst().toInetAddress());
				_endIP = new IPAddr(ipNetwork.getLast().toInetAddress());
				
				prefixLength = ipNetwork.getNetmask().asPrefixLength();
			}
		}
		// IPv4
		else
		{
			parseIPv4NetworkString(network);
		}
	}
	
	private void parseIPv4NetworkString(String network) throws UnknownHostException {
		
		int index = network.indexOf("/");
		
		if (index > 0) {
			
			String ipString = network.substring(0, index);
			String bitString = network.substring(index+1);
			
			prefixLength = Integer.parseInt(bitString);
			
			IPAddr networkIP = new IPAddr(ipString);
			
			if (networkIP.isIPv4()) {
				
				byte[] bytes = networkIP.getBytes();
				
				long _network = ((bytes[0] & 0xFF) << (3*8)) +
					         	((bytes[1] & 0xFF) << (2*8)) +
					         	((bytes[2] & 0xFF) << (1*8)) +
					         	(bytes[3] &  0xFF);
				
				//long _network = networkIP.getNumberToLong();
				
				long _netmask = (-1L) << (32 - prefixLength);

				long startIP = (long) _network & _netmask;
				long endIP = (long) (_network | ~_netmask);

				_startIP = new IPAddr(startIP);
				_endIP = new IPAddr(endIP);
			}
		}
	}
	
	public int getPrefixLength() {
		return prefixLength;
	}
	
	public IPAddr getStartIP() {
		return this._startIP;
	}
	
	public IPAddr getEndIP() {
		return this._endIP;
	}
	
	public BigInteger getIPCount() {
		if ( this._startIP != null && this._endIP != null) {
			BigInteger result = this._endIP.getNumberToBigInteger().subtract( this._startIP.getNumberToBigInteger() );
			return result;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		
		if (_startIP == null)
			return null;
		
		return String.format("%s/%d", _startIP.toString(), prefixLength);
	}
}
