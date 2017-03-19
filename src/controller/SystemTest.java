package controller;

import analyzer.*;

public class SystemTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		boolean scenario1 = testScenario1();
		if (!scenario1) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Full System Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Full System Test: FAIL");
			System.exit(1);
		}
	}

	//SCENARIO 1: MINIMAL CONGESTION.
	private static boolean testScenario1() {
		Controller contr = new Controller(1000000, 1000, 10);
		contr.run();
		Analyzer analyzer = contr.analyzer();

		int numReceived = analyzer.numberOfReceivedPackets();
		if (numReceived > 1000 || numReceived < 999) {
			System.out.println("	Expected 999-1000 received packets. Got: " + numReceived + ".");
			return false;
		}

		int minTravelTime = analyzer.minimumTravelTime();
		if (minTravelTime != 10) {
			System.out.println("	Expected minimum travel time for packets to be 10. Got: " + minTravelTime + ".");
			return false;
		}

		int maxTravelTime = analyzer.maximumTravelTime();
		if (maxTravelTime < 10 || maxTravelTime > 20) {
			System.out.println("	Expected maximim travel time for packets to be in range 10-20. Got: " + maxTravelTime + ".");
			return false;
		}

		double averageTravelTime = analyzer.averageTravelTime();
		if (averageTravelTime < 10.0 || averageTravelTime > 10.1) {
			System.out.println("	Expected average travel time for packets to be in range 10 to 10.1. Got: " + averageTravelTime + ".");
			return false;
		}

		int maxBufferQueue = analyzer.maximumBufferQueueLength();
		if (maxBufferQueue < 0 || maxBufferQueue > 2) {
			System.out.println("	Expected maximim buffer queue length to be in range 0-2. Got: " + maxBufferQueue + ".");
			return false;
		}

		double averageQueueLength = analyzer.averageBufferQueueLength();
		if (averageQueueLength < 0.002 || averageQueueLength > 0.02) {
			System.out.println("	Expected average buffer queue length to be in range 0.0 to 0.1. Got: " + averageQueueLength + ".");
			return false;
		}

		return true;
	}
}