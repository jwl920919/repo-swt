package com.shinwootns.common.utils;

import java.io.ByteArrayOutputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtils {

	private final static Logger _logger = LoggerFactory.getLogger(SystemUtils.class);
	
	public enum OS_Type {
		UNKNOWN, WINDOWS, LINUX, UNIX, MAC, SOLARIS  
	}
	
	public enum IP_TYPE {
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
	
	//region [FUNC] Execute Command (Async)
	public static void executeCommandAsync(CommandLine command) {
		
		_logger.info(String.format("execAsync(). command: " + command.toString()));
		
		DefaultExecutor executor = new DefaultExecutor();
		  
		try {
			
			// Execute Command
			executor.execute(new CommandLine(command), new DefaultExecuteResultHandler());
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		executor = null;
	}
	//endregion
	
	//region [FUNC] Execute Command
	public static String executeCommand(CommandLine command) {
		
		_logger.info(String.format("executeCommand(). command: " + command.toString()));
		
		String result = "";
		
		ByteArrayOutputStream baos = null;
		
		try {
			
			DefaultExecutor executor = new DefaultExecutor();
			baos = new ByteArrayOutputStream();
			PumpStreamHandler streamHandler = new PumpStreamHandler(baos);
			
			// Set StreamHandler
			executor.setStreamHandler(streamHandler);
			
			// Execute Command
			int exitCode = executor.execute(new CommandLine(command));
			
			result = baos.toString();
			
			_logger.debug("executeCommand() - exitCode : " + exitCode);
			_logger.debug("executeCommand() - outputStr : " + result);
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		finally {
			if (baos != null)
			{
				try
				{
					baos.close();
				} catch (Exception e) {}
				
				baos = null;
			}
		}
		
		return result;
	}
	//endregion
	
	//region [FUNC] Get OS name
	public static OS_Type GetOSName() {
		String osName = System.getProperty("os.name");
		
		if (osName == null)
			return OS_Type.UNKNOWN;
					
		osName = osName.toUpperCase();
		
		 if (osName.indexOf("win") >= 0)
			 return OS_Type.WINDOWS; 
		 else if (osName.indexOf("linux") >= 0)
			 return OS_Type.LINUX;
		 else if ( osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0 )
			 return OS_Type.UNIX;
		 else if (osName.indexOf("mac") >= 0)
			 return OS_Type.MAC;
		 else if (osName.indexOf("sunos") >= 0)
			 return OS_Type.SOLARIS;
		
		return OS_Type.UNKNOWN;
	}
	//endregion
	
	//region [FUNC] Get HostName
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {}
		
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {}
		
		return "";
	}
	//endregion
	
	//region [FUNC] Get InterfaceInfo
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
	//endregion
}
