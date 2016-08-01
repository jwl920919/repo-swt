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
import com.shinwootns.data.entity.DhcpFixedIp;
import com.shinwootns.data.entity.DhcpIpStatus;
import com.shinwootns.data.entity.DhcpMacFilter;
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
	
	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		// collectDhcp info (60 sec)
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						collectDhcp();
					}
				}
				,0 ,60 ,TimeUnit.SECONDS
		);
		
		// updateDhcpStatus (10 sec)
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
	
	//region [FUNC] Collect Dhcp
	public void collectDhcp() {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		DhcpHandler handler = new DhcpHandler();
		if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
			
			// Collect Network
			LinkedList<DhcpNetwork> listNetwork = collectDhcpNetwork(handler);
			
			// Collect Range
			collectDhcpRange(handler);

			// Collect Filter
			collectMacFilter(handler);
			
			// Collect Fixed IP
			collectFixedIP(handler);
			
			// Collect IP Status
			for(DhcpNetwork network : listNetwork) {
				collectDhcpIpSatus(handler, network);
			}
		}
		handler.close();
	}
	//endregion
	
	//region [FUNC] Collect DHCP Network
	private LinkedList<DhcpNetwork> collectDhcpNetwork(DhcpHandler handler) {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return null;
		
		LinkedList<DhcpNetwork> listNetwork = handler.getDhcpNetwork(SharedData.getInstance().getSiteID());
		
		if (listNetwork != null) {
			DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
			if (dataMapper == null)
				return null;
			
			try
			{
				for(DhcpNetwork network : listNetwork) {
					if (network != null && network.getNetwork().isEmpty() == false) {
						int affected = dataMapper.updateDhcpNetwork(network);
						
						if (affected == 0)
							affected = dataMapper.insertDhcpNetwork(network);
					}
				}
				
				_logger.info("collectDhcpNetwork()... OK");
			}
			catch(Exception ex) {
				_logger.error("collectDhcpNetwork()... Failed");
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return listNetwork;
	}
	//endregion
	
	//region [FUNC] Collect DHCP Range
	private void collectDhcpRange(DhcpHandler handler) {
		
		LinkedList<DhcpRange> listRange = handler.getDhcpRange(SharedData.getInstance().getSiteID());
		
		if (listRange != null) {
			DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
			if (dataMapper == null)
				return;
				
			try
			{
				for(DhcpRange range : listRange) {
					if (range != null && range.getNetwork().isEmpty() == false) {
						
						int affected = dataMapper.updateDhcpRange(range);
						
						if (affected == 0)
							affected = dataMapper.insertDhcpRange(range);
					}
				}
				
				_logger.info("collectDhcpRange()... OK");
			}
			catch(Exception ex) {
				_logger.error("collectDhcpRange()... Failed");
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion
	
	//region [FUNC] Collect Mac Filter
	private void collectMacFilter(DhcpHandler handler) {
		
		LinkedList<DhcpMacFilter> listFilter = handler.getDhcpMacFilter(SharedData.getInstance().getSiteID());
		
		if (listFilter != null) {
			DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
			if (dataMapper == null)
				return;
			
			try
			{
				for(DhcpMacFilter filter : listFilter) {

					if (filter != null && filter.getFilterName().isEmpty() == false) {
					
						int affected = dataMapper.updateDhcpFilter(filter);
						
						if (affected == 0)
							affected = dataMapper.insertDhcpFilter(filter);
					}
				}
				
				_logger.info("collectMacFilter()... OK");
			}
			catch(Exception ex) {
				_logger.error("collectMacFilter()... Failed");
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion

	//region [FUNC] Collect Fixed IP
	private void collectFixedIP(DhcpHandler handler) {
		
		LinkedList<DhcpFixedIp> listFilter = handler.getDhcpFixedIP(SharedData.getInstance().getSiteID());
		
		if (listFilter != null) {
			DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
			if (dataMapper == null)
				return;
				
			try
			{
				for(DhcpFixedIp fixedIp : listFilter) {
				
					if (fixedIp != null && fixedIp.getIpaddr().isEmpty() == false) {
						int affected = dataMapper.updateDhcpFixedIp(fixedIp);
						
						if (affected == 0)
							affected = dataMapper.insertDhcpFixedIp(fixedIp);
					}
				}
				
				_logger.info("collectFixedIP()... OK");
			}
			catch(Exception ex) {
				_logger.error("collectFixedIP()... Failed");
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion
	
	//region [FUNC] Collect IP Status
	private void collectDhcpIpSatus(DhcpHandler handler, DhcpNetwork network) {
		
		DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
		if (dataMapper == null)
			return;
		
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

			try
			{
				for(DhcpIpStatus ipStatus : listIpStatus) {
				
				if (ipStatus == null)
					continue;
				
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
				
				_logger.info((new StringBuilder()).append("collectDhcpIpSatus(").append(network.getNetwork()).append(")... OK").toString());
			}
			catch(Exception ex) {
				_logger.error((new StringBuilder()).append("collectDhcpIpSatus(").append(network.getNetwork()).append(")... Failed").toString());
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion
	
	//region [FUNC] Update DHCP
	public void updateDhcpStatus() {
		
		if ( ClusterManager.getInstance().isMaster() == false)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		RedisClient client = RedisHandler.getInstance().getRedisClient();
		if(client == null)
			return;
		
		DhcpHandler handler = new DhcpHandler();
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
			_logger.error("updateDhcpDeviceStatus()... Failed");
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
			_logger.error("updateDhcpVrrpStatus()... Failedd");
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
			_logger.error("updateDhcpCount()... Failed");
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
			_logger.error("updateDnsCount()... Failed");
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
}
