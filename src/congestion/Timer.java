package congestion;

import java.util.concurrent.ThreadLocalRandom;

public class Timer {
	private final int ticksPerSecond;
	private int currentTick = -1;

	//Hides the default initializer. SHould never be called.
	private Timer() {
		ticksPerSecond = -1;
	}

	//ticksPerSecond sets the number of ticks per second.
	public Timer(int ticksPerSecond) {
		assert (ticksPerSecond > 0) : "Ticks per second is not positive: " + ticksPerSecond + ".";
		this.ticksPerSecond = ticksPerSecond;
		this.currentTick = 0;
	}

	//ticksPerSecond returns the number of ticks per second.
	public int ticksPerSecond() {
		assert (this.ticksPerSecond > 0) : "Ticks per second is not positive, cannot return ticks per second: " + ticksPerSecond + ".";
		return this.ticksPerSecond;
	}

	//currentTicks returns the current tick number.
	public int currentTick() {
		assert (ticksPerSecond > 0) : "Ticks per second is not positive: " + ticksPerSecond + ".";
		assert (this.currentTick >= 0) : "Current tick is negative.";

		return this.currentTick;
	}

	//tickForward increments the "time" by one tick. If the second is over, it returns false.
	public boolean tickForward() {
		assert (ticksPerSecond > 0) : "Ticks per second is not positive: " + ticksPerSecond + ".";
		assert (this.currentTick >= 0) : "Current tick is negative.";
		assert (this.currentTick < this.ticksPerSecond) : "Tick number wen't beyond ticks per second.";

		if (this.currentTick + 1 < this.ticksPerSecond) {
			this.currentTick++;
			return true;
		}
		return false;
	}

	//randomTickNumber returns a random integer in the range [0, ticksPerSecond -1].
	public int randomTickNumber() {
		return ThreadLocalRandom.current().nextInt(0, this.ticksPerSecond);
	}
}