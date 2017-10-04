import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class IOSTestBase
{
	protected IOSDriver driver;
	protected DesiredCapabilities desiredCapabilities;
	protected URL remoteURL;
	
	public enum Location { EU, US }
	protected Location location = Location.US; // default
	
	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String appiumVersion;
	protected Boolean phoneOnly;
	protected Boolean tabletOnly;
	
	protected static final String TESTOBJECT_URL_US = "https://us1.appium.testobject.com/wd/hub";
	protected static final String TESTOBJECT_URL_EU = "https://eu1.appium.testobject.com/wd/hub";
	
	@Rule
	public TestName testName = new TestName();
	
	@Rule
	public TestObjectResultWatcher watcher = new TestObjectResultWatcher();
	
	public void setStatus(Boolean passed)
	{
		new TestObjectResultReporter().saveTestStatus(driver.getSessionId().toString(), passed);
	}
	
	public URL getRemoteURL(Location location) throws MalformedURLException
	{
		switch (location)
		{
			case US: return new URL(TESTOBJECT_URL_US);
			case EU: return new URL(TESTOBJECT_URL_EU);
			default: throw new RuntimeException("unknown TestObject datacenter location:" + location);
		}
	}
	
	public URL getRemoteURL() throws MalformedURLException
	{
		return getRemoteURL(this.location);
	}
	
	public DesiredCapabilities getDesiredCapabilities(String platformName, String platformVersion, String deviceName, String appiumVersion)
	{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		/** set testobject capabililites **/
		desiredCapabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", testName.getMethodName() + " on " + deviceName);
		
		/** set device capabilities **/
		if (platformName != null) desiredCapabilities.setCapability("platformName", platformName);
		if (platformVersion != null) desiredCapabilities.setCapability("platformVersion", platformVersion);
		if (deviceName != null) desiredCapabilities.setCapability("deviceName", deviceName);
		if (appiumVersion != null) desiredCapabilities.setCapability("appiumVersion", appiumVersion);
		
		if (phoneOnly != null) desiredCapabilities.setCapability("phoneOnly", String.valueOf(phoneOnly));
		if (tabletOnly != null) desiredCapabilities.setCapability("tabletOnly", String.valueOf(tabletOnly));
		
		return desiredCapabilities;
	}
	
	public DesiredCapabilities getDesiredCapabilities()
	{
		return getDesiredCapabilities(this.platformName, this.platformVersion, this.deviceName, this.appiumVersion);
	}
	
	public String getTestName()
	{
		return this.getClass().getSimpleName() + " " + testName.getMethodName();
	}
	
	@Before
	public void setup() throws MalformedURLException
	{
		/** specify the appium server (testobject US or EU datacenter) **/
		remoteURL = getRemoteURL();
		
		/** specify desired capabilities **/
		desiredCapabilities = getDesiredCapabilities(platformName, platformVersion, deviceName, appiumVersion);
		
		/** initialize IOS driver **/
		driver = new IOSDriver(remoteURL, desiredCapabilities);
		
		/** add test object result watcher to report pass or fail **/
		watcher.bindDriver(driver);
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
}
