import java.util.Scanner;

public class Executioner {

	private Scanner scanner;
	private int testNumber;
	public Tests tests;
	
	public Executioner() throws InterruptedException{
		scanner = new Scanner(System.in);
		
		//take input to run test =========================
		System.out.println("Please Select\n-1 to Quit"
							+ "\n0 to execute Tests Automatically"
							+ "\n1 : SearchTest"
							+ "\n2 : Search Stress Testing"
							+ "\n3 : Search button submission Test (clicking search button again and again without pause)"
							+ "\n4 : Login Test"
							+ "\n5 : Signup Stress Testing"
							+ "\n6 : SignUp Limit Testing"
							+ "\n7 : BlackBox Testing"
							+ "\n8 : Sanity Testing"
							+ "\n9 : Facebook Testing"
							+ "\n10 : Cookies Test"
							+ "\n11 : Title Test"
							+ "\n12 : Capital Login Test");
		testNumber = scanner.nextInt();
		tests = new Tests();
		while(testNumber != -1){		
			tests.executeTest(testNumber);
			testNumber = scanner.nextInt();
			System.out.println("i am printed");
		}
		
		//write the results to excel file================================
		WriteExcelFile.writeStudentsListToExcel(tests.saveResultsToExcel);
	}
	
	//main thread of execution==========================================
	public static void main(String[] args) throws InterruptedException {
		
		new Executioner();
	}
}