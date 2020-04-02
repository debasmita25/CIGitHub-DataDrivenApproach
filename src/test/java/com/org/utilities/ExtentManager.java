package com.org.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewStyle;

public class ExtentManager {
	
	static ExtentReports extent;
	
	public static ExtentReports createInstance()
	{
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"//src//test//resources//extentreports//extent.html");
		sparkReporter.config().setEncoding("uft-8");
		sparkReporter.config().setCSS("css-string");
		sparkReporter.config().setDocumentTitle("Automation Reports");
		sparkReporter.config().setReportName("Build Version 1.0");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setTimeStampFormat("MMM dd,yyyy HH:mm:ss");
		
		 
		 extent = new ExtentReports();
		 extent.attachReporter(sparkReporter);

		 extent.setSystemInfo("Tester Name", "Debasmita Biswas");
		 extent.setSystemInfo("ENV", "UAT");
		 
		 return extent;
	}
	
	public static String captureScreenshot(WebDriver driver,String methodName,String path) 
	{ 
		System.out.println(driver.toString()+"..."+"methodName");
		 TakesScreenshot screenshot=(TakesScreenshot)driver;
	  	 File source=screenshot.getScreenshotAs(OutputType.FILE);
	  	
         Date d=new Date();
		
		// String fileConcat= d.toString().replace(":", "_").replace(" ", "_");
		 String newPath=path+"\\screenshots\\"+methodName+".png";
	  	 try {
		 FileUtils.copyFile(source, new File(newPath));
	  	 }
	  	 catch(IOException e)
	  	 {
	  		 e.printStackTrace();
	  	 }
	  	
	  	 return newPath;
	}

}
