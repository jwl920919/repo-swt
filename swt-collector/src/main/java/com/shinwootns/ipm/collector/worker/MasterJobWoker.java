package com.shinwootns.ipm.collector.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterJobWoker extends BaseWorker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		while(this.isStopFlag() == false) {
			
			
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		_logger.info("MasterJobWoker... end.");
	}

}
