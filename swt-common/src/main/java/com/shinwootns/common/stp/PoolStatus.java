package com.shinwootns.common.stp;

public class PoolStatus {
	
	private int activeCount = 0;
	private int poolSize = 0;
	private int taskQueueSize = 0;
	private int completedCount = 0;
	
	@Override
	public String toString() {
		return (new StringBuilder())
				.append("PoolStatus - Active:").append(activeCount)
				.append(", PoolSize:").append(poolSize)
				.append(", TaskQueue:").append(taskQueueSize)
				.append(", Completed:").append(completedCount)
				.toString();
	}
	
	public int getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public int getTaskQueueSize() {
		return taskQueueSize;
	}
	public void setTaskQueueSize(int taskQueueSize) {
		this.taskQueueSize = taskQueueSize;
	}
	public int getCompletedCount() {
		return completedCount;
	}
	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}
}
