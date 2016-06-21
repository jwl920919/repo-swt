package com.shinwootns.swt.input;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SyslogServer extends Thread {
	
	private final static int PACKET_SIZE = 1024;
	private final static int SYSLOG_PORT = 514;
	
	@Override
	public void run() {
		
		DatagramSocket serverSocket = null;

		try {
			
			serverSocket = new DatagramSocket(SYSLOG_PORT);

			byte[] empty = new byte[PACKET_SIZE];
			byte[] buff = new byte[PACKET_SIZE];
			
			while (true) {
				System.arraycopy(empty, 0, buff, 0, buff.length);
				
				DatagramPacket receivePacket = new DatagramPacket(buff, buff.length);

				// Receive
				serverSocket.receive(receivePacket);

				// IP
				InetAddress ipAddr = receivePacket.getAddress();
				
				// Raw Syslog
				String rawSyslog = new String(receivePacket.getData()).trim();
				
				if (rawSyslog.isEmpty()) continue;
				if (rawSyslog.charAt(0) != '<') continue;
				
				int nIndex2 = rawSyslog.indexOf('>', 1);
				 
				String sPriority = rawSyslog.substring(1, nIndex2);
				
				int nPriority = Integer.parseInt( sPriority );
				
				int nFacility = (int)(nPriority / 7);
				int nSeverity = nPriority % 8;
				
				System.out.println(rawSyslog);
				
				
				// Put data queue
				/*
				RawSyslog syslog = new RawSyslog();
				syslog.setHost(ipAddr.getHostAddress().toString());
				syslog.setType("syslog");
				syslog.setData(sSyslog);
				syslog.setSeverity(nSeverity);
				syslog.setFacility(nFacility);
				syslog.setRecvTime(System.currentTimeMillis());
				
				CommonSyslog.queue_lock.lock();
				CommonSyslog.queue.add(syslog);
				CommonSyslog.queue_lock.unlock();

				// Update Stats
				CommonSyslog.rcv_lock.lock();
				if (CommonSyslog.rcv_count == 0)
					CommonSyslog.sampleMsg = syslog.getData();
				CommonSyslog.rcv_count++;
				CommonSyslog.rcv_lock.unlock();
				*/
			}
			
			//client.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (serverSocket != null)
				serverSocket.close();
		}
	}
}
