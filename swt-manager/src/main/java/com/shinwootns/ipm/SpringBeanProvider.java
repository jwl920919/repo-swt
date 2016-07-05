package com.shinwootns.ipm;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.data.mapper.EventLogMapper;

@Component
public class SpringBeanProvider {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private ApplicationContext _context = null;
	private ApplicationProperties appProperties = null;

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
    
    public void setApplicationProperties(ApplicationProperties appProperties) {
    	this.appProperties = appProperties;
    }
    
    public ApplicationProperties getApplicationProperties() {
    
    	return appProperties;
    }
    
    public EventLogMapper getEventLogMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	EventLogMapper eventLogMapper = null;
    	
    	try
    	{
    		eventLogMapper = _context.getBean("eventLogMapper", EventLogMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return eventLogMapper;
    }
}
