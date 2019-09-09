import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class AndroidCalculatorEmulatorTest
{
	String SAUCE_URL = "https://ondemand.saucelabs.com/wd/hub";
	String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	String SELENIUM_PLATFORM = System.getenv("SELENIUM_PLATFORM");
	String SELENIUM_BROWSER = System.getenv("SELENIUM_BROWSER");
	String SELENIUM_VERSION = System.getenv("SELENIUM_VERSION");

	String filename = "Calculator.apk";

	AppiumDriver driver;
	SauceREST api;

	@Before
	public void setup() throws IOException
	{
		api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

		File file = getFile(filename);
		String checksum = api.uploadFile(file, filename, false);
		System.out.println(checksum);
	}


	@Test
	public void test() throws MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("username", SAUCE_USERNAME);
		capabilities.setCapability("accessKey", SAUCE_ACCESS_KEY);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0");
		capabilities.setCapability("deviceName", "Android GoogleAPI Emulator");
		capabilities.setCapability("browserName", "");
		capabilities.setCapability("app", "sauce-storage:" + filename);
		capabilities.setCapability("name", "Android Calculator Test");

		driver = new AndroidDriver(new URL(SAUCE_URL), capabilities);


		MobileElement seven = (MobileElement) driver.findElementById("net.ludeke.calculator:id/digit7");
		MobileElement times = (MobileElement) driver.findElementByAccessibilityId("multiply");
		MobileElement eight = (MobileElement) driver.findElementById("net.ludeke.calculator:id/digit8");
		MobileElement equals = (MobileElement) driver.findElementByAccessibilityId("equals");

		seven.click();
		times.click();
		eight.click();
		equals.click();

		MobileElement field = (MobileElement) driver.findElementsByXPath("//android.widget.EditText").get(0);
		String result = field.getText().trim();
		System.out.println(result);

		assertThat(result).isEqualTo("56");

		driver.quit();
	}

	public File getFile(String filename)
	{
		String userDir = System.getProperty("user.dir");
		System.out.println(userDir);

		String filePath = getClass().getClassLoader().getResource(filename).getFile();
		System.out.println(filePath);

		return new File(filePath);
	}
}
