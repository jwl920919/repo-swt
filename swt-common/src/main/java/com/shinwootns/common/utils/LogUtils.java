package com.shinwootns.common.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogUtils {
	
	/*
	public static void WriteLog(Logger logger, Level level, Exception ex) {
		
		if (logger != null)
		{
			try
			{
				if (level == Level.FATAL )
					logger.fatal(ex.getMessage(), ex);
				else if (level == Level.ERROR )
					logger.error(ex.getMessage(), ex);
				else if (level == Level.WARN )
					logger.warn(ex.getMessage(), ex);
				else if (level == Level.INFO )
					logger.info(ex.getMessage(), ex);
				else if (level == Level.DEBUG )
					logger.debug(ex.getMessage(), ex);
				else if (level == Level.TRACE )
					logger.trace(ex.getMessage(), ex);
				else if (level == Level.OFF ) {}
				else
					logger.info(ex.getMessage(), ex);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			ex.printStackTrace();
		}
	}
	
	public static void WriteLog(Logger logger, Level level, String message) {
		
		if (logger != null)
		{
			try
			{
				if (level == Level.FATAL )
					logger.fatal(message);
				else if (level == Level.ERROR )
					logger.error(message);
				else if (level == Level.WARN )
					logger.warn(message);
				else if (level == Level.INFO )
					logger.info(message);
				else if (level == Level.DEBUG )
					logger.debug(message);
				else if (level == Level.OFF ) {}
				else
					logger.info(message);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println(message);
		}
	}
	*/
}
