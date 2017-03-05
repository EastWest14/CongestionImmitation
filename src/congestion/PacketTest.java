package congestion;

public class PacketTest {
	public static void main(String[] args) {
		boolean testPasses = true;

		Packet p = new Packet();
		p.setTickSent(3);
		int tickSent = p.tickSent();
		if (tickSent != 3) {
			System.out.println("	TickSent setter or getter functions incorrectly. Expected: " + 3 + ", got: " + tickSent);
			testPasses = false;
		}


		p.setTickBuffered(10);
		int tickBuffered = p.tickBuffered();
		if (tickBuffered != 10) {
			System.out.println("	TickBuffer setter or getter functions incorrectly. Expected: " + 10 + ", got: " + tickBuffered);
			testPasses = false;
		}

		p.setNumElementsInBuffer(255);
		int numElementsInBuffer = p.numElementsInBuffer();
		if (numElementsInBuffer != 255) {
			System.out.println("	NumElementsInBuffer setter or getter functions incorrectly. Expected: " + 255 + ", got: " + numElementsInBuffer);
			testPasses = false;
		}

		p.setTickReceived(1024);
		int tickReceived = p.tickReceived();
		if (tickReceived != 1024) {
			System.out.println("	TickReceived setter or getter functions incorrectly. Expected: " + 1024 + ", got: " + tickReceived);
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Packet Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Packet Test: FAIL");
			System.exit(1);
		}
	}
}