package com.shinwootns.ipm.collector.worker;

public abstract class BaseThread implements Runnable {

	private Thread thisThread;
	private String threadName;

	final private static int STATE_INIT = 0x1;
	final private static int STATE_STARTED = 0x1 << 1;
	final private static int STATE_SUSPENDED = 0x1 << 2;
	final private static int STATE_STOPPED = 0x1 << 3;

	private volatile int stateCode = STATE_INIT;

	public BaseThread() {
	}

	public BaseThread(String threadName) {
		this.threadName = threadName;
	}
	
	public void start() {
		
		synchronized (this) {
			
			if (stateCode != STATE_INIT)
				throw new IllegalStateException("already started");

			thisThread = new Thread(this);

			if (threadName != null)
				thisThread.setName(threadName);
			
			thisThread.start();
			
			stateCode = STATE_STARTED;
		}
	}

	public void stop() {
		synchronized (this) {
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			this.stateCode = STATE_STOPPED;
			thisThread.interrupt();
		}
	}

	public void resume() {
		synchronized (this) {
			
			if (stateCode == STATE_STARTED || stateCode == STATE_INIT)
				return;
			
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			
			stateCode = STATE_STARTED;
			thisThread.interrupt(); // 꼭 해줘야 한다.
		}
	}

	public void suspend() {
		synchronized (this) {
			if (stateCode == STATE_SUSPENDED)
				return;
			if (stateCode == STATE_INIT)
				throw new IllegalStateException("not started yet");
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			stateCode = STATE_SUSPENDED;
		}
	}

	public void run() {
        while ( true ) {
            while ( stateCode == STATE_SUSPENDED) {
                try {
                    System.out.println("[handle] suspending...");
                    Thread.sleep(24 * 60 * 60 * 1000);
                } catch (InterruptedException e) {
                    if ( stateCode != STATE_SUSPENDED){
                        System.out.println("[handle] resuming...");
                        break;
                    }
                }
            }
            if ( stateCode == STATE_STOPPED){
                System.out.println("[handle] stopping...");
                break;
            }
            
            processJob();
        }
	}
	
	public boolean isStopped() {
		if ( stateCode != STATE_STARTED )
			return true;
		
		return false;
	}
	public boolean isSuspended() {
		if ( stateCode == STATE_SUSPENDED )
			return true;
		
		return false;
	}

	abstract public void processJob();
}
