package congestion;

public class CongTimerTest {
	public static void main(String[] args) {
		boolean testPasses = true;

		boolean tpsTest = testTicksPerSeconds();
		if (!tpsTest) {
			testPasses = false;
		}

		boolean tickingTest = testTicking();
		if (!tickingTest) {
			testPasses = false;
		}

		boolean randomTest = testRandomTick();
		if (!randomTest) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Timer Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Timer Test: FAIL");
			System.exit(1);
		}
	}

	private static boolean testTicksPerSeconds() {
		Timer aTimer = new CongTimer(60);
		if (aTimer == null) {
			System.out.println("	Timer initializer returns null.");
			return false;
		}

		int ticksPerSecond = aTimer.ticksPerSecond();
		if (ticksPerSecond != 60) {
			System.out.println("	TicksPerSecond expected: " + 60 + ", got: " + ticksPerSecond);
			return false;
		}

		return true;
	}

	private static boolean testTicking() {
		CongTimer aTimer = new CongTimer(2);

		int currentTick = aTimer.currentTick();
		if (currentTick != 0) {
			System.out.println("	Initial tick should be 0, got: " + currentTick);
			return false;
		}

		boolean tickForwardSucceeds = aTimer.tickForward();
		if (!tickForwardSucceeds) {
			System.out.println("	Tick forward from 0 to 1 should succeed, but failed");
			return false;
		}
		currentTick = aTimer.currentTick();
		if (currentTick != 1) {
			System.out.println("	Tick should be 1, got: " + currentTick);
			return false;
		}

		tickForwardSucceeds = aTimer.tickForward();
		if (tickForwardSucceeds) {
			System.out.println("	Tick forward from 1 to 2 should fail, but succeeded");
			return false;
		}
		currentTick = aTimer.currentTick();
		if (currentTick != 1) {
			System.out.println("	Tick should still be 1, got: " + currentTick);
			return false;
		}

		return true;
	}

	private static boolean testRandomTick() {
		CongTimer aTimer = new CongTimer(10);

		for (int i = 0; i < 100; i++) {
			int randomTick = aTimer.randomTickNumber();
			if ((randomTick < 0) || (randomTick >= 10)) {
				System.out.println("	Random tick schould be in range [0, 9], got: " + randomTick);
				return false;
			}
		}
		return true;
	}
}

