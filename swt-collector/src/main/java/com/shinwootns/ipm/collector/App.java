package com.shinwootns.ipm.collector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static Logger _logger = LogManager.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	DOMConfigurator.configure("resource/log4j.xml");
    	
    	_logger.info("Application Start");
    	
    	try {
	    	MainThread mainThread = new MainThread();
	    	mainThread.start();
    	
			mainThread.wait();
		} catch (InterruptedException e) {
		}catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}

    	_logger.info("Application end");
    }
}
