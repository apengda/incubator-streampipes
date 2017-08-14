package org.streampipes.pe.sources.samples.wunderbar;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.streampipes.commons.Utils;
import org.streampipes.container.declarer.EventStreamDeclarer;
import org.streampipes.model.impl.EventGrounding;
import org.streampipes.model.impl.EventStream;
import org.streampipes.model.impl.TransportFormat;
import org.streampipes.model.impl.eventproperty.EventPropertyPrimitive;
import org.streampipes.model.impl.graph.SepDescription;
import org.streampipes.model.impl.quality.MeasurementCapability;
import org.streampipes.model.impl.quality.MeasurementObject;
import org.streampipes.model.vocabulary.MessageFormat;
import org.streampipes.model.vocabulary.XSD;
import org.streampipes.pe.sources.samples.config.ProaSenseSettings;

public abstract class AbstractWunderbarStream implements EventStreamDeclarer {

	protected WunderbarVariables variable;
	
	protected final static String SEPA_PREFIX = "http://event-processing.org/sepa/";
	
	public AbstractWunderbarStream(WunderbarVariables variable) {
		this.variable = variable;
	}
	
	public EventStream prepareStream(SepDescription sep) {
		
		EventStream stream = new EventStream();
		stream.setName(variable.eventName());
		stream.setDescription(variable.description());
		stream.setUri(sep.getUri() + variable.path());
		
		EventGrounding grounding = new EventGrounding();
		grounding.setTransportProtocol(ProaSenseSettings.jmsProtocol(variable.topic()));
		grounding.setTransportFormats(Utils.createList(new TransportFormat(MessageFormat.Json)));
				
		stream.setEventGrounding(grounding);	
		
		return stream;
	}
	
	protected List<MeasurementCapability> mc(String capabilitySuffix) {
		return Arrays.asList(new MeasurementCapability(URI.create(SEPA_PREFIX +capabilitySuffix)));
	}
	
	protected List<MeasurementObject> mo(String measurementObjectSuffix) {
		return Arrays.asList(new MeasurementObject(URI.create(SEPA_PREFIX +measurementObjectSuffix)));
	}
	
	@Override
	public void executeStream() {
		
	}

	@Override
	public boolean isExecutable() {
		return false;
	}

	
	public EventPropertyPrimitive timestampProperty() {
		return new EventPropertyPrimitive(XSD._long.toString(), "timestamp", "", Utils.createURI("http://schema.org/DateTime"));
	}
}