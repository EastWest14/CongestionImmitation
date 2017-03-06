package congestion;

import java.util.*;

public class ReceiverTest {
	public static void main(String[] args) {
		boolean testPasses = true;

		boolean initializeTest = testInitialize();
		if (!initializeTest) {
			testPasses = false;
		}

		boolean allReceivedPacketsTest = testInitialAllReceivedPackets();
		if (!allReceivedPacketsTest) {
			testPasses = false;
		}

		boolean receiveTest = testReceive();
		if (!receiveTest) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Receiver Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Receiver Test: FAIL");
			System.exit(1);
		}
	}

	private static boolean testInitialize() {
		MockTimer mTimer = new MockTimer();
		Receiver aReceiver = new Receiver(mTimer);

		if (aReceiver == null) {
			System.out.println("	Receiver initializer returns null.");
			return false;
		}

		return true;
	}

	private static boolean testInitialAllReceivedPackets() {
		MockTimer mTimer = new MockTimer();
		Receiver aReceiver = new Receiver(mTimer);

		List<Packet> allPackets= aReceiver.allReceivedPackets();
		int length = allPackets.size();

		if (length != 0) {
			System.out.println("	Expected initial number of returned packets to be 0, got: " + length + ".");
			return false;
		}

		return true;
	}

	private static boolean testReceive() {
		MockTimer mTimer = new MockTimer();
		Receiver aReceiver = new Receiver(mTimer);

		Packet packetOne = new Packet();
		Packet packetTwo = new Packet();

		aReceiver.receive(packetOne);
		aReceiver.receive(packetTwo);

		List<Packet> allPackets = aReceiver.allReceivedPackets();

		int length = allPackets.size();
		if (length != 2) {
			System.out.println("	Expected total number of returned packets to be 2, got: " + length + ".");
			return false;
		}

		Packet receivedPacOne = allPackets.get(0);
		int pOneReceivedTick = receivedPacOne.tickReceived();
		if (pOneReceivedTick != 1) {
			System.out.println("	Expected packet one to be received at 1, got: " + pOneReceivedTick + ".");
			return false;
		}

		Packet receivedPacTwo = allPackets.get(1);
		int pTwoReceivedTick = receivedPacTwo.tickReceived();
		if (pTwoReceivedTick != 12) {
			System.out.println("	Expected packet two to be received at 12, got: " + pTwoReceivedTick + ".");
			return false;
		}

		return true;
	}
}

class MockTimer implements Timer {
	private int[] currentTicksReturned = {1, 12};
	private int currentTickCounter = 0;

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