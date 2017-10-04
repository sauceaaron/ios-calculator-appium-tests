import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertEquals;

public class RecordedIOSCalculatorTest {
	
	private IOSDriver driver;
	
	@Before
	public void setUp() throws MalformedURLException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability("platformVersion", "10.2");
		desiredCapabilities.setCapability("testobject_suite_name", "IOS Calculator test");
		desiredCapabilities.setCapability("testobject_test_name", "recorded with Appium Desktop");
		desiredCapabilities.setCapability("privateDevicesOnly", "true");
		desiredCapabilities.setCapability("testobject_api_key", "056DFB79F9A34D6A9012906C36D5FBED");
		
		URL remoteUrl = new URL("https://us1.appium.testobject.com/wd/hub");
		
		driver = new IOSDriver(remoteUrl, desiredCapabilities);
	}
	
	@Test
	public void sampleTest() {
		MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("1");
		el1.click();
		MobileElement el2 = (MobileElement) driver.findElementByAccessibilityId("+");
		el2.click();
		MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("2");
		el3.click();
		MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("=");
		el4.click();
		List<MobileElement> els1 = (List<MobileElement>) driver.findElementsByXPath("//XCUIElementTypeStaticText[1]");
		String result = els1.get(0).getText();
		
		assertEquals("3", result);
		
		els1.get(0).clear();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
}
