package congestion;

public class CongBufferTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		Timer mTimer = new MockTimerB();
		CongBuffer aBuffer = new CongBuffer(mTimer);
		if (aBuffer == null) {
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
}

class MockTimerB implements Timer {
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

