package testobject.util;

import io.appium.java_client.AppiumDriver;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

public class TestObjectResultWatcher extends TestWatcher
{
	protected String sessionId;
	public boolean status;
	
	protected static final boolean PASSED = true;
	protected static final boolean FAILED = false;
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	@Override
	protected void succeeded(Description description)
	{
		status = PASSED;
		reportTestStatus(description);
	}
	
	@Override
	protected void failed(Throwable e, Description description)
	{
		status = FAILED;
		reportTestStatus(description);
	}
	
	
	protected void reportTestStatus(Description description)
	{
		System.out.println(description.getDisplayName());
		System.out.println("status: " + (status ? "PASSED" : "FAILED"));
		System.out.println("sessionId: " + sessionId);
		
		sendStatus();
		new TestObjectResultReporter().saveTestStatus(sessionId, status);
	}
	
	private void sendStatus()
	{
		String TESTOBJECT_APPIUM_API_ENDPOINT = "https://app.testobject.com/api/rest/v1/appium/";
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(TESTOBJECT_APPIUM_API_ENDPOINT);
		final Response response = resource.path("session")
				.path(sessionId)
				.path("test")
				.request(new MediaType[]{MediaType.APPLICATION_JSON_TYPE})
				.put(Entity.json(Collections.singletonMap("passed", status)));
		
		System.out.println("RESPONSE: " + response.getStatus());
		
	}
}
