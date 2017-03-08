package congestion;

public class CongBuffer {
	private Timer sysTimer;
	private Receiver connectedReceiver;
	//Hide default initializer
	private CongBuffer() {
	}

	public CongBuffer(Timer sysTimer, int packetProcessingTime) {
		assert (sysTimer != null) : "SysTimer is null. Can't buffer packets.";

		this.sysTimer = sysTimer;
	}

	public int packetProcessingTime() {
		return -1;
	}

	public void setReceiver(Receiver connectedReceiver) {
		this.connectedReceiver = connectedReceiver;
	}

	//Receive takes in a packet, stampes the arrival tick and queues it.
	public void receive(Packet p) {
	}

	//Tick causes the buffer to check if any new packets are due to be forwarded to receiver.
	public void tick() {

	}

	public int numberOfQueuedPackets() {
		assert (this.connectedReceiver != null) : "Receiver is null. Can't return number of queued packets.";

		return -1;
	}
}