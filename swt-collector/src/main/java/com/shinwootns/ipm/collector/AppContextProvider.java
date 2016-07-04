package com.shinwootns.ipm.collector;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppContextProvider {
	
	private static ConfigurableApplicationContext _context;

	// Singleton
	private static AppContextProvider _instance;
	private AppContextProvider() {}
	public static synchronized AppContextProvider getInstance() {

		if (_instance == null) {
			_instance = new AppContextProvider();
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
