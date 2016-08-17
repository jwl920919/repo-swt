package com.shinwootns.ipm.insight.controller;

import java.util.List;

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
import com.shinwootns.common.utils.SystemUtils.InterfaceIP;
import com.shinwootns.common.utils.SystemUtils.InterfaceInfo;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceIp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.SiteInfo;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.WorkerManager;
import com.shinwootns.ipm.insight.config.ApplicationProperty;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DeviceMapper;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.amqp.RabbitMQHandler;
import com.shinwootns.ipm.insight.service.redis.RedisManager;
import com.shinwootns.ipm.insight.service.syslog.SyslogReceiveHandlerImpl;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=true)
	private ApplicationProperty appProperty;
	
	@Autowired
	private ApplicationContext context;
	
	//region Start Service
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
		if ( RabbitMQHandler.getInstance().connect() == false ) {
			_logger.error("RabbitMQHandler connect()... Failed.");
			WorkerManager.getInstance().TerminateApplication();
			return;
		}
		
		// Connect Redis
		if (RedisManager.getInstance().connect() == false ) {
			_logger.error("RedisHandler connect()... Failed.");
			WorkerManager.getInstance().TerminateApplication();
			return;
		}
		
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
	//endregion
	
	//region StopService
	@PreDestroy
	public void stopService() {
		
		// Stop receive handler
		SyslogManager.getInstance().stop();
				
		// Stop Work Manager
		WorkerManager.getInstance().stop();
		
		// Close RabbitMQ
		RabbitMQHandler.getInstance().close();
		
		_logger.info("Stop Service Controller.");
	}
	//endregion
	
	//region [public] LoadConfig
	public boolean LoadConfig() {
		
		DhcpMapper dataMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (dataMapper == null || deviceMapper == null) {
			_logger.error("LoadConfig.. failed. (Mapper is null)");
			return false;
		}
		
		try
		{
			if ( SharedData.getInstance().LoadsiteInfo(deviceMapper, appProperty.siteCode) == false )
				return false;
			
			SharedData.getInstance().LoadConfigAll();

			// Get System Info
			String hostName = SystemUtils.getHostName();
			
			// Regist Insight Info
			if (hostName.isEmpty() == false && SharedData.getInstance().getSiteID() > 0) {
			
				// Device Insight
				DeviceInsight insight = new DeviceInsight();
				
				// Set Host name
				insight.setHost(hostName);
				
				// Get IP from config
				String localIP = SpringBeanProvider.getInstance().getApplicationProperty().local_ip;
				if (localIP != null && localIP.trim().length() > 0)
				{
					insight.setIpaddr(localIP);
				}
				else {
					
					// Get IP from interface
					List<InterfaceInfo> listInf = SystemUtils.getInterfaceInfo();
					if (listInf != null) {
						
						for(InterfaceInfo inf : listInf) {
							for(InterfaceIP ifip : inf.listIPAddr)  {
	
								if (ifip.ipaddr != null 
										&& ifip.ipaddr.length() > 0 
										&& ifip.ipaddr.indexOf("127.") != 0)
								{
									insight.setIpaddr(ifip.ipaddr);
									break;
								}
							}
							
							if (insight.getIpaddr() != null && insight.getIpaddr().length() > 0)
								break;
						}
					}
				}
				
				insight.setSiteId(SharedData.getInstance().getSiteID());
				insight.setPort(appProperty.serverPort);
				insight.setVersion(appProperty.version);
				insight.setClusterMode(appProperty.clusterMode);
				insight.setClusterIndex(appProperty.clusterIndex);
				
				// Update
				int affected = deviceMapper.updateInsight(insight);
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
					affected = deviceMapper.insertInsight(insight);
					
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
	//endregion
}
