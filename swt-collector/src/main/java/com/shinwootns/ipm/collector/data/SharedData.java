package com.shinwootns.ipm.collector.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.shinwootns.common.datatype.ExtendedQueue;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.data.entity.DeviceDhcp;

public class SharedData {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int MAX_SYSLOG_RECV_QUEUE_SIZE = 10000;
	
	// Singleton
	private static SharedData _instance = null;
	private SharedData() {}
	public static synchronized SharedData getInstance() {

		if (_instance == null) {
			_instance = new SharedData();
		}
		return _instance;
	}
	
	// Syslog Queue
	public java.util.Queue<SyslogEntity> syslogQueue = new ConcurrentLinkedQueue<SyslogEntity>();
	
	
	// Data
	public DeviceDhcp dhcpDevice = null;
}