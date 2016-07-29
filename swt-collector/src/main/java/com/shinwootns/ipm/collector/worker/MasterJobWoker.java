package com.shinwootns.ipm.collector.worker;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DhcpIpStatus;
import com.shinwootns.data.entity.DhcpNetwork;
import com.shinwootns.data.entity.DhcpRange;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.data.status.DhcpCounter;
import com.shinwootns.data.status.DhcpDeviceStatus;
import com.shinwootns.data.status.DhcpVrrpStatus;
import com.shinwootns.data.status.DnsCounter;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.data.mapper.DataMapper;
import com.shinwootns.ipm.collector.service.cluster.ClusterManager;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;

public class MasterJobWoker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);
	
	// DHCP Handler
	DhcpHandler handler = null;

	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		// DHCP Handler
		if (handler == null)
			handler = new DhcpHandler();
		
		
		LinkedList<DhcpNetwork> listNetwork = collectDhcpNetwork();
		collectDhcpRange();
		for(DhcpNetwork network : listNetwork) {
			collectDhcpIpSatus(network);
		}
		
		
		// FixedDelay 10 seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						updateDhcpStatus();
					}
				}
				,0 ,10 ,TimeUnit.SECONDS
		);
		
		// wait
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		schedulerService.shutdown();
		
		_logger.info("MasterJobWoker... end.");
	}
	
	//region [FUNC] Collect DHCP Network
	public LinkedList<DhcpNetwork> collectDhcpNetwork() {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return null;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return null;
		
		LinkedList<DhcpNetwork> listNetwork = null;
		
		if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {

			listNetwork = handler.getDhcpNetwork(SharedData.getInstance().getSiteID());
			
			if (listNetwork != null) {
				DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
				if (dataMapper == null)
					return null;
					
				for(DhcpNetwork network : listNetwork) {
					
					if (network == null)
						continue;
					
					try
					{
						if (network.getNetwork().isEmpty() == false) {
							int affected = dataMapper.updateDhcpNetwork(network);
							
							if (affected == 0)
								affected = dataMapper.insertDhcpNetwork(network);
						}
					}
					catch(Exception ex) {
						_logger.error(ex.getMessage(), ex);
					}
				}
			}
		}
		handler.close();
		
		return listNetwork;
	}
	//endregion
	
	//region [FUNC] Collect DHCP Range
	public void collectDhcpRange() {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {

			LinkedList<DhcpRange> listRange = handler.getDhcpRange(SharedData.getInstance().getSiteID());
			
			if (listRange != null) {
				DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
				if (dataMapper == null)
					return;
					
				for(DhcpRange range : listRange) {
					
					if (range == null)
						continue;
					
					try
					{
						if (range.getNetwork().isEmpty() == false) {
							int affected = dataMapper.updateDhcpRange(range);
							
							if (affected == 0)
								affected = dataMapper.insertDhcpRange(range);
						}
					}
					catch(Exception ex) {
						_logger.error(ex.getMessage(), ex);
					}
				}
			}
		}
		handler.close();
	}
	//endregion
	
	//region [FUNC] Collect IP Status
	public void collectDhcpIpSatus(DhcpNetwork network) {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
		if (dataMapper == null)
			return;
		
		if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
			
			// Previous IP Status
			List<DhcpIpStatus> listPrevData = dataMapper.selectDhcpIpStatusByNetwork(SharedData.getInstance().getSiteID(), network.getNetwork());
			
			HashSet<String> setPrevIPAddr = new HashSet<String>(); 

			for(DhcpIpStatus prevIp : listPrevData) {
				if (setPrevIPAddr.contains(prevIp.getIpaddr()) == false )
					setPrevIPAddr.add(prevIp.getIpaddr());
			}
			

			// Collect IP Status
			LinkedList<DhcpIpStatus> listIpStatus = handler.getDhcpIpStatus(SharedData.getInstance().getSiteID(), network);
			
			if (listIpStatus != null) {
				
				for(DhcpIpStatus ipStatus : listIpStatus) {
					
					if (ipStatus == null)
						continue;
					
					try
					{
						// DELETE
						if ( (ipStatus.getMacaddr() == null || ipStatus.getMacaddr().isEmpty()) &&
								(ipStatus.getDuid() == null || ipStatus.getDuid().isEmpty()) &&
								(ipStatus.getIsConflict() == null || ipStatus.getIsConflict() == false) &&
								(ipStatus.getStatus() == null || ipStatus.getStatus().equals("UNUSED")) &&
								(ipStatus.getLeaseState() == null || ipStatus.getLeaseState().isEmpty() || ipStatus.getLeaseState().equals("FREE")) &&
								(ipStatus.getDiscoverStatus() == null || ipStatus.getDiscoverStatus().isEmpty() || ipStatus.getDiscoverStatus().equals("NONE")) &&
								(ipStatus.getObjTypes() == null || ipStatus.getObjTypes().isEmpty() 
									|| ipStatus.getObjTypes().equals("DHCP_RANGE"))
							) 
						{
							// Delete when previous data exist
							if (setPrevIPAddr.contains(ipStatus.getIpaddr())) {
								dataMapper.deleteDhcpIpStatus(ipStatus.getSiteId(), ipStatus.getIpaddr());
							}
						}
						else {
							// Update or Insert
							if (ipStatus.getIpaddr().isEmpty() == false) {
								
								int affected = dataMapper.updateDhcpIpStatus(ipStatus);
								
								if (affected == 0)
									affected = dataMapper.insertDhcpIpStatus(ipStatus);
							}
						}
					}
					catch(Exception ex) {
						_logger.error(ex.getMessage(), ex);
					}
				}
			} 
		}
	}
	
	//region [FUNC] Update DHCP
	public void updateDhcpStatus() {
		
		if ( ClusterManager.getInstance().isMaster() == false)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		RedisClient client = RedisHandler.getInstance().getRedisClient();
		if(client == null)
			return;

		if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {

			updateDhcpDeviceStatus(handler, client);
			updateDhcpVrrpStatus(handler, client);
			updateDhcpCounter(handler, client);
			updateDnsCounter(handler, client);
		}
		handler.close();
		
		client.close();
	}
	//endregion

	//region [FUNC] Update DHCP - Device Status
	private void updateDhcpDeviceStatus(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			DhcpDeviceStatus dhcpStatus = handler.getDeviceStatus();
			
			if (dhcpStatus != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dhcpStatus);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DEVICE)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);
				
				_logger.info("updateDhcpDeviceStatus()... OK");
			}
			
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion

	//region [FUNC] Update DHCP - VRRP Status
	private void updateDhcpVrrpStatus(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {
			
			DhcpVrrpStatus vrrpStatus = handler.getVRRPStatus();

			if (vrrpStatus != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(vrrpStatus);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_VRRP)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);

				_logger.info("updateDhcpVrrpStatus()... OK");
			}

		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region [FUNC] Update DHCP - DHCP Counter
	private void updateDhcpCounter(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			DhcpCounter dhcpCounter = handler.getDhcpCounter();
			
			if (dhcpCounter != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dhcpCounter);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DHCP_COUNTER)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);

				_logger.info("updateDhcpCount()... OK");
			}

		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region [FUNC] Update DHCP - DNS Counter
	private void updateDnsCounter(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			DnsCounter dnsCounter = handler.getDnsCounter();
			
			if (dnsCounter != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dnsCounter);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DNS_COUNTER)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);

				_logger.info("updateDnsCount()... OK");
			}

		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
}
