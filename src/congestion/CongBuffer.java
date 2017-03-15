package congestion;

import java.util.*;

public class CongBuffer implements Buffer {
	private Timer sysTimer;
	private Receiver connectedReceiver;

	private Queue<Packet> packetQueue = new LinkedList<Packet>();

	private final int packetProcessingTime;

	//Counts ticks to forward the next packet.
	private int tickCountup = 0;

	//Hide default initializer. Don't call it
	private CongBuffer() {
		this.packetProcessingTime = -1;
	}

	public CongBuffer(Timer sysTimer, int packetProcessingTime) {
		assert (sysTimer != null) : "SysTimer is null. Can't buffer packets.";

		this.sysTimer = sysTimer;
		this.packetProcessingTime = packetProcessingTime;
	}

	public int packetProcessingTime() {
		assert (this.packetProcessingTime > 0) : "Packet processing time must be > 0, got: " + this.packetProcessingTime + ".";

		return this.packetProcessingTime;
	}

	public void setReceiver(Receiver connectedReceiver) {
		this.connectedReceiver = connectedReceiver;
	}

	//Receive takes in a packet, stampes the arrival tick and queues it.
	public void receive(Packet p) {
		assert (this.sysTimer != null) : "SysTimer is null. Can't receive packets.";

		int currentTick = this.sysTimer.currentTick();
		int numberOfQueuedPackets = this.packetQueue.size();
		p.setTickBuffered(currentTick);
		p.setNumElementsInBuffer(numberOfQueuedPackets);
		this.packetQueue.add(p);
	}

	//Tick causes the buffer to check if any new packets are due to be forwarded to receiver.
	public void tick() {
		if (this.packetQueue.size() == 0) {
			return;
		}

		this.tickCountup++;
		assert (this.tickCountup <= this.packetProcessingTime) : "Countup exceeds packet processing time.";

		if (this.tickCountup < this.packetProcessingTime) {
			return;
		}

		//Packet is due to be forwarded.
		Packet p = this.packetQueue.remove();
		this.tickCountup = 0;
		this.forward(p);
	}

	private void forward(Packet p) {
		assert (this.connectedReceiver != null) : "Receiver is null, can't forward the packet.";

		this.connectedReceiver.receive(p);
	}

	public int numberOfQueuedPackets() {
		assert (this.connectedReceiver != null) : "Receiver is null. Can't return number of queued packets.";

		return this.packetQueue.size();
	}
}