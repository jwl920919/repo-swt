package com.shinwootns.common.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSingleton {
	
	private static final Map<Class<? extends AbstractSingleton>, AbstractSingleton> instances = 
			new HashMap<Class<? extends AbstractSingleton>, AbstractSingleton>();

	public static AbstractSingleton getInstance(Class<? extends AbstractSingleton> cls)
			throws InstantiationException, IllegalAccessException {
		
		if (instances.get(cls) == null) {
			synchronized (instances) {
				if (instances.get(cls) == null) {
					instances.put(cls, cls.newInstance());
				}
			}
		}
		return instances.get(cls);
	}
}
