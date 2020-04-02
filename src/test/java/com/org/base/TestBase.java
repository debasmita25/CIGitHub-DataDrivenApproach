package com.org.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.org.listeners.CustomListener;

import com.org.utilities.ExcelReading;
import com.org.utilities.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static Date d = new Date();
	public static WebDriverWait wait;
	public static ExcelReading excelRead =new ExcelReading(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	
	@BeforeSuite
	public void setUp() {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");
		log.debug("Logger Property file is configured");
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		if (driver == null) {
			try {

				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				or.load(fis);
				log.debug("Object Repository - or file loaded successfully");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("config file is loaded successfully");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("ChromeDriver is launched successfully");
			} else if (config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equals("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();

			}

			driver.get(config.getProperty("testURL"));
			log.debug("Navigate To : " + config.getProperty("testURL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("explicit.wait")));
		}

	}
	
	public static boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}
	
	public void verifyEquals(String expected,String actual) {
		
		try
		{
			Assert.assertEquals(expected,actual);
		}
		catch(Throwable t)
		{
			TestUtil.captureScreenshot();
			log.debug("Assertion Verify Error");
			Reporter.log("Assertion Verify Error");
			Reporter.log("<br>");
			Reporter.log(t.getMessage());
			Reporter.log("<br>");
			Reporter.log("<a href="+TestUtil.screenshotName+" target=\"_blank\"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			
			
			CustomListener.testReport.get().fail("Verify Equals fail");
			CustomListener.testReport.get().log(Status.FAIL, t.getMessage());
			String exceptionMessage=Arrays.toString(t.getStackTrace());
			
			CustomListener.testReport.get().fail("<details> <summary> <font color=red> Exception Occured : Click to see </font></summary> "+exceptionMessage.replaceAll(",", "<br>")+"</details> ");
			try {
				CustomListener.testReport.get().fail("<font color=red>FAILED SCREENSHOT: </font><br>", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotPathExtent).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.close();
			log.debug("Test Execution completed");
		}
	}

	// keywords

	public static void click(String locator) {
		int check = locator.indexOf("_");

		switch (locator.substring(check)) {
		case "_css":
			driver.findElement(By.cssSelector(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_xpath":
			driver.findElement(By.xpath(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_id":
			driver.findElement(By.id(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_name":
			driver.findElement(By.name(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_classname":
			driver.findElement(By.className(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_partiallinktext":
			driver.findElement(By.partialLinkText(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_linktext":
			driver.findElement(By.linkText(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		case "_tagname":
			driver.findElement(By.tagName(or.getProperty(locator))).click();
			//CustomListener.test.log(Status.INFO, "CLICKING ON " + locator);
			break;
		default:
			//CustomListener.test.log(Status.ERROR, "UNABLE TO CLICK incorrect Locator");
			break;

		}
		log.debug("Required Element is Clicked");
	}

	public static void type(String locator, String value) {
		int check = locator.indexOf("_");

		switch (locator.substring(check)) {
		case "_css":
			driver.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_xpath":
			driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_id":
			driver.findElement(By.id(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_name":
			driver.findElement(By.name(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_classname":
			driver.findElement(By.className(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_partiallinktext":
			driver.findElement(By.partialLinkText(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_linktext":
			driver.findElement(By.linkText(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		case "_tagname":
			driver.findElement(By.tagName(or.getProperty(locator))).sendKeys(value);
			//CustomListener.test.log(Status.INFO, "TYPING ON " + locator + " with value " + value);
			break;
		default:
			//CustomListener.test.log(Status.DEBUG, "Unable to TYPE, INCORRECT LOCATOR");
			break;

		}
		log.debug("Required Element is Typed");
	}

	public static void dropdownSelect(String locator, String selectioncriteria, String value) {
		int check = locator.indexOf("_");
		WebElement element = null;
		switch (locator.substring(check)) {
		case "_css":
			element = driver.findElement(By.cssSelector(or.getProperty(locator)));
			break;
		case "_xpath":
			element = driver.findElement(By.xpath(or.getProperty(locator)));
			break;
		case "_id":
			element = driver.findElement(By.id(or.getProperty(locator)));
			break;
		case "_name":
			element = driver.findElement(By.name(or.getProperty(locator)));
			break;
		case "_classname":
			element = driver.findElement(By.className(or.getProperty(locator)));
			break;
		case "_partiallinktext":
			element = driver.findElement(By.partialLinkText(or.getProperty(locator)));
			break;
		case "_linktext":
			element = driver.findElement(By.linkText(or.getProperty(locator)));
			break;
		case "_tagname":
			element = driver.findElement(By.tagName(or.getProperty(locator)));
			break;
		default:
			//CustomListener.test.log(Status.DEBUG, "Unable to SELECT, INCORRECT LOCATOR");
			break;

		}
		if (element != null) {
			Select select = new Select(element);

			if (selectioncriteria.equalsIgnoreCase("selectByindex")) {
				select.selectByIndex(Integer.parseInt(value));
				//CustomListener.test.log(Status.INFO, "selected the option by index");
			} else if (selectioncriteria.equalsIgnoreCase("selectByValue")) {
				select.selectByValue(value);
				//CustomListener.test.log(Status.INFO, "selected the option by value");
			} else if (selectioncriteria.equalsIgnoreCase("selectByVisibleText")) {
				select.selectByVisibleText(value);
				//CustomListener.test.log(Status.INFO, "selected the option by VisibleText");
			} else
			{
				log.debug("Incorrect Selection option");
			}
				//CustomListener.test.log(Status.DEBUG, "selected OPTION IS INVALID");

		}
		
		log.debug("Required Element is Selected from DROPDOWN");

	}


}
