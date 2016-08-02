package com.shinwootns.ipm.worker;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.SiteInfo;
import com.shinwootns.data.entity.ViewNetworkIpStatus;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.data.status.DashboardNetworkIpStatus;
import com.shinwootns.data.status.DashboardNetworkIpStatus.NetworkIpStatus;
import com.shinwootns.data.status.DhcpCounter;
import com.shinwootns.data.status.DhcpDeviceStatus;
import com.shinwootns.data.status.DhcpVrrpStatus;
import com.shinwootns.data.status.DnsCounter;
import com.shinwootns.ipm.SpringBeanProvider;
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
	
	private void UpdateDashboardData() {
		
		DashboardMapper dahsboardMapper = SpringBeanProvider.getInstance().getDashboardMapper();
		if (dahsboardMapper == null)
			return;
		
		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if(redis == null)
			return;
		
		try {
		
			// Total
			List<ViewNetworkIpStatus> listStatus = dahsboardMapper.selectViewNetworkIpStatus(null);
			if (listStatus != null ) {
				
				DashboardNetworkIpStatus ipStatus = new DashboardNetworkIpStatus(); 
				
				for(ViewNetworkIpStatus data : listStatus) {
					ipStatus.addIPStatus(data.getNetwork(), data.getAllocUsage().doubleValue());
				}
				
				// Serialize to Json
				String json = JsonUtils.serialize(ipStatus);
				
				// Set value
				redis.set((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_NETWORK_IP).toString()
						, json
				);
			}
			
			// Site
			DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
			if (deviceMapper == null)
				return;
			
			List<SiteInfo> listSite = deviceMapper.selectSiteInfo();
			if (listSite != null) {
				for(SiteInfo site : listSite) {
					
					List<ViewNetworkIpStatus> listSiteStatus = dahsboardMapper.selectViewNetworkIpStatus(site.getSiteId());
					
					if (listSiteStatus != null ) {
						
						DashboardNetworkIpStatus ipStatus = new DashboardNetworkIpStatus(); 
						
						for(ViewNetworkIpStatus data : listSiteStatus) {
							ipStatus.addIPStatus(data.getNetwork(), data.getAllocUsage().doubleValue());
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
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		finally {
			redis.close();
			redis = null;
		}
	}
}
