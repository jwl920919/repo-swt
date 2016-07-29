package com.shinwootns.ipm.worker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.data.entity.EventData;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.mapper.EventMapper;

public class EventWorker extends BaseWorker {

	private Logger _logger = null;
	
	private int _index = 0;
	
	public EventWorker(int index, Logger logger) {
		this._index = index;
		this._logger = logger;
	}
	
	private boolean isSkipInDebugMode() {
		// get ApplicationProperty
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return true;
		
		// debug_insert_event_enable
		if (appProperty.enableInsertEvent == false)
			return true;
		
		return false;
	}
	
	@Override
	public void run() {
		
		if (isSkipInDebugMode())
			return;
		
		_logger.info((new StringBuilder()).append("EventWorker#").append( this._index).append("... start.").toString());
		
		// get EventMapper
		EventMapper eventMapper = SpringBeanProvider.getInstance().getEventMapper();
		if (eventMapper == null)
			return;
		
		List<EventData> listEvent = null;
		
		while(true)
		{
			listEvent = SharedData.getInstance().eventQueue.pop(500, 100);
			if (listEvent == null)
				continue;
			
			for(EventData event : listEvent)
			{
				if (event == null) 
					continue;
				
				try {
					// Insert to db
					eventMapper.insert(event);
				}
				catch (Exception ex) {
					_logger.error(ex.getMessage(), ex);
				}
			}
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				break;
			}
		}
	
		_logger.info((new StringBuilder()).append("EventWorker#").append( this._index).append("... end.").toString());
	}

}
