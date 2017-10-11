package testobject.results;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestObjectResultWatcher extends TestWatcher
{
	protected String sessionId;
	public boolean status;
	
	protected static final boolean PASSED = true;
	protected static final boolean FAILED = false;
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	@Override
	protected void succeeded(Description description)
	{
		status = PASSED;
		reportTestStatus(description);
	}
	
	@Override
	protected void failed(Throwable e, Description description)
	{
		status = FAILED;
		reportTestStatus(description);
	}
	
	protected void reportTestStatus(Description description)
	{
		System.out.println(description.getDisplayName());
		System.out.println("status: " + (status ? "PASSED" : "FAILED"));
		System.out.println("sessionId: " + sessionId);
		
		new TestObjectResultReporter().saveTestStatus(sessionId, status);
	}
}
