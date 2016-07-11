package com.shinwootns.ipm.collector;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
    
    public RabbitTemplate getRabbitTemplate() {
    	return _context.getBean("rabbitTemplate", RabbitTemplate.class);
    }
}
