import testobject.results.TestObjectResultReporter;

public class UpdateTestStatus
{
	static TestObjectResultReporter reporter;
	
	static final boolean PASSED = true;
	static final boolean FAILED = false;
	
	public static void main(String [] args)
	{
		/** update test status by calling **/
//		https://app.testobject.com/api/rest/v2/appium/session/{{SESSION_ID}}/test
		
		reporter = new TestObjectResultReporter();
		
		String additionTestId = "4b6ea3c3-ace1-4e36-bf93-0831aaba27d1";
		String multiplicationTestId = "58aa2357-a467-4e6a-ab38-536710c25a8d";
		
		pass(additionTestId);
		fail(multiplicationTestId);
	}
	
	public static void pass(String sessionId)
	{
		reporter.saveTestStatus(sessionId, PASSED);
	}
	
	public static void fail(String sessionId)
	{
		reporter.saveTestStatus(sessionId, FAILED);
	}
}
