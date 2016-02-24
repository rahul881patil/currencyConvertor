package currencyConvertor;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;




public class endToEndTestCases {
	
	public WebDriver driver = null;
	
	@BeforeTest
	public void initializeBrowser(){
		driver = Util.getDriver("firefox");	
	}
	
	
	@Test(priority = 1)
	public void verifyTitleName(){
		AssertJUnit.assertEquals(driver.getTitle(), "Currency Converter | Foreign Exchange Rates | OANDA");
	}

	@Test(priority = 2)
	public void verifyUpperLeftPanel() throws InterruptedException{
	
		String flag = driver.findElement(By.xpath("//*[@id='quote_currency_flag']")).getAttribute("class");
		String currenyName = driver.findElement(By.xpath("//*[@id='quote_currency_input']")).getAttribute("value");
		String currenyAbbrivation = driver.findElement(By.xpath("//*[@id='quote_currency_code']")).getText();
		String moneyAmount = driver.findElement(By.xpath("//*[@id='quote_amount_input']")).getAttribute("value");
		
		Assert.assertEquals(flag, "USD flag");
		Assert.assertEquals(currenyName, "US Dollar");
		Assert.assertEquals(currenyAbbrivation, "USD");
		Assert.assertEquals(moneyAmount, "1");
		
	}
	
	@Test(priority = 3)
	public void verifyUpperRightPanel() throws InterruptedException{
	
		String flag = driver.findElement(By.xpath("//*[@id='base_currency_flag']")).getAttribute("class");
		String currenyName = driver.findElement(By.xpath("//*[@id='base_currency_input']")).getAttribute("value");
		String currenyAbbrivation = driver.findElement(By.xpath("//*[@id='base_currency_code']")).getText();
		String conversionRate = driver.findElement(By.xpath("//*[@id='ncc_detail_form']")).findElement(By.id("form_base_amount_input_hidden")).getAttribute("value");
		String moneyAmount = driver.findElement(By.xpath("//*[@id='base_amount_input']")).getAttribute("value");
		
		Assert.assertEquals(flag, "EUR flag");
		Assert.assertEquals(currenyName, "Euro");
		Assert.assertEquals(currenyAbbrivation, "EUR");
		Assert.assertEquals(moneyAmount, conversionRate);
	}
	
	@Test(priority = 4)
	public void verifyUpperBottonPanel() {
		String interBankRate = driver.findElement(By.xpath("//*[@id='interbank_rates_input']")).getAttribute("value");
		String date = driver.findElement(By.xpath("//*[@id='end_date_input']")).getAttribute("value");
		
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		Date currentdt = new Date();
		
		Assert.assertEquals(interBankRate, "0%");
		Assert.assertEquals(date, dateFormat.format(currentdt));
		
	}
	

	@AfterTest
	public void closeBrowser(){
		driver.close();
		System.exit(0);
		
	}
	
}
