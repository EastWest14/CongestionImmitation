package congestion;

public interface Buffer {
	public int packetProcessingTime();
	public void setReceiver(Receiver connectedReceiver);

	public void receive(Packet p);
	public void tick();

	public int numberOfQueuedPackets();
}