package com.org.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import com.org.base.TestBase;





public class TestUtil extends TestBase{
	
	public static String screenshotPathExtent;
	public static String screenshotPathSurefire;
	public static int errorCount=0;
	public static String screenshotName;
	
	public static void captureScreenshot()
	{
		
		File sourceFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//screenshotName = d.toString().replace(":", "_").replace(" ", "_")+"_error.jpg";
		errorCount++;
		screenshotName="error"+errorCount+".jpg";
		screenshotPathExtent="F:\\Selenium MyProjects\\DataDrivenApproach\\src\\test\\resources\\screenshots\\"+screenshotName;
		screenshotPathSurefire=".\\target\\surefire-reports\\html\\"+screenshotName;
		
			try {
				FileUtils.copyFile(sourceFile, new File(screenshotPathExtent));
				FileUtils.copyFile(sourceFile, new File(screenshotPathSurefire));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Screenshot not copied");
				Reporter.log("Screenshot not copied");
				//CustomListener.test.log(Status.FAIL, "Screenshot not taken from TestUtil class");
			}
	}
	@DataProvider(name="dp1")
	public Object[][] getData(Method m){
		
		
		
		String sheetName=m.getName();
	
		 int rowCount = excelRead.getRowCount(sheetName); 
		 int colCount = excelRead.getColumnCount(sheetName);
		 
		Object[][] data = new Object[rowCount -1][colCount]; 
		 for (int i = 2; i <= rowCount; i++) { 
			 for (int j = 0; j <colCount; j++) {
		 
		 data[i-2][j] = excelRead.getCellData(sheetName, i, j); }
		 
		 }
		 log.debug("Data provided successfully");
		 Reporter.log("DataProvided Executed successfully");
		 //CustomListener.test.log(Status.INFO, "Screenshot is taken from TestUtil");
		return data;

}
	
	public static boolean isTestRunnable(String testName,ExcelReading excelRead)
	{
		String sheetName="test_suite";
		int rows=excelRead.getRowCount(sheetName);
		for(int rowNum=2;rowNum<=rows;rowNum++)
		{
		if(testName.equalsIgnoreCase(excelRead.getCellData(sheetName, rowNum, "TCID")))
		{
			if(excelRead.getCellData(sheetName, rowNum, "RunMode").equalsIgnoreCase("Y"))
			{
				return true;
			}
			else
				return false;
		}
		 
		}
		return false;
	}
}
