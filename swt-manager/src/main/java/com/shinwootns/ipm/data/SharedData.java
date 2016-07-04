package com.shinwootns.ipm.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONObject;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.TimeUtils;

public class SharedData {
	
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
	public java.util.Queue<JSONObject> syslogRecvQueue = new ConcurrentLinkedQueue<JSONObject>();
	
	
	// Add Syslog Task
	public boolean addSyslogData(JSONObject jobj) {
		
		boolean bResult = false;
		
		while(bResult == false)
		{
			bResult = syslogRecvQueue.add(jobj);
			
			if (bResult)
				break;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		
		return bResult;
	}
	
	public List<JSONObject> popSyslogList(int popCount, int timeout) {
		
		List<JSONObject> resultList = new ArrayList<JSONObject>();
		
		if (popCount < 1)
			popCount = 1000;
		
		int count=0;
		
		long startTime = TimeUtils.currentTimeMilis();
		
		while(count < popCount )
		{
			JSONObject data = syslogRecvQueue.poll();
			
			if (data != null)
			{
				count++;
				resultList.add(data);
			}
			else {
				
				if ( TimeUtils.currentTimeMilis() - startTime > timeout )
					break;
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
		
		return resultList;
	}
}
