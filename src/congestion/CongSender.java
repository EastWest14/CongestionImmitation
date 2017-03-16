package congestion;

import java.util.*;

public class CongSender {

	//Hide default initializer.
	private CongSender() {
	}

	public CongSender(Timer sysTimer, Buffer buff, int numberOfPacketsToSend) {
		//Assign instance variable values.
		//Initialize schedule map.
	}

	private void schedulePacketSending() {
		//Repeat by the number of packets sent:
			//Get a timeslot.
			//Check if it is used.
				//If yes - repeat loop.
				//If no - mark timeslot in the map.
	}

	public Map<Integer,Boolean> packetSchedule() {
		//Return a copy of packet schedule map.
		return new HashMap<Integer,Boolean>();
	}

	public void tick() {
		//Get the current tick value.
		//Check if any packets are scheduled for this tick.
			//If no - do nothing.
			//If yes - create a packet, timestamp it and forward it to the buffer.
	}
}