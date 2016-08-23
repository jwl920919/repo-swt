package com.shinwootns.ipm.insight.worker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.ClientInfo;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DhcpFixedIp;
import com.shinwootns.data.entity.DhcpIpStatus;
import com.shinwootns.data.entity.DhcpMacFilter;
import com.shinwootns.data.entity.DhcpNetwork;
import com.shinwootns.data.entity.DhcpRange;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.data.status.DhcpCounter;
import com.shinwootns.data.status.DnsCounter;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.ClientMapper;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.cluster.ClusterManager;
import com.shinwootns.ipm.insight.service.infoblox.DhcpHandler;
import com.shinwootns.ipm.insight.service.redis.RedisManager;

import redis.clients.jedis.Jedis;

public class MasterJobWoker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);
	
	//private Thread _syslogAggrWorker = null;
	
	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		/*
		// Start syslogPublisher
		if (_syslogAggrWorker == null) {
			_syslogAggrWorker = new Thread(new SyslogAggrWorker(), "SyslogAggrWorker");
			_syslogAggrWorker.start();
		}*/
		
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
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		// End syslogPublisher
		/*
		if (_syslogAggrWorker != null) {
			try {
				_syslogAggrWorker.interrupt();
				_syslogAggrWorker.join();
			}catch(Exception ex ) {
			}finally{
				_syslogAggrWorker = null;
			}
		}
		*/
		
		schedulerService.shutdown();
		
		_logger.info("MasterJobWoker... end.");
	}
	
	//region [public] Collect Dhcp
	public void collectDhcp() {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().GetDhcpDevice();
		if (dhcp == null)
			return;
		
		DhcpHandler handler = new DhcpHandler();

		_logger.info("collectDhcp()... Start");
		
		try
		{
			if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
				
				// Collect Network
				LinkedList<DhcpNetwork> listNetwork = collectDhcpNetwork(handler);
				
				Thread.sleep(300);
				
				if (Thread.currentThread().isInterrupted())
					return;
				
				// Collect Range
				collectDhcpRange(handler);
				
				Thread.sleep(300);
	
				if (Thread.currentThread().isInterrupted())
					return;
				
				// Collect Filter
				collectMacFilter(handler);
				
				Thread.sleep(300);
				
				if (Thread.currentThread().isInterrupted())
					return;
				
				// Collect Fixed IP
				collectFixedIP(handler);
				
				Thread.sleep(300);
				
				if (Thread.currentThread().isInterrupted())
					return;
				
				// Collect IP Status
				for(DhcpNetwork network : listNetwork) {
					
					if (Thread.currentThread().isInterrupted())
						return;
					
					Thread.sleep(300);
					
					collectDhcpIpSatus(handler, network);
				}
				
				listNetwork.clear();
			}
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			handler.close();
		}
		
		_logger.info("collectDhcp()... End");
	}
	//endregion
	
	//region - Collect DHCP Network
	private LinkedList<DhcpNetwork> collectDhcpNetwork(DhcpHandler handler) {
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return null;
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return null;
		
		// Collect DHCP Network
		LinkedList<DhcpNetwork> listNetwork = handler.getDhcpNetwork(SharedData.getInstance().getSiteID());
		
		if (listNetwork == null && listNetwork.size() == 0)
			return null;
		
		HashSet<String> setRemoved = new HashSet<String>();
		
		// Load previous data
		List<DhcpNetwork> listPrev = dhcpMapper.selectDhcpNetworkBySiteId(SharedData.getInstance().getSiteID());
		for(DhcpNetwork prev : listPrev) {
			setRemoved.add(prev.getNetwork());
		}
		
		// Update to DB
		for(DhcpNetwork network : listNetwork) {
			
			setRemoved.remove(network.getNetwork());
			
			try
			{
				if (network != null && network.getNetwork().isEmpty() == false) {

					int affected = dhcpMapper.updateDhcpNetwork(network);
					
					if (affected == 0) {
						affected = dhcpMapper.insertDhcpNetwork(network);
						
						_logger.info((new StringBuilder())
								.append("[INSERT] Insert DHCP Network")
								.append(" (site_id=").append(network.getSiteId())
								.append(", network=").append(network.getNetwork())
								.append(")")
								.toString());
					}
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
				_logger.error(network.toString());
			}
		}
		
		// Delete removed network
		for(String removed : setRemoved) {
			dhcpMapper.deleteDhcpNetwork(SharedData.getInstance().getSiteID(), removed);
			
			_logger.info((new StringBuilder())
					.append("[DELETE] Delete DHCP Network ")
					.append(" (network=").append(removed).append(")")
					.toString());
		}
		
		_logger.info("collectDhcpNetwork()... OK");
		
		return listNetwork;
	}
	//endregion
	
	//region - Collect DHCP Range
	private void collectDhcpRange(DhcpHandler handler) {
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return;
		
		HashMap<String, DhcpRange> setRemoved = new HashMap<String, DhcpRange>();
		// Load previous data
		List<DhcpRange> listPrev = dhcpMapper.selectDhcpRange(SharedData.getInstance().getSiteID());
		for(DhcpRange prev : listPrev) {
			setRemoved.put(prev.getStartIp(), prev);
		}
		
		// Collect DHCP Range
		LinkedList<DhcpRange> listRange = handler.getDhcpRange(SharedData.getInstance().getSiteID());
		if (listRange == null || listRange.size() == 0)
			return;
		
		// Update to DB
		for(DhcpRange range : listRange) {

			try
			{
				setRemoved.remove(range.getStartIp());
				
				if (range != null && range.getNetwork().isEmpty() == false) {
					
					int affected = dhcpMapper.updateDhcpRange(range);
					if (affected == 0) {
						affected = dhcpMapper.insertDhcpRange(range);
						
						_logger.info((new StringBuilder())
								.append("[INSERT] Insert DHCP Range")
								.append(" (site_id=").append(range.getSiteId())
								.append(", network=").append(range.getNetwork())
								.append(", start=").append(range.getStartIp())
								.append(", end=").append(range.getEndIp())
								.append(")")
								.toString());
					}
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		_logger.info("collectDhcpRange()... OK");

		// Delete removed range
		for(DhcpRange removed : setRemoved.values()) {
			dhcpMapper.deleteDhcpRange(SharedData.getInstance().getSiteID(), removed.getNetwork(), removed.getStartIp());
			
			_logger.info((new StringBuilder())
					.append("[DELETE] Delete DHCP Range ")
					.append(" (site_id:").append(removed.getSiteId())
					.append(", network=").append(removed.getNetwork())
					.append(", start_ip=").append(removed.getStartIp())
					.append(")")
					.toString());
		}
	}
	//endregion
	
	//region - Collect Mac Filter
	private void collectMacFilter(DhcpHandler handler) {
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return;
		
		HashSet<String> setRemoved = new HashSet<String>();
		// Load previous data
		List<DhcpMacFilter> listPrev = dhcpMapper.selectDhcpFilter(SharedData.getInstance().getSiteID());
		if (listPrev != null) {
			for(DhcpMacFilter prev : listPrev) {
				setRemoved.add(prev.getFilterName());
			}
		}
		
		// Collect Mac Filter
		LinkedList<DhcpMacFilter> listFilter = handler.getDhcpMacFilter(SharedData.getInstance().getSiteID());
		if (listFilter == null) 
			return;
		

		// Update to DB
		for(DhcpMacFilter filter : listFilter) {

			setRemoved.remove(filter.getFilterName());
			
			try
			{
				if (filter != null && filter.getFilterName().isEmpty() == false) {
				
					int affected = dhcpMapper.updateDhcpFilter(filter);
					
					if (affected == 0) {
						affected = dhcpMapper.insertDhcpFilter(filter);
						
						_logger.info((new StringBuilder())
								.append("[INSERT] Insert DHCP Mac filter")
								.append(" (site_id=").append(filter.getSiteId())
								.append(", name=").append(filter.getFilterName())
								.append(", desc=").append(filter.getFilterDesc())
								.append(")")
								.toString());
					}
				}
			}
			catch(Exception ex) {
				_logger.error("collectMacFilter()... Failed");
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		_logger.info("collectMacFilter()... OK");
		
		// Delete removed filter
		for(String removed : setRemoved) {
			
			dhcpMapper.deleteDhcpFilter(SharedData.getInstance().getSiteID(), removed);
			
			_logger.info((new StringBuilder())
					.append("[DELETE] Delete Mac Filter ")
					.append(" (site_id:").append(SharedData.getInstance().getSiteID())
					.append(", filter_name=").append(removed)
					.append(")")
					.toString());
		}
		
	}
	//endregion

	//region - Collect Fixed IP
	private void collectFixedIP(DhcpHandler handler) {
		
		LinkedList<DhcpFixedIp> listFilter = handler.getDhcpFixedIP(SharedData.getInstance().getSiteID());
		
		if (listFilter != null) {
			DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
			if (dhcpMapper == null)
				return;
				
			try
			{
				for(DhcpFixedIp fixedIp : listFilter) {
				
					if (fixedIp != null && fixedIp.getIpaddr().isEmpty() == false) {
						int affected = dhcpMapper.updateDhcpFixedIp(fixedIp);
						
						if (affected == 0) {
							affected = dhcpMapper.insertDhcpFixedIp(fixedIp);
							
							_logger.info((new StringBuilder())
									.append("[INSERT] Insert DHCP Fixed IP")
									.append(" (ip=").append(fixedIp.getIpaddr())
									.append(", ip_type=").append(fixedIp.getIpType())
									.append(", comment=").append(fixedIp.getComment())
									.append(")")
									.toString());
						}
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
	
	//region - Collect IP Status
	private void collectDhcpIpSatus(DhcpHandler handler, DhcpNetwork network) {
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return;

		HashSet<String> setPrevIPAddr = new HashSet<String>();
		
		// Previous IP Status
		List<DhcpIpStatus> listPrevData = dhcpMapper.selectDhcpIpStatusByNetwork(SharedData.getInstance().getSiteID(), network.getNetwork());
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
							dhcpMapper.deleteDhcpIpStatus(ipStatus.getSiteId(), ipStatus.getIpaddr());
						}
					}
					else {
						// Update or Insert
						if (ipStatus.getIpaddr().isEmpty() == false) {
							
							int affected = dhcpMapper.updateDhcpIpStatus(ipStatus);
							if (affected == 0) {
								affected = dhcpMapper.insertDhcpIpStatus(ipStatus);
							}
						}
						
						/*
						// Update Client Info
						if (ipStatus.getMacaddr() != null && ipStatus.getMacaddr().length() > 0)
						{
							ClientMapper clientMapper = SpringBeanProvider.getInstance().getClientMapper();
							if (clientMapper == null)
								return;
							
							// Client Info
							ClientInfo clientInfo = new ClientInfo();
							clientInfo.setMacaddr(ipStatus.getMacaddr());
							clientInfo.setIpType(ipStatus.getIpType());
							clientInfo.setLastIp(ipStatus.getIpaddr());
							clientInfo.setLastIpNum(ipStatus.getIpNum());
							
							if (ipStatus.getFingerprint() != null && ipStatus.getFingerprint().length() > 0)
								clientInfo.setOs(ipStatus.getFingerprint());
							
							if (ipStatus.getHostname() != null && ipStatus.getHostname().length() > 0)
								clientInfo.setHostname(ipStatus.getHostname());
							
							if (ipStatus.getDuid() != null && ipStatus.getDuid().length() > 0)
								clientInfo.setDuid(ipStatus.getDuid());
							
							int affected = clientMapper.updateClientInfo(clientInfo);
							if (affected == 0)
								affected = clientMapper.insertClientInfo(clientInfo);
						}
						*/
					}
				}
				
				
				
				// Update Client Info from ip_status
				ClientMapper clientMapper = SpringBeanProvider.getInstance().getClientMapper();
				if (clientMapper == null)
					return;
				
				int affected = clientMapper.updateClientInfoFromIpStatus(network.getSiteId(), network.getNetwork());
				
				_logger.info((new StringBuilder()).append("Update Client Info. Count=").append(affected).toString());
				
				
				_logger.info((new StringBuilder()).append("collectDhcpIpSatus(").append(network.getNetwork()).append(")... OK").toString());
			}
			catch(Exception ex) {
				_logger.error((new StringBuilder()).append("collectDhcpIpSatus(").append(network.getNetwork()).append(")... Failed").toString());
				_logger.error(ex.getMessage(), ex);
			}
			
			setPrevIPAddr.clear();
			listPrevData.clear();
			listIpStatus.clear();
		}
	}
	//endregion
	
	//region [FUNC] Update DHCP
	public void updateDhcpStatus() {
		
		if ( ClusterManager.getInstance().isMaster() == false)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().GetDhcpDevice();
		if (dhcp == null)
			return;
		
		Jedis client = RedisManager.getInstance().getRedisClient();
		if(client == null)
			return;

		DhcpHandler handler = new DhcpHandler();
		
		try
		{
			if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
			
				updateDhcpMemberInfo(handler, client);
				
				if (Thread.currentThread().isInterrupted())
					return;
				
				updateDhcpCounter(handler, client);
				
				if (Thread.currentThread().isInterrupted())
					return;
				
				updateDnsCounter(handler, client);
			}
			
		}catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			handler.close();
		}
		
		client.close();
	}
	//endregion

	//region - Update Member Info
	private void updateDhcpMemberInfo(DhcpHandler handler, Jedis client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			JsonArray jArray = handler.getDhcpMemberInfo();
			
			if (jArray != null) {
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DEVICE)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, jArray.toString()
				);
				
				_logger.info("updateDhcpDeviceStatus()... OK");
			}
			
		} catch(Exception ex) {
			_logger.error("updateDhcpDeviceStatus()... Failed");
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	/*
	//region [FUNC] Update DHCP - Device Status
	private void updateDhcpDeviceStatus(DhcpHandler handler, Jedis client) {

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
	private void updateDhcpVrrpStatus(DhcpHandler handler, Jedis client) {

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
	*/
	
	//region [FUNC] Update DHCP - DHCP Counter
	private void updateDhcpCounter(DhcpHandler handler, Jedis client) {

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
	private void updateDnsCounter(DhcpHandler handler, Jedis client) {

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
