package com.shinwootns.ipm.service.handler;

/*

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.query.api.ExecutionPlan;
import org.wso2.siddhi.query.api.annotation.Annotation;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.definition.Attribute.Type;
import org.wso2.siddhi.query.api.definition.StreamDefinition;

public class CEPManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());

	// Singleton
	private static CEPManager _instance = null;

	private CEPManager() {
	}

	public static synchronized CEPManager getInstance() {

		if (_instance == null) {
			_instance = new CEPManager();
		}
		return _instance;
	}

	private SiddhiManager _manager = new SiddhiManager();;

	public boolean createPlane(String planName) {
		
		if ( _manager.getExecutionPlanRuntime(planName) == null )
		{
			ExecutionPlan plan = new ExecutionPlan(planName);
			
			
			ExecutionPlanRuntime runtime = _manager.createExecutionPlanRuntime(plan); 
			
			if ( runtime == null ) 
				return false;
				
			runtime.start();
			
			return true;
		}
		
		return false;
	}
	
	public ExecutionPlanRuntime createPlanByQuery(String planQuery)
	{
		try
		{
			ExecutionPlanRuntime runtime = _manager.createExecutionPlanRuntime(planQuery);
			
			runtime.start();
			
			return runtime;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	
	public void shutdownExecutionPlan(String planName) {
		
		ExecutionPlanRuntime runtime = _manager.getExecutionPlanRuntime(planName);
		if (runtime != null) {
			runtime.shutdown();
		}
	}
	
	public void shutdown() {
        _manager.shutdown();
	}
	
	public Map<String,AbstractDefinition> getStreamDefinitionMap(ExecutionPlanRuntime runtime) {
		return runtime.getStreamDefinitionMap();
	}
	
	public boolean addCallback(ExecutionPlanRuntime runtime, String queryName, StreamCallback callback) {
		try
		{
			runtime.addCallback(queryName, callback);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public ExecutionPlanRuntime getExecutionPlanName(String planName) {
		return _manager.getExecutionPlanRuntime(planName);
	}
	
	public boolean validateExecutionPlay(String executionPlanQuery) {
		try
		{
			_manager.validateExecutionPlan(executionPlanQuery);
			
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void addStream(String planName, String streamName) {
	
		ExecutionPlanRuntime runtime = _manager.getExecutionPlanRuntime(planName);
		
		if (runtime != null) {
			
		}
	}
	
	public InputHandler getInputHandler(ExecutionPlanRuntime runtime, String streamName) {

		if (runtime == null)
			return null;
		
		return runtime.getInputHandler(streamName);
	}
	
	public void addData(String planName, String streamName, Object[] data) {
		try
		{
			ExecutionPlanRuntime runtime = getExecutionPlanName(planName);
			
			if (runtime == null)
				return;
			
			InputHandler inputHandler = runtime.getInputHandler(streamName);
	        
			if (inputHandler == null)
				return;
				
			inputHandler.send(data);
				
	        //inputHandler.send(new Object[]{"IBM", 700f, 100l});
	        //inputHandler.send(new Object[]{"WSO2", 60.5f, 200l});
	        //inputHandler.send(new Object[]{"GOOG", 50f, 30l});
	        //inputHandler.send(new Object[]{"IBM", 76.6f, 400l});
	        //inputHandler.send(new Object[]{"WSO2", 45.6f, 50l});
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
}

*/