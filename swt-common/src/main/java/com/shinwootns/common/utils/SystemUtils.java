package com.shinwootns.common.utils;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SystemUtils {

	private static final Logger _logger = Logger.getLogger(SystemUtils.class);
	
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
	
	enum OS_Type {
		UNKNOWN, WINDOWS, LINUX, UNIX, MAC, SOLARIS  
	}
	
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
	
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {}
		
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {}
		
		return "";
	}
	
	
	/*
	public static Vector executeCommand(String command, Logger logger) {
		
		Vector result = new Vector();
		
		Process process = null;
		BufferedReader bufferedReader = null;

		String line;
		
        try
        {
            process = Runtime.getRuntime().exec(command);
            
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            while((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }
        finally {
        	
        	if (bufferedReader != null)
        	{
        		try {
					bufferedReader.close();
				} catch (Exception e) {}
        		
        		bufferedReader = null;
        	}
        	
        	if (process != null) {
        		
        		try {
        			process.destroy();
        		} catch (Exception e) {}
        		
                process = null;
        	}
        }
        
        return result;
	}
	*/
}
