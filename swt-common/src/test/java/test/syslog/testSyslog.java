package test.syslog;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.network.SyslogManager;

public class testSyslog {

	/*
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		
		try
		{
			SyslogManager.getInstance().start(new testSyslogHandlerImpl());
			
			
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
			
			SyslogManager.getInstance().stop();
			
			sc.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	*/
}
