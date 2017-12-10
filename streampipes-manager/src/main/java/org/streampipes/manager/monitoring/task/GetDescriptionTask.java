package org.streampipes.manager.monitoring.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import org.streampipes.manager.monitoring.job.MonitoringUtils;
import org.streampipes.model.base.ConsumableStreamPipesEntity;
import org.streampipes.model.client.monitoring.TaskReport;

public class GetDescriptionTask extends TaskDefinition {
	
	private ConsumableStreamPipesEntity element;
	
	private static final String TASK_NAME = "HTTP Get Availability consul";
	
	public GetDescriptionTask(ConsumableStreamPipesEntity element)
	{
		super();
		this.element = element;
	}
	

	@Override
	public void executeBefore() {
		// TODO Auto-generated method stub
	}

	@Override
	public void executeAfter() {
		// TODO Auto-generated method stub
	}

	@Override
	public TaskReport defineTaskExecution() {
		try {
			int statusCode = MonitoringUtils.getHttpResponse(element.getUri()).getStatusLine().getStatusCode();
			if (statusCode == 200) {
				return successMsg(TASK_NAME);
			}
			else
			{
				return errorMsg(TASK_NAME, "Wrong status code");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return errorMsg(TASK_NAME, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return errorMsg(TASK_NAME, e.getMessage());
		}
	}
}
