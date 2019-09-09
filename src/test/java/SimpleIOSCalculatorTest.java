import calculator.automation.IOSCalculatorDriver;

import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;
import testobject.results.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class SimpleIOSCalculatorTest
{
	IOSCalculatorDriver calculator;

	IOSDriver driver;
	DesiredCapabilities desiredCapabilities;
	URL webdriverURL;
	TestResult result;

	protected boolean status;
	protected boolean PASSED=true;
	protected boolean FAILED=false;

	String TESTOBJECT_API_KEY = System.getenv("TESTOBJECT_API_KEY");

	@Rule
	public TestName testName = new TestName();

	@Rule
	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

	@Before
	public void setup() throws MalformedURLException
	{
		/** specify the appium server (testobject US or EU datacenter) **/
		webdriverURL = new URL("https://us1.appium.testobject.com/wd/hub");

		/** specify desired capabilities **/
		desiredCapabilities = new DesiredCapabilities();

		/** set testobject capabililites **/
		desiredCapabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", testName.getMethodName());
		
		/** set device capabilities **/
		desiredCapabilities.setCapability("platformName", "iOS");
//		desiredCapabilities.setCapability("platformVersion", "11.4");
		desiredCapabilities.setCapability("deviceName", "iPhone SE");
//		desiredCapabilities.setCapability("appiumVersion", "1.7.2");
//		desiredCapabilities.setCapability("orientation", "PORTRAIT");
//		desiredCapabilities.setCapability("phoneOnly", "false");
//		desiredCapabilities.setCapability("tabletOnly", "false");
//		desiredCapabilities.setCapability("privateDevicesOnly", "true");
		
		System.out.println("-----DESIRED CAPABILITIES-----\n" + desiredCapabilities);
		
		/** initialize IOS driver **/
		driver = new IOSDriver(webdriverURL, desiredCapabilities);

		/** add test object result watcher to report pass or fail **/
		resultWatcher.setRemoteWebDriver(driver);
		result = new TestResult(driver.getSessionId().toString());

		/** use "page objects" to encapsulate appium steps **/
		calculator = new IOSCalculatorDriver(driver);
	}
	
	@Test
	public void add_two_numbers()
	{
		calculator.pressKey("1");
		calculator.pressKey("+");
		calculator.pressKey("2");
		calculator.pressKey("=");

		String output = calculator.readScreen();
		System.out.println("CALCULATOR GOT VALUE: " + output);

		try {
			assertEquals("3.0", output);
			status = PASSED;
		} catch (AssertionError e)
		{
			e.printStackTrace();
			status = FAILED;
		}
	}
	
	@Test
	public void multiply_two_numbers()
	{
		calculator.pressKey("7");
		calculator.pressKey("*");
		calculator.pressKey("8");
		calculator.pressKey("=");

		String output = calculator.readScreen();
		System.out.println("CALCULATOR GOT VALUE: " + result);

		try {
			assertEquals("56.0", output);
			status = PASSED;
		} catch (AssertionError e)
		{
			e.printStackTrace();
			status = FAILED;
		}
	}

	/** this should fail **/
	@Test
	public void divide_two_numbers()
	{
		calculator.pressKey("9");
		calculator.pressKey("/");
		calculator.pressKey("2");
		calculator.pressKey("=");
		
		String output = calculator.readScreen();
		System.out.println("CALCULATOR GOT VALUE: " + result);

		assertEquals("3.0", output);

		try {
			assertEquals("3.0", output);
			status = PASSED;
		} catch (AssertionError e)
		{
			e.printStackTrace();
			status = FAILED;
		}
	}
	
	@After
	public void cleanup()
	{
		if (driver != null)
		{


			/** report test result **/
			System.out.println("status: " + status);
			new TestObjectResultReporter().saveTestStatus(driver.getSessionId().toString(), status);

			/** cleanup driver after test **/
			driver.quit();
		}
	}
}
