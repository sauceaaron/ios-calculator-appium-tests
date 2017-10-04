import io.appium.java_client.AppiumDriver;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestObjectResultWatcher extends TestWatcher
{
	AppiumDriver driver;
	
	public void bindDriver(AppiumDriver driver)
	{
		this.driver = driver;
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
	
	protected void reportTestStatus(boolean status)
	{
		new TestObjectResultReporter().saveTestStatus(driver.getSessionId().toString(), status);
	}
}