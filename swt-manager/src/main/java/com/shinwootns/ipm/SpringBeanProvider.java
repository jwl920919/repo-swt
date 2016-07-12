package com.shinwootns.ipm;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.data.mapper.EventMapper;

@Component
public class SpringBeanProvider {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private ApplicationContext _context = null;
	private ApplicationProperty appProperties = null;

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
    
    public void setApplicationProperties(ApplicationProperty appProperties) {
    	this.appProperties = appProperties;
    }
    
    public ApplicationProperty getApplicationProperties() {
    
    	return appProperties;
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
    
    public StringRedisTemplate getRedisTemplate() { 
    	if (_context == null)
    		return null;
    	
    	StringRedisTemplate redisTemplate = null;
    	
    	try
    	{
    		redisTemplate = _context.getBean(StringRedisTemplate.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getRedisTemplate().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return redisTemplate;
    }
}
