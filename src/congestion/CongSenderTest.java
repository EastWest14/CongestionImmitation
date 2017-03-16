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
		MockTimerS mTimer = new MockTimerS();
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
		//Create a mock timer instance.
		//Create a mock buffer instance.
		//Create a numer of scheduled packets variable.
		//Initialize Sender with 3 values.

		//Check the packets are forwarded or not forwarded at the right points.
		//Check the timestamp of the individual packets.

		return false;
	}
}

class MockTimerS implements Timer {
	//Returns a specific timeseries for random numbers.
	//Correctly increments by one on every currentTick call.
	private int[] pseudoRandomTickNumbers = {1, 2, 2, 3, 1, 4, 5};
	private int pseudoRanndomTickCounter = 0;

	private int currentTickCounter;

	MockTimerS() {
		this.currentTickCounter = 0;
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
		return this.pseudoRandomTickNumbers[this.pseudoRanndomTickCounter];
	}
}

class MockBufferS implements Buffer {
	//Has an expectation for receiving/not receiving a packet.
	//Can return last packet received.

	public int packetProcessingTime() {
		return 0;
	}

	public void setReceiver(Receiver connectedReceiver) {
	}

	public void receive(Packet p) {

	}

	public void tick() {

	}

	public int numberOfQueuedPackets() {
		return 0;
	}
}

