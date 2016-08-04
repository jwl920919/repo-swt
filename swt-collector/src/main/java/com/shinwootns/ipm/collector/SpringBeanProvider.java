package com.shinwootns.ipm.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.mapper.ClientMapper;
import com.shinwootns.ipm.collector.data.mapper.DataMapper;

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
    	
    	if (_context != null)
    		_logger.info( (new StringBuilder()).append("getApplicationContext :").append(this._context.toString()).toString());
    	
        return _context;
    }
    public void setApplicationContext(ApplicationContext context) {
    	
        this._context = context;
        
        if (this._context != null)
        	_logger.info( (new StringBuilder()).append("setApplicationContext : ").append(this._context.toString()).toString());
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
    
    //region [FUNC] getDataMapper
    public DataMapper getDataMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DataMapper dataMapper = null;
    	
    	try
    	{
    		dataMapper = _context.getBean("dataMapper", DataMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDhcpMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return dataMapper;
    }
    //endregion
    
  	//region [FUNC] getClientMapper
	public ClientMapper getClientMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	ClientMapper clientMapper = null;
    	
    	try
    	{
    		clientMapper = _context.getBean("clientMapper", ClientMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getClientMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return clientMapper;
    }
    //endregion
    
}
