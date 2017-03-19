package controller;

import congestion.*;
import analyzer.*;

public class Controller {
	private CongTimer timer;
	private CongReceiver receiver;
	private CongBuffer buffer;
	private CongSender sender;

	private Analyzer analyzer;

	//Hide default initializer
	private Controller() {
	}

	public Controller(int numberOfTicks, int numberOfPacketsToSend, int packetProcessingTime) {
		this.timer = new CongTimer(numberOfTicks);
		this.receiver = new CongReceiver(this.timer);
		this.buffer = new CongBuffer(this.timer, packetProcessingTime);
		this.buffer.setReceiver(this.receiver);
		this.sender = new CongSender(this.timer, this.buffer, numberOfPacketsToSend);
		
		this.analyzer = new Analyzer();
	}

	public void run() {
		do {
			this.buffer.tick();
			this.sender.tick();
		} while(this.timer.tickForward());

		this.analyzer.analyzePackets(this.receiver.allReceivedPackets());
	}

	public Analyzer analyzer() {
		assert this.analyzer != null : "Analyzer is null";

		return this.analyzer;
	}
}