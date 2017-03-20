package controller;

import analyzer.*;

public class Launcher {
	public static void main(String[] args) throws NumberFormatException {
		assert args.length == 3 : "Need 3 input parameters: number of ticks in simulation, number of packets sent, packet processing time. Got " + args.length + " parameters.";

		int numberOfTicks = Integer.parseInt(args[0]);
		int numberOfPackets = Integer.parseInt(args[1]);
		int packetProcessingTime = Integer.parseInt(args[2]);

		assert (numberOfTicks > 0) : "Number of ticks is not positive.";
		assert (numberOfPackets >= 0) : "Number of ticks is negative.";
		assert (packetProcessingTime > 0) : "Packet processing time is not positive.";

		Controller controller = new Controller(numberOfTicks, numberOfPackets, packetProcessingTime);
		controller.run();
		Analyzer analyzer = controller.analyzer();

		System.out.println("");
		System.out.println("Number of packets scheduled: " + numberOfPackets);
		System.out.println("Number of packets received: " + analyzer.numberOfReceivedPackets());
		System.out.println("Average travel time: " + analyzer.averageTravelTime());
		System.out.println("Minimum travel time: " + analyzer.minimumTravelTime());
		System.out.println("Maximum travel time: " + analyzer.maximumTravelTime());
		System.out.println("Average buffer queue length: " + analyzer.averageBufferQueueLength());
		System.out.println("Maximum buffer queue length: " + analyzer.maximumBufferQueueLength());
		System.out.println("");
	}
}