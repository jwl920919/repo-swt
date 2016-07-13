package com.shinwootns.ipm.service.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterManager {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	// Singleton
	private static ClusterManager _instance = null;
	private ClusterManager() {}
	public static synchronized ClusterManager getInstance() {

		if (_instance == null) {
			_instance = new ClusterManager();
		}
		return _instance;
	}
	
	private boolean isMasterNode = false;
	
	public void setMasterNode(boolean isMasterNode) {
		
		// If changed cluster mode.
		if (this.isMasterNode != isMasterNode) {
			
			_logger.info( String.format("\n\n[CLUSTER] Changed Cluster Mode ==> %s !!!\n", (isMasterNode)? "MASTER":"SLAVE"));
			
			this.isMasterNode = isMasterNode;
		}
	}
}
