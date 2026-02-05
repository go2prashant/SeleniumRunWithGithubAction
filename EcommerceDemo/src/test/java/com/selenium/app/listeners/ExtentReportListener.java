package com.selenium.app.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private static final String REPORTS_DIR = "target/extent-reports";

    @Override
    public void onStart(ITestContext context) {
        // Create reports directory if it doesn't exist
        new File(REPORTS_DIR).mkdirs();

        // Create timestamp for unique report names
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = REPORTS_DIR + "/ExtentReport_" + timestamp + ".html";

        // Initialize ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Selenium Test Report");
        sparkReporter.config().setReportName("E-Commerce Demo Test Report");
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        // Add system information
        extentReports.setSystemInfo("Test Environment", "QA");
        extentReports.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));

        System.out.println("✓ Extent Report initialized at: " + reportPath);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        extentTest = extentReports.createTest(testName, description);
        extentTest.info("Test started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        extentTest.pass("✓ Test passed: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        extentTest.fail("✗ Test failed: " + testName);
        if (throwable != null) {
            extentTest.fail(throwable);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        extentTest.skip("⊘ Test skipped: " + testName);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not commonly used
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("✓ Extent Report generated successfully!");
            System.out.println("✓ Report location: " + new File(REPORTS_DIR).getAbsolutePath());
        }
    }
}
