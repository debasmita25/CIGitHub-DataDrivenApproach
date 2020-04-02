package com.org.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.org.base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankMngLogin() {

		
		verifyEquals("abc", "xyz");
	
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(or.getProperty("bmlBtn_xpath"))));
		log.debug("Inside Bank manager Login Test");
		click("bmlBtn_xpath");
		Assert.assertTrue(isElementPresent(By.cssSelector(or.getProperty("addCust_css"))),
				"Bank Manager Login Is NOT successful");
		log.debug("Bank Manager successfully logged in ");
		Reporter.log("Bank Manager Logged in successfully :)");
	}

}
