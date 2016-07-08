package com.shinwootns.ipm.service.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.entity.EventLogEntity;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.service.BaseWorker;

public class EventWorker extends BaseWorker {

	private final Log _logger = LogFactory.getLog(getClass());
	
	private int _index = 0;
	
	public EventWorker(int index) {
		this._index = _index;
	}
	
	@Override
	public void run() {
		
		_logger.info(String.format("EventWorker#%d... start.", this._index));
		
		// get EventMapper
		EventMapper eventMapper = SpringBeanProvider.getInstance().getEventMapper();
		if (eventMapper == null)
			return;
		
		List<EventLogEntity> listEvent = null;
		
		while(true)
		{
			listEvent = SharedData.getInstance().eventQueue.pop(500, 100);
			if (listEvent == null)
				continue;
			
			for(EventLogEntity event : listEvent)
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
