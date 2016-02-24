package currencyConvertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// utility class
public class Util {

	public static String baseUrl = "http://www.oanda.com/currency/converter/";
	public static WebDriver driver = null;
	private static Calendar cal = null;
	private static DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
	
	
	// get url of currency converter 
	public static String getUrl(){
			return baseUrl;
	}
	
	// Initialize driver with Fire Fox driver
	public static void initFirefox(){
		driver = new FirefoxDriver();
		driver.get(baseUrl);
	}
	
	// Initialize driver with chrome driver
	public static void initChromeDriver(){
		System.setProperty("webdriver.chrome.driver","/Users/Rahul/Documents/workspace/testing/seleniumWebdriver/src/seleniumWebdriver/chromedriver");
		driver = new ChromeDriver();
		driver.get(baseUrl);
	}
	
	// get Driver
	public static WebDriver getDriver(String browser){ 
	
		if( browser.toLowerCase() == "firefox"){
			initFirefox();
			return driver;
		}else if(browser.toLowerCase() == "chrome"){
			initChromeDriver();
			return driver;
		}else{
			System.out.println("Invalid browser request");
			return null;
		}		
	}
	
	// get current date
	public static String getCurrentDate(String format){
		
		cal = Calendar.getInstance();
		if(format == "MMM dd, yyyy"){
			DateFormat monthDate = new SimpleDateFormat(format);
			return monthDate.format(cal.getTime());
		}else{
			return dateFormat.format(cal.getTime());
		}
	}

	// get previous date
	public static String getPreviousDate(){
		
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	
	// get previous date as per calculation with format e.g. "Feb 4"
		public static String getPreviousDate(int days){
			DateFormat monthDate = new SimpleDateFormat("MMM d");
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -days);
			return monthDate.format(cal.getTime());
		}
		
	// get selected currency
	public static String getSelectedCurrency(){
		return driver.findElement(By.xpath("//*[@id='quote_currency_code']")).getText();	
	}
	
	
	// get output currency
	public static String getOutputCurrency(){
		return driver.findElement(By.xpath("//*[@id='base_currency_code']")).getText();
	}
	
	
}
