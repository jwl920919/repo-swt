package com.shinwootns.ipm;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider {
	
	private static ConfigurableApplicationContext _context;

	// Singleton
	private static ApplicationContextProvider _instance;
	private ApplicationContextProvider() {}
	public static synchronized ApplicationContextProvider getInstance() {

		if (_instance == null) {
			_instance = new ApplicationContextProvider();
		}
		return _instance;
	}
	 
    public ConfigurableApplicationContext getApplicationContext() {
        return _context;
    }
 
    public void setApplicationContext(ConfigurableApplicationContext context) {
        this._context = context;
    }
}
