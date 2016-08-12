package com.shinwootns.ipm.insight.worker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.data.entity.InterfaceCam;
import com.shinwootns.data.entity.InterfaceInfo;
import com.shinwootns.data.entity.InterfaceIp;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DeviceMapper;
import com.shinwootns.ipm.insight.service.snmp.SnmpHandler;
import com.shinwootns.ipm.insight.service.snmp.SystemEntry;

public class NetworkDeviceCollctor implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private HashMap<Integer, DeviceSnmp> mapDevice = new HashMap<Integer, DeviceSnmp>(); 
	
	@Override
	public void run() {
		
		_logger.info("NetworkDeviceCollctor... start.");
		
		loadNetworkDevice();
		
		// wait termination
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		_logger.info("NetworkDeviceCollctor... end.");
	}

	//region Load NetworkDevice
	private void loadNetworkDevice() {
		
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper == null)
			return;
		
		String hostName = SystemUtils.getHostName();
		
		List<DeviceSnmp> listDevice = deviceMapper.selectDeviceSnmp(SharedData.getInstance().getSiteID(), hostName);
		if (listDevice == null)
			return;
		
		synchronized(this.mapDevice)
		{
			HashSet<Integer> setPrev = new HashSet<Integer>();
			
			// Old Data
			for(DeviceSnmp oldDevice : this.mapDevice.values())
				setPrev.add(oldDevice.getDeviceId());

			// Update & Insert
			for(DeviceSnmp device : listDevice) {
				
				// For check removed
				setPrev.remove(device.getDeviceId());
				
				if (mapDevice.containsKey(device.getDeviceId()) == false) {
					
					_logger.info( (new StringBuilder())
							.append("Add device. (id=").append(device.getDeviceId()).append(", host=").append(device.getHost()).append(")")
							.toString());
					
					// Insert
					mapDevice.put(device.getDeviceId(), device);
				}
				else {
					// Update
					mapDevice.put(device.getDeviceId(), device);
				}
			}
			
			// Delete removed device
			for(int deleted : setPrev) {
				
				DeviceSnmp remDevice = mapDevice.get(deleted);
				if (remDevice != null) {
					_logger.info( (new StringBuilder())
							.append("Delete device. (id=").append(remDevice.getDeviceId()).append(", host=").append(remDevice.getHost()).append(")")
							.toString());
				}
				mapDevice.remove(deleted);
			}
		}
	}
	//endregion

	//region Collect Start
	private void collectStart() {
		
		/*
		synchronized(mapDevice) 
		{
			for(DeviceSnmp device : this.mapDevice.values()) {
				
				SnmpHandler handler = new SnmpHandler(device.getDeviceId(), device.getHost(), device.getSnmpCommunity());

				// system
				System.out.println( "[SYSTEM] ==================================" );
				SystemEntry system = handler.collectSystem();
				System.out.println( system );
				
				// Interface
				System.out.println( "[INTERFACE] ==================================" );
				HashMap<Integer, InterfaceInfo> mapInf = handler.collectInterface();
				if (mapInf != null) {
					for(InterfaceInfo inf : mapInf.values()) {
						System.out.println(inf);
					}
				}
				
				// Interface IP
				System.out.println( "[INTERFACE IP] ==================================" );
				List<InterfaceIp> listInfIp = handler.collectInterfaceIP();
				if (listInfIp != null) {
					for(InterfaceIp ifIp : listInfIp) {
						System.out.println(ifIp);
					}
				}
				
				// Interface CAM
				System.out.println( "[CAM TABLE] ==================================" );
				HashMap<String, InterfaceCam> mapCam = handler.collectCam();
				if (mapCam != null) {
					for(InterfaceCam cam : mapCam.values()) {
						System.out.println(cam);
					}
				}
			}
		}
		*/
	}
	//endregion
}
