package com.org.listeners;

import java.io.IOException;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.org.utilities.ExtentManager;
import com.org.utilities.TestUtil;




public class CustomListener implements ITestListener {
	
	public static ExtentReports extent=ExtentManager.createInstance();
	public static ThreadLocal<ExtentTest> testReport=new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test=extent.createTest("Test started for Test: "+result.getTestClass().getName()+" and Method: "+result.getMethod().getMethodName());
		testReport.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Markup m=MarkupHelper.createLabel(result.getMethod().getMethodName().toUpperCase()+" is Passed", ExtentColor.GREEN);
		testReport.get().pass(m);
		Reporter.log(result.getMethod().getMethodName().toUpperCase()+" is Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		Markup m=MarkupHelper.createLabel(result.getMethod().getMethodName().toUpperCase()+" is Failed", ExtentColor.RED);
		testReport.get().fail(m);
		TestUtil.captureScreenshot();
		testReport.get().log(Status.FAIL, result.getThrowable().getMessage());
		String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		
		testReport.get().fail("<details> <summary> <font color=red> Exception Occured : Click to see </font></summary> "+exceptionMessage.replaceAll(",", "<br>")+"</details> ");
		try {
			testReport.get().addScreenCaptureFromPath(TestUtil.screenshotPathExtent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reporter.log(result.getMethod().getMethodName().toUpperCase()+" is Failed");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Markup m=MarkupHelper.createLabel(result.getMethod().getMethodName().toUpperCase()+" is Skipped", ExtentColor.ORANGE);
		testReport.get().skip(m);
		testReport.get().log(Status.SKIP, result.getThrowable().getMessage());
String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		
		testReport.get().skip("<details> <summary> <font color=orange> Exception Occured : Click to see </font></summary> "+exceptionMessage.replaceAll(",", "<br>")+"</details> ");
		Reporter.log(result.getMethod().getMethodName().toUpperCase()+" is Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		if (extent != null) {

			extent.flush();
		}
	}

}
