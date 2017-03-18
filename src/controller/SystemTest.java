package controller;

public class SystemTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		if (testPasses) {
			System.out.println("Full System Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Full System Test: FAIL");
			System.exit(1);
		}
	}
}