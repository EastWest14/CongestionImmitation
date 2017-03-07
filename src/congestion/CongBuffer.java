package congestion;

public class CongBuffer {
	private Timer sysTimer;
	//Hide default initializer
	private CongBuffer() {
	}

	public CongBuffer(Timer sysTimer) {
		assert (sysTimer != null) : "SysTimer is null. Can't buffer packets.";

		this.sysTimer = sysTimer;
	}
}