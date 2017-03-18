package analyzer;

import congestion.*;
import java.util.*;

public class Analyzer {
	//analysisPerformed variable makes sure analyzePackets is called before any pther method.
	private boolean analysisPerformed = false;

	private List<Packet> packets;

	public void analyzePackets(List<Packet> packets) {
		assert (packets != null) : "Supplied packet list is null.";

		this.analysisPerformed = true;
		this.packets = packets;
	}

	public int numberOfReceivedPackets() {
		this.checkAnalysisReady();

		if (this.packets == null) {
			return 0;
		}

		return this.packets.size();
	}

	public int minimumTravelTime() {
		this.checkAnalysisReady();

		int minTravelTime = Integer.MAX_VALUE;
		for (Packet p : this.packets) {
			int travelTime = p.tickReceived() - p.tickSent();
			assert (travelTime >= 0) : "Getting a negative packet travel time";

			if (travelTime < minTravelTime) {
				minTravelTime = travelTime;
			}
		}

		return minTravelTime;
	}

	public int maximumTravelTime() {
		this.checkAnalysisReady();

		int maxTravelTime = -1;
		for (Packet p : this.packets) {
			int travelTime = p.tickReceived() - p.tickSent();
			assert (travelTime >= 0) : "Getting a negative packet travel time.";

			if (travelTime > maxTravelTime) {
				maxTravelTime = travelTime;
			}
		}

		return maxTravelTime;
	}

	public double averageTravelTime() {
		this.checkAnalysisReady();

		int numPackets = this.packets.size();
		if (numPackets == 0) {
			return -1.0;
		}
		double sumOfTravelTimes = 0.0;
		for (Packet p : this.packets) {
			int travelTime = p.tickReceived() - p.tickSent();
			assert (travelTime >= 0) : "Getting a negative packet travel time.";

			sumOfTravelTimes += travelTime;
		}

		return sumOfTravelTimes / (double)numPackets;
	}

	public int maximumBufferQueueLength() {
		this.checkAnalysisReady();

		int maxBufferQueueSize = -1;
		for (Packet p : this.packets) {
			int queueSize = p.numElementsInBuffer();
			assert (queueSize >= 0) : "Getting a negative number of buffer queued packets.";

			if (queueSize > maxBufferQueueSize) {
				maxBufferQueueSize = queueSize;
			}
		}

		return maxBufferQueueSize;
	}

	public double averageBufferQueueLength() {
		this.checkAnalysisReady();

		int numPackets = this.packets.size();
		if (numPackets == 0) {
			return -1.0;
		}
		double sumOfQueueLengths = 0.0;
		for (Packet p : this.packets) {
			int queueSize = p.numElementsInBuffer();
			assert (queueSize >= 0) : "Getting a negative number of buffer queued packets.";

			sumOfQueueLengths += queueSize;
		}

		return sumOfQueueLengths / (double)numPackets;
	}

	private void checkAnalysisReady() {
		assert analysisPerformed : "analyzePackets method must be called before any other analyzer methods.";
	}
}