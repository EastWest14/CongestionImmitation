package controller;

import congestion.*;

public class Controller {
	private CongTimer timer;
	private CongReceiver receiver;
	private CongBuffer buffer;
	private CongSender sender;

	//Hide default initializer
	private Controller() {
	}

	public Controller(int numberOfTicks, int numberOfPacketsToSend, int packetProcessingTime) {
		this.timer = new CongTimer(numberOfTicks);
		this.receiver = new CongReceiver(this.timer);
		this.buffer = new CongBuffer(this.timer, packetProcessingTime);
		this.buffer.setReceiver(this.receiver);
		this.sender = new CongSender(this.timer, this.buffer, numberOfPacketsToSend);
		//Initializa analyzer.
	}

	public void run() {
		System.out.println("The system is running!");
		do {
			this.sender.tick();
			this.buffer.tick();
		} while(this.timer.tickForward());
		System.out.println("The system finished to run!");
		System.out.println(this.receiver.allReceivedPackets().size() + " packets made it through.");
		//Provide result to analyzer.
		//Trigger analyzer result return.
	}
}