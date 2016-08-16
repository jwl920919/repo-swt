package com.shinwootns.ipm.worker;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.IpCount;
import com.shinwootns.data.entity.SiteInfo;
import com.shinwootns.data.entity.ViewLeaseIpStatus;
import com.shinwootns.data.entity.ViewNetworkIpStatus;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.data.status.NetworkIpStatus;
import com.shinwootns.data.status.GuestIpStatus;
import com.shinwootns.data.status.LeaseIpStatus;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.mapper.DashboardMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.service.redis.RedisHandler;

import redis.clients.jedis.Jedis;

public class MasterJobWoker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);

	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		// FixedDelay 10 seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						UpdateDashboardData();
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
		
		schedulerService.shutdown();
		
		_logger.info("MasterJobWoker... end.");
	}
	
	//region [FUNC] UpdateDashboardData
	private void UpdateDashboardData() {
		
		DashboardMapper dahsboardMapper = SpringBeanProvider.getInstance().getDashboardMapper();
		if (dahsboardMapper == null)
			return;
		
		DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
		if (dataMapper == null)
			return;
		
		List<SiteInfo> listSite = dataMapper.selectSiteInfo();
		
		Jedis redis = RedisHandler.getInstance().getRedisClient();
		if(redis == null)
			return;
		
		_logger.info("UpdateDashboardData()... Start");
		
		try
		{
			// Network IP Status
			UpdateNetworkIpStatus(dahsboardMapper, redis, listSite);
			
			if (Thread.currentThread().isInterrupted())
				return;
			
			// Lease IP Status (IPv4)
			UpdateLeaseIpStatus(dahsboardMapper, redis, listSite, "IPV4", RedisKeys.KEY_DASHBOARD_LEASE_IPV4);
			
			if (Thread.currentThread().isInterrupted())
				return;
			
			// Lease IP Status (Pv6)
			UpdateLeaseIpStatus(dahsboardMapper, redis, listSite, "IPV6", RedisKeys.KEY_DASHBOARD_LEASE_IPV6);
			
			if (Thread.currentThread().isInterrupted())
				return;
			
			// Guest IP Status
			UpdateGuestIPCount(dahsboardMapper, redis, listSite, RedisKeys.KEY_DASHBOARD_GUEST_IP);
			
		}catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}finally {
			redis.close();
		}
		
		_logger.info("UpdateDashboardData()... end");
	}
	//endregion
	
	//region Update Network IP Status
	private void UpdateNetworkIpStatus(DashboardMapper dahsboardMapper, Jedis redis, List<SiteInfo> listSite) {
		
		try
		{
			// Total
			List<ViewNetworkIpStatus> listStatus = dahsboardMapper.selectViewNetworkIpStatus(null);
			if (listStatus != null ) {
				
				NetworkIpStatus ipStatus = new NetworkIpStatus(); 
				
				for(ViewNetworkIpStatus data : listStatus) {
					
					//System.out.println(data.getNetwork() + " : " + data.getRangeUsage().doubleValue());
					
					ipStatus.addIPStatus(data.getNetwork(), data.getRangeUsage().doubleValue());
				}
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);
				
				// Set value
				redis.set((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_NETWORK_IP).toString()
						, json
				);
			}
			
			if (listSite == null)
				return;
			
			for(SiteInfo site : listSite) {
				
				List<ViewNetworkIpStatus> listSiteStatus = dahsboardMapper.selectViewNetworkIpStatus(site.getSiteId());
				if (listSiteStatus == null )
					continue;
				
				NetworkIpStatus ipStatus = new NetworkIpStatus(); 
				
				for(ViewNetworkIpStatus data : listSiteStatus) {
					ipStatus.addIPStatus(data.getNetwork(), data.getRangeUsage().doubleValue());
				}
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);
				
				// Set value
				redis.set((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_NETWORK_IP)
						.append(":").append(site.getSiteId())
						.toString()
						, json
				);
			}
			
			_logger.info("UpdateNetworkIpStatus()... ok");
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region Update Lease IP Status
	private void UpdateLeaseIpStatus(DashboardMapper dahsboardMapper, Jedis redis, List<SiteInfo> listSite, String ip_type, String redisKey) {
		
		try
		{
			List<ViewLeaseIpStatus> listStatus = dahsboardMapper.selectViewLeaseIpStatus(null, ip_type);
			if (listStatus != null ) {

				LeaseIpStatus ipStatus = new LeaseIpStatus(); 
				for(ViewLeaseIpStatus data : listStatus) {
					ipStatus.addIPStatus(data.getTotalCount(), data.getFixedIp(), data.getLeaseIp(), data.getUnusedIp());
				}
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);
				
				// Set value
				redis.set((new StringBuilder())
						.append(redisKey).toString()
						, json
				);
			}
			
			if (listSite == null)
				return;
			
			for(SiteInfo site : listSite) {
				
				List<ViewLeaseIpStatus> listSiteStatus = dahsboardMapper.selectViewLeaseIpStatus(site.getSiteId(), ip_type);
				if (listSiteStatus == null )
					continue;
				
					
				LeaseIpStatus ipStatus = new LeaseIpStatus(); 
				
				for(ViewLeaseIpStatus data : listSiteStatus) {
					if (data.getTotalCount() != null && data.getFixedIp() != null && data.getLeaseIp() != null && data.getUnusedIp() != null)
						ipStatus.addIPStatus(data.getTotalCount(), data.getFixedIp(), data.getLeaseIp(), data.getUnusedIp());
				}
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);
				
				// Set value
				redis.set((new StringBuilder())
						.append(redisKey)
						.append(":").append(site.getSiteId())
						.toString()
						, json
				);
			}
			
			_logger.info((new StringBuilder()).append("UpdateLeaseIpStatus() - ").append(ip_type).append("... OK").toString());
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region Update Guest IP Count
	private void UpdateGuestIPCount(DashboardMapper dahsboardMapper, Jedis redis, List<SiteInfo> listSite, String redisKey) {
	
		try
		{
			IpCount ipCount = dahsboardMapper.selectGuestIpCount(null);
			if (ipCount != null ) {

				GuestIpStatus ipStatus = new GuestIpStatus(); 
				ipStatus.GUEST_IP.used = ipCount.getUsedCount();
				ipStatus.GUEST_IP.totoal = ipCount.getTotalCount();
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);
				
				// Set value
				redis.set((new StringBuilder())
						.append(redisKey).toString()
						, json
				);
			}
			
			if (listSite == null)
				return;
			
			for(SiteInfo site : listSite) {
				
				ipCount = dahsboardMapper.selectGuestIpCount(site.getSiteId());

				GuestIpStatus ipStatus = new GuestIpStatus();
				
				if (ipCount != null ) {
					ipStatus.GUEST_IP.used = ipCount.getUsedCount();
					ipStatus.GUEST_IP.totoal = ipCount.getTotalCount();
				}
				else {
					ipStatus.GUEST_IP.used = 0;
					ipStatus.GUEST_IP.totoal = 0;
				}
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);

				// Set value
				redis.set((new StringBuilder())
						.append(redisKey)
						.append(":").append(site.getSiteId())
						.toString()
						, json
				);
			}
			
			_logger.info("UpdateGuestIPCount()... ok");
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
}
