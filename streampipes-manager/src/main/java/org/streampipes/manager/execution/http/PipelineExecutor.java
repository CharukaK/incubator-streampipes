package org.streampipes.manager.execution.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.lightcouch.DocumentConflictException;

import org.streampipes.manager.execution.status.PipelineStatusManager;
import org.streampipes.manager.execution.status.SepMonitoringManager;
import org.streampipes.manager.util.TemporaryGraphStorage;
import org.streampipes.model.client.pipeline.PipelineOperationStatus;
import org.streampipes.model.client.pipeline.PipelineStatusMessage;
import org.streampipes.model.client.pipeline.PipelineStatusMessageType;
import org.streampipes.model.base.InvocableStreamPipesEntity;
import org.streampipes.model.client.pipeline.Pipeline;
import org.streampipes.model.graph.DataSinkInvocation;
import org.streampipes.storage.controller.StorageManager;

public class PipelineExecutor {

	private Pipeline pipeline;
	private boolean visualize;
	private boolean storeStatus;
	private boolean monitor;
	
	public PipelineExecutor(Pipeline pipeline, boolean visualize, boolean storeStatus, boolean monitor)
	{
		this.pipeline = pipeline;
		this.visualize = visualize;
		this.storeStatus = storeStatus;
		this.monitor = monitor;
	}
	
	public PipelineOperationStatus startPipeline()
	{

		List<DataProcessorInvocation> sepas = pipeline.getSepas();
		List<DataSinkInvocation> secs = pipeline.getActions();

		List<InvocableStreamPipesEntity> graphs = new ArrayList<>();
		graphs.addAll(sepas);
		graphs.addAll(secs);

		graphs.forEach(g -> g.setStreamRequirements(Arrays.asList()));

		PipelineOperationStatus status = new GraphSubmitter(pipeline.getPipelineId(), pipeline.getName(), graphs).invokeGraphs();
		
		if (status.isSuccess()) 
		{
			storeInvocationGraphs(pipeline.getPipelineId(), graphs);
			
			PipelineStatusManager.addPipelineStatus(pipeline.getPipelineId(), 
					new PipelineStatusMessage(pipeline.getPipelineId(), System.currentTimeMillis(), PipelineStatusMessageType.PIPELINE_STARTED.title(), PipelineStatusMessageType.PIPELINE_STARTED.description()));
			
			if (monitor) SepMonitoringManager.addObserver(pipeline.getPipelineId());
			
			if (storeStatus) setPipelineStarted(pipeline);
		}
		return status;
	}
	
	public PipelineOperationStatus stopPipeline()
	{
		List<InvocableStreamPipesEntity> graphs = TemporaryGraphStorage.graphStorage.get(pipeline.getPipelineId());
		PipelineOperationStatus status = new GraphSubmitter(pipeline.getPipelineId(), pipeline.getName(), graphs).detachGraphs();
		
		if (status.isSuccess())
		{
			if (visualize) StorageManager.INSTANCE.getVisualizationStorageApi().deleteVisualization(pipeline.getPipelineId());
			if (storeStatus) setPipelineStopped(pipeline);
			
			PipelineStatusManager.addPipelineStatus(pipeline.getPipelineId(), 
					new PipelineStatusMessage(pipeline.getPipelineId(), System.currentTimeMillis(), PipelineStatusMessageType.PIPELINE_STOPPED.title(), PipelineStatusMessageType.PIPELINE_STOPPED.description()));
			
			if (monitor) SepMonitoringManager.removeObserver(pipeline.getPipelineId());
			
		}
		return status;
	}
	
	private void setPipelineStarted(Pipeline pipeline) {
		pipeline.setRunning(true);
		pipeline.setStartedAt(new Date().getTime());
		try {
			StorageManager.INSTANCE.getPipelineStorageAPI().updatePipeline(pipeline);
		} catch (DocumentConflictException dce)
		{
			//dce.printStackTrace();
		}
	}
	
	private void setPipelineStopped(Pipeline pipeline) {
		pipeline.setRunning(false);
		StorageManager.INSTANCE.getPipelineStorageAPI().updatePipeline(pipeline);
	}
	
	private void storeInvocationGraphs(String pipelineId, List<InvocableStreamPipesEntity> graphs)
	{
		TemporaryGraphStorage.graphStorage.put(pipelineId, graphs);
	}

}
