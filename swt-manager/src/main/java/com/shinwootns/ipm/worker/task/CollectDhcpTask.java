package com.shinwootns.ipm.worker.task;

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
import com.shinwootns.ipm.data.entity.DhcpLeaseIp;
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
				jArray = wapiHandler.getNetworkInfo();

				insertDhcpNetwork(dhcpMapper, jArray);
				
				_logger.info(String.format("DHCP - Collect Network Info : %s.... OK (count:%d)", host, jArray.size()));
				
				// Collect Range
				jArray = wapiHandler.getRangeInfo();
				
				insertDhcpRange(dhcpMapper, jArray);
				
				_logger.info(String.format("DHCP - Collect Rage Info : %s.... OK (count:%d)", host, jArray.size()));
				
				// Collect Fixed IP
				jArray = wapiHandler.getFixedIPList();
				
				insertDhcpFixedIP(dhcpMapper, jArray);
				
				_logger.info(String.format("DHCP - Collect Fixed IP: %s.... OK (count:%d)", host, jArray.size()));

				// Collect Filter
				jArray = wapiHandler.getFilterInfo();
				
				insertDhcpFilter(dhcpMapper, jArray);
				
				// Collect Lease IP
				//jArray = wapiHandler.getLeaseIpAll(200);
				//insertDhcpLeaseIP(dhcpMapper, jArray);

				int splitCount = 200;
				int leaseCount = 0;
				
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
				
				_logger.info(String.format("DHCP - Collect Lease IP: %s.... OK (count:%d)", host, leaseCount));
			}
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
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
	
	private void insertDhcpLeaseIP(DhcpMapper dhcpMapper, JSONArray jArray) {
		
		if (jArray != null) {
			
			for(Object obj : jArray) {
				
				try
				{
					DhcpLeaseIp ip = new DhcpLeaseIp();
					ip.setSiteId(this.device.getSiteId());
					ip.setIpaddr(JsonUtils.getValueToString((JSONObject)obj, "address", ""));
					ip.setNetwork(JsonUtils.getValueToString((JSONObject)obj, "network", ""));
					ip.setIpType(JsonUtils.getValueToString((JSONObject)obj, "protocol", ""));
					ip.setMacaddr(JsonUtils.getValueToString((JSONObject)obj, "hardware", "").toUpperCase());
					ip.setHostname(JsonUtils.getValueToString((JSONObject)obj, "client_hostname", ""));
					
					// Binding State ( ABANDONED, ACTIVE, BACKUP, DECLINED, EXPIRED, FREE, OFFERED, RELEASED, RESET, STATIC)
					ip.setState(JsonUtils.getValueToString((JSONObject)obj, "binding_state", ""));
					
					ip.setUsername(JsonUtils.getValueToString((JSONObject)obj, "username", ""));
					
					long startTime = JsonUtils.getValueToNumber((JSONObject)obj, "starts", 0);
					if (startTime > 0)
						ip.setLeaseStartTime(TimeUtils.convertLongToTimestamp(startTime * 1000));
					
					long endTime = JsonUtils.getValueToNumber((JSONObject)obj, "ends", 0);
					if (endTime > 0)
						ip.setLeaseEndTime(TimeUtils.convertLongToTimestamp(endTime * 1000));
					
					long lastDiscoverd = JsonUtils.getValueToNumber((JSONObject)obj, "discovered_data.last_discovered", 0);
					if (lastDiscoverd > 0)
						ip.setLastDiscovered(TimeUtils.convertLongToTimestamp(lastDiscoverd * 1000));
					
					// Fingerprint ???
					//ip.setFingerprint(JsonUtils.getValueToString((JSONObject)obj, "fingerprint", "").toUpperCase());
					
					// OS ????
					//ip.setOs(JsonUtils.getValueToString((JSONObject)obj, "os", "").toUpperCase());
					
					// IPv6 duid
					ip.setDuid(JsonUtils.getValueToString((JSONObject)obj, "ipv6_duid", "").toUpperCase());
					
					if (ip.getIpaddr().isEmpty() == false) {
						int affected = dhcpMapper.updateDhcpLeaseIp(ip);
						
						if (affected == 0)
							affected = dhcpMapper.insertDhcpLeaseIp(ip);
					}
				}
				catch(Exception ex) {
					_logger.error(ex.getMessage(), ex);
				}
			}
		}
	}
}
