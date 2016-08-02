package com.shinwootns.ipm.worker;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.google.gson.JsonArray;
import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.SiteInfo;
import com.shinwootns.data.entity.ViewLeaseIpStatus;
import com.shinwootns.data.entity.ViewNetworkIpStatus;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.data.status.NetworkIpStatus;
import com.shinwootns.data.status.DhcpCounter;
import com.shinwootns.data.status.DhcpDeviceStatus;
import com.shinwootns.data.status.DhcpVrrpStatus;
import com.shinwootns.data.status.DnsCounter;
import com.shinwootns.data.status.LeaseIpStatus;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.WorkerManager;
import com.shinwootns.ipm.data.mapper.DashboardMapper;
import com.shinwootns.ipm.data.mapper.DeviceMapper;
import com.shinwootns.ipm.service.redis.RedisHandler;

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
	
	private void UpdateDashboardData() {
		
		DashboardMapper dahsboardMapper = SpringBeanProvider.getInstance().getDashboardMapper();
		if (dahsboardMapper == null)
			return;
		
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper == null)
			return;
		
		List<SiteInfo> listSite = deviceMapper.selectSiteInfo();
		
		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if(redis == null)
			return;
		
		// Network IP Status
		UpdateNetworkIpStatus(dahsboardMapper, redis, listSite);
		
		// Lease IP Status (IPv4, IPv6)
		UpdateLeaseIpStatus(dahsboardMapper, redis, listSite, "IPV4", RedisKeys.KEY_DASHBOARD_LEASE_IPV4);
		UpdateLeaseIpStatus(dahsboardMapper, redis, listSite, "IPV6", RedisKeys.KEY_DASHBOARD_LEASE_IPV6);

		redis.close();
	}
	
	//region [FUNC] Update Network IP Status
	private void UpdateNetworkIpStatus(DashboardMapper dahsboardMapper, RedisClient redis, List<SiteInfo> listSite) {
		
		try
		{
			// Total
			List<ViewNetworkIpStatus> listStatus = dahsboardMapper.selectViewNetworkIpStatus(null);
			if (listStatus != null ) {
				
				NetworkIpStatus ipStatus = new NetworkIpStatus(); 
				
				for(ViewNetworkIpStatus data : listStatus) {
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
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region [FUNC] Update Lease IP Status
	private void UpdateLeaseIpStatus(DashboardMapper dahsboardMapper, RedisClient redis, List<SiteInfo> listSite, String ip_type, String redisKey) {
		
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
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
}
