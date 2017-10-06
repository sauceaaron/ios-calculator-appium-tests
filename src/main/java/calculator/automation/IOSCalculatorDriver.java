package calculator.automation;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

public class IOSCalculatorDriver implements CalculatorDriver
{
	IOSDriver driver;
	
	public IOSCalculatorDriver(IOSDriver driver)
	{
		this.driver = driver;
	}
	
	public By one = By.id("1");
	public By two = By.id("2");
	public By three = By.id("3");
	public By four = By.id("4");
	public By five = By.id("5");
	public By six = By.id("6");
	public By seven = By.id("7");
	public By eight = By.id("8");
	public By nine = By.id("9");
	public By zero = By.id("0");
	public By add = By.id("+");
	public By subtract = By.id("-");
	public By multiply = By.id("×");
	public By divide = By.id("÷");
	public By equals = By.id("=");
	public By screen = By.xpath("//UIAStaticText");
	public By clear = By.xpath("Idontknow");
	
	public HashMap<String, By> keypad = new HashMap<String, By>() {{
		put("1", one);
		put("2", two);
		put("3", three);
		put("4", four);
		put("5", five);
		put("6", six);
		put("7", seven);
		put("8", eight);
		put("9", nine);
		put("0", zero);
		put("+", add);
		put("-", subtract);
		put("*", multiply);
		put("/", divide);
		put("=", equals);
	}};
	
	public MobileElement getKey(String key)
	{
		return (MobileElement) driver.findElement(keypad.get(key));
	}
	
	public void pressKey(String key)
	{
		getKey(key).click();
	}
	
	public void pressKey(int i)
	{
		pressKey(String.valueOf(i));
	}
	
	public MobileElement getScreen()
	{
		return (MobileElement) driver.findElements(screen).get(0);
	}
	
	public String readScreen()
	{
		return getScreen().getText();
	}

	public int getDisplayedNumber()
	{
		return Integer.parseInt(readScreen());
	}
	
	public void clear()
	{
		// not implemented yet
	}
	
}
