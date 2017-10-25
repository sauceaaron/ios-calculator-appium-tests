import calculator.automation.IOSCalculatorDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import testobject.results.TestObjectResultReporter;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleParallelIOSTestNG
{
	@Test(dataProvider = "devices")
	public void subtract_two_numbers(String platformName, String platformVersion, String deviceName, String appiumVersion, Method method) throws MalformedURLException
	{
		/** specify the appium server (testobject US or EU datacenter) **/
		URL remoteURL = new URL("https://us1.appium.testobject.com/wd/hub");
		
		/** specify desired capabilities **/
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		/** set testobject specific capabilities **/
		desiredCapabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", method.getName());
		
		/** set device specific capabilities **/
		desiredCapabilities.setCapability("platformName", platformName);
		desiredCapabilities.setCapability("platformVersion", platformVersion);
		desiredCapabilities.setCapability("deviceName", deviceName);
		desiredCapabilities.setCapability("appiumVersion", appiumVersion);
		
		/** set optional device groupings **/
		desiredCapabilities.setCapability("phoneOnly", "false");
		desiredCapabilities.setCapability("tabletOnly", "false");
		desiredCapabilities.setCapability("privateDevicesOnly", "false");
		
		System.out.println("desiredCapabilities: " + desiredCapabilities);
		
		/** initialize IOS driver **/
		IOSDriver driver = new IOSDriver(remoteURL, desiredCapabilities);
		
		/** use "page objects" to encapsulate appium steps **/
		IOSCalculatorDriver calculator = new IOSCalculatorDriver(driver);
		
		/** perform test steps **/
		calculator.pressKey("10");
		calculator.pressKey("-");
		calculator.pressKey("1");
		calculator.pressKey("=");
		
		/** verify expected conditions **/
		boolean testStatus;
		try
		{
			assertThat(calculator.readScreen()).isEqualTo("9.0");
			testStatus = true;
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
			testStatus = false;
		}
		
		/** report test result back to testobject **/
		String sessionId = driver.getSessionId().toString();
		new TestObjectResultReporter().saveTestStatus(sessionId, testStatus);
	}
	
	@DataProvider(name="devices", parallel=true)
	public static Object[][] specifyDevices()
	{
		Object[][] devices = new Object[][] {
				new String[]{"iOS", "10.2", "iPhone SE", "1.7.1" },
				new String[]{"iOS", "10.1.1", "iPad Air", "1.7.1"},
		};
		
		return devices;
	}
}