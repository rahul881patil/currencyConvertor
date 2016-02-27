package currencyConvertor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// utility class
public class Util {

	public static String baseUrl = "http://www.oanda.com/currency/converter/";
	public static WebDriver driver = null;
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
	
	// Initialize driver with chrom driver
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

		if(format == "MMM dd, yyyy"){
			DateFormat monthDate = new SimpleDateFormat(format);
			return monthDate.format(Util.getCurrentAppDate());
		}else{
			return dateFormat.format(Util.getCurrentAppDate());
		}
	}
	
	// get current web site Date
	public static String getCurrentAppDate(){
		return driver.findElement(By.xpath("//*[@id='end_date_input']")).getAttribute("value");
	}

	// get previous date
	public static String getPreviousDate() throws ParseException{
		
		String currentDate = getCurrentAppDate();
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		SimpleDateFormat day = new SimpleDateFormat("EEEE");
		
		Date myDate = dateFormat.parse(currentDate);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		String myday = day.format(Calendar.DAY_OF_WEEK);
		String finalString = myday + ", "+ currentDate;
		dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		myDate = dateFormat.parse(finalString);
		cal.add(Calendar.DATE, -1);
		
		
		return dateFormat.format(cal.getTime());
	}
	
	// get previous date as per calculation with format e.g. "Feb 4"
		public static String getPreviousDate(int days) throws ParseException{
			
			DateFormat monthDate = new SimpleDateFormat("MMM d");
			String currentDate = getCurrentAppDate();
			Date myDate = monthDate.parse(currentDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(myDate);

			cal.add(Calendar.DATE, -days);

			return monthDate.format(cal.getTime());
		}
		
	// get selected currency abbreviation
	public static String getQuotedCurrencyAbbrevation(){
		return driver.findElement(By.xpath("//*[@id='quote_currency_code']")).getText();	
	}
	
	// get output currency
	public static String getBaseCurrencyAbbrevation(){
		return driver.findElement(By.xpath("//*[@id='base_currency_code']")).getText();
	}
	
	// getCurrency selector drop down list
	public static WebElement getQutoteCurrencySelectorDropDown(){
		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='quote_currency_selector']"));
		quoteCurrency.click();
		return quoteCurrency;
	}
	
	// getCurrency selector drop down list
	public static List<WebElement> getBaseCurrencySelectorDropDown(){
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		List<WebElement> currencyDropDown = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.className("ltr_list_item"));
		return currencyDropDown;
	}
	
	
}
