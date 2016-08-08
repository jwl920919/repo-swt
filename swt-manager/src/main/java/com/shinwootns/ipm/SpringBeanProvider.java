package com.shinwootns.ipm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.DashboardMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;

@Component
public class SpringBeanProvider {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private ApplicationContext _context = null;
	private ApplicationProperty appProperty = null;

	//region Singleton
	private static SpringBeanProvider _instance;
	private SpringBeanProvider() {}
	public static synchronized SpringBeanProvider getInstance() {

		if (_instance == null) {
			_instance = new SpringBeanProvider();
		}
		return _instance;
	}
	//endregion

	//region [FUNC] get / set ApplicationContext
    public ApplicationContext getApplicationContext() {
    	synchronized(this) {
    		return _context;
    	}
    }
 
    public void setApplicationContext(ApplicationContext context) {
    	
    	synchronized(this) {
    		this._context = context;
    	}
        
        if (this._context != null)
        	_logger.info( (new StringBuilder()).append("setApplicationContext : ").append(this._context).toString());
    }
    //endregion
    
    //region [FUNC] get / set ApplicationProperties
    public void setApplicationProperty(ApplicationProperty appProperty) {
    	this.appProperty = appProperty;
    }
    
    public ApplicationProperty getApplicationProperty() {
    	return appProperty;
    }
    //endregion
    
    //region [FUNC] Get Mapper (Event, Device, Dhcp, ...)
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

    public DataMapper getDataMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DataMapper dataMapper = null;
    	
    	try
    	{
    		dataMapper = _context.getBean("dataMapper", DataMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDataMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return dataMapper;
    }
    
    public DashboardMapper getDashboardMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DashboardMapper dashboardMapper = null;
    	
    	try
    	{
    		dashboardMapper = _context.getBean("dashboardMapper", DashboardMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDashboardMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return dashboardMapper;
    }
    
    public AuthMapper getAuthMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	AuthMapper authMapper = null;
    	
    	try
    	{
    		authMapper = _context.getBean("authMapper", AuthMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getAuthMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return authMapper;
    }
    
    //endregion
}
