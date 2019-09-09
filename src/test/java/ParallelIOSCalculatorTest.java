import calculator.automation.IOSCalculatorDriver;
import com.saucelabs.junit.ConcurrentParameterized;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
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
//		devices.add(new String[]{"iOS", "10.2", "iPhone SE", "1.8.0"});
//		devices.add(new String[]{"iOS", "11.4", "iPad Air 2", "1.8.0"});
		devices.add(new String[]{"iOS", null, "iPhone.*", null});
		devices.add(new String[]{"iOS", null, "iPad.*", null});

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
		
		try {
			assertThat(calculator.readScreen()).isEqualTo("3.0");
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
		calculator = new IOSCalculatorDriver(driver);

		calculator.pressKey("7");
		calculator.pressKey("*");
		calculator.pressKey("8");
		calculator.pressKey("=");

		String output = calculator.readScreen();
		System.out.println("CALCULATOR GOT VALUE: " + output);

		try {
			assertThat(calculator.readScreen()).isEqualTo("56.0");
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
		calculator = new IOSCalculatorDriver(driver);

		calculator.pressKey("9");
		calculator.pressKey("/");
		calculator.pressKey("2");
		calculator.pressKey("=");

		String output = calculator.readScreen();
		System.out.println("CALCULATOR GOT VALUE: " + output);

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
}