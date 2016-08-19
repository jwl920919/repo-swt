package com.shinwootns.ipm;

/*

import java.util.regex.Matcher;
import org.apache.commons.exec.CommandLine;
import org.junit.Test;

import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.common.utils.SystemUtils.CommandOutput;
import com.shinwootns.data.regex.RegexPatterns;

public class TestNmap {

	// Task Count
	private static final int TASK_COUNT = 20;
	
	// Task Pool
	private SmartThreadPool _taskPool = new SmartThreadPool();
	
	@Test
	public void testNamp() {
		
		_taskPool.createPool(TASK_COUNT, TASK_COUNT, TASK_COUNT);
		
		
		System.out.println("START !!!");

		int start = 1;
		int end = 255;
		
		int index = start;
		while(true) {

			try {
				
				String ip = String.format("192.168.1.%d", index);
				
				if ( _taskPool.addTask( new ScanNode(ip) ) == false ) {
					Thread.sleep(500);
					continue;
				}
				
				index++;
				
				if (index > end || index >= 255)
					break;
			
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		
		_taskPool.shutdownAndWait();
		
		System.out.println("END !!!");
	}
	
	public class ScanNode implements Runnable {

		private String ip = "";
		
		public ScanNode(String ip) {
			this.ip = ip;
		}
		
		@Override
		public void run() {

			// Command
			StringBuilder command = new StringBuilder();
			command.append("nmap \" -O -v ").append(ip).append("\"");
			
			String mac = "", vendor = "", os = "";
			
			// Execute Command
			CommandLine cmd = new CommandLine(command.toString());
			CommandOutput result = SystemUtils.executeCommand(cmd);
			
			if (result.output != null) {
				
				Matcher match = RegexPatterns.NMAP_FINGERPRINT.matcher(result.output);
				
				if (match.find()) {
					
					if (match.groupCount() > 0) {
						
						//for(int t=0; t<=match.groupCount(); t++)
						//	System.out.println(String.format("[%d] %s", t, match.group(t)));
						
						// MAC
						mac = match.group(1);
						
						// Vendor
						vendor = match.group(2);
						if (vendor != null)
							vendor = vendor.replace("(", "").replace(")", "").trim();
						
						// OS
						os = match.group(3);
					}
				}
			}
			
			System.out.println("["+ip+"] MAC=" + mac +", Vendor=" + vendor + ", OS=" + os);
		}
	}
}

*/