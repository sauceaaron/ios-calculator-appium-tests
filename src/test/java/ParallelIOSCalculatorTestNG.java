import calculator.automation.IOSCalculatorDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelIOSCalculatorTestNG extends IOSTestBase
{
	IOSCalculatorDriver calculator;
	
	@Test(dataProvider = "devices")
	public void subtract_two_numbers(String platformName, String platformVersion, String deviceName, String appiumVersion, Method method) throws MalformedURLException
	{
		remoteURL = getRemoteURL(Location.US);
		desiredCapabilities = getDesiredCapabilities(platformName, platformVersion, deviceName, appiumVersion);
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", method.getName());
		
		driver = new IOSDriver(remoteURL, desiredCapabilities);
		
		calculator = new IOSCalculatorDriver(driver);
		
		calculator.pressKey("10");
		calculator.pressKey("-");
		calculator.pressKey("1");
		calculator.pressKey("=");
		
		try
		{
			assertThat(calculator.readScreen()).isEqualTo("9.0");
			status = PASSED;
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
			status = FAILED;
		}
	}
	
	@DataProvider(name="devices", parallel=true)
	public static Object[][] specifyDevices()
	{
		Object[][] devices = new Object[][] {
				new String[]{"iOS", "10.2", "iPhone SE", "1.6.5" },
				new String[]{"iOS", "10.1.1", "iPad Air", "1.6.5"},
		};
		
		return devices;
	}
}
