package com.shinwootns.ipm.worker.persist;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.entity.EventData;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.worker.BaseWorker;

public class EventWorker extends BaseWorker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private int _index = 0;
	
	public EventWorker(int index) {
		this._index = _index;
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
		
		_logger.info(String.format("EventWorker#%d... start.", this._index));
		
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
		}
	
	}

}
