package analyzer;

import congestion.*;
import java.util.*;
import java.lang.*;

public class AnalyzerTest {
	public static void main(String[] args) {
		boolean testPasses = true;

		boolean testNumReceived = testNumberOfPackets();
		if (!testNumReceived) {
			testPasses = false;
		}

		boolean testTravTime = testTravelTime();
		if (!testTravTime) {
			testPasses = false;
		}

		boolean testBufferLen = testBufferQueueLength();
		if (!testBufferLen) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Analyzer Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Analyzer Test: FAIL");
			System.exit(1);
		}
	}

	private static boolean testNumberOfPackets() {
		//Empty packet list case.
		Analyzer analyzer1 = new Analyzer();
		analyzer1.analyzePackets(new ArrayList<Packet>());
		int numReceived1 = analyzer1.numberOfReceivedPackets();
		if (numReceived1 != 0) {
			System.out.println("	Analyzer expected to receive 0 packets, got " + numReceived1 + ".");
			return false;
		}

		//Packet list with 3 Packets.
		Analyzer analyzer2 = new Analyzer();
		List<Packet> pList = new ArrayList<Packet>();
		pList.add(new Packet());
		pList.add(new Packet());
		pList.add(new Packet());
		analyzer2.analyzePackets(pList);
		int numReceived2 = analyzer2.numberOfReceivedPackets();
		if (numReceived2 != 3) {
			System.out.println("	Analyzer expected to receive 3 packets again, got " + numReceived2 + ".");
			return false;
		}

		return true;
	}

	private static boolean testTravelTime() {
		Analyzer analyzer1 = new Analyzer();
		analyzer1.analyzePackets(new ArrayList<Packet>());
		int minTime1 = analyzer1.minimumTravelTime();
		if (minTime1 != Integer.MAX_VALUE) {
			System.out.println("	Analyzer expected minimum travel time to be Integer.MAX_VALUE for no packets, got " + minTime1 + ".");
			return false;
		}
		int maxTime1 = analyzer1.maximumTravelTime();
		if (maxTime1 != -1) {
			System.out.println("	Analyzer expected maximum travel time to be -1 for no packets, got " + maxTime1 + ".");
			return false;
		}
		double averageTime1 = analyzer1.averageTravelTime();
		if (Math.abs(averageTime1 - (-1.0)) >= 0.000001) {
			System.out.println("	Analyzer expected average travel time to be -1.0 for no packets, got " + averageTime1 + ".");
			return false;
		}

		//List of 3 packets
		Analyzer analyzer2 = new Analyzer();
		List<Packet> pList = new ArrayList<Packet>();

		Packet p1 = new Packet();
		p1.setTickSent(3);
		p1.setTickReceived(5);
		pList.add(p1);
		Packet p2 = new Packet();
		p2.setTickSent(30);
		p2.setTickReceived(35);
		pList.add(p2);
		Packet p3 = new Packet();
		p3.setTickSent(100);
		p3.setTickReceived(108);
		pList.add(p3);

		analyzer2.analyzePackets(pList);
		int minTime2 = analyzer2.minimumTravelTime();
		if (minTime2 != 2) {
			System.out.println("	Analyzer ahould get minimum travel time of 2, got " + minTime2 + ".");
			return false;
		}
		int maxTime2 = analyzer2.maximumTravelTime();
		if (maxTime2 != 8) {
			System.out.println("	Analyzer expected maximum travel time to be 8, got " + maxTime2 + ".");
			return false;
		}
		double averageTime2 = analyzer2.averageTravelTime();
		if (Math.abs(averageTime2 - (5.0)) >= 0.000001) {
			System.out.println("	Analyzer expected average travel time to be 5.0, got " + averageTime2 + ".");
			return false;
		}

		return true;
	}

	private static boolean testBufferQueueLength() {
		Analyzer analyzer1 = new Analyzer();
		analyzer1.analyzePackets(new ArrayList<Packet>());
		int maxQueueLength1 = analyzer1.maximumBufferQueueLength();
		if (maxQueueLength1 != -1) {
			System.out.println("	Analyzer expected maximum buffer queue length be -1 for no packets, got " + maxQueueLength1 + ".");
			return false;
		}
		double averageQueueLength1 = analyzer1.averageBufferQueueLength();
		if (Math.abs(averageQueueLength1 - (-1.0)) >= 0.000001) {
			System.out.println("	Analyzer expected average buffer queue length to be -1.0 for no packets, got " + averageQueueLength1 + ".");
			return false;
		}

		//List of 3 packets
		Analyzer analyzer2 = new Analyzer();
		List<Packet> pList = new ArrayList<Packet>();

		Packet p1 = new Packet();
		p1.setNumElementsInBuffer(30);
		pList.add(p1);
		Packet p2 = new Packet();
		p2.setNumElementsInBuffer(50);
		pList.add(p2);
		Packet p3 = new Packet();
		p3.setNumElementsInBuffer(40);
		pList.add(p3);

		analyzer2.analyzePackets(pList);
		int maxQueueLength2 = analyzer2.maximumBufferQueueLength();
		if (maxQueueLength2 != 50) {
			System.out.println("	Analyzer expected maximum buffer queue length be 50, got " + maxQueueLength1 + ".");
			return false;
		}
		double averageQueueLength2 = analyzer2.averageBufferQueueLength();
		if (Math.abs(averageQueueLength2 - (40.0)) >= 0.000001) {
			System.out.println("	Analyzer expected average buffer queue length to be 40.0, got " + averageQueueLength2 + ".");
			return false;
		}

		return true;
	}
}