package congestion;

import java.util.*;

public class CongSender {
	private Timer sysTimer;
	private Buffer packetBuffer;
	private Map<Integer,Boolean> packetSchedule;
	private int numberOfPacketsToSend;

	//Hide default initializer.
	private CongSender() {
	}

	public CongSender(Timer sysTimer, Buffer buff, int numberOfPacketsToSend) {
		//Assign instance variable values.
		this.sysTimer = sysTimer;
		this.packetBuffer = buff;
		this.numberOfPacketsToSend = numberOfPacketsToSend;
		//Initialize schedule map.
		this.schedulePacketSending();
		assert (this.packetSchedule.size() == this.numberOfPacketsToSend) : "Schedule initialized incorrectly. Should schedule " + this.numberOfPacketsToSend + ", but got " + this.packetSchedule.size() + ".";
	}

	private void schedulePacketSending() {
		this.packetSchedule = new HashMap<Integer,Boolean>();
		int numScheduled = 0;

		do {
			int randomTick = this.sysTimer.randomTickNumber();

			if (!this.packetSchedule.containsKey(randomTick)) {
				this.packetSchedule.put(randomTick, true);
				numScheduled++;
			}
		} while (numScheduled < this.numberOfPacketsToSend);
	}

	//packetSchedule should be used internally or for testing/debugging.
	public Map<Integer,Boolean> packetSchedule() {
		return this.packetSchedule;
	}

	public void tick() {
		assert (this.packetBuffer != null) : "Buffer is null, can't forward packets to it.";

		int currentTick = this.sysTimer.currentTick();
		boolean packetScheduled = this.packetSchedule.containsKey(currentTick);
		if (packetScheduled) {
			Packet p = new Packet();
			p.setTickSent(currentTick);
			this.packetBuffer.receive(p);
		}
	}
}