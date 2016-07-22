package com.shinwootns.ipm;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.mapper.DeviceMapper;
import com.shinwootns.ipm.data.mapper.DhcpMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;

@Component
public class SpringBeanProvider {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private ApplicationContext _context = null;
	private ApplicationProperty appProperty = null;

	// Singleton
	private static SpringBeanProvider _instance;
	private SpringBeanProvider() {}
	public static synchronized SpringBeanProvider getInstance() {

		if (_instance == null) {
			_instance = new SpringBeanProvider();
		}
		return _instance;
	}

	// get ApplicationContext
    public ApplicationContext getApplicationContext() {
    	
    	if (_context != null)
    		_logger.info( String.format("AppContextProvider - getApplicationContext : %s", this._context.toString()));
    	
        return _context;
    }
 
    // set ApplicationContext
    public void setApplicationContext(ApplicationContext context) {
    	
        this._context = context;
        
        if (this._context != null)
        	_logger.info( String.format("AppContextProvider - setApplicationContext : %s", this._context.toString()));
    }
    
    // ApplicationProperties
    public void setApplicationProperty(ApplicationProperty appProperty) {
    	this.appProperty = appProperty;
    }
    
    public ApplicationProperty getApplicationProperty() {
    	return appProperty;
    }
    
    public EventMapper getEventMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	EventMapper eventMapper = null;
    	
    	try
    	{
    		eventMapper = _context.getBean("eventMapper", EventMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getEventMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return eventMapper;
    }
    
    public DeviceMapper getDeviceMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DeviceMapper deviceMapper = null;
    	
    	try
    	{
    		deviceMapper = _context.getBean("deviceMapper", DeviceMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDeviceMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return deviceMapper;
    }
    
    public DhcpMapper getDhcpMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DhcpMapper dhcpMapper = null;
    	
    	try
    	{
    		dhcpMapper = _context.getBean("dhcpMapper", DhcpMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDhcpMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return dhcpMapper;
    }
}
