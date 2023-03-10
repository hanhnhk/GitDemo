import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class RegressionTestCase {

	static WebDriver driver;

	public static boolean VerifyRegressionYahooLandingPageTitle(JSONObject json) {
		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();

		// your test case steps here
		setUp();
		driver.get("https://vn.yahoo.com/?p=us");
		try {
			if (!driver.getTitle().contains(json.get("Expected").toString())) {
				Global.errMsg = methodName + "> Yahoo VN title not found.  Expected:" + json.get("Expected").toString() + " - Actual: " + driver.getTitle();
				System.err.println("\n" + Global.errMsg + "\n");
				
				tearDown();
				return false;
			}
		} catch (Error e) {
		}

		tearDown();
		return true;
	}
	
	public static boolean VerifySumTwoDigits(JSONObject json) {
		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();

		// your test case steps here
		setUp();
		driver.get("https://www.calculator.net/");
		try {
			List<Integer> numbers= new ArrayList<>();
			numbers.add(7);
			numbers.add(4);
			Integer sum = 11;
			
			List<WebElement> digitElements = findDigitElement(driver, numbers);
			WebElement sumOperator= findElement(driver, "//span[@class='sciop']", "+");			
			for(int i = 0; i < digitElements.size() - 1; i++) {
				digitElements.get(i).click();
				sumOperator.click();
			}
			digitElements.get(digitElements.size()-1).click();			
			String result = driver.findElement(By.id("sciOutPut")).getText().trim();
			if(!result.equals(sum.toString())) {
				Global.errMsg = methodName + "> Sum 2 digits incorrectly.  Expected:" + sum + " - Actual: " + 0;
				System.err.println("\n" + Global.errMsg + "\n");
				
				tearDown();
				return false;
			}
		} catch (Error e) {
		}

		tearDown();
		return true;
	}
	
	public static List<WebElement> findDigitElement(WebDriver driver, List<Integer> nums){		
		List<WebElement> elements = new ArrayList<>();
		List<WebElement> digitElements = driver.findElements(By.xpath("//span[@class='scinm']"));
		Integer num;
		for(int i = 0; i< nums.size(); i++) {
			num= (Integer) nums.get(i);
			for(int j= 0; j< digitElements.size(); j++)
			{
				if(digitElements.get(j).getText().equals(num.toString())) {
					elements.add(digitElements.get(j));
					break;
				}
			}
		}			
		return elements;
	}
	
	public static WebElement findElement(WebDriver driver, String xPath, String text) {		
		List<WebElement> elements = driver.findElements(By.xpath(xPath));
		for(int i = 0; i<elements.size(); i++)
		{
			if(elements.get(i).getText().equals(text)) {
				return elements.get(i);
			}
		}
		return null;
	}
	
	
	

	// utility method
	public static void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		try {
			driver = new ChromeDriver(options);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	public static void tearDown() {
		driver.quit();
	}
}
