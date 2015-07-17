package de.fzi.cep.sepa.sources.samples.taxi;

import java.io.File;

import de.fzi.cep.sepa.commons.messaging.IMessagePublisher;
import de.fzi.cep.sepa.desc.declarer.EventStreamDeclarer;

public abstract class AbstractNycStream implements EventStreamDeclarer{

	protected IMessagePublisher publisher;
	protected IMessagePublisher timePublisher;
	
	public AbstractNycStream(String topic) {
		publisher = NycTaxiUtils.streamPublisher(topic);
		timePublisher = NycTaxiUtils.streamPublisher("FZI.Timer");
	}
	
	public void executeReplay(File file) {
		new Thread(new TaxiStreamGenerator(file, SimulationSettings.DEMONSTRATE_10, publisher, timePublisher)).start();
	}

	
}
