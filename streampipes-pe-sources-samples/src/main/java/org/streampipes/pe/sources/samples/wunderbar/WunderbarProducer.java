package org.streampipes.pe.sources.samples.wunderbar;

import java.util.ArrayList;
import java.util.List;

import org.streampipes.container.declarer.EventStreamDeclarer;
import org.streampipes.container.declarer.SemanticEventProducerDeclarer;
import org.streampipes.model.graph.DataSourceDescription;

public class WunderbarProducer implements SemanticEventProducerDeclarer {

	@Override
	public DataSourceDescription declareModel() {
		DataSourceDescription sep = new DataSourceDescription("source-wunderbar", "Wunderbar Sensor Bar (1)", "Provides streams generated by Wunderbar IoT sensors (Wunderbar 1)");
		
		return sep;
	}

	@Override
	public List<EventStreamDeclarer> getEventStreams() {
		List<EventStreamDeclarer> streams = new ArrayList<>();
		streams.add(new AccelerometerStream(WunderbarVariables.ACCELEROMETER));
		streams.add(new AngularSpeedStream(WunderbarVariables.ANGULAR_SPEED));
		streams.add(new ColorStream(WunderbarVariables.COLOR));
		streams.add(new HumidityStream(WunderbarVariables.HUMIDITY));
		streams.add(new LuminosityStream(WunderbarVariables.LUMINOSITY));
		streams.add(new NoiseLevelStream(WunderbarVariables.NOISE_LEVEL));
		streams.add(new ProximityStream(WunderbarVariables.PROXIMITY));
		streams.add(new TemperatureStream(WunderbarVariables.TEMPERATURE));
		return streams;
	}

}
