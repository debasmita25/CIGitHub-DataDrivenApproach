package com.org.testcases;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.org.base.TestBase;
import com.org.utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class,dataProvider="dp1")
	public void addCustomerTest(String firstname,String lastname,String postcode,String alertmsg,String runmode)
	{
		log.debug("Inside Customer added test");
		
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(or.getProperty("bmlBtn_xpath"))));
		//click("bmlBtn_xpath");
		
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(or.getProperty("firstName_css"))));
		if(!runmode.equalsIgnoreCase("Y"))
		{
		  throw new SkipException("Skipping the TEST as the RUNMODE is NO");
		}
		click("addCust_css");
		type("firstName_css",firstname);
		type("lastName_css",lastname);
		type("postCode_css",postcode);
		click("addCustBtn_css");
		
		
		wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(driver.switchTo().alert().getText().toLowerCase().contains(alertmsg.toLowerCase()),"Alert Text is Not Present");
		
        log.debug("Customer added successfully");
        Reporter.log("Customer added successfully");
        driver.switchTo().alert().accept();
		
	}
	

}
