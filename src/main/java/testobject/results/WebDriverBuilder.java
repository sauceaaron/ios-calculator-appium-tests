package testobject.results;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverBuilder
{
	String TESTOBJECT_API_KEY = System.getenv("TESTOBJECT_API_KEY");
	String APPIUM_VERSION = "1.6.5";
	
	String TESTOBJECT_URL_US = "https://us1.appium.testobject.com/wd/hub";
	String TESTOBJECT_URL_EU = "https://eu1.appium.testobject.com/wd/hub";
	
	public WebDriver createDriver(String platformName, String platformVersion, String deviceName, TestCase test) throws MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
		
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("platformVersion", platformName);
		capabilities.setCapability("deviceName", deviceName);
		
		capabilities.setCapability("onlyPrivateDevices", false);
		capabilities.setCapability("onlyPhones", false);
		capabilities.setCapability("onlyTablets", false);
		
		capabilities.setCapability("appiumVersion", APPIUM_VERSION);
		
		capabilities.setCapability("testobject_suite_name", test.suiteName);
		capabilities.setCapability("testobject_test_name", test.testName);
		
		URL url = new URL(TESTOBJECT_URL_US);
		
		switch (platformName.toUpperCase())
		{
			case "IOS":
				return new IOSDriver(url, capabilities);
			case "ANDROID":
				return new AndroidDriver(url, capabilities);
			default:
				throw new RuntimeException("unknown platform name:: " + platformName);
		}
	}
}
