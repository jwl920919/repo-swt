package com.shinwootns.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private Properties _properties = new Properties();
	
	//region Singleton
	private static PropertyUtils _instance = null;
	private PropertyUtils() {}
	public static synchronized PropertyUtils getInstance() {

		if (_instance == null) {
			_instance = new PropertyUtils();
		}
		return _instance;
	}
	//endregion
	
	//region [FUNC] load / save Properties
	public boolean loadProperties(String filepath) {

		FileInputStream fis = null;
		
		try
		{
			// Is exist?
			File file = new File(filepath);
			if (file.exists() == false) {
				_logger.error( (new StringBuilder()).append("ERROR - Not exist properties file : ").append(filepath).toString());
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
			_logger.error(ex.getMessage(), ex);
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
	
	public boolean saveProperties(String filePath) {
		
		FileOutputStream fos = null;
		
		try
		{
			fos = new FileOutputStream(filePath);
			
			_properties.store(fos, "Properties");
			
			return true;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
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
	//endregion
	
	//region [FUNC] getProperty Value
	public String getPropertyString(String key, String defaultValue) {
		
		if ( _properties.containsKey(key) == false )
		{
			_logger.error((new StringBuilder()).append("ERROR - Not defined properties key : ").append(key).toString());
			return defaultValue;
		}
		
		return _properties.getProperty(key);
	}
	
	public int getPropertyNumber(String key, int defaultValue) {
		
		if ( _properties.containsKey(key) == false )
		{
			_logger.error((new StringBuilder()).append("ERROR - Not defined properties key : ").append(key).toString());
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
			_logger.error((new StringBuilder()).append("ERROR - Invalid properties number value : ").append(sVlaue).toString());
		}
		
		return defaultValue;
	}
	//endregion
}
