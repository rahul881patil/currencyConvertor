package currencyConvertor;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;

import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.*;
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
	public void verifyDefaultQuoteCurrency() throws InterruptedException{
	
		String flag = driver.findElement(By.xpath("//*[@id='quote_currency_flag']")).getAttribute("class");
		String currenyName = driver.findElement(By.xpath("//*[@id='quote_currency_input']")).getAttribute("value");
		String currenyAbbrivation = driver.findElement(By.xpath("//*[@id='quote_currency_code']")).getText();
		String moneyAmount = driver.findElement(By.xpath("//*[@id='quote_amount_input']")).getAttribute("value");
		
		AssertJUnit.assertEquals(flag, "USD flag");
		AssertJUnit.assertEquals(currenyName, "US Dollar");
		AssertJUnit.assertEquals(currenyAbbrivation, "USD");
		AssertJUnit.assertEquals(moneyAmount, "1");
		
	}
	
	@Test(priority = 3)
	public void verifyDefaultBaseCurrency() throws InterruptedException{
	
		String flag = driver.findElement(By.xpath("//*[@id='base_currency_flag']")).getAttribute("class");
		String currenyName = driver.findElement(By.xpath("//*[@id='base_currency_input']")).getAttribute("value");
		String currenyAbbrivation = driver.findElement(By.xpath("//*[@id='base_currency_code']")).getText();
		String conversionRate = driver.findElement(By.xpath("//*[@id='ncc_detail_form']")).findElement(By.id("form_base_amount_input_hidden")).getAttribute("value");
		String moneyAmount = driver.findElement(By.xpath("//*[@id='base_amount_input']")).getAttribute("value");
		
		AssertJUnit.assertEquals(flag, "EUR flag");
		AssertJUnit.assertEquals(currenyName, "Euro");
		AssertJUnit.assertEquals(currenyAbbrivation, "EUR");
		AssertJUnit.assertEquals(moneyAmount, conversionRate);
	}
	
	@Test(priority = 4)
	public void verifyDefaultFavoritesListInQuoteCurrencySelectorDropDown(){
		
		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		quoteCurrency.click();
		
		List<WebElement> currencyCodes = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.xpath("//*[@class='code_right']"));
		String[] favCurrencyCodes = {"EUR", "USD", "GBP", "CAD", "AUD", };
		
		for(int i=0; i<5; i++){
			Assert.assertEquals(currencyCodes.get(i).getAttribute("innerHTML"), favCurrencyCodes[i]);
		}
		
	}
	
	@Test(priority = 4)
	public void verifyDefaultFavoritesListInBaseCurrencySelectorDropDown(){
		
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		
		List<WebElement> currencyCodes = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.xpath("//*[@class='code_right']"));
		String[] favCurrencyCodes = {"EUR", "USD", "GBP", "CAD", "AUD", };
		
		for(int i=0; i<5; i++){
			Assert.assertEquals(currencyCodes.get(i).getAttribute("innerHTML"), favCurrencyCodes[i]);
		}
		
	}
	
	@Test(priority = 4)
	public void checkFlipperButton(){
		
		// verify that flip button is working as expected
		
		String quoteCurrency = Util.getQuotedCurrencyAbbrevation();
		String baseCurrency = Util.getBaseCurrencyAbbrevation();
		
		driver.findElement(By.xpath("//*[@id='flipper']")).click();
		
		AssertJUnit.assertEquals(Util.getQuotedCurrencyAbbrevation(), baseCurrency);
		AssertJUnit.assertEquals(Util.getBaseCurrencyAbbrevation(), quoteCurrency);
		
	}
	
	@Test(priority = 5)
	public void verifyInterBankAndDefaultDate() {
		String interBankRate = driver.findElement(By.xpath("//*[@id='interbank_rates_input']")).getAttribute("value");
		String date = driver.findElement(By.xpath("//*[@id='end_date_input']")).getAttribute("value");

		AssertJUnit.assertEquals(interBankRate, "0%");
		AssertJUnit.assertEquals(date, Util.getCurrentAppDate());
		
	}
	
	@Test(priority = 6)
	public void verifyRateDetailPanel() throws ParseException{
		// verify head line
		String ipCurrency = Util.getQuotedCurrencyAbbrevation();
		String opCurrency = Util.getBaseCurrencyAbbrevation();
		String headLine = ipCurrency+"/"+opCurrency+" Details";
		
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='infoDetails']")).getText(), headLine);
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='infoDetails']")).getAttribute("class"), "headline");
		
		//verify validity of conversion rates 
		String validity = driver.findElement(By.xpath("//*[@id='atDateAndRate']")).getText();
		String interBankRate = driver.findElement(By.xpath("//*[@id='interbank_rates_input']")).getAttribute("value");
		String expectedValidity = ipCurrency+"/"+opCurrency+" for the 24-hour period ending "+Util.getPreviousDate()+" 22:00 UTC @ +/- "+interBankRate;
		
		AssertJUnit.assertEquals(validity, expectedValidity);
		
		// verify Rate Details sub tag
		String rateDetails = driver.findElement(By.xpath("//*[@id='bidAskSubtitle']")).getText();
		String expectedRateDetails = ipCurrency+"/"+opCurrency+" for the 24-hour period ending";
		String bidDate = driver.findElement(By.xpath("//*[@id='bidAskDate']")).getText();
		String bidTime = driver.findElement(By.xpath("//*[@id='bidAskTime']")).getText();
		
		AssertJUnit.assertEquals(rateDetails, expectedRateDetails);
		AssertJUnit.assertEquals(bidDate, Util.getPreviousDate());
		AssertJUnit.assertEquals(bidTime, "22:00 UTC");
		
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
		
		AssertJUnit.assertEquals(sellingIP, sellingTagLine);
		AssertJUnit.assertEquals(buyingOP, buyingTagLine);
		AssertJUnit.assertEquals(convertionGetMoney, amountGet);
		AssertJUnit.assertEquals(conversionPayMoney, amountPaying);
	}
	
	@Test(priority = 7)
	public void checkCurrencyChart() throws ParseException{
		
		// verify markers date on chart
		
		// verify Graph default condition 
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='range30']")).isSelected(), true);
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='range60']")).isSelected(), false);
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//*[@id='range90']")).isSelected(), false);
		
		// verify chart default dates on x axis
		List<WebElement> chartDates = driver.findElement(By.xpath("//*[@class='flotr-labels']")).findElements((By.className("flotr-grid-label")));
		int chartIntervalSize = 0, i = 0, days = 30;
		while(chartIntervalSize != 4){
			AssertJUnit.assertEquals((chartDates.get(i).getText()).replace("\n", " "), Util.getPreviousDate(days));
			days -= 10;
			chartIntervalSize++;
			i++;
		}
	}
	
	@Test(priority= 8)
	public void checkQuoteCurrencySelector() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		Thread.sleep(100);
		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='quote_currency_selector']"));
		quoteCurrency.click();
		List<WebElement> currencyQDropDown = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.className("ltr_list_item"));
		currencyQDropDown.get(3).click();	
	}
	
	@Test(priority= 9)
	public void checkBaseCurrencySelector() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		
		Thread.sleep(100);
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		List<WebElement> currencyBDropDown = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.className("ltr_list_item"));
		currencyBDropDown.get(3).click();
	}
	
	@Test(priority= 10)
	public void checkAutoCompleteQuoteCurrencySelector() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		Thread.sleep(100);
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
					Assert.assertTrue(currencyInfo.toLowerCase().contains("af"));
				
			}
			i++;
		}
	}
	
	@Test(priority= 11)
	public void checkAutoCompleteBaseCurrencySelector() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		Thread.sleep(100);
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		baseCurrency.sendKeys("Eu");
		List<WebElement> currencyNames = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.xpath("//*[@class='left name']"));
		List<WebElement> currencyCodes = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.xpath("//*[@class='code_right']"));

		int i = 0;
		String currencyInfo = new String();
		while(i < currencyNames.size()){
			
			if (currencyNames.get(i).isDisplayed()){
					currencyInfo = "";
					currencyInfo += currencyNames.get(i).getAttribute("innerHTML")
								 +" "+currencyCodes.get(i).getAttribute("innerHTML");
					Assert.assertTrue(currencyInfo.toLowerCase().contains("eu"));
				
			}
			i++;
		}
	}
	
	@Test(priority = 13)
	public void checkDatesAxisForChart() throws ParseException{
		
		// verify that correct dates are displayed for chart when selected multiple option
		int chartIntervalSize = 0, i = 0, days = 0, daysOption = 3, intervalDays = 0;
		
		while(daysOption != 0){
			// choose option of days 
			if(daysOption == 3){
				driver.findElement(By.xpath("//*[@id='range30']")).click();
				days = 30; intervalDays = 10;
			}else if(daysOption == 2){
				driver.findElement(By.xpath("//*[@id='range60']")).click();
				days = 60; intervalDays = 20;
			}else{
				driver.findElement(By.xpath("//*[@id='range90']")).click();
				days = 90; intervalDays = 30;
			}	
			
			// verify the dates axis
			List<WebElement> chartDates = driver.findElement(By.xpath("//*[@class='flotr-labels']")).findElements((By.className("flotr-grid-label")));
			i = 0; chartIntervalSize = 0;
			while(chartIntervalSize != 4){
				System.out.println((chartDates.get(i).getText()).replace("\n", " ")+" "+Util.getPreviousDate(days)+" "+daysOption+" "+days);
				AssertJUnit.assertEquals((chartDates.get(i).getText()).replace("\n", " "), Util.getPreviousDate(days));
				days -= intervalDays;
				chartIntervalSize++;
				i++;
			} 
			daysOption--;
		}
	}
	
	@Test(priority= 14)
	public void checkFavoriteQuoteCurrencySelectorAfterSelectingCuurency() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		WebElement quoteCurrency =  driver.findElement(By.xpath("//*[@id='quote_currency_selector']"));
		quoteCurrency.click();
		List<WebElement> currencyQDropDown = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.className("ltr_list_item"));
		currencyQDropDown.get(9).click();	
		
		List<WebElement> currencyCodes = quoteCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-1']")).findElements(By.xpath("//*[@class='code_right']"));

		Assert.assertEquals(currencyCodes.get(0).getAttribute("innerHTML"), Util.getQuotedCurrencyAbbrevation());
	}
	
	@Test(priority= 15)
	public void checkFavoriteBaseCurrencySelectorAfterSelectingCuurency() throws InterruptedException{
		
		// verify user should be able to select currency from both drop down	
		WebElement baseCurrency =  driver.findElement(By.xpath("//*[@id='base_currency_selector']"));
		baseCurrency.click();
		List<WebElement> currencyQDropDown = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.className("ltr_list_item"));
		currencyQDropDown.get(9).click();	
		
		List<WebElement> currencyCodes = baseCurrency.findElement(By.xpath("//*[@id='scroll-innerBox-2']")).findElements(By.xpath("//*[@class='code_right']"));

		Assert.assertEquals(currencyCodes.get(0).getAttribute("innerHTML"), Util.getQuotedCurrencyAbbrevation());
	}
	
	
	
	@AfterTest
	public void closeBrowser(){
		driver.close();
		System.exit(0);
		
	}
	
}
