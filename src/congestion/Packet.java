package congestion;

public class Packet {
	private int tickSent = -1;
	private int tickBuffered = -1;
	private int numElementsInBuffer = -1;
	private int tickReceived = -1;

	public String toString() {
		return String.format("Packet sent at: %d, buffered at %d with %d packets in front, received at %d.", this.tickSent, this.tickBuffered, this.numElementsInBuffer, this.tickReceived);
	}

	public void setTickSent(int tickSent) {
		this.tickSent = tickSent;
	}

	//tickSent returns the tick number at which the packet was sent.
	public int tickSent() {
		return this.tickSent;
	}

	public void setTickBuffered(int tickBuffered) {
		this.tickBuffered = tickBuffered;
	}

	//tickBuffered returns the tick number at which the packet was buffered.
	public int tickBuffered() {
		return this.tickBuffered;
	}

	public void setNumElementsInBuffer(int numElementsInBuffer) {
		this.numElementsInBuffer = numElementsInBuffer;
	}

	//numElementsInBuffer returns the number of elements in the buffer when the packet arrived at the buffer.
	public int numElementsInBuffer() {
		return this.numElementsInBuffer;
	}

	public void setTickReceived(int tickReceived) {
		this.tickReceived = tickReceived;
	}

	//tickReceived returns the tick at which the packet arrived at the receiver.
	public int tickReceived() {
		return this.tickReceived;
	}
}