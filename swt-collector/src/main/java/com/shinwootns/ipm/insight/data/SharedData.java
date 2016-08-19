package com.shinwootns.ipm.insight.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceIp;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.data.entity.DeviceSysOID;
import com.shinwootns.data.entity.DhcpMacFilter;
import com.shinwootns.data.entity.DhcpRange;
import com.shinwootns.data.entity.SiteInfo;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.mapper.DeviceMapper;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;

public class SharedData {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	// Syslog Queue
	public java.util.Queue<SyslogEntity> syslogQueue = new ConcurrentLinkedQueue<SyslogEntity>();
	
	// SysOID 
	private HashMap<String, DeviceSysOID> _mapSysOID = new HashMap<String, DeviceSysOID>();
	// Dhcp device
	private DeviceDhcp _dhcpDevice = null;
	// Network Device
	private HashMap<Integer, DeviceSnmp> mapDevice = new HashMap<Integer, DeviceSnmp>();
	// SiteInfo
	private SiteInfo _site_info = null;
	// Device IP - ID
	private HashMap<String, Integer> _mapDeviceIp = new HashMap<String, Integer>();
	// BlackList
	private DhcpMacFilter _blacklistFilter = null;
	// Guest Range
	private List<DhcpRange> _listGuestRange = new LinkedList<DhcpRange>();  
	
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
	
