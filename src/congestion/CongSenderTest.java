package congestion;

public class CongSenderTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		int a = CongSender.a(3);
		if (a != 3) {
			testPasses = false;
		}

		if (testPasses) {
			System.out.println("Sender Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Sender Test: FAIL");
			System.exit(1);
		}
	}
}

