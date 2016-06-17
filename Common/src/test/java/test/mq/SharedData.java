package test.mq;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {

	// Singleton
	private static SharedData _instance;
	private SharedData() {}

	public static synchronized SharedData getInstance() {

		if (_instance == null) {
			_instance = new SharedData();
		}
		return _instance;
	}

	// ...
}
