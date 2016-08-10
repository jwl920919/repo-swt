package com.shinwootns.ipm.insight.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.shinwootns.common.datatype.ExtendedQueue;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceIp;
import com.shinwootns.data.entity.SiteInfo;

public class SharedData {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int MAX_SYSLOG_RECV_QUEUE_SIZE = 10000;

	// Syslog Queue
	public java.util.Queue<SyslogEntity> syslogQueue = new ConcurrentLinkedQueue<SyslogEntity>();
	
	// Dhcp Info
	public DeviceDhcp dhcpDevice = null;

	// SiteInfo
	private SiteInfo site_info = null;
	
	HashMap<String, Integer> mapDeviceIp = new HashMap<String, Integer>(); 
	
	//region Singleton
	private static SharedData _instance = null;
	private SharedData() {}
	public static synchronized SharedData getInstance() {

		if (_instance == null) {
			_instance = new SharedData();
		}
		return _instance;
	}
	//endregion
	
	//region [FUNC] SiteInfo
	public void setsiteInfo(SiteInfo site_info) {
		synchronized(this) 
		{
			this.site_info = site_info;
		}
	}
	
	public int getSiteID() {
		synchronized(this) 
		{
			if (this.site_info != null)
				return this.site_info.getSiteId();
		}
		return 0;
	}
	//endregion
	
	//region [FUNC] Add Syslog Data
	public boolean addSyslogData(SyslogEntity syslog) {
		
		boolean bResult = false;
		
		while(bResult == false)
		{
			bResult = SharedData.getInstance().syslogQueue.add(syslog);
			
			if (bResult)
				break;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		
		return bResult;
	}
	//endregion

	//region [FUNC] Pop Syslog Data
	public List<SyslogEntity> popSyslogList(int popCount, int timeout) throws InterruptedException {
		
		List<SyslogEntity> resultList = new ArrayList<SyslogEntity>();
		
		if (popCount < 1)
			popCount = 1000;
		
		int count=0;
		
		long startTime = TimeUtils.currentTimeMilis();
		
		while(count < popCount )
		{
			SyslogEntity syslog = SharedData.getInstance().syslogQueue.poll();
			
			if (syslog != null)
			{
				count++;
				resultList.add(syslog);
			}
			else {
				
				if ( TimeUtils.currentTimeMilis() - startTime > timeout )
					break;
				
				Thread.sleep(100);
			}
		}
		
		return resultList;
	}
	//endregion

	//region [FUNC] Set device IP
	
	public void setDeviceId(List<DeviceIp> listDhcpIp) {

		if (listDhcpIp.size() == 0)
			return;
		
		synchronized(mapDeviceIp) 
		{
			mapDeviceIp.clear();
			
			for(DeviceIp dhcpIp : listDhcpIp) {
			
				if (mapDeviceIp.containsKey(dhcpIp.getIpaddr()) == false) {
					mapDeviceIp.put(dhcpIp.getIpaddr(), dhcpIp.getDeviceId());
				}
			}
		}
	}
	
	//endregion
	
	//region [FUNC] Get Device Id
	public Integer getDeviceId(String ipAddr) {
	
		synchronized(mapDeviceIp) 
		{
			return mapDeviceIp.get(ipAddr);
		}
	}
	//endregion
	
}
