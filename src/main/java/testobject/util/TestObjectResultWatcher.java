package testobject.util;

import io.appium.java_client.AppiumDriver;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestObjectResultWatcher extends TestWatcher
{
	protected AppiumDriver driver;
	
	public enum TestStatus { PASSED, FAILED }
	public TestStatus testStatus;
	
	protected static final boolean PASSED = true;
	protected static final boolean FAILED = false;
	
	public void setDriver(AppiumDriver driver)
	{
		this.driver = driver;
	}
	
	public void reportTestStatus(String sessionId, boolean status)
	{
		System.out.println("reporting test status: " + (status ? "PASSED" : "FAILED"));
		new TestObjectResultReporter().saveTestStatus(sessionId, status);
	}
	
	public void reportTestStatus(String sesssionId)
	{
		reportTestStatus(sesssionId, testStatus == TestStatus.PASSED);
	}
	
	public void reportTestStatus()
	{
		reportTestStatus(driver.getSessionId().toString());
	}
	
	@Override
	protected void succeeded(Description description)
	{
		testStatus = TestStatus.PASSED;
		reportTestStatus(driver.getSessionId().toString(), PASSED);
	}
	
	@Override
	protected void failed(Throwable e, Description description)
	{
		testStatus = TestStatus.FAILED;
		reportTestStatus(driver.getSessionId().toString(), FAILED);
	}
	
}
