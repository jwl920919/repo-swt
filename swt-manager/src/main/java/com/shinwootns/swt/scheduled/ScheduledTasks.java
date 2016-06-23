package com.shinwootns.swt.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.stereotype.Component;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.swt.service.SyslogAnalyzer;

@Component
public class ScheduledTasks {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Bean
	public ScheduledExecutorFactoryBean scheduledExecutorService() {
		ScheduledExecutorFactoryBean bean = new ScheduledExecutorFactoryBean();
		bean.setPoolSize(5);
		return bean;
	}

	// fixedRate
    @Scheduled(fixedRate = 5000)
    public void monitorPoolStatus() {
    	
    	PoolStatus syslogPoolstatus = SyslogAnalyzer.getInstance().GetPoolStatus();
    	
    	//_logger.debug(String.format("[Syslog-Pool] %s", syslogPoolstatus.toString()));
    	System.out.println(String.format("[Syslog-Pool] %s", syslogPoolstatus.toString()));
    }
    
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
