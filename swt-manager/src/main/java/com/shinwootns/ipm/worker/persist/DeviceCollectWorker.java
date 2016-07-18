package com.shinwootns.ipm.worker.persist;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.DeviceEntity;
import com.shinwootns.ipm.data.mapper.DeviceMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.worker.BaseWorker;

public class DeviceCollectWorker extends BaseWorker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private int _index = 0;
	
	public DeviceCollectWorker(int index) {
		this._index = _index;
	}
	
	@Override
	public void run() {
		
		_logger.info(String.format("DeviceCollectWorker#%d... start.", this._index));
		
		LoadDeviceInfo();
		
		while(true)
		{
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	public void LoadDeviceInfo() {
		// DeviceMapper
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper == null)
			return;
		
		List<DeviceEntity> listDevice = deviceMapper.selectDeviceByType("DHCP");
		
		for(DeviceEntity device : listDevice) {
			
			System.out.println(device.getIpv4());
		}
	}
}
