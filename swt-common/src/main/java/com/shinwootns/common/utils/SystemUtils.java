package com.shinwootns.common.utils;

import java.io.ByteArrayOutputStream;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SystemUtils {

	
	public static void executeCommandAsync(CommandLine command, Logger logger) {
		
		LogUtils.WriteLog(logger, Level.INFO, String.format("execAsync(). command: " + command.toString()));
		
		DefaultExecutor executor = new DefaultExecutor();
		  
		try {
			
			// Execute Command
			executor.execute(new CommandLine(command), new DefaultExecuteResultHandler());
			
		} catch (Exception ex) {
			LogUtils.WriteLog(logger, Level.ERROR, ex);
		}
		
		executor = null;
	}
	
	public static String executeCommand(CommandLine command, Logger logger) {
		
		LogUtils.WriteLog(logger, Level.INFO, String.format("executeCommand(). command: " + command.toString()));
		
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
			
			LogUtils.WriteLog(logger, Level.DEBUG, "executeCommand() - exitCode : " + exitCode);
			LogUtils.WriteLog(logger, Level.DEBUG, "executeCommand() - outputStr : " + result);
			
		} catch (Exception ex) {
			LogUtils.WriteLog(logger, Level.ERROR, ex);
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
