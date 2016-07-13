package com.shinwootns.ipm.collector.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.stereotype.Component;

import com.shinwootns.common.stp.PoolStatus;

@Component
public class ScheduledTasks {

	/*

	private final Logger _logger = Logger.getLogger(this.getClass());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Bean
	public ScheduledExecutorFactoryBean scheduledExecutorService() {
		ScheduledExecutorFactoryBean bean = new ScheduledExecutorFactoryBean();
		bean.setPoolSize(5);
		return bean;
	}

	// fixedRate
    @Scheduled(fixedRate = 10000)
    public void monitorPoolStatus() {
    	
    	PoolStatus syslogPoolstatus = WorkerPoolManager.getInstance().GetPoolStatus();
    	
    	_logger.info(String.format("[WorkerPool] %s", syslogPoolstatus.toString()));
    }
	*/
	
	// fixedRate
    @Scheduled(fixedRate = 10000)
    public void monitorPoolStatus() {
    }
}
