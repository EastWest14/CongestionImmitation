package congestion;

public interface Timer {
	public int ticksPerSecond();

	//currentTicks returns the current tick number.
	public int currentTick();

	//tickForward increments the "time" by one tick. If the second is over, it returns false.
	public boolean tickForward();

	//randomTickNumber returns a random integer in the range [0, ticksPerSecond -1].
	public int randomTickNumber();
}