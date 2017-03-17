package congestion;

import java.util.*;

public class CongSenderTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		boolean testScheduleForming = testScheduling();
		if (!testScheduleForming) {
			testPasses = false;
		}

		boolean testTick = testTick();
		if (!testTick) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Sender Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Sender Test: FAIL");
			System.exit(1);
		}
	}

	private static boolean testScheduling() {
		int[] pseudoRandomTickNumbers = {1, 2, 2, 3, 1, 4, 5};
		MockTimerS mTimer = new MockTimerS(pseudoRandomTickNumbers);

		MockBufferS mBuffer = new MockBufferS();
		int numberOfPacketsToSchedule = 4;
		CongSender sender = new CongSender(mTimer, mBuffer, numberOfPacketsToSchedule);

		Map<Integer,Boolean> schedule = sender.packetSchedule();
		int numScheduledPackets = schedule.size();

		if (numScheduledPackets != 4) {
			System.out.println("	Expected 4 scheduled packets, got: " + numScheduledPackets + ".");
			return false;
		}

		//Cheching a complete small schedule one by one.
		if (!schedule.containsKey(1)) {
			System.out.println("	Not all keys scheduled correctly. Nothing scheduled for tick 1.");
			return false;
		}
		if (!schedule.containsKey(2)) {
			System.out.println("	Not all keys scheduled correctly. Nothing scheduled for tick 2.");
			return false;
		}
		if (!schedule.containsKey(3)) {
			System.out.println("	Not all keys scheduled correctly. Nothing scheduled for tick 1.");
			return false;
		}
		if (!schedule.containsKey(4)) {
			System.out.println("	Not all keys scheduled correctly. Nothing scheduled for tick 1.");
			return false;
		}

		return true;
	}

	private static boolean testTick() {
		int[] pseudoRandomTickNumbers = {2, 3};
		MockTimerS mTimer = new MockTimerS(pseudoRandomTickNumbers);
		MockBufferS mBuffer = new MockBufferS();
		int numberOfPacketsToSchedule = 2;
		CongSender sender = new CongSender(mTimer, mBuffer, numberOfPacketsToSchedule);

		//Check the packets are forwarded or not forwarded at the right ticks.
		mBuffer.expectToReceivePacket(false);
		sender.tick();
		if (!mBuffer.expectationsFulfilled()) {
			System.out.println("	Sender should forward nothing on tick 1, but did forward.");
			return false;
		}

		mBuffer.expectToReceivePacket(true);
		sender.tick();
		if (!mBuffer.expectationsFulfilled()) {
			System.out.println("	Sender should forward on tick 2, but did not forward.");
			return false;
		}
		//Check the sent timestamp of the first sent packet.
		Packet sentPacket = mBuffer.lastReceivedPacket();
		if (sentPacket.tickSent() != 2) {
			System.out.println("	Packet should have been sent on tick 2, was sent on tick: " + sentPacket.tickSent() + ".");
			return false;
		}

		mBuffer.expectToReceivePacket(true);
		sender.tick();
		if (!mBuffer.expectationsFulfilled()) {
			System.out.println("	Sender should forward on tick 3, but did not forward.");
			return false;
		}
		sentPacket = mBuffer.lastReceivedPacket();
		if (sentPacket.tickSent() != 3) {
			System.out.println("	Packet should have been sent on tick 3, was sent on tick: " + sentPacket.tickSent() + ".");
			return false;
		}

		mBuffer.expectToReceivePacket(false);
		sender.tick();
		if (!mBuffer.expectationsFulfilled()) {
			System.out.println("	Sender should forward nothing on tick 4, but did forward.");
			return false;
		}

		return true;
	}
}

class MockTimerS implements Timer {
	private int[] pseudoRandomTickNumbers;
	private int pseudoRandomTickCounter = 0;

	private int currentTickCounter;

	MockTimerS(int[] pseudoRandomTickNumbers) {
		this.pseudoRandomTickNumbers = pseudoRandomTickNumbers;
		this.currentTickCounter = 1;
	}

	public int ticksPerSecond() {
		return 0;
	}

	public int currentTick() {
		return this.currentTickCounter++;
	}

	public boolean tickForward() {
		return true;
	}

	public int randomTickNumber() {
		return this.pseudoRandomTickNumbers[this.pseudoRandomTickCounter++];
	}
}

class MockBufferS implements Buffer {
	private boolean expectationsFulfilled = false;
	private boolean expectToReceivePacket;
	private boolean expectationSet = false;

	private Packet lastReceivedPacket;

	public int packetProcessingTime() {
		return 0;
	}

	public void setReceiver(Receiver connectedReceiver) {
	}

	public void receive(Packet p) {
		assert (this.expectationSet != false) : "Expectation for packet receipt was not set.";

		this.lastReceivedPacket = p;

		this.expectationSet = false;

		if (this.expectToReceivePacket) {
			this.expectationsFulfilled = true;
		} else {
			this.expectationsFulfilled = false;
		}
	}

	public void tick() {

	}

	public int numberOfQueuedPackets() {
		return 0;
	}

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

	public Packet lastReceivedPacket() {
		return this.lastReceivedPacket;
	}

	public boolean expectationsFulfilled() {
		return this.expectationsFulfilled;
	}
}