	public void LoadConfigAll() {
		
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper != null) {
			SharedData.getInstance().LoadDhcpDevice(deviceMapper);
			SharedData.getInstance().LoadNetworkDevice(deviceMapper);
			SharedData.getInstance().LoadDeviceIP(deviceMapper);
			SharedData.getInstance().LoadSysOID(deviceMapper);
		}
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper != null) {
			SharedData.getInstance().LoadGuestRange(dhcpMapper);
			SharedData.getInstance().LoadBlacklistMacFilter(dhcpMapper);
		}
	}

	//region - Add / Pop Syslog Data
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
	
	//region - Load / Get SiteInfo
	public boolean LoadsiteInfo(DeviceMapper deviceMapper, String siteCode) {
		
		// Load Site Info
		SiteInfo siteInfo = deviceMapper.selectSiteInfoByCode(siteCode);
		if (siteInfo == null) {
			_logger.error( (new StringBuilder())
					.append("Failed get site info, SiteCode=").append(siteCode)
					.toString()
			);
			return false;
		}
		
		_logger.info( (new StringBuilder())
				.append("Load siteInfo... SiteID=").append(siteInfo.getSiteId())
				.append(", SiteCode=").append(siteInfo.getSiteCode()) 
				.append(", SiteName=").append(siteInfo.getSiteName())
				.toString()
		);

		synchronized(this) 
		{
			this._site_info = siteInfo;
			
			return true;
		}
	}
	
	public int getSiteID() {
		synchronized(this) 
		{
			if (this._site_info != null)
				return this._site_info.getSiteId();
		}
		return 0;
	}
	//endregion
	
	//region - Load / Get Blacklist Filter
	public void LoadBlacklistMacFilter(DhcpMapper dhcpMapper) {
		
		List<DhcpMacFilter> listBlacklist = dhcpMapper.selectDhcpBlacklistFilter(SharedData.getInstance().getSiteID());
		if (listBlacklist == null)
			return;
		
		synchronized(this)
		{
			for(DhcpMacFilter blacklist : listBlacklist) {
				this._blacklistFilter = blacklist;
			}
		}
	}
	
	public String getBlacklistFilterName() {
		synchronized(this)
		{
			if (this._blacklistFilter != null)
				return this._blacklistFilter.getFilterName();
		}
		return null;
	}
	//endregion
	
	//region - Load / Get Dhcp Device
	public boolean LoadDhcpDevice(DeviceMapper deviceMapper) {

		// Load DHCP
		DeviceDhcp dhcpInfo = deviceMapper.selectDeviceDhcp(SharedData.getInstance().getSiteID());
		if (dhcpInfo == null) {
			_logger.error( (new StringBuilder())
					.append("Failed LoadDhcpDevice, SiteID=").append(SharedData.getInstance().getSiteID())
					.toString()
			);
			return false;
		}
		
		try {
			// Decrypt password
			if ( dhcpInfo.getWapiPassword().isEmpty() == false )
				dhcpInfo.setWapiPassword( CryptoUtils.Decode_AES128(dhcpInfo.getWapiPassword()));
			
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			return false;
		}
		
		_logger.info( (new StringBuilder())
				.append("Load DhcpDevice... host=").append(dhcpInfo.getHost())
				.append(", WAPI user=").append(dhcpInfo.getWapiUserid()) 
				.toString()
		);
		
		synchronized(this)
		{
			this._dhcpDevice = dhcpInfo;
		}
		
		return true;
	}
	
	public DeviceDhcp GetDhcpDevice() {
		synchronized(this)
		{
			return this._dhcpDevice;
		}
	}
	//endregion
	
	//region - Load / Get Network Device
	public void LoadNetworkDevice(DeviceMapper deviceMapper) {
		
		String hostName = SystemUtils.getHostName();
		
		List<DeviceSnmp> listDevice = deviceMapper.selectCollectNetworkDevice(SharedData.getInstance().getSiteID(), hostName);
		if (listDevice == null)
			return;
		
		_logger.info( (new StringBuilder()).append("Load Network Device.... Count:").append(listDevice.size()).toString() );
		
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
	
	public List<DeviceSnmp> getDeviceList() throws CloneNotSupportedException
	{
		List<DeviceSnmp> listDevice = new LinkedList<DeviceSnmp>();
		
		synchronized(mapDevice) 
		{
			for(DeviceSnmp device : this.mapDevice.values()) {
				listDevice.add( device.clone() );
			}
		}
		
		return listDevice;
	}
	//endregion
	
	//region - Load / Get Device IP
	public boolean LoadDeviceIP(DeviceMapper deviceMapper) {

		List<DeviceIp> listDeviceIp = deviceMapper.selectDeviceIP(SharedData.getInstance().getSiteID());
		if (listDeviceIp == null )
			return false;
		
		_logger.info( (new StringBuilder())
				.append("Load DeviceIP... count=").append(listDeviceIp.size())
				.toString()
		);
		
		synchronized(this._mapDeviceIp) 
		{
			this._mapDeviceIp.clear();
			
			for(DeviceIp dhcpIp : listDeviceIp) {
			
				if (this._mapDeviceIp.containsKey(dhcpIp.getIpaddr()) == false) {
					this._mapDeviceIp.put(dhcpIp.getIpaddr(), dhcpIp.getDeviceId());
				}
			}
		}
		
		return true;
	}
	
	public Integer getDeviceIDByIP(String ipAddr) {
	
		synchronized(this._mapDeviceIp) 
		{
			return this._mapDeviceIp.get(ipAddr);
		}
	}
	//endregion
	
	//region - Load / Get SysOID, Find Vendor & Model
	public void LoadSysOID(DeviceMapper deviceMapper) {

		List<DeviceSysOID> listSysOID = deviceMapper.selectSysOID();
		if (listSysOID == null)
			return;
		
		_logger.info( 
				(new StringBuilder())
				.append("Load SysOID.... Count=").append(listSysOID.size())
				.toString() 
		);
		
		synchronized(this._mapSysOID)
		{
			for(DeviceSysOID sysOID :listSysOID ) {
				this._mapSysOID.put(sysOID.getSysoid(), sysOID);
			}
		}
	}
 
	public DeviceSysOID FindVendorAndModel(String sysOid) {

		String key = sysOid;
		
		synchronized(this._mapSysOID)
		{
			DeviceSysOID data = this._mapSysOID.get(key);
			if ( data != null)
				return data;
			
			while(true) {

				int index = key.lastIndexOf(".");
				if (index <= 0)
					break;
				
				key = key.substring(0, index);
				
				data = this._mapSysOID.get(key+".%");
			
				if ( data != null)
					return data;
			}
		}
		
		return null;
	}
	//endregion

	//region - Load / Get Guest Range
	
	public void LoadGuestRange(DhcpMapper dhcpMapper) {
		
		List<DhcpRange> guestRange = dhcpMapper.selectDhcpGuestRange(getSiteID());
		if (guestRange == null)
			return;
		
		_logger.info( (new StringBuilder())
				.append("Load Guest Range... count=").append(guestRange.size())
				.toString()
		);
		
		synchronized(this._listGuestRange)
		{
			this._listGuestRange.clear();
			
			this._listGuestRange = guestRange;
		}
	}
	
	public boolean isGuestRange(BigInteger ip) {
		
		synchronized(this._listGuestRange)
		{
			for(DhcpRange range : this._listGuestRange) {
				if ( range.getStartNum().compareTo(ip) <= 0 && ip.compareTo(range.getEndNum()) <= 0 )
					return true;
			}
		}
		
		return false;
	}
	
	//endregion
}
