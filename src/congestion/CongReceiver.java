package congestion;

import java.util.*;

public class CongReceiver implements Receiver {
	private Timer sysTimer;
	private ArrayList<Packet> receivedPackets = new ArrayList<Packet>();

	//Hiding default initializer.
	private CongReceiver() {
	}

	public CongReceiver(Timer systemTimer) {
		this.sysTimer = systemTimer;
	}

	//receive takes in a new packet arring at the receiver, marks arrival tick and puts in into the received list.
	public void receive(Packet p) {
		assert (this.sysTimer != null) : "SysTimer is null. Can't receive packets.";
		assert (p != null) : "Received packet is null.";

		int currentTick = this.sysTimer.currentTick();
		assert (currentTick >= 0) : "CurrentTick is negative, can't receive packets.";

		p.setTickReceived(currentTick);
		this.receivedPackets.add(p);
	}

	//allReceivedPackets returns all the packets received.
	public List<Packet> allReceivedPackets() {
		return (List<Packet>)this.receivedPackets.clone();
	}
}