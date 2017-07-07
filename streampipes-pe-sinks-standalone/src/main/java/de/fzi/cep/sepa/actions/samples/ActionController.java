package de.fzi.cep.sepa.actions.samples;

import de.fzi.cep.sepa.client.declarer.SemanticEventConsumerDeclarer;
import de.fzi.cep.sepa.commons.config.ClientConfiguration;
import de.fzi.cep.sepa.messaging.EventListener;
import de.fzi.cep.sepa.messaging.kafka.StreamPipesKafkaConsumer;
import de.fzi.cep.sepa.model.impl.EventGrounding;
import de.fzi.cep.sepa.model.impl.JmsTransportProtocol;
import de.fzi.cep.sepa.model.impl.KafkaTransportProtocol;
import de.fzi.cep.sepa.model.impl.eventproperty.EventProperty;
import de.fzi.cep.sepa.model.impl.graph.SecInvocation;

import java.util.ArrayList;
import java.util.List;


public abstract class ActionController implements SemanticEventConsumerDeclarer {

	protected StreamPipesKafkaConsumer kafkaConsumer;

	protected void startKafkaConsumer(String kafkaUrl, String topic, EventListener<byte[]> eventListener) {
		kafkaConsumer = new StreamPipesKafkaConsumer(kafkaUrl, topic, eventListener);
		Thread thread = new Thread(kafkaConsumer);
		thread.start();
	}

	protected void stopKafkaConsumer() {
		if (kafkaConsumer != null) {
			kafkaConsumer.close();
		}
	}
	
	protected String createWebsocketUri(SecInvocation sec)
	{
		if (ClientConfiguration.INSTANCE.isNissatechRunning()) return "wss://proasense.nissatech.com/ws/"; 
		return getEventGrounding(sec).getTransportProtocol().getBrokerHostname().replace("tcp",  "ws") + ":61614";
	}
	
	protected String extractTopic(SecInvocation sec)
	{
		return "/topic/" +getEventGrounding(sec).getTransportProtocol().getTopicName();
	}
	
	protected String createJmsUri(SecInvocation sec)
	{
		return getEventGrounding(sec).getTransportProtocol().getBrokerHostname() + ":" +((JmsTransportProtocol)getEventGrounding(sec).getTransportProtocol()).getPort();
	}
	
	protected String createKafkaUri(SecInvocation sec)
	{
		return getEventGrounding(sec).getTransportProtocol().getBrokerHostname() + ":" +((KafkaTransportProtocol)getEventGrounding(sec).getTransportProtocol()).getZookeeperPort();
	}
	
	private EventGrounding getEventGrounding(SecInvocation sec)
	{
		return sec.getInputStreams().get(0).getEventGrounding();
	}
	
	protected String[] getColumnNames(List<EventProperty> eventProperties)
	{
		List<String> result = new ArrayList<>();
		for(EventProperty p : eventProperties)
		{
			result.add(p.getRuntimeName());
		}
		return result.toArray(new String[0]);
	}
}