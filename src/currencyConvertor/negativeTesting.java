package currencyConvertor;


/*
 *  Author : Rahul Patil
 *  Test Scripts : Currency conversion page
 *   
 */

import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class negativeTesting {

public WebDriver driver = null;
	
	@BeforeTest
	public void initializeBrowser(){
		driver = Util.getDriver("firefox");	
	}	
	
	@AfterTest
	public void closeBrowser(){
		driver.close();
		System.exit(0);
		
	}
	
	@Test(priority=0)
	public void checkQuotedCurrencySelectorsForInvalidInput() throws InterruptedException{
		
		// verify for invalid characters no value should be populated in Quoted currency	
		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='quote_currency_selector']"));
		quoteCurrency.click();
		quoteCurrency.sendKeys("#");
		List<WebElement> currencyQDropDown = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.xpath("//*[@class='left name']"));
		int i=0;
		while(i< currencyQDropDown.size()){
			AssertJUnit.assertEquals(false, currencyQDropDown.get(i).isDisplayed());
			i++;
		}
	}
	
	@Test(priority=1)
	public void checkBaseCurrencySelectorsForInvalidInput(){
		
		// verify for invalid characters no value should be populated in Base currency 
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		baseCurrency.sendKeys("#");
		List<WebElement> currencyBDropDown = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.xpath("//*[@class='left name']"));
		int i=0;
		while(i< currencyBDropDown.size()){
			AssertJUnit.assertEquals(false, currencyBDropDown.get(i).isDisplayed());
			i++;
		}
	}
	
	@Test(priority = 2)
	public void checkAmountTextBoxForDecimalDigits() throws InterruptedException{
			
		WebElement  quoteCurrenyAmount= driver.findElement(By.xpath("//*[@id='quote_amount_input']"));

		// verify amount accepts only 5 or less digits after decimal point
		quoteCurrenyAmount.sendKeys("12.3456337");
		String baseCurrency = driver.findElement(By.xpath("//*[@id='base_amount_input']")).getAttribute("value");
		String[] sellingAmount = driver.findElement(By.xpath("//*[@id='sellMyCurrency']")).getText().split(" ");
		String[] buyingAmount = driver.findElement(By.xpath("//*[@id='buyMyCurrency']")).getText().split(" ");
		
		sellingAmount[1] = sellingAmount[1].substring(sellingAmount[1].indexOf('.'), sellingAmount[1].length()-1);
		buyingAmount[1] = buyingAmount[1].substring(buyingAmount[1].indexOf('.'), buyingAmount[1].length()-1);
		baseCurrency = baseCurrency.substring(baseCurrency.indexOf('.'), baseCurrency.length()-1);
		
		assertTrue(5 >= baseCurrency.length());
		assertTrue(5 >= sellingAmount[1].length());
		assertTrue(5 >= buyingAmount[1].length());	
		
	}
	
	@Test(priority = 3)
	public void checkAmountTextBoxForInvalidInput() throws InterruptedException{
			
		WebElement  quoteCurrenyAmount= driver.findElement(By.xpath("//*[@id='quote_amount_input']"));

		// verify amount accepts only 5 or less digits after decimal point
		quoteCurrenyAmount.sendKeys("scdcdc");
		String[] sellingRateTag = driver.findElement(By.xpath("//*[@id='sellMyCurrency']")).getText().split(" ");
		String[] buyingRateTag = driver.findElement(By.xpath("//*[@id='buyMyCurrency']")).getText().split(" ");
	
		AssertJUnit.assertEquals("-", driver.findElement(By.xpath("//*[@id='base_amount_input']")).getAttribute("value"));	
		Assert.assertEquals(sellingRateTag[1], "-");
		Assert.assertEquals(buyingRateTag[1], "-");
		
	}
	
	@Test(priority = 4)
	public void checkAmountTextBoxForNegativeValues(){
		
		// only numeric value are accepted
		driver.findElement(By.xpath("//*[@id='quote_amount_input']")).clear();
		driver.findElement(By.xpath("//*[@id='quote_amount_input']")).sendKeys("-1");

		AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='base_amount_input']")).getAttribute("value"), "-");

	}
	
	@Test(priority = 5)
	public void checkQuotedPreSelectedValue() throws InterruptedException{
		
		// verify currency remains same if user does not select any from drop down
		String preSelectedCurrency = Util.getQuotedCurrencyAbbrevation();

		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='quote_currency_selector']"));
		quoteCurrency.click();
		quoteCurrency.sendKeys("dqddc");
		quoteCurrency.click();
		
		Assert.assertEquals(Util.getQuotedCurrencyAbbrevation(), preSelectedCurrency);
		
	}
	
	@Test(priority = 6)
	public void checkBasePreSelectedValue() throws InterruptedException{
		
		// verify currency remains same if user does not select any from drop down
		String preSelectedCurrency = Util.getBaseCurrencyAbbrevation();

		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		baseCurrency.sendKeys("dqddc");
		baseCurrency.click();
		
		Assert.assertEquals(Util.getBaseCurrencyAbbrevation(), preSelectedCurrency);
		
	}
	
	@Test(priority = 7)
	public void checkTomorrowsDateFromDatePicker() throws InterruptedException{
		
		driver.findElement(By.xpath("//*[@id='date_control']"));
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id='date_forward']")).getAttribute("class"), "forward disabled");
	}
	
	@Test(priority = 8)
	public void checkAmountAfterFlipButton(){
		
		// verify that flip button is working as expected
		
				String quoteCurrency = Util.getQuotedCurrencyAbbrevation();
				String baseCurrency = Util.getBaseCurrencyAbbrevation();
				String moneyAmount = driver.findElement(By.xpath("//*[@id='quote_amount_input']")).getAttribute("value");
				
				
				driver.findElement(By.xpath("//*[@id='flipper']")).click();
				
				AssertJUnit.assertEquals(Util.getQuotedCurrencyAbbrevation(), baseCurrency);
				AssertJUnit.assertEquals(Util.getBaseCurrencyAbbrevation(), quoteCurrency);
				AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='quote_amount_input']")).getAttribute("value"), moneyAmount);
	}
	
	
	
	
	
}
