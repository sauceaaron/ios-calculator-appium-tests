import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class SimpleIOSCalculatorTest
{
	IOSCalculatorDriver calculator;
	
	IOSDriver driver;
	DesiredCapabilities desiredCapabilities;
	URL webdriverURL;
	
	@Rule
	public TestName testName = new TestName();
	
	@Rule
	public TestObjectResultWatcher resultWatcher = new TestObjectResultWatcher();
	
	@Before
	public void setup() throws MalformedURLException
	{
		/** specify the appium server (testobject US or EU datacenter) **/
		webdriverURL = new URL("https://us1.appium.testobject.com/wd/hub");
		
		/** specify desired capabilities **/
		desiredCapabilities = new DesiredCapabilities();
		
		/** set testobject capabililites **/
		desiredCapabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", testName.getMethodName());
		
		desiredCapabilities.setCapability("phoneOnly", "false");
		desiredCapabilities.setCapability("tabletOnly", "false");
		desiredCapabilities.setCapability("privateDevicesOnly", "false");
		
		/** set device capabilities **/
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability("platformVersion", "10.2");
		desiredCapabilities.setCapability("deviceName", "iPhone SE");
		desiredCapabilities.setCapability("appiumVersion", "1.6.5");
		desiredCapabilities.setCapability("privateDevicesOnly", "false");
		
		/** initialize IOS driver **/
		driver = new IOSDriver(webdriverURL, desiredCapabilities);
		
		/** add test object result watcher to report pass or fail **/
		resultWatcher.setSessionId(driver.getSessionId().toString());
		
		/** use "page objects" to encapsulate appium steps **/
		calculator = new IOSCalculatorDriver(driver);
	}
	
	@After
	public void cleanup()
	{
		/** cleanup driver after test **/
		if (driver != null)
		{
			new TestObjectResultReporter().saveTestStatus(driver.getSessionId().toString(), true);
			driver.quit();
		}
	}
	
	@Test
	public void add_two_numbers()
	{
		calculator = new IOSCalculatorDriver(driver);
		
		calculator.pressKey("1");
		calculator.pressKey("+");
		calculator.pressKey("2");
		calculator.pressKey("=");
		
		assertEquals(3, calculator.getDisplayedNumber());
	}
	
	@Test
	public void multiply_two_numbers()
	{
		calculator = new IOSCalculatorDriver(driver);
		
		calculator.pressKey("7");
		calculator.pressKey("*");
		calculator.pressKey("8");
		calculator.pressKey("=");
		
		assertEquals(56, calculator.getDisplayedNumber());
	}
	
	@Test
	public void divide_two_numbers()
	{
		calculator = new IOSCalculatorDriver(driver);
		
		calculator.pressKey("9");
		calculator.pressKey("/");
		calculator.pressKey("2");
		calculator.pressKey("=");
		
		assertEquals(5, calculator.getDisplayedNumber());
	}
}
