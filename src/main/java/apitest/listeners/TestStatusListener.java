package apitest.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import apitest.restassuredFuntions.API;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

 
public class TestStatusListener extends API implements ITestListener {
	
	QueryableRequestSpecification queryableRequestSpecification ;
	//to capture the unique thread Id of each test. While creating a test, will pass that thread Id in below var.
	ExtentTest test;
	ExtentReports extent = ExtentManager.getReporter();
	ThreadLocal<ExtentTest> threadSafe = new ThreadLocal<>();
	
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
 
    
    public void onStart(ITestContext iTestContext) {
        System.out.println("Initializing TestClass - " + iTestContext.getName());
    }
 
    
    public void onFinish(ITestContext iTestContext) {
        threadSafe.get().log(Status.INFO, "Finishing TestClass - " + iTestContext.getName());
        threadSafe.get().log(Status.INFO, MarkupHelper.createLabel("Completed executing all the test cases", ExtentColor.BLUE));
        extent.flush();
        try {
			Desktop.getDesktop().browse(new File(System.getProperty("user.dir")+"\\extendReports\\indexWindows.html").toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
       
    }

 
    public void onTestStart(ITestResult iTestResult) {
    	test = extent.createTest(iTestResult.getMethod().getMethodName()).assignAuthor("Core Automation Team").assignCategory("Regression").assignDevice("Window 10");
    	threadSafe.set(test);
        System.out.println("Initializing test method " + getTestMethodName(iTestResult)+ " start");
    }
 
    
    public void onTestSuccess(ITestResult iTestResult) {
        threadSafe.get().log(Status.PASS, getTestMethodName(iTestResult)+ " is passed"); 
        queryableRequestSpecification = SpecificationQuerier.query(reqSpec);
        threadSafe.get().log(Status.INFO,MarkupHelper.createLabel("Testcase Passed :: "+"",ExtentColor.GREEN));
        threadSafe.get().log(Status.INFO, "Request URI :: " +queryableRequestSpecification.getURI());
	    threadSafe.get().log(Status.INFO, "Request Payload is :: "+ queryableRequestSpecification.getBody());
        threadSafe.get().log(Status.INFO, "Response is :: "+resp.asString());
    }
 
    
    public void onTestFailure(ITestResult iTestResult) {
    	queryableRequestSpecification = SpecificationQuerier.query(reqSpec);
    	threadSafe.get().log(Status.INFO, "Request URI :: " +queryableRequestSpecification.getURI());
    	threadSafe.get().log(Status.INFO, "Failed Request Payload is :: "+ queryableRequestSpecification.getBody());
        threadSafe.get().log(Status.INFO, "Failed Response is :: "+resp.asString());
        threadSafe.get().log(Status.FAIL,"Classname :: "+""+iTestResult.getTestClass());
        threadSafe.get().log(Status.FAIL,"MethodName :: "+""+iTestResult.getMethod().getMethodName());
        threadSafe.get().log(Status.FAIL,MarkupHelper.createLabel("FAILED with status code :: "+""+resp.getStatusCode(),ExtentColor.RED));
        threadSafe.get().log(Status.FAIL,MarkupHelper.createLabel("Testcase FAILED due to below issues :: "+"",ExtentColor.RED));
        threadSafe.get().fail(iTestResult.getThrowable());
      
	}
 
    
    public void onTestSkipped(ITestResult iTestResult) {
        //ExtentReports log operation for skipped tests.
    	queryableRequestSpecification = SpecificationQuerier.query(reqSpec);
    	threadSafe.get().log(Status.SKIP, "Request URI :: " +queryableRequestSpecification.getURI());
    	threadSafe.get().log(Status.SKIP, "Failed Request Payload is :: "+ queryableRequestSpecification.getBody());
        threadSafe.get().log(Status.SKIP, "Failed Response is :: "+resp.asString());
        threadSafe.get().log(Status.SKIP,"Classname :: "+""+iTestResult.getTestClass());
        threadSafe.get().log(Status.SKIP,"MethodName :: "+""+iTestResult.getMethod().getMethodName());
        threadSafe.get().log(Status.SKIP,MarkupHelper.createLabel("FAILED with status code :: "+""+resp.getStatusCode(),ExtentColor.RED));
        threadSafe.get().log(Status.SKIP,MarkupHelper.createLabel("Testcase FAILED due to below issues :: "+"",ExtentColor.RED));
        
    }
 
    
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        threadSafe.get().log(Status.WARNING, "Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}