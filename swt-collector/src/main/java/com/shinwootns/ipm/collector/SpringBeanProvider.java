package com.shinwootns.ipm.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.mapper.AuthMapper;
import com.shinwootns.ipm.collector.data.mapper.ClientMapper;
import com.shinwootns.ipm.collector.data.mapper.DhcpMapper;
import com.shinwootns.ipm.collector.data.mapper.DeviceMapper;

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
    
    //region [FUNC] getDhcpMapper
    public DhcpMapper getDhcpMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DhcpMapper mapper = null;
    	
    	try
    	{
    		mapper = _context.getBean("dhcpMapper", DhcpMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDhcpMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return mapper;
    }
    //endregion
    
  	//region [FUNC] getClientMapper
	public ClientMapper getClientMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	ClientMapper mapper = null;
    	
    	try
    	{
    		mapper = _context.getBean("clientMapper", ClientMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getClientMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return mapper;
    }
    //endregion
    
	//region [FUNC] getAuthMapper
	public AuthMapper getAuthMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	AuthMapper mapper = null;
    	
    	try
    	{
    		mapper = _context.getBean("authMapper", AuthMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getAuthMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return mapper;
    }
    //endregion
	
	//region [FUNC] getDeviceMapper
	public DeviceMapper getDeviceMapper() {
    	
    	if (_context == null)
    		return null;
    	
    	DeviceMapper mapper = null;
    	
    	try
    	{
    		mapper = _context.getBean("deviceMapper", DeviceMapper.class);
    	}
    	catch(Exception ex) {
    		_logger.error("SpringBeanProvider.getDeviceMapper().... failed");
    		_logger.error(ex.getMessage(), ex);
    	}
    	
    	return mapper;
    }
	//endregion
	
}
