import com.saucelabs.junit.ConcurrentParameterized;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class ParallelIOSCalculatorTest
{
	IOSCalculatorDriver calculator;
	
	IOSDriver driver;
	DesiredCapabilities desiredCapabilities;
	URL webdriverURL;
	
	@Rule
	public TestName testName = new TestName();
	
	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();
	
	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String deviceOrientation;
	protected String appiumVersion;
	
	public ParallelIOSCalculatorTest(String platformName, String platformVersion, String deviceName, String deviceOrientation, String appiumVersion)
	{
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.deviceOrientation = deviceOrientation;
		this.appiumVersion = appiumVersion;
	}
	
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
		desiredCapabilities.setCapability("testobject_test_name", testName.getMethodName() + " on " + deviceName);
		
		/** set device capabilities **/
		desiredCapabilities.setCapability("platformName", platformName);
		desiredCapabilities.setCapability("platformVersion", platformVersion);
		desiredCapabilities.setCapability("deviceName", deviceName);
		desiredCapabilities.setCapability("appiumVersion", appiumVersion);
		
		/** initialize IOS driver **/
		driver = new IOSDriver(webdriverURL, desiredCapabilities);
		
		/** add test object result watcher to report pass or fail **/
		resultWatcher.setRemoteWebDriver(driver);
		
		/** use "page objects" to encapsulate appium steps **/
		calculator = new IOSCalculatorDriver(driver);
	}
	
	@After
	public void cleanup()
	{
		/** cleanup driver after test **/
		if (driver != null)
		{
			driver.quit();
		}
	}
	
	/** run each test in parallel across multiple devices **/
	@ConcurrentParameterized.Parameters
	public static LinkedList browsersStrings()
	{
		LinkedList<String[]> devices = new LinkedList<>();
		devices.add(new String[]{"iOS", "10.2", "iPhone SE", "portrait", "1.6.5"});
		devices.add(new String[]{"iOS", "10.1.1", "iPad Air", "landscape", "1.6.5"});
		
		return devices;
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
}
