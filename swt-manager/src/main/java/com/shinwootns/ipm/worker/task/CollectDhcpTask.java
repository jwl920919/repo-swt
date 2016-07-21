package com.shinwootns.ipm.worker.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.IPv4Range;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.entity.DhcpFixedIp;
import com.shinwootns.ipm.data.entity.DhcpIpStatus;
import com.shinwootns.ipm.data.entity.DhcpMacFilter;
import com.shinwootns.ipm.data.entity.DhcpNetwork;
import com.shinwootns.ipm.data.entity.DhcpRange;
import com.shinwootns.ipm.data.mapper.DhcpMapper;
import com.shinwootns.ipm.service.handler.InfobloxWAPIHandler;
import com.shinwootns.ipm.service.handler.InfobloxWAPIHandler.NextPageData;
import com.shinwootns.ipm.worker.BaseWorker;

public class CollectDhcpTask extends BaseWorker{
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private DeviceDhcp device;
	
	public CollectDhcpTask(DeviceDhcp device) {
		this.device = device;
	}

	@Override
	public void run() {
		if (this.device == null)
			return;
		
		try
		{
			collectDhcpData(
					this.device.getHost()
					, this.device.getWapiUserid()
					, CryptoUtils.Decode_AES128(this.device.getWapiPassword()));
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	
	public void collectDhcpData(String host, String userid, String password)
	{
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return;
		
		InfobloxWAPIHandler wapiHandler;
		
		try {
			
			wapiHandler = new InfobloxWAPIHandler(host, userid, password);

			JSONArray jArray;
			
			if (wapiHandler.Connect()) {
				
				// Collect Network
				HashSet<String> setNetwork = collectDhcpNetwork(wapiHandler, dhcpMapper);
				
				// Collect Range
				collectDhcpRange(wapiHandler, dhcpMapper);
				
				// Collect Fixed IP
				collectFixedIp(wapiHandler, dhcpMapper);

				// Collect Filter
				collectMacFilter(wapiHandler, dhcpMapper);
				
				// Collect IP Status
				collectIPStatus(wapiHandler, dhcpMapper, setNetwork);
			}
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	
	private HashSet<String> collectDhcpNetwork(InfobloxWAPIHandler wapiHandler, DhcpMapper dhcpMapper) {
		
		HashSet<String> setNetwork = new HashSet<String>(); 
		
		// Collect Network
		JSONArray jArray = wapiHandler.getNetworkInfo();
		
		if (jArray != null)
		for(Object obj : jArray) {
			String network = JsonUtils.getValueToString((JSONObject)obj, "network", "");
			if (network != null && network.isEmpty() == false)
				setNetwork.add(network);
		}

		insertDhcpNetwork(dhcpMapper, jArray);
		
		_logger.info(String.format("DHCP - Collect Network Info : %s.... OK (count:%d)", this.device.getHost(), jArray.size()));
		
		return setNetwork;
	}
	
	
	private void collectDhcpRange(InfobloxWAPIHandler wapiHandler, DhcpMapper dhcpMapper) {
		
		JSONArray jArray = wapiHandler.getRangeInfo();
		
		insertDhcpRange(dhcpMapper, jArray);
		
		_logger.info(String.format("DHCP - Collect Rage Info : %s.... OK (count:%d)", this.device.getHost(), jArray.size()));
	}
	
	
	private void collectFixedIp(InfobloxWAPIHandler wapiHandler, DhcpMapper dhcpMapper) {
		
		JSONArray jArray = wapiHandler.getFixedIPList();
		
		insertDhcpFixedIP(dhcpMapper, jArray);
		
		_logger.info(String.format("DHCP - Collect Fixed IP: %s.... OK (count:%d)", this.device.getHost(), jArray.size()));
	}
	
	private void collectMacFilter(InfobloxWAPIHandler wapiHandler, DhcpMapper dhcpMapper) {
		
		JSONArray jArray = wapiHandler.getFilterInfo();
		
		insertDhcpFilter(dhcpMapper, jArray);
		
		_logger.info(String.format("DHCP - Collect MacFilter: %s.... OK (count:%d)", this.device.getHost(), jArray.size()));
	}
	
	private void collectIPStatus(InfobloxWAPIHandler wapiHandler, DhcpMapper dhcpMapper, HashSet<String> setNetwork) {
		
		int splitCount = 100;
		int ipCount = 0;
		
		 for(String network : setNetwork) {
			 
			HashMap<String, DhcpIpStatus> mapIp = new HashMap<String, DhcpIpStatus>();
		
			// Collect IPv4
			NextPageData nextData1 = wapiHandler.getIPv4AddressFirst(splitCount, network);
			extractIpv4Data(nextData1, mapIp);
			
			while(nextData1 != null && nextData1.IsExistNextPage()) {
				
				nextData1 = wapiHandler.getIPv4AddressNext(splitCount, nextData1.nextPageID);
				extractIpv4Data(nextData1, mapIp);
			}
			
			/*
			// Collect IPv6
			NextPageData nextData2 = wapiHandler.getIPv6AddressFirst(splitCount, network);
			extractIpv6Data(nextData2, mapIp);
			
			while(nextData2 != null && nextData2.IsExistNextPage()) {
				
				nextData2 = wapiHandler.getIPv6AddressNext(splitCount, nextData2.nextPageID);
				extractIpv6Data(nextData2, mapIp);
			}*/
			
			// Collect Lease IP
			NextPageData nextData3 = wapiHandler.getLeaseIPFirst(splitCount, network);
			extractLeaseIpData(nextData3, mapIp);
			
			while(nextData3 != null && nextData3.IsExistNextPage()) {
				
				nextData3 = wapiHandler.getLeaseIPNext(splitCount, nextData3.nextPageID);
				extractLeaseIpData(nextData3, mapIp);
			}
			
			// Insert Data
			insertDhcpIPStatus(this.device.getSiteId(), network, dhcpMapper, mapIp);
			
			mapIp.clear();
		}
		
		

		/*
		NextPageData nextData = wapiHandler.getLeaseIPFirst(splitCount);
		
		if (nextData != null && nextData.jArrayData != null) {
			insertDhcpLeaseIP(dhcpMapper, nextData.jArrayData);
			leaseCount += nextData.jArrayData.size();
		}
		
		while(nextData != null && nextData.IsExistNextPage()) {
			
			nextData = wapiHandler.getLeaseIPNext(splitCount, nextData.nextPageID);
			
			if (nextData != null && nextData.jArrayData != null) {
				insertDhcpLeaseIP(dhcpMapper, nextData.jArrayData);
				leaseCount += nextData.jArrayData.size();
			}
		}
		*/
		
		_logger.info(String.format("DHCP - Collect Lease IP: %s.... OK (count:%d)", this.device.getHost(), ipCount));
	}
	
	
	private void extractIpv4Data(NextPageData nextData, HashMap<String, DhcpIpStatus> mapIp) {
		
		if (nextData == null || nextData.jArrayData == null)
			return;
		
		for(Object obj : nextData.jArrayData) {
			
			JSONObject jObj = (JSONObject)obj; 
			
			try
			{
				DhcpIpStatus ip = new DhcpIpStatus();
				ip.setSiteId(this.device.getSiteId());
				ip.setIpaddr(JsonUtils.getValueToString(jObj, "ip_address", ""));
				ip.setIpType("IPV4");
				ip.setNetwork(JsonUtils.getValueToString(jObj, "network", ""));
				ip.setMacaddr(JsonUtils.getValueToString(jObj, "mac_address", "").toUpperCase());
				ip.setIsConflict(JsonUtils.getValueToBoolean(jObj, "is_conflict", false));
				ip.setConflictTypes(JsonUtils.getValueToString(jObj, "conflict_types", ""));
				ip.setStatus(JsonUtils.getValueToString(jObj, "status", ""));
				ip.setLeaseState(JsonUtils.getValueToString(jObj, "lease_state", ""));
				ip.setObjTypes(JsonUtils.getValueToString(jObj, "types", ""));
				ip.setDiscoverStatus(JsonUtils.getValueToString(jObj, "discover_now_status", ""));
				ip.setUsage(JsonUtils.getValueToString(jObj, "usage", ""));
				ip.setHostname(JsonUtils.getValueToString(jObj, "names", ""));
				ip.setHostOs(JsonUtils.getValueToString(jObj, "discovered_data.os", ""));
				ip.setFingerprint(JsonUtils.getValueToString(jObj, "fingerprint", ""));
				
				long lastDiscoverd = JsonUtils.getValueToNumber((JSONObject)obj, "discovered_data.last_discovered", 0);
				if (lastDiscoverd > 0)
					ip.setLastDiscovered(TimeUtils.convertLongToTimestamp(lastDiscoverd * 1000));

				// Put Data
				if (mapIp.containsKey(ip.getIpaddr()) == false)
					mapIp.put(ip.getIpaddr(), ip);
				else
					_logger.warn("Check duplicated ipv4 address :" + ip.getIpaddr());
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	
	private void extractIpv6Data(NextPageData nextData, HashMap<String, DhcpIpStatus> mapIp) {
		
	}
	
	private void extractLeaseIpData(NextPageData nextData, HashMap<String, DhcpIpStatus> mapIp) {
		
		if (nextData == null || nextData.jArrayData == null)
			return;
		
		for(Object obj : nextData.jArrayData) {
			
			try
			{
				JSONObject jObj = (JSONObject)obj; 
				
				String ipAddr = JsonUtils.getValueToString(jObj, "address", "");
				if (ipAddr == null || ipAddr.isEmpty())
					continue;
				
				if (mapIp.containsKey(ipAddr))
				{
					DhcpIpStatus ip = mapIp.get(ipAddr);
					
					ip.setIsNeverEnds(JsonUtils.getValueToBoolean(jObj, "never_ends", false));
					ip.setIsNeverStart(JsonUtils.getValueToBoolean(jObj, "never_starts", false));
					
					long startTime = JsonUtils.getValueToNumber((JSONObject)obj, "starts", 0);
					if (startTime > 0)
						ip.setLeaseStartTime(TimeUtils.convertLongToTimestamp(startTime * 1000));
					
					long endTime = JsonUtils.getValueToNumber((JSONObject)obj, "ends", 0);
					if (endTime > 0)
						ip.setLeaseEndTime(TimeUtils.convertLongToTimestamp(endTime * 1000));
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	
	
	private void insertDhcpNetwork(DhcpMapper dhcpMapper, JSONArray jArray) {
		
		if (jArray == null) 
			return;
			
		for(Object obj : jArray) {
			
			try
			{
				DhcpNetwork network = new DhcpNetwork();
				network.setSiteId(this.device.getSiteId());
				network.setNetwork(JsonUtils.getValueToString((JSONObject)obj, "network", ""));
				network.setComment(JsonUtils.getValueToString((JSONObject)obj, "comment", ""));
				
				IPv4Range ipRange = NetworkUtils.getIPV4Range(network.getNetwork());
				
				if (ipRange != null) {
					
					String startIp = NetworkUtils.longToIPv4(ipRange.getStartIP());
					String endIp = NetworkUtils.longToIPv4(ipRange.getEndIP());
					
					network.setStartIp(startIp);
					network.setEndIp(endIp);
					
					if (network.getNetwork().isEmpty() == false) {
						int affected = dhcpMapper.updateDhcpNetwork(network);
						
						if (affected == 0)
							affected = dhcpMapper.insertDhcpNetwork(network);
					}
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	
	private void insertDhcpRange(DhcpMapper dhcpMapper, JSONArray jArray) {
		
		if (jArray == null) 
			return;
			
		for(Object obj : jArray) {
			
			try
			{
				DhcpRange range = new DhcpRange();
				range.setSiteId(this.device.getSiteId());
				range.setNetwork(JsonUtils.getValueToString((JSONObject)obj, "network", ""));
				range.setStartIp(JsonUtils.getValueToString((JSONObject)obj, "start_addr", ""));
				range.setEndIp(JsonUtils.getValueToString((JSONObject)obj, "end_addr", ""));
					
				if (range.getNetwork().isEmpty() == false) {
					int affected = dhcpMapper.updateDhcpRange(range);
					
					if (affected == 0)
						affected = dhcpMapper.insertDhcpRange(range);
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	
	private void insertDhcpFixedIP(DhcpMapper dhcpMapper, JSONArray jArray) {

		if (jArray == null) 
			return;
			
		for(Object obj : jArray) {
			
			try
			{
				DhcpFixedIp fixedIp = new DhcpFixedIp();
				fixedIp.setSiteId(this.device.getSiteId());
				fixedIp.setNetwork(JsonUtils.getValueToString((JSONObject)obj, "network", ""));
				fixedIp.setIpaddr(JsonUtils.getValueToString((JSONObject)obj, "ipv4addr", ""));
				fixedIp.setMacaddr(JsonUtils.getValueToString((JSONObject)obj, "mac", ""));
				fixedIp.setComment(JsonUtils.getValueToString((JSONObject)obj, "comment", ""));
				fixedIp.setDisable(JsonUtils.getValueToBoolean((JSONObject)obj, "disable", false));
				
				if (fixedIp.getIpaddr().isEmpty() == false) {
					int affected = dhcpMapper.updateDhcpFixedIp(fixedIp);
					
					if (affected == 0)
						affected = dhcpMapper.insertDhcpFixedIp(fixedIp);
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}

	private void insertDhcpFilter(DhcpMapper dhcpMapper, JSONArray jArray) {
		
		if (jArray == null)
			return;
			
		for(Object obj : jArray) {
			
			try
			{
				DhcpMacFilter filter = new DhcpMacFilter();
				filter.setSiteId(this.device.getSiteId());
				filter.setFilterName(JsonUtils.getValueToString((JSONObject)obj, "name", ""));
				
				if (filter.getFilterName().isEmpty() == false) {
					int affected = dhcpMapper.updateDhcpFilter(filter);
					
					if (affected == 0)
						affected = dhcpMapper.insertDhcpFilter(filter);
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	
	private void insertDhcpIPStatus(int site_id, String network, DhcpMapper dhcpMapper, HashMap<String, DhcpIpStatus> mapIp) {
		
		List<DhcpIpStatus> listPrevData = dhcpMapper.selectDhcpIpStatusByNetwork(site_id, network);
		
		HashSet<String> setPrevIPAddr = new HashSet<String>(); 

		for(DhcpIpStatus prevIp : listPrevData) {
			if (setPrevIPAddr.contains(prevIp.getIpaddr()) == false )
				setPrevIPAddr.add(prevIp.getIpaddr());
		}
		
		
		for(DhcpIpStatus ip : mapIp.values()) {
			
			try
			{
				if (ip.getIpaddr().isEmpty() == false) {
					
					
					// DELETE
					if ( (ip.getMacaddr() == null || ip.getMacaddr().isEmpty()) &&
							(ip.getDuid() == null || ip.getDuid().isEmpty()) &&
							(ip.getIsConflict() == null || ip.getIsConflict() == false) &&
							//(ip.getStatus() == null || ip.getStatus().equals("UNUSED")) &&
							(ip.getLeaseState() == null || ip.getLeaseState().isEmpty() || ip.getLeaseState().equals("FREE")) &&
							(ip.getDiscoverStatus() == null || ip.getDiscoverStatus().isEmpty() || ip.getDiscoverStatus().equals("NONE")) &&
							(ip.getObjTypes() == null || ip.getObjTypes().isEmpty() 
								|| ip.getObjTypes().equals("DHCP_RANGE"))
						) 
					{
						// Delete when previous data exist
						if (setPrevIPAddr.contains(ip.getIpaddr())) {
							dhcpMapper.deleteDhcpIpStatus(ip.getSiteId(), ip.getIpaddr());
						}
					}
					else
					{
						// UPDATE & INSERT
						int affected = dhcpMapper.updateDhcpIpStatus(ip);
						
						if (affected == 0)
							affected = dhcpMapper.insertDhcpIpStatus(ip);
					}
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
}
