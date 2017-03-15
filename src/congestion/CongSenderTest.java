package congestion;

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
		//Create a mock timer instance.
		//Create a mock buffer instance.
		//Create a numer of scheduled packets variable.
		//Initialize Sender with 3 values.

		//Get back the schedule.
		//Check that nothing is beyond total tick number.
		//Check that there are exactly the right number of entries - no duplicates were introduced.

		//Can actually check the values one by one.

		return false;
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

	private int currentTickCounter = 0;

	MockTimerS() {
		this.currentTickCounter = 0;
	}

	public int ticksPerSecond() {
		return 0;
	}

	public int currentTick() {
		return 0;
	}

	public boolean tickForward() {
		return true;
	}

	public int randomTickNumber() {
		return 0;
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

