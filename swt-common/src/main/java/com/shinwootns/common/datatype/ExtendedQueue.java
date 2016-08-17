package com.shinwootns.common.datatype;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.shinwootns.common.utils.TimeUtils;

public class ExtendedQueue<T> {
	
	// Thread safe.
	private LinkedBlockingQueue<T> _queue = null;

	//region - Constructor
	public ExtendedQueue(int maxSize) {
		_queue = new LinkedBlockingQueue<T>(maxSize);
	}
	//endregion
	
	//region - put
	public boolean put(T data) {
		return _queue.add(data);
	}
	//endregion
	
	//region - put All
	public boolean putAll(Collection<T> col) {
		return _queue.addAll(col);
	}
	//endregion
	
	//region - pop (Only one)
	public Object pop() {
		
		try {
			return _queue.poll(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return null;
		}
	}
	//endregion
	
	//region - pop All
	public LinkedList<T> popAll() {
		
		LinkedList<T> listData = new LinkedList<T>();
		
		while(true)
		{
			
			T data;
			try {
				data = _queue.poll(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				break;
			}
			
			if (data == null)
				break;

			listData.add(data);
		}
		
		return listData;
	}
	//endregion
	
	//region - pop (Multiple)
	public LinkedList<T> pop(int popCount) {
		
		LinkedList<T> listData = new LinkedList<T>();
		
		int count = 0;
		while(count < popCount )
		{
			
			T data;
			try {
				data = _queue.poll(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				break;
			}
			
			if (data == null)
				break;

			count++;
			listData.add(data);
		}
		
		return listData;
	}
	//endregion
	
	//region - pop (Multiple, Timeout)
	public LinkedList<T> pop(int popCount, int waitTime) throws InterruptedException {
		
		LinkedList<T> listData = new LinkedList<T>();
		
		long startTime = TimeUtils.currentTimeMilis();
		
		int count = 0;
		while(count < popCount )
		{
			T data;
			
			data = _queue.poll(100, TimeUnit.MILLISECONDS);
			
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
	//endregion
}
