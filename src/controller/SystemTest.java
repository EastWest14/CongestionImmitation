package controller;

public class SystemTest {
	public static void main(String args[]) {
		boolean testPasses = true;

		Controller contr = new Controller(1000000, 1000, 10000);
		if (contr == null) {
			testPasses = false;
		}

		contr.run();

		if (testPasses) {
			System.out.println("Full System Test: PASS");
			System.exit(0);
		} else {
			System.out.println("Full System  Test: FAIL");
			System.exit(1);
		}
	}
}