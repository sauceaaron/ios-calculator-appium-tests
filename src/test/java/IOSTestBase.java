import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;
import testobject.results.TestObjectResultReporter;
import testobject.results.TestObjectResultWatcher;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class IOSTestBase
{
	protected IOSDriver driver;
	protected DesiredCapabilities desiredCapabilities;
	protected URL remoteURL;
	
	protected String sessionId;
	
	protected boolean status;
	protected boolean PASSED=true;
	protected boolean FAILED=false;
	
	public enum Location { EU, US }
	protected Location location = Location.US; // default
	
	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String appiumVersion;
	protected Boolean phoneOnly = false;
	protected Boolean tabletOnly = false;
	protected Boolean privateDevicesOnly = false;

	protected static final String TESTOBJECT_URL_US = "https://us1.appium.testobject.com/wd/hub";
	protected static final String TESTOBJECT_URL_EU = "https://eu1.appium.testobject.com/wd/hub";

	String TESTOBJECT_API_KEY = System.getenv("TESTOBJECT_API_KEY");

	@Rule
	public TestName testName = new TestName();
	
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
		
//		if (phoneOnly != null) desiredCapabilities.setCapability("phoneOnly", String.valueOf(phoneOnly));
//		if (tabletOnly != null) desiredCapabilities.setCapability("tabletOnly", String.valueOf(tabletOnly));
//		if (privateDevicesOnly != null) desiredCapabilities.setCapability("privateDevicesOnly", String.valueOf(privateDevicesOnly));
		
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
		
		System.out.println("-----DESIRED CAPABILITIES-----\n" + desiredCapabilities);
		
		/** initialize IOS driver **/
		driver = new IOSDriver(remoteURL, desiredCapabilities);
		sessionId = driver.getSessionId().toString();
	}
	
	@After
	public void cleanup()
	{
		/** cleanup driver after test **/
		if (driver != null)
		{
			new TestObjectResultReporter().saveTestStatus(driver.getSessionId().toString(), status);
			driver.quit();
		}
	}
}
