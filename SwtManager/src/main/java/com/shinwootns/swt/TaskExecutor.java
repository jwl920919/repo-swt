package com.shinwootns.swt;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutor {
	
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
//    // cron
//    @Scheduled(cron = "*/10 * * * * MON-FRI")
//    public void ScheduledCron() {
//    	
//    	try { Thread.sleep(1000); } 
//    	catch (InterruptedException e) {}
//    	
//        System.out.println("Scheduled-Cron : " + dateFormat.format(new Date()));
//    }
    
    // Async
}
