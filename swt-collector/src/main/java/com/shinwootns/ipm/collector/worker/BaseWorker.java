package com.shinwootns.ipm.collector.worker;

public abstract class BaseWorker implements Runnable {

	private boolean stopFlag = false;

	abstract public void run();

	public boolean isStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
	}
}
