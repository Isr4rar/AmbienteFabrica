package commons;

import static aqui.fica.o.driver.DriverFactory.getDriver;
import static aqui.fica.o.driver.DriverFactory.resetDriver;
import static aqui.fica.o.driver.DriverFactory.setDriver;
import static aqui.fica.o.driver.DriverFactory.setSizeAndPosition;
import static aqui.fica.o.driver.DriverFactory.startUrl;
import static utils.ExtentManager.getCurrentPlatform;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.openqa.selenium.Platform.LINUX;
import static org.openqa.selenium.Platform.MAC;
import static org.openqa.selenium.Platform.WINDOWS;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentManager;


public class InvokedMethodListener implements IInvokedMethodListener, ITestListener, ISuiteListener  {
	
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			System.out.println("Test Method BeforeInvocation is started. " + Thread.currentThread().getId());
			String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("browser");
			setDriver(browserName);
			setSizeAndPosition();
			startUrl("https://www.google.com/");
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		IRetryAnalyzer retry = testResult.getMethod().getRetryAnalyzer();
		if (retry == null) {
			return;
		} 
		testResult.getTestContext().getFailedTests().removeResult(testResult.getMethod());
		testResult.getTestContext().getSkippedTests().removeResult(testResult.getMethod());

		if (!testResult.isSuccess()) {
			File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
			try {
				if(getCurrentPlatform() == WINDOWS) {
					copyFile(scrFile, new File(System.getProperty("user.dir") + "//tmp//"+
							method.getTestMethod().getMethodName()+".png"));
					test.get().addScreenCaptureFromPath(System.getProperty("user.dir") + "//tmp//"+
							method.getTestMethod().getMethodName()+".png");
				}
				else if(getCurrentPlatform() == LINUX || getCurrentPlatform() == MAC) {
					copyFile(scrFile, new File(System.getProperty("user.dir") + "/tmp/"+
							method.getTestMethod().getMethodName()+".png"));
					test.get().addScreenCaptureFromPath(( System.getProperty("user.dir") + "/tmp/"+
							method.getTestMethod().getMethodName()+".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (method.isTestMethod()) {
			System.out.println("Test Method AfterInvocation is started. " + Thread.currentThread().getId());
			resetDriver();
		}
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
		test.get().assignCategory(result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf(".") + 1) + "_" +
				result.getMethod().getXmlTest().getLocalParameters().get("browser"));
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		test.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));

	}

	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Extent Reports Version 3 Test Suite started!");	
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Extent Reports Version 3  Test Suite is ending!"));
		extent.flush();
	}

	@Override
	public void onStart(ISuite suite) {
	}
	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Finish Suite" + suite.getName());

	}

}
