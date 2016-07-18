package com.shinwootns.ipm.worker.task;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.IPv4Range;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.entity.DhcpNetwork;
import com.shinwootns.ipm.data.mapper.DhcpMapper;
import com.shinwootns.ipm.service.handler.InfobloxWAPIHandler;
import com.shinwootns.ipm.worker.BaseWorker;

public class CollectDhcpTask extends BaseWorker{
	
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
				
				if (jArray != null) {
					
					for(Object obj : jArray) {
						
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
					
					//System.out.println(jArray.toJSONString());
				}

				System.out.println("================== Grid Info");

				jArray = wapiHandler.getGridInfo();
				if (jArray != null)
					System.out.println(jArray.toJSONString());

				System.out.println("================== Filter List");

				jArray = wapiHandler.getFilterInfo();
				if (jArray != null)
					System.out.println(jArray.toJSONString());

				System.out.println("================== Range Info");

				jArray = wapiHandler.getRangeInfo();
				if (jArray != null)
					System.out.println(jArray.toJSONString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
