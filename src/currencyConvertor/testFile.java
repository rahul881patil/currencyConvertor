package currencyConvertor;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testFile {

public WebDriver driver = null;
	
	@BeforeTest
	public void initializeBrowser(){
		driver = Util.getDriver("firefox");	
	}	
	

	@Test(priority= 10)
	public void checkBaseCurrencySelector() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		
		Thread.sleep(100);
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		
		List<WebElement> currencyBDropDown = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.className("ltr_list_item"));
		currencyBDropDown.get(9).click();
		System.out.println(Util.getBaseCurrencyAbbrevation());
		baseCurrency.click();
		System.out.println("FAV 1st : " + (baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.xpath("//*[@class='code_right']")).get(0).getAttribute("innerHTML")));
		
		
		
		
	}
	
	
	
	//@Test(priority= 1)
	public void checkAutoCompleteQuoteCurrencySelector(){
		
		// verify user should be able to select currency from both drop down	
		
		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='quote_currency_selector']"));
		quoteCurrency.click();
		quoteCurrency.sendKeys("AF");
		List<WebElement> currencyNames = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.xpath("//*[@class='left name']"));
		List<WebElement> currencyCodes = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.xpath("//*[@class='code_right']"));

		int i = 0;
		String currencyInfo = new String();
		while(i < currencyNames.size()){
			
			if (currencyNames.get(i).isDisplayed()){
					currencyInfo = "";
					currencyInfo += currencyNames.get(i).getAttribute("innerHTML")
								 +" "+currencyCodes.get(i).getAttribute("innerHTML");
					System.out.println(currencyInfo);
					Assert.assertTrue(currencyInfo.toLowerCase().contains("af"));
				
			}
			i++;
		}
	}
	
	//@AfterTest
	public void closeBrowser(){
		driver.close();
		System.exit(0);
		
	}
	
}
