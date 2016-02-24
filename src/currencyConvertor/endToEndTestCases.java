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
	
	@Test(priority = 5)
	public void verifyLowerRateDetailPanel(){
		// verify head line
		String ipCurrency = driver.findElement(By.xpath("//*[@id='quote_currency_code']")).getText();
		String opCurrency = driver.findElement(By.xpath("//*[@id='base_currency_code']")).getText();
		String headLine = ipCurrency+"/"+opCurrency+" Details";
		
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id='infoDetails']")).getText(), headLine);
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id='infoDetails']")).getAttribute("class"), "headline");
		
		//verify validity of conversion rates 
		String validity = driver.findElement(By.xpath("//*[@id='atDateAndRate']")).getText();
		String interBankRate = driver.findElement(By.xpath("//*[@id='interbank_rates_input']")).getAttribute("value");
		String expectedValidity = ipCurrency+"/"+opCurrency+" for the 24-hour period ending "+Util.getPreviousDate()+" 22:00 UTC @ +/- "+interBankRate;
		
		Assert.assertEquals(validity, expectedValidity);
		
		// verify Rate Details sub tag
		String rateDetails = driver.findElement(By.xpath("//*[@id='bidAskSubtitle']")).getText();
		String expectedRateDetails = ipCurrency+"/"+opCurrency+" for the 24-hour period ending";
		String bidDate = driver.findElement(By.xpath("//*[@id='bidAskDate']")).getText();
		String bidTime = driver.findElement(By.xpath("//*[@id='bidAskTime']")).getText();
		
		Assert.assertEquals(rateDetails, expectedRateDetails);
		Assert.assertEquals(bidDate, Util.getPreviousDate());
		Assert.assertEquals(bidTime, "22:00 UTC");
		
		// verify conversion rates tag lines
		String sellingIP = driver.findElement(By.xpath("//*[@id='sellMyCurrency']")).getText();
		String buyingOP = driver.findElement(By.xpath("//*[@id='buyMyCurrency']")).getText();
		
		float sellingMoney = Float.parseFloat(driver.findElement(By.xpath("//*[@id='quote_amount_input']")).getAttribute("value"));
		String sellingTagLine = "Selling "+String.format("%.5f", sellingMoney)+" "+ipCurrency;
		String buyingTagLine = "Buying "+String.format("%.5f", sellingMoney)+" "+ipCurrency;

		String convertionGetMoney = driver.findElement(By.xpath("//*[@id='sellMyCurrencyGet']")).getText();
		String conversionPayMoney = driver.findElement(By.xpath("//*[@id='buyMyCurrencyCost']")).getText();
		
		float buyingMoney = Float.parseFloat(driver.findElement(By.xpath("//*[@id='base_amount_input']")).getAttribute("value"));
		float buyingAvgPay = Float.parseFloat(driver.findElement(By.xpath("//*[@id='bidAskAskAvg']")).getText());
		String amountGet = "you get "+String.format("%.5f", buyingMoney)+" "+opCurrency;
		String amountPaying = "you pay "+String.format("%.5f", buyingAvgPay)+" "+opCurrency;
		
		Assert.assertEquals(sellingIP, sellingTagLine);
		Assert.assertEquals(buyingOP, buyingTagLine);
		Assert.assertEquals(convertionGetMoney, amountGet);
		Assert.assertEquals(conversionPayMoney, amountPaying);
		
	}
	

	@AfterTest
	public void closeBrowser(){
		driver.close();
		System.exit(0);
		
	}
	
}
