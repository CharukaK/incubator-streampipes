package org.streampipes.pe.sources.kd2.streams;

import org.streampipes.model.schema.EventSchema;
import org.streampipes.model.SpDataStream;
import org.streampipes.model.schema.EventProperty;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.pe.sources.kd2.config.Kd2Variables;
import org.streampipes.pe.sources.kd2.utils.BiodataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riemer on 18.11.2016.
 */
public class BiodataStream extends AbstractBioDataStream {

    @Override
    public SpDataStream declareModel(DataSourceDescription sep) {
        SpDataStream stream = prepareStream(Kd2Variables.Biodata.topic());

        EventSchema schema = new EventSchema();
        List<EventProperty> eventProperties = new ArrayList<EventProperty>();
        eventProperties.addAll(getPreparedProperties());
        eventProperties.add(BiodataUtils.getHeartRateProperty());
        eventProperties.add(BiodataUtils.getEdaProperty());
        eventProperties.add(BiodataUtils.getArousalProperty());
        eventProperties.add(BiodataUtils.getPulseProperty());

        schema.setEventProperties(eventProperties);
        stream.setEventSchema(schema);
        stream.setName(Kd2Variables.Biodata.eventName());
        stream.setDescription(Kd2Variables.Biodata.description());
        stream.setUri(sep.getUri() + "/biodata");

        return stream;
    }
}
