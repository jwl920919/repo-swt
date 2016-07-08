package com.shinwootns.common.datatype;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import com.shinwootns.common.utils.TimeUtils;

public class ExtendedQueue<T> {
	
	// Thread safe.
	private LinkedBlockingQueue<T> _queue = null;

	public ExtendedQueue(int maxSize) {
		_queue = new LinkedBlockingQueue<T>(maxSize);
	}
	
	public boolean put(T data) {
		return _queue.add(data);
	}
	
	public boolean putAll(Collection<T> col) {
		return _queue.addAll(col);
	}
	
	public Object pop() {
		return _queue.poll();
	}
	
	public LinkedList<T> popAll() {
		
		LinkedList<T> listData = new LinkedList<T>();
		
		while(true)
		{
			T data = _queue.poll();
			
			if (data == null)
				break;

			listData.add(data);
		}
		
		return listData;
	}
	
	public LinkedList<T> pop(int popCount) {
		
		LinkedList<T> listData = new LinkedList<T>();
		
		int count = 0;
		while(count < popCount )
		{
			T data = _queue.poll();
			
			if (data == null)
				break;

			count++;
			listData.add(data);
		}
		
		return listData;
	}
	
	public LinkedList<T> pop(int popCount, int waitTime) {
		
		LinkedList<T> listData = new LinkedList<T>();
		
		long startTime = TimeUtils.currentTimeMilis();
		
		int count = 0;
		while(count < popCount )
		{
			T data = _queue.poll();
			
			if (data != null)
			{
				count++;
				listData.add(data);
			}
			else {
				
				if ( TimeUtils.currentTimeMilis() - startTime > waitTime )
					break;
				
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {}
			}
		}
		
		return listData;
	}
}
