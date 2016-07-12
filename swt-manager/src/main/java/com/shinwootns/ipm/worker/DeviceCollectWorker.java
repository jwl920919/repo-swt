package com.shinwootns.ipm.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceCollectWorker extends BaseWorker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private int _index = 0;
	
	public DeviceCollectWorker(int index) {
		this._index = _index;
	}
	
	@Override
	public void run() {
		
		_logger.info(String.format("DeviceCollectWorker#%d... start.", this._index));
		
		while(true)
		{
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
