package Common.ip;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddr {
	
	private InetAddress _ipAddr = null;
	
	public IPAddr() {
	}
	
	public IPAddr(long ip) throws UnknownHostException {
		
		byte[] bytes = new byte[] {
			    (byte)((ip >>> 24) & 0xff)
			    , (byte)((ip >>> 16) & 0xff)
			    , (byte)((ip >>>  8) & 0xff)
			    , (byte)(ip & 0xff)
			  };
		
		_ipAddr = InetAddress.getByAddress(bytes);
	}
	
	public IPAddr(InetAddress ip) {
		_ipAddr = ip;
	}
	
	public IPAddr(String ip) throws UnknownHostException {
		_ipAddr = InetAddress.getByName(ip);
	}
	
	public IPAddr(byte[] bytes) throws UnknownHostException {
		_ipAddr = InetAddress.getByAddress(bytes);
	}
	
	public boolean isIPv4() {
		if ( _ipAddr != null && _ipAddr instanceof Inet4Address )
			return true;
		
		return false;
	}
	
	public boolean isIPv6() {
		if ( _ipAddr != null && _ipAddr instanceof Inet6Address )
			return true;
		
		return false;
	}
	
	public InetAddress getIPAddress() {
		return _ipAddr;
	}
	
	public void setIPAddress(InetAddress ipAddr) {
		this._ipAddr = ipAddr;
	}
	
	public byte[] getBytes() {
		
		if (_ipAddr == null)
			return null;
		
		return _ipAddr.getAddress();
	}
	
	// IPv4 & IPv6
	public BigInteger getNumberToBigInteger() {
		
		byte[] bytes = _ipAddr.getAddress();
		
	    return new BigInteger(1, bytes);
	}
	
	// Only IPv4
	public long getNumberToLong() {
		
		byte[] bytes = _ipAddr.getAddress();
		
		BigInteger bip = new BigInteger(bytes);
		
		return bip.longValue();
		
		/*
		boolean isLittleEndian = false;
		
		byte[] bytes = _ipAddr.getAddress();
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		
	    if (isLittleEndian) 
	    	bb.order(ByteOrder.LITTLE_ENDIAN);
	    
	    return bb.getLong();
	    */
	}
	
	@Override
	public String toString() {
		
		if (_ipAddr == null)
			return null;
		
		return _ipAddr.toString().split("/")[1];
	}
}
