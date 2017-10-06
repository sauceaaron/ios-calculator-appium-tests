import calculator.automation.IOSCalculatorDriver;
import com.saucelabs.junit.ConcurrentParameterized;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class ParallelIOSCalculatorTest extends IOSTestBase
{
	IOSCalculatorDriver calculator;
	
	public ParallelIOSCalculatorTest(String platformName, String platformVersion, String deviceName, String appiumVersion)
	{
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.appiumVersion = appiumVersion;
	}
	
	/** run each test in parallel across multiple devices **/
	@ConcurrentParameterized.Parameters
	public static LinkedList runOnTheseDevices()
	{
		LinkedList<String[]> devices = new LinkedList<String[]>();
		devices.add(new String[]{"iOS", "10.2", "iPhone SE", "1.6.5"});
		devices.add(new String[]{"iOS", "10.1.1", "iPad Air", "1.6.5"});
		
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
		
		assertEquals(5, calculator.getDisplayedNumber());
	}
}
