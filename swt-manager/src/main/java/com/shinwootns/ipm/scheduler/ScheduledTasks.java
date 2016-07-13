package com.shinwootns.ipm.scheduler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.service.handler.RedisHandler;

@Component
public class ScheduledTasks {
	
	//private final Logger _logger = Logger.getLogger(this.getClass());
	
	/*
	@Bean
	public ScheduledExecutorFactoryBean scheduledExecutorService() {
		ScheduledExecutorFactoryBean bean = new ScheduledExecutorFactoryBean();
		bean.setPoolSize(5);
		return bean;
	}*/
	
	// fixedDelay
	@Scheduled(fixedDelay = 5000)
	public void updateClusterInfo() {
		
		RedisHandler.getInstance().updateClusterMember();
		
	}
	
//	// fixedRate
//    @Scheduled(fixedRate = 10000)
//    public void monitorPoolStatus() {
//    	
//    	PoolStatus syslogPoolstatus = WorkerPoolManager.getInstance().GetPoolStatus();
//    	
//    	_logger.debug(String.format("[WorkerPool] %s", syslogPoolstatus.toString()));
//    }

//    // fixedDelay
//    @Scheduled(fixedDelay = 5000)
//    public void ScheduledFixedDelay() {
//    	
//    	try { Thread.sleep(1000); } 
//    	catch (InterruptedException e) {}
//    	
//        System.out.println("Scheduled-FixedDelay : " + dateFormat.format(new Date()));
//    }
//    
//    // Cron: 월~금, 매 10초 주기로
//    @Scheduled(cron = "*/10 * * * * MON-FRI")
//    public void ScheduledCron() {
//    	
//    	try { Thread.sleep(1000); } 
//    	catch (InterruptedException e) {}
//    	
//        System.out.println("Scheduled-Cron : " + dateFormat.format(new Date()));
//    }
	
//  // Cron: 매일 5시 30분 0초에 실행한다.
//    @Scheduled(cron = "0 30 5 * * *")
//    public void aJob() {
//
//        // 실행될 로직
//    }
//
//    // Cron: 매월 1일 0시 0분 0초에 실행한다.
//    @Scheduled(cron = "0 0 0 1 * *")
//    public void anotherJob() {
//
//        // 실행될 로직
//    }

}
