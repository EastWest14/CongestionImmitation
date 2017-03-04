package congestion;

public class PacketTest {
	public static void main(String[] args) {
		int s = Packet.sum(2, 3);
		if (s == 6) {
			return;
		}

		System.out.println("Packet Test: FAIL");
		System.exit(1);
	}
}