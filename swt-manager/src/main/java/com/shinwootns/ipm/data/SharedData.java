package com.shinwootns.ipm.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.datatype.ExtendedQueue;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.data.entity.EventLogEntity;

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
	public ExtendedQueue<JSONObject> syslogQueue = new ExtendedQueue<JSONObject>(MAX_SYSLOG_RECV_QUEUE_SIZE);
	
	// Event Queue
	public ExtendedQueue<EventLogEntity> eventQueue = new ExtendedQueue<EventLogEntity>(MAX_SYSLOG_RECV_QUEUE_SIZE);
}
