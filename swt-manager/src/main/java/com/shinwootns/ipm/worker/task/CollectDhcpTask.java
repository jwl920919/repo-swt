package com.shinwootns.ipm.worker.task;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.IPv4Range;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.entity.DhcpNetwork;
import com.shinwootns.ipm.data.entity.DhcpRange;
import com.shinwootns.ipm.data.mapper.DhcpMapper;
import com.shinwootns.ipm.service.handler.InfobloxWAPIHandler;
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
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return;
		
		
		InfobloxWAPIHandler wapiHandler;
		try {
			
			wapiHandler = new InfobloxWAPIHandler(
					this.device.getHost(), 
					this.device.getWapiUserid(), 
					CryptoUtils.Decode_AES128(this.device.getWapiPassword()));

			JSONArray jArray;
			
			if (wapiHandler.Connect()) {
				
				// Network
				jArray = wapiHandler.getNetworkInfo();
				
				insertDhcpNetwork(dhcpMapper, jArray);
				
				// Range
				jArray = wapiHandler.getRangeInfo();
				insertDhcpRange(dhcpMapper, jArray);

				// Filter
				jArray = wapiHandler.getFilterInfo();
				if (jArray != null)
					System.out.println(jArray.toJSONString());

				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertDhcpNetwork(DhcpMapper dhcpMapper, JSONArray jArray) {
		
		if (jArray != null) {
			
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
						
						int affected = dhcpMapper.updateDhcpNetwork(network);
						
						if (affected == 0) {
							affected = dhcpMapper.insertDhcpNetwork(network);
						}
					}
				}
				catch(Exception ex) {
					_logger.error(ex.getMessage(), ex);
				}
			}
		}
	}
	
	private void insertDhcpRange(DhcpMapper dhcpMapper, JSONArray jArray) {
		
		if (jArray != null) {
			
			for(Object obj : jArray) {
				
				try
				{
				
					DhcpRange range = new DhcpRange();
					range.setSiteId(this.device.getSiteId());
					range.setNetwork(JsonUtils.getValueToString((JSONObject)obj, "network", ""));
					range.setComment(JsonUtils.getValueToString((JSONObject)obj, "comment", ""));
					range.setStartIp(JsonUtils.getValueToString((JSONObject)obj, "start_addr", ""));
					range.setEndIp(JsonUtils.getValueToString((JSONObject)obj, "end_addr", ""));
						
					int affected = dhcpMapper.updateDhcpRange(range);
					
					if (affected == 0) {
						affected = dhcpMapper.insertDhcpRange(range);
					}
				}
				catch(Exception ex) {
					_logger.error(ex.getMessage(), ex);
				}
			}
		}
	}

}
