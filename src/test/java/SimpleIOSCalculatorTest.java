import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class SimpleIOSCalculatorTest
{
	IOSCalculatorDriver calculator;
	
	IOSDriver driver;
	DesiredCapabilities desiredCapabilities;
	URL webdriverURL;
	
	@Rule
	public TestName testName = new TestName();
	
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();
	
	@Before
	public void setup() throws MalformedURLException
	{
		webdriverURL = new URL("https://us1.appium.testobject.com/wd/hub");
		
		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", testName.getMethodName());
		desiredCapabilities.setCapability("appiumVersion", "1.6.5");
		
		/** set device specific capabilities **/
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability("platformVersion", "10.3");
		desiredCapabilities.setCapability("deviceName", "iPhone 7");
		desiredCapabilities.setCapability("phoneOnly", "true");
		
		driver = new IOSDriver(webdriverURL, desiredCapabilities);
		resultWatcher.setRemoteWebDriver(driver);
	}
	
	@After
	public void cleanup()
	{
		if (driver != null)
		{
			driver.quit();
		}
	}
	
	@Test
	public void add_two_numbers()
	{
		assertTrue(true);
	}
}
