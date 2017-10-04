import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class SimpleIOSCalculatorTest
{
	IOSCalculatorDriver calculator;
	
	IOSDriver driver;
	DesiredCapabilities desiredCapabilities;
	URL webdriverURL;
	
	@Before
	public void setup() throws MalformedURLException
	{
	}
	
	@After
	public void cleanup()
	{
	
	}
	
	@Test
	public void add_two_numbers()
	{
	}
}
