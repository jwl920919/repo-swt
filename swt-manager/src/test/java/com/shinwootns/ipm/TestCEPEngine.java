package com.shinwootns.ipm;
/*

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.tomcat.jni.Time;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.common.network.SyslogReceiveHandler;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.service.cep.CEPManager;

import test.syslog.testSyslogHandlerImpl;

public class TestCEPEngine {

	@Test
	public void testCEP() {
		
		BasicConfigurator.configure();
		
		String query = "" 
				+ " define stream dhcpStream (dhcptype string, ipaddr string, macaddr string);"
				+ ""
				+ " @info(name = 'dhcpResult')" 
				+ " from dhcpStream[dhcptype == 'DHCACK']#window.timeBatch(1 sec)"
				+ " select dhcptype, ipaddr, macaddr, count(*) as cnt"
				+ " group by dhcptype, ipaddr, macaddr"
				+ " having cnt > 2"
				+ " insert into outputStream;";
		
		try
		{
			
			boolean bCheck = CEPManager.getInstance().validateExecutionPlay(query);
			
			ExecutionPlanRuntime runtime = CEPManager.getInstance().createPlanByQuery(query);
			
			String sName = runtime.getName();
			//= CEPManager.getInstance().getExecutionPlanName("");
			
			DataProducer producer = null;// = new DataProducer(sName, "dhcpStream");
			
			CEPManager.getInstance().addCallback(runtime, "outputStream", new ResultCallback());
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("\n[Command List] : start, stop, exit\n");
			
			while(true)
			{
				String command = sc.nextLine().toLowerCase();
	
				if (command.toLowerCase().equals("exit"))
				{
					break;
				}
				else if (command.toLowerCase().equals("start"))
				{
					if (producer == null) {
						producer = new DataProducer(sName, "dhcpStream");
						producer.start();
					}
				}
				else if (command.toLowerCase().equals("stop"))
				{
					if (producer != null)
						producer.interrupt();
					
					producer = null;
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
		
		return;
		
	}
	
	class ResultCallback extends StreamCallback {

		@Override
		public void receive(Event[] events) {
			
			// Print
			EventPrinter.print(events);
		}
		
	}
	
	class DataProducer extends Thread {
		String planName;
		String streamName;
		
		public DataProducer(String planName, String streamName) {
			this.planName = planName;
			this.streamName = streamName;
		}
		
		@Override
		public void run() {
			
			List<Object> listData = new LinkedList<Object>();
			
			try {
				
				String dhcpType = "";
				
				while(this.interrupted() == false) {
					
					int nRandom = (new Random()).nextInt(100) + 1;
					
					if (nRandom % 7 == 0)
						dhcpType = "DHCPREQUEST";
					else if (nRandom % 7 == 1)
						dhcpType = "DHCPOFFER";
					else if (nRandom % 7 == 2)
						dhcpType = "DHCPDISCOVER";
					else if (nRandom % 7 == 3)
						dhcpType = "DHCPINFORM";
					else if (nRandom % 7 == 4)
						dhcpType = "DHCPACK";
					else
						dhcpType = "DHCACK";
					
					listData.add(dhcpType);									// dhcp_type
					listData.add( String.format("192.168.1.%d",nRandom));	// ipaddr
					listData.add("f0:24:75:96:47:69");						// macaddr
					
					CEPManager.getInstance().addData(planName, streamName, listData.toArray());
					
					listData.clear();
					
					Thread.sleep(0);
				}
			}
			//catch(InterruptedException ex) {
			//}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	};
}
*/