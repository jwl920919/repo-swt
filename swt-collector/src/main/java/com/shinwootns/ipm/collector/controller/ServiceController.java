package com.shinwootns.ipm.collector.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.SiteInfo;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.WorkerManager;
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.data.mapper.DataMapper;
import com.shinwootns.ipm.collector.service.amqp.RabbitmqHandler;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;
import com.shinwootns.ipm.collector.service.syslog.SyslogReceiveHandlerImpl;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=true)
	private ApplicationProperty appProperty;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private DataMapper dataMapper;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("Start Service Controller.");
		
		// Set BeanProvider
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperty( appProperty );
		
		_logger.info(appProperty.toString());
		
		// Load Config
		if ( LoadConfig() == false )
			return;
		
		// Connect RabbitMQ
		RabbitmqHandler.getInstance().connect();
		
		RedisHandler.getInstance().connect();
		
		// Start Work Manager
		WorkerManager.getInstance().start();

		if (appProperty.debugEnable == false || appProperty.enable_recv_syslog == true )
		{
			// Start receive handler
			if ( SyslogManager.getInstance().start(new SyslogReceiveHandlerImpl()) ) {
				_logger.info("SyslogManager.getInstance().start()... OK");
			}
			else {
				_logger.info("SyslogManager.getInstance().start()... failed");
				
				WorkerManager.getInstance().TerminateApplication();
			}
		}
	}
	
	@PreDestroy
	public void stopService() {
		
		// Stop receive handler
		SyslogManager.getInstance().stop();
				
		// Stop Work Manager
		WorkerManager.getInstance().stop();
		
		// Close RabbitMQ
		RabbitmqHandler.getInstance().close();
		
		_logger.info("Stop Service Controller.");
	}
	
	public boolean LoadConfig() {
		
		DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
		if (dataMapper == null) {
			_logger.error("LoadConfig.. failed (dataMapper is null");
			return false;
		}
		
		try
		{
			// Load Site Info
			SiteInfo siteInfo = dataMapper.selectSiteInfoByCode(appProperty.siteCode);
			if (siteInfo == null) {
				_logger.error( (new StringBuilder())
						.append("Failed get site info, SiteCode=").append(appProperty.siteCode)
						.toString()
				);
				return false;
			}
			
			_logger.info( (new StringBuilder())
					.append("[Site Info] SiteID=").append(siteInfo.getSiteId())
					.append(", SiteCode=").append(siteInfo.getSiteCode()) 
					.append(", SiteName=").append(siteInfo.getSiteName())
					.toString()
			);
			SharedData.getInstance().setsiteInfo( siteInfo );
			
			// Load DHCP
			DeviceDhcp dhcpInfo = dataMapper.selectDeviceDhcp(siteInfo.getSiteId());
			if (dhcpInfo != null ) {
				
				_logger.info( (new StringBuilder())
						.append("[DHCP Info] host=").append(dhcpInfo.getHost())
						.append(", WAPI user=").append(dhcpInfo.getWapiUserid()) 
						.toString()
				);
				
				// Decrypt password
				if ( dhcpInfo.getWapiPassword().isEmpty() == false ) {
					dhcpInfo.setWapiPassword( CryptoUtils.Decode_AES128(dhcpInfo.getWapiPassword()));
				}
				
				SharedData.getInstance().dhcpDevice = dhcpInfo;
			}
			
			String hostName = SystemUtils.getHostName();
			
			if (hostName.isEmpty() == false && siteInfo.getSiteId() > 0) {
			
				// Device Insight
				DeviceInsight insight = new DeviceInsight();
				insight.setHost(hostName);
				insight.setSiteId(siteInfo.getSiteId());
				insight.setPort(appProperty.serverPort);
				insight.setVersion(appProperty.version);
				insight.setClusterMode(appProperty.clusterMode);
				insight.setClusterIndex(appProperty.clusterIndex);
				
				// Update
				int affected = dataMapper.updateInsight(insight);
				if ( affected > 0) {

					// Update OK
					_logger.info((new StringBuilder())
							.append("[DB] Update device insight... ")
							.append(", siteId=").append(insight.getSiteId())
							.append(", host=").append(insight.getHost())
							.append(", port=").append(insight.getPort())
							.append(", version=").append(insight.getVersion())
							.toString()
							);
				}
				else {
					// Insert New
					affected = dataMapper.insertInsight(insight);
					
					if ( affected > 0) {
						// Insert OK
						_logger.info((new StringBuilder())
								.append("[DB] Insert device insight... ")
								.append(", siteId=").append(insight.getSiteId())
								.append(", host=").append(insight.getHost())
								.append(", port=").append(insight.getPort())
								.append(", version=").append(insight.getVersion())
								.toString()
								);
					}
				}
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return true;
	}
}
