package test.syslog;

import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.shinwootns.common.network.SyslogManager;

public class testSyslog {
	
	private static final Logger logger = Logger.getLogger(testSyslog.class);
	
	public static void main(String[] args) {
		
		try
		{
			BasicConfigurator.configure();
			
			SyslogManager.getInstance(logger).start(new SyslogHandlerImpl());
			
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("\n[Command List] : exit\n");
			
			while(true)
			{
				String command = sc.nextLine().toLowerCase();
	
				if (command.toLowerCase().equals("exit"))
				{
					break;
				}
				else {
					System.out.println("\n[Command List] : start, stop, exit\n");
				}
			}
			
			SyslogManager.getInstance(logger).stop();
			
			sc.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
