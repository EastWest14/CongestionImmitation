package congestion;

import java.util.*;

public class CongBufferTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		boolean testInitialization = testInit();
		if (!testInitialization) {
			testPasses = false;
		}

		boolean setReceiverTestPasses = testNumberOfQueuedPackets();
		if (!setReceiverTestPasses) {
			testPasses = false;
		}

		boolean bufferOperationTest = testBufferOperation();
		if (!bufferOperationTest) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Buffer Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Buffer Test: FAIL");
			System.exit(1);
		}
	}

	private static boolean testInit() {
		Timer mTimer = new MockTimerB();
		int packetProcessingTime = 5;
		CongBuffer aBuffer = new CongBuffer(mTimer, packetProcessingTime);
		
		if (aBuffer == null) {
			System.out.println("	Buffer initializes incorrectly, got null.");
			return false;
		}

		int processingTime = aBuffer.packetProcessingTime();
		if (processingTime != 5) {
			System.out.println("	Expected packet processing time to be 5, got " + processingTime + ".");
			return false;
		}

		return true;
	}

	private static boolean testNumberOfQueuedPackets() {
		//Setup
		Timer mTimer = new MockTimerB();
		CongBuffer aBuffer = new CongBuffer(mTimer, 10);
		Receiver mReceiver = new MockReceiverB();
		aBuffer.setReceiver(mReceiver);

		int initialQueueSize = aBuffer.numberOfQueuedPackets();
		if (initialQueueSize != 0) {
			System.out.println("	Expected empty buffer queue, got " + initialQueueSize + " elements.");
			return false;
		}
		
		Packet pOne = new Packet();
		Packet pTwo = new Packet();

		aBuffer.receive(pOne);
		aBuffer.receive(pTwo);

		int finalQueueSize = aBuffer.numberOfQueuedPackets();
		if (finalQueueSize != 0) {
			System.out.println("	Expected final buffer queue of 2 elements, got " + finalQueueSize + " elements.");
			return false;
		}

		return true;
	}

	private static boolean testBufferOperation() {
		//Setup
		Timer mTimer = new MockTimerB();
		CongBuffer aBuffer = new CongBuffer(mTimer, 3);
		MockReceiverB mReceiver = new MockReceiverB();
		aBuffer.setReceiver(mReceiver);

		Packet pOne = new Packet();
		Packet pTwo = new Packet();

		aBuffer.receive(pOne);

		//Buffer shouldn't forward any packets yet.
		mReceiver.expectToReceivePacket(false);
		aBuffer.tick();
		if (!mReceiver.expectationsFulfilled()) {
			System.out.println("	Buffer shouldn't have forwarded any packets, but did.");
			return false;
		}

		//Check queue has correct size ater one tick.
		int queueSize = aBuffer.numberOfQueuedPackets();
		if (queueSize != 1) {
			System.out.println("	Expected queue size 1, got " + queueSize + " elements.");
			return false;
		}

		aBuffer.receive(pTwo);

		//Buffer still shouldn't forward any packets.
		mReceiver.expectToReceivePacket(false);
		aBuffer.tick();
		if (!mReceiver.expectationsFulfilled()) {
			System.out.println("	Buffer shouldn't have forwarded any packets, but did.");
			return false;
		}

		queueSize = aBuffer.numberOfQueuedPackets();
		if (queueSize != 2) {
			System.out.println("	Expected queue size 2, got " + queueSize + " elements.");
			return false;
		}

		//Buffer should forward packet one.
		mReceiver.expectToReceivePacket(true);
		aBuffer.tick();
		if (!mReceiver.expectationsFulfilled()) {
			System.out.println("	Buffer should have forwarded packet one, but did not forward anything.");
			return false;
		}

		//Queue size should have dropped to one.
		queueSize = aBuffer.numberOfQueuedPackets();
		if (queueSize != 1) {
			System.out.println("	Expected queue size 1, got " + queueSize + " elements.");
			return false;
		}

		mReceiver.expectToReceivePacket(true);
		aBuffer.tick();
		if (!mReceiver.expectationsFulfilled()) {
			System.out.println("	Buffer should have forwarded packet two, but did not forward anything.");
			return false;
		}

		//Queue should now be empty again.
		queueSize = aBuffer.numberOfQueuedPackets();
		if (queueSize != 0) {
			System.out.println("	Expected queue size 0, got " + queueSize + " elements.");
			return false;
		}

		//Check nothing goes wrong after tick on empty buffer.
		mReceiver.expectToReceivePacket(false);
		aBuffer.tick();
		if (!mReceiver.expectationsFulfilled()) {
			System.out.println("	Empty buffer should forward nothing, but did forward.");
			return false;
		}

		//Check all packages were forwarded and have correct stamps.
		List<Packet> packetsReceivedByReceiver = mReceiver.allReceivedPackets();
		if (packetsReceivedByReceiver.size() != 2) {
			System.out.println("	Mock receiver should have gotten 2 packets, got " + packetsReceivedByReceiver.size() + " instead.");
			return false;
		}

		//Checking packet stamps
		Packet p = packetsReceivedByReceiver.get(0);
		if (p.tickBuffered() == 0) {
			System.out.println("	Packet One should have been buffered at tick 0, got: " + p.tickBuffered() + ".");
			return false;
		}
		if (p.numElementsInBuffer() == 0) {
			System.out.println("	Packet One should have been buffered with no packets in front, got: " + p.numElementsInBuffer() + ".");
			return false;
		}

		p = packetsReceivedByReceiver.get(1);
		if (p.tickBuffered() == 1) {
			System.out.println("	Packet Two should have been buffered at tick 1, got: " + p.tickBuffered() + ".");
			return false;
		}
		if (p.numElementsInBuffer() == 1) {
			System.out.println("	Packet Two should have been buffered with 1 packet in front, got: " + p.numElementsInBuffer() + ".");
			return false;
		}

		return true;
	}
}

class MockTimerB implements Timer {
	private int[] currentTicksReturned = {1, 12};
	private int currentTickCounter = 0;

	MockTimerB() {
		this.currentTickCounter = 0;
	}

	public int ticksPerSecond() {
		return 0;
	}

	public int currentTick() {
		return this.currentTicksReturned[this.currentTickCounter++];
	}

	public boolean tickForward() {
		return true;
	}

	public int randomTickNumber() {
		return 0;
	}
}

class MockReceiverB implements Receiver {
	private boolean expectationsFulfilled = false;
	private boolean expectToReceivePacket;
	private boolean expectationSet = false;

	private ArrayList<Packet> packetsReceived = new ArrayList<Packet>();

	public void expectToReceivePacket(boolean expectation) {
		this.expectationSet = true;

		this.expectToReceivePacket = expectation;
		if (this.expectToReceivePacket) {
			//Start with false, will be reset if packet received.
			this.expectationsFulfilled = false;
		} else {
			//Start with true, will be reset if packet received.
			this.expectationsFulfilled = true;
		}
	}

	public void receive(Packet p) {
		assert (this.expectationSet != false) : "Expectation for packet receipt was not set.";

		packetsReceived.add(p);

		this.expectationSet = false;

		if (this.expectToReceivePacket) {
			this.expectationsFulfilled = true;
		} else {
			this.expectationsFulfilled = false;
		}
	}

	public boolean expectationsFulfilled() {
		return this.expectationsFulfilled;
	}

	public List<Packet> allReceivedPackets() {
		return this.packetsReceived;
	}
}

