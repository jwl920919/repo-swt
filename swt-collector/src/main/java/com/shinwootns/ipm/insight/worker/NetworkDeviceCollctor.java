package com.shinwootns.ipm.insight.worker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.data.entity.DeviceNetwork;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.data.entity.DeviceSysOID;
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
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(1);
	
	@Override
	public void run() {
		
		_logger.info("NetworkDeviceCollctor... start.");
		
		// Collect System & Interface
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						collectStart();
					}
				}
				,0 ,60 ,TimeUnit.SECONDS
		);
		
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
	
	//region Collect Start
	private void collectStart() {
		
		List<DeviceSnmp> listDevice = null;
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper == null)
			return;
		
		// Get Device
		try {
			listDevice = SharedData.getInstance().getDeviceList();
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
			return;
		}

		// Collect Start
		for(DeviceSnmp device : listDevice)
		{
			try
			{
				SnmpHandler handler = new SnmpHandler(device.getDeviceId(), device.getHost(), device.getSnmpCommunity());
	
				_logger.info( (new StringBuilder()).append("Collect Network Device : ").append(device.getHost()).append("... Start").toString() );
				
				if ( handler.checkSnmp() )
				{
				
					// Collect System Info
					collectSystemInfo(handler, device, deviceMapper);
					
					// Collect Interface Info
					collectInterfaceInfo(handler, device, deviceMapper);
					
					Thread.sleep(1000);
					
					// Collect Interface IP
					collectInterfaceIp(handler, device, deviceMapper);
					
					Thread.sleep(1000);
					
					// Interface CAM
					collectInterfaceCAM(handler, device, deviceMapper);

				}
				else {
					_logger.info( (new StringBuilder()).append("SNMP failed - ").append(device.getHost()).toString() );
				}
				
				Thread.sleep(5000);
				
			} catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			} 
			
			_logger.info( (new StringBuilder()).append("Collect Network Device : ").append(device.getHost()).append("... End").toString() );
		}
			
		if (listDevice != null)
			listDevice.clear();
	}
	//endregion

	//region Collect System Info
	private void collectSystemInfo(SnmpHandler handler, DeviceSnmp device, DeviceMapper deviceMapper) {

		SystemEntry system = handler.collectSystem();
		
		if (system == null)
			return;

		DeviceSysOID sysOid = SharedData.getInstance().FindVendorAndModel(system.getSysObjectID());
		
		DeviceNetwork networkDev = new DeviceNetwork();
		networkDev.setDeviceId( device.getDeviceId() );
		networkDev.setHostName( system.getSysName() );
		if (sysOid != null) {
			networkDev.setVendor(sysOid.getVendor());
			networkDev.setModel(sysOid.getModel());
		}
		networkDev.setServiceType(system.getSysServices());
		networkDev.setSysOid(system.getSysObjectID());
		networkDev.setDescription(system.getSysDescr());
		
		// Update to DB
		deviceMapper.updateNetworkDevice(networkDev);
	}
	//endregion
	
	//region Collect Interface Info
	private void collectInterfaceInfo(SnmpHandler handler, DeviceSnmp device, DeviceMapper deviceMapper) {
	
		HashSet<Integer> mapRemoved = new HashSet<Integer>();
		HashMap<Integer, BigDecimal> mapPrevOctet = new HashMap<Integer, BigDecimal>();
		
		// Load previous data
		List<InterfaceInfo> listInterface = deviceMapper.selectInterfaceInfo(device.getDeviceId());
		if (listInterface != null) {
			for(InterfaceInfo prevInf : listInterface ) {
				
				// For check remove
				mapRemoved.add(prevInf.getIfNumber());
				
				// For check octet increase
				if ( prevInf.getLastOctet() != null ) {
					mapPrevOctet.put(prevInf.getIfNumber(), prevInf.getLastOctet());
				}
			}
		}
		
		HashMap<Integer, InterfaceInfo> mapInf = handler.collectInterface();
		if (mapInf == null)
			return;
		
		// Update to DB
		for(InterfaceInfo inf : mapInf.values()) {

			// Check removed
			mapRemoved.remove(inf.getIfNumber());
			
			// Check octet increase
			BigDecimal prevData = mapPrevOctet.get(inf.getIfNumber());
			if (prevData == null || inf.getLastOctet().compareTo( prevData ) > 0 ) {
				
				// Update time
				inf.setLastOctetUpdate( new Timestamp(System.currentTimeMillis()) );
			}
			
			int affected = deviceMapper.updateInterfaceInfo(inf);
			if (affected == 0) {
				deviceMapper.insertInterfaceInfo(inf);
				
				_logger.info((new StringBuilder())
						.append("[INSERT] Insert Interface Info")
						.append(" (device_id=").append(inf.getDeviceId())
						.append(", if_number=").append(inf.getIfNumber())
						.append(", if_name=").append(inf.getIfName())
						.append(", if_desc=").append(inf.getIfDesc())
						.append(")")
						.toString());
			}
		}
		
		// Delete removed data
		for(int removedIf : mapRemoved) {
			deviceMapper.deleteInterfaceInfo(device.getDeviceId(), removedIf);
			
			_logger.info((new StringBuilder())
					.append("[DELETE] Delete Interface Info")
					.append(" (device_id=").append(device.getDeviceId())
					.append(", if_number=").append(removedIf)
					.append(")")
					.toString());
		}
	}
	//endregion

	//region Collect Interface IP
	private void collectInterfaceIp(SnmpHandler handler, DeviceSnmp device, DeviceMapper deviceMapper) {
		
		HashMap<String, InterfaceIp> mapRemoved = new HashMap<String, InterfaceIp>();	// IfNumber|ifIpAddr
		
		// Load previous data
		List<InterfaceIp> listPrev = deviceMapper.selectInterfaceIp(device.getDeviceId());
		if (listPrev != null) {
			for(InterfaceIp prevData : listPrev) {
				mapRemoved.put(String.format("%d|%s", prevData.getIfNumber(), prevData.getIfIpaddr()), prevData);
			}
		}
		
		// Collect Interface IP
		List<InterfaceIp> listInfIp = handler.collectInterfaceIP();
		if (listInfIp == null)
			return;
		
		// Update to DB
		for(InterfaceIp ifIp : listInfIp) {
			
			//For check removed
			mapRemoved.remove(String.format("%d|%s", ifIp.getIfNumber(), ifIp.getIfIpaddr()));
			
			int affected = deviceMapper.updateInterfaceIp(ifIp);
			if (affected == 0) {
				deviceMapper.insertInterfaceIp(ifIp);
				
				_logger.info((new StringBuilder())
						.append("[INSERT] Insert Interface IP")
						.append(" (device_id=").append(ifIp.getDeviceId())
						.append(", if_number=").append(ifIp.getIfNumber())
						.append(", if_ipaddr=").append(ifIp.getIfIpaddr())
						.append(")")
						.toString());
			}
		}
		
		// Delete removed data
		for(InterfaceIp removed : mapRemoved.values()) {
			deviceMapper.deleteInterfaceIp(removed.getDeviceId(), removed.getIfNumber(), removed.getIfIpaddr());
			
			_logger.info((new StringBuilder())
					.append("[DELETE] Delete Interface IP")
					.append(" (device_id=").append(removed.getDeviceId())
					.append(", if_number=").append(removed.getIfNumber())
					.append(", ip_addr=").append(removed.getIfIpaddr())
					.append(")")
					.toString());
		}
	}
	//endregion
	
	//region Collect Interface CAM
	private void collectInterfaceCAM(SnmpHandler handler, DeviceSnmp device, DeviceMapper deviceMapper) {
		
		HashMap<String, InterfaceCam> mapRemoved = new HashMap<String, InterfaceCam>();	// IfNumber|ifIpAddr|MacAddr
		
		// Load previous data
		List<InterfaceCam> listPrev = deviceMapper.selectInterfaceCam(device.getDeviceId());
		for(InterfaceCam prevData : listPrev) {
			mapRemoved.put(
					String.format("%d|%s|%s", prevData.getIfNumber(), prevData.getIfNumber(), prevData.getMacaddr())
					, prevData);
		}
		
		// Collect CAM
		HashMap<String, InterfaceCam> mapCam = handler.collectCam();
		if (mapCam == null)
			return;
			
		// Update to DB
		for(InterfaceCam cam : mapCam.values()) {

			//For check removed
			mapRemoved.remove(String.format("%d|%s|%s", cam.getIfNumber(), cam.getIfNumber(), cam.getMacaddr()));
			
			int affected = deviceMapper.updateInterfaceCam(cam);
			if (affected == 0) {
				deviceMapper.insertInterfaceCam(cam);
			}
		}
		
		// Delete removed data
		for(InterfaceCam removed : mapRemoved.values()) {
			deviceMapper.deleteInterfaceCam(removed.getDeviceId(), removed.getIfNumber(), removed.getMacaddr());
		}
	}
	//endregion
}
