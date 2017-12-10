package org.streampipes.pe.processors.esper.geo.durationofstay;

import org.streampipes.pe.processors.esper.geo.geofencing.GeofencingData;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.params.binding.EventProcessorBindingParams;

public class DurationOfStayParameters extends EventProcessorBindingParams {

	private GeofencingData geofencingData;
	
	private String latitudeMapping;
	private String longitudeMapping;
	private String partitionMapping;
	private String timestampMapping;

	public DurationOfStayParameters(DataProcessorInvocation invocationGraph,
			GeofencingData geofencingData, String latitudeMapping, String longitudeMapping, String partitionMapping, String timestampMapping) {
		super(invocationGraph);
		this.geofencingData = geofencingData;
		this.latitudeMapping = latitudeMapping;
		this.longitudeMapping = longitudeMapping;
		this.partitionMapping = partitionMapping;
		this.timestampMapping = timestampMapping;
	}


	public GeofencingData getGeofencingData() {
		return geofencingData;
	}


	public void setGeofencingData(GeofencingData geofencingData) {
		this.geofencingData = geofencingData;
	}


	public String getLatitudeMapping() {
		return latitudeMapping;
	}


	public String getLongitudeMapping() {
		return longitudeMapping;
	}


	public String getPartitionMapping() {
		return partitionMapping;
	}


	public void setPartitionMapping(String partitionMapping) {
		this.partitionMapping = partitionMapping;
	}


	public String getTimestampMapping() {
		return timestampMapping;
	}


	public void setTimestampMapping(String timestampMapping) {
		this.timestampMapping = timestampMapping;
	}
	
	
	
	
	
}
