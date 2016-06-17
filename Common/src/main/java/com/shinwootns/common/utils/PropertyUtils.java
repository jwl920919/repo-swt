package com.shinwootns.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PropertyUtils {

	private Logger _logger = null;
	private Properties _properties = new Properties();
	
	// Singleton
	private static PropertyUtils _instance = null;

	private PropertyUtils() {}

	public static synchronized PropertyUtils getInstance() {

		if (_instance == null) {
			_instance = new PropertyUtils();
		}
		return _instance;
	}
	
	public void setLogger(Logger logger) {
		this._logger = logger;
	}

	//region Load Properties
	public boolean loadProperties(String filepath) {

		FileInputStream fis = null;
		
		try
		{
			// Is exist?
			File file = new File(filepath);
			if (file.exists() == false) {
				LogUtils.WriteLog(_logger, Level.ERROR, String.format("ERROR - Not exist properties file : '%s'", filepath));
				return false;
			}
			
			// FileInputStream
			fis = new FileInputStream(filepath);
			
			// Load
			_properties.load(fis);
			
			return true;
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		finally {

			// Close
			try {
				if (fis != null)
					fis.close();
			}
			catch(Exception ex1) {}
		}
		
		return false;
	}
	//endregion
	
	public boolean saveProperties(String filePath) {
		
		FileOutputStream fos = null;
		
		try
		{
			fos = new FileOutputStream(filePath);
			
			_properties.store(fos, "Properties");
			
			return true;
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		finally {
			
			// Close
			try {
				if (fos != null)
					fos.close();
			}
			catch(Exception ex1) {}
		}
		
		return false;
	}
	
	public String getPropertyString(String key, String defaultValue) {
		
		if ( _properties.containsKey(key) == false )
		{
			LogUtils.WriteLog(_logger, Level.ERROR, String.format("ERROR - Not defined properties key : '%s'", key));
			return defaultValue;
		}
		
		return _properties.getProperty(key);
	}
	
	public int getPropertyNumber(String key, int defaultValue) {
		
		if ( _properties.containsKey(key) == false )
		{
			LogUtils.WriteLog(_logger, Level.ERROR, String.format("ERROR - Not defined properties key : '%s'", key));
			return defaultValue;
		}
		
		String sVlaue = "";
		try
		{
			sVlaue = _properties.getProperty(key);
			
			return Integer.parseInt(sVlaue);
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, String.format("ERROR - Invalid properties number value : '%s'", sVlaue));
		}
		
		return defaultValue;
	}
}
