import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class SimpleIOSCalculatorTest
{
	IOSCalculatorDriver calculator;
	
	IOSDriver driver;
	DesiredCapabilities desiredCapabilities;
	URL webdriverURL;
	
	@Rule
	public TestName testName = new TestName();
	
	@Before
	public void setup() throws MalformedURLException
	{
		webdriverURL = new URL("https://us1.appium.testobject.com/wd/hub");
		
		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", testName.getMethodName());
		desiredCapabilities.setCapability("appiumVersion", "1.6.5");
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability("platformVersion", "10.3");
		desiredCapabilities.setCapability("phoneOnly", "yes");
		
		driver = new IOSDriver(webdriverURL, desiredCapabilities);
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
	}
}
