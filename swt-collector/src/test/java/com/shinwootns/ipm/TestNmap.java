package com.shinwootns.ipm;


import java.util.regex.Matcher;
import org.apache.commons.exec.CommandLine;
import org.junit.Test;

import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.common.utils.SystemUtils.CommandOutput;
import com.shinwootns.data.regex.RegexPatterns;

public class TestNmap {

	@Test
	public void testParseResult() {
		String output = "" 
				+"PORT     STATE  SERVICE\r\n"
				+"22/tcp   closed ssh\r\n"
				+"80/tcp   open   http\r\n"
				+"427/tcp  open   svrloc\r\n"
				+"443/tcp  open   https\r\n"
				+"902/tcp  open   iss-realsecure\r\n"
				+"5988/tcp closed wbem-http\r\n"
				+"5989/tcp open   wbem-https\r\n"
				+"8000/tcp open   http-al\nt\r\n"
				+"8080/tcp closed http-proxy\r\n"
				+"8100/tcp open   xprint-server\r\n"
				+"8300/tcp open   tmi\r\n"
				+"MAC Address: 00:0B:AB:56:08:52 (Advantech Technology (china) Co.)\r\n"
				+"Aggressive OS guesses: VMware ESXi Server 5.0 (96%), VMware ESXi Server 4.1 (95%), Crestron XPanel control system (92%), FreeBSD 7.0-RELEASE-p1 - 10.0-CURRENT (92%), NAS4Free (FreeBSD 9.1) (91%), Netgear DG834G WAP or Western Digital WD TV media player (91%), VMware ESX Server 4.0.1 (91%), FreeBSD 5.2.1-RELEASE (90%), FreeNAS 0.686 (FreeBSD 6.2-RELEASE) or VMware ESXi Server 3.0 - 4.0 (90%), FreeBSD 8.0-RELEASE (90%)\r\n"
				+"No exact OS matches for host (test conditions non-ideal).\r\n";
						
		String mac = "", vendor = "", os = "";
		
		Matcher match1 = RegexPatterns.NMAP_FINGERPRINT_MAC.matcher(output);
		if (match1.find()) {
			if (match1.groupCount() > 0) {
				for(int t=0; t<=match1.groupCount(); t++)
					System.out.println(String.format("[%d] %s", t, match1.group(t)));
			}
		}
		
		System.out.println("================");
		
		Matcher match2 = RegexPatterns.NMAP_FINGERPRINT_OS_DETAIL.matcher(output);
		if (match2.find()) {
			if (match2.groupCount() > 0) {
				for(int t=0; t<=match2.groupCount(); t++)
					System.out.println(String.format("[%d] %s", t, match2.group(t)));
			}
		}
		
		System.out.println("================");
		
		Matcher match3 = RegexPatterns.NMAP_FINGERPRINT_OS_GUESS.matcher(output);
		if (match3.find()) {
			if (match3.groupCount() > 0) {
				for(int t=0; t<=match3.groupCount(); t++) {
					System.out.println(String.format("[%d] %s", t, match3.group(t)));
				}
				
				System.out.println("================");
				
				String[] listOS = match3.group(1).split(",");
				
				if (listOS.length > 0)
				{
					for(int t1=0; t1<=listOS.length && t1 < 5; t1++) {
						System.out.println(String.format("[%d] %s", t1, listOS[t1].replaceAll("\\(\\d+%\\)", "").trim()));
					}
				}
			}
		}
		
		System.out.println("================");
	}

	
/*
	// Task Count
	private static final int TASK_COUNT = 20;
	
	// Task Pool
	private SmartThreadPool _taskPool = new SmartThreadPool();
	
	@Test
	public void testNamp() {
		
		_taskPool.createPool(TASK_COUNT, TASK_COUNT, TASK_COUNT);
		
		
		System.out.println("START !!!");

		int start = 209;
		int end = 209;
		
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
						
						for(int t=0; t<=match.groupCount(); t++)
							System.out.println(String.format("[%d] %s", t, match.group(t)));
						
						// MAC
						mac = match.group(1);
						
						// Vendor
						vendor = match.group(2);
						if (vendor != null)
							vendor = vendor.replace("(", "").replace(")", "").trim();
						
						// OS
						os = match.group(4);
					}
				}
			}
			
			System.out.println("["+ip+"] MAC=" + mac +", Vendor=" + vendor + ", OS=" + os);
		}
	}
	*/
}


