package com.org.testcases;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.org.base.TestBase;
import com.org.utilities.TestUtil;

public class OpenAccountTest extends TestBase

{
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp1")
	public void openAccountTest(String customername, String currency, String alertmsg) throws IOException {

		if(!TestUtil.isTestRunnable("OpenAccountTest", excelRead))
		{
			throw new SkipException("SKIPPING the Test AS RUNMODE is NO");
		}
		
		click("openAccnt_css");
		dropdownSelect("custName_id", "selectbyvisibletext", customername);
		dropdownSelect("currency_id", "selectbyvisibletext", currency);
		click("process_xpath");

		wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(driver.switchTo().alert().getText().toLowerCase().contains(alertmsg.toLowerCase()),"Alert Text is Not Present");
		 log.debug("Account created successfully");
	        Reporter.log("Account created successfully");
	        driver.switchTo().alert().accept();
	}
}
