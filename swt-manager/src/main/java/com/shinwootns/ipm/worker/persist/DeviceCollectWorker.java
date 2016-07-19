package com.shinwootns.ipm.worker.persist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.mapper.DeviceMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.worker.BaseWorker;
import com.shinwootns.ipm.worker.WorkerManager;
import com.shinwootns.ipm.worker.task.CollectDhcpTask;

public class DeviceCollectWorker extends BaseWorker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private int _index = 0;
	
	Map _mapDhcp = new HashMap<Integer, DeviceDhcp>();
	
	public DeviceCollectWorker(int index) {
		this._index = _index;
	}
	
	@Override
	public void run() {
		
		_logger.info(String.format("DeviceCollectWorker#%d... start.", this._index));
		
		loadDeviceDhcp();
		
		collectDeviceDhcp();
		
		while(true)
		{
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	public void loadDeviceDhcp() {
		
		// DeviceMapper
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper == null)
			return;
		
		List<DeviceDhcp> listDhcp = deviceMapper.selectDeviceDhcp();
		
		updateDeviceDhcp(listDhcp);
		
		listDhcp.clear();
	}
	
	public void updateDeviceDhcp(List<DeviceDhcp> listDhcp) {
		
		synchronized(this._mapDhcp) {

			Set<Integer> newIdSet = new HashSet<Integer>();  
			
			// Update new device info
			for(DeviceDhcp dhcp : listDhcp) {
				
				if (newIdSet.contains(dhcp.getDeviceId()) == false)
					newIdSet.add(dhcp.getDeviceId());
				
				// Update
				this._mapDhcp.put(dhcp.getDeviceId(), dhcp);
			}
			
			// Check removed device
			for(Object keyObj : this._mapDhcp.keySet()) {
				
				Integer key = (Integer)keyObj;
				
				if (newIdSet.contains(key) == false) {
					
					// Delete
					this._mapDhcp.remove(key);
				}
			}

			
		}
	}
	
	public void collectDeviceDhcp() {
		
		List<DeviceDhcp> listDhcp = new ArrayList<DeviceDhcp>();
		
		synchronized(this._mapDhcp) {
			// Copy all
			listDhcp.addAll(this._mapDhcp.values());
		}
		
		_logger.info("collectDeviceDhcp()... start");
		
		for(DeviceDhcp dhcp : listDhcp) {
			WorkerManager.getInstance().AddTask(new CollectDhcpTask(dhcp));
		}
		
		listDhcp.clear();
		
		_logger.info("collectDeviceDhcp()... end");
	}
}
