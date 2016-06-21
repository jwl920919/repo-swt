package com.shinwootns.swt.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//	// fixedRate
//    @Scheduled(fixedRate = 5000)
//    public void ScheduledFixedRate() {
//    	
//    	try { Thread.sleep(1000); } 
//    	catch (InterruptedException e) {}
//    	
//        System.out.println("Scheduled-FixedRate : " + dateFormat.format(new Date()));
//    }
//    
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