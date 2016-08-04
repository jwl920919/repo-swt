package com.shinwootns.common.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyslogServer extends Thread {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int PACKET_SIZE = 1024;
	private final static int SYSLOG_PORT = 514;
	
	private SyslogReceiveHandler _handler = null;
	private DatagramSocket _serverSocket = null;
	private boolean _stopFlag = false;
	
	
	public SyslogServer(SyslogReceiveHandler handler) {
		this._handler = handler;
	}
	
	public synchronized boolean bindSocket() {
		
		try
		{
			if (_serverSocket == null)
			{
				_serverSocket = new DatagramSocket(SYSLOG_PORT);
				
				_logger.info("bindSocket()... ok.");
				
				return true;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
			
			_logger.error("bindSocket()... failed.");
		}
		
		return false;
	}
	
	public synchronized void setStopFlag(boolean stopFlag) {
		this._stopFlag = stopFlag;
	}
	
	public synchronized boolean getStopFlag() {
		return this._stopFlag;
	}
	
	public synchronized void closeSocket() {
		
		if ( _serverSocket != null ) {
			
			try {
				_serverSocket.close();
			}
			catch(Exception ex) {}
			finally {
				_serverSocket = null;
			}
			
			try {
				this.wait();
			}
			catch(Exception ex) {}
			
			_logger.info("closeSocket()... ok.");
		}
	}
	
	private void receiveSyslog(SyslogReceiveHandler handler) {
		
		int nPriority= 0;
		int nFacility = 0;
		int nSeverity = 0;
		
		if (_serverSocket == null)
			return;
		
		byte[] buff = new byte[PACKET_SIZE];
		DatagramPacket receivePacket = new DatagramPacket(buff, buff.length);
		
		_logger.info("receiveSyslog()... start.");

		try {

			while (this.getStopFlag() == false) {
				
				// Initialize
				receivePacket.setLength(buff.length);
				
				// Receive
				_serverSocket.receive(receivePacket);
				
				try
				{
					// IP
					InetAddress ipAddr = receivePacket.getAddress();
					
					// Raw Syslog
					String rawSyslog = new String(receivePacket.getData()).trim();
					
					if (rawSyslog.isEmpty()) continue;
					//if (rawSyslog.charAt(0) != '<') continue;
					
					if ( rawSyslog.charAt(0) == '<' )
					{
						int nIndex2 = rawSyslog.indexOf('>', 1);
						
						if (nIndex2 > 0)
						{
							String priority = rawSyslog.substring(1, nIndex2);
							
							nPriority = Integer.parseInt( priority );
							
							nFacility = (int)(nPriority / 7);
							nSeverity = nPriority % 8;
						}
					}
					
					SyslogEntity syslog = new SyslogEntity();
					syslog.setHost(ipAddr.getHostAddress().toString());
					syslog.setData(rawSyslog);
					syslog.setSeverity(nSeverity);
					syslog.setFacility(nFacility);
					syslog.setRecvTime(System.currentTimeMillis());
					
					//System.out.println(String.format("[%s, %s] - %s", syslog.getHost(), TimeUtils.convertToStringTime(syslog.getRecvTime()), syslog.getData()));
					//LogUtils.WriteLog(_logger, Level.DEBUG , String.format("[%s, %s] - %s", syslog.getHost(), TimeUtils.convertToStringTime(syslog.getRecvTime()), syslog.getData()));
					
					if (handler != null)
					{
						handler.processSyslog(syslog);
					}
					
					/*
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
				catch(Exception ex) {
					_logger.error(ex.getMessage(), ex);
				}
			}
		
		} catch (SocketException ex1) {
			// Socket Close
		} catch (Exception ex2) {
			_logger.error(ex2.getMessage(), ex2);
		} finally {
			_logger.info("receiveSyslog()... stop.");
		}
	}
	
	@Override
	public void run() {
		
		setStopFlag(false);
		
		receiveSyslog(this._handler);
	}
	
}
