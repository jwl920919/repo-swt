package com.shinwootns.ipm.collector;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.collector.config.ApplicationProperty;

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
    
    /*
    // RabbitTemplate
    public RabbitTemplate getRabbitTemplate() {
    	return _context.getBean("rabbitTemplate", RabbitTemplate.class);
    }*/
}
