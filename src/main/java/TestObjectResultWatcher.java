import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestObjectResultWatcher extends TestWatcher
{
	String sessionId;
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	@Override
	protected void failed(Throwable e, Description description)
	{
		reportTestStatus(false);
	}
	
	@Override
	protected void succeeded(Description description)
	{
		reportTestStatus(true);
	}
	
	protected void reportTestStatus(boolean passed)
	{
		new TestObjectResultReporter().saveTestStatus(sessionId, passed);
	}
}